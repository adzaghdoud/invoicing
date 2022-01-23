<%@ page pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">		  
		
<style>
body {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #555;
  background: #ececec;
  margin-top:20px;
}
.project-list > tbody > tr > td {
  padding: 12px 8px;
}

.project-list > tbody > tr > td .avatar {
  width: 22px;
  border: 1px solid #CCC;
}
</style>
</head>
<body>
 <div class="card card-outline-secondary">
                        <div class="card-header">
                            <h5 class="mb-0">Calcul TVA</h5>
                            
                        </div>
                        <div class="card-body">
                              <div class="form-group row">
                              <label class="col-sm-2 col-form-label"><b>Date début</b></label>
                              <div class="col-sm-4">
                              <div class="datepicker date input-group">
                              <input type="date" placeholder="Choisir une date" class="form-control" id="datedeb">
                              <div class="input-group-append"><span class="input-group-text px-4"><i class="fa fa-calendar"></i></span></div>
                              </div>
                              </div>
                              </div>
                              
                               <div class="form-group row">
                              <label class="col-sm-2 col-form-label"><b>Date Fin</b></label>
                              <div class="col-sm-4">
                              <div class="datepicker date input-group">
                              <input type="date" placeholder="Choisir une date" class="form-control" id="datefin">
                              <div class="input-group-append"><span class="input-group-text px-4"><i class="fa fa-calendar"></i></span></div>
                              </div>
                              </div>
                              </div>
                              
                              <div class="form-group row" >
                              <label class="col-sm-2 col-form-label"><b>Total TVA Encaissée €</b></label>
                              <div class="col-sm-4">
                              <input type="text"  id ="totaltva" class="form-control"/> 
                              </div>
                              </div>
                              <div class="form-group row" >
                              <label class="col-sm-2 col-form-label"><b>Total TVA dû €</b></label>
                              <div class="col-sm-4">
                              <input type="text"  id ="totaltvadu" class="form-control"/> 
                              </div>
                              </div>
                              
                              
                              
                              
                <div class="form-group row">
                <div class="col-sm-3">      
			    <button type="button" class="btn btn-success" onclick="totaltva()"><i class="fas fa-search"></i> Chercher</button>
			   <button type="button" id="reset"  class="btn btn-primary" onclick="javascript:document.getElementById('datedeb').value = '';document.getElementById('datefin').value = '';document.getElementById('totaltva').value = '' ;document.getElementById('totaltvadu').value = '';$('#tbobytransaction').empty();">Reset</button>
			   </div>
				</div>
				
				
				
				
                        
                        </div>
                        </div>
                        
<br/>
<div class="container bootstrap snippets bootdey">
    <div class="table-responsive">
    	<!-- PROJECT TABLE -->
    	<table class="table colored-header datatable project-list">
    		<thead>
    			<tr>
    				<th>label</th>
    				<th>Reference</th>
    				<th>settled_at</th>
    				<th>Side</th>
    				<th>Operation Type</th>
    				<th>Amout TTC</th>
    	            <th>Amout HT</th>
    			</tr>
    		</thead>
    		<tbody id="tbobytransaction"> 
    			
    		</tbody>
    	</table>
    	<!-- END PROJECT TABLE -->
    </div>
</div>
</body>
</html>