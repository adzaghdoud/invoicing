<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
<script src="https://code.jquery.com/jquery-3.6.0.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script type="text/javascript"src="https://www.gstatic.com/charts/loader.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
<style>
.card-counter{
    box-shadow: 2px 2px 10px #DADADA;
    margin: 5px;
    padding: 20px 10px;
    background-color: #fff;
    height: 100px;
    border-radius: 5px;
    transition: .3s linear all;
  }

  .card-counter:hover{
    box-shadow: 4px 4px 20px #c1c1a7;
    transition: .3s linear all;
  }

  .card-counter.primary{
    background-color: #007bff;
    color: #FFF;
  }

  .card-counter.danger{
    background-color: #ef5350;
    color: #FFF;
  }  

  .card-counter.success{
    background-color: #66bb6a;
    color: #FFF;
  }  

  .card-counter.info{
    background-color: #26c6da;
    color: #FFF;
  }  

  .card-counter i{
    font-size: 5em;
    opacity: 0.2;
  }

  .card-counter .count-numbers{
    position: absolute;
    right: 35px;
    top: 20px;
    font-size: 32px;
    display: block;
  }

  .card-counter .count-name{
    position: absolute;
    right: 35px;
    top: 65px;
    font-style: italic;
    text-transform: capitalize;
    opacity: 0.5;
    display: block;
    font-size: 18px;
  }
.filterable {
    margin-top: 15px;
}
.filterable .panel-heading .pull-right {
    margin-top: -20px;
}
.filterable .filters input[disabled] {
    background-color: transparent;
    border: none;
    cursor: auto;
    box-shadow: none;
    padding: 0;
    height: auto;
}
.filterable .filters input[disabled]::-webkit-input-placeholder {
    color: #333;
}
.filterable .filters input[disabled]::-moz-placeholder {
    color: #333;
}
.filterable .filters input[disabled]:-ms-input-placeholder {
    color: #333;
}

</style>
    </head>
    <body>
  
<div class="container">
    <div class="row">
    <div class="col-md-4">
      <div class="card-counter primary">
        <i class="fas fa-euro-sign"></i>
        <span class="count-numbers">${ca}</span>
        <span class="count-name">Chiffre d'affaire</span>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card-counter danger">
      <i class="fas fa-money-check"></i>
        <span class="count-numbers">${nb_paiement_to_validate}</span>
        <span class="count-name">Paiement à valider</span>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card-counter success">
         <i class="fas fa-money-check"></i>
        <span class="count-numbers">${nb_paiement_validated}</span>
        <span class="count-name">Paiement validés</span>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card-counter info">
        <i class="fa fa-users"></i>
        <span class="count-numbers">${nb_clients}</span>
        <span class="count-name">Clients</span>
      </div>
    </div>
    
     <div class="col-md-4">
      <div class="card-counter hover">
        <i class="fas fa-money-bill-wave"></i>
        <span class="count-numbers">${tresorie} €</span>
        <span class="count-name">Trésorie ${bankname}</span>
      </div>
    </div>
  </div>
</div>


<div class="row">
  <div class="col-sm-6">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title"><span style="color:green;font-weight:bold"><i class="fas fa-receipt"></i> Factures récentes</span></h5>
        <table class="table table-borderless table-striped">
  <thead>
    <tr >
      <th scope="col">Num Facture</th>
      <th scope="col">date</th>
      <th scope="col">client</th>
      <th scope="col">Montant TTC</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="prestation" items="${liste_prestations }" begin="0" end="4">
     <tr>
            <td>${prestation.numfacture}</td>
            <c:set var = "date" value = "${prestation.date}"/>
            <c:set var = "datereformatted" value = "${fn:substring(date, 0, 19)}" />
            <td><c:out value="${datereformatted}"></c:out></td>
            <td>${prestation.client}</td>
            <td>${prestation.totalttc}</td>
     </tr>          
   </c:forEach>
  </tbody>
</table>
<div style="display: flex; justify-content: flex-end">
 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalprestations" >Détails factures</button>
</div>
      </div>
    </div>
  </div>
  
  <div class="col-sm-6">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title"><span style="color:green;font-weight:bold"><i class="fas fa-chart-line"></i> Evolution chiffre d'affaire</span></h5>
        <hr>
        <div id ="combodiv"></div>
      </div>
    </div>
  </div>
</div>
<script>
drawcharts();
</script>
 </body>
</html>