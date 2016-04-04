<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>

    <meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Dodawanie Kierowcy</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
<script src="/js/driver.js"></script>
<script src="/js/token.js"></script>
</head>

<body>

		<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/index">logistics_mgmt</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/drivers">Kierowcy</a></li>
					<li class="inactive"><a href="/vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="/clients">Klienci</a></li>
					<li class="inactive"><a href="/transports">Transporty</a></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="inactive"><a href="/users/">Panel
							Administracyjny</a></li>
					<li class="dropdown"><a href="/" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Zaloguj/Wyloguj <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/login">Zaloguj</a></li>
							<li><a href="/logout">Wyloguj</a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>

	<form name="add_driver_form" id="add_driver_form"
		modelAttribute="addDriverForm" data-toggle="validator"
		onsubmit="return addDriver();">
		<div class="form-group">
			<label for="firstName">Imie:</label> <input type="text"
				class="form-control" name="firstName" minlength="2" path="firstName"
				id="firstName" required="true" />
		</div>
		<div class="form-group">
			<label for="lastName">Nazwisko:</label> <input type="text"
				class="form-control" id="lastName" minlength="2" path="lastName"
				name="lastName" required="true" />
		</div>
		<div class="form-group">
			<label for="pesel">Pesel:</label> <input type="text"
				class="form-control" id="pesel" path="pesel" minlength="11"
				data-error="pesel musi miec dokładnie 11 znaków." maxlength="11"
				name="pesel" required="true" />
		</div>
		<div class="form-group">
			<label for="salary">Pensja:</label>
			<div class="input-group">
				<span class="input-group-addon">zł</span> <input type="number"
					class="form-control" id="salary" name="salary" path="salary"
					required="true" />
			</div>
			<div class="form-group">
				<label for="salaryBonus">Premia:</label>
				<div class="input-group">
					<span class="input-group-addon">zł</span> <input type="number"
						class="form-control" id="salaryBonus" name="salaryBonus"
						path="salaryBonus" />
				</div>
			</div>
			<div class="form-group">
				<label for="addressId">Adres:</label> <select name="addressId"
					id="addressId" path="addressId" class="form-control">
					<c:forEach var="address" items="${addresses}">
						<option value=${address.id}>${address.street}
							${address.houseNumber}, ${address.town}, ${address.country}</option>
					</c:forEach>
				</select>
			</div>
			<div class="checkbox">
				<label><input type="checkbox" checked="checked"
					path="available" id="available" name="available" />Dostepny</label>
			</div>

			<button type="submit" class="btn btn-success">Dodaj</button>
			<a class="btn btn-warning" href="/drivers">Anuluj</a>
	</form>
</body>
</html>