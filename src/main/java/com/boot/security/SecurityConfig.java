package com.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
//	@Autowired
//    private MaxSessionsAuthenticationFailureHandler maxSessionsAuthenticationFailureHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		http
//			.authorizeRequests()
//			.requestMatchers(HttpMethod.GET, "/", "/register", "/css/**", "/js/**", "/image/**").permitAll()
//            .requestMatchers(HttpMethod.POST, "/register").permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//			.and()
//			.authenticationProvider(authenticationProvider);
//			
		http.authorizeHttpRequests(
	            auth -> auth.requestMatchers(HttpMethod.GET, "/", "/register", "/css/**", "/js/**", "/image/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/register").permitAll()
	            .requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll() 
	            .anyRequest().authenticated()
	           )
	            .formLogin(formLogin -> formLogin
	                    .loginPage("/login")
	                    .defaultSuccessUrl("/chat", true)
	                    .failureUrl("/login?parameter=error")
	                    .permitAll()
	            )
	            .logout(logout -> logout.logoutUrl("/signout").logoutSuccessUrl("/?parameter=logout").permitAll())
	            .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//				.maximumSessions(1)
//				.maxSessionsPreventsLogin(true)
//				.expiredUrl("/login?parameter=expired")
//			    .sessionRegistry(sessionRegistry()))
	           .authenticationProvider(authenticationProvider);
		return http.build();
	}
	
//	@Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//	
//	@Bean 
//	HttpSessionEventPublisher sessionEventPublisher() {
//	    return new HttpSessionEventPublisher();
//	}

}
