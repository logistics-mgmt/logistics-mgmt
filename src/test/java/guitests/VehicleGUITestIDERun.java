package guitests;

import java.io.File;
import java.text.SimpleDateFormat;
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

import com.jdbc.demo.Application;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Vehicle;

import utils.TestModelsFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class VehicleGUITestIDERun {	
	
	@Autowired
	private VehicleDAO vehicleManager;


	private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

	static WebElement element;
	static FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
	static FirefoxProfile profile = new FirefoxProfile();
	static WebDriver driver;

	@Before
	public void setup() throws Exception {
		
		Vehicle vehicle = vehicleManager.add(TestModelsFactory.createTestVehicle1());
		vehicleList.add(vehicle);


		driver = new FirefoxDriver(binary, profile);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/vehicles"); 
		
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
		
		for (Vehicle vehicle : vehicleList) {
			vehicleManager.delete(vehicle);
		}


	}
	
	@Test 
	public void addVehicle() throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Vehicle testVehicle = TestModelsFactory.createTestVehicle2();
		testVehicle.setId(vehicleList.get(0).getId()+1);
		vehicleList.add(testVehicle);
		
		driver.get("http://localhost:8080/vehicles");
		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/a")));
		element.click();
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"brand\"]")));
		element.sendKeys(testVehicle.getBrand());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"model\"]")));
		element.sendKeys(testVehicle.getModel());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"maxPayload\"]")));
		element.sendKeys(String.valueOf(testVehicle.getMaxPayload()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"engine\"]")));
		element.sendKeys(String.valueOf(testVehicle.getEngine()));		
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vin\"]")));
		element.sendKeys(testVehicle.getVIN());
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mileage\"]")));
		element.sendKeys(String.valueOf(testVehicle.getMileage()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"horsepower\"]")));
		element.sendKeys(String.valueOf(testVehicle.getHorsepower()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"productionDate\"]")));
		element.sendKeys(String.valueOf(format.format(testVehicle.getProductionDate())));
		Thread.sleep(5000);
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"add_vehicle_form\"]/button")));
		element.click();
		Thread.sleep(2000);
		
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getBrand(),testVehicle.getBrand());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getModel(),testVehicle.getModel());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getMaxPayload(),testVehicle.getMaxPayload());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getEngine(),testVehicle.getEngine());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getVIN(),testVehicle.getVIN());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getMileage(),testVehicle.getMileage());
		Assert.assertEquals(vehicleManager.get(vehicleList.get(0).getId()+1).getHorsepower(),testVehicle.getHorsepower());
		Assert.assertEquals(String.valueOf(vehicleManager.get(vehicleList.get(0).getId()+1).getProductionDate()),format.format(testVehicle.getProductionDate()));
	}
	
	@Test
	public void deleteVehicle() throws Exception {

		Vehicle testVehicle = vehicleList.get(0);
		
		driver.get("http://localhost:8080/vehicles");
		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delete-button-" + testVehicle.getId() + "\"]")));
		element.click();
		Thread.sleep(2000);
		element = (new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/button[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		Thread.sleep(5000);
		vehicleList.remove(testVehicle);
		Assert.assertFalse(vehicleManager.getAll().contains(testVehicle));
	}
	
	@Test
	public void updateVehicle() throws Exception {
		
		Vehicle testVehicle1= TestModelsFactory.createTestVehicle2();
		vehicleList.add(testVehicle1);
		
		driver.get("http://localhost:8080/vehicles");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/vehicles/edit/"+vehicleList.get(0).getId()+"']")));
		element.click();

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"brand\"]")));
		element.clear();
		element.sendKeys(testVehicle1.getBrand());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"model\"]")));
		element.clear();
		element.sendKeys(testVehicle1.getModel());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"engine\"]")));
		element.clear();
		element.sendKeys(String.valueOf(testVehicle1.getEngine()));		

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vin\"]")));
		element.clear();
		element.sendKeys(testVehicle1.getVIN());

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mileage\"]")));
		element.clear();
		element.sendKeys(String.valueOf(testVehicle1.getMileage()));

		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"horsepower\"]")));
		element.clear();
		element.sendKeys(String.valueOf(testVehicle1.getHorsepower()));
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edit_vehicle_form\"]/button")));
		element.click();
		
		Thread.sleep(1000);
		Vehicle testVehicle2 = vehicleManager.get(vehicleList.get(0).getId());

		Assert.assertEquals(testVehicle2.getBrand(), testVehicle1.getBrand());
		Assert.assertEquals(testVehicle2.getModel(), testVehicle1.getModel());
		Assert.assertEquals(testVehicle2.getEngine(), testVehicle1.getEngine());
		Assert.assertEquals(testVehicle2.getVIN(), testVehicle1.getVIN());
		Assert.assertEquals(testVehicle2.getMileage(), testVehicle1.getMileage());
		Assert.assertEquals(testVehicle2.getHorsepower(), testVehicle1.getHorsepower());


	}
	
	@Test
	public void getVehicle() throws Exception {
		
		driver.get("http://localhost:8080/vehicles");
		
		element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='" + vehicleList.get(0).getId() + "']")));
		element.click();
		String URL = driver.getCurrentUrl();
		Thread.sleep(1000);
		Assert.assertEquals(URL, "http://localhost:8080/vehicles/"+ vehicleList.get(0).getId() );
	}
	
}
