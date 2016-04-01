package com.jdbc.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jdbc.demo.UserProfileService;
import com.jdbc.demo.domain.security.UserProfile;
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{
 
    @Autowired
    UserProfileService userProfileService;
 
    public UserProfile convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        UserProfile profile= userProfileService.getById(id);
        System.out.println("Profile : "+profile);
        return profile;
    }
     
}
