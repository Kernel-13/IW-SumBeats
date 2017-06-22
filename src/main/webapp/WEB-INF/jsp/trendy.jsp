<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<title>Tendencias</title>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container illust-container">
		<div class="row">
			<div class="col-sm-12 illust-box">
				<div style="text-align: center; margin-bottom: 50px;">
					<h1>Tendencias</h1>
				</div>
				<c:forEach items="${lista}" var="c">
					<div class="trendy">
						<a href="project/${c.name}"><img alt="res1" src="static/img/${c.icon}.jpg"></a>
						<div class="search-text">
							<h3><a href="project/${fn:replace(c.name,' ','_')}">${c.name}</a></h3>
							<p>${c.desc}</p>
							<h4 style="color:burlywood; font-weight: bold;">Points: ${c.weekRating}</h4>
						</div>
					</div>

				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>