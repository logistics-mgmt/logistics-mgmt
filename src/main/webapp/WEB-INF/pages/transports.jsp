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
<title>Transporty</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" crossorigin="anonymous"></script>
<!-- bootbox.js at 4.4.0 -->
<script src="https://rawgit.com/makeusabrew/bootbox/f3a04a57877cab071738de558581fbc91812dce9/bootbox.js"></script>
<script>
$(function() {

$( ".clickable-row" ).click( function() {
        window.document.location = $(this).data("href");
    });
});
</script>
</head>

<body>

<!-- Static navbar -->
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/">logistics_mgmt</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="transports">Transporty</a></li>
                <li class="inactive"><a href="drivers">Kierowcy</a></li>
                <li class="inactive"><a href="vehicles">Pojazdy</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div><!--/.container-fluid -->
        </nav>

  <a href="/transports/add" class="btn btn-success">Dodaj kierowcę</a>

  <table class="table table-hover" id="transports_table">
  <thead>
      <tr>
           <td>ID</td>
           <td>Adres załadunku</td>
           <td>Adres rozładunku</td>
           <td>Klient</td>
           <td>Wartość</td>
           <td>Edycja</td>
           <td>Usuwanie</td>
      </tr>
  </thead>

  <c:forEach var="transport" items="${transports}">
          <tr class="clickable-row" data-href="/transports/${transport.id}">
              <td>${transport.id}</td>
              <td>${transport.loadAddress}</td>
              <td>${transport.unloadAddress}</td>
              <td>${transport.client}</td>
              <td>${transport.value}</td>
                <td><a class="btn btn-primary edit-transport-button" href="/transport/edit/${transport.id}">Edytuj</a></td>
                <td><button class="btn btn-danger delete-transport-button" id="delete-button-${transport.id}">Usuń</button></td>
          </tr>
  </c:forEach>
  </table>
</body>
</html>