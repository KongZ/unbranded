/**
 * 
 */
package com.simple.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.model.User;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends BaseControllerTest {
   private static Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

   @Autowired
   private ObjectMapper objectMapper;

   @InjectMocks
   private UserController controller;

   @Test
   public void usersEndpointAuthorized() throws Exception {
      // @formatter:off
      String newName = RandomStringUtils.randomAlphanumeric(5);
      User user = new User();
      user.setId(3);
      user.setFirstName(newName);
      logger.info("Test updating user");
      mvc.perform(put("/user/1")
               .header("Authorization", "Bearer " + getAccessToken("simple", "simple", true))
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(user))
               .param("first_name", newName))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))  
            .andExpect(jsonPath("$.first_name", is(newName)));
      logger.info("Test getting user");
      mvc.perform(get("/user/1")
               .header("Authorization", "Bearer " + getAccessToken("simple", "simple", true))
               .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)));      
      // @formatter:on
   }

   @Test
   public void usersEndpointAccessDenied() throws Exception {
      // @formatter:off
      logger.info("Test listing user");
      mvc.perform(get("/user")
             .header("Authorization", "Bearer " + getAccessToken("unknown", "password", false))
             .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().is(401));

      logger.info("Test updating user");
      mvc.perform(get("/user/3")
            .header("Authorization", "Bearer " + getAccessToken("unknown", "password", false))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(401));
      // @formatter:on
   }

}
