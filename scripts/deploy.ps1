$ErrorActionPreference = "Stop"

# ============================================================
# HURECOM DEV DEPLOYMENT CONFIGURATION
# ============================================================

$deployPath = "C:\Apps\hurecom-dev"

$stagedJar = "$deployPath\target\hurecom-0.0.1-SNAPSHOT.jar"

$currentJar = "$deployPath\hurecom.jar"

$backupJar = "$deployPath\hurecom.jar.bak"

$javaExe = "C:\Program Files\Java\jdk-17\bin\java.exe"

$port = 8280

$devUrl = "http://localhost:8280"


# ============================================================
# HELPER: CHECK DEV PORT
# ============================================================

function Test-DevPort {

    $connection = Get-NetTCPConnection `
        -LocalPort $port `
        -State Listen `
        -ErrorAction SilentlyContinue

    return [bool]$connection
}


# ============================================================
# START
# ============================================================

Write-Host ""
Write-Host "=================================================="
Write-Host "       HURECOM DEV DEPLOYMENT"
Write-Host "=================================================="
Write-Host ""

Write-Host "Deployment path:"
Write-Host $deployPath

Write-Host "DEV port:"
Write-Host $port

Write-Host ""


# ============================================================
# CHECK JAVA
# ============================================================

if (!(Test-Path $javaExe)) {

    throw "Java executable not found: $javaExe"
}

Write-Host "Java executable found."


# ============================================================
# CHECK STAGED JAR
# ============================================================

Write-Host ""
Write-Host "Checking staged JAR..."

if (!(Test-Path $stagedJar)) {

    throw "Staged JAR not found: $stagedJar"
}

$stagedInfo = Get-Item $stagedJar

Write-Host "Staged JAR:"
Write-Host "  Name : $($stagedInfo.Name)"
Write-Host "  Size : $($stagedInfo.Length) bytes"
Write-Host "  Date : $($stagedInfo.LastWriteTime)"


# ============================================================
# FIND DEV PROCESS
# ============================================================

Write-Host ""
Write-Host "Checking DEV application..."

$devProcesses = Get-CimInstance Win32_Process | Where-Object {

    $_.CommandLine -and
    $_.CommandLine -match "server\.port=8280" -and
    $_.CommandLine -match "hurecom"
}


foreach ($process in $devProcesses) {

    Write-Host "DEV process found:"
    Write-Host "  PID: $($process.ProcessId)"
    Write-Host "  Command: $($process.CommandLine)"
}


# ============================================================
# STOP ONLY DEV
# ============================================================

Write-Host ""
Write-Host "Stopping DEV application..."

foreach ($process in $devProcesses) {

    Write-Host "Stopping DEV PID: $($process.ProcessId)"

    Stop-Process `
        -Id $process.ProcessId `
        -Force `
        -ErrorAction SilentlyContinue
}

Start-Sleep -Seconds 5


# ============================================================
# VERIFY DEV PORT IS FREE
# ============================================================

Write-Host ""
Write-Host "Checking port 8280..."

if (Test-DevPort) {

    $portDetails = Get-NetTCPConnection `
        -LocalPort $port `
        -State Listen `
        -ErrorAction SilentlyContinue

    $portDetails |
        Select-Object LocalAddress, LocalPort, OwningProcess |
        Format-Table -AutoSize

    throw "Port 8280 is still in use. Deployment stopped."
}

Write-Host "Port 8280 is free."


# ============================================================
# BACKUP CURRENT JAR
# ============================================================

if (Test-Path $currentJar) {

    Write-Host ""
    Write-Host "Creating backup of current JAR..."

    Copy-Item `
        $currentJar `
        $backupJar `
        -Force

    Write-Host "Backup created:"
    Write-Host $backupJar

}
else {

    Write-Host ""
    Write-Host "No existing hurecom.jar found."
    Write-Host "This is the first deployment."
}


# ============================================================
# INSTALL NEW JAR
# ============================================================

Write-Host ""
Write-Host "Installing new JAR..."

Copy-Item `
    $stagedJar `
    $currentJar `
    -Force

Write-Host "New JAR installed:"
Write-Host $currentJar


# ============================================================
# START NEW DEV VERSION
# ============================================================

Write-Host ""
Write-Host "Starting new DEV application..."

Start-Process `
    -FilePath $javaExe `
    -ArgumentList "-jar", $currentJar, "--server.port=8280" `
    -WorkingDirectory $deployPath

Write-Host "Application start command executed."


# ============================================================
# WAIT FOR STARTUP
# ============================================================

Write-Host ""
Write-Host "Waiting 15 seconds for application startup..."

Start-Sleep -Seconds 15


# ============================================================
# VERIFY NEW VERSION
# ============================================================

Write-Host ""
Write-Host "Checking DEV port 8280..."

