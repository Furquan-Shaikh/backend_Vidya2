package edu.js.project.controller;

import edu.js.project.dto.ResetPasswordRequest;
import edu.js.project.dto.StudentDto;
import edu.js.project.dto.UsersDto;
import edu.js.project.responseStructure.*;
import edu.js.project.service.EmailService;
import edu.js.project.service.impl.OtpService;
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

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/VidyaSarthi")

public class OAuthController {

    @Autowired
    private AuthenticationManager authManager;
    private JwtEncoder jwtEncoder;
    private BlacklistToken blacklistToken;
    private final UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;


    public OAuthController( AuthenticationManager authManager, JwtEncoder jwtEncoder, BlacklistToken blacklistToken, UserService userService) {
        this.authManager = authManager;
        this.jwtEncoder = jwtEncoder;
        this.blacklistToken = blacklistToken;
        this.userService = userService;
    }

    // Temporary storage for pending registrations
    private final Map<String, StudentDto> pendingRegistrations = new ConcurrentHashMap<>();
    // Track pending password resets so we can allow resend only if initiated
    private final Map<String, Instant> pendingPasswordResets = new ConcurrentHashMap<>();

    ResponseStructure<String> rs = new ResponseStructure<>();

    /**
     * Step 1: Initiate signup and send OTP
     */
    @PostMapping("/initiateSignup")
    public ResponseEntity<ResponseStructure<String>> initiateSignup(@RequestBody StudentDto req) {

        // Check if username/email already exists
        if (userService.isUserExists(req.getEmail())) {
            rs.setStatus(HttpStatus.CONFLICT.value());
            rs.setMessage("Username/Email already exists");
            return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
        }

        // Generate OTP
        String otp = otpService.generateOtp(req.getEmail());

        // Store the registration data temporarily
        pendingRegistrations.put(req.getEmail(), req);

        // Send OTP via email
        boolean emailSent = emailService.sendOtpEmail(req.getEmail(), otp, req.getName());

        if (emailSent) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage("OTP has been sent to your email. Please verify to complete registration.");
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            // Clean up if email fails
            otpService.clearOtp(req.getEmail());
            pendingRegistrations.remove(req.getEmail());

            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Failed to send OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Step 2: Verify OTP and complete signup
     */
    @PostMapping("/verifyOtpAndSignup")
    public ResponseEntity<ResponseStructure<String>> verifyOtpAndSignup(@RequestBody OtpVerificationRequest request) {

        // Verify OTP
        boolean isOtpValid = otpService.verifyOtp(request.getEmail(), request.getOtp());

        if (!isOtpValid) {
            rs.setStatus(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("Invalid or expired OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }

        // Retrieve pending registration data
        StudentDto studentDto = pendingRegistrations.get(request.getEmail());

        if (studentDto == null) {
            rs.setStatus(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("Registration data not found. Please start the signup process again.");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }

        // Complete the registration
        boolean isSuccessfullySignedUp = userService.addUserToDB(studentDto);

        if (isSuccessfullySignedUp) {
            // Clean up temporary data
            pendingRegistrations.remove(request.getEmail());

            rs.setStatus(HttpStatus.CREATED.value());
            rs.setMessage(String.format("Account created successfully for %s", studentDto.getName()));
            return new ResponseEntity<>(rs, HttpStatus.CREATED);
        } else {
            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Failed to create account. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Resend OTP endpoint
     */
    @PostMapping("/resendOtp")
    public ResponseEntity<ResponseStructure<String>> resendOtp(@RequestBody ResendOtpRequest request) {

        StudentDto studentDto = pendingRegistrations.get(request.getEmail());

        if (studentDto == null) {
            rs.setStatus(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("No pending registration found. Please start the signup process again.");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }

        // Generate new OTP
        String otp = otpService.generateOtp(request.getEmail());

        // Send OTP via email
        boolean emailSent = emailService.sendOtpEmail(request.getEmail(), otp, studentDto.getName());

        if (emailSent) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage("New OTP has been sent to your email.");
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Failed to send OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    ResponseStructure<String> rs = new ResponseStructure<>();

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

    /**
     * Step 1 (forgot password): Initiate forgot-password and send OTP if user exists
     */
    @PostMapping("/initiateForgotPassword")
    public ResponseEntity<ResponseStructure<String>> initiateForgotPassword(@RequestBody ResendOtpRequest req) {
        String email = req.getEmail();
        if (!userService.isUserExists(email)) {
            rs.setStatus(HttpStatus.NOT_FOUND.value());
            rs.setMessage("User not found with provided email");
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
        }

        // Generate OTP
        String otp = otpService.generateOtp(email);
        // mark pending reset (timestamp only; OtpService enforces expiry)
        pendingPasswordResets.put(email, Instant.now());

        // Get a friendly name to include in email (best-effort)
        String name = "User";
        try {
            UsersDto u = userService.getUserDetail(email);
            if (u.getStudentDto() != null) name = u.getStudentDto().getName();
            else if (u.getNewTeacherDto() != null) name = u.getNewTeacherDto().getName();
            else if (u.getAdminClgDto() != null) name = u.getAdminClgDto().getEmail(); // fallback
        } catch (Exception ignored) {}

        boolean emailSent = emailService.sendOtpEmail(email, otp, name);
        if (emailSent) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage("OTP has been sent to your email. Please verify to reset your password.");
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            // cleanup
            otpService.clearOtp(email);
            pendingPasswordResets.remove(email);
            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Failed to send OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Resend OTP for forgot-password (only when initiateForgotPassword was called)
     */
    @PostMapping("/resendForgotOtp")
    public ResponseEntity<ResponseStructure<String>> resendForgotOtp(@RequestBody ResendOtpRequest request) {
        String email = request.getEmail();
        if (!pendingPasswordResets.containsKey(email)) {
            rs.setStatus(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("No pending password reset found. Please initiate forgot password process first.");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }

        String otp = otpService.generateOtp(email);
        // send email
        String name = "User";
        try {
            UsersDto u = userService.getUserDetail(email);
            if (u.getStudentDto() != null) name = u.getStudentDto().getName();
            else if (u.getNewTeacherDto() != null) name = u.getNewTeacherDto().getName();
        } catch (Exception ignored) {}

        boolean emailSent = emailService.sendOtpEmail(email, otp, name);
        if (emailSent) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage("New OTP has been sent to your email.");
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } else {
            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Failed to send OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Step 2 (forgot password): Verify OTP & reset password
     */
    @PostMapping("/verifyForgotOtpAndReset")
    public ResponseEntity<ResponseStructure<String>> verifyForgotOtpAndReset(@RequestBody ResetPasswordRequest request) {
        String email = request.email();
        String otp = request.otp();
        String newPassword = request.newPassword();

        boolean isOtpValid = otpService.verifyOtp(email, otp);
        if (!isOtpValid) {
            rs.setStatus(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("Invalid or expired OTP. Please try again.");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }

        try {
            boolean updated = userService.resetPassword(email, newPassword);
            if (updated) {
                // cleanup
                otpService.clearOtp(email);
                pendingPasswordResets.remove(email);

                rs.setStatus(HttpStatus.OK.value());
                rs.setMessage("Password has been reset successfully.");
                return new ResponseEntity<>(rs, HttpStatus.OK);
            } else {
                rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                rs.setMessage("Failed to reset password. Please try again.");
                return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RuntimeException e) {
            rs.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rs.setMessage("Error while resetting password: " + e.getMessage());
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
