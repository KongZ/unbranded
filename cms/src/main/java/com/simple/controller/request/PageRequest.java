/**
 * 
 */
package com.simple.controller.request;

/**
 */
public class PageRequest extends org.springframework.data.domain.PageRequest {
   private static final long serialVersionUID = 1151810164099323139L;

   /**
    * @param page
    * @param size
    */
   public PageRequest() {
      super(0, Integer.MAX_VALUE);
   }

   /**
    * @param page
    * @param size
    */
   public PageRequest(int page, int size) {
      super(page, size);
   }

}
