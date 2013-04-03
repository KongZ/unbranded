/**
 * 
 */
package com.simple.security;

import javax.sql.DataSource;

import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

public class ClientAuthorityService extends JdbcClientDetailsService {

   /**
    * @param dataSource
    */
   public ClientAuthorityService(DataSource dataSource) {
      super(dataSource);
   }

   @Override
   public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
      ClientDetails clientDetails = super.loadClientByClientId(clientId);
      return clientDetails;
//      Client clientDetails = new Client(clientId, "simple", "read,write", "password,authorization_code,refresh_token", "", "");
//      clientDetails.setClientSecret("xxx");
//      return clientDetails;
   }

   public ClientDetails loadClient(String clientId, String clientSecret) {
      ClientDetails client = loadClientByClientId(clientId);
      if (client.getClientSecret().equals(clientSecret))
         return client;
      throw new UnauthorizedClientException("Invalid Client");
   }
}
