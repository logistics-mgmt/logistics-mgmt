<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />

<meta name="_csrf_header" content="${_csrf.headerName}" />
<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>Klienci</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script
	src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>

<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
<script src="/js/client.js"></script>
<script src="/js/token.js"></script>

<script type="text/javascript">
$(function() {
	$(".delete-client-button").button().on(
			"click",
			function() {
				var $this = $(this).closest('tr').children();
				var clientName = $this.eq(3).text();
				clientId = $this.eq(0).text();
				console.log("deleting");
				bootbox.confirm("Usunąć klienta "  + clientName + "?",
						function(result) {
							if (result == true)
								deleteClient(clientId);
						});
			});
});
</script>
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

	<a href="/clients/add" class="btn btn-success">Dodaj klienta</a>
	<table class="table table-hover" id="clients_table">
		<thead>
			<tr>
				<td>ID</td>
				<td>Adres</td>
				<td>Nazwa</td>
				<td>NIP</td>
				<td>Numer konta bankowego</td>
				<td>Edycja</td>
				<td>Usuwanie</td>
			</tr>
		</thead>
		<c:forEach var="client" items="${clients}">
			<tr>
				<td>${client.id}</td>
				<td style="display: none;">${client.address.id}</td>
				<td>${client.address.street} ${client.address.houseNumber},
					${client.address.town}, ${client.address.country}</td>
				<td>${client.name}</td>
				<td>${client.NIP}</td>
				<td>${client.bankAccountNumber}</td>



				<td><a class="btn btn-primary edit-client-button"
					href="/clients/edit/${client.id}">Edytuj</a></td>
				<td><button class="btn btn-danger delete-client-button"
						id="delete-button-${client.id}">Usuń</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>