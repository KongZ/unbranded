/**
 * 
 */
package com.simple.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Access;

@Transactional
public interface AccessRepository extends CrudRepository<Access, String> {

}
