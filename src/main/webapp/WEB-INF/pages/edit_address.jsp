<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />

<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Edycja Klienta</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
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
					<li class="inactive"><a href="/drivers">Kierowcy</a></li>
					<li class="inactive"><a href="/vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="/clients">Klienci</a></li>
					<li class="inactive"><a href="/transports">Transporty</a></li>
					<li class="active"><a href="/addresses">Baza adresów</a></li>

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

	<form name="edit_address_form" id="edit_address_form" class="form-horizontal"
		modelAttribute="editAddressForm" data-toggle="validator"
		onsubmit="return editAddress();">
		
			<input type="hidden" value="${address.id}" path="id" id="id" name="id"  style=""/>
		
			<div class="form-group">
			<label for="town" class="col-sm-2 control-label">Miasto:</label>
		       <div class="col-sm-8"> <input type="text"
					class="form-control" id="town" value="${address.town}" path="town" name="town"
					required="true" />
			</div>
			</div>
			<div class="form-group">
			<label for="street" class="col-sm-2 control-label">Ulica:</label>
		       <div class="col-sm-8"> <input type="text"
					class="form-control" id="street" value="${address.street}" path="street" name="street"
					required="true" />
			</div>
			</div>
			<div class="form-group">
				<label for="code" class="col-sm-2 control-label">Kod pocztowy:</label>
		       <div class="col-sm-8"> <input type="text"
					class="form-control" id="code" value="${address.code}" path="code" name="code"
					required="true" />
			</div>
			</div>
			<div class="form-group">
				<label for="houseNumber" class="col-sm-2 control-label">Numer budynku:</label>
		       <div class="col-sm-8"> <input type="text"
					class="form-control" id="houseNumber" value="${address.houseNumber}" path="houseNumber" name="houseNumber"
					required="true" />
			</div>
			</div>
			<div class="form-group">
			<label for="country" class="col-sm-2 control-label">Państwo:</label>
		       <div class="col-sm-8"> <input type="text"
					class="form-control" id="country" value="${address.country}" path="country" name="country"
					required="true" />
			</div>
			</div>

      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
		<button type="submit" class="btn btn-primary">Edytuj</button>
		<a class="btn btn-warning" href="/addresses">Anuluj</a>
		</div>
       </div>
	</form>
</body>
</html>
