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
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="css/home.css">

		<!-- google fonts  -->
		<link href='https://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900italic,900' rel='stylesheet' type='text/css'>
	</head>
	<body>
		<!-- navigation section -->
		<nav class="navbar navbar-default">
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
		        <li><a href="/">Home</a></li>
		      </ul>
		    </div>
		  </div>
		</nav>
		<div class="accountActivation">
			<h1>Congratulations ${username}!</h1>
			<h2>Your Account has been fully activated</h2>
			<p>You will be redirected to our home page in 15 seconds or if you're impatient like us

			</p>
			<p class="text-center">
				<a href="/" class="btn btn-primary" type="button" name="button">Click to go home <i class="fa fa-home"></i></a>
			</p>
		</div>

		<footer>
			<p>&copy; Copyright 2016 <br />
				Powered by <a target="_blank" href="http://www.getbootstrap.com">Twitter bootstrap</a> and
				<a target="_blank" href="http://www.fontawesome.io">Fontawesome</a>
			</p>
		</footer>
		<!-- javascript Libraries and custom files -->
		<script src="js/jquery.js"></script>
		<script type="text/javascript">
			var timer = setTimeout(function() {
				window.location=window.location.hostname;
			}, 15000);
		</script>
	</body>
</html>
