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
<title>Transport</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" crossorigin="anonymous"></script>
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
              <a class="navbar-brand" href="/">logistics_mgmt</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="/vehicles">Kierowcy</a></li>
                <li class="inactive"><a href="/vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>


<div class="container-fluid">
  <div class="col-sm-6">
    <iframe width="600" height="450" frameborder="0" style="border:0"
    src="https://www.google.com/maps/embed/v1/directions?origin=${transport.loadAddress.town}&destination=${transport.unloadAddress.town}&key=AIzaSyAAONK8KCLueoDJQhrkKzQgZsKy7F48LmM&mode=driving" allowfullscreen></iframe>
  </div>

  <div class="col-sm-6">
    <div class="row list-group">
      <div class="list-group-item">
        <h4 class="list-group-item-heading">Kierowcy:</h4>
      </div>
      <c:forEach var="driver" items="${drivers}">
        <a href="/drivers/${driver.id}" class="list-group-item">
            <h4 class="list-group-item-text">${driver.firstName} ${driver.lastName} (${driver.PESEL})</h4>
          </a>
      </c:forEach>
    </div>

    <div class="row list-group">
      <div class="list-group-item">
        <h4 class="list-group-item-heading">Pojazdy:</h4>
      </div>
      <c:forEach var="vehicle" items="${vehicles}">
        <a href="/vehicles/${vehicle.id}" class="list-group-item">
            <h4 class="list-group-item-text">${vehicle.brand} ${vehicle.model} (${vehicle.VIN})</h4>
        </a>
      </c:forEach>
    </div>

    <div class="row">

       <div class="col-sm-6">
        <div class="list-group-item">
          <h4 class="list-group-item-heading">Data załadunku:</h4>
          <h4 class="list-group-item-text">${transport.loadDate}</h4>
        </div>
      </div>

       <div class="col-sm-6">
        <div class="list-group-item">
          <h4 class="list-group-item-heading">Data rozładunku:</h4>
          <h4 class="list-group-item-text">${transport.unloadDate}</h4>
        </div>
      </div>

      <div class="col-sm-6">
        <div class="list-group-item">
          <h4 class="list-group-item-heading">Wartość:</h4>
          <h4 class="list-group-item-text">${transport.value}</h4>
        </div>
      </div>

    </div>

  </div>

</div>

</body>
</html>