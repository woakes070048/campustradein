<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" body="IE=edge">
    <meta name="viewport" body="width=device-width, initial-scale=1">
    <#-- The above 3 meta tags *must* come first in the head; any other head body must come *after* these tags -->
    <title>campustradein - the college marketplace</title>

    <#-- Bootstrap -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/master.css" rel="stylesheet">


    <#-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <#-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <#--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <#[endif]-->
</head>
<body>

<#if important_message??>
<div class="alert alert-success alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <strong>${important_message}</strong>
</div>
</#if>
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

        <#if user_name??>
        <ul class="nav navbar-nav navbar-right">
            <p class="navbar-text">Welcome ${user_name}</p>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                    aria-haspopup="true" aria-expanded="false"><i class="fa fa-userAccount-plus fa-fw"></i>
                    <span class="caret"></span>
                </a>

                <ul class="dropdown-menu">
                    <#if user_active == "true">
                    <li><a href="#"><i class="fa fa-book fa-fw"></i> Add a book</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#"><i class="fa fa-cog fa-fw"></i> Settings</a></li>
                    <li><a href="${logout_url}"><i class="fa fa-sign-out fa-fw"></i> Logout</a></li>
                    <#else>
                    <li><a href="${activate_account_url}"><i class="fa fa-check fa-fw"></i> Please verify your email address</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="${logout_url}"><i class="fa fa-sign-out fa-fw"></i> Logout</a></li>

                    </#if>

                </ul>
            </li>

        </ul>

        <#else>
        <div id="navbar" class="collapse navbar-collapse navbar-right">
            <ul class="nav navbar-nav">
                <li><a href="${login_url}">Login</a></li>
                <li><a href="${signup_url}">Signup</a></li>
            </ul>
        </div>
        </#if>
    </div>
</nav>

<#-- page header -->
<div class="jumbotron jumbotron-welcome-image">
    <div class="container jumbotron-welcome-body text-center">
        <h1 class="jumbo-text">Welcome to campustradein</h1>
        <p class="jumbo-text">
            The best way to buy and sell your textbooks
            on campus
        </p>
        <div class="row">
            <form >
                <div class="col-sm-8 col-sm-offset-2">
                    <div class="input-group">
                        <input type="text" class="form-control search-form"
                               placeholder="Enter book title or ISBN number">
                        <div class="input-group-btn">
                            <button type="submit" class="btn btn-primary search-form">
                                Search
                            </button>
                        </div>

                    </div>
                </div>
            </form>
            <br><br>
        </div>
    </div>
</div>

<#-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<#-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
