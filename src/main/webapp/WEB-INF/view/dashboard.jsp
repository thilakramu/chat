<!DOCTYPE html>
<html>
<body>
<h1>Login success</h1>
<%=session.getAttribute("username")%>
<h1>Getting server updates</h1>
<div id="result"></div>

<script>
if(typeof(EventSource) !== "undefined") {
  var source = new EventSource("http://localhost:8080/sseTest");
  source.onmessage = function(event) {
	  console.log(event);
    document.getElementById("result").innerHTML += event.data + "<br>";
  };
} else {
  document.getElementById("result").innerHTML = "Sorry, your browser does not support server-sent events...";
}
</script>

</body>
</html>