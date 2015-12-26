<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Klienci</title>
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
					<li class="active"><a href="clients">Klienci</a></li>
					<li class="inactive"><a href="transports">Transporty</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>
	<table class="table table-hover" id="drivers_table">
		<thead>
			<tr>
				<td>ID</td>
				<td>Adres</td>
				<td>Nazwa</td>
				<td>NIP</td>
				<td>Edycja</td>
				<td>Usuwanie</td>
			</tr>
		</thead>
	</table>
</body>
</html>