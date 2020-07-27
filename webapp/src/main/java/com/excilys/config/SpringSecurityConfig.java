package com.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	

	DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		return digestAuthenticationFilter;
	}

	@Bean
	DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
		entryPoint.setRealmName("realm");
		entryPoint.setKey("key");
		return entryPoint;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(userDetailsServiceBean());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilter(digestAuthenticationFilter())
		.exceptionHandling().authenticationEntryPoint(digestEntryPoint())
		.and()
		.authorizeRequests().antMatchers("/add*").authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.formLogin().disable();
		
	}
}
