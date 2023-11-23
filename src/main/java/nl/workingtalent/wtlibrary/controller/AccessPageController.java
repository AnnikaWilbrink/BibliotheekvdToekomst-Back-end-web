package nl.workingtalent.wtlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import nl.workingtalent.wtlibrary.service.AccessPageService;
import nl.workingtalent.wtlibrary.dto.AccessControlDto;

import nl.workingtalent.wtlibrary.model.User;

@Controller
public class AccessPageController {

    @Autowired
    private AccessPageService accessPageService;

    @PostMapping(value = "/authenticate/admin", produces = "application/json")
    public ResponseEntity<User> adminPage(@RequestBody AccessControlDto accessRequestDto) {
        String role = accessRequestDto.getRole();
        String token = accessRequestDto.getToken();
        long userId = accessRequestDto.getUserId();
        User user = accessPageService.adminHasPermissionToAccessPage(role, token, userId);
        if (user.isAdmin() == true){
            return ResponseEntity.ok(user);
        }
        /* 
        if (accessPageService.adminHasPermissionToAccessPage(role, token, userId)) {
            return ResponseEntity.ok(true);
        } 
        return ResponseEntity.ok(false);
        */
    }

    @PostMapping(value = "/authenticate/frontdesk", produces = "application/json")
    public ResponseEntity<Boolean> frontdeskPage(@RequestBody AccessControlDto accessRequestDto) {
        String role = accessRequestDto.getRole();
        String token = accessRequestDto.getToken();
        long userId = accessRequestDto.getUserId();
        if (accessPageService.frontdeskHasPermissionToAccessPage(role, token, userId)) {
            return ResponseEntity.ok(true);
        } 
        return ResponseEntity.ok(false);
    }

    /* 
    @PostMapping(value = "/authenticate/user/{id}", produces = "application/json")
    public ResponseEntity<Boolean> userPage(@RequestBody AccessControlDto accessRequestDto) {
        String role = accessRequestDto.getRole();
        String token = accessRequestDto.getToken();
        long userId = accessRequestDto.getUserId();
        if (accessPageService.hasPermissionToAccessPage(role, token, userId)) {
            return ResponseEntity.ok(true);
        } 
        return ResponseEntity.ok(false);
    }
    */
}

