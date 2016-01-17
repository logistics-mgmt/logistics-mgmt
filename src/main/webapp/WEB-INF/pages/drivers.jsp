<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Kierowcy</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- bootbox.js at 4.4.0 -->
<script
	src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>

<script src="/js/utils.js"></script>
<script src="/js/address.js"></script>
<script src="/js/driver.js"></script>

<script type="text/javascript">
	$(function() {
        $( ".clickable-row" ).click( function() {
                window.document.location = $(this).data("href");
        });

		$(".delete-driver-button").button().on(
				"click",
				function() {
					var $this = $(this).closest('tr').children();
					var driverFirstName = $this.eq(1).text();
					var driverLastName = $this.eq(2).text();
					var driverPesel = $this.eq(3).text();
					driverId = $this.eq(0).text();
					console.log("deleting");
					bootbox.confirm("Usunąć kierowcę " + driverFirstName + " "
							+ driverLastName + "(" + driverPesel + ")?",
							function(result) {
								if (result == true)
									deleteDriver(driverId);
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
					<li class="active"><a href="drivers">Kierowcy</a></li>
					<li class="inactive"><a href="vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="clients">Klienci</a></li>
					<li class="inactive"><a href="transports">Transporty</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>

	<a href="/drivers/add" class="btn btn-success">Dodaj kierowcę</a>

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

		<c:forEach var="driver" items="${drivers}">
			<tr class="clickable-row" data-href="/transports/${transport.id}">
				<td>${driver.id}</td>
				<td>${driver.firstName}</td>
				<td>${driver.lastName}</td>
				<td>${driver.PESEL}</td>
				<td>${driver.salary}</td>
				<td>${driver.salaryBonus}</td>
				<td style="display: none;">${driver.address.id}</td>
				<td>${driver.address.street}${driver.address.houseNumber},
					${driver.address.town}, ${driver.address.country}</td>

				<td><a class="btn btn-primary edit-driver-button"
					href="/drivers/edit/${driver.id}">Edytuj</a></td>
				<td><button class="btn btn-danger delete-driver-button"
						id="delete-button-${driver.id}">Usuń</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>