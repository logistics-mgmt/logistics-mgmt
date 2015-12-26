<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Pojazdy</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" crossorigin="anonymous"></script>
<!-- bootbox.js at 4.4.0 -->
<script src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/vehicle.js"></script>


<script>
$(function() {

$( ".delete-vehicle-button" ).button().on( "click", function() {
        var $this = $(this).closest('tr').children();
        var vehicleBrand = $this.eq(1).text();
        var vehicleModel = $this.eq(2).text();
        var vehicleVIN = $this.eq(4).text();
        vehicleId = $this.eq(0).text();
        console.log("deleting");
        bootbox.confirm("Usunąć pojazd "+vehicleBrand+" "+vehicleModel+"("+vehicleVIN+")?" , function(result){
          if(result == true)
            deleteVehicle(vehicleId);
        });
        
    });
});
</script>

</head>

<body>

<!-- Static navbar -->
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/">JEE Servlet Demo</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="inactive"><a href="drivers">Kierowcy</a></li>
                <li class="active"><a href="vehicles">Pojazdy</a></li>
                <li class="inactive"><a href="clients">Klienci</a></li>
                <li class="inactive"><a href="transports">Transporty</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <a href="/vehicles/add" class="btn btn-success">Dodaj pojazd</a>

  <table class="table table-hover" id="vehicles_table">
  <thead>
      <tr>
           <td>ID</td>
           <td>Marka</td>
           <td>Typ</td>
           <td>Silnik</td>
           <td>VIN</td>
           <td>KM</td>
           <td>Przebieg</td>
           <td>Edycja</td>
           <td>Usuwanie</td>
      </tr>
  </thead>

  <c:forEach var="vehicle" items="${vehicles}">
          <tr>
              <td>${vehicle.id}</td>
              <td>${vehicle.brand}</td>
              <td>${vehicle.model}</td>
              <td>${vehicle.engine}</td>
              <td>${vehicle.VIN}</td>
              <td>${vehicle.horsepower}</td>
              <td>${vehicle.mileage}</td>

              <td><a class="btn btn-primary edit-vehicle-button" href="/vehicles/edit/${vehicle.id}">Edytuj</a></td>
              <td><button class="btn btn-danger delete-vehicle-button" id="delete-button-${vehicle.id}">Usuń</button></td>
          </tr>
  </c:forEach>
  </table>

</body>
</html>