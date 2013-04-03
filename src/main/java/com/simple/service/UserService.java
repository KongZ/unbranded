/**
 * 
 */
package com.simple.service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.controller.request.UserAndPassword;
import com.simple.model.Access;
import com.simple.model.User;
import com.simple.repository.AccessRepository;
import com.simple.repository.UserRepository;

/**
 * 
 */
@Service
@Transactional
public class UserService {

   private static Logger logger = LoggerFactory.getLogger(UserService.class);

   private final UserRepository userRepository;
   private final AccessRepository accessRepository;

   @Autowired
   public UserService(UserRepository userRepository, AccessRepository accessRepository) {
      this.userRepository = userRepository;
      this.accessRepository = accessRepository;
   }

   public @NotNull User find(@NotNull Integer userId) throws EntityNotFoundException {
      return find(userId, false, false);
   }

   public @NotNull Iterable<User> findAll() throws EntityNotFoundException {
      return userRepository.findAll();
   }

   public @NotNull Page<User> findAll(@NotNull Pageable pagable) throws EntityNotFoundException {
      return userRepository.findAll(pagable);
   }

   public @NotNull User find(@NotNull Integer userId, boolean includeGroup, boolean includeAccess) throws EntityNotFoundException {
      User user = userRepository.findOne(userId);
      if (user == null)
         throw new EntityNotFoundException("'" + userId + "' does not exists");
      if (includeGroup) // lazy load
         user.getGroups().size();
      if (includeAccess) // lazy load
         user.getAccesses().size();
      return user;
   }

   public @NotNull User create(@NotNull UserAndPassword userAndPassword) {
      // check username
      Access access = accessRepository.findOne(userAndPassword.getUsername());
      if (access != null)
         throw new EntityExistsException("Username already exists");

      // create user
      User user = new User();
      BeanUtils.copyProperties(userAndPassword, user);
      user.setStatus(User.STATUS_ENABLED);
      user = create(user);
      // create authorize
      access = new Access();
      access.setUsername(userAndPassword.getUsername());
      access.setPassword(userAndPassword.getPassword());
      access.setUser(user);
      access.setEnabled(true);
      accessRepository.save(access);
      return user;
   }

   public @NotNull User create(@NotNull User user) {
      return userRepository.save(user);
   }

   public @NotNull User update(@NotNull User user) {
      return userRepository.save(user);
   }

   public void delete(@NotNull Integer userId) {
      userRepository.delete(userId);
   }

   public void updatePassword(@NotNull String userName, @NotNull String password) {
      // check username
      Access access = accessRepository.findOne(userName);
      if (access == null)
         throw new EntityNotFoundException("Invalid username");
      access.setPassword(password);
      accessRepository.save(access);
   }

   public void deleteUsername(@NotNull String userName) {
      accessRepository.delete(userName);
   }

   public @Null Access findAccess(@Null String username) {
      Access access = accessRepository.findOne(username);
      if (access.getUser().getId() == null) {
         logger.warn("Orphan username found '{}'", username);
         return null;
      }
      return access;
   }

   public @Null String findUsername(@Null Principal principal) {
      if (principal instanceof OAuth2Authentication) {
         OAuth2Authentication authentication = (OAuth2Authentication)principal;
         if (!authentication.isClientOnly()) {
            return principal.getName();
         }
      }
      return null;
   }

   public @NotNull Set<User> findUser(@NotNull String username) {
      Set<User> userSet = new HashSet<User>();
      Access access = accessRepository.findOne(username);
      Set<Access> accesses = access.getUser().getAccesses();
      for (Access ac : accesses) {
         userSet.add(ac.getUser());
      }
      return userSet;
   }

}
