package com.store.passmeby.PassMeByApi.filter;

import com.store.passmeby.PassMeByApi.dao.CustomerDao;
import com.store.passmeby.PassMeByApi.dao.vo.Users;
import com.store.passmeby.PassMeByApi.security.details.UserDetailsImpl;
import com.store.passmeby.PassMeByApi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// "/login"으로 들어오는 요청에 대해서는 작동하지 않도록 하기 위함

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomerDao usersRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        // 프리플라이트는 무조건 통과
        if ("OPTIONS".equalsIgnoreCase(method)) return true;

        // 회원가입/아이디중복/이메일중복 등 공개 API는 JWT 검사 제외
        if (path.startsWith("/api/v1/customer")) return true;

        return false;
    }

    /**
     * 1. 리프레시 토큰이 오는 경우 -> 유효하면 AccessToken 재발급 후, 필터 진행 x, 바로 ㄷ튕김
     * 2. 리프레시 토큰은 없고, AccessToken만 존재하는 경우 -> 유저정보 저장 후 필터 계속 진행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Optional<String> token = jwtService.extractAccessToken(request);

        String NO_CHECK_URL = "/login";
        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
            //안해주면 아래로 내려가서 계속 필터를 진행하게됨
        }
        String refreshToken = jwtService
                .extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null); //2


        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);//3
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);//4
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request).filter(jwtService::isTokenValid).ifPresent(
                accessToken -> jwtService.extractEmail(accessToken).ifPresent(
                        email -> usersRepository.findByEmail(email).ifPresent(
                                users -> saveAuthentication(users)
                        )
                )
        );

        filterChain.doFilter(request,response);
    }


    private void saveAuthentication(Users users) {
        UserDetailsImpl userDetails = new UserDetailsImpl(users);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));


        SecurityContext context = SecurityContextHolder.createEmptyContext();//5
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        usersRepository.findByRefreshToken(refreshToken).ifPresent(
                users -> jwtService.sendAccessToken(response, jwtService.createAccessToken(users.getEmail()))
        );


    }

}
