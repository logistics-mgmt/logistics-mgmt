package com.jdbc.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jdbc.demo.domain.security.UserRole;
import com.jdbc.demo.services.security.UserRoleService;
@Component
public class RoleConverter implements Converter<Object, UserRole>{
 
    @Autowired
    UserRoleService userRoleService;
 
    public UserRole convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        UserRole profile= userRoleService.getById(id);
        return profile;
    }
     
}
