# logistics_mgmt

<h2>How to run locally:</h2>

<h3>Prerequisites:</h3>
  We assume that you are using Unix like OS.
  1. Java 1.8
  2. PostgreSQL 9.4.x
  3. Maven 3.3.3
  4. Google Maps API keys (https://developers.google.com/maps/documentation/javascript/get-api-key) with access to following services:
     * Directions
     * Distance Matrix
     * Geocoding
     * Javascript API
  

<h3>Local setup:</h3>
  * Create local PostgreSQL database (DBScript.sql file is located in main folder of the repository):
	1. `createdb -U postgres logistics`
  	2. `psql -U postgres -d logistics -a -f DBScript.sql`
 
  * Set following environment variables:
   * `DATABASE_URL` - connection string for PostgreSQL server in following format: `postgres://<username>:<password>@<host>:<port>/<database_name>` (e.g. for PostgreSQL running on localhost on port 5432, assuming that you have user `postgres` with password `postgres` use `postgres://postgres:postgres@localhost:5432/logistics` as DATABASE_URL)
   * `GOOGLE_MAPS_SERVER_KEY` - Google Maps API Server key
   * `GOOGLE_MAPS_BROWSER_KEY` - Google Maps API Browser key 


If you are using Linux, run `source configure_env_variables.sh` and enter `DATABASE_URL`, `GOOGLE_MAPS_BROWSER_KEY`, `GOOGLE_MAPS_SERVER_KEY` parameters.

 If you are using Windows, set mentioned environment variables by running following commands in command prompt:
```
SET DATABASE_URL=<database url>
SET GOOGLE_MAPS_BROWSER_KEY=<google maps browser key> 
SET GOOGLE_MAPS_SERVER_KEY=<google maps server key>
```

  Compile and start embedded Tomcat server with: `mvn spring-boot:run`.

  Application should be available under `localhost:8080` address.
  
<h4>Running transport emulator:</h4>
  Enter `py-transport-emulator` directory and execute `python transport-emulator.py --api "http://localhost:8080/api" --username admin --password admin`.
  
<h4>Deploying on Heroku</h4>
  Uncomment `<property name="connectionProperties" value="ssl=true;sslfactory=org.postgresql.ssl.NonValidatingFactory;"/>` entry in `src/main/resources/beans.xml` file in order to avoid problems with connecting to Heroku PostgreSQL instance.
  Refer to official Heroku guide: https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
  After successful deployment, run `cat DBScript.sql | heroku pg:psql` to seed Heroku PostgreSQL database.
