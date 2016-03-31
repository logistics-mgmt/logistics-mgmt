package utils;


import com.jdbc.demo.domain.psql.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mateusz on 04-Nov-15.
 */
public abstract class TestModelsFactory {

    public static Client createTestClient1(Address address){
        Client client1 = new Client();
        client1.setName("ABC SA");
        client1.setAddress(address);
        client1.setNIP("1234567890");
        client1.setBankAccountNumber("14532534634632");
        return client1;
    }

    public static Client createTestClient2(Address address){
        Client client2 = new Client();
        client2.setName("XYZ sp. z. o o.");
        client2.setAddress(address);
        client2.setNIP("1234567490");
        client2.setBankAccountNumber("145325312334632");
        return client2;
    }

    public static Address createTestAddress1(){
        Address testAddress1 = new Address();
        testAddress1.setTown("Radom");
        testAddress1.setCode("54-543");
        testAddress1.setCountry("Polska");
        testAddress1.setHouseNumber("87/4");
        testAddress1.setStreet("Dolna");
        return testAddress1;
    }
    
    public static Address createTestAddress2(){
        Address testAddress2 = new Address();
        testAddress2.setTown("Sosnowiec");
        testAddress2.setCode("11-700");
        testAddress2.setCountry("Polska");
        testAddress2.setHouseNumber("3/4");
        testAddress2.setStreet("Agrarna");
        return testAddress2;
    }
    
    public static Address createTestAddress3(){
        Address testAddress3 = new Address();
        testAddress3.setTown("Bialystok");
        testAddress3.setCode("88-777");
        testAddress3.setCountry("Polska");
        testAddress3.setHouseNumber("21/5");
        testAddress3.setStreet("Gorna");
        return testAddress3;
    }
    public static Driver createTestDriver1(Address address){
        Driver driver1 = new Driver();
        driver1.setAddress(address);
        driver1.setFirstName("Jerzy");
        driver1.setLastName("Banan");
        driver1.setSalary(new BigDecimal(3200).setScale(2, BigDecimal.ROUND_CEILING));
        driver1.setPESEL("12345678910");
        driver1.setAvailable(true);
        driver1.setDeleted(false);
        return driver1;
    }

    public static Driver createTestDriver2(Address address){
        Driver driver2 = new Driver();
        driver2.setAddress(address);
        driver2.setFirstName("Tadeusz");
        driver2.setLastName("Czapla");
        driver2.setSalary(new BigDecimal(3200).setScale(2, BigDecimal.ROUND_CEILING));
        driver2.setPESEL("12345678911");
        driver2.setAvailable(true);
        driver2.setDeleted(false);
        return driver2;
    }
    
    public static Driver createTestDriver3(Address address){
        Driver driver3 = new Driver();
        driver3.setAddress(address);
        driver3.setFirstName("Jan");
        driver3.setLastName("Czapla");
        driver3.setSalary(new BigDecimal(3200).setScale(2, BigDecimal.ROUND_CEILING));
        driver3.setPESEL("12345687911");
        driver3.setAvailable(true);
        driver3.setDeleted(false);
        return driver3;
    }
    
    public static Vehicle createTestVehicle1(){
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand("Scania");
        vehicle1.setEngine(16);
        vehicle1.setHorsepower(300);
        vehicle1.setModel("ZX-83");
        vehicle1.setVIN("1M8GDM9A_KP042777");
        vehicle1.setProductionDate(new Date(System.currentTimeMillis()));
        return vehicle1;
    }

    public static Vehicle createTestVehicle2(){
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand("Scania");
        vehicle2.setEngine(16);
        vehicle2.setHorsepower(300);
        vehicle2.setModel("ZX-83");
        vehicle2.setVIN("1M8GDM9A_KE042777");
        vehicle2.setProductionDate(new Date(System.currentTimeMillis()));
        return vehicle2;
    }
    
    public static Vehicle createTestVehicle3(){
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBrand("Scania");
        vehicle3.setEngine(16);
        vehicle3.setHorsepower(300);
        vehicle3.setModel("ZX-83");
        vehicle3.setVIN("1M8GDM9A_KE042777");
        vehicle3.setProductionDate(new Date(System.currentTimeMillis()));
        return vehicle3;
    }

    public static FreightTransport createTestFreightTransport1(Client client, List<Driver> drivers, List<Vehicle> vehicles,
                                                              Address loadAddress, Address unloadAddress){
        FreightTransport freightTransport1 = new FreightTransport();
        freightTransport1.setFinished(false);
        freightTransport1.setValue(new BigDecimal(45000).setScale(2, BigDecimal.ROUND_CEILING));
        freightTransport1.setDistance(1450);
        freightTransport1.setUnloadAddress(unloadAddress);
        freightTransport1.setClient(client);
        freightTransport1.setLoadAddress(unloadAddress);
        freightTransport1.setDrivers(drivers);
        freightTransport1.setVehicles(vehicles);
        return freightTransport1;
    }

    public static FreightTransport createTestFreightTransport2(Client client, List<Driver> drivers, List<Vehicle> vehicles,
                                                               Address loadAddress, Address unloadAddress){
        FreightTransport freightTransport2 = new FreightTransport();
        freightTransport2.setFinished(false);
        freightTransport2.setValue(new BigDecimal(32657).setScale(2, BigDecimal.ROUND_CEILING));
        freightTransport2.setDistance(1234);
        freightTransport2.setUnloadAddress(unloadAddress);
        freightTransport2.setClient(client);
        freightTransport2.setLoadAddress(loadAddress);
        freightTransport2.setDrivers(drivers);
        freightTransport2.setVehicles(vehicles);
        return freightTransport2;
    }
}
