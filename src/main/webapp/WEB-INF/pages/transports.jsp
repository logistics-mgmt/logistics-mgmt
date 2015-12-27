<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>Transporty</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- Static navbar -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">JEE Servlet Demo</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="inactive"><a href="drivers">Kierowcy</a></li>
					<li class="inactive"><a href="vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="clients">Klienci</a></li>
					<li class="active"><a href="clients">Transporty</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>
	<table class="table table-hover" id="transports_table">
		<thead>
			<tr>
				<td>ID</td>
				<td>Adres początkowy</td>
				<td>Adres docelowy</td>
				<td>Klient</td>
				<td>Dystans</td>
				<td>Wartość transportu</td>
				<td>Data załadunku</td>
				<td>Data rozładunku</td>
				<td>Data płatnosci</td>
				<td>Stan</td>
				<td>Dodatkowe informacje</td>
				<td>Edycja</td>
				<td>Usuwanie</td>
			</tr>
		</thead>
		<c:forEach var="transport" items="${transports}">
			<tr>
				<td>${transport.id}</td>

				<td style="display: none;">${transport.loadAddress.id}</td>
				<td>${transport.loadAddress.town},
					${transport.loadAddress.street}
					${transport.loadAddress.houseNumber},
					${transport.loadAddress.code}, ${transport.loadAddress.country}</td>
				
				<td style="display: none;">${transport.unloadAddress.id}</td>
				<td>${transport.unloadAddress.town},
					${transport.unloadAddress.street}
					${transport.unloadAddress.houseNumber},
					${transport.unloadAddress.code}, ${transport.unloadAddress.country}</td>
				
				<td style="display: none;">${transport.client.id}</td>
				<td>${transport.client.name}${transport.client.NIP},
					${transport.client.bankAccountNumber},
					${transport.client.address.town},
					${transport.client.address.street}
					${transport.client.address.houseNumber},
					${transport.client.address.code},
					${transport.client.address.country}</td>
				<td>${transport.distance}</td>
				<td>${transport.value}</td>
				<td>${transport.loadDate}</td>
				<td>${transport.unloadDate}</td>
				<td>${transport.paymentDate}</td>
				<td>${transport.finished}</td>
				<td>${transport.notes}</td>


				<td><a class="btn btn-primary edit-transport-button"
					href="/transports/edit/${transport.id}">Edytuj</a></td>
				<td><button class="btn btn-danger delete-transport-button"
						id="delete-button-${transport.id}">Usuń</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>