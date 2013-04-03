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
public class Error {

   @JsonProperty("error")
   private String error;

   @JsonProperty("error_description")
   private String description;

   @JsonProperty("error_uri")
   private String uri;

   /**
    * Gets error.
    * 
    * @return the error
    */
   public String getError() {
      return error;
   }

   /**
    * Sets error.
    * 
    * @param error the error to set
    */
   public void setError(String error) {
      this.error = error;
   }

   /**
    * Gets description.
    * 
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets description.
    * 
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Gets uri.
    * 
    * @return the uri
    */
   public String getUri() {
      return uri;
   }

   /**
    * Sets uri.
    * 
    * @param uri the uri to set
    */
   public void setUri(String uri) {
      this.uri = uri;
   }

}
