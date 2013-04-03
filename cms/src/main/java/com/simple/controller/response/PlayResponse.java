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
public class PlayResponse {

   @JsonProperty("id")
   private String id;

   @JsonProperty("content_id")
   private Integer contentId;

   @JsonProperty("url")
   private String url;

   @JsonProperty("comments")
   private String comments;

   /**
    * 
    */
   public PlayResponse() {
   }


   /**
    * Gets id.
    * 
    * @return the id
    */
    public String getId() {
      return id;
   }

   /**
    * Sets id.
    * 
    * @param id the id to set
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Gets contentId.
    * 
    * @return the contentId
    */
   public Integer getContentId() {
      return contentId;
   }

   /**
    * Sets contentId.
    * 
    * @param contentId the contentId to set
    */
   public void setContentId(Integer contentId) {
      this.contentId = contentId;
   }

   /**
    * Gets url.
    * 
    * @return the url
    */
   public String getUrl() {
      return url;
   }

   /**
    * Sets url.
    * 
    * @param url the url to set
    */
   public void setUrl(String url) {
      this.url = url;
   }

   /**
    * Gets comments.
    * 
    * @return the comments
    */
   public String getComments() {
      return comments;
   }

   /**
    * Sets comments.
    * 
    * @param comments the comments to set
    */
   public void setComments(String comments) {
      this.comments = comments;
   }

}
