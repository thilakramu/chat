<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Chat Application</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {
    	height: 600px;
   	}
    
    /* Set gray background color and 100% height */
    .sidenav {
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;} 
    }
    .form-group {
    	display:flex;
    }
    .form-control-overwrite{
    	width:75%;
    }
    .btn-overwrite{
	    width:25%;
	    margin-left:15px;
    }
    
  </style>
</head>
<body>

<div class="container-fluid">
 	 <div class="row content">
		<div class="col-sm-3 sidenav">
			<h4><%=session.getAttribute("name")%></h4>
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#section1">Home</a></li>
				<li><a href="/user/logout">Logout</a></li>
			</ul>
		</div>

   		 <div class="col-sm-9">
			<h4><small><a>CHAT MESSAGES</small></h4>
			<hr>	
			
			  <!-- <ul class="list-group">
					<li class="list-group-item">First item</li>
			  </ul> -->
			  <div class="form-group col-md-8">
				  <input type="text" class="form-control form-control-overwrite" id="usr"><span class="btn btn-sm btn-primary btn-overwrite">Submit</span>
			  </div>
			      
    	</div>
  	</div>
</div>
<script>
	/* if(typeof(EventSource) !== "undefined") {
	  var source = new EventSource("http://localhost:8080/sseTest");
	  source.onmessage = function(event) {
		  console.log(event);
	    document.getElementById("result").innerHTML += event.data + "<br>";
	  };
	} else {
	  document.getElementById("result").innerHTML = "Sorry, your browser does not support server-sent events...";
	} */
</script>
</body>
</html>


