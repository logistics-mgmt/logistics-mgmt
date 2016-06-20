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
<script src="/js/client.js"></script>
<script src="/js/token.js"></script>
</head>

<body>

	<!-- Static navbar -->
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
					<li class="active"><a href="/clients">Klienci</a></li>
					<li class="inactive"><a href="/transports">Transporty</a></li>
					<li class="inactive"><a href="/addresses">Baza adresów</a></li>

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
		<form name="edit_client_form" id="edit_client_form" class="form-horizontal"
			modelAttribute="editClientForm" data-toggle="validator"
			onsubmit="return editClient();">
			<input type="hidden" value="${client.id}" path="id" id="id" name="id" style="" />
			<div class="form-group">
			<label for="name" class="col-sm-2 control-label">Name:</label>
			<div class="col-sm-8">
			 <input type="text"
					class="form-control" name="name" minlength="2" path="name"
					id="name" value="${client.name }" required="true" />
			</div>
			</div>
			<div class="form-group">
			<label for="nip" class="col-sm-2 control-label">NIP:</label>
			<div class="col-sm-8"> 
				<input type="text"
					class="form-control" id="nip" path="nip"
					data-error="NIP ma mieć 10 znaków." maxlenght="10"
					placeholder="Tylko dla polskich firm" name="nip"
					value="${client.NIP}" />
			</div>
			</div>

			<div class="form-group">
				<label for="bankAccountNumber" class="col-sm-2 control-label">Numer konta bankowego:</label>
				<div class="col-sm-8"> <input
					type="text" class="form-control" id="bankAccountNumber"
					path="bankAccountNumber" minlength="26"
					data-error="Numer konta bankowego ma mieć 26 znaków."
					maxlength="26" name="bankAccountNumber" required="true"
					value="${client.bankAccountNumber }" />
			</div>
			</div>

			<div class="form-group">
				<label for="addressId" class="col-sm-2 control-label">Adres:</label>
				<div class="col-sm-8">  <select name="addressId"
					id="addressId" path="addressId" class="form-control"}">
					<option value="${client.address.id}" selected="selected">${client.address.street}
							${client.address.houseNumber}, ${client.address.town}, ${client.address.country}</option>
					<c:forEach var="address" items="${addresses}">
						<option value=${address.id }>${address.street}
							${address.houseNumber}, ${address.town}, ${address.country}</option>
					</c:forEach>
				</select>
			</div>
			</div>
				      <div class="form-group">
			        <div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-primary">Edytuj</button>
			<a class="btn btn-warning" href="/clients">Anuluj</a>
			        </div>
       </div>
		</form>
</body>
</html>
