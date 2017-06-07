<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<title>Lista de Usuarios</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-md-12"
				style="text-align: center; margin-bottom: 50px;">
				<h1>Lista de Usuarios</h1>
			</div>

			<div class="col-sm-12 illust-box">
				<table style="width: 100%; text-align: center;">
					<tr style="font-size: 28px;">
						<td>Nombre</td>
						<td>Descripción</td>
					</tr>

					<c:forEach items="${lista}" var="c">
						<tr>
							<td><h2>
									<a href="/user/${c.name}">${c.name}</a>
								</h2></td>
							<td><h4>${c.description}</h4></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>