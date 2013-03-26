/**
 * 
 */
package com.simple.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Content;
import com.simple.repository.ContentRepository;

/**
 * 
 */
@Service
@Transactional
public class ContentService {

   private final ContentRepository contentRepository;

   @Autowired
   public ContentService(ContentRepository contentRepository) {
      this.contentRepository = contentRepository;
   }

   public @NotNull Content find(@NotNull Integer contentId) throws EntityNotFoundException {
      return find(contentId, false, false);
   }

   public @NotNull Iterable<Content> findAll() throws EntityNotFoundException {
      return contentRepository.findAll();
   }

   public @NotNull Iterable<Content> findAll(@NotNull Pageable pagable) throws EntityNotFoundException {
      return contentRepository.findAll(pagable);
   }

   public @NotNull Content find(@NotNull Integer contentId, boolean includeCatalog, boolean includeGroup) throws EntityNotFoundException {
      Content content = contentRepository.findOne(contentId);
      if (content == null)
         throw new EntityNotFoundException("'" + contentId + "' does not exists");
      if (includeCatalog) // lazy load
         content.getCatalogs().size();
      if (includeGroup) // lazy load
         content.getGroups().size();
      return content;
   }

   public @NotNull Content create(@NotNull Content content) {
      Content newContent = contentRepository.save(content);
      return newContent;
   }

   public @NotNull Content update(@NotNull Content content) {
      return contentRepository.save(content);
   }

   public void delete(@NotNull Integer contentId) {
      contentRepository.delete(contentId);
   }

   public @NotNull Iterable<Content> findContentByTitle(@NotNull String title) {
      Content probe = new Content();
      probe.setTitle(title);
      Iterable<Content> contents = contentRepository.findAll(Example.of(probe));
      return contents;
   }

   @Transactional(readOnly = true)
   public @NotNull Iterable<Content> searchContentByTitle(@NotNull String title, @NotNull Pageable pagable) {
      Content probe = new Content();
      probe.setTitle(title);
      Iterable<Content> contents = contentRepository.findAll(Example.of(probe, ExampleMatcher.matching().withMatcher("title",
               ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())), pagable);
      return contents;
   }

}