if (Test-DevPort) {

    Write-Host "Port 8280 is listening."

}
else {

    Write-Host ""
    Write-Host "=================================================="
    Write-Host "       NEW VERSION FAILED TO START"
    Write-Host "       STARTING AUTOMATIC ROLLBACK"
    Write-Host "=================================================="
    Write-Host ""


    # ========================================================
    # STOP FAILED VERSION
    # ========================================================

    $failedProcesses = Get-CimInstance Win32_Process | Where-Object {

        $_.CommandLine -and
        $_.CommandLine -match "server\.port=8280" -and
        $_.CommandLine -match "hurecom\.jar"
    }


    foreach ($process in $failedProcesses) {

        Write-Host "Stopping failed DEV PID: $($process.ProcessId)"

        Stop-Process `
            -Id $process.ProcessId `
            -Force `
            -ErrorAction SilentlyContinue
    }

    Start-Sleep -Seconds 3


    # ========================================================
    # CHECK BACKUP
    # ========================================================

    if (!(Test-Path $backupJar)) {

        throw "CRITICAL: Rollback failed because backup JAR does not exist."
    }


    # ========================================================
    # RESTORE BACKUP
    # ========================================================

    Write-Host ""
    Write-Host "Restoring previous JAR..."

    Copy-Item `
        $backupJar `
        $currentJar `
        -Force

    Write-Host "Previous JAR restored."


    # ========================================================
    # START PREVIOUS VERSION
    # ========================================================

    Write-Host ""
    Write-Host "Starting previous DEV version..."

    Start-Process `
        -FilePath $javaExe `
        -ArgumentList "-jar", $currentJar, "--server.port=8280" `
        -WorkingDirectory $deployPath

    Start-Sleep -Seconds 15


    # ========================================================
    # VERIFY ROLLBACK
    # ========================================================

    Write-Host ""
    Write-Host "Verifying rollback..."

    if (!(Test-DevPort)) {

        Write-Host ""
        Write-Host "=================================================="
        Write-Host "       CRITICAL ROLLBACK FAILURE"
        Write-Host "=================================================="
        Write-Host ""

        throw "CRITICAL: Previous DEV version failed to start."
    }


    Write-Host ""
    Write-Host "=================================================="
    Write-Host "       AUTOMATIC ROLLBACK SUCCESSFUL"
    Write-Host "=================================================="
    Write-Host ""

    throw "New DEV version failed. Previous version restored successfully."
}


# ============================================================
# HTTP CHECK
# ============================================================

Write-Host ""
Write-Host "Testing DEV HTTP endpoint..."

try {

    $response = Invoke-WebRequest `
        -Uri $devUrl `
        -UseBasicParsing `
        -TimeoutSec 10 `
        -ErrorAction Stop

    Write-Host "HTTP Status: $($response.StatusCode)"
}
catch {

    if ($_.Exception.Response) {

        $statusCode = [int]$_.Exception.Response.StatusCode

        Write-Host "HTTP Status: $statusCode"

        # 403 is expected because Spring Security
        # protects the root endpoint.

        if ($statusCode -ne 403) {

            Write-Host ""
            Write-Host "Unexpected HTTP status."
            Write-Host "Starting rollback..."

            # Stop current version
            $failedProcesses = Get-CimInstance Win32_Process | Where-Object {

                $_.CommandLine -and
                $_.CommandLine -match "server\.port=8280" -and
                $_.CommandLine -match "hurecom\.jar"
            }

            foreach ($process in $failedProcesses) {

                Stop-Process `
                    -Id $process.ProcessId `
                    -Force `
                    -ErrorAction SilentlyContinue
            }

            Start-Sleep -Seconds 3

            # Restore backup
            if (!(Test-Path $backupJar)) {

                throw "CRITICAL: HTTP check failed and rollback backup does not exist."
            }

            Copy-Item `
                $backupJar `
                $currentJar `
                -Force

            # Start previous version
            Start-Process `
                -FilePath $javaExe `
                -ArgumentList "-jar", $currentJar, "--server.port=8280" `
                -WorkingDirectory $deployPath

            Start-Sleep -Seconds 15

            if (!(Test-DevPort)) {

                throw "CRITICAL: HTTP check failed and rollback also failed."
            }

            throw "New DEV version failed HTTP verification. Rollback successful."
        }

    }
    else {

        Write-Host "HTTP endpoint could not be reached."

        throw "DEV HTTP verification failed."
    }
}


# ============================================================
# CLEAN STAGING JAR
# ============================================================

if (Test-Path $stagedJar) {

    Write-Host ""
    Write-Host "Removing staged JAR..."

    Remove-Item `
        $stagedJar `
        -Force
}


# ============================================================
# SUCCESS
# ============================================================

Write-Host ""
Write-Host "=================================================="
Write-Host "       HURECOM DEV DEPLOYMENT SUCCESSFUL"
Write-Host "=================================================="
Write-Host ""
Write-Host "DEV URL : http://dev.hurecom.com"
Write-Host "PORT    : 8280"
Write-Host "JAR     : $currentJar"
Write-Host ""