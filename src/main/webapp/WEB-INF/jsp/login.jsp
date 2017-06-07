<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css"
	href="static/css/session-style.css">
	<title>Inicio de Sesión</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<sec:authorize access="isAuthenticated()">
		<div class="container">
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-12" style="text-align: center;">
					<h1>No puedes acceder a esta página si ya has Iniciado Sesión</h1>
				</div>
			</div>
		</div>
	</sec:authorize>

	<sec:authorize access="isAnonymous()">
		<div class="container-fluid login">
			<div class="row form-class">
				<div class="col-lg-12">
					<%@ include file="../jspf/authinfo.jspf"%>
					<div class="panel panel-info">
						<div class="panel-heading">Inicio de Sesión</div>
						<div class="panel-body">
							<form class="form-horizontal" action="/login" method="post">
								<div class="form-group">
									<div class="col-sm-12">
										<label class="sr-only" for="email">Email</label> <input
											type="text" name="username" class="form-control" id="email"
											placeholder="Usuario" required="required" value='aa' maxlength="255">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<label class="sr-only" for="pass">Password</label> <input
											type="password" name="password" class="form-control"
											required="required" id="pass" placeholder="Password"
											value='aa' maxlength="255">
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-group">
									<div class="col-sm-12 submitButton">
										<input type="submit" class="btn btn-success"
											value="Iniciar Sesión">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</body>
</html>