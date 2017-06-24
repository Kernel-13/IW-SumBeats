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
<link rel="stylesheet" type="text/css"
	href="static/css/image-picker.css">
<script src="static/js/image-picker.js" type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.0.0.min.js"
	type="text/javascript"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/image-picker/0.3.0/image-picker.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/image-picker/0.3.0/image-picker.css" />
<title>Registro</title>
<style type="text/css">
.thumbnail img {
	max-height: 150px;
}
</style>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<sec:authorize access="isAnonymous()">
		<div class="container-fluid login">
			<form class="form-horizontal" action="/addUser" method="post">

				<div class="row form-class" style="width: 500px;">
					<div class="col-lg-12">
						<div class="panel panel-info">
							<div class="panel-heading">Registro de Usuario</div>
							<div class="panel-body">
								<div class="form-group">
									<div class="col-sm-12">
										<input name="name" type="text" class="form-control" id="title"
											placeholder="Nombre de Usuario" required="required"
											maxlength="255" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input name="email" type="email" class="form-control"
											id="email" placeholder="Correo Electronico"
											required="required" maxlength="255" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input name="pass" type="password" class="form-control"
											id="password" onchange="correctPassword()"
											placeholder="Contraseña" required="required" maxlength="255" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<input name="pass2" type="password" class="form-control"
											id="password2" onchange="correctPassword()"
											placeholder="Repita la Contraseña" required="required"
											maxlength="255" />
									</div>
								</div>
								<div class="col-sm-12 form-group" id="wrongPass"
									style="display: none">
									<h4>Las contraseñas no son iguales!</h4>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<textarea name="desc" class="form-control"
											style="width: 100%;" placeholder="Descripción"
											maxlength="500"></textarea>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-group">
									<div class="col-sm-12 submitButton">
										<input type="submit" id="submit-button" class="btn btn-success"
											value="Registrarse">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<div class="container">
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-12" style="text-align: center;">
					<h1>No puedes registrar un usuario si ya has iniciado sesión</h1>
				</div>
			</div>
		</div>
	</sec:authorize>

	<script type="text/javascript">
		function correctPassword() {
			var pass1 = document.getElementById("password");
			var pass2 = document.getElementById("password2");
			var helpText = document.getElementById("wrongPass");
			if (pass1.value == pass2.value) {
				pass1.style.background = "lightgreen";
				pass2.style.background = "lightgreen";
				helpText.style.display = "none";
				helpText.style.color = "black";
				helpText.style.margin = "5px";
				document.getElementById("submit-button").disabled = false;
			} else {
				pass1.style.background = "navajowhite";
				pass2.style.background = "navajowhite";
				helpText.style.display = "initial";
				document.getElementById("submit-button").disabled = true;
			}
		}
	</script>

</body>
</html>