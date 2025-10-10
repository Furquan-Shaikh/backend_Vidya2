package edu.js.project.service.impl;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    
    private final Map<String, OtpDetails> otpStorage = new ConcurrentHashMap<>();
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;
    
    public String generateOtp(String email) {
        // Generate 6-digit OTP
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        String otpString = String.valueOf(otp);
        
        // Store OTP with expiry time
        OtpDetails otpDetails = new OtpDetails(otpString, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otpStorage.put(email, otpDetails);
        
        return otpString;
    }
    
    public boolean verifyOtp(String email, String otp) {
        OtpDetails otpDetails = otpStorage.get(email);
        
        if (otpDetails == null) {
            return false;
        }
        
        // Check if OTP is expired
        if (LocalDateTime.now().isAfter(otpDetails.expiryTime)) {
            otpStorage.remove(email);
            return false;
        }
        
        // Verify OTP
        boolean isValid = otpDetails.otp.equals(otp);
        
        if (isValid) {
            otpStorage.remove(email); // Remove OTP after successful verification
        }
        
        return isValid;
    }
    
    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
    
    // Clean up expired OTPs periodically (optional)
    public void cleanupExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        otpStorage.entrySet().removeIf(entry -> 
            now.isAfter(entry.getValue().expiryTime)
        );
    }
    
    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime expiryTime;
        
        public OtpDetails(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}