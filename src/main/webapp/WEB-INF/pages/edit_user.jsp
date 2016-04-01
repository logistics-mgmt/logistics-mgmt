<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Panel administracyjny</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


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

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="/users/">Panel
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

	<form:form method="POST" modelAttribute="user">

		<form:input type="hidden" path="id" id="id" />
		<div>
			<label for="firstName">Imię</label>
			<div>
				<form:input type="text" path="firstName" id="firstName"
					class="form-control" />
				<div class="has-error">
					<form:errors path="firstName" class="help-inline" />
				</div>
			</div>
		</div>

		<div>
			<label for="lastName">Nazwisko</label>
			<div>
				<form:input type="text" path="lastName" id="lastName"
					class="form-control" />
				<div class="has-error">
					<form:errors path="lastName" class="help-inline" />
				</div>
			</div>
		</div>

		<div>
			<label for="ssoId">SSO ID(login)</label>
			<div>
				<form:input type="text" path="ssoId" id="ssoId" class="form-control"
					disabled="true" />
				<div class="has-error">
					<form:errors path="ssoId" class="help-inline" />
				</div>
			</div>

			<div class="form-group">
				<label for="password">Hasło</label>
				<form:input type="password" path="password" id="password"
					class="form-control" />
				<div class="has-error">
					<form:errors path="password" class="help-inline" />
				</div>
			</div>

			<div class="form-group">
				<label for="userProfiles">Role</label>
				<div>
					<form:select path="userProfiles" items="${roles}" multiple="true"
						itemValue="id" itemLabel="type" class="form-control" />
					<div class="has-error">
						<form:errors path="userProfiles" class="help-inline" />
					</div>
				</div>
			</div>

			<div>
				<div class="form-group">
					<input type="submit" value="Zatwierdź" class="btn btn-success" /> <a
						href="<c:url value='/users/' />"
						class="btn btn-danger custom-width">Anuluj</a>
				</div>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>