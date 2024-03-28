//package com.feuji.employeeservice.serviceimpl;
//
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.feuji.employeeservice.entity.RefreshToken;
//import com.feuji.employeeservice.repository.RefreshTokenRepository;
//import com.feuji.employeeservice.repository.UserInfoRepository;
//
//@Service
//public class RefreshTokenService {
//
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;
//    @Autowired
//    private UserInfoRepository userInfoRepository;
//
//    public RefreshToken createRefreshToken(String email) {
//        RefreshToken refreshToken = RefreshToken.builder()
//                .entity(userInfoRepository.findByUserEmail(email).get())
//                .token(UUID.randomUUID().toString())
//                .expiryDate(Instant.now().plusSeconds(604800)) // 7 days (7 * 24 * 60 * 60 seconds)
//                .build();
//        return refreshTokenRepository.save(refreshToken);
//    }
//
//
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
//        }
//        return token;
//    }
//
//}
