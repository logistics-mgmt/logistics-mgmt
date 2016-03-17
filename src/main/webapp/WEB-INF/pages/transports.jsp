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
<title>Transporty</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- bootbox.js at 4.4.0 -->
<script
	src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
<script src="/js/client.js"></script>
<script src="/js/token.js"></script>
<script src="/js/transport.js"></script>
<script type="text/javascript">
	$(function() {

		$(".delete-transport-button").button().on(
				"click",
				function() {
					var $this = $(this).closest('tr').children();
					var loadAdress = $this.eq(1).text();
					var unloadAdress = $this.eq(2).text();
					var value = $this.eq(4).text();
					transportId = $this.eq(0).text();
					console.log("deleting");
					bootbox.confirm("Usunąć transport z " + loadAdress + " do "
							+ unloadAdress + " o wartości(" + value + ")?",
							function(result) {
								if (result == true)
									deleteFreightTransport(transportId);
							});

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
				<a class="navbar-brand" href="/">logistics_mgmt</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="inactive"><a href="drivers">Kierowcy</a></li>
					<li class="inactive"><a href="vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="clients">Klienci</a></li>
					<li class="active"><a href="transports">Transporty</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>

	<a href="/transports/add" class="btn btn-success">Dodaj transport</a>

	<table class="table table-hover" id="transports_table">
		<thead>
			<tr>
				<td>ID</td>
				<td>Adres załadunku</td>
				<td>Adres rozładunku</td>
				<td>Klient</td>
				<td>Wartość</td>
				<td>Szczegóły</td>
				<td>Edycja</td>
				<td>Usuwanie</td>
			</tr>
		</thead>

		<c:forEach var="transport" items="${transports}">
			<tr>
				<td>${transport.id}</td>
				<td>${transport.loadAddress}</td>
				<td>${transport.unloadAddress}</td>
				<td>${transport.client}</td>
				<td>${transport.value}</td>
				<td><a class="btn btn-default details-transport-button" href="/transports/${transport.id}"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button></td>
				<td><a class="btn btn-primary edit-transport-button"
					href="/transports/edit/${transport.id}">Edytuj</a></td>
				<td><button class="btn btn-danger delete-transport-button"
						id="delete-button-${transport.id}">Usuń</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>