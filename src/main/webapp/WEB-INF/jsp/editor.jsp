<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<%@ include file="../jspf/head.jspf"%>
<script src="static/js/abcjs_editor_midi_3.1.2-min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="static/css/abcjs-midi.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"> 
<title>Editor Test</title>
<script type="text/javascript">
	window.onload = function() {
		abc_editor = new ABCJS.Editor("abc", {
			canvas_id : "canvas0",
			midi_id : "midi",
			generate_midi : true,
			midi_download_id : "download",
			warnings_id : "warnings"
		});
	}
</script>
</head>
<body>
	<%@ include file="../jspf/navbar.jspf"%>
	<div class="container-fluid illust-container">
		<div class="row">
			<div class="col-sm-12 studio-info">
				<div class="studio-txt">
					<h1>Nombre archivo</h1>
				</div>
				<div class="studio-pic">
					<a href="/SumBeats/user"><img alt="pr1"
						src="static/img/logUsu.png" style=""></a>
				</div>
			</div>

			<body>

				<textarea class="text-editor" id="abc" cols="80" rows="15">X: 1
T: Cooley's
M: 4/4
L: 1/8
R: reel
K: Emin
|:D2|EB{c}BA B2 EB|~B2 AB dBAG|FDAD BDAD|FDAD dAFD|
EBBA B2 EB|B2 AB defg|afe^c dBAF|DEFD E2:|
|:gf|eB B2 efge|eB B2 gedB|A2 FA DAFA|A2 FA defg|
eB B2 eBgB|eB B2 defg|afe^c dBAF|DEFD E2:|
</textarea>

				<div
					style="width: 500px; height: 50px; color: black; background: white; margin: 20px;"
					id="canvas0"></div>
				<div
					style="width: 500px; height: 50px; color: black; background: red; margin: 20px;"
					id="warnings"></div>
				<div id="midi"></div>
				<div
					style="width: 500px; height: 50px; color: black; background: gray; margin: 20px;"
					id="canvas"></div>
				<div class="download"></div>
		</div>
	</div>


</body>
</html>