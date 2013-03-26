/**
 * 
 */
package com.simple.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "content")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {

   public static Integer STATUS_DISABLED = 0;
   public static Integer STATUS_ENABLED = 1;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String title;

   private String synopsis;

   private String genres;

   private Integer status;

   private String poster;

   private String url;

   @Column(name = "imdb_id")
   private String imdbId;

   private String comments;

   @ManyToMany(mappedBy = "contents", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   private Set<Catalog> catalogs = new HashSet<Catalog>();

   @ManyToMany(mappedBy = "contents", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   private Set<Group> groups = new HashSet<Group>();

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
    * Gets synopsis.
    * 
    * @return the synopsis
    */
   public String getSynopsis() {
      return synopsis;
   }

   /**
    * Sets synopsis.
    * 
    * @param synopsis the synopsis to set
    */
   public void setSynopsis(String synopsis) {
      this.synopsis = synopsis;
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
    * Gets status.
    * 
    * @return the status
    */
   public Integer getStatus() {
      return status;
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

   /**
    * Sets status.
    * 
    * @param status the status to set
    */
   public void setStatus(Integer status) {
      this.status = status;
   }

   /**
    * Gets catalogs.
    * 
    * @return the catalogs
    */
   public Set<Catalog> getCatalogs() {
      return catalogs;
   }

   /**
    * Sets catalogs.
    * 
    * @param catalogs the catalogs to set
    */
   public void setCatalogs(Set<Catalog> catalogs) {
      this.catalogs = catalogs;
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
    * Gets imdbId.
    * 
    * @return the imdbId
    */
   public String getImdbId() {
      return imdbId;
   }

   /**
    * Sets imdbId.
    * 
    * @param imdbId the imdbId to set
    */
   public void setImdbId(String imdbId) {
      this.imdbId = imdbId;
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

   /**
    * Gets groups.
    * 
    * @return the groups
    */
   public Set<Group> getGroups() {
      return groups;
   }

   /**
    * Sets groups.
    * 
    * @param groups the groups to set
    */
   public void setGroups(Set<Group> groups) {
      this.groups = groups;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      Content other = (Content)obj;
      if (id == null) {
         if (other.getId() != null)
            return false;
      }
      else if (!id.equals(other.getId()))
         return false;
      return true;
   }

}
