package guitests;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.Application;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.UserDAO;
import com.jdbc.demo.UserProfileDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserProfile;
import utils.TestModelsFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
//@Transactional
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserGUITestIDERun {
	
	@Autowired
	private UserDAO userManager;

	@Autowired
	private UserProfileDAO userProfileManager;

	private ArrayList<User> testUsers = new ArrayList<User>();	

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;
	
	@Before	
	public void setup() throws Exception {
		
		List<UserProfile> userProfilelist = userProfileManager.getAll();		
		Set<UserProfile> userProfileSet = new HashSet<UserProfile>(userProfilelist);
		
		testUsers.add(TestModelsFactory.createTestUser1(userProfileSet));
		testUsers.add(TestModelsFactory.createTestUser2(userProfileSet));
	
		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/drivers"); 
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"username\"]")));
		element.sendKeys("admin");
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"password\"]")));
		element.sendKeys("admin");
		
		Thread.sleep(2000);
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mainWrapper\"]/div/div/div/form/div[3]/input")));
		element.click();
	}

	@After
	public void cleanup() throws Exception {
		
		driver.close();
		
		for (User user : testUsers) {
			userManager.deleteBySSO(user.getSsoId());
		}
		
	}
	
	@Test 
	public void addDriver() throws Exception {
		
		User testUser = testUsers.get(0);
		
		driver.get("http://localhost:8080/users/");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"firstName\"]")));
		element.sendKeys(testUser.getFirstName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"lastName\"]")));
		element.sendKeys(testUser.getLastName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ssoId\"]")));
		element.sendKeys(testUser.getSsoId());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"password\"]")));
		element.sendKeys(testUser.getPassword());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userProfiles\"]/option[1]")));
		element.click();
		Thread.sleep(3000);	
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user\"]/div[6]/div/input")));
		element.click();
		Thread.sleep(1000);
        //Assert.assertTrue(driverManager.getAll().contains(driverManager.get(driverList.get(1).getId()+1)));	
	}
	
}
