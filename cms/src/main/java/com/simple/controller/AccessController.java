/**
 * 
 */
package com.simple.controller;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.controller.request.AccessRequest;
import com.simple.controller.request.UserAndPassword;
import com.simple.controller.response.BaseResponse;
import com.simple.controller.response.UserResponse;
import com.simple.model.Access;
import com.simple.model.Client;
import com.simple.model.User;
import com.simple.service.UserService;

@RestController
@RequestMapping("/access")
public class AccessController {

   private final UserService userService;

   @Autowired
   public AccessController(UserService userService) {
      this.userService = userService;
   }

   @RequestMapping(method = RequestMethod.GET, produces = "application/json")
   public Iterable<UserResponse> get(Access access) throws IOException {
      Set<User> users = userService.findUser(access.getUsername());
      return BaseResponse.iterator(users, UserResponse.class);
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{username}", produces = "application/json")
   public UserResponse get(@PathVariable("username") String username, Access access) {
      Access foundAccess = userService.findAccess(username);
      if (foundAccess == null)
         throw new EntityNotFoundException("Invalid username");
      if (foundAccess.getUser().getId().equals(access.getUser().getId()))
         return new UserResponse(access.getUser());
      throw new EntityNotFoundException("Invalid username");
   }

   @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
   public UserResponse create(Client client, @RequestBody UserAndPassword userAndPassword) {
      userAndPassword.setId(null); // remove id
      userAndPassword.setStatus(User.STATUS_ENABLED); // default enable
      User user = userService.create(userAndPassword);
      return new UserResponse(user);
   }

   @RequestMapping(method = RequestMethod.PUT, value = "/{username}", consumes = "application/json")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void update(@PathVariable("username") String username, @RequestBody AccessRequest accessRequest, Access access)
      throws IOException {
      if (username.equals(access.getUsername()))
         userService.updatePassword(username, accessRequest.getPassword());
      else
         throw new EntityNotFoundException("Invalid username");
   }

   @RequestMapping(method = RequestMethod.DELETE, value = "/{username}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable("username") String username, Access access) {
      if (username.equals(access.getUsername()))
         userService.deleteUsername(username);
      else
         throw new EntityNotFoundException("Invalid username");
   }

}
