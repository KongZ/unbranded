/**
 * 
 */
package com.simple.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessRequest {

   @JsonProperty(value = "username")
   private String username;

   @JsonProperty(value = "password")
   private String password;

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

   /**
    * Gets password.
    * 
    * @return the password
    */
   public String getPassword() {
      return password;
   }

   /**
    * Sets password.
    * 
    * @param password the password to set
    */
   public void setPassword(String password) {
      this.password = password;
   }

}
