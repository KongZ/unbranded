/**
 * 
 */
package com.simple.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.model.Access;
import com.simple.model.UserGroup;
import com.simple.repository.AccessRepository;

/**
 * Provide user authority.
 * 
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserAuthorityService implements UserDetailsService {

   @Autowired
   private AccessRepository accessRepository;

   public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
      Access access = accessRepository.findOne(login);
      if (access == null)
         throw new UsernameNotFoundException("Username not found");
      com.simple.model.User user = access.getUser();
      if (user == null)
         throw new UsernameNotFoundException("User not found");
      boolean accountNonExpired = true;
      boolean credentialsNonExpired = true;
      boolean accountNonLocked = true;
      return new User(access.getUsername(), access.getPassword(), access.isEnabled(), accountNonExpired, credentialsNonExpired,
         accountNonLocked, getGrantedAuthorities(user.getGroups()));
   }

   public static List<GrantedAuthority> getGrantedAuthorities(Collection<UserGroup> groups) {
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      for (UserGroup group : groups) {
         // authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(group.getGroup()));
         authorities.addAll(AuthorityUtils.createAuthorityList(group.getGroup().getId()));
      }
      return authorities;
   }
}
