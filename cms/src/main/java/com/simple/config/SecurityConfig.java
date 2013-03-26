/**
 * 
 */
package com.simple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.simple.security.UserAuthorityService;

/**
 * Enable Spring security.
 */
// @Order(-1)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   @Autowired
   private UserAuthorityService userDetailsService;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
      // auth.jdbcAuthentication().dataSource(dataSource);// .passwordEncoder(encoder);
   }

   @Override
   public void configure(WebSecurity web) throws Exception {
      // super.configure(web);
      // ignore security on the following resources
      web.ignoring().antMatchers("/resources/**", "/webjars/**", "/images/**");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      // super.configure(http);
      // http.userDetailsService(customUserDetailsService)//
      // .authorizeRequests() //
      // .antMatchers("/login.jsp").permitAll() // allow everyone to login
      // .and().exceptionHandling().accessDeniedPage("/login.jsp?authorization_error=true") // login error
      // .and().csrf() // CSRF protection back into this endpoint
      // .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable() // admin page
      // .logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp") // logout page
      // .and().formLogin().loginProcessingUrl("/token") // login page
      // .failureUrl("/login.jsp?authentication_error=true").loginPage("/login.jsp");
   }

   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

}
