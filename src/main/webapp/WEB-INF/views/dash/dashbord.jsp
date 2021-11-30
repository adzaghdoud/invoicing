<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
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

  .card-counter.treso{
    background-color: #FFD700;
    color: #FFF;
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
.table td.fit, 
.table th.fit {
    white-space: nowrap;
    width: 1%;
}
</style>
    </head>
    <body>
<br/>
<div id="loading" style="display: none">
<div class="d-flex justify-content-center">
<br/>
<img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="25" height="25"  id="refresh_gif_send_tempo" >
</div>
</div>
<div class="container">
    <div class="row">
    <div class="col-md-4">
      <div class="card-counter primary">
        <i class="fas fa-euro-sign"></i>
        <span class="count-numbers">${ca} €</span>
        <span class="count-name">Chiffre d'affaire , au ${date_cloture}</span>
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
      <div class="card-counter treso">
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
        <table class="table table-borderless table-striped" id="tableprestations">
  <thead>
    <tr >
      <th scope="col">Num Facture</th>
      <th scope="col">Client</th>
      <th scope="col">Total TTC</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="prestation" items="${liste_prestations }" begin="0" end="4">
     <tr>
            <td>${prestation.numfacture}</td>
            <td >${prestation.client}</td>
            <td >${prestation.totalttc} <i class="fas fa-euro-sign"></i>   <a href="#" onclick="javascript:show_modal_prestation()"><span style="color:green"><i class="fas fa-info-circle"></i></span></a></td>
     </tr>          
   </c:forEach>
  </tbody>
</table>
      </div>
    </div>
  </div>
  
  <div class="col-sm-6">
    <div class="card">
      <div class="card-body">
      <jsp:useBean id="date" class="java.util.Date" />
      <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
        <h5 class="card-title"><span style="color:green;font-weight:bold"><i class="fas fa-chart-line"></i> Evolution chiffre d'affaire de l'année ${currentYear}</span></h5>
        <hr>
        <div id ="combodiv"></div>
      </div>
    </div>
  </div>
</div>
<script>
drawcharts();
</script>


   <div class="modal fade" id="Modalprestation" tabindex="-1"  role="dialog" aria-hidden="true">
   <div class="modal-dialog" role="document">
   <div class="modal-content">
   <div class="modal-header">
   <h6 class="modal-title"> <span style="color:green"><i class="fas fa-info-circle"></i>  Détail Prestation</span></h6>
   </div>
   <div class="modal-body">  
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Num facture</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="numfacture" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Nom Fichier</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="nomfacture" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Client</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="client" class="form-control" /> 
                                 </div>
                                 </div>
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Date</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="date" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                <label class="col-sm-4 col-form-label"><b>Article</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="article" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                  <div class="form-group row" >
                                <label class="col-sm-4 col-form-label"><b>Quantite</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="quantite" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                <label class="col-sm-4 col-form-label"><b>Montant HT <i class="fas fa-euro-sign"></i></b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="montantht" class="form-control" />
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                 <label class="col-sm-4 col-form-label"><b>Total TTC <i class="fas fa-euro-sign"></i></b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="totalttc" class="form-control" placeholder="0"/> 
                                 </div>
                                 </div>
                                
                                <div class="form-group row" >
                                <label class="col-sm-4 col-form-label"><b>Statut Paiement</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="statutpaiement" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                 <div class="form-group row" >
                                <label class="col-sm-4 col-form-label"><b>Mode Paiement</b></label>
                                 <div class="col-sm-8">
                                 <input type="text" id="modepaiement" class="form-control" /> 
                                 </div>
                                 </div>
                                 
                                                            
      
       </div>
       <div class="modal-footer">
       <button type="button" class="btn btn-primary" data-dismiss="modal">close</button>
       </div>
       </div>
       </div>
       </div>


 </body>
</html>