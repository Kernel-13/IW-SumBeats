<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
						<a href="project/${c.name}"><img alt="res1" src="static/img/logPro.png"></a>
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