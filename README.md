# jdbc_demo

<h2>How to run:</h2>
  Create local PostgreSQL database(DBScript.sql is located in main folder of repository):
  1. `createdb -U postgres logistics`
  2. `psql -U postgres -d logistics -a -f DBScript.sql`
  
  Fill `logistics-mgmt/src/main/resources/jdbc.properties.default` file with correct credentials to your local PostgreSQL database and copy/rename it to `jdbc.properties`.

  Compile and start embedded Tomcat server with: `mvn spring-boot:run`.

  Application should be available under `localhost:8080` address.
