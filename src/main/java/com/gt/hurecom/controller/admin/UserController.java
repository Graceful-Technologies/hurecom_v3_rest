package com.gt.hurecom.controller.admin;

import com.gt.hurecom.dto.admin.UserRequest;
import com.gt.hurecom.dto.admin.UserResponse;
import com.gt.hurecom.dto.admin.UserSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.service.admin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        log.debug("Controller :: createUser :: Entered");

        UserResponse response = userService.createUser(request);

        log.debug("Controller :: createUser :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        log.debug("Controller :: updateUser :: Entered");

        UserResponse response = userService.updateUser(id, request);

        log.debug("Controller :: updateUser :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        log.debug("Controller :: getUser :: Entered");

        UserResponse response = userService.getUser(id);

        log.debug("Controller :: getUser :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponse>> getActiveUsers() {
        log.debug("Controller :: getActiveUsers :: Entered");

        List<UserResponse> response = userService.getActiveUsers();

        log.debug("Controller :: getActiveUsers :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<UserResponse>> searchUsers(@RequestBody UserSearchRequest request) {
        log.debug("Controller :: searchUsers :: Entered");

        PageResponse<UserResponse> response = userService.searchUsers(request);

        log.debug("Controller :: searchUsers :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<UserResponse>> getAvailableUsers(@RequestParam("teamId") Long teamId) {
        log.debug("Controller :: getAvailableUsers :: Entered");

        List<UserResponse> response = userService.getAvailableUsers(teamId);

        log.debug("Controller :: getAvailableUsers :: Exited");
        return ResponseEntity.ok(response);
    }
}
