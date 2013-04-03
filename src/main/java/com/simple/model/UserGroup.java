/**
 * 
 */
package com.simple.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "users_groups")
@IdClass(UserGroupId.class)
public class UserGroup {

   @Id
   @Column(name = "user_id", insertable = false, updatable = false)
   private Integer userId;

   @Id
   @Column(name = "group_id", insertable = false, updatable = false)
   private String groupId;

   @Column(name = "expiry_date")
   private Timestamp expiryDate;

   @ManyToOne
   @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
   private User user;

   @ManyToOne
   @PrimaryKeyJoinColumn(name = "group_id", referencedColumnName = "id")
   private Group group;

   /**
    * Gets userId.
    * 
    * @return the userId
    */
   public Integer getUserId() {
      return userId;
   }

   /**
    * Sets userId.
    * 
    * @param userId the userId to set
    */
   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   /**
    * Gets groupId.
    * 
    * @return the groupId
    */
   public String getGroupId() {
      return groupId;
   }

   /**
    * Sets groupId.
    * 
    * @param groupId the groupId to set
    */
   public void setGroupId(String groupId) {
      this.groupId = groupId;
   }

   /**
    * Gets expiryDate.
    * 
    * @return the expiryDate
    */
   public Timestamp getExpiryDate() {
      return expiryDate;
   }

   /**
    * Sets expiryDate.
    * 
    * @param expiryDate the expiryDate to set
    */
   public void setExpiryDate(Timestamp expiryDate) {
      this.expiryDate = expiryDate;
   }

   /**
    * Gets user.
    * 
    * @return the user
    */
   public User getUser() {
      return user;
   }

   /**
    * Sets user.
    * 
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }

   /**
    * Gets group.
    * 
    * @return the group
    */
   public Group getGroup() {
      return group;
   }

   /**
    * Sets group.
    * 
    * @param group the group to set
    */
   public void setGroup(Group group) {
      this.group = group;
   }

}
