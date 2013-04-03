/**
 * 
 */
package com.simple.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.User;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

}
