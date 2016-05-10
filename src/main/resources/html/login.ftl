<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <#-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
   <title>campustradein - the college marketplace</title>

   <#-- Bootstrap -->
   <link href="css/bootstrap.min.css" rel="stylesheet">
   <link href="css/login.css" rel="stylesheet">


   <#-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
   <#-- WARNING: Respond.js doesn't work if you view the page via file:// -->
   <#--[if lt IE 9]>
   <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
   <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
   <#[endif]-->
</head>
<body>

   <#-- navigation -->
   <nav class="navbar navbar-inverse navbar-fixed-top">

      <div class="container">
         <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
               <span class="sr-only">Toggle navigation</span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">campustradein</a>
         </div>

         <div id="navbar" class="collapse navbar-collapse navbar-right">
            <p class="navbar-text">
               Don't have an account?
            </p>
            <ul class="nav navbar-nav">
               <li><a href="${signup_url}">Signup</a></li>
            </ul>
         </div><#--/.nav-collapse -->
      </div>
   </nav>

   <div class="container">
            <form id="loginForm" data-toggle="validator" class="form-signin" role="form" method="#">
               <div id="errorMsg" class="hidden alert alert-dismissible alert-danger" role="alert">
                  <button type="button" class="close" data-dismiss="alert"
                  aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <p></p>
               </div>
              <h2 class="form-signin-heading text-center">Please sign in</h2>
              <label for="usernameOrEmail" class="sr-only">Username or Email address</label>
              <input type="text" id="usernameOrEmail" class="form-control" placeholder="Username or Email" required autofocus>
              <label for="password" class="sr-only">Password</label>
              <input type="password" id="password" class="form-control" placeholder="Password" required>
              <div class="checkbox">
                <label>
                  <input type="checkbox" value="remember-me"> Remember me
                </label>
              </div>
              <button class="btn btn-primary btn-block" type="submit">Login</button>
            </form>
    </div> <#-- /container -->




   <#-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
   <#-- Include all compiled plugins (below), or include individual files as needed -->
   <script src="js/bootstrap.min.js"></script>
   <script src="js/validator.min.js"></script>
   <script src="js/login.js"></script>
</body>
</html>
