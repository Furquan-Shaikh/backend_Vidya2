package edu.js.project.controller;

import edu.js.project.dto.StudentDto;
import edu.js.project.dto.UsersDto;
import edu.js.project.responseStructure.LoginSuccess;
import edu.js.project.service.UserService;
import edu.js.project.service.impl.BlacklistToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import edu.js.project.responseStructure.LoginRequest;
import edu.js.project.responseStructure.LoginResultResponse;
import edu.js.project.responseStructure.ResponseStructure;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/VidyaSarthi")

public class OAuthController {

    @Autowired
    private AuthenticationManager authManager;
    private JwtEncoder jwtEncoder;
    private BlacklistToken blacklistToken;
    private final UserService userService;

    public OAuthController( AuthenticationManager authManager, JwtEncoder jwtEncoder, BlacklistToken blacklistToken, UserService userService) {
        this.authManager = authManager;
        this.jwtEncoder = jwtEncoder;
        this.blacklistToken = blacklistToken;
        this.userService = userService;
    }

    ResponseStructure<String> rs = new ResponseStructure<>();

    @PostMapping("/signUpAcc")
    public ResponseEntity<ResponseStructure<String>>signUp(@RequestBody StudentDto req){

        boolean isSuccessfullySignedUp = userService.addUserToDB(req);
        if (isSuccessfullySignedUp){
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage(String.format("%s account create",req.getName()));

            return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
        }else {
            throw new RuntimeException("Username already exists");
        }

    }





    @PostMapping("/loginAcc")
    public ResponseEntity<?>loginAcc(@RequestBody LoginRequest req){

        Instant now = Instant.now();
        Authentication authenticate = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(),
                        req.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        List<String> roles = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(req.username())
                .expiresAt(now.plusSeconds(3600))
                .issuedAt(Instant.now())
                .claim("roles",roles)
                .build();


        String token = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        LoginResultResponse loginResultResponse = new LoginResultResponse();
        if(Objects.nonNull(token)) {

            loginResultResponse.setToken(token);
            loginResultResponse.setSuccess(true);
        }else {

            loginResultResponse.setToken(null);
            loginResultResponse.setSuccess(false);
        }
        UsersDto userDetail = userService.getUserDetail(req.username());
        LoginSuccess success = getLoginSuccess(userDetail, loginResultResponse);
        loginResultResponse.setDto(userService.getUserDetail(req.username()));
        return ResponseEntity.ok(success);
    }

    private static LoginSuccess getLoginSuccess(UsersDto userDetail, LoginResultResponse loginResultResponse) {
        String userId;
        if(("Admin").equals(userDetail.getRoles())){
            userId = userDetail.getAdminClgDto().getAdminId();
        } else if (("Faculty").equals(userDetail.getRoles())) {
            userId = userDetail.getNewTeacherDto().getFacultyId();
        }else {
            userId = userDetail.getStudentDto().getStudentId();
        }
        LoginSuccess success = new LoginSuccess(loginResultResponse.getToken(), userDetail.getRoles(), loginResultResponse.isSuccess(), userDetail.getEmail(), userId);
        return success;
    }

    @PostMapping("/logoutAcc")
    public ResponseEntity<String> logout(@RequestHeader ("Authorization") String auth){

        if (auth.startsWith("Bearer ")){
            blacklistToken.addToBlacklist(auth.substring(7));
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout Successfully");

    }

    @PreAuthorize("hasAuthority('Faculty')")
    @GetMapping("/hello")
    public String hello(){
        return ("Hello world");
    }

}
