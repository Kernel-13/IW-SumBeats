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
	<title>Crear Proyecto</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<div class="container-fluid login">
		<div class="row form-class" style="width: 500px;">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">Creación de Proyecto</div>
					<div class="panel-body">
						<form class="form-horizontal" action="/addProject" method="post">
							<div class="form-group">
								<div class="col-sm-12">
									<label class="sr-only" for="title">Titulo</label> <input
										type="text" name="title" class="form-control" id="title"
										placeholder="Titulo" required="required" maxlength="255">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<label class="sr-only" for="desc">Descripcion</label>
									<textarea name="desc" class="form-control" required="required"
										id="desc" placeholder="Description" style="height: 200px;" maxlength="500"></textarea>
								</div>
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<input type="hidden" name="user"
								value="<sec:authentication
								property="principal.username" />" />
							<div class="form-group">
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-success"
										value="Crear Proyecto">
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