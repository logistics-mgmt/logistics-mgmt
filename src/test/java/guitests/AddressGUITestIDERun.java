package guitests;

import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.Application;
import com.jdbc.demo.domain.psql.Address;

import utils.FirefoxPath;
import utils.TestModelsFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class AddressGUITestIDERun {

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Address> addressList = new ArrayList<Address>();

	private static FirefoxPath getPath;
	
	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File(getPath.firefoxPath()));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Before
	public void setup() throws Exception {

		Address address1 = addressManager.add(TestModelsFactory.createTestAddress1());
		addressList.add(address1);
		
		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/addresses"); 
		
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
		
		for (Address address : addressList) {
			addressManager.delete(address.getId());
		}

	}
	
	@Test 
	public void addAddress() throws Exception {
		
		Address testAddress = TestModelsFactory.createTestAddress2();
		testAddress.setId(addressList.get(0).getId()+1);
		addressList.add(testAddress);
		
		driver.get("http://localhost:8080/addresses");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"town\"]")));
		element.sendKeys(testAddress.getTown());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"street\"]")));
		element.sendKeys(testAddress.getStreet());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"code\"]")));
		element.sendKeys(testAddress.getCode());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"houseNumber\"]")));
		element.sendKeys(testAddress.getHouseNumber());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"country\"]")));
		element.sendKeys(testAddress.getCountry());
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"add_address_form\"]/button")));
		element.click();
		Thread.sleep(1000);

        Assert.assertTrue(addressManager.getAll().contains(addressManager.get(addressList.get(0).getId()+1)));		
	}
	
	@Test
	public void deleteAddress() throws Exception {

		Address testAddress = addressList.get(0);
		
		driver.get("http://localhost:8080/addresses");
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delete-button-" + testAddress.getId() + "\"]")));
		element.click();
		
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/button[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		Thread.sleep(1000);
		
		addressList.remove(testAddress);
		Assert.assertFalse(addressManager.getAll().contains(testAddress));
	}
	
	@Test
	public void updateAddress() throws Exception {
		
		Address testAddress1 = TestModelsFactory.createTestAddress2();
		
		driver.get("http://localhost:8080/addresses");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/addresses/edit/"+addressList.get(0).getId()+"']")));
		element.click();

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"town\"]")));
		element.clear();
		element.sendKeys(testAddress1.getTown());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"street\"]")));
		element.clear();
		element.sendKeys(testAddress1.getStreet());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"code\"]")));
		element.clear();
		element.sendKeys(testAddress1.getCode());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"houseNumber\"]")));
		element.clear();
		element.sendKeys(testAddress1.getHouseNumber());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"country\"]")));
		element.clear();
		element.sendKeys(testAddress1.getCountry());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edit_address_form\"]/button")));
		element.click();
		
		Thread.sleep(1000);
		
		Address testAddress2 = addressManager.get(addressList.get(0).getId());

		Assert.assertEquals(testAddress2.getTown(), testAddress1.getTown());
		Assert.assertEquals(testAddress2.getStreet(), testAddress1.getStreet());
		Assert.assertEquals(testAddress2.getCode().substring(0, 6), testAddress1.getCode().substring(0, 6));
		Assert.assertEquals(testAddress2.getHouseNumber(), testAddress1.getHouseNumber());
		Assert.assertEquals(testAddress2.getCountry(), testAddress1.getCountry());
	
	}
	
}