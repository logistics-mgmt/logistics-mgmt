package guitests;

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
import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.Application;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Driver;
import utils.TestModelsFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class DriverGUITestIDERun {
	
	@Autowired
	private DriverDAO driverManager;

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Driver> driverList = new ArrayList<Driver>();
	private ArrayList<Address> addressList = new ArrayList<Address>();
	
	WebElement element;
	FirefoxBinary binary = new FirefoxBinary(new File(firefoxPath()));
	FirefoxProfile profile = new FirefoxProfile();
	WebDriver driver;

	@Before
	public void setup() throws Exception {

		addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		Driver driver1 = driverManager.add(TestModelsFactory.createTestDriver1(addressList.get(0)));
		driverList.add(driver1);
		Driver driver2 = driverManager.add(TestModelsFactory.createTestDriver2(addressList.get(0)));
		driverList.add(driver2);  

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
		
		for (Driver driver : driverList) {
			driverManager.delete(driver.getPESEL());
		}

		addressManager.delete(addressList.get(0));

	}

	@Test 
	public void addDriver() throws Exception {
		
		Driver testDriver = TestModelsFactory.createTestDriver3(addressList.get(0));
		testDriver.setId(driverList.get(1).getId()+1);
		driverList.add(testDriver);
		
		driver.get("http://localhost:8080/drivers");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"firstName\"]")));
		element.sendKeys(testDriver.getFirstName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"lastName\"]")));
		element.sendKeys(testDriver.getLastName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pesel\"]")));
		element.sendKeys(testDriver.getPESEL());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"salary\"]")));
		element.sendKeys(String.valueOf(testDriver.getSalary()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"addressId\"]")));
		element.sendKeys(String.valueOf(testDriver.getAddress()));
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"add_driver_form\"]/div[4]/button")));
		element.click();
		Thread.sleep(1000);

        Assert.assertTrue(driverManager.getAll().contains(driverManager.get(driverList.get(1).getId()+1)));		
	}

	@Test
	public void deleteDriver() throws Exception {

		Driver testDriver = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));
		driverList.add(testDriver);
		
		driver.get("http://localhost:8080/drivers");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delete-button-" + testDriver.getId() + "\"]")));
		element.click();
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/button[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
;
		Assert.assertFalse(driverManager.getAll().contains(testDriver));
	}
	
	@Test
	public void updateDriver() throws Exception {
		
		Driver testDriver1= TestModelsFactory.createTestDriver3(addressList.get(0));
		driverList.add(testDriver1);
		
		driver.get("http://localhost:8080/drivers");
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/drivers/edit/"+driverList.get(1).getId()+"']")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"firstName\"]")));
		element.clear();
		element.sendKeys(testDriver1.getFirstName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"lastName\"]")));
		element.clear();
		element.sendKeys(testDriver1.getLastName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pesel\"]")));
		element.clear();
		element.sendKeys(testDriver1.getPESEL());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"salary\"]")));
		element.clear();
		element.sendKeys(String.valueOf(testDriver1.getSalary()));
		
		Thread.sleep(1000);
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edit_driver_form\"]/button")));
		element.click();
		
		Thread.sleep(1000);
		
		Driver testdriver2 = driverManager.get(driverList.get(1).getId());
		
		Assert.assertEquals(testdriver2.getFirstName(), testDriver1.getFirstName());
		Assert.assertEquals(testdriver2.getLastName(), testDriver1.getLastName());
		Assert.assertEquals(testdriver2.getPESEL(), testDriver1.getPESEL());
		Assert.assertEquals(testdriver2.getSalary(), testDriver1.getSalary());
		
	}
	
	@Test
	public void getDriver() throws Exception {
		
		driver.get("http://localhost:8080/drivers");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='" + driverList.get(1).getId() + "']")));
		element.click();
		
		String URL = driver.getCurrentUrl();
		Thread.sleep(1000);
		Assert.assertEquals(URL, "http://localhost:8080/drivers/"+ driverList.get(1).getId() );
	}
	
	public String firefoxPath() {
		Properties prop = new Properties();
		InputStream input = null;
		String path;
		
			try {
				input = getClass().getClassLoader().getResourceAsStream("firefox.properties");
				prop.load(input);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		path = prop.getProperty("firefox.path");
		return path;
	}
	
}