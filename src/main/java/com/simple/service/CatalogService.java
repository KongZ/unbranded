/**
 * 
 */
package com.simple.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.controller.response.CatalogResponse;
import com.simple.controller.response.ContentResponse;
import com.simple.model.Catalog;
import com.simple.model.Content;
import com.simple.repository.CatalogRepository;

/**
 * 
 */
@Service
@Transactional
public class CatalogService {

   private final CatalogRepository catalogRepository;

   @Autowired
   public CatalogService(CatalogRepository catalogRepository) {
      this.catalogRepository = catalogRepository;
   }

   public @NotNull Catalog find(@NotNull Integer catalogId) throws EntityNotFoundException {
      return find(catalogId, false, false);
   }

   public @NotNull Iterable<Catalog> findAll() throws EntityNotFoundException {
      return catalogRepository.findAll();
   }

   public @NotNull Iterable<Catalog> findAll(@NotNull Pageable pagable) throws EntityNotFoundException {
      return catalogRepository.findAll(pagable);
   }

   public @NotNull Catalog find(@NotNull Integer catalogId, boolean includeSubCatalog, boolean includeSubContent)
      throws EntityNotFoundException {
      Catalog catalog = catalogRepository.findOne(catalogId);
      if (catalog == null)
         throw new EntityNotFoundException("'" + catalogId + "' does not exists");
      if (includeSubContent) // lazy load
         catalog.getContents().size();
      if (includeSubCatalog) {
         Iterable<Catalog> subCatalogs = findCatalogByParentId(catalogId);
         for (Catalog subCatalog : subCatalogs) {
            if (includeSubContent)
               subCatalog.getContents().size();
            catalog.getSubCatalos().add(subCatalog);
         }
      }
      return catalog;
   }

   public @NotNull CatalogResponse findAsResponse(@NotNull Integer catalogId, boolean includeSubCatalog, boolean includeContent)
      throws EntityNotFoundException {
      Catalog catalog = catalogRepository.findOne(catalogId);
      if (catalog == null)
         throw new EntityNotFoundException("'" + catalogId + "' does not exists");
      CatalogResponse catalogResponse = new CatalogResponse(catalog);
      if (includeContent) { // lazy load
         catalog.getContents().size();
         for (Content content : catalog.getContents()) {
            catalogResponse.getContents().add(new ContentResponse(content));
         }
      }
      if (includeSubCatalog) {
         Iterable<Catalog> subCatalogs = findCatalogByParentId(catalogId);
         for (Catalog subCatalog : subCatalogs) {
            if (includeContent)
               subCatalog.getContents().size();
            CatalogResponse subCatalogResponse = new CatalogResponse(subCatalog);
            if (includeContent) {
               for (Content content : subCatalog.getContents()) {
                  subCatalogResponse.getContents().add(new ContentResponse(content));
               }
            }
            catalogResponse.getCatalogs().add(subCatalogResponse);
         }
      }
      return catalogResponse;
   }

   public @NotNull Set<CatalogResponse> findRootAsResponse(boolean includeSubCatalog, boolean includeContent)
      throws EntityNotFoundException {
      Set<CatalogResponse> rootSet = new HashSet<CatalogResponse>();
      Iterable<Catalog> catalogs = findRootCatalog();
      for (Catalog catalog : catalogs) {
         CatalogResponse catalogResponse = new CatalogResponse(catalog);
         if (includeContent) { // lazy load
            catalog.getContents().size();
            for (Content content : catalog.getContents()) {
               catalogResponse.getContents().add(new ContentResponse(content));
            }
         }
         if (includeSubCatalog) {
            Iterable<Catalog> subCatalogs = findCatalogByParentId(catalog.getId());
            for (Catalog subCatalog : subCatalogs) {
               if (includeContent)
                  subCatalog.getContents().size();
               CatalogResponse subCatalogResponse = new CatalogResponse(subCatalog);
               if (includeContent) {
                  for (Content content : subCatalog.getContents()) {
                     subCatalogResponse.getContents().add(new ContentResponse(content));
                  }
               }
               catalogResponse.getCatalogs().add(subCatalogResponse);
            }
         }
         rootSet.add(catalogResponse);
      }
      return rootSet;
   }

   public @NotNull Catalog create(@NotNull Catalog catalog) {
      Catalog newCatalog = catalogRepository.save(catalog);
      return newCatalog;
   }

   public @NotNull Catalog update(@NotNull Catalog catalog) {
      return catalogRepository.save(catalog);
   }

   public void delete(@NotNull Integer catalogId) {
      catalogRepository.delete(catalogId);
   }

   public @NotNull Iterable<Catalog> findCatalogByTitle(@NotNull String title) {
      Catalog probe = new Catalog();
      probe.setTitle(title);
      Iterable<Catalog> catalogs = catalogRepository.findAll(Example.of(probe));
      return catalogs;
   }

   public @NotNull Iterable<Catalog> findCatalogByParentId(@NotNull Integer parentId) {
      Catalog probe = new Catalog();
      probe.setParentId(parentId);
      Iterable<Catalog> catalogs = catalogRepository.findAll(Example.of(probe));
      return catalogs;
   }

   public @NotNull Iterable<Catalog> findRootCatalog() {
      Catalog probe = new Catalog();
      probe.setRoot(true);
      Iterable<Catalog> catalogs = catalogRepository.findAll(Example.of(probe));
      return catalogs;
   }

}
