package com.simple.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initialize the Spring.
 * 
 * 
 */
@Order(1)
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   /**
    * Specify {@link org.springframework.context.annotation.Configuration @Configuration} and/or
    * {@link org.springframework.stereotype.Component @Component} classes to be provided to the
    * {@linkplain #createRootApplicationContext() root application context}.
    * 
    * @return the configuration classes for the root application context, or {@code null} if creation and
    *         registration of a root context is not desired
    */
   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] { RootConfig.class, SecurityConfig.class };
   }

   /**
    * Specify {@link org.springframework.context.annotation.Configuration @Configuration} and/or
    * {@link org.springframework.stereotype.Component @Component} classes to be provided to the
    * {@linkplain #createServletApplicationContext() dispatcher servlet application context}.
    * 
    * @return the configuration classes for the dispatcher servlet application context or {@code null} if all
    *         configuration is specified through root config classes.
    */
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { WebAppConfig.class };
   }

   /**
    * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash; for example {@code "/"},
    * {@code "/app"}, etc.
    * 
    * @see #registerDispatcherServlet(ServletContext)
    */
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }

   @Override
   public void onStartup(ServletContext servletContext) throws ServletException {
      super.onStartup(servletContext);
      registerProxyFilter(servletContext, "springSecurityFilterChain");
   }

   private void registerProxyFilter(ServletContext servletContext, String name) {
      DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
      filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
      servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
   }
}
