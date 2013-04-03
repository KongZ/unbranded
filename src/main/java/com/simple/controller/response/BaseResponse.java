/**
 * 
 */
package com.simple.controller.response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 
 */
public class BaseResponse<M> {

   private static Logger logger = LoggerFactory.getLogger(BaseResponse.class);
   
   /**
    * 
    */
   public BaseResponse() {
   }

   /**
    * 
    */
   public BaseResponse(M m) {
      BeanUtils.copyProperties(m, this);
   }

   @SuppressWarnings("unchecked")
   protected Class<M> getModelClass() {
      return (Class<M>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }
   
   public static <R, M> Iterable<R> iterator(Iterable<M> source, Class<R> targetClass) {
      try {
         Constructor<?> constructor = targetClass.getConstructors()[0];
         return CollectionUtils.collect(source, new Transformer<M, R>() {
            @SuppressWarnings("unchecked")
            @Override
            public R transform(M input) {
               try {
                  return (R)constructor.newInstance(input);
               }
               catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                  logger.error(e.getMessage(), e);
               }
               return null;
            }
         });
      }
      catch (SecurityException e) {
         logger.error(e.getMessage(), e);
      }
      return Collections.<R> emptyList();
   }

   public static <R, M> List<R> list(Iterable<M> source, Class<R> targetClass) {
      try {
         Constructor<?> constructor = targetClass.getConstructors()[0];
         return new ArrayList<R>(CollectionUtils.collect(source, new Transformer<M, R>() {
            @SuppressWarnings("unchecked")
            @Override
            public R transform(M input) {
               try {
                  return (R)constructor.newInstance(input);
               }
               catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                  logger.error(e.getMessage(), e);
               }
               return null;
            }
         }));
      }
      catch (SecurityException e) {
         logger.error(e.getMessage(), e);
      }
      return Collections.<R> emptyList();
   }

}
