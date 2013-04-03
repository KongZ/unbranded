/**
 * 
 */
package com.simple.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.controller.response.Error;

/**
 * 
 */
@ControllerAdvice
public class ControllerExceptionHandler implements OAuth2ExceptionRenderer {
   private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

   @Autowired
   private ObjectMapper objectMapper;

   @ResponseStatus(HttpStatus.CONFLICT) // 409
   @ExceptionHandler(DataIntegrityViolationException.class)
   public void handleConflict() {
      // Nothing to do
   }

   @ExceptionHandler(value = Exception.class)
   public ResponseEntity<Error> defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
      logger.error(exception.getMessage(), exception);
      // If the exception is annotated with @ResponseStatus rethrow it and let
      // the framework handle it
      if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
         throw exception;
      // Otherwise setup and send the user to a default error-view.
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      Error error = new Error();
      error.setError(exception.getClass().getSimpleName());
      error.setDescription(exception.getMessage());
      return new ResponseEntity<Error>(error, headers, HttpStatus.FORBIDDEN);
   }

   public void handleHttpEntityResponse(HttpEntity<?> responseEntity, ServletWebRequest webRequest) throws Exception {
      if (responseEntity == null) {
         return;
      }
      HttpOutputMessage outputMessage = createHttpOutputMessage(webRequest);
      if (responseEntity instanceof ResponseEntity && outputMessage instanceof ServerHttpResponse) {
         ((ServerHttpResponse)outputMessage).setStatusCode(((ResponseEntity<?>)responseEntity).getStatusCode());
      }
      HttpHeaders entityHeaders = responseEntity.getHeaders();
      if (!entityHeaders.isEmpty()) {
         outputMessage.getHeaders().putAll(entityHeaders);
      }
      Object body = responseEntity.getBody();
      if (body instanceof OAuth2Exception) {
         outputMessage.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
         OAuth2Exception exception = (OAuth2Exception)body;
         Error error = new Error();
         error.setError(exception.getOAuth2ErrorCode());
         error.setDescription(exception.getMessage());
         outputMessage.getBody().write(objectMapper.writeValueAsBytes(error));
      }
   }

   @SuppressWarnings("unused")
   private HttpInputMessage createHttpInputMessage(NativeWebRequest webRequest) throws Exception {
      HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
      return new ServletServerHttpRequest(servletRequest);
   }

   private HttpOutputMessage createHttpOutputMessage(NativeWebRequest webRequest) throws Exception {
      HttpServletResponse servletResponse = (HttpServletResponse)webRequest.getNativeResponse();
      return new ServletServerHttpResponse(servletResponse);
   }
}
