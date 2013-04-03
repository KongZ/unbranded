/**
 * 
 */
package com.simple.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "groups")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private String id;

   private String name;

   // @ManyToMany(cascade = CascadeType.REMOVE)
   // @JoinTable(name = "USERS_GROUPS", joinColumns = @JoinColumn(name = "GROUP_ID"), inverseJoinColumns =
   // @JoinColumn(name = "USER_ID"))
   // private Set<User> users = new HashSet<User>();

   @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<UserGroup> users;

   @ManyToMany(cascade = CascadeType.REMOVE)
   @JoinTable(name = "contents_groups", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
   private Set<Content> contents = new HashSet<Content>();

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   /**
    * Gets name.
    * 
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Sets name.
    * 
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets users.
    * 
    * @return the users
    */
   public Set<UserGroup> getUsers() {
      return users;
   }

   /**
    * Sets users.
    * 
    * @param users the users to set
    */
   public void setUsers(Set<UserGroup> users) {
      this.users = users;
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
      Group other = (Group)obj;
      if (id == null) {
         if (other.getId() != null)
            return false;
      }
      else if (!id.equals(other.getId()))
         return false;
      return true;
   }

}
