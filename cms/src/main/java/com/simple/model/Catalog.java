/**
 * 
 */
package com.simple.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "catalog")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Catalog {

   public static Integer STATUS_DISABLED = 0;
   public static Integer STATUS_ENABLED = 1;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String title;

   private String banner;

   private Integer status;

   @JsonProperty(value = "parent_id")
   @Column(name = "parent_id")
   private Integer parentId;

   @JsonProperty(value = "is_root")
   @Column(name = "is_root")
   private boolean root;

   @ManyToMany(cascade = CascadeType.REMOVE)
   @JoinTable(name = "catalogs_contents", joinColumns = @JoinColumn(name = "catalog_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
   private Set<Content> contents = new HashSet<Content>();

   // -- net persis -- //
   @Transient
   private Set<Catalog> subCatalogs = new HashSet<Catalog>();

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
    * Gets status.
    * 
    * @return the status
    */
   public Integer getStatus() {
      return status;
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
    * Gets parentId.
    * 
    * @return the parentId
    */
   public Integer getParentId() {
      return parentId;
   }

   /**
    * Sets parentId.
    * 
    * @param parentId the parentId to set
    */
   public void setParentId(Integer parentId) {
      this.parentId = parentId;
   }

   /**
    * Gets contents.
    * 
    * @return the contents
    */
   public Set<Content> getContents() {
      return contents;
   }

   /**
    * Sets contents.
    * 
    * @param contents the contents to set
    */
   public void setContents(Set<Content> contents) {
      this.contents = contents;
   }

   /**
    * Gets subCatalos.
    * 
    * @return the subCatalos
    */
   public Set<Catalog> getSubCatalos() {
      return subCatalogs;
   }

   /**
    * Sets subCatalos.
    * 
    * @param subCatalos the subCatalos to set
    */
   public void setSubCatalos(Set<Catalog> subCatalos) {
      this.subCatalogs = subCatalos;
   }

   /**
    * Gets root.
    * 
    * @return the root
    */
   public boolean isRoot() {
      return root;
   }

   /**
    * Sets root.
    * 
    * @param root the root to set
    */
   public void setRoot(boolean root) {
      this.root = root;
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
      Catalog other = (Catalog)obj;
      if (id == null) {
         if (other.getId() != null)
            return false;
      }
      else if (!id.equals(other.getId()))
         return false;
      return true;
   }

}
