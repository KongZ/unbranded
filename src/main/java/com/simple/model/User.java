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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

   public static final Integer STATUS_DISABLED = 0;
   public static final Integer STATUS_ENABLED = 1;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "first_name")
   @JsonProperty("first_name")
   private String firstName;

   @Column(name = "last_name")
   @JsonProperty("last_name")
   private String lastName;

   @Column(name = "status")
   private Integer status;

   // @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   // @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
   // inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
   // --
   // @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   // private Set<Group> groups = new HashSet<Group>();

   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
   private Set<Access> accesses = new HashSet<Access>();

   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<UserGroup> groups;

   public Integer getId() {
      return id;
   }

   @JsonIgnore
   public void setId(Integer id) {
      this.id = id;
   }

   /**
    * Gets firstName.
    * 
    * @return the firstName
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Sets firstName.
    * 
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * Gets lastName.
    * 
    * @return the lastName
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Sets lastName.
    * 
    * @param lastName the lastName to set
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
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
   @JsonIgnore
   public void setStatus(Integer status) {
      this.status = status;
   }

   /**
    * Gets groups.
    * 
    * @return the groups
    */
   public Set<UserGroup> getGroups() {
      return groups;
   }

   /**
    * Sets groups.
    * 
    * @param groups the groups to set
    */
   public void setGroups(Set<UserGroup> groups) {
      this.groups = groups;
   }

   /**
    * Gets accesses.
    * 
    * @return the accesses
    */
   public Set<Access> getAccesses() {
      return accesses;
   }

   /**
    * Sets accesses.
    * 
    * @param accesses the accesses to set
    */
   public void setAccesses(Set<Access> accesses) {
      this.accesses = accesses;
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
      User other = (User)obj;
      if (id == null) {
         if (other.getId() != null)
            return false;
      }
      else if (!id.equals(other.getId()))
         return false;
      return true;
   }

}
