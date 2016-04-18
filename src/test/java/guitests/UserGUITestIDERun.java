package guitests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.Application;
import com.jdbc.demo.UserRoleDAO;
import com.jdbc.demo.domain.security.User;
import com.jdbc.demo.domain.security.UserRole;

import utils.FirefoxPath;
import utils.TestModelsFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserGUITestIDERun {

	@Autowired
	private UserRoleDAO userRoleManager;

	private ArrayList<User> testUsers = new ArrayList<User>();
	
	private static FirefoxPath getPath;

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File(getPath.firefoxPath()));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Test
	public void AaddUser() throws Exception {

		List<UserRole> userRolelist = userRoleManager.getAll();
		Set<UserRole> userRoleSet = new HashSet<UserRole>(userRolelist);

		testUsers.add(TestModelsFactory.createTestUser1(userRoleSet));
		

		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/users/");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"username\"]")));
		element.sendKeys("admin");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"password\"]")));
		element.sendKeys("admin");

		Thread.sleep(2000);

		element = (new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@id=\"mainWrapper\"]/div/div/div/form/div[3]/input")));
		element.click();
		
		User testUser = testUsers.get(0);

		driver.get("http://localhost:8080/users/");
		Thread.sleep(3000);

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
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"login\"]")));
		element.sendKeys(testUser.getLogin());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"password\"]")));
		element.sendKeys(testUser.getPassword());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"userRoles\"]/option[1]")));
		element.click();
		Thread.sleep(3000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user\"]/div[6]/div/input")));
		element.click();
		Thread.sleep(1000);
		
	}

	@Test
	public void BupdateUser() throws Exception {
		List<UserRole> userRolelist = userRoleManager.getAll();
		Set<UserRole> userRoleSet = new HashSet<UserRole>(userRolelist);
		
		testUsers.add(TestModelsFactory.createTestUser2(userRoleSet));
		
		User testUser = testUsers.get(0);
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/users/edit/admin1']")));
		element.click();
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"firstName\"]")));
		element.clear();
		element.sendKeys(testUser.getFirstName());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"lastName\"]")));
		element.clear();
		element.sendKeys(testUser.getLastName());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"password\"]")));
		element.clear();
		
		element.sendKeys(testUser.getPassword());

		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user\"]/div[3]/div[4]/div/input")));
		element.click();
		Thread.sleep(1000);
		
	}
	
	@Test
	public void CdaleteUser() throws Exception {
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/users/delete/admin1']")));
		element.click();
		Thread.sleep(1000);
		driver.close();
	}
}



