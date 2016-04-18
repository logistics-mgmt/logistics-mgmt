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
<title>Baza adresów</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script
	src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>

<script src="/js/address.js"></script>
<script src="/js/utils.js"></script>
<script src="/js/token.js"></script>
<script type="text/javascript">
$(function() {
	$(".delete-address-button").button().on(
			"click",
			function() {
				var $this = $(this).closest('tr').children();
				addressId = $this.eq(0).text();
				console.log("deleting");
				bootbox.confirm("Usunąć adres "  + addressId + "?",
						function(result) {
							if (result == true)
								deleteAddress(addressId);
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
<a href="/addresses/add" class="btn btn-success">Dodaj adres</a>
	<table class="table table-hover" id="addresses_table">
		<thead>
			<tr>
				<td>ID</td>
				<td>Miasto</td>
				<td>Ulica</td>
				<td>Numer budynku</td>
				<td>Kod pocztowy</td>
				<td>Państwo</td>
				<td>Edycja</td>
				<td>Usuwanie</td>
			</tr>
		</thead>
		<c:forEach var="address" items="${addresses}">
			<tr>
				<td>${address.id}</td>			
				<td>${address.town}</td>
				<td>${address.street}</td>
				<td>${address.houseNumber}</td>
				<td>${address.code}</td>
				<td>${address.country}</td>
				<td><a class="btn btn-primary edit-address-button"
					href="/addresses/edit/${address.id}">Edytuj</a></td>
				<td><button class="btn btn-danger delete-address-button"
						id="delete-button-${address.id}">Usuń</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
