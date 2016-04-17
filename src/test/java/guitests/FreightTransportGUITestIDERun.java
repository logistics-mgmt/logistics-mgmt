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
import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.domain.psql.Vehicle;
import utils.TestModelsFactory;
import java.io.File;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class FreightTransportGUITestIDERun {

	@Autowired
	private AddressDAO addressManager;
	
	@Autowired
	private DriverDAO driverManager;
	
	@Autowired
	private VehicleDAO vehicleManager;
	
	@Autowired
	private ClientDAO clientManager;
	
	@Autowired
	private FreightTransportDAO FreightTransportManager;

	private ArrayList<Address> addressList = new ArrayList<Address>();
	private ArrayList<Driver> driverList = new ArrayList<Driver>();
	private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ArrayList<FreightTransport> FreightTransportList = new ArrayList<FreightTransport>();

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Before
	public void setup() throws Exception {

		addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		addressList.add(addressManager.add(TestModelsFactory.createTestAddress2()));

		driverList.add(driverManager.add(TestModelsFactory.createTestDriver1(addressList.get(0))));
		driverList.add(driverManager.add(TestModelsFactory.createTestDriver2(addressList.get(0))));

		vehicleList.add(vehicleManager.add(TestModelsFactory.createTestVehicle1()));
		vehicleList.add(vehicleManager.add(TestModelsFactory.createTestVehicle2()));

		clientList.add(clientManager.add(TestModelsFactory.createTestClient1(addressList.get(0))));
		clientList.add(clientManager.add(TestModelsFactory.createTestClient2(addressList.get(0))));
		
		FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
				vehicleList, addressList.get(0), addressList.get(1))));

		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/");
driver.get("http://localhost:8080/transports"); 
		
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
		
		for (FreightTransport freightTransport : FreightTransportList) {
			FreightTransportManager.delete(freightTransport.getId());
		}				
		
		for (Client client : clientList) {
			clientManager.delete(client);
		}

		for (Address address : addressList) {
			addressManager.delete(address);
		}
	}
	
	@Test 
	public void addFreightTransport() throws Exception {
		
		FreightTransport testFreightTransport = TestModelsFactory.createTestFreightTransport2(clientList.get(0),driverList,
				vehicleList, addressList.get(1), addressList.get(0));
		testFreightTransport.setId(FreightTransportList.get(0).getId()+1);
		FreightTransportList.add(testFreightTransport);
		
		driver.get("http://localhost:8080/transports");

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"loadAddressId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport.getLoadAddress()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"unloadAddressId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport.getUnloadAddress()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"clientId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport.getClient()));
		
		element = (new WebDriverWait(driver, 10))				
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"value\"]")));
		element.sendKeys(String.valueOf(testFreightTransport.getValue()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"loadDate\"]")));
		element.click();
		driver.findElement(By.linkText("24")).click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"unloadDate\"]")));
		element.click();
		element.click();
		
		driver.findElement(By.linkText("28")).click();
		
		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"add_transport_form\"]/button")));
		element.click();
		Thread.sleep(2000);

		Assert.assertEquals(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getLoadAddress().getId(),testFreightTransport.getLoadAddress().getId());
		Assert.assertEquals(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getUnloadAddress().getId(),testFreightTransport.getUnloadAddress().getId());
		Assert.assertEquals(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getClient().getId(),testFreightTransport.getClient().getId());
		Assert.assertEquals(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getValue(),testFreightTransport.getValue());
		Assert.assertEquals(String.valueOf(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getLoadDate()),"2016-04-24");
		Assert.assertEquals(String.valueOf(FreightTransportManager.get(FreightTransportList.get(0).getId()+1).getUnloadDate()),"2016-04-28");
	}
	
	@Test
	public void deleteFreightTransport() throws Exception {

		FreightTransport testFreightTransport = FreightTransportList.get(0);
		
		driver.get("http://localhost:8080/transports");
		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delete-button-" + testFreightTransport.getId() + "\"]")));
		element.click();
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/button[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		FreightTransportList.remove(testFreightTransport);
		Thread.sleep(2000);
		Assert.assertFalse(FreightTransportManager.getAll().contains(testFreightTransport));
	}
	
	@Test
	public void updateFreightTransport() throws Exception {
		
		FreightTransport testFreightTransport1= TestModelsFactory.createTestFreightTransport2(clientList.get(0),driverList,
				vehicleList, addressList.get(1), addressList.get(0));
		FreightTransportList.add(testFreightTransport1);
		
		driver.get("http://localhost:8080/transports");
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/transports/edit/"+FreightTransportList.get(0).getId()+"']")));
		element.click();
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"loadAddressId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport1.getLoadAddress()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"unloadAddressId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport1.getUnloadAddress()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"clientId\"]")));
		element.sendKeys(String.valueOf(testFreightTransport1.getClient()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"value\"]")));
		element.clear();
		element.sendKeys(String.valueOf(testFreightTransport1.getValue()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"loadDate\"]")));
		element.click();
		driver.findElement(By.linkText("23")).click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"unloadDate\"]")));
		element.click();
		element.click();
		driver.findElement(By.linkText("27")).click();
		
		Thread.sleep(1000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edit_transport_form\"]/button")));
		element.click();
		FreightTransportList.remove(testFreightTransport1);
		Thread.sleep(2000);
		
		for (Driver driver : driverList) {
			driverManager.delete(driver);
		}
		
		for (Vehicle vehicle : vehicleList) {
			vehicleManager.delete(vehicle);
		}
		
		FreightTransport testFreightTransport2 = FreightTransportManager.get(FreightTransportList.get(0).getId());
		

		Assert.assertEquals(testFreightTransport2.getLoadAddress().getId(),testFreightTransport1.getLoadAddress().getId());
		Assert.assertEquals(testFreightTransport2.getUnloadAddress().getId(),testFreightTransport1.getUnloadAddress().getId());
		Assert.assertEquals(testFreightTransport2.getClient().getId(),testFreightTransport1.getClient().getId());
		Assert.assertEquals(testFreightTransport2.getValue(),testFreightTransport1.getValue());
		Assert.assertEquals(String.valueOf(testFreightTransport2.getLoadDate()),"2016-04-23");
		Assert.assertEquals(String.valueOf(testFreightTransport2.getUnloadDate()),"2016-04-27");

	}
	
	@Test
	public void getFreightTransport() throws Exception {
		
		driver.get("http://localhost:8080/transports");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='" + FreightTransportList.get(0).getId() + "']")));
		element.click();
		String URL = driver.getCurrentUrl();
		Thread.sleep(1000);
		Assert.assertEquals(URL, "http://localhost:8080/transports/"+ FreightTransportList.get(0).getId() );
	}
	
}