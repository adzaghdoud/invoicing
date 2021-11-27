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
<body>
<div class="container">
    <h3><i class="fas fa-database"></i> Tracking Import Bank Transactions</h3>
    <hr>
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Tracking Import From ${bank_name}</h3>
                <div class="pull-right">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filter</button>
                </div>
             
                
            </div>
            <table class="table table-striped" id="Trancking_Table">
                <thead>
                    <tr class="filters">
                        <th><input type="text" class="form-control" placeholder="Date" disabled></th>
                        <th><input type="text" class="form-control" placeholder="New Trancations" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Nb Credits" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Nb Debits" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Total Credits" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Total Debits" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Old Balance" disabled></th>
                        <th><input type="text" class="form-control" placeholder="New Balance" disabled></th>
                        <th><input type="text" class="form-control" placeholder="State" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Comment" disabled></th>
                    </tr>
                </thead>
                <tbody>
                              <c:forEach var="tracking" items="${ list_tracked_batch }">
            <tr>
            <td>${tracking.date}</td>
            <td>${tracking.nb_transaction_imported}</td>
            <td>${tracking.nb_credit}</td>
            <td>${tracking.nb_debit}</td>
        
            <c:if test = "${tracking.total_credit >0}">
            <td><span style="color:green">${tracking.total_credit}</span></td>
            </c:if>
            <c:if test = "${tracking.total_credit == 0 }">
            <td>${tracking.total_credit}</td>
            </c:if>
            <c:if test = "${tracking.total_debit >0}">
            <td><span style="color:red">${tracking.total_debit}</span></td>
            </c:if>
            <c:if test = "${tracking.total_debit  == 0}">
            <td>${tracking.total_debit}</td>
            </c:if> 
            <td>${tracking.old_balance}</td>
            <td>${tracking.new_balance}</td>
            <c:if test = "${tracking.state == 'OK'}"> 
            <td><span class="label label-success">${tracking.state}</span></td>
            </c:if>
               <c:if test = "${tracking.state == 'KO'}"> 
            <td><span class="label label-danger">${tracking.state}</span></td>
            </c:if>
            
            <td>${tracking.comment}</td>
            </tr>
            </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </div>