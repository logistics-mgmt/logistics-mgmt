set datestyle to "ISO, YMD";


DROP TABLE FreightTransportDrivers CASCADE;

DROP TABLE FreightTransportVehicles CASCADE;

DROP TABLE FreightTransport CASCADE;

DROP TABLE Client CASCADE;

DROP TABLE Driver CASCADE;

DROP TABLE Address CASCADE;

DROP TABLE Vehicle CASCADE;

DROP TABLE UsersRolesData CASCADE;
DROP TABLE Users CASCADE;
DROP TABLE UserRoles CASCADE;



create table Users (
   user_id SERIAL PRIMARY KEY UNIQUE,
   login VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL, 
   UNIQUE (login)
);
  

create table UserRoles(
   user_roles_id SERIAL PRIMARY KEY UNIQUE,
   type VARCHAR(30) NOT NULL,
   UNIQUE (type)
);
  

CREATE TABLE UsersRolesData (
    user_id BIGINT NOT NULL,
    user_roles_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_roles_id),
    CONSTRAINT FK_Users FOREIGN KEY (user_id) REFERENCES Users (user_id),
    CONSTRAINT FK_UserRoles FOREIGN KEY (user_roles_id) REFERENCES UserRoles (user_roles_id)
);
 



CREATE TABLE Vehicle (
  id_Vehicle SERIAL PRIMARY KEY UNIQUE,
  brand VARCHAR(30) NOT NULL CHECK(char_length(brand)>2),
  model VARCHAR(30) NOT NULL,
  mileage INTEGER NOT NULL CHECK(mileage>=0),
  engine DECIMAL (8,2)  NOT NULL CHECK(engine>=0),
  production_date DATE NOT NULL,
  VIN CHAR(17) UNIQUE  NOT NULL CHECK(char_length(VIN)=17),
  horsepower INTEGER  NOT NULL,
  max_payload INTEGER NULL DEFAULT 0,
  available boolean DEFAULT '1'
);

CREATE TABLE Address (
  id_Address SERIAL PRIMARY KEY UNIQUE,
  house_number VARCHAR(8) NOT NULL,
  street VARCHAR(40) NOT NULL CHECK(char_length(street)>2),
  town VARCHAR(30) NOT NULL CHECK(char_length(town)>1),
  country VARCHAR(30) NOT NULL CHECK(char_length(country)>2),
  code CHAR(10) NOT NULL
);

CREATE TABLE Driver (
  id_Driver SERIAL PRIMARY KEY UNIQUE,
  id_Address INTEGER NOT NULL REFERENCES Address(id_Address),
  first_name VARCHAR(30) NOT NULL CHECK(char_length(first_name)>2),
  last_name VARCHAR(30) NOT NULL CHECK(char_length(last_name)>2),
  pesel CHAR(11) UNIQUE NOT NULL CHECK(char_length(pesel)=11),
  salary DECIMAL(8,2) NULL DEFAULT 1600 CHECK(salary>=0),
  salary_bonus DECIMAL(8,2) DEFAULT 0,
  available boolean DEFAULT '1',
  deleted boolean DEFAULT '0'
);

CREATE TABLE Client (
  id_Client SERIAL PRIMARY KEY UNIQUE,
  id_Address INTEGER NOT NULL REFERENCES Address(id_Address),
  name VARCHAR(50) UNIQUE NOT NULL CHECK (char_length(name)>2),
  NIP CHAR(10) NULL CHECK (char_length(NIP)=10),
  account_number CHAR(26)  NULL,
  deleted boolean DEFAULT '0'
);

