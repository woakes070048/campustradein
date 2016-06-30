<!DOCTYPE html>
<html>
<head>
	<title>campustradein</title>
	<link rel="icon" href="img/ico.png" type="image/png" sizes="16x16">
	<!-- meta tags/keywords for search engines -->
	<meta charset="UTF-8">
	<meta name="description" content="college online marketpalce">
	<meta name="keywords" content="books,college books,top rated college textbook marketplace">
	<meta name="author" content="campustradein">
	<!-- style sheets -->
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/animate.css">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-social.css">
	<link rel="stylesheet" type="text/css" href="css/formValidation.min.css">
	<link rel="stylesheet" type="text/css" href="css/home.css">
	<link rel="stylesheet" type="text/css" href="css/owl.carousel.css">
	<link rel="stylesheet" type="text/css" href="css/owl.theme.css">

	<!-- google fonts  -->
	<link href='https://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900italic,900' rel='stylesheet' type='text/css'>
</head>
<body>
	<!-- navigation section -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">campustradein</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<#if username??>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							<i class="fa fa-user" aria-hidden="true"></i> Welcome ${username}
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#"><i class="fa fa-plus" aria-hidden="true"></i> List a book</a></li>
							<li><a href="#"><i class="fa fa-book" aria-hidden="true"></i> Bookshelf</a></li>
							<li><a href="#"><i class="fa fa-inbox" aria-hidden="true"></i> Inbox</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a></li>
						</ul>
					</li>
					<#else>
					<li><a href="#" data-toggle="modal" data-target="#myModal" id="loginUser">Login</a></li>
					<li><a href="signup.html">Signup</a></li>
					</#if>
				</ul>
			</div>
		</div>
	</nav>
	<!-- login/sign up form modal -->
	<!-- Modal -->
	<div class="modal fade modalCustom" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="container">
			<div class="row">
				<div class="closeButton">
					<h4 data-dismiss="modal">X</h4>
				</div>
				<h1>campustradein</h1>
				<h6>The best college marketplace.</h6>
				<ul role="tablist" id="activator">
					<li role="presentation" class="active itemActive">
						<a href="#login" aria-controls="login" role="tab" data-toggle="tab" id="targetLogInUser">
							Login
						</a>
					</li>
				</ul>
				<div class="col-lg-6 itemModelForms tab-content">
					<!-- <img src="assets/ico/tooltip.png" id="tooltip"> -->
					<!-- login form -->
					<div role="tabpanel" class="tab-pane active itemLoginForm" id="login">
						<form id="loginForm">
							<button>
								<span>
									<i class="fa fa-facebook-official" aria-hidden="true"></i>
								</span>
								Login with Facebook
							</button>
							<p>- Or -</p>
							<input type="text" class="form-control" placeholder="Username or Email" id="usernameOrEmail" name="username">
							<input type="password" class="form-control" placeholder="Password" id="password" name="password">
							<button type="submit" class="formButton">Login</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>

	<!-- arrow for push up to the top -->
	<a href="#home" class="scroll ArrowUp">
		<i class="fa fa-chevron-up"></i>
	</a>

	<!-- header/default slider section -->
	<section class="header" id="home">
		<div class="overlayHeader">
			<h1>Welcome to campustradein</h1>
			<h4>The best way to buy and sell your textbooks on campus</h4>
			<form class="form-inline">
				<div class="form-group">
					<input type="text" class="form-control" id="exampleInputName2" placeholder="Enter book title or ISBN">
					<span class="fa fa-search errspan hidden-lg hidden-md"></span>
				</div>
				<input type="submit" class="SearchButton hidden-xs hidden-sm" value="search">
			</form>
			<button data-toggle="modal" data-target="#myModalVideo" class="modalSeeVideoButton">
				<span><i class="fa fa-play" aria-hidden="true"></i></span> &nbsp;
				See how it works
			</button>
		</div>
	</section>
	<div class="clearfix"></div>

	<!-- Modal -->
	<div class="modal fade" id="myModalVideo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="ButtonVideoClose">
			<h4 data-dismiss="modal" id="player1Close">X</h4>
		</div>
		<div class="container">
			<div class="row">
				<div class="videoVimeo">
					<iframe id="player1" src="" width="400" height="225" frameborder="0" webkitAllowFullScreen mozallowfullscreen allowFullScreen autoplay></iframe>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>

	<!-- sectionImageContent 1  -->
	<section class="sectionImageContent1">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="wow fadeIn">Buy and Sell Textbooks, Fast and Easy</h1>
					<p class="wow fadeIn">
						That Economics textbook you've been stressing over? It's just one click away!<br/>
						There are 100s of textbooks for students to choose from and the best part? It's easy and convenient!
					</p>
					<img src="https://placeholdit.imgix.net/~text?txtsize=33&txt=600%C3%97170&w=600&h=170" class="wow fadeIn img-responsive">
				</div>
			</div>
		</div>
	</section>

	<!-- sectionImageContent 2  -->
	<section class="sectionImageContent2">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="wow fadeIn">Get more money for your textbooks</h1>
					<p class="wow fadeIn">
						Students can make up to 80% back for textbooks compared to bookstore re-sale prices
					</p>
					<img src="https://placeholdit.imgix.net/~text?txtsize=33&txt=600%C3%97170&w=600&h=170" class="wow fadeIn img-responsive">
				</div>
			</div>
		</div>
	</section>

	<!-- sectionImageContent 2  -->
	<section class="sectionImageContent3">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="wow fadeIn">One community tailored to your needs</h1>
					<p class="wow fadeIn">
						Dedicated to students in the Wichita State University community. Your most frequently used books are here
						so you will never run out of options
					</p>
					<img src="https://placeholdit.imgix.net/~text?txtsize=33&txt=600%C3%97170&w=600&h=170" class="wow fadeIn img-responsive">
				</div>
			</div>
		</div>
	</section>

	<footer>
		<p>&copy; Copyright 2016 <br />
			Powered by <a target="_blank" href="http://www.getbootstrap.com">Twitter bootstrap</a> and
			<a target="_blank" href="http://www.fontawesome.io">Fontawesome</a>
		</p>
	</footer>

	<#if signupWasSuccessful??>
	<div class="modal fade" role="dialog" id="signupSuccess">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4>Success!</h4>
				</div>
				<div class="modal-body">
					<p>${username}, Thanks for joining campustradein</p>
					<p>
						An <em>email</em> has been sent to <em>${email}</em>. Please activate your account.
						Click on your email provider to go straight to your inbox
					</p>
					<a target="_blank" class="btn btn-primary btn-social btn-google" href="http://mail.google.com">
						<span class="fa fa-google"></span> Gmail
					</a>
					<a target="_blank" class="btn btn-primary btn-social btn-yahoo" href="http://mail.yahoo.com">
						<span class="fa fa-yahoo"></span> Yahoo
					</a>
					<a target="_blank" class="btn btn-primary btn-social btn-microsoft" href="http://www.outlook.com">
						<span class="fa fa-windows"></span> Outlook
					</a>
				</div><!-- modal body -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div><!-- modal-dialog-->
	</div>
	</#if>

	<!-- javascript Libraries and custom files -->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/formValidation.min.js"></script>
	<script type="text/javascript" src="js/formValidation-bootstrap.min.js"></script>
	<script type="text/javascript" src="js/morphext.js"></script>
	<script type="text/javascript" src="js/backstretch.min.js"></script>
	<script type="text/javascript" src="js/wow.js"></script>
	<script type="text/javascript" src="js/owl.carousel.js"></script>
	<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	ga('create', 'UA-80043851-1', 'auto');
	ga('send', 'pageview');
	</script>

	<script type="text/javascript" src="js/custom.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<#if signupWasSuccessful??>
	<script type="text/javascript">
	$(window).load(function(){
		$('#signupSuccess').modal('show');
	})
	</script>
	</#if>
</body>
</html>
