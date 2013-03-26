/**
 * 
 */
package com.simple.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Group;

@Transactional
public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {
}
