/**
 * 
 */
package com.simple.model;

import java.io.Serializable;

/**
 * 
 */
public class UserGroupId implements Serializable {

   private static final long serialVersionUID = -1067442130654926416L;

   private Integer userId;

   private String groupId;

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

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
      result = prime * result + ((userId == null) ? 0 : userId.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      UserGroupId other = (UserGroupId)obj;
      if (groupId == null) {
         if (other.getGroupId() != null)
            return false;
      }
      else if (!groupId.equals(other.getGroupId()))
         return false;
      if (userId == null) {
         if (other.getUserId() != null)
            return false;
      }
      else if (!userId.equals(other.getUserId()))
         return false;
      return true;
   }

}
