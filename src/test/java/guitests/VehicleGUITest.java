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
import com.jdbc.demo.Application;
import com.jdbc.demo.VehicleDAO;
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
public class VehicleGUITest {

	@Autowired
	private VehicleDAO vehicleManager;


	private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Before
	public void setup() throws Exception {

		Vehicle vehicle1 = vehicleManager.add(TestModelsFactory.createTestVehicle1());
		vehicleList.add(vehicle1);


		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/");
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/input")));
		element.clear();
		element.sendKeys("admin");

		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/input")));
		element.clear();
		element.sendKeys("admin");

		Thread.sleep(1000);
		
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/form/table/tbody/tr[3]/td/input")));
		element.click();
	}

	@After
	public void cleanup() throws Exception {
		
		driver.close();
		
		for (Vehicle vehicle : vehicleList) {
			vehicleManager.delete(vehicle);
		}


	}

	@Test 
	public void addVehicle() throws Exception {
		
		Vehicle testVehicle = TestModelsFactory.createTestVehicle1();
		testVehicle.setId(vehicleList.get(0).getId()+1);
		vehicleList.add(testVehicle);
		
		driver.get("http://localhost:8080/vehicles");
	
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"brand\"]")));
		element.sendKeys(testVehicle.getBrand());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"model\"]")));
		element.sendKeys(testVehicle.getModel());
		
	}
	
	
}
