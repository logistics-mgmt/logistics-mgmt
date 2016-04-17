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
import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;

import utils.TestModelsFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ClientGUITestIDERun {

	@Autowired
	private ClientDAO clientManager;

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ArrayList<Address> addressList = new ArrayList<Address>();

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Before
	public void setup() throws Exception {

		addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		Client client1 = clientManager.add(TestModelsFactory.createTestClient1(addressList.get(0)));
		clientList.add(client1);
		Client client2 = clientManager.add(TestModelsFactory.createTestClient2(addressList.get(0)));
		clientList.add(client2);

		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/clients"); 
		
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
		
		for (Client client : clientList) {
			clientManager.delete(client);
		}

		addressManager.delete(addressList.get(0));

	}

	@Test 
	public void addClient() throws Exception {
		
		Client testClient = TestModelsFactory.createTestClient3(addressList.get(0));
		testClient.setId(clientList.get(1).getId()+1);
		clientList.add(testClient);
		
		driver.get("http://localhost:8080/clients");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"name\"]")));
		element.sendKeys(testClient.getName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"nip\"]")));
		element.sendKeys(testClient.getNIP());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"bankAccountNumber\"]")));
		element.sendKeys(testClient.getBankAccountNumber());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"addressId\"]")));
		element.sendKeys(String.valueOf(testClient.getAddress()));
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"add_client_form\"]/button")));
		element.click();
		Thread.sleep(1000);

        Assert.assertTrue(clientManager.getAll().contains(clientManager.get(clientList.get(1).getId()+1)));		
	}

	@Test
	public void deleteClient() throws Exception {

		Client testClient = clientList.get(0);
		
		driver.get("http://localhost:8080/clients");
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delete-button-" + testClient.getId() + "\"]")));
		element.click();
		
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/button[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		Thread.sleep(1000);
		
		clientList.remove(testClient);
		Assert.assertFalse(clientManager.getAll().contains(testClient));
	}
	
	@Test
	public void updateClient() throws Exception {
		
		Client testClient1= TestModelsFactory.createTestClient3(addressList.get(0));
		clientList.add(testClient1);
		
		driver.get("http://localhost:8080/clients");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/clients/edit/"+clientList.get(0).getId()+"']")));
		element.click();

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"name\"]")));
		element.clear();
		element.sendKeys(testClient1.getName());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"nip\"]")));
		element.clear();
		element.sendKeys(testClient1.getNIP());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"bankAccountNumber\"]")));
		element.clear();
		element.sendKeys(testClient1.getBankAccountNumber());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edit_client_form\"]/button")));
		element.click();
		
		Thread.sleep(1000);
		
		Client testClient2 = clientManager.get(clientList.get(0).getId());

		Assert.assertEquals(testClient2.getName(), testClient1.getName());
		Assert.assertEquals(testClient2.getNIP(), testClient1.getNIP());
		Assert.assertEquals(testClient2.getBankAccountNumber().substring(0, 14), testClient1.getBankAccountNumber().substring(0, 14));
	}
	
}