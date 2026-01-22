package com.store.passmeby.PassMeByApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.passmeby.PassMeByApi.dao.CustomerDao;
import com.store.passmeby.PassMeByApi.filter.JsonUsernamePasswordAuthenticationFilter;
import com.store.passmeby.PassMeByApi.filter.JwtAuthenticationProcessingFilter;
import com.store.passmeby.PassMeByApi.handler.LoginFailureHandler;
import com.store.passmeby.PassMeByApi.handler.LoginSuccessJWTProvideHandler;
import com.store.passmeby.PassMeByApi.service.JwtService;
import com.store.passmeby.PassMeByApi.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // JWT(Json Web Token) 토큰으로 인증 진행
    // Spring Security + JWT
    // JWT의 장점 (Stateless)
    // 세션(stateful)의 경우 사용자가 많아질수록 서버가 처리해야하는 작업의 양이 커지게 되는 반면
    // JWT는 서버가 아닌 클라이언트 측에서 관리를 한다. 브라우저의 로컬 스토리지 또는 쿠키에 저장되고,
    // 이 토큰을 HTTP 요청과 함께 서버 측에 보내면 서버는 이 토큰에 대한 위변조 검사, 만료 여부 등의 유효성 검사 진행

    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final CustomerDao usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoderConfig passwordEncoder;

    // 스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해)
//	@Bean
//	public WebSecurityCustomizer configure() {
//		return (web -> web.ignoring()
//				.requestMatchers(toH2Console())
//				.requestMatchers("/h2-console/**")
//		);
//	}

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http	.cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)      // rest api 환경에서는 session기반의 인증과 다르므로
                .httpBasic(AbstractHttpConfigurer::disable) // 서버에 인증정보를 보관하지 않고, 권한 요청시 인증정보(Oauth2, Jwt토큰) 요청을 포함하므로 불필요
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers( "/", "/index.html", "/favicon.ico", "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer/join/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/join/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 폼 로그인은 현재 사용하지 않음
//				.formLogin(formLogin -> formLogin
//						.loginPage("/login")
//						.defaultSuccessUrl("/home"))
                .logout((logout) -> logout
                        .invalidateHttpSession(true))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http
                .addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class) // 추가 : 커스터마이징 된 필터를 SpringSecurityFilterChain에 등록
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());

        return daoAuthenticationProvider;
    }

//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {//2 - AuthenticationManager 등록
        DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
        return new ProviderManager(provider);
    }

    // 로그인 인증 성공했을 때
    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
        return new LoginSuccessJWTProvideHandler(jwtService, usersRepository);
    }

    // 로그인 인증 실패했을 때
    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jsonUsernamePasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, usersRepository);

        return jsonUsernamePasswordLoginFilter;
    }
}