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
<script src="/js/address.js"></script>
<script src="/js/driver.js"></script>
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
              <a class="navbar-brand" href="/">logistics_mgmt</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="/drivers">Kierowcy</a></li>
                <li class="inactive"><a href="/vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <form name="driver_disabled_form" id="driver_disabled_form" data-toggle="validator" disabled>
      <input type="hidden" value="${driver.id}" path="id" id="id" name="id"/>
      <div class="form-group">
        <label for="firstName">Imie:</label>
        <input type="text" value="${driver.firstName}" class="form-control" name="firstName"  path="firstName" id="firstName" disabled />
      </div>
      <div class="form-group">
        <label for="lastName">Nazwisko:</label>
        <input type="text" value="${driver.lastName}" class="form-control" id="lastName"  path="lastName" name="lastName" disabled />
      </div>
      <div class="form-group">
        <label for="pesel">Pesel:</label>
        <input type="text" value="${driver.PESEL}" class="form-control" id="pesel"  path="pesel" data-minlength="11" data-error="pesel musi miec dokładnie 11 znaków." data-maxlength="11" name="pesel" disabled />
      </div>
      <div class="form-group">
        <label for="salary">Pensja:</label>
        <input type="text" value="${driver.salary}" class="form-control" id="salary" name="salary"  path="salary" disabled />
       </div>
      <div class="form-group">
        <label for="salaryBonus">Premia:</label>
        <input type="text" value="${driver.salaryBonus}" class="form-control" id="salaryBonus" name="salaryBonus"  path="salaryBonus" disabled />
      </div>
      <div class="form-group">
        <label for="addressId">Adres:</label>
        <select name="addressId" id="addressId"  path="addressId" class="form-control" disabled>
          <c:forEach var="address" items="${addresses}">
              <option value=${address.id}>${address.street} ${address.houseNumber}, ${address.town}, ${address.country}</option>
          </c:forEach>
        </select>
      </div>
      <div class="checkbox">
        <label><input type="checkbox" checked="checked"  path="available" id="available" name="available" disabled/>Dostepny</label>
      </div>
      
        <a class="btn btn-success" href="/drivers">Przejdź do listy kierowców</a>
  </form>
</body>
</html>