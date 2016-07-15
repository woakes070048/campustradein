<!DOCTYPE html>
<html>
<head>
    <title>campustradein</title>
    <link rel="icon" href="img/ico.png" type="image/png" sizes="16x16">
    <!-- meta tags/keywords for search engines -->
    <meta charset="UTF-8">
    <meta name="description" body="college online marketpalce">
    <meta name="keywords" body="books,college books,top rated college textbook marketplace">
    <meta name="author" body="campustradein">
    <meta name="csrf-token" content="${csrf_token}">
    <meta name="name" content="content">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- style sheets -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/animate.css">
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap-social.css">
    <link rel="stylesheet" type="text/css" href="css/formValidation.min.css">
    <link rel="stylesheet" type="text/css" href="css/typeaheadjs.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/dropzone/4.3.0/min/dropzone.min.css">
    <link rel="stylesheet" href="https://www.fuelcdn.com/fuelux/3.4.0/css/fuelux.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.tagsinput/0.4.2/bootstrap-tagsinput.css" />
    <link rel="stylesheet" type="text/css" href="css/home.css">
    <link rel="stylesheet" type="text/css" href="css/wizard.css">

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
                <a class="navbar-brand" href="/">campustradein</a>
            </div>


            <!-- <form class="navbar-form navbar-left" action="/search" method="GET">
            <div class="form-group">
            <input type="hidden" name="page" value="1">
            <input type="text" class="form-control" name="q" placeholder="I'm looking for...">
            <input type="submit" class="btn btn-default" value="Search">
        </div>
    </form> -->

</div>
</nav>

<div class="loader" data-initialize="loader" id="myLoader"></div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h2 class="panel-title">Add a book</h2>
                </div>
                <div class="panel-body">
                        <form id="bookDetailsForm" class="form-horizontal" action="" method="post">
                            <div class="form-group">
                                <label for="isbnSearch" class="col-md-2 control-label">Search Book</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="isbnSearch" name="isbnSearch" placeholder="Enter ISBN">
                                </div>
                                <div class="col-md-3" id="poweredByGoogle">
                                    <img class="img-responsive control-label" src="img/powered_by_google_on_white.png" alt="" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="title" class="col-md-2 control-label">Title</label>
                                <div class="col-md-6">
                                    <input type="text" id="bookTitle" class="form-control" name="title" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="authors" class="col-md-2 control-label">Authors</label>
                                <div class="col-md-6">
                                    <input type="text" id="authors" class="form-control" name="authors" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="isbn13" class="col-md-2 control-label">ISBN 13</label>
                                <div class="col-md-3">
                                    <input type="text" id="isbn13" class="form-control" name="isbn13" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="isbn10" class="col-md-2 control-label">ISBN 10</label>
                                <div class="col-md-3">
                                    <input type="text" id="isbn10" class="form-control" name="isbn10" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="category" class="col-md-2 control-label">Categories</label>
                                <div class="col-md-6">
                                    <input type="text" id="tags" class="form-control" name="tags" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="condition" class="col-md-2 control-label">Condition</label>
                                <div class="col-md-6">
                                    <select class="form-control" id="condition" name="condition">
                                        <option>New (barely even used it)</option>
                                        <option>Fairly new (less than one semester)</option>
                                        <option>Used (four semesters or less)</option>
                                        <option>Just ok (had it for while now)</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="price" class="col-md-2 control-label">Price</label>
                                <div class="col-md-2">
                                    <div class="input-group">
                                        <div class="input-group-addon"><strong>$</strong></div>
                                        <input type="text" id="price" class="form-control" name="price">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-2">
                                    <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
                                </div>
                            </div>
                        </form>
                        </div> <!-- End book list panel -->
                </div> <!-- end panel body -->
            </div>
        </div>
</div>
</div>
<!-- footer section -->
<footer>
    <p>&copy; Copyright 2016 <br />
        Powered by <a target="_blank" href="http://www.getbootstrap.com">Twitter bootstrap</a> and
        <a target="_blank" href="http://www.fontawesome.io">Fontawesome</a>
    </p>
</footer>

<!-- javascript Libraries and custom files -->
<script  type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/4.3.0/min/dropzone.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/easy-autocomplete/1.3.5/jquery.easy-autocomplete.min.js"></script>
<script  type="text/javascript" src="js/bootstrap.min.js"></script>
<script  type="text/javascript" src="js/formValidation.min.js"></script>
<script  type="text/javascript" src="js/formValidation-bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.11.1/typeahead.jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/0.11.1/bloodhound.min.js"></script>
<script src="https://cdn.jsdelivr.net/bootstrap.tagsinput/0.4.2/bootstrap-tagsinput.min.js"></script>
<script src="https://www.fuelcdn.com/fuelux/3.13.0/js/fuelux.min.js"></script>
<script src="https://cdn.jsdelivr.net/bootbox/4.4.0/bootbox.min.js"></script>
<script type="text/javascript" src="js/wizard.js"></script>
</body>
</html>
