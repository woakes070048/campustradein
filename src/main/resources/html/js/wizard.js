$(document).ready(function() {
    /** drop zone image upload */
    // Dropzone.autoDiscover = false;
    // var dropzoneOptions = {
    //     url : '/upload',
    //     paramName : 'file',
    //     maxFileSize : 2, // MB
    //     filesizeBase: 1024,
    //     parallelUploads: 5,
    //     maxFiles : 1,
    //     addRemoveLinks: true,
    //     dictDefaultMessage: 'Drag images here or click to select one',
    //     acceptedFiles : 'image/*',
    //     headers: {
    //         'x-csrf-token' : $('meta[name="csrf-token"]').attr('content')
    //     },
    //     accept: function(file, done) {
    //         file.acceptDimensions = done;
    //         file.rejectDimensions = function() {
    //             done('File is too small. Image must be at least 640 by 480 pixels in size');
    //         }
    //     }
    // };
    // var imageDropzone = new Dropzone("form#bookImages", dropzoneOptions);
    // // make sure the file is 1024 x 764 pixels
    // imageDropzone.on('thumbnail', function(file) {
    //     if(file.width < 640 || file.height < 480) {
    //         file.rejectDimensions;
    //     } else {
    //         file.acceptDimensions;
    //     }
    // });
    //
    // imageDropzone.on('addedFile', function(file) {
    //     console.log('file was added');
    //     console.log(file);
    // });
    //
    // imageDropzone.on('sending', function(file, xhr, formData) {
    //     console.log('sending file');
    //     console.log(formData);
    // });

    /** Typeahead book suggestions **/
    var userInput = null;
    $('#isbnSearch').on('change paste keyup', function() {
        userInput = $(this).val();
    });

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
                        return asyncResults(response.data);
                    },
                    error: function(response) {
                        $('#bookTitle').prop('disabled', false);
                        $('#authors').prop('disabled', false);
                        $('#isbn13').prop('disabled', false);
                        $('#isbn10').prop('disabled', false);
                        $('#tags').prop('disabled', false);
                        return asyncResults();
                    }
                });
            },
            templates: {
                notFound: '<div><strong>No results found. Fill out form</strong></div>',
                pending: '<div>Searching...</div>',
                suggestion: function(book) {
                    return '<div>' + book.title + ' <em>by ' + book.authors + '</em></div>';
                }
            }
        })
        .on('typeahead:selected', function(e, suggestion, dataSetName) {
            $('#isbnSearch').typeahead('val', userInput);
            $('#bookTitle').val(suggestion.title);
            $('#authors').val(suggestion.authors);
            $('#isbn13').val(suggestion.isbn13);
            $('#isbn10').val(suggestion.isbn10);
            $('#tags').val(suggestion.tags);

            $('#bookTitle').prop('disabled', true);
            $('#authors').prop('disabled', true);
            $('#isbn13').prop('disabled', true);
            $('#isbn10').prop('disabled', true);
            $('#tags').prop('disabled', true);
            // revalidate form
            $('#bookDetailsForm').formValidation('revalidateField', 'isbnSearch');
        })
        .on('typeahead:closed', function(e) {
            // Revalidate the state field
            $('#bookDetailsForm').formValidation('revalidateField', 'isbnSearch');
        });

    /* form validation */
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
                    numeric: {
                        message: 'ISBN should be a number',
                        transformer: function($field, validatorName, validator) {
                            var value = $field.val();
                            return value.replace('-', '');
                        }
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
            bookTitle: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please enter book title'
                    }
                }
            },
            authors: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please provide a comma separated string of authors '
                    }
                }
            },
            isbn13: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please enter the ISBN13 number'
                    },
                    numeric: {
                        message: 'No dashes allowed'
                    },
                    isbn: {
                        message: 'ISBN13 is not valid'
                    }
                }
            },
            isbn10: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please enter the ISBN10 number'
                    },
                    numeric: {
                        message: 'No dashes allowed'
                    },
                    isbn: {
                        message: 'ISBN10 is not valid'
                    }
                }
            },
            tags: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please enter the ISBN13 number'
                    }
                }
            },
            price: {
                validators: {
                    verbose: false,
                    notEmpty: {
                        message: 'Please name your price'
                    },
                    numeric: {
                        message: 'Price must be numeric'
                    }
                }
            }
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
    });

    $('#bookDetailsForm').submit(function(e) {
        var authors = $("#authors").val().split(',');
        var tags = $('#tags').val().split(',');
        var bookDetails = {
            'title' : $('#bookTitle').val(),
            'authors' : authors,
            'isbn13' : $('#isbn13').val(),
            'isbn10' : $('#isbn10').val(),
            'tags' : tags,
            'condition' : $('#condition').val(),
            'price' : $('#price').val()
        };
        console.log(bookDetails);
        var $form = $(e.target);
        $.ajax({
            url: '/books',
            type: 'POST',
            data: JSON.stringify(bookDetails),
            contentType: 'application/json',
            dataType: 'json'
        }).success(function(response) {
            if(response.result == 'ok') {
                window.location.href = response.redirect;
            } else { // just in case
                $('#submitButton').prop('disabled', false);
                bootbox.dialog({
                    message: 'An error occurred listing your book',
                    title: 'Error',
                    buttons: {
                        danger: {
                            label: 'Close',
                            className: 'btn-danger'
                        }
                    }
                });
            }
        }).error(function(response) {
            $('#submitButton').prop('disabled', false);
            bootbox.dialog({
                message: 'An error occurred listing your book',
                title: 'Error. Please try again',
                buttons: {
                    danger: {
                        label: 'Close',
                        className: 'btn-danger'
                    }
                }
            });
        });
    });

    /** fuel ux wizard */
    $('#listBookWizard')
        .wizard()
        .on('actionclicked.fu.wizard', function(e, data) {
            var fv = $('#bookDetailsForm').data('formValidation'), // FormValidation instance
            step = data.step,                              // Current step
            // The current step container
            $container = $('#bookDetailsForm').find('.step-pane[data-step="' + step +'"]');

            // Validate the book details form
            fv.validate();
            var isValidStep = fv.isValid();
            if (isValidStep === false || isValidStep === null) {
                // force user to fix errors
                e.preventDefault();
            }
        })
        .on('finished.fu.wizard', function(e) {
            console.log('thanks');
        });
});
