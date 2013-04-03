/**
 * 
 */
package com.simple.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.simple.model.User;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CatalogControllerTest extends BaseControllerTest {
   private static Logger logger = LoggerFactory.getLogger(CatalogControllerTest.class);

   @InjectMocks
   private UserController controller;

   @Test
   public void catalogEndpoint() throws Exception {
      // @formatter:off
      String newName = RandomStringUtils.randomAlphanumeric(5);
      User user = new User();
      user.setId(3);
      user.setFirstName(newName);
      logger.info("Test all catalogs");
      // no child
      mvc.perform(get("/catalog")
               .header("Authorization", clientAuthorization()))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()", greaterThan(0)));  
      // @formatter:on
   }

   @Test
   public void catalogRootEndpoint() throws Exception {
      // @formatter:off
      String newName = RandomStringUtils.randomAlphanumeric(5);
      User user = new User();
      user.setId(3);
      user.setFirstName(newName);
      logger.info("Test all catalogs");
      // no child
      mvc.perform(get("/catalog/root")
            .header("Authorization", clientAuthorization()))
      .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThan(0)));  
      logger.info("Test catalog with children");
      mvc.perform(get("/catalog/root?subCatalog=true")
            .header("Authorization", clientAuthorization()))
            .andDo(MockMvcResultHandlers.print()) 
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title", is("Root")))  
            .andExpect(jsonPath("$[0].catalogs.length()", greaterThan(0)));
      // @formatter:on
   }

   @Test
   public void catalogIdEndpoint() throws Exception {
      // @formatter:off
      logger.info("Test catalog path");
      // no child
      mvc.perform(get("/catalog/2")
            .header("Authorization", clientAuthorization()))
      .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Drama")))  
            .andExpect(jsonPath("$.catalogs.length()", is(0)))
            .andExpect(jsonPath("$.contents.length()", is(0)));
      logger.info("Test catalog path with children");
      mvc.perform(get("/catalog/2?subCatalog=true&content=true")
            .header("Authorization", clientAuthorization()))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Action")))  
            .andExpect(jsonPath("$.catalogs.length()", is(0)))
            .andExpect(jsonPath("$.contents.length()", greaterThan(0)));
      // @formatter:on
   }

}
