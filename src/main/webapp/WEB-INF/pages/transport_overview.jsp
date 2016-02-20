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
<meta name="_csrf" content="${_csrf.token}" />

<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Transport</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="http://maps.googleapis.com/maps/api/js?key=${api_key}"></script>
<link rel="stylesheet" type="text/css" href="/css/map.css">
<script src="/js/maps.js"></script>
<script src="/js/vehicle.js"></script>
<script src="/js/transport.js"></script>

<script>
$(document).ready(function() {
    var initialLocation = new google.maps.LatLng(52, 21);
    var map = initializeMapWithDirections(initialLocation, "${transport.loadAddress.town}",
                                          "${transport.unloadAddress.town}");


    getFreightTransport(${transport.id}).done(function(transport){
        for (var vehicle_id in transport.vehicles){
          var vehicle = transport.vehicles[vehicle_id];
          console.log(vehicle);
          var marker = initializeMarker(map, initialLocation);
          setInterval(function(){
          pollForLocation('vehicle', vehicle.id, map, marker);
          }, 5000);  
      };
    });
    

});
</script>

</head>

<body>

<!-- Static navbar -->
        <nav class="navbar navbar-inverse">
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
                <li class="active"><a href="/transports">Transporty</a></li>
                <li class="inactive"><a href="/drivers">Kierowcy</a></li>
                <li class="inactive"><a href="/vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

<div class="container-fluid">
  <div class="row">
        <c:choose>
                <c:when test="${transport.finished == true}">
                 <span class="label label-success center-block">
                     <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Zakończony
                 </span>
                </c:when>
                <c:otherwise>
                    <span class="label label-default center-block">
                      <span class="glyphicon glyphicon-road" aria-hidden="true"></span>W trakcie
                    </span>
                </c:otherwise>
          </c:choose>
        <div class="map-container">
          <div id="map-canvas" class="map-canvas"></div>
        </div>
  </div>

  <div class="row">

  <div class="col-md-4">
    <div class="row list-group">
      <div class="list-group-item">
          <h4 class="list-group-item-heading">Wartość:</h4>
          <h4 class="list-group-item-text">${transport.value}</h4>
      </div>
      <div class="list-group-item">
          <h4 class="list-group-item-heading">Data załadunku:</h4>
          <h4 class="list-group-item-text">${transport.loadDate}</h4>
      </div>
      <div class="list-group-item">
          <h4 class="list-group-item-heading">Data rozładunku:</h4>
          <h4 class="list-group-item-text">${transport.unloadDate}</h4>
      </div>
    </div>

  </div>

  <div class="col-md-4">
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

  </div>

    <div class="col-md-4">

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

    </div>
  

  </div>

</div>

</body>
</html>