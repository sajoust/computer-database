package com.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	

//	@Bean
//	DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
//		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
//		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
//		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
//		return digestAuthenticationFilter;
//	}
//
//	@Bean
//	DigestAuthenticationEntryPoint digestEntryPoint() {
//		DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
//		entryPoint.setRealmName("realm");
//		entryPoint.setKey("key");
//		return entryPoint;
//	}
	
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();

    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;

    }
	
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(authenticationProvider());

    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login", "/resource/**").permitAll()
                .and()
          .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/home")
                .failureUrl("/loginFailed")
                .and()
                .authorizeRequests().antMatchers("/home").hasAnyRole("ADMIN","USER")
                .and()
                .logout().logoutUrl("/doLogout").logoutSuccessUrl("/logout").permitAll()
                .and()
                .authorizeRequests().antMatchers("/addComputer").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/editComputer").hasRole("ADMIN")
                .and()
                .logout()
                .and()
                .csrf().disable();
        
 

    }
}
