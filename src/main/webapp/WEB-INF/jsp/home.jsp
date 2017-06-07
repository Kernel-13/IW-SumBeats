<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<title>SumBeats</title>
</head>
<body class="frontpage">
	<%@ include file="../jspf/navbar.jspf"%>
	<sec:authorize access="isAnonymous()">
		<div class="row something-bad">
			<p>Necesitas Registrarte</p>
		</div>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<div class="row something-bad">
			<h1>
				Tienes rol(es) :
				<sec:authorize access="hasRole('USER')">  USER	</sec:authorize>
				<sec:authorize access="hasRole('ADMIN')"> y ADMIN </sec:authorize>
			</h1>
		</div>
	</sec:authorize>

</body>
</html>