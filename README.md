# logistics_mgmt

<h2>How to run locally:</h2>

<h3>Prerequisites:</h3>
  1. Java 1.8
  2. PostgreSQL 9.4.x
  3. Maven 3.3.3
  4. Google Maps API keys (https://developers.google.com/maps/documentation/javascript/get-api-key) with access to following services:
     * Directions
     * Distance Matrix
     * Geocoding
     * Javascript API
  5. MongoDB 3.2
  

<h3>Local setup:</h3>
  * Create local PostgreSQL database (DBScript.sql file is located in main folder of the repository):
	1. `createdb -U postgres logistics`
  	2. `psql -U postgres -d logistics -a -f DBScript.sql`
  
  * Create local MongoDB database (routeWaypoints.json file is located in main folder of the repository):
  	1. `mongod # Run local mongoDB instance`
  	2. `mongoimport --db test --collection routeWaypoint --drop --file routeWaypoints.json` 
  
  
  * Fill `logistics-mgmt/src/main/resources/jdbc.properties.default` file with correct credentials to your local PostgreSQL and MongoDB databases and copy/rename it to `jdbc.properties`.
  
  * Fill `logistics_mgmt/src/main/resources/maps.properties.default` with your Google Maps API keys. If you don't have them, follow instructions on https://developers.google.com/maps/documentation/distance-matrix/get-api-key#key to acquire server key, make sure to enable access to Directions, Distance Matrix, GeoCoding and JavaScript APIs. To generate browser key, click `get a key` button on https://developers.google.com/maps/documentation/embed/. 

  Compile and start embedded Tomcat server with: `mvn spring-boot:run`.

  Application should be available under `localhost:8080` address.
  
<h4>Running transport emulator:</h4>
  Enter `py-transport-emulator` directory and execute `python transport-emulator.py --api "http://localhost:8080/api" --username admin --password admin`.
  
  <h4>Heroku deployment:</h4>
Instructions about deploying app on Heroku can be found on heroku-support feature branch.
