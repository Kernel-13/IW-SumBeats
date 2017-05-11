<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="spanish">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="../static/css/main.css">
	<link rel="stylesheet" type="text/css" href="../static/css/session-style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="../jspf/header.jspf" %>

	<div class="container-fluid login">
		<div class="row form-class">
			<div class="col-lg-12">
				<%@ include file="../jspf/authinfo.jspf"%>
				<div class="panel panel-info">
					<div class="panel-heading">
						Inicio de Sesión
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="/login" method="post">
							<div class="form-group">
								<div class="col-sm-12">
									<label class="sr-only" for="email">Email</label>
									<input type="email" class="form-control" id="email" placeholder="Email" required="required">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12"> 
									<label class="sr-only" for="pass">Password</label>
									<input type="password" class="form-control" required="required" id="pass" placeholder="Password">
								</div>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="form-group"> 
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-success" value="Iniciar Sesión">
								</div>
							</div>
						</form>
					</div>
				</div>	
			</div>
		</div>			
	</div>
</body>
</html>