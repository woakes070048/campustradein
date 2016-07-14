$(document).ready(function() {
    /** mock rest ful service */
    // $.mockjax([
    //     {
    //         url: /^\/suggestions\/isbn\/([\d]+)$/,
    //         urlParams: ["isbnNumber"],
    //         dataType: 'json',
    //         contentType: 'text/json',
    //         responseTime: [350, 750], // simulate network laterncy of 750ms
    //         status: 200,
    //         responseText: {
    //             "result" : "ok",
    //             "data" : [
    //                 {
    //                     "previewLink": "https://www.googleapis.com/books/v1/volumes/XXdyQgAACAAJ",
    //                     "title": "The C Book, Featuring the ANSI C Standard",
    //                     "authors": ["Mike Banahan", "Declan Brady", "Mark Doran"],
    //                     "publisher": "Addison-Wesley Longman",
    //                     "publishedDate": "1991-01"
    //                 }
    //             ]
    //         }
    //     }, {
    //         url: '/upload',
    //         responseTime: 350, // simulate network laterncy of 750ms
    //         status: 200
    //     }
    // ]);

    /** fuel ux wizard */
    $('#listBookWizard').wizard();

    /** drop zone image upload */
    Dropzone.autoDiscover = false;
    var dropzoneOptions = {
        url : '/upload',
        paramName : 'file',
        maxFileSize : 2, // MB
        maxFiles : 2,
        dictDefaultMessage: 'Drag images here or click to select one',
        acceptedFiles : 'image/*',
        headers: {
            'x-csrf-token' : $('meta[name="csrf-token"]').attr('content')
        },
        accept: function(file, done) {
            file.acceptDimensions = done;
            file.rejectDimensions = function() {
                done('File is too small. Image must be at least 640 by 480 pixels in size');
            }
        }
    };
    var imageDropzone = new Dropzone("#bookImages", dropzoneOptions);
    // make sure the file is 1024 x 764 pixels
    imageDropzone.on('thumbnail', function(file) {
        if(file.width < 640 || file.height < 480) {
            file.rejectDimensions;
        } else {
            file.acceptDimensions;
        }
    });

    imageDropzone.on('addedFile', function(file) {
        console.log(file);
    });

    imageDropzone.on('sending', function(file, xhr, formData) {
        console.log('sending file');
        console.log(formData);
    });

    /** Typeahead book suggestions **/
    $('#bookDetailsForm')
        .find('input[name="isbnSearch"]')
        .typeahead({
            hint: true,
            highlight: true,
            minLength: 10
        }, {
            name: 'book',
            source: function(query, syncResults, asyncResults) {
                return $.ajax('/suggestions/isbn/' + query, {
                    headers: {
                        'Content-Type' : 'application/json',
                        'Accept' : 'application/json',
                        'x-csrf-token' : $('meta[name="csrf-token"]').attr('content')
                    },
                    success: function(response) {
                        console.log(response);
                        return asyncResults(response.data);
                    },
                    error: function(response) {
                        console.log(response);
                    }
                });
            },
            templates: {
                suggestion: function(book) {
                    return '<div>' + book.title + ' <em>by ' + book.authors + '</em></div>';
                },
                pending: '<div>Please wait...</div>',
                notFound: '<div>Book not found. Enter details manually</div>'
            }
        })
        .on('typeahead:selected', function(e, suggestion, dataSetName) {
            // revalidate form
            $('#bookDetailsForm').formValidation('revalidateField', 'isbnSearch');
            // populate the other fields
            $('#bookTitle').val(suggestion.title);
            $('#authors').val(suggestion.authors);
            $('#isbn13').val(suggestion.isbn13);
            $('#isbn10').val(suggestion.isbn10);
            $('#tags').val(suggestion.tags);
        })
        .on('typeahead:closed', function(e) {
            // Revalidate the state field
            $('#bookDetailsForm').formValidation('revalidateField', 'isbnSearch');
        });

    $('#bookDetailsForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            isbnSearch: {
                verbose: false,
                validators: {
                    notEmpty: {
                        message: 'ISBN is required'
                    },
                    isbn: {
                        message: 'The ISBN is not valid'
                    },
                    stringLength: {
                        min: 10,
                        max: 13,
                        message: 'ISBN should be 10 or 13 digits long and should have no dashes'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: 'Please name your price'
                    },
                    numeric: {
                        message: 'Price must be numeric'
                    }
                }
            }
        }
    }).on('click', '', function() {

    });
});
