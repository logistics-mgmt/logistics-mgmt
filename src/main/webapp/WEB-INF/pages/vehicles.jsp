<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="com.jdbc.demo.domain.Vehicle"
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


<script>
$(function() {
    var dialog, form,

      brand = $( "#vehicle-brand" ),
      type = $( "#vehicle-type" ),
      engine = $( "#vehicle-engine" ),
      VIN = $( "#vehicle-VIN" ),
      mileage = $( "#vehicle-mileage" ),
      horsepower = $( "#vehicle-horsepower" ),
      allFields = $( [] ).add( brand ).add( type ).add( engine ).add( VIN ).add( mileage ),
      tips = $( ".validateTips" );
 
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
          min + " and " + max + "." );
        return false;
      } else {
        return true;
      }
    }
 
    function editVehicle() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
  /*
      valid = valid && checkLength( $( "#brand" ), "First Name", 3, 16 );
      valid = valid && checkLength( type, "Last Name", 6, 80 );
      valid = valid && checkLength( engine, "PESEL", 11, 11 );

      valid = valid && checkRegexp( VIN, /^([0-9])+$/, "Salary field must contain only digits." );
      valid = valid && checkRegexp( mileage, /^([0-9])+$/, "Salary bonus field must contain only digits." );
 */
      if ( valid ) {
        // dispatch AJAX POST request to vehicle servlet
        /*
        $( "#users tbody" ).append( "<tr>" +
          "<td>" + name.val() + "</td>" +
          "<td>" + email.val() + "</td>" +
          "<td>" + password.val() + "</td>" +
        "</tr>" );
        dialog.dialog( "close" );
        */
        $( "#dialog-form" ).submit();
        dialog.dialog( "close" );
      }
      console.log("POST vehicle");
      return valid;
    }
 
    dialog = $( "#dialog-div" ).dialog({
      autoOpen: false,
      height: 600,
      width: 650,
      modal: true,
      buttons: {
        "Edytuj": editVehicle,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        //form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 /*
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      addUser();
    });
 */
    $( ".edit-vehicle-button" ).button().on( "click", function() {
        var $this = $(this).closest('tr').children();
        var vehicleId = $this.eq(0).text();
        var vehicleBrand = $this.eq(1).text();
        var vehicleType = $this.eq(2).text();
        var vehicleEngine = $this.eq(3).text();
        var vehicleVin = $this.eq(4).text();
        var vehicleHorsepower = $this.eq(5).text();
        var vehicleMileage = $this.eq(6).text();
        console.log(vehicleId);
        console.log(vehicleBrand);
        console.log(vehicleType);
        console.log(vehicleEngine);
        console.log(vehicleVin);
        $( "#vehicle-id" ).val(vehicleId);
        $( "#vehicle-brand" ).val(vehicleBrand);
        $( "#vehicle-type" ).val(vehicleType);
        $( "#vehicle-mileage" ).val(vehicleMileage);
        $( "#vehicle-engine" ).val(vehicleEngine);
        $( "#vehicle-VIN" ).val(vehicleVin);
        $( "#vehicle-horsepower" ).val(vehicleHorsepower);
        dialog.dialog( "open" );
        
    });
  });
</script>

</head>

<body>

<div id="dialog-div" title="Edytuj kierowcę" class="modal-fade">
 
  <form id="dialog-form" action="vehicles" method="post">
    <fieldset>
      <input type="hidden" name="id" id="vehicle-id">
      <div class="form-group">
        <label for="vehicle-brand">Marka</label>
        <input type="text" name="brand" id="vehicle-brand" class="form-control">
      </div>
      <div class="form-group">
        <label for="vehicle-type">Typ</label>
        <input type="text" name="type" id="vehicle-type" class="form-control">
      </div>
      <div class="form-group">
        <label for="vehicle-engine">Silnik</label>
        <input type="text" name="engine" id="vehicle-engine" class="form-control">
      </div>
      <div class="form-group">
        <label for="vehicle-VIN">VIN</label>
        <input type="text" name="VIN" id="vehicle-VIN" class="form-control">
      </div>
      <div class="form-group">
        <label for="vehicle-horsepower">KM</label>
        <input type="text" name="horsepower" id="vehicle-horsepower" class="form-control">
      </div>
      <div class="form-group">
        <label for="vehicle-mileage">Przebieg</label>
        <input type="text" name="mileage" id="vehicle-mileage" class="form-control">
      </div>
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>


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
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

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

                <td><button class="btn btn-primary edit-vehicle-button">Edytuj</button></td>
              <td>
              <form name="delete_vehicle_form" action="vehicles" method="post">
                <input type="hidden" class="form-control" name="id" value="${vehicle.id}">
                <button type=submit name="delete" value="true" class="btn btn-danger">Usuń</button>
              </form>
            </td>
          </tr>
  </c:forEach>
  </table>

  <form name="add_vehicle_form" action="vehicles" method="post">
      <div class="form-group">
        <label for="brand">Marka:</label>
        <input type="text" class="form-control" name="brand" id="brand">
      </div>
      <div class="form-group">
        <label for="type">Typ:</label>
        <input type="text" class="form-control" id="type" name="type">
      </div>
      <div class="form-group">
        <label for="engine">Silnik:</label>
        <input type="text" class="form-control" id="engine" name="engine">
      </div>
      <div class="form-group">
        <label for="horsepower">KM:</label>
        <input type="text" class="form-control" id="horsepower" name="horsepower">
      </div>
      <div class="form-group">
        <label for="VIN">VIN:</label>
        <input type="text" class="form-control" id="VIN" name="VIN">
      </div>
      <div class="checkbox">
        <label><input type="checkbox" checked="checked" id="available" name="available">Dostepny</label>
      </div>

        <button type="submit" class="btn btn-default">Dodaj</button>
  </form>

</body>
</html>