/**
 * 
 */
package com.simple.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.security.AccessRepositoryListener;

@Entity
@Table(name = "access")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AccessRepositoryListener.class)
public class Access {

   @Id
   private String username;

   @JsonIgnore
   private String password;

   private boolean enabled;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   private User user;

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

   /**
    * Gets enabled.
    * 
    * @return the enabled
    */
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * Sets enabled.
    * 
    * @param enabled the enabled to set
    */
   @JsonIgnore
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * Gets user.
    * 
    * @return the user
    */
   public User getUser() {
      return user;
   }

   /**
    * Sets user.
    * 
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((username == null) ? 0 : username.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      Access other = (Access)obj;
      if (username == null) {
         if (other.getUsername() != null)
            return false;
      }
      else if (!username.equals(other.getUsername()))
         return false;
      return true;
   }

}
