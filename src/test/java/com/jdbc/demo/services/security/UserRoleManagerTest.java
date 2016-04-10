package com.jdbc.demo.services.security;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.jdbc.demo.UserRoleDAO;
import com.jdbc.demo.domain.security.UserRole;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional
public class UserRoleManagerTest {

	
	@Autowired
	private UserRoleDAO userRoleManager;	
	
	@Test
	public void getAllTest() throws Exception {
		UserRole userRole1 = userRoleManager.getById(1);
		UserRole userRole2 = userRoleManager.getById(2);

		Assert.assertTrue(userRoleManager.getAll().contains(userRole1));
		Assert.assertTrue(userRoleManager.getAll().contains(userRole2));
	}
	
	@Test
	public void getByIdTest() throws Exception {
		List<UserRole> userRole = userRoleManager.getAll();

		UserRole foundUserRole = userRoleManager.getById(1);

		Assert.assertTrue(userRole.contains(foundUserRole));

	}
	
	@Test
	public void getByTypeTest() throws Exception {
		List<UserRole> userRole = userRoleManager.getAll();

		UserRole foundUserRole = userRoleManager.getByType("USER");

		Assert.assertTrue(userRole.contains(foundUserRole));

	}
}
