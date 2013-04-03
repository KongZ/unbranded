/**
 * 
 */
package com.simple.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAndPassword {

   @JsonProperty("id")
   private Integer id;

   @JsonProperty("first_name")
   private String firstName;

   @JsonProperty("last_name")
   private String lastName;

   @JsonProperty("status")
   private Integer status;

   // -- Access --//
   @JsonProperty("username")
   private String username;

   @JsonProperty("password")
   private String password;

   /**
    * Gets id.
    * 
    * @return the id
    */
   public Integer getId() {
      return id;
   }

   /**
    * Sets id.
    * 
    * @param id the id to set
    */
   public void setId(Integer id) {
      this.id = id;
   }

   /**
    * Gets firstName.
    * 
    * @return the firstName
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Sets firstName.
    * 
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * Gets lastName.
    * 
    * @return the lastName
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Sets lastName.
    * 
    * @param lastName the lastName to set
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   /**
    * Gets status.
    * 
    * @return the status
    */
   public Integer getStatus() {
      return status;
   }

   /**
    * Sets status.
    * 
    * @param status the status to set
    */
   public void setStatus(Integer status) {
      this.status = status;
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
