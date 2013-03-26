/**
 * 
 */
package com.simple.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simple.model.Content;

/**
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentResponse extends BaseResponse<Content> {

   @JsonProperty("id")
   private Integer id;

   @JsonProperty("title")
   private String title;

   @JsonProperty("sysnopsis")
   private String sysnopsis;

   @JsonProperty("genres")
   private String genres;

   @JsonProperty("poster")
   private String poster;

   /**
    * 
    */
   public ContentResponse(Content content) {
      super(content);
   }

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
    * Gets title.
    * 
    * @return the title
    */
   public String getTitle() {
      return title;
   }

   /**
    * Sets title.
    * 
    * @param title the title to set
    */
   public void setTitle(String title) {
      this.title = title;
   }

   /**
    * Gets sysnopsis.
    * 
    * @return the sysnopsis
    */
   public String getSysnopsis() {
      return sysnopsis;
   }

   /**
    * Sets sysnopsis.
    * 
    * @param sysnopsis the sysnopsis to set
    */
   public void setSysnopsis(String sysnopsis) {
      this.sysnopsis = sysnopsis;
   }

   /**
    * Gets genres.
    * 
    * @return the genres
    */
   public String getGenres() {
      return genres;
   }

   /**
    * Sets genres.
    * 
    * @param genres the genres to set
    */
   public void setGenres(String genres) {
      this.genres = genres;
   }

   /**
    * Gets poster.
    * 
    * @return the poster
    */
   public String getPoster() {
      return poster;
   }

   /**
    * Sets poster.
    * 
    * @param poster the poster to set
    */
   public void setPoster(String poster) {
      this.poster = poster;
   }

}
