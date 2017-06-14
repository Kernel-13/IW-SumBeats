<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../../static/css/session-style.css">
<link rel="stylesheet" type="text/css" href="../../static/css/main.css">
<title>Customization</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<div class="container-fluid login">
		<div class="row form-class" style="width: 500px;">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">Modificación de tu Descripcion</div>
					<div class="panel-body">
						<form class="form-horizontal" action="/customization"
							method="post">
							<div class="form-group">
								<div class="col-sm-12">
									<textarea name="desc" class="form-control"
										style="width: 100%; height: 250px;" placeholder="Descripción"
										maxlength="500">${user.description}</textarea>
								</div>
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-group">
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-info"
										value="Aplicar Cambios">
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