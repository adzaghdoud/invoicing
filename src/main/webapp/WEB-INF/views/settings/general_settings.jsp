<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style>
body {
    margin: 0;
    padding-top: 50px;
    color: #C0C0C0;
    background: #C0C0C0;
    position: center;
    height: 100%;
    
}

</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" id="containersettings" >
<div class="row gutters">
<div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
<div class="card h-100 ">
	<div class="card-body "  style="background-color:#D3D3D3;" >
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mb-2 text-primary">SMTP Settings</h6>
				<hr>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="fullName"><b>SMTP HOST</b></label>
					<input type="text" class="form-control" id="smtphost" value="${smtphost}">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail"><b>SMTP PORT</b></label>
					<input type="email" class="form-control" id="smptport" value="${smptport}">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>SMTP USERNAME</b></label>
					<input type="text" class="form-control" id="smtpusername" value="${smtpusername}">
				</div>
			</div>
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>SMTP PASSWORD</b></label>
					<input type="text" class="form-control" id="smtppassword" value="${smtppassword}">
				</div>
			</div>	
			
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>COMPANY EMAIL</b></label>
					<input type="text" class="form-control" id="companyemail" value="${companyemail}">
				</div>
			</div>	
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mb-2 text-primary">LDAP Settings</h6>
				<hr>
				
		  	</div>
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>LDAP HOST</b></label>
					<input type="text" class="form-control" id="ldaphost" value="${ldaphost}">
				</div>
			</div>	
			
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>LDAP PORT</b></label>
					<input type="text" class="form-control" id="ldapport" value="${ldapport}">
				</div>
			</div>		
					
				
		
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>LDAP ADMIN</b></label>
					<input type="text" class="form-control" id="ldapadmin" value="${ldapadmin}">
				</div>
			</div>
			
			
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone"><b>LDAP PASSWORD</b></label>
					<input type="text" class="form-control" id="ldappassword" value="${ldappassword}">
				</div>
			</div>
			
				
		</div>
				<hr>
				<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="text-right">
					<button type="button" id="submit" name="submit" class="btn btn-success" onclick="update_global_settings()">Update</button>
				</div>
			</div>
		</div>

	</div>
</div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/scripts.js" ></script> 
  <div class="modal fade" id="Modalnotify" tabindex="-1"  aria-labelledby="titleModalnotify" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="titleModalnotify"></h5>
      </div>
      <div class="modal-body">
       <span id="msgmodalnotif"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>