/**
 * 
 */
package com.simple.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.controller.request.PageRequest;
import com.simple.controller.response.BaseResponse;
import com.simple.controller.response.UserResponse;
import com.simple.model.User;
import com.simple.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

   private UserService userService;

   @Autowired
   public UserController(UserService userService) {
      this.userService = userService;
   }

   @RequestMapping(method = RequestMethod.GET, produces = "application/json")
   public ResponseEntity<Page<UserResponse>> list(PageRequest pageRequest) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept-Patch", "application/json-patch+json");
      return new ResponseEntity<Page<UserResponse>>(
         new PageImpl<UserResponse>(BaseResponse.list(userService.findAll(pageRequest), UserResponse.class)), headers, HttpStatus.OK);
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
   public UserResponse get(@PathVariable("id") Integer userId) {
      User user = userService.find(userId);
      return new UserResponse(user);
   }

   @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
   @Transactional
   public UserResponse update(@PathVariable("id") Integer userId, @RequestBody User newUser) throws IOException {
      User oldUser = userService.find(userId);
      newUser.setId(userId);
      newUser.setStatus(oldUser.getStatus());
      return new UserResponse(userService.update(newUser));
   }

   @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable("id") Integer userId) {
      userService.delete(userId);
   }
}
