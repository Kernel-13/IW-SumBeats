<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<title>Búsqueda</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container"
		style="padding: 20px 0px 20px 0px;">
		<div class="row">
			<div class="co-md-12" style="text-align: center">
				<h2>Resultados de la Busqueda</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 search-result">
				<c:forEach items="${lista}" var="c">
					<div class="search-1">
						<a href="project/${fn:replace(c.name,' ','_')}"><img alt="res1"
							src="static/img/${c.icon}.jpg"></a>
						<div class="search-text">
							<h3>${c.name}</h3>
							<p>${c.desc}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>