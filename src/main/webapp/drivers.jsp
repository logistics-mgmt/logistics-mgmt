<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="com.jdbc.demo.domain.Driver"
import="com.jdbc.demo.domain.Address"
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Kierowcy</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" crossorigin="anonymous"></script>


<script>
$(function() {
    var dialog, form,

      firstName = $( "#driver-first-name" ),
      lastName = $( "#druver-last-name" ),
      pesel = $( "#driver-pesel" ),
      salary = $( "#driver-salary" ),
      salaryBonus = $( "#driver-salary-bonus" ),
      allFields = $( [] ).add( firstName ).add( lastName ).add( pesel ).add( salary ).add( salaryBonus ),
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
 
    function addDriver() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
  /*
      valid = valid && checkLength( $( "#firstName" ), "First Name", 3, 16 );
      valid = valid && checkLength( lastName, "Last Name", 6, 80 );
      valid = valid && checkLength( pesel, "PESEL", 11, 11 );

      valid = valid && checkRegexp( salary, /^([0-9])+$/, "Salary field must contain only digits." );
      valid = valid && checkRegexp( salaryBonus, /^([0-9])+$/, "Salary bonus field must contain only digits." );
 */
      if ( valid ) {
        // dispatch AJAX POST request to driver servlet
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
      console.log("POST driver");
      return valid;
    }
 
    dialog = $( "#dialog-div" ).dialog({
      autoOpen: false,
      height: 600,
      width: 650,
      modal: true,
      buttons: {
        "Edytuj": addDriver,
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
    $( ".edit-driver-button" ).button().on( "click", function() {
        var $this = $(this).closest('tr').children();
        var driverId = $this.eq(0).text();
        var driverFirstName = $this.eq(1).text();
        var driverLastName = $this.eq(2).text();
        var driverPesel = $this.eq(3).text();
        var driverSalary = $this.eq(4).text();
        var driverSalaryBonus = $this.eq(5).text();
        var driverAddressId = $this.eq(6).text();
        console.log(driverId);
        console.log(driverFirstName);
        console.log(driverLastName);
        console.log(driverPesel);
        console.log(driverAddressId);
        $( "#driver-id" ).val(driverId);
        $( "#driver-first-name" ).val(driverFirstName);
        $( "#driver-last-name" ).val(driverLastName);
        $( "#driver-pesel" ).val(driverPesel);
        $( "#driver-salary" ).val(driverSalary);
        $( "#driver-salary-bonus" ).val(driverSalaryBonus);
        $( "#driver-address-id" ).val(driverAddressId);
        dialog.dialog( "open" );
        
    });
  });
</script>

</head>

<body>
<jsp:useBean id="drivers" class="com.jdbc.demo.services.DriverEntityManager" scope="application" />
<jsp:useBean id="addresses" class="com.jdbc.demo.services.AddressEntityManager" scope="application" />

 <div id="dialog-div" title="Edytuj kierowcę" class="modal-fade">
 
  <form id="dialog-form" data-toggle="validator" action="driver" method="post">
    <fieldset>
      <input type="hidden" name="id" id="driver-id">
      <div class="form-group">
        <label for="driver-first-name">Imię</label>
        <input type="text" name="first-name" id="driver-first-name" class="form-control">
      </div>
      <div class="form-group">
        <label for="driver-last-name">Nazwisko</label>
        <input type="text" name="last-name" id="driver-last-name" class="form-control">
      </div>
      <div class="form-group">
        <label for="driver-pesel">PESEL</label>
        <input type="text" name="pesel" id="driver-pesel" class="form-control">
      </div>
      <div class="form-group">
        <label for="driver-salary">Pensja</label>
        <input type="number" name="salary" id="driver-salary" class="form-control">
      </div>
      <div class="form-group">
        <label for="driver-salary-bonus">Premia</label>
        <input type="number" name="salary-bonus" id="driver-salary-bonus" class="form-control">
      </div>
      <div class="form-group">
        <label for="driver-address-id">Adres</label>
        <select name="address-id" id="driver-address-id" class="form-control">
          <c:forEach var="address" items="${addresses.all}">
              <option value=${address.id}>${address.street} ${address.houseNumber}, ${address.town}, ${address.country}</option>
          </c:forEach>
        </select>
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
              <a class="navbar-brand" href="/jdbc_demo">JEE Servlet Demo</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="drivers.jsp">Kierowcy</a></li>
                <li class="inactive"><a href="vehicles.jsp">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <table class="table table-hover" id="drivers_table">
  <thead>
      <tr>
           <td>ID</td>
           <td>Imie</td>
           <td>Nazwisko</td>
           <td>PESEL</td>
           <td>Pensja</td>
           <td>Premia</td>
           <td>Adres</td>
           <td>Edycja</td>
           <td>Usuwanie</td>
      </tr>
  </thead>

  <c:forEach var="driver" items="${drivers.all}">
          <tr>
              <td>${driver.id}</td>
              <td>${driver.firstName}</td>
              <td>${driver.lastName}</td>
              <td>${driver.PESEL}</td>
              <td>${driver.salary}</td>
              <td>${driver.salaryBonus}</td>
              <td style="display:none;">${driver.address.id}</td>
              <td>${driver.address.street} ${driver.address.houseNumber}, ${driver.address.town}, ${driver.address.country}</td>

                <td><button class="btn btn-primary edit-driver-button">Edytuj</button></td>
              <td>
                <form name="delete_driver_form" action="driver" method="post">
                  <input type="hidden" class="form-control" name="id" value="${driver.id}">
                  <button type=submit name="delete" value="true" class="btn btn-danger">Usuń</button>
                </form>
              </td>
          </tr>
  </c:forEach>
  </table>

  <form name="add_driver_form" data-toggle="validator" action="driver" method="post">
      <div class="form-group">
        <label for="first-name">Imie:</label>
        <input type="text" class="form-control" name="first-name" id="first-name" required>
      </div>
      <div class="form-group">
        <label for="last-name">Nazwisko:</label>
        <input type="text" class="form-control" id="last-name" name="last-name" required>
      </div>
      <div class="form-group">
        <label for="pesel">Pesel:</label>
        <input type="text" class="form-control" id="pesel" data-minlength="11" data-error="PESEL musi miec dokładnie 11 znaków." data-maxlength="11" name="pesel" required>
      </div>
      <div class="form-group">
        <label for="address-id">Adres:</label>
        <select name="address-id" id="address-id" class="form-control">
          <c:forEach var="address" items="${addresses.all}">
              <option value=${address.id}>${address.street} ${address.houseNumber}, ${address.town}, ${address.country}</option>
          </c:forEach>
        </select>
      </div>
      <div class="checkbox">
        <label><input type="checkbox" checked="checked" id="available" name="available">Dostepny</label>
      </div>
      
        <button type="submit" class="btn btn-default">Dodaj</button>
  </form>
</body>
</html>