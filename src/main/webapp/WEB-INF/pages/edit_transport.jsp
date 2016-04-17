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
<title>Edycja Transportu</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>

<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
<script src="/js/client.js"></script>
<script src="/js/transport.js"></script>
<script src="/js/token.js"></script>
<script>
	$(function() {
		$("#loadDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
<script>
	$(function() {
		$("#unloadDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
</script>


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
					<li class="inactive"><a href="/clients">Klienci</a></li>
					<li class="active"><a href="/transports">Transporty</a></li>
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

	<form name="edit_transport_form" id="edit_transport_form"
		modelAttribute="editFreightTransportForm" data-toggle="validator"
		onsubmit="return editFreightTransport();">

		<input type="hidden" value="${transport.id}" path="id" id="id"
			name="id" />

		<div class="form-group">
			<label for="loadAddressId">Adres załadunku:</label> <select
				name="loadAddressId" id="loadAddressId" path="loadAddressId"
				class="form-control">
				<option value="${transport.loadAddress.id}" selected="selected">${transport.loadAddress.street}
					${transport.loadAddress.houseNumber},
					${transport.loadAddress.town}, ${transport.loadAddress.country}</option>
				<c:forEach var="loadAddress" items="${load_address}">
					<option value=${loadAddress.id}>${loadAddress.street}
						${loadAddress.houseNumber}, ${loadAddress.town},
						${loadAddress.country}</option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group">
			<label for="unloadAddressId">Adres rozładunku:</label> <select
				name="unloadAddressId" id="unloadAddressId" path="unloadAddressId"
				class="form-control">
				<option value="${transport.unloadAddress.id}" selected="selected">${transport.unloadAddress.street}
					${transport.unloadAddress.houseNumber},
					${transport.unloadAddress.town}, ${transport.unloadAddress.country}</option>
				<c:forEach var="unloadAddress" items="${unload_address}">
					<option value=${unloadAddress.id}>${unloadAddress.street}
						${unloadAddress.houseNumber}, ${unloadAddress.town},
						${unloadAddress.country}</option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group">
			<label for="clientId">Klient:</label> <select name="clientId"
				id="clientId" path="clientId" class="form-control">
				<c:forEach var="client" items="${clients}">
					<option value=${client.id}>${client.name}</option>
				</c:forEach>
			</select>
		</div>



		<div class="form-group">
			<label for="value">Wartość:</label>
			<div class="input-group">
				<span class="input-group-addon">zł</span> <input type="number"
					step="any" class="form-control" id="value" name="value"
					path="value" value="${transport.value}" />
			</div>
		</div>

		<div class="form-group">
			<label for="value">Data załadunku:</label>
			<div class="input-group">
				<input type="text" class="form-control" id="loadDate"
					name="loadDate" path="loadDate" value="${transport.loadDate}" />
			</div>
		</div>
		<div class="form-group">
			<label for="value">Data rozładunku:</label>
			<div class="input-group">
				<input type="text" class="form-control" id="unloadDate"
					name="unloadDate" path="unloadDate" value="${transport.unloadDate}" />
			</div>
		</div>
		<input type="hidden" class="form-control" id="distance"
			name="distance" path="distance" value="1" />
		<button type="submit" class="btn btn-success">Edytuj</button>
		<a class="btn btn-warning" href="/transports">Anuluj</a>
	</form>
</body>
</html>