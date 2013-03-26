/**
 * 
 */
package com.simple.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.simple.controller.response.PlayResponse;
import com.simple.model.Access;
import com.simple.model.Content;
import com.simple.model.Group;
import com.simple.model.User;
import com.simple.model.UserGroup;
import com.simple.service.ContentService;
import com.simple.service.UserService;

@RestController
@RequestMapping("/play")
public class PlayController {

   private final ContentService contentService;
   private final UserService userService;

   @Autowired
   public PlayController(ContentService contentService, UserService userService) {
      this.contentService = contentService;
      this.userService = userService;
   }

   @RequestMapping(method = RequestMethod.POST, value = "/{id}")
   public PlayResponse play(@PathVariable("id") Integer contentId, Access access) throws IOException {
      User user = access.getUser();
      user = userService.find(user.getId(), true, false); // refresh user from db
      Set<UserGroup> userGroups = user.getGroups();
      Content content = contentService.find(contentId, false, true);
      Set<Group> contentGroups = content.getGroups();

      PlayResponse playResponse = new PlayResponse();
      Timestamp now = new Timestamp(System.currentTimeMillis());
      for (UserGroup userGroup : userGroups) {
         if (userGroup.getExpiryDate().after(now)) { // not expired yet
            for (Group group : contentGroups) {
               if (group.equals(userGroup.getGroup())) {
                  // return URL only when user has any group inside content group and not expires
                  playResponse.setUrl(content.getUrl());
                  playResponse.setComments(content.getComments());
                  return playResponse;
               }
            }
         }
      }
      return playResponse;
   }
}
