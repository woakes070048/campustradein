var multer   =  require( 'multer' );
var upload   =  multer( { dest: 'uploads/' } );
var sizeOf   =  require( 'image-size' );
var express = require('express');
var app = express();
var responseText = {
    "result" : "ok",
    "data" : [
        {
            "previewLink": "https://www.googleapis.com/books/v1/volumes/XXdyQgAACAAJ",
            "title": "The C Book, Featuring the ANSI C Standard",
            "isbn13": "9780201544336",
            "isbn10": "0201544334",
            "authors": ["Mike Banahan", "Declan Brady", "Mark Doran"],
            "publisher": "Addison-Wesley Longman",
            "publishedDate": "1991-01",
            "tags": [
             "Computers", "C programming", "Computer Science"
            ]
        }
    ]
};

app.use(express.static(__dirname + '/../src/main/resources/html'));

app.get('/', function(req, res) {
    res.send('hello from express');
});

app.get('/suggestions/isbn/:isbn', function(req, res) {
    res.setHeader('Content-Type', 'application/json');
    res.status(200);
    // res.send(req.params);
    res.send(JSON.stringify(responseText));
});

app.post('/books', function(req, res) {
    res.setHeader('Content-Type', 'application/json');
    res.status(200);
    // res.send(req.params);
    res.send(JSON.stringify(responseText));
});

app.post( '/upload', upload.single( 'file' ), function( req, res, next ) {
  if ( !req.file.mimetype.startsWith( 'image/' ) ) {
    return res.status( 422 ).json( {
      error : 'The uploaded file must be an image'
    } );
  }
  var dimensions = sizeOf( req.file.path );
  if ( ( dimensions.width < 640 ) || ( dimensions.height < 480 ) ) {
    return res.status( 422 ).json( {
      error : 'The image must be at least 640 x 480px'
    } );
  }
  return res.status( 200 ).send( req.file );
});

app.listen(3000, function() {
    console.log('upload service listening on port 3000');
});
