package com.store.passmeby.PassMeByApi.handler;

import com.store.passmeby.PassMeByApi.dao.CustomerDao;
import com.store.passmeby.PassMeByApi.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

// 로그인 인증이 성공했

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final CustomerDao usersRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email = extractEmail(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken= jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        usersRepository.findByEmail(email).ifPresent(
                users -> users.updateRefreshToken(refreshToken)
        );
        log.info("로그인 인증에 성공하였습니다. email: {}", email);
        log.info( "AccessToken 을 발급합니다. AccessToken: {}" ,accessToken);
        log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" ,refreshToken);

        // 여기서 리프레시 토큰 저장 필요
        usersRepository.insertRefreshToken(refreshToken, email);

        response.getWriter().write("success");
    }

    private String extractEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}