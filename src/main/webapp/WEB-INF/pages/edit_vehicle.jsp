<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
   <meta name="_csrf" content="${_csrf.token}"/>

    <meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Edycja Pojazdu</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>

<script src="/js/utils.js"></script>
<script src="/js/vehicle.js"></script>
<script src="/js/token.js"></script>
<script>
	$(function() {
		$("#productionDate").datepicker({
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
					<li class="active"><a href="/vehicles">Pojazdy</a></li>
					<li class="inactive"><a href="/clients">Klienci</a></li>
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

  <form name="edit_vehicle_form" id="edit_vehicle_form" class="form-horizontal" data-toggle="validator" onsubmit="return editVehicle();">
      <input type="hidden" value="${vehicle.id}" path="id" id="id" name="id" style=""/>
      <div class="form-group">
        <label for="brand" class="col-sm-2 control-label">Marka:</label>
        <div class="col-sm-8">
            <input type="text" value="${vehicle.brand}" class="form-control" name="brand"  path="brand" id="brand" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="model" class="col-sm-2 control-label">Model:</label>
        <div class="col-sm-8">
            <input type="text" value="${vehicle.model}" class="form-control" id="model"  path="model" name="model" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="maxPayload" class="col-sm-2 control-label">Ładowność:</label>
        <div class="col-sm-8">
            <input type="number" value="${vehicle.maxPayload}" class="form-control" id="maxPayload"  path="maxPayload" name="maxPayload" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="engine" class="col-sm-2 control-label">Silnik:</label>
        <div class="col-sm-8">
            <input type="number" value="${vehicle.engine}" class="form-control" id="engine"  path="engine" name="engine" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="vin" class="col-sm-2 control-label">VIN:</label>
        <div class="col-sm-8">
            <input type="text" value="${vehicle.VIN}" class="form-control" id="vin" name="vin" path="vin"
            minlength="17" maxlength="17" data-error="VIN musi miec dokładnie 17 znaków." required="true"/>
        </div>
       </div>
      <div class="form-group">
        <label for="mileage" class="col-sm-2 control-label">Przebieg:</label>
        <div class="col-sm-8">
            <input type="number" value="${vehicle.mileage}" class="form-control" id="mileage" name="mileage" path="mileage" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="horsepower" class="col-sm-2 control-label">KM:</label>
        <div class="col-sm-8">
            <input type="number" value="${vehicle.horsepower}" class="form-control" id="horsepower" name="horsepower" path="horsepower" required="true" />
        </div>
      </div>
      <div class="form-group">
        <label for="productionDate" class="col-sm-2 control-label">Data produkcji:</label>
        <div class="col-sm-8">
            <input type="date" value="${vehicle.productionDate}" class="form-control" id="productionDate" name="productionDate" path="productionDate" required="true" />
        </div
      </div>
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
            <label><input type="checkbox" checked="checked"  path="available" id="available" name="available"/>Dostepny</label>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button model="submit" class="btn btn-primary">Edytuj</button>
            <a class="btn btn-warning" href="/vehicles">Anuluj</a>
        </div>
      </div>
  </form>
</body>
</html>