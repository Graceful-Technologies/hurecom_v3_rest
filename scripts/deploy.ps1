$ErrorActionPreference = "Stop"

# ============================================================
# HURECOM DEV DEPLOYMENT
# ============================================================

$deployPath = "C:\Apps\hurecom-dev"
$stagedJar = "$deployPath\target\hurecom-0.0.1-SNAPSHOT.jar"
$currentJar = "$deployPath\hurecom.jar"
$backupJar = "$deployPath\hurecom.jar.bak"

$logsPath = "$deployPath\logs"
$applicationLog = "$logsPath\hurecom.log"
$errorLog = "$logsPath\hurecom-error.log"

$javaExe = "C:\Program Files\Java\jdk-17\bin\java.exe"

$port = 8280
$devUrl = "http://localhost:8280"

# Startup timeout
$startupTimeoutSeconds = 60


# ============================================================
# FUNCTIONS
# ============================================================

function Test-DevPort {

    $connection = Get-NetTCPConnection `
        -LocalPort $port `
        -State Listen `
        -ErrorAction SilentlyContinue

    return [bool]$connection
}


function Get-DevProcesses {

    return @(Get-CimInstance Win32_Process | Where-Object {

        $_.CommandLine -and
        $_.CommandLine -match "server\.port=$port" -and
        $_.CommandLine -match "hurecom"

    })
}


function Stop-DevProcesses {

    $processes = Get-DevProcesses

    foreach ($process in $processes) {

        Write-Host "Stopping DEV PID: $($process.ProcessId)"

        Stop-Process `
            -Id $process.ProcessId `
            -Force `
            -ErrorAction SilentlyContinue
    }

    Start-Sleep -Seconds 5
}


function Wait-ForDevStartup {

    Write-Host ""
    Write-Host "Waiting for DEV application startup..."

    $elapsed = 0

    while ($elapsed -lt $startupTimeoutSeconds) {

        if (Test-DevPort) {

            Write-Host "DEV port 8280 is listening."

            return $true
        }

        Start-Sleep -Seconds 5

        $elapsed += 5

        Write-Host "Waiting... $elapsed/$startupTimeoutSeconds seconds"
    }

    return $false
}


function Show-ApplicationError {

    Write-Host ""
    Write-Host "=================================================="
    Write-Host "APPLICATION ERROR LOG"
    Write-Host "=================================================="

    if (Test-Path $errorLog) {

        Get-Content $errorLog -Tail 80

    }
    else {

        Write-Host "Error log does not exist."

    }

    Write-Host ""
}


# ============================================================
# PREPARE
# ============================================================

Write-Host ""
Write-Host "=================================================="
Write-Host "       HURECOM DEV DEPLOYMENT"
Write-Host "=================================================="
Write-Host ""

Write-Host "Deployment path : $deployPath"
Write-Host "DEV port        : $port"
Write-Host "Java            : $javaExe"
Write-Host ""


# ============================================================
# CREATE LOG DIRECTORY
# ============================================================

if (!(Test-Path $logsPath)) {

    New-Item `
        -ItemType Directory `
        -Path $logsPath `
        -Force | Out-Null
}


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

Write-Host ""
Write-Host "Staged JAR:"
Write-Host "  Name : $($stagedInfo.Name)"
Write-Host "  Size : $($stagedInfo.Length) bytes"
Write-Host "  Date : $($stagedInfo.LastWriteTime)"


# ============================================================
# FIND CURRENT DEV
# ============================================================

Write-Host ""
Write-Host "Checking current DEV application..."

$devProcesses = Get-DevProcesses

foreach ($process in $devProcesses) {

    Write-Host ""
    Write-Host "DEV process found:"
    Write-Host "  PID     : $($process.ProcessId)"
    Write-Host "  Command : $($process.CommandLine)"
}


# ============================================================
# STOP DEV
# ============================================================

Write-Host ""
Write-Host "Stopping DEV application..."

Stop-DevProcesses


# ============================================================
# VERIFY PORT FREE
# ============================================================

Write-Host ""
Write-Host "Checking port 8280 is free..."

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
    Write-Host "Creating backup..."

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
    Write-Host "First deployment."
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
# CLEAR OLD LOGS
# ============================================================

if (Test-Path $applicationLog) {

    Remove-Item `
        $applicationLog `
        -Force `
        -ErrorAction SilentlyContinue
}

if (Test-Path $errorLog) {

    Remove-Item `
        $errorLog `
        -Force `
        -ErrorAction SilentlyContinue
}


# ============================================================
# START NEW VERSION
# ============================================================

Write-Host ""
Write-Host "Starting new DEV application..."

$process = Start-Process `
    -FilePath $javaExe `
    -ArgumentList "-jar", $currentJar, "--server.port=8280" `
    -WorkingDirectory $deployPath `
    -RedirectStandardOutput $applicationLog `
    -RedirectStandardError $errorLog `
    -WindowStyle Hidden `
    -PassThru

Write-Host ""
Write-Host "New DEV process started."
Write-Host "PID: $($process.Id)"


# ============================================================
# WAIT FOR STARTUP
# ============================================================

$started = Wait-ForDevStartup


# ============================================================
# NEW VERSION STARTED
# ============================================================

if ($started) {

    Write-Host ""
    Write-Host "=================================================="
    Write-Host "       DEV APPLICATION STARTED"
    Write-Host "=================================================="
    Write-Host ""

    Write-Host "Testing HTTP endpoint..."

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

            if ($statusCode -eq 403) {

                Write-Host "HTTP 403 is expected because Spring Security protects the root endpoint."

            }
            else {

                Write-Host "Unexpected HTTP status."
                $started = $false
            }

        }
        else {

            Write-Host "DEV HTTP endpoint could not be reached."
            $started = $false
        }
    }
}


# ============================================================
# SUCCESS
# ============================================================

if ($started) {

    Write-Host ""
    Write-Host "=================================================="
    Write-Host "       HURECOM DEV DEPLOYMENT SUCCESSFUL"
    Write-Host "=================================================="
    Write-Host ""

    Write-Host "DEV URL : http://dev.hurecom.com"
    Write-Host "PORT    : 8280"
    Write-Host "JAR     : $currentJar"
    Write-Host "PID     : $($process.Id)"
    Write-Host ""

    # Remove staging JAR
    if (Test-Path $stagedJar) {

        Write-Host "Removing staged JAR..."

        Remove-Item `
            $stagedJar `
            -Force
    }

    Write-Host ""
    Write-Host "Deployment completed successfully."

    exit 0
}


# ============================================================
# NEW VERSION FAILED
# ============================================================

Write-Host ""
Write-Host "=================================================="
Write-Host "       NEW VERSION FAILED TO START"
Write-Host "       STARTING AUTOMATIC ROLLBACK"
Write-Host "=================================================="
Write-Host ""


# ============================================================
# SHOW NEW VERSION ERROR
# ============================================================

Show-ApplicationError


# ============================================================
# STOP FAILED VERSION
# ============================================================

Write-Host ""
Write-Host "Stopping failed DEV version..."

Stop-DevProcesses


# ============================================================
# VERIFY PORT FREE
# ============================================================

if (Test-DevPort) {

    throw "CRITICAL: Port 8280 is still in use after stopping failed version."
}


# ============================================================
# CHECK BACKUP
# ============================================================

if (!(Test-Path $backupJar)) {

    throw "CRITICAL: Rollback backup does not exist: $backupJar"
}

Write-Host ""
Write-Host "Rollback backup found."


# ============================================================
# RESTORE PREVIOUS JAR
# ============================================================

Write-Host ""
Write-Host "Restoring previous JAR..."

Copy-Item `
    $backupJar `
    $currentJar `
    -Force

Write-Host "Previous JAR restored."


# ============================================================
# CLEAR LOGS FOR ROLLBACK
# ============================================================

if (Test-Path $applicationLog) {

    Remove-Item `
        $applicationLog `
        -Force `
        -ErrorAction SilentlyContinue
}

if (Test-Path $errorLog) {

    Remove-Item `
        $errorLog `
        -Force `
        -ErrorAction SilentlyContinue
}


# ============================================================
# START PREVIOUS VERSION
# ============================================================

Write-Host ""
Write-Host "Starting previous DEV version..."

$rollbackProcess = Start-Process `
    -FilePath $javaExe `
    -ArgumentList "-jar", $currentJar, "--server.port=8280" `
    -WorkingDirectory $deployPath `
    -RedirectStandardOutput $applicationLog `
    -RedirectStandardError $errorLog `
    -WindowStyle Hidden `
    -PassThru

Write-Host ""
Write-Host "Rollback process started."
Write-Host "PID: $($rollbackProcess.Id)"


# ============================================================
# WAIT FOR ROLLBACK
# ============================================================

$rollbackStarted = Wait-ForDevStartup


# ============================================================
# ROLLBACK SUCCESS
# ============================================================

if ($rollbackStarted) {

    Write-Host ""
    Write-Host "=================================================="
    Write-Host "       AUTOMATIC ROLLBACK SUCCESSFUL"
    Write-Host "=================================================="
    Write-Host ""

    Write-Host "Previous DEV version is running."
    Write-Host "Port 8280 is listening."
    Write-Host ""

    # Remove failed staged JAR
    if (Test-Path $stagedJar) {

        Write-Host "Removing failed staged JAR..."

        Remove-Item `
            $stagedJar `
            -Force
    }

    # IMPORTANT:
    # Return failure to GitHub Actions because the new
    # deployment failed, even though rollback succeeded.

    throw "New DEV version failed to start. Automatic rollback completed successfully."
}


# ============================================================
# CRITICAL ROLLBACK FAILURE
# ============================================================

Write-Host ""
Write-Host "=================================================="
Write-Host "       CRITICAL ROLLBACK FAILURE"
Write-Host "=================================================="
Write-Host ""

Show-ApplicationError

throw "CRITICAL: Previous DEV version also failed to start on port 8280."