<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container">
		<div class="row">
			<div class="col-sm-12 illust-box">
				<div style="text-align: center; margin-bottom: 50px;">
					<h1>Usuarios</h1>
				</div>

				<c:forEach items="${lista}" var="c">
					<div>
						<h3>${c.name}</h3><br>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>