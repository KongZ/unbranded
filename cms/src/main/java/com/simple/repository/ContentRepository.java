/**
 * 
 */
package com.simple.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Content;

@Transactional
public interface ContentRepository extends PagingAndSortingRepository<Content, Integer>, QueryByExampleExecutor<Content> {

}
