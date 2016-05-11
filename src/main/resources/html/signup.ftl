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
    <link href="css/master.css" rel="stylesheet">
    <link href="css/signup.css" rel="stylesheet">


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
                Already have an account?
            </p>
            <ul class="nav navbar-nav">
                <li><a href="${login_url}">Login</a></li>
            </ul>
        </div><#--/.nav-collapse -->
    </div>
</nav>
<#-- end navigation -->

<div class="container">
    <div class="row centered-form">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">Sign up for campustradein <small>It's free!</small></h3>
                </div>
                <div class="panel-body">
                    <form id="signupForm" role="form">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="username">Pick a username (at least 3 characters)</label>
                                    <input type="text" name="username" id="username"
                                          data-minLength="3"
                                           class="form-control" placeholder="username"
                                           required>
                                    <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email">Email address</label>
                                    <input type="email" name="email" id="email"
                                           class="form-control" pattern="^.+\@.+\..+$"

                                           placeholder="email address"
                                           equired>
                                    <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                                    <div class="help-block with-errors"></div>
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password">Create a password (at least 8 characters)</label>
                            <input type="password" name="password" id="password"
                                class="form-control input-sm" data-minLength="8"
                                data-error="Please pick a password"
                                placeholder="Password" required>
                            <div class="help-block with-errors"></div>
                        </div>

                        <div class="form-group">
                            <label for="matchingPassword">Confirm password</label>
                            <input type="password" name="matchingPassword" id="matchingPassword"
                                 data-error="Please re-enter your password"
                                data-match="#password" data-match-error="Password does not match"
                                class="form-control input-sm" placeholder="Confirm Password" required>
                           <div class="help-block with-errors"></div>
                        </div>

                        <div class="form-group">
                            <label for="college">Select college you're attending </label>
                            <select name="college" id="college" class="form-control">
                                <option>Wichita State University</option>
                                <option>Kansas State University</option>
                                <option>Butler Community College</option>
                            </select>
                        </div>

                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="terms" value="" required>
                                I agree to the <a href="#">terms and conditions</a>
                                and <a href="#">privacy policy</a>
                            </label>
                        </div>
                        <input type="submit" id="register" value="Signup" class="btn btn-info btn-block">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<#-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<script src="js/validator.min.js"></script>
<script src="js/signup.js"></script>
</body>
</html>
