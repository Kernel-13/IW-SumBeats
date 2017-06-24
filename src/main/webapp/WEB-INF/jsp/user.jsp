<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../static/css/main.css">
<title>${user.name}</title>

</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container">
		<sec:authorize access="isAnonymous()">
			<div class="row something-bad">
				<p>Necesitas Registrarte</p>
			</div>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<div class="row">
				<div class="col-sm-12 user-profile">
					<div style="text-align: center; margin-bottom: 50px;">
						<img alt="user_profile_pic" style="max-height: 200px" class="img-circle" src="../user/photo?id=${user.id}">
						<h3>${user.name}</h3>
						<p>${user.description}</p>
						<hr width="50%">
					</div>
				</div>
			</div>
			<div class="row">
				<c:forEach items="${lista}" var="c">
					<div class="col-sm-3">
						<div class="project-view">
							<a href="/project/${c.safeName() }"><img alt="pr1" style="max-height: 200px;"
								class="img-circle" src="../project/photo?id=${c.id}"></a>
							<div class="caption" style="margin: 10px;">
								<p>${c.name}</p>
							</div>
						</div>
					</div>

				</c:forEach>
				

			</div>
		</sec:authorize>
	</div>
</body>
</html>