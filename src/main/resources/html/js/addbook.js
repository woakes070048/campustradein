$(document).ready(function() {
    function booksApiCall(isbn, async) {
        return $.ajax('/suggestions/isbn/' + isbn, {
            headers: {
                'Content-Type' : 'application/json',
                'Accept' : 'application/json'
            },
            success: function(response) {
                console.log(response);
                return async(response.data);
            }
        });
    }

    $('#addBookModal')
        .find('input[name="isbnSearch"]')
            .typeahead({
                hint: true,
                highlight: true,
                minLength: 10
            }, {
                name: 'books',
                source: function(query, sync, async) {
                    booksApiCall(query, async);
                },
                templates: {
                    suggestion: function(book) {
                        return '<div>' + book.title + '</div>';
                    },
                    pending: '<div>Please wait...</div>'
                }
            })
            .on('typeahead:selected', function(e, suggestion, dataSetName) {
                // Revalidate the state field
                $('#addBookForm').formValidation('revalidateField', 'state');
            })
            .on('typeahead:closed', function(e) {
                // Revalidate the state field
                $('#addBookForm').formValidation('revalidateField', 'state');
            });

    $('#addBookForm').formValidation({
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
