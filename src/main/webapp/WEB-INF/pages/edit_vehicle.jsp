<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Edycja Kierowcy</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" crossorigin="anonymous"></script>
<script src="/js/utils.js"></script>
<script src="/js/vehicle.js"></script>
</head>

<body>

<!-- Static navbar -->
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <div class="navbar-header">
              <button model="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/">JEE Servlet Demo</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="/vehicles">Kierowcy</a></li>
                <li class="inactive"><a href="/vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <form name="edit_vehicle_form" id="edit_vehicle_form" data-toggle="validator" onsubmit="return editVehicle();">
      <input type="hidden" value="${vehicle.id}" path="id" id="id" name="id"/>
      <div class="form-group">
        <label for="brand">Marka:</label>
        <input type="text" value="${vehicle.brand}" class="form-control" name="brand"  path="brand" id="brand" required="true" />
      </div>
      <div class="form-group">
        <label for="model">Model:</label>
        <input type="text" value="${vehicle.model}" class="form-control" id="model"  path="model" name="model" required="true" />
      </div>
      <div class="form-group">
        <label for="engine">Silnik:</label>
        <input type="text" value="${vehicle.engine}" class="form-control" id="engine"  path="engine" data-minlength="11" name="engine" />
      </div>
      <div class="form-group">
        <label for="vin">VIN:</label>
        <input type="text" value="${vehicle.VIN}" class="form-control" id="vin" name="vin" path="vin" />
       </div>
      <div class="form-group">
        <label for="mileage">Przebieg:</label>
        <input type="text" value="${vehicle.mileage}" class="form-control" id="mileage" name="mileage" path="mileage" />
      </div>
      <div class="form-group">
        <label for="horsepower">KM:</label>
        <input type="text" value="${vehicle.horsepower}" class="form-control" id="horsepower" name="horsepower" path="horsepower" />
      </div>
      <div class="form-group">
        <label for="productionDate">Data produkcji:</label>
        <input type="text" value="${vehicle.productionDate}" class="form-control" id="productionDate" name="productionDate" path="productionDate" />
      </div>
      <div class="checkbox">
        <label><input type="checkbox" checked="checked"  path="available" id="available" name="available"/>Dostepny</label>
      </div>
      
        <button model="submit" class="btn btn-primary">Edytuj</button>
        <a class="btn btn-warning" href="/vehicles">Anuluj</a>
  </form>
</body>
</html>