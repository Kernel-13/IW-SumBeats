<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../../static/css/session-style.css">
<link rel="stylesheet" type="text/css" href="../../static/css/main.css">
<title>Modificar Proyecto</title>
<link rel="stylesheet" type="text/css"	href="../../static/css/image-picker.css">
<script src="../../static/js/image-picker.js" type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.0.0.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/image-picker/0.3.0/image-picker.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/image-picker/0.3.0/image-picker.css" />
<style type="text/css">
	.thumbnail img{
		max-height: 150px;
	}
</style>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>

	<div class="container-fluid login">
		<form class="form-horizontal" action="/changingProject" method="post">
			<div class="row form-class" style="width: 500px;">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">Modifica la Descripción del Proyecto</div>
						<div class="panel-body">
							<div class="form-group">
								<div class="col-sm-12">
									<label class="sr-only" for="desc">Descripcion</label>
									<textarea name="desc" class="form-control" required="required"
										id="desc" placeholder="Description" style="height: 200px;"
										maxlength="500">${project.desc}</textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row form-class" style="width: 942px;">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">Escoge tu Imagen:</div>
						<div class="panel-body">
							<select class="image-picker" name="foto">
								<c:forEach var="i" begin="1" end="${n}">
									<option data-img-src="../../static/img/${i}.jpg" data-img-alt="${i}"
										value="${i}">${i}</option>
								</c:forEach>
							</select> <input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="hidden" name="id"
								value="${project.id}" /> <input type="hidden" name="user"
								value="<sec:authentication
								property="principal.username" />" />
							<div class="form-group">
								<div class="col-sm-12 submitButton">
									<input type="submit" class="btn btn-success"
										value="Aplicar Cambios">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</form>
	</div>
	<script type="text/javascript">

$("select").imagepicker({
    hide_select : true,
    show_label  : false
  })
  </script>
</body>
</html>