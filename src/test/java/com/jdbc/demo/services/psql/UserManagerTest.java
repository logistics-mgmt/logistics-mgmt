package com.jdbc.demo.services.psql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.jdbc.demo.UserDAO;
import com.jdbc.demo.UserProfileDAO;
import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserProfile;

import utils.TestModelsFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional
public class UserManagerTest {
	
	@Autowired
	private UserDAO userManager;

	@Autowired
	private UserProfileDAO userProfileManager;

	private ArrayList<User> testUsers = new ArrayList<User>();

	@Before
	public void setUp() throws Exception {
		
		List<UserProfile> userProfilelist = userProfileManager.getAll();		
		Set<UserProfile> userProfileSet = new HashSet<UserProfile>(userProfilelist);
		
		testUsers.add(TestModelsFactory.createTestUser1(userProfileSet));
		testUsers.add(TestModelsFactory.createTestUser2(userProfileSet));
	
	}	
	
    @Test
    public void addTest  () throws Exception {
        int sizeBeforeAddition = userManager.getAllUsers().size();
        User user = userManager.add(testUsers.get(0));
        List<User> users = userManager.getAllUsers();
        Assert.assertTrue(users.contains(user));
        Assert.assertEquals(sizeBeforeAddition+1, users.size());
    }
    
    @Test
    public void getAllTest  () throws Exception {
    	User user1 = userManager.add(testUsers.get(0));
    	User user2 = userManager.add(testUsers.get(1));
        List<User> users = userManager.getAllUsers();
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }
    
    @Test
    public void deleteTest  () throws Exception {
    	User user1 = userManager.add(testUsers.get(0));
    	User user2 = userManager.add(testUsers.get(1));
        userManager.deleteBySSO(user1.getSsoId());
        List<User> users = userManager.getAllUsers();
        Assert.assertFalse(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }
    
    @Test
    public void getByIdTest  () throws Exception {
        User user1 = userManager.add(testUsers.get(0));

        User foundUser1 = userManager.getById(user1.getId());
        Assert.assertEquals(user1, foundUser1);
    }
    
    @Test
    public void getBySSOTest  () throws Exception {
        User user1 = userManager.add(testUsers.get(0));

        User foundUser1 = userManager.getBySSO(user1.getSsoId());
        Assert.assertEquals(user1, foundUser1);
    }
    
}
