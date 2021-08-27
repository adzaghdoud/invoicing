<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style>
<%@include file="/resources/css/profil.css" %>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" id="containerprofil">
<div class="row gutters">
<div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
<div class="card h-100">
	<div class="card-body">
		<div class="account-settings">
			<div class="user-profile">
				<div class="user-avatar">
				
				<c:choose>
                <c:when test="${empty avatar}">
                <img src="https://bootdey.com/img/Content/avatar/avatar7.png">
                </c:when>
                <c:otherwise>
                <img src="data:image/jpg;base64,${avatar}" >
                </c:otherwise>
                </c:choose>
				</div>
				<input type="file" id="imgupload" style="display:none" accept="image/*"/> 
				<button type="button" id="buttonmodifyimage"  class="btn btn-success"><i class="fas fa-user-cog"></i> Update Logo</button>
				<h5 class="user-name">${CN}</h5>
				<h6 class="user-email">${email}</h6>
			</div>
		</div>
	</div>
</div>
</div>
<div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
<div class="card h-100">
	<div class="card-body">
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mb-2 text-primary">Information du compte</h6>
				<hr>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="fullName">Login</label>
					<input type="text" class="form-control" id="login" value="${login}" readonly>
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">Email</label>
					<input type="email" class="form-control" id="eMail" value="${email}">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Tel</label>
					<input type="text" class="form-control" id="phone" value="${tel}">
				</div>
			</div>
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Company</label>
					<input type="text" class="form-control" id="company" value="${company}">
				</div>
			</div>		
		</div>
				<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="text-right">
					<button type="button" id="submit" name="submit" class="btn btn-warning" onclick="updateinfo()">Update Information</button>
				</div>
			</div>
		</div>
		<hr/>
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mt-3 mb-2 text-primary">Mise à jour password</h6>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="Street">Nouveau password</label>
					<input type="password" class="form-control" id="newpassword" >
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="ciTy">confirmer password</label>
					<input type="password" class="form-control" id="repeatnewpassword">
				</div>
			</div>
		</div>
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="text-right">
					<button type="button" id="submit" name="submit" class="btn btn-warning" onclick="updatepassword()">Update Password</button>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
<script>
$('#buttonmodifyimage').click(function(){ $('#imgupload').trigger('click'); 
});
$("#imgupload").change(function(){
changeavatar(document.getElementById('imgupload').files[0]) ;   
});
</script>

  <div class="modal fade" id="Modalnotifyprofil" tabindex="-1"  aria-labelledby="titlemodalnotifyprofil" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titlemodalnotifyprofil"></h5>
      </div>
      <div class="modal-body">
       <span id="msgmodalnotifyprofil"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>