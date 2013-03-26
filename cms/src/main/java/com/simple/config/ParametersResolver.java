/**
 * 
 */
package com.simple.config;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.simple.controller.request.PageRequest;
import com.simple.model.Access;
import com.simple.security.ClientAuthorityService;
import com.simple.service.UserService;

/**
 * 
 */
public class ParametersResolver implements HandlerMethodArgumentResolver {

   private static Logger logger = LoggerFactory.getLogger(ParametersResolver.class);

   @Autowired
   private ClientAuthorityService clientService;

   @Autowired
   private UserService userService;

   @Override
   public boolean supportsParameter(MethodParameter parameter) {
      return parameter.getParameterType().isAssignableFrom(ClientDetails.class)
               || parameter.getParameterType().isAssignableFrom(Access.class)
               || parameter.getParameterType().isAssignableFrom(PageRequest.class);
   }

   @Override
   public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                 WebDataBinderFactory binderFactory)
      throws Exception {
      Class<?> modelType = parameter.getParameterType();
      if (modelType.isAssignableFrom(ClientDetails.class)) {
         return getClient(parameter, webRequest);
      }
      else if (modelType.isAssignableFrom(Access.class)) {
         return getAccess(parameter, webRequest);
      }
      else if (modelType.isAssignableFrom(PageRequest.class)) {
         return getPageRequest(parameter, webRequest);
      }
      return null;
   }

   /**
    * Gets the client from the HTTP Authorization header.
    *
    * @param parameter the parameter
    * @param request the request
    * @return the client
    * @throws APIException the API exception
    */
   protected ClientDetails getClient(MethodParameter parameter, NativeWebRequest request) throws NoSuchClientException {
      HttpServletRequest servletRequest = (HttpServletRequest)request.getNativeRequest();
      String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
      if (StringUtils.isBlank(authorization)) {
         logger.trace("No Authorization header found on {}", servletRequest.getPathInfo()); //$NON-NLS-1$
         throw new NoSuchClientException("No Authorization header found");
      }
      String[] basicBase64 = authorization.trim().split(" "); //$NON-NLS-1$
      if (basicBase64.length != 2 || !"Basic".equals(basicBase64[0])) { //$NON-NLS-1$
         logger.debug("Invalid Authorization method"); //$NON-NLS-1$
         throw new NoSuchClientException("Invalid Authorization method");
      }
      else {
         String clientIdAndSecret = org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.decodeBase64(basicBase64[1]));
         String[] idAndSecret = clientIdAndSecret.split(":"); //$NON-NLS-1$
         if (idAndSecret.length != 2) {
            logger.debug("Invalid Authorization header {}", clientIdAndSecret); //$NON-NLS-1$
            throw new NoSuchClientException("Invalid Authorization header");
         }
         else {
            return clientService.loadClient(idAndSecret[0], idAndSecret[1]);
         }
      }
   }

   /**
    * Gets the client from the HTTP Authorization header.
    *
    * @param parameter the parameter
    * @param request the request
    * @return the client
    * @throws APIException the API exception
    */
   @Transactional(readOnly = true)
   protected Access getAccess(MethodParameter parameter, NativeWebRequest request) throws NoSuchClientException {
      Principal principal = request.getUserPrincipal();
      if (principal instanceof OAuth2Authentication) {
         OAuth2Authentication authentication = (OAuth2Authentication)principal;
         if (!authentication.isClientOnly() && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userService.findAccess(username);
         }
      }
      throw new ClientRegistrationException(
         "The request is not approved by the user. Please login and grant the permission before continue");
   }

   /**
    * Gets the client from the HTTP Authorization header.
    *
    * @param parameter the parameter
    * @param request the request
    * @return the client
    * @throws APIException the API exception
    */
   protected PageRequest getPageRequest(MethodParameter parameter, NativeWebRequest request) throws NoSuchClientException {
      HttpServletRequest servletRequest = (HttpServletRequest)request.getNativeRequest();
      try {
         Integer page = Integer.valueOf(servletRequest.getParameter("page"));
         Integer size = Integer.valueOf(servletRequest.getParameter("size"));
         PageRequest pageRequest = new PageRequest(page, size);
         return pageRequest;
      }
      catch (NumberFormatException e) {
         PageRequest pageRequest = new PageRequest();
         return pageRequest;
      }
   }
}
