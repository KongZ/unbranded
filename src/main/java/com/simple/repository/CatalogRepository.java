/**
 * 
 */
package com.simple.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Catalog;

@Transactional
public interface CatalogRepository extends PagingAndSortingRepository<Catalog, Integer>, QueryByExampleExecutor<Catalog> {

}
