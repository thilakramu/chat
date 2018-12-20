<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content="Admin: Unauthorised access prohibited">
	<meta name="author" content="sparknova">


		<link rel="icon" type="image/x-icon" href="/favicon.ico" />

	<title> User: Unauthorised access prohibited</title>

	<!-- Bootstrap core CSS -->
	<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">

	<!-- Custom styles for this template -->
	<link href="/css/jumbotron-narrow.css" rel="stylesheet">
	<link href="/css/admin.css" rel="stylesheet">
	<style>
		@font-face {
			font-family: 'FontAwesome';
			src: url('/fonts/fontawesome-webfont.eot?v=4.4.0');
			src: url('/fonts/fontawesome-webfont.eot?#iefix&amp;v=4.4.0') format('embedded-opentype'),
			url('/fonts/fontawesome-webfont.woff2?v=4.4.0') format('woff2'),
			url('/fonts/fontawesome-webfont.woff?v=4.4.0') format('woff'),
			url('/fonts/fontawesome-webfont.ttf?v=4.4.0') format('truetype'),
			url('/fonts/fontawesome-webfont.svg?v=4.4.0#fontawesomeregular') format('svg');
			font-weight: normal;
			font-style: normal;
		}

		@font-face {
			font-family: 'Glyphicons Halflings';
			src: url('/fonts/glyphicons/glyphicons-halflings-regular.eot');
			src: url('/fonts/glyphicons/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'),
			url('/fonts/glyphicons/glyphicons-halflings-regular.woff') format('woff'),
			url('/fonts/glyphicons/glyphicons-halflings-regular.ttf') format('truetype'),
			url('/fonts/glyphicons/glyphicons-halflings-regular.svg#glyphicons_halflingsregular') format('svg');
		}

	</style>

	<link href="/css/font-awesome.css" rel="stylesheet">

	<!-- Core bootstrap js for this template -->
	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>



			<a class="navbar-brand" href="/admin/home">
				<!-- <img src="/images/medico.PNG" class="img-responsive" width="30"/> -->
				User
			</a>
		</div>

		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
			</ul>
		</div>
	</div>
</nav>




<div class="container-fluid">
	<div class="row content">
		
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-2">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">User Login</div>
            </div>

            <div style="padding-top:30px" class="panel-body" >
            	<c:if test="${not empty error}">
				    <div class="alert alert-danger">
					  <strong>${error}</strong> 
					</div>									    
				</c:if>
            	
                <form id="loginform" class="form-horizontal"  method="POST" role="form">
                     <div class="row">
                    	                	</div>
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input type="text" class="form-control" name="username" placeholder="Username" required="" autofocus="" />
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
                    </div>
                    
                    <div style="margin-top:10px" class="form-group">
                        <!-- Button -->
                        <div class="col-sm-12 controls">
                        	<button class="btn btn-primary btn-block" name="login_btn" type="submit">Login</button>
                        </div>
					</div>                    
                </form>
            </div>						
        </div><br/>
    </div>
    

	<!--  -->
	</div>



</div>





<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script>
// Sidebar
$(function () {
    var URL = window.location,
        $BODY = $('body'),
        $SIDEBAR_MENU = $('#sidebar-menu'),
        $MENU_TOGGLE = $('#menu_toggle');
        $SIDEBAR_FOOTER = $('.sidebar-footer');
        $LEFT_COL = $('.left_col');

    $SIDEBAR_MENU.find('li ul').slideUp();
    $SIDEBAR_MENU.find('li').removeClass('active');

    $SIDEBAR_MENU.find('li').on('click', function(ev) {
        var link = $('a', this).attr('href');

        // prevent event bubbling on parent menu
        if (link) {
            ev.stopPropagation();
        } 
        // execute slidedown if parent menu
        else {
            if ($(this).is('.active')) {
                $(this).removeClass('active');
                $('ul', this).slideUp();
            } else {
                $SIDEBAR_MENU.find('li').removeClass('active');
                $SIDEBAR_MENU.find('li ul').slideUp();
                
                $(this).addClass('active');
                $('ul', this).slideDown();
            }
        }
    });

    $MENU_TOGGLE.on('click', function() {
        if ($BODY.hasClass('nav-md')) {
            $BODY.removeClass('nav-md').addClass('nav-sm');
            $LEFT_COL.removeClass('scroll-view').removeAttr('style');
            $SIDEBAR_FOOTER.hide();

            if ($SIDEBAR_MENU.find('li').hasClass('active')) {
                $SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
            }
        } else {
            $BODY.removeClass('nav-sm').addClass('nav-md');
            $SIDEBAR_FOOTER.show();

            if ($SIDEBAR_MENU.find('li').hasClass('active-sm')) {
                $SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
            }
        }
    });

    // check active menu
    $SIDEBAR_MENU.find('a[href="' + URL + '"]').parent('li').addClass('current-page');

    $SIDEBAR_MENU.find('a').filter(function () {
        return this.href == URL;
    }).parent('li').addClass('current-page').parent('ul').slideDown().parent().addClass('active');
});
</script>

</body>
</html>
