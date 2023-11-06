package nl.workingtalent.wtlibrary.controller;

import nl.workingtalent.wtlibrary.dto.InvitationTokenDto;
import nl.workingtalent.wtlibrary.dto.InvitationTokenRoleDto;
import nl.workingtalent.wtlibrary.model.InvitationToken;
import nl.workingtalent.wtlibrary.service.InvitationTokenService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class InvitationTokenController {

    @Autowired
    private InvitationTokenService service;

    @RequestMapping(method = RequestMethod.POST, value = "invitation-token/generate")
    public ResponseEntity<InvitationTokenDto> generateToken(@RequestBody InvitationTokenRoleDto dto) {
        try {
            InvitationToken invitationToken = service.generateToken(dto.getRole());
            InvitationTokenDto tokenDto = new InvitationTokenDto();
            tokenDto.setToken(invitationToken.getToken());
            tokenDto.setRole(invitationToken.getRole());
            tokenDto.setExpirationDate(invitationToken.getExpirationDate());
            return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("invitation-token/validate/{token}")
    public ResponseEntity<String> validateToken(@PathVariable String token) {
        String role = service.validateToken(token);
        if (role != null) {
            return new ResponseEntity<>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("invitation-token/getAllUnusedTokens")
    public ResponseEntity<List<InvitationTokenDto>> getAllUnusedTokens() {
        try {
            List<InvitationToken> unusedTokens = service.getAllTokens().stream()
                    .filter(token -> token.getDateUsed() == null)
                    .collect(Collectors.toList());

            List<InvitationTokenDto> tokenDtos = unusedTokens.stream().map(token -> {
                InvitationTokenDto dto = new InvitationTokenDto();
                dto.setToken(token.getToken());
                dto.setRole(token.getRole());
                dto.setExpirationDate(token.getExpirationDate());
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(tokenDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("invitation-token/delete/{token}")
    public ResponseEntity<?> deleteToken(@PathVariable String token) {
        boolean isDeleted = service.deleteToken(token);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token not found", HttpStatus.NOT_FOUND);
        }
    }
    
}
