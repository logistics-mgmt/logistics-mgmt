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


<script>
        function getFormData($form){
        var unindexed_array = $form.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function(n, i){
            indexed_array[n['name']] = n['value'];
        });

        return indexed_array;
        }

        function editDriver(){
            // send ajax
            console.log(getFormData($("#edit_driver_form")));
            $.ajax({
                url: '/api/drivers/${driver.id}',
                type : "POST",
                headers: { 
                          'Accept': 'application/json',
                          'Content-Type': 'application/json' 
                },
                dataType : 'json',
                data : getFormData($("#edit_driver_form")),
                success : function(result) {
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })

            return false;
        };

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
                <li class="active"><a href="drivers">Kierowcy</a></li>
                <li class="inactive"><a href="vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <form name="edit_driver_form" id="edit_driver_form" modelAttribute="editDriverForm" data-toggle="validator" onsubmit="return editDriver();">
      <div class="form-group">
        <label for="firstName">Imie:</label>
        <input type="text" value="${driver.firstName}" class="form-control" name="firstName"  path="firstName" id="firstName" required="true" />
      </div>
      <div class="form-group">
        <label for="lastName">Nazwisko:</label>
        <input type="text" value="${driver.lastName}" class="form-control" id="lastName"  path="lastName" name="lastName" required="true" />
      </div>
      <div class="form-group">
        <label for="PESEL">Pesel:</label>
        <input type="text" value="${driver.PESEL}" class="form-control" id="PESEL"  path="PESEL" data-minlength="11" data-error="PESEL musi miec dokładnie 11 znaków." data-maxlength="11" name="PESEL" required="true" />
      </div>
      <div class="form-group">
        <label for="salary">Pensja:</label>
        <input type="text" value="${driver.salary}" class="form-control" id="salary" name="salary"  path="salary" />
       </div>
      <div class="form-group">
        <label for="salaryBonus">Premia:</label>
        <input type="text" value="${driver.salaryBonus}" class="form-control" id="salaryBonus" name="salaryBonus"  path="salaryBonus" />
      </div>
      <div class="form-group">
        <label for="address-id">Adres:</label>
        <select name="address-id" id="address-id"  path="addressId" class="form-control">
          <c:forEach var="address" items="${addresses}">
              <option value=${address.id}>${address.street} ${address.houseNumber}, ${address.town}, ${address.country}</option>
          </c:forEach>
        </select>
      </div>
      <div class="checkbox">
        <label><input type="checkbox" checked="checked"  path="available" id="available" name="available" />Dostepny</label>
      </div>
      
        <button type="submit" class="btn btn-default">Edytuj</button>
  </form>
</body>
</html>