<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/b16c365929.js"></script>
<style>
.loader{
    width: 70px;
    height: 70px;
    margin: 40px auto;
}
.loader p{
    font-size: 16px;
    color: #777;
}
.loader .loader-inner{
    display: inline-block;
    width: 15px;
    border-radius: 15px;
    background: #74d2ba;
}
.loader .loader-inner:nth-last-child(1){
    -webkit-animation: loading 1.5s 1s infinite;
    animation: loading 1.5s 1s infinite;
}
.loader .loader-inner:nth-last-child(2){
    -webkit-animation: loading 1.5s .5s infinite;
    animation: loading 1.5s .5s infinite;
}
.loader .loader-inner:nth-last-child(3){
    -webkit-animation: loading 1.5s 0s infinite;
    animation: loading 1.5s 0s infinite;
}
@-webkit-keyframes loading{
    0%{
        height: 15px;
    }
    50%{
        height: 35px;
    }
    100%{
        height: 15px;
    }
}
@keyframes loading{
    0%{
        height: 15px;
    }
    50%{
        height: 35px;
    }
    100%{
        height: 15px;
    }
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
card-counter{
    box-shadow: 2px 2px 10px #DADADA;
    margin: 5px;
    padding: 20px 10px;
    background-color: #fff;
    height: 100px;
    border-radius: 5px;
    transition: .3s linear all;
  }

  .card-counter:hover{
    box-shadow: 4px 4px 20px #DADADA;
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
  .btn-default.btn-on.active{background-color: #5BB75B;color: white;}
.btn-default.btn-off.active{background-color: #DA4F49;color: white;}
  #cover-spin {
    position:fixed;
    width:100%;
    left:0;right:0;top:0;bottom:0;
    background-color: rgba(255,255,255,0.1);
    z-index:9999;
    display:none;
}

@-webkit-keyframes spin {
	from {-webkit-transform:rotate(0deg);}
	to {-webkit-transform:rotate(360deg);}
}

@keyframes spin {
	from {transform:rotate(0deg);}
	to {transform:rotate(360deg);}
}

#cover-spin::after {
    content:'';
    display:block;
    position:absolute;
    left:48%;top:40%;
    width:40px;height:40px;
    border-style:solid;
    border-color:black;
    border-top-color:transparent;
    border-width: 4px;
    border-radius:50%;
    -webkit-animation: spin .8s linear infinite;
    animation: spin .8s linear infinite;
}
</style>
<body>
<div id="cover-spin"></div>
<div class="container" id="container_transaction">
    
    <div class="row">
    <h3><i class="fas fa-university"></i> Transactions Bancaires</h3>
      <div class="col-md-4">
      <div class="card-counter danger">
     <i class="fas fa-sign-out-alt"></i>
     <span  style="font-size:medium ;" >Sorties du mois</span>
     <span class="count-numbers" >-${amount_out} €</span>
      </div>
    </div>
        <div class="col-md-4">
      <div class="card-counter success">
         <i class="fas fa-sign-in-alt"></i>
         <span  style="font-size:medium ;" >Entrées du mois</span>
        <span class="count-numbers">+${amount_in} €</span>
      </div>
    </div>
    </div>
    <hr>
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fas fa-chart-line"></i> Transactions Bancaires    <button class="btn btn-primary" onclick="refresh_transactions()"><img src="${pageContext.request.contextPath}/resources/images/icon_refresh.gif" width="20" height="20" style="display: none" id="refresh" > Refresh</button>
                </h3>
                <div class="pull-right">
                    <i><span style="color:black"> <i class="fas fa-sync"></i> Last refresh : ${last_refresh_transaction}</span></i>  <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
                </div>
             
                
            </div>
            <table  class="table table-striped" id="table_transactions">
                <thead>
                    <tr class="filters">
                        <th  width="auto"><input type="text" class="form-control" placeholder="Amount" disabled></th>
                        <th  width="auto"><input type="text" class="form-control" placeholder="Side" disabled></th>
                        <th  width="auto"><input type="text" class="form-control" placeholder="Status" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Type" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Currency" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Label" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Settled at" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Updated_at" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Reference" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="TVA" disabled></th>
						<th  width="auto"><input type="text" class="form-control" placeholder="Action" disabled></th>
                    </tr>
                </thead>
                <tbody>
                              <c:forEach var="liste" items="${ List_transactions }">
            
            <tr>
            <c:if test = "${liste.side == 'credit'}"> 
            <td width="auto">${liste.amount}</td>
            <td width="auto"><span class="label label-success">${liste.side}</span></td>
            <td width="auto">${liste.status}</td>
            <td width="auto">${liste.operation_type}</td>
            <td width="auto">${liste.currency}</td>
            <td width="auto">${liste.label}</td>
            <td width="auto">${liste.settled_at}</td>
            <td width="auto">${liste.updated_at}</td>
            <td width="auto">${liste.reference}</td>
            <td width="auto"> 
              <c:if test = "${liste.amount_HT >0}"> 
              <div class="btn-group" id="status" data-toggle="buttons">
              <label class="btn btn-default btn-on btn-xs active" onclick="javascript:addtva()"> 
              <input type="radio" >Oui</label>
              <label class="btn btn-default btn-off btn-xs " onclick="javascript:reducetva()">
              <input type="radio" >Non</label>
                  <br/>
              <small><i><b>HT ${liste.amount_HT} €</b></i></small>
              <br/>
              <small><i><b>TVA 20%</b></i></small>
            </div>
            </c:if>
            
             <c:if test = "${liste.amount_HT == 0}"> 
              <div class="btn-group" id="status" data-toggle="buttons">
              <label class="btn btn-default btn-on btn-xs " onclick="javascript:addtva()"> 
              <input type="radio" >Oui</label>
              <label class="btn btn-default btn-off btn-xs active " onclick="javascript:reducetva()">
              <input type="radio" >Non</label>
            </div>
            </c:if>
            
            
            
                    
                   
</td>
           </c:if>
           
             <c:if test = "${liste.side == 'debit'}"> 
            <td>${liste.amount}</td>
            <td><span class="label label-danger">${liste.side}</span></td>
            <td>${liste.status}</td>
            <td>${liste.operation_type}</td>
            <td>${liste.currency}</td>
            <td>${liste.label}</td>
            <td>${liste.settled_at}</td>
            <td>${liste.updated_at}</td>
            <td>${liste.reference}</td>
             <td width="auto"> 
              <c:if test = "${liste.amount_HT >0}"> 
              <div class="btn-group" id="status" data-toggle="buttons">
              <label class="btn btn-default btn-on btn-xs active" onclick="javascript:addtva()"> 
              <input type="radio" >Oui</label>
              <label class="btn btn-default btn-off btn-xs " onclick="javascript:reducetva()">
              <input type="radio" >Non</label>
              <br/>
              <small><i><b>HT ${liste.amount_HT} €</b></i></small>
              <br/>
              <small><i><b>TVA 20%</b></i></small>
            </div>
            </c:if>
            
             <c:if test = "${liste.amount_HT == 0}"> 
              <div class="btn-group" id="status" data-toggle="buttons">
              <label class="btn btn-default btn-on btn-xs " onclick="addtva()"> 
              <input type="radio" >Oui</label>
              <label class="btn btn-default btn-off btn-xs active " onclick="javascript:reducetva()">
              <input type="radio" >Non</label>
              <br/>
              <c:if test = "${empty liste.manual_validation}"> 
              <span style='color: orange;'><i class="fas fa-star"></i> New</span>
             </c:if>
            </div>
            </c:if>              
            </td>
           </c:if>  
           <c:if test = "${liste.side == 'debit'}"> 
           <td><a href="#" onclick="UpdateTransaction()"><span style='color: green;'><i class="fas fa-edit"></i></span></a></td>
           </c:if>
           </tr>
            
            
            
 
            
            
            
            </c:forEach>
                </tbody>
            </table>
            
        </div>
       
            
  <div class="modal fade" id="modalupdatetransaction" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"> <span style="color:green"><i class="fas fa-envelope-open-text"></i> Update Transaction</span></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="form-group row">
      <label class="col-sm-4 col-form-label"><b>Justificatif</b></label>
      <div class="col-sm-8">
      
     
      <input type="file"  id ="proof"  class="form-control"  multiple>  
    
      </div>
      </div>                                
      </div>
      <div class="alert alert-success" role="success" style="display: none;" id="divconfirmok">
      <i class="far fa-check-circle"></i> <span id="msgokupload"></span>
      </div>
      
      <div class="alert alert-danger" role="error" style="display: none;" id="divconfirmko">
      <i class="fas fa-exclamation-triangle"></i> <span id="msgkoupload"></span>
      </div>
      
      <div class="modal-footer">
      <button type="button" id="button_submit" class="btn btn-success" onclick="UpdateTransaction()">Upload</button>
      <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="javascript:$('#container_transaction').load('liste_transactions_bank');">Close</button>
      </div>  
  
      </div>
      </div>
      </div>  
            

    </div>
       
    </div>
    

 
  
  
  
  
  
  <div class="modal fade" id="Modalnotify" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodal"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       <span id="msgModalnotify"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div> 
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 	
    <script>
    /*
    Please consider that the JS part isn't production ready at all, I just code it to show the concept of merging filters and titles together !
    */
    $(document).ready(function(){
        $('.filterable .btn-filter').click(function(){
            var $panel = $(this).parents('.filterable'),
            $filters = $panel.find('.filters input'),
            $tbody = $panel.find('.table tbody');
            if ($filters.prop('disabled') == true) {
                $filters.prop('disabled', false);
                $filters.first().focus();
            } else {
                $filters.val('').prop('disabled', true);
                $tbody.find('.no-result').remove();
                $tbody.find('tr').show();
            }
        });

        $('.filterable .filters input').keyup(function(e){
            /* Ignore tab key */
            var code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            var $input = $(this),
            inputContent = $input.val().toLowerCase(),
            $panel = $input.parents('.filterable'),
            column = $panel.find('.filters th').index($input.parents('th')),
            $table = $panel.find('.table'),
            $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            var $filteredRows = $rows.filter(function(){
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            /* Clean previous no-result if exist */
            $table.find('tbody .no-result').remove();
            /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
            $rows.show();
            $filteredRows.hide();
            /* Prepend no-result row if all rows are filtered */
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
            }
        });
    });
    </script>
</body>
</html>