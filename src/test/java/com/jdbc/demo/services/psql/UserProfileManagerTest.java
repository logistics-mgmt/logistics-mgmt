package com.jdbc.demo.services.psql;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.jdbc.demo.UserProfileDAO;
import com.jdbc.demo.domain.security.UserProfile;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional
public class UserProfileManagerTest {

	
	@Autowired
	private UserProfileDAO userProfileManager;	
	
	@Test
	public void getAllTest() throws Exception {
		UserProfile userProfile1 = userProfileManager.getById(1);
		UserProfile userProfile2 = userProfileManager.getById(2);

		Assert.assertTrue(userProfileManager.getAll().contains(userProfile1));
		Assert.assertTrue(userProfileManager.getAll().contains(userProfile2));
	}
	
	@Test
	public void getByIdTest() throws Exception {
		List<UserProfile> userProfile = userProfileManager.getAll();

		UserProfile foundUserProfile = userProfileManager.getById(1);

		Assert.assertTrue(userProfile.contains(foundUserProfile));

	}
	
	@Test
	public void getByTypeTest() throws Exception {
		List<UserProfile> userProfile = userProfileManager.getAll();

		UserProfile foundUserProfile = userProfileManager.getByType("USER");

		Assert.assertTrue(userProfile.contains(foundUserProfile));

	}
}
