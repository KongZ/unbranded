/**
 * 
 */
package com.simple.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.controller.request.PageRequest;
import com.simple.controller.response.BaseResponse;
import com.simple.controller.response.ContentResponse;
import com.simple.controller.response.PlayResponse;
import com.simple.model.Access;
import com.simple.model.Content;
import com.simple.model.Group;
import com.simple.model.User;
import com.simple.model.UserGroup;
import com.simple.service.ContentService;
import com.simple.service.UserService;

@RestController
@RequestMapping("/content")
public class ContentController {

   private final ContentService contentService;
   private final UserService userService;

   @Autowired
   public ContentController(ContentService contentService, UserService userService) {
      this.contentService = contentService;
      this.userService = userService;
   }

   @RequestMapping(method = RequestMethod.GET, produces = "application/json")
   public Page<ContentResponse> list(PageRequest pageRequest) {
      return new PageImpl<ContentResponse>(BaseResponse.list(contentService.findAll(pageRequest), ContentResponse.class));
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
   public ContentResponse get(@PathVariable("id") Integer contentId) {
      Content content = contentService.find(contentId);
      return new ContentResponse(content);
   }

   @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
   @Transactional
   public ContentResponse create(@RequestBody Content content) throws IOException {
      return new ContentResponse(contentService.create(content));
   }

   @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
   @Transactional
   public ContentResponse update(@PathVariable("id") Integer contentId, @RequestBody Content content) throws IOException {
      content.setId(contentId);
      return new ContentResponse(contentService.update(content));
   }

   @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable("id") Integer contentId) {
      contentService.delete(contentId);
   }

   @RequestMapping(method = RequestMethod.POST, value = "/{id}/play")
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
                  return playResponse;
               }
            }
         }
      }
      return playResponse;
   }

   @RequestMapping(method = RequestMethod.GET, value = "/search", produces = "application/json")
   public Page<ContentResponse> search(@RequestParam("q") String query, PageRequest pageRequest) {
      return new PageImpl<ContentResponse>(
         BaseResponse.list(contentService.searchContentByTitle(query, pageRequest), ContentResponse.class));
   }

}
