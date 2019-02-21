<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Chat Application</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
 	
	<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
  <script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	
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
    .otherdiv {
      background-color: #eaeaea;
      color: white;
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
				<li class="active"><a href="/user/dashboard">Home</a></li>
				<li><a href="/user/logout">Logout</a></li>
			</ul>
		</div>

   		 <div class="col-sm-9">
			<h4><small><a>CHAT MESSAGES</small></h4>
			<hr>	
			
			  <!--<ul class="list-group">
					<li class="list-group-item otherdiv">First item</li>
					<li class="list-group-item">First item</li>
			  </ul>-->
			  <div class="col-md-8" >
			  		<ul class="list-group" id="chat_list">
			  		<c:forEach items="${chatList}" var="chat">
			  		<c:choose>
					    <c:when test="${chat.fromId == user_id}">
					        <li class="list-group-item">${chat.message}</li>
					    </c:when>    
					    <c:otherwise>
					        <li class="list-group-item otherdiv">${chat.message}</li>
					    </c:otherwise>
					</c:choose>

			  					  			
			  		</c:forEach>
			  		</ul>
			  </div>
			  <div class="form-group col-md-8" id="input-div">
				  <input type="text" class="form-control form-control-overwrite" id="message"><span class="btn btn-sm btn-primary btn-overwrite" onclick="save()">Submit</span>
			  </div>
			      
    	</div>
  	</div>
</div>
<script>
	var from_id = parseInt("<%=session.getAttribute("user_id")%>");
	var to_id = "${to_id}";
	if(typeof(EventSource) !== "undefined") {
		var source = new EventSource("http://localhost:8080/chat/messages/unread/"+to_id);
  		source.onmessage = function(event) {
		  	console.log(JSON.parse(event.data));
		  	var data = JSON.parse(event.data);
		    var html = "";
		  	for (var i=0; i<data.length; i++) {
		  		if (from_id == data[i].from_id) {
		  			html += '<li class="list-group-item">' + data[i].message + '</li>';
		  		} else {
		  			html += '<li class="list-group-item otherdiv">' + data[i].message + '</li>';
		  		}
		  		
		  	}
		  	
		  	$("#chat_list").append(html);
		  	
			$('#input-div')[0].scrollIntoView(true);
  		};
	} else {
	  console.log("Sorry, your browser does not support server-sent events...");
	}
	
	
	function save() {
		var to_id = "${to_id}";
		var message = $("#message").val();
		$("#message").val("");
		
		$.ajax({
			url:"/chat/save",
			type:"POST",
			data:{
				to_id: to_id,
				message: message
			}
		}).done(function(json) {
			$("#chat_list").append('<li class="list-group-item">' + message + '</li>');
			console.log(json);
		}).fail(function(xhr){
			console.log(xhr);
		});
	}
	
	document.querySelector("#message").addEventListener("keypress", function(e) {
		var key = e.which || e.keyCode;
		if (key == 13) {
			save();
		}
	});
		
	
</script>
</body>
</html>


