/**
 * 
 */
package com.simple.model;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends BaseClientDetails {
   private static final long serialVersionUID = 3564225561442241802L;

   public Client() {
      super();
   }

   public Client(ClientDetails prototype) {
      super(prototype);
   }

   public Client(String clientId, String resourceIds, String scopes, String grantTypes, String authorities) {
      super(clientId, resourceIds, scopes, grantTypes, authorities);
   }

   public Client(String clientId, String resourceIds, String scopes, String grantTypes, String authorities, String redirectUris) {
      super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
   }

}
