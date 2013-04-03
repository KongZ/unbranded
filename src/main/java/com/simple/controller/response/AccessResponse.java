/**
 * 
 */
package com.simple.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessResponse {

   @JsonProperty("username")
   private String username;

   /**
    * 
    */
   public AccessResponse() {
   }

   /**
    * Gets username.
    * 
    * @return the username
    */
   public String getUsername() {
      return username;
   }

   /**
    * Sets username.
    * 
    * @param username the username to set
    */
   public void setUsername(String username) {
      this.username = username;
   }

}