CREATE TABLE FreightTransport (
  id_FreightTransport SERIAL PRIMARY KEY UNIQUE,
  id_load_Address INTEGER NOT NULL REFERENCES Address(id_Address),
  id_unload_Address INTEGER NOT NULL REFERENCES Address(id_Address),
  id_Client INTEGER  NOT NULL REFERENCES Client(id_Client),
  distance INTEGER NULL CHECK(distance>0 OR distance IS NULL),
  value DECIMAL(8,2) NOT NULL CHECK(value>=0),
  payload_weight INTEGER NULL DEFAULT 0,
  name VARCHAR(100) NULL,
  load_date date NULL,
  unload_date date NULL,
  payment_date date NULL,
  finished boolean DEFAULT '0',
  notes VARCHAR(200) NULL
);


CREATE TABLE FreightTransportDrivers (
  id_FreightTransport INTEGER NOT NULL REFERENCES FreightTransport(id_FreightTransport),
  id_Driver INTEGER NOT NULL REFERENCES Driver(id_Driver),
  PRIMARY KEY(id_FreightTransport, id_Driver)
);


CREATE TABLE FreightTransportVehicles (
  id_FreightTransport INTEGER NOT NULL REFERENCES FreightTransport(id_FreightTransport),
  id_Vehicle INTEGER NOT NULL REFERENCES Vehicle(id_Vehicle),
  PRIMARY KEY(id_FreightTransport, id_Vehicle)
);


INSERT INTO UserRoles(type)
VALUES ('USER');
 
INSERT INTO UserRoles(type)
VALUES ('ADMIN');
 
INSERT INTO UserRoles(type)
VALUES ('FORWARDER');

INSERT INTO Users(login, password, first_name, last_name)
VALUES ('admin','admin', 'Administrator','Administratorowy');

INSERT INTO UsersRolesData (user_id, user_roles_id)
  SELECT users.user_id, profile.user_roles_id FROM users users, UserRoles profile  
  where users.login='admin' and profile.type='ADMIN';

INSERT INTO UsersRolesData (user_id, user_roles_id)
  SELECT users.user_id, profile.user_roles_id FROM users users, UserRoles profile  
  where users.login='admin' and profile.type='FORWARDER';

  INSERT INTO UsersRolesData (user_id, user_roles_id)
  SELECT users.user_id, profile.user_roles_id FROM users users, UserRoles profile  
  where users.login='admin' and profile.type='USER';

INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '14', 'Mazowiecka', '00-130');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '6', 'Armii Krajowej', '00-145');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Kraków','Polska', '13', 'Władysława Łokietka', '30-124');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Kraków','Polska', '67', 'Stefana Batorego', '30-150');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Poznań','Polska', '3', 'Piernikowa', '60-080');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Poznań','Polska', '19', 'Kolejarska', '60-230');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Gdańsk','Polska', '4', 'Jaśkowa Dolina', '80-005');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Katowice','Polska', '24', 'Weglowa', '40-354');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Brema','Niemcy', '7', 'Am Markt', '2801');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Brema','Niemcy', '123', 'Hanseatischer Platz', '23552');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Lubeka','Niemcy', '35', 'Friedrich Barbarossa Strasse', '23553');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Lubeka','Niemcy', '123', 'Hansa Strasse', '23552');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Frankfurt nad Menem','Niemcy', '40', 'Unter Vogeln', '60310');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Antwerpia','Belgia', '1', 'Hoboken', '2045');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Amsterdam','Holandia', '76', 'Vijzel', '1024');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Sztokholm','Szwecja', '45', 'Vargattan', '1236');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Berlin','Niemcy', '66/7', 'Kurfurtstendamm', '12567');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '3', 'Obroncow Tobruku', '00-542');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Katowice','Polska', '73', 'Brunatna', '40-542');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '45', 'Lewa', '00-427');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '5', 'Spichrzowa', '80-111');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '7', 'Kaperska', '80-132');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '6', 'Prawa', '80-143');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Warszawa','Polska', '1', 'Prosta', '80-123');
INSERT INTO Address(town, country, house_number, street, code) VALUES ('Poznań','Polska', '11', 'Okopowa', '40-123');

