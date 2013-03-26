/**
 * 
 */
package com.simple.controller.response;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simple.model.Catalog;
import com.simple.model.Content;

/**
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogResponse extends BaseResponse<Catalog> {

   @JsonProperty("id")
   private Integer id;

   @JsonProperty("title")
   private String title;

   @JsonProperty("banner")
   private String banner;

   @JsonProperty("catalogs")
   private Set<CatalogResponse> catalogs = new HashSet<CatalogResponse>();

   @JsonProperty("contents")
   private Set<ContentResponse> contents = new HashSet<ContentResponse>();

   /**
    * 
    */
   public CatalogResponse(Catalog catalog) {
      super(catalog);
   }

   /**
    * 
    */
   public CatalogResponse(Catalog catalog, Collection<Catalog> subCatalogs, Collection<Content> contents) {
      super(catalog);
      if (subCatalogs != null) {
         for (Catalog subCatalog : subCatalogs) {
            this.catalogs.add(new CatalogResponse(subCatalog));
         }
      }
      if (contents != null) {
         for (Content content : contents) {
            this.contents.add(new ContentResponse(content));
         }
      }
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
    * Gets banner.
    * 
    * @return the banner
    */
   public String getBanner() {
      return banner;
   }

   /**
    * Sets banner.
    * 
    * @param banner the banner to set
    */
   public void setBanner(String banner) {
      this.banner = banner;
   }

   /**
    * Gets catalogs.
    * 
    * @return the catalogs
    */
   public Set<CatalogResponse> getCatalogs() {
      return catalogs;
   }

   /**
    * Sets catalogs.
    * 
    * @param catalogs the catalogs to set
    */
   public void setCatalogs(Set<CatalogResponse> catalogs) {
      this.catalogs = catalogs;
   }

   /**
    * Gets contents.
    * 
    * @return the contents
    */
   public Set<ContentResponse> getContents() {
      return contents;
   }

   /**
    * Sets contents.
    * 
    * @param contents the contents to set
    */
   public void setContents(Set<ContentResponse> contents) {
      this.contents = contents;
   }
}
