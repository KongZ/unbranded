/**
 * 
 */
package com.simple.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.simple.security.ClientAuthorityService;
import com.simple.security.UserAuthorityService;

/**
 * Configure OAuth 2 server.
 */
@Configuration
public class OAuth2ServerConfig {

   public static final String RESOURCE_ID = "simple";

   /**
    * Configure Resource server.
    */
   @Configuration
   @EnableResourceServer
   protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

      @Override
      public void configure(ResourceServerSecurityConfigurer resources) {
         resources.resourceId(RESOURCE_ID).stateless(false);
      }

      @Override
      public void configure(HttpSecurity http) throws Exception {
         // @formatter:off
         http
         .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/users").hasRole("ADMIN")
            .antMatchers("/user/**").authenticated()
            .antMatchers("/access/**").permitAll()
            .antMatchers("/catalog/**").permitAll()
            .antMatchers("/content/**").permitAll()
            .antMatchers("/play/*").authenticated();
         
//         http
//            // Since we want the protected resources to be accessible in the UI as well we need 
//            // session creation to be allowed (it's disabled by default in 2.0.6)
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//         .and()
//            .requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**","/me")
//         .and()
//            .authorizeRequests()
//               .antMatchers("/me").access("#oauth2.hasScope('read')")               
//               .antMatchers("/shop").access("#oauth2.hasScope('read') and #oauth2.isOAuth() and hasAuthority('ROLE_USER')")                                        
//               .antMatchers("/photos").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")                                        
//               .antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
//               .antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")              
//               .antMatchers("/photos/**").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
//               .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
//                  .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
//               .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
//                  .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
//               .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
//                  .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
         // @formatter:on
      }
   }

   /**
    * Configure Authorization server.
    */
   @Configuration
   @EnableAuthorizationServer
   protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

      @Autowired
      @Qualifier("authenticationManagerBean")
      private AuthenticationManager authenticationManager;

      @Autowired
      private UserAuthorityService userDetailsService;

      @Autowired
      private DataSource dataSource;

      @Autowired
      private ControllerExceptionHandler exceptionHandler;

      @Autowired
      private TokenStore tokenStore;

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
         // @formatter:off
         endpoints.tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
         // @formatter:on
      }

      @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
         // @formatter:off
         clients.withClientDetails(clientServices());
//         clients.inMemory()
//         .withClient("clientapp")
//         .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//         .authorities("USER")
//         .scopes("read", "write")
//         .resourceIds(RESOURCE_ID)
//         .secret("123456");
//
//                .withClient("tonr")
//                  .resourceIds(RESOURCE_ID)
//                  .authorizedGrantTypes("authorization_code", "implicit")
//                  .authorities("ROLE_CLIENT")
//                  .scopes("read", "write")
//                  .secret("secret")
//               .and()
//                .withClient("tonr-with-redirect")
//                   .resourceIds(RESOURCE_ID)
//                   .authorizedGrantTypes("authorization_code", "implicit")
//                   .authorities("ROLE_CLIENT")
//                   .scopes("read", "write")
//                   .secret("secret")
//                   .redirectUris(tonrRedirectUri)
//               .and()
//                .withClient("my-client-with-registered-redirect")
//                   .resourceIds(RESOURCE_ID)
//                   .authorizedGrantTypes("authorization_code", "client_credentials")
//                   .authorities("ROLE_CLIENT")
//                   .scopes("read", "trust")
//                   .redirectUris("http://anywhere?key=value")
//               .and()
//                .withClient("my-trusted-client")
//                   .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                   .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                   .scopes("read", "write", "trust")
//                   .accessTokenValiditySeconds(60)
//               .and()
//                .withClient("my-trusted-client-with-secret")
//                   .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                   .authorities("ROLE_USER", "ROLE_TRUSTED_CLIENT")
//                   .scopes("read", "write", "trust")
//                   .resourceIds(RESOURCE_ID)
//                   .secret("somesecret")
//               .and()
//                .withClient("my-less-trusted-client")
//                   .authorizedGrantTypes("authorization_code", "implicit")
//                   .authorities("ROLE_CLIENT")
//                   .scopes("read", "write", "trust")
//               .and()
//                .withClient("my-less-trusted-autoapprove-client")
//                   .authorizedGrantTypes("implicit")
//                   .authorities("ROLE_CLIENT")
//                   .scopes("read", "write", "trust")
//                    .autoApprove(true);
         // @formatter:on
      }

      // @Bean
      // public OAuth2ExceptionRenderer exceptionHandler() {
      // return new DefaultOAuth2ExceptionRenderer();
      // }

      @Override
      public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
         // oauthServer.realm(RESOURCE_ID + "/client");
         // oauthServer.realm("oauth2/client").accessDeniedHandler(oauthAccessDeniedHandler())
         // .authenticationEntryPoint(clientAuthenticationEntryPoint());
         // This allows you to replace default filter for Basic authentication and customize error responses
         // oauthServer.addTokenEndpointAuthenticationFilter(
         // new BasicAuthenticationFilter(authenticationManager, clientAuthenticationEntryPoint()));
      }

      // @Bean
      // public OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint() {
      // OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
      // entryPoint.setRealmName(OAuth2ServerConfig.RESOURCE_ID);
      // entryPoint.setExceptionRenderer(exceptionHandler);
      // return entryPoint;
      // }

      @Bean
      public AuthenticationEntryPoint clientAuthenticationEntryPoint() {
         OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
         entryPoint.setRealmName("oauth2/client");
         entryPoint.setTypeName("Basic");
         entryPoint.setExceptionRenderer(exceptionHandler);
         return entryPoint;
      }

      @Bean
      public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
         OAuth2AccessDeniedHandler entryPoint = new OAuth2AccessDeniedHandler();
         entryPoint.setExceptionRenderer(exceptionHandler);
         return entryPoint;
      }

      @Bean
      public TokenStore tokenStore() {
         return new JdbcTokenStore(dataSource);
      }

      @Bean
      @Primary
      public DefaultTokenServices tokenServices() {
         DefaultTokenServices tokenServices = new DefaultTokenServices();
         tokenServices.setTokenStore(tokenStore());
         tokenServices.setSupportRefreshToken(true);
         tokenServices.setAccessTokenValiditySeconds(300);
         tokenServices.setRefreshTokenValiditySeconds(30000);
         return tokenServices;
      }

      @Bean
      @Primary
      public ClientDetailsService clientServices() {
         ClientDetailsService clientServices = new ClientAuthorityService(dataSource);
         return clientServices;
      }
   }

}
