/**
 * 
 */
package com.simple.security;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.simple.model.Access;
import com.simple.repository.AccessRepository;

/**
 * 
 */
@RepositoryEventHandler(Access.class)
public class AccessRepositoryListener {

   private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   @Autowired
   private AccessRepository accessRepository;

   public AccessRepositoryListener() {
   }

   @HandleBeforeCreate
   @PrePersist
   public void handleBeforeCreate(Access access) {
      if (access.getPassword() != null)
         access.setPassword(passwordEncoder.encode(access.getPassword()));
   }

   @HandleBeforeSave
   @PreUpdate
   public void handleBeforeSave(Access access) {
      if (StringUtils.isBlank(access.getPassword())) {
         // keeps the last password
         Access storedUser = accessRepository.findOne(access.getUsername());
         access.setPassword(storedUser.getPassword());
      }
      else {
         // password change request
         access.setPassword(passwordEncoder.encode(access.getPassword()));
      }
   }
}
