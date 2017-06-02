<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container"
		style="padding: 0px 0px 20px 0px;">

		<div class="col-sm-3 busqueda">
			<form class="navbar-form navbar-left">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search">
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
		
		<div class="col-sm-9 search-result">
			<c:forEach items="${lista}" var="c">
				<div class="search-1">
					<a href="project"><img alt="res1" src="static/img/logPro.png"></a>
					<div class="search-text">
						<h3>${c.name}</h3>
						<p>${c.desc}</p>
					</div>
				</div>
			</c:forEach>

			<!--
			</a><div class="search-1">
				<a href="project"><img alt="res1" src="static/img/logPro.png"></a>
				<div class="search-text">
					<h3>Result #1</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
						do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
						enim ad minim veniam, quis nostrud exercitation ullamco laboris
						nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
						reprehenderit in voluptate velit esse cillum dolore eu fugiat
						nulla pariatur.</p>
				</div>
-->
		</div>
	</div>
	</div>
</body>
</html>