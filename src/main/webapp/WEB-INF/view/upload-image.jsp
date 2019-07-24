<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
  <script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
  <style>
    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: 1500px}
    
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
  </style>
</head>
<body>

<div class="container-fluid">
  <div class="row content">
		<div class="col-sm-3 sidenav">
			<h4><%=session.getAttribute("name")%></h4>
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="/user/dashboard">Home</a></li>
				<li><a href="/user/logout">Logout</a></li>
			</ul>
		</div>

   		 <div class="col-sm-9">
			<h4><small>Upload Image</small></h4>
			<hr>
			<form method="POST" action="/user/upload/image" enctype="multipart/form-data">
    <input type="file" name="file" /><br/><br/>
    <input type="submit" value="Submit" />
</form>

			
    </div>
  </div>
</div>

<footer class="container-fluid">
  <p>Footer Text</p>
</footer>
</body>
</html>


