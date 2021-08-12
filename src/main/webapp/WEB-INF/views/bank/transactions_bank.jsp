<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  
</style>
<body>
<div class="container" id="container_transaction">
    
    <div class="row">
    <h3>Transactions Bancaires</h3>
      <div class="col-md-4">
      <div class="card-counter danger">
     <i class="fas fa-sign-out-alt"></i>
     <span class="count-numbers">${amount_out} €</span>
      </div>
    </div>
        <div class="col-md-4">
      <div class="card-counter success">
         <i class="fas fa-sign-in-alt"></i>
        <span class="count-numbers">${amount_in} €</span>
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
              <small><i><b>TVA ${liste.amount_HT} €</b></i></small>
            </div>
            </c:if>
            
             <c:if test = "${liste.amount_HT == 0}"> 
              <div class="btn-group" id="status" data-toggle="buttons">
              <label class="btn btn-default btn-on btn-xs " onclick="addtva()"> 
              <input type="radio" >Oui</label>
              <label class="btn btn-default btn-off btn-xs active " onclick="javascript:reducetva()">
              <input type="radio" >Non</label>
            </div>
            </c:if>              
            </td>
           </c:if>
           
           
           </tr>
            </c:forEach>
                </tbody>
            </table>
            
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