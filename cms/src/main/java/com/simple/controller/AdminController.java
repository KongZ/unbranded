/**
 * 
 */
package com.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

   private final TokenStore tokenStore;
   private final ConsumerTokenServices tokenServices;

   @Autowired
   public AdminController(TokenStore tokenStore, ConsumerTokenServices tokenServices) {
      this.tokenStore = tokenStore;
      this.tokenServices = tokenServices;
   }

   @RequestMapping(method = RequestMethod.GET, value = "/token/{access_token}", produces = "application/json")
   public OAuth2AccessToken getToken(@PathVariable("access_token") String accessToken) {
      OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
      return oAuth2AccessToken;
   }

   @RequestMapping(method = RequestMethod.DELETE, value = "/token/{access_token}", produces = "application/json")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteToken(@PathVariable("access_token") String accessToken) {
      tokenServices.revokeToken(accessToken);
   }
}
