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
	<link rel="stylesheet" type="text/css" href="css/home.css">
	<link rel="stylesheet" type="text/css" href="css/owl.carousel.css">
	<link rel="stylesheet" type="text/css" href="css/owl.theme.css">
	<link rel="stylesheet" type="text/css" href="css/search.css">

	<!-- google fonts  -->
	<link href='https://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900italic,900' rel='stylesheet' type='text/css'>
</head>
<body>
	<!-- navigation section -->
	<nav id="navigation-header" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">campustradein</a>
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
							<li><a href="#"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a></li>
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
				<div class="col-lg-6 itemModelForms tab-content" id="modalLogin">
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

	<section class="searchButton searchFont">
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<form action="/search" method="get">
						<div class="form-group">
							<div class="input-group">
								<#if query??>
								<input type="text" name="q" class="form-control input-lg" value="${query}" />
								<#else>
								<input type="text" name="q" class="form-control input-lg" placeholder="I'm looking for..." />
								</#if>
								<span class="input-group-btn">
									<button type="submit" class="btn btn-primary btn-lg">Search</button>
								</span>
							</div>
							<#if totalNumberOfBooks gt 0>
							<p class="help-block">
								Your search for <strong>${query}</strong>, returned <strong>${totalNumberOfBooks}</strong> results
							</p>
							<#else>
							<p class="help-block">
								Your search for <strong>${query}</strong>, returned no results
							</p>
							</#if>
						</div>
						<#if totalNumberOfBooks gt 0>
						<div class="checkbox">
							<label>
								<input type="checkbox" value="">
								Sort by price
							</label>
						</div>
						</#if>
					</form>
				</div>
			</div>

		</div>
	</section>
	<div class="clearfix"></div>

	<section class="searchResults searchFont">
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<#if books?size == 0>
						<h4 class="text-center">
							No results found
						</h4>
						<form action="/catalog" method="get">
							<input type="hidden" name="page" value="1">
							<button type="submit" class="btn btn-primary btn-lg center-block"><i class="fa fa-search"></i> Browse catalog</button>
						</form>
					<#else>
					<div class="panel panel-default ">
						<div class="panel-body searchItems">
							<#list books as book>
								<div class="media">
									<div class="media-left">
										<a href="#">
											<img class="media-object" src="http://placehold.it/100x100" alt="" />
										</a>
									</div>
									<div class="media-body">
										<h4 class="media-heading"><a href="#">${book.title} </a>
											<small>by Walter J. Savitch
												<#list books.authors as author>
													${author},
												</#list>
											</small>
										</h4>
										<h4 class="media-heading"><small>ISBN: <span class="label label-default">${book.isbn13}</span> <span class="label label-default">${book.isbn10}</span></small></h4>
										<button type="button" class="btn btn-primary" name="button"><i class="fa fa-shopping-cart"></i> Buy now</button>
										<button type="button" class="btn btn-danger" name="button"><i class="fa fa-exchange"></i> Negotiate</button>
									</div>
									<div class="media-right">
										<h4>${book.price}</h4>
									</div>
									<hr>
								</div><!-- end media -->
							</#list>

						</div> <!-- end panel-body -->
					</div><!-- end panel -->

					<#if books?size > 0>
					<nav class="text-center">
						<ul class="pagination">
							<li>
								<a href="#" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li>
								<a href="#" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</ul>
					</nav>
					</#if>
					</#if>
				</div> <!-- end col-md-8 -->
			</div><!-- end row -->
		</div><!-- end container-->
	</section><!-- end searchResults -->


	<!-- footer section -->
	<footer>
		<p>&copy; Copyright 2016 <br />
			Powered by <a target="_blank" href="http://www.getbootstrap.com">Twitter bootstrap</a> and
			<a target="_blank" href="http://www.fontawesome.io">Fontawesome</a>
		</p>
	</footer>

	<div class="modal fade" role="dialog" id="signupSuccess">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4>Success!</h4>
				</div>
				<div class="modal-body">
					<p>Thanks for joining campustradein</p>
					<p>
						An <em>email</em> has been sent to <em>youremail@email.com</em>. Click on your email provider to go straight
						to your inbox
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

	<!-- javascript Libraries and custom files -->
	<script  type="text/javascript" src="js/jquery.js"></script>
	<script  type="text/javascript" src="js/bootstrap.min.js"></script>
	<script  type="text/javascript" src="js/morphext.js"></script>
	<script  type="text/javascript" src="js/backstretch.min.js"></script>
	<script  type="text/javascript" src="js/wow.js"></script>
	<script  type="text/javascript" src="js/owl.carousel.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</body>
</html>