INSERT INTO Client(id_Address, name,account_number) VALUES (10, 'Hans und Klaus GmbH', 'DE54694860382758391748');
INSERT INTO Client(id_Address, name, NIP, account_number) VALUES (1, 'WarChem Sp. z o.o.','4614353534', '80124046970375491234643657');
INSERT INTO Client(id_Address, name, NIP) VALUES (3, 'Feanor S.A.', '7613383904');
INSERT INTO Client(id_Address, name, account_number) VALUES (17, 'Sigurd-Logistik Aktiengesellschaft', 'DE416948260982750331490');
INSERT INTO Client(id_Address, name, NIP) VALUES (18, 'FerraMet Sp. z o.o.', '3273239895');


INSERT INTO Driver ( id_Address, last_name, first_name, pesel, salary) VALUES (7, 'Banan', 'Jerzy', '86200407043', 2000);
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (6, 'Czapla', 'Tadeusz', '75100347691');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (1, 'Ostrogski', 'Wacław', '82150647073');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (2, 'Palipies', 'Janusz', '76170407095');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel, salary) VALUES (3, 'Niedzwiedz', 'Stefan', '84030509011', 3000);
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (19, 'Potocki', 'Michał', '89070908767');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (20, 'Tarnowski', 'Jerzy', '88050208654');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (21, 'Wioniowiecki', 'Roman', '84020108678');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (22, 'Opolczyk', 'Zygfryd', '90010107653');
INSERT INTO Driver ( id_Address, last_name, first_name, pesel, salary) VALUES (23, 'Tabaka', 'Jan', '80050908432', 3000);
INSERT INTO Driver ( id_Address, last_name, first_name, pesel) VALUES (4, 'Koło', 'Jan', '88030106789');

INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('Scania', 'R-500', 12456, 16, '2010-02-01', '1M8GDM9A_KP042788', 500, 15000);
INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('Scania', 'R-500', 78219, 16, '2009-10-12', '1M8GDM9A_KP145890', 500, 20000);
INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('MAN', 'TGA', 123798, 12, '2007-05-19', '1N8GTM6B_KP047893', 430, 25000);
INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('MAN', 'TGA', 156129, 12, '2007-03-10', '1N8GTM6B_KP123487', 430, 25000);
INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('MAN', 'TGX', 194982, 12, '2007-03-10', '1N8GTM6B_KP548730', 540, 15000);
INSERT INTO Vehicle (brand, model, mileage, engine, production_date, VIN, horsepower, max_payload) VALUES ('DAF', 'XF 105', 48723, 14, '2006-02-10', '2Y3TTE6B_KP432334', 560, 24000);


INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date, unload_date, payment_date, finished) VALUES (8, 15, 4, 794, 25416.23, '2014-03-14', '2014-03-17', '2014-03-19', '1');
INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date, unload_date, payment_date, finished) VALUES (8, 16, 5,476, 17867.34, '2014-01-11', '2014-01-13', '2014-01-22', '1');
INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date) VALUES (1, 14, 2,653, 12854.67, '2014-05-05');
INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date, unload_date, payment_date, finished) VALUES (8, 20, 5,494, 27867.23, '2014-01-11', '2014-01-13', '2014-01-22', '1');
INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date, unload_date) VALUES (21, 13, 1, 768, 38786.89, '2014-05-02', '2014-05-04');


INSERT INTO FreightTransport (id_load_Address, id_unload_Address, id_Client, distance, value, load_date, unload_date, name) VALUES (21, 13, 1, 768, 38786.89, '2016-02-02', '2016-02-28', 'Test drive');

INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (1,3);

INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (1,2);

INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (2,4);
INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (3,1);
INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (4,3);
INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (5,6);
INSERT INTO FreightTransportVehicles (id_FreightTransport, id_Vehicle) VALUES (6,4);

INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (1,1);
INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (1,2);


INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (2,6);


INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (3,5);
INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (4,2);
INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (5,3);
INSERT INTO FreightTransportDrivers (id_FreightTransport, id_Driver) VALUES (6,9);