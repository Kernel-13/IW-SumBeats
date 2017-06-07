<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css"
	href="static/css/session-style.css">
	<title>Registro</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<sec:authorize access="isAnonymous()">
		<div class="container-fluid login">
			<div class="row form-class" style="width: 500px;">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">Registro de Usuario</div>
						<div class="panel-body">
							<form class="form-horizontal" action="/addUser" method="post">
								<div class="form-group">
									<div class="col-sm-12">
										<input name="name" type="text" class="form-control" id="title"
											placeholder="Nombre de Usuario" required="required" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input name="email" type="email" class="form-control"
											id="email" placeholder="Correo Electronico"
											required="required" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input name="pass" type="password" class="form-control"
											id="password" placeholder="Password" required="required" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<textarea name="desc" class="form-control"
											style="width: 100%;" placeholder="Descripci�n" maxlength="255"></textarea>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-group">
									<div class="col-sm-12 submitButton">
										<input type="submit" class="btn btn-success"
											value="Registrarse">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<div class="container">
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-12" style="text-align: center;">
					<h1>No puedes registrar un usuario si ya has iniciado sesi�n</h1>
				</div>
			</div>
		</div>
	</sec:authorize>


</body>
</html>