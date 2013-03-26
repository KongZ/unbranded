/**
 * 
 */
package com.simple.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import com.simple.config.ControllerExceptionHandler;
import com.simple.config.Initializer;
import com.simple.config.MethodSecurityConfig;
import com.simple.config.RootConfig;
import com.simple.security.ClientAuthorityService;

/**
 * 
 */
@WebAppConfiguration()
@ContextConfiguration(classes = { Initializer.class, RootConfig.class, MethodSecurityConfig.class, ControllerExceptionHandler.class })
public class BaseControllerTest {
   protected static final String CLIENT_ID = "clientapp";
   protected static final String CLIENT_SECRET = "clientsecret";

   protected MockMvc mvc;

   @Mock
   protected ClientAuthorityService clientAuthorityService;

   @Autowired
   protected WebApplicationContext context;

   @Autowired
   protected FilterChainProxy springSecurityFilterChain;

   @Before
   public void setUp() {
      MockitoAnnotations.initMocks(this);
      mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
      // Client client = new Client(CLIENT_ID, "simple", "read,write", "password,authorization_code,refresh_token
      // ", null);
      // client.setClientSecret(CLIENT_SECRET);
      // Mockito.when(clientAuthorityService.loadClientByClientId(CLIENT_ID)).thenReturn(client);
   }

   protected static String clientAuthorization() {
      return "Basic " + new String(Base64Utils.encode((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
   }

   protected String getAccessToken(String username, String password, boolean expectValid) throws Exception {
      String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
      // @formatter:off
      ResultActions result = mvc.perform(
            post("/oauth/token")
                  .header("Authorization", clientAuthorization())
                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                  .param("username", username)
                  .param("password", password)
                  .param("grant_type", "password")
                  .param("scope", "read write")
                  .param("client_id", CLIENT_ID)
                  .param("client_secret", CLIENT_SECRET));
      if (expectValid) {
         String content = result
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(contentType))
                  .andExpect(jsonPath("$.access_token", is(notNullValue())))
                  .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                  .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                  .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                  .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                  .andReturn().getResponse().getContentAsString();
         return content.substring(17, 53);
      }
      else {
         result
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.error", is("invalid_grant")));
         return null;
      }
      // @formatter:on
   }
}
