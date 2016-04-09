package com.jdbc.demo.services.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserRole;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
    @Autowired
    private UserService userService;
     

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(UserRole userRole: user.getUserRoles()){
            System.out.println("UserRole : "+userRole);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getType()));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }
     
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.getByLogin(login);
        System.out.println("User : "+user);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), 
            		true, true, true, true, getGrantedAuthorities(user));
    }
}