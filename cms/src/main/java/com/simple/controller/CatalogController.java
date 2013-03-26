/**
 * 
 */
package com.simple.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.controller.request.PageRequest;
import com.simple.controller.response.BaseResponse;
import com.simple.controller.response.CatalogResponse;
import com.simple.model.Catalog;
import com.simple.service.CatalogService;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

   private final CatalogService catalogService;

   @Autowired
   public CatalogController(CatalogService catalogService) {
      this.catalogService = catalogService;
   }

   @RequestMapping(method = RequestMethod.GET, produces = "application/json")
   public Page<CatalogResponse> list(PageRequest pageRequest) {
      return new PageImpl<CatalogResponse>(BaseResponse.list(catalogService.findAll(pageRequest), CatalogResponse.class));
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
   public CatalogResponse get(@PathVariable("id") Integer catalogId,
                              @RequestParam(name = "subCatalog", defaultValue = "false") boolean includeSubCatalog,
                              @RequestParam(name = "content", defaultValue = "false") boolean includeContent) {
      CatalogResponse catalogResponse = catalogService.findAsResponse(catalogId, includeSubCatalog, includeContent);
      return catalogResponse;
   }

   @RequestMapping(method = RequestMethod.GET, value = "/root", produces = "application/json")
   public Iterable<CatalogResponse> get(@RequestParam(name = "subCatalog", defaultValue = "false") boolean includeSubCatalog,
                                        @RequestParam(name = "content", defaultValue = "false") boolean includeContent) {
      Set<CatalogResponse> rootSet = catalogService.findRootAsResponse(includeSubCatalog, includeContent);
      return rootSet;
   }

   @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
   @Transactional
   public CatalogResponse create(@RequestBody Catalog catalog) throws IOException {
      return new CatalogResponse(catalogService.create(catalog));
   }

   @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
   @Transactional
   public CatalogResponse update(@PathVariable("id") Integer catalogId, @RequestBody Catalog catalog) throws IOException {
      catalog.setId(catalogId);
      return new CatalogResponse(catalogService.update(catalog));
   }

   @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable("id") Integer catalogId) {
      catalogService.delete(catalogId);
   }

}
