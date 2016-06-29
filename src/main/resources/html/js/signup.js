// form validation
$(document).ready(function() {
	function convertFormDataToJson(formArray) {
		var json = {};
		$.each(formArray, function() {
			json[this.name] = this.value || '';
		});
		return json;
	}

	$('#signupForm').formValidation({
		framework: 'bootstrap',
		icon: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			username: {
				verbose: false,
				validators: {
					notEmpty: {
						message: 'Please pick a username'
					},
					stringLength: {
						min: 6,
						max: 30,
						message: 'The username must be more than 6 and less than 30 characters long'
					},
					remote: {
						threshold: 6,
						delay: 5000,
						message: 'The username is not available',
						url: '/signupok',
						type: 'POST',
						dataType: 'json',
						data: {
							type: 'username'
						}
					}
				}
			},
			email: {
				verbose: false,
				validators: {
                    notEmpty: {
                        message: 'Please enter your email address'
                    },
					remote: {
						message: 'The email address is not available',
						threshold: 3,
						delay: 5000,
						url: '/signupok',
						type: 'POST',
						dataType: 'json',
						data: {
							type: 'email'
						}
					}
				}
			},
			password: {
				verbose: false,
				validators: {
					notEmpty: {
						message: 'Password is required'
					},
					stringLength: {
						min: 8,
						max: 30,
						message: 'The password must be more than 6 and less than 30 character long'
					},
					callback: {
						callback: function(value, validator, $field) {
							var password = $field.val();
							if(password == '') {
								return true;
							}
							var result = zxcvbn(password),
								score = result.score,
								message = result.feedback.warning || 'The password is weak';

							var $bar = $('#strength-bar');
							switch(score) {
								case 0:
									$bar.attr('class', 'progress-bar progress-bar-danger')
										.css('width', '1%');
									break;
								case 1:
									$bar.attr('class', 'progress-bar progress-bar-danger')
										.css('width', '25%');
									break;
								case 2:
									$bar.attr('class', 'progress-bar progress-bar-danger')
										.css('width', '50%');
									break;
								case 3:
									$bar.attr('class', 'progress-bar progress-bar-warning')
										.css('width', '75%');
									break;
								case 4:
									$bar.attr('class', 'progress-bar progress-bar-success')
										.css('width', '100%');
									break;
							}
							// passsword with a score less than 3 is weak
							if(score < 3) {
								return {
									valid: false,
									message: message
								}
							}
							return true;
						}
					}
				}
			},
			matchingPassword: {
				verbose: false,
				validators: {
					notEmpty: {
						message: 'Please enter the same password'
					},
					identical: {
						field: 'password',
						message: 'Both passwords must be the same'
					},
					stringLength: {
						min: 8,
						max: 30,
						message: 'The password must be more than 6 and less than 30 character long'
					}
				}
			}
		}
	}).on('success.form.fv', function(e) {
		e.preventDefault();
		console.log('form is valid');
	});

	$('#signupForm').submit(function(e) {
		console.log('Here we areeeeeeeeee');
		var $form = $(e.target),
			fv = $form.data('formValidation');
		console.log($form.serializeArray());
		var formArray = $form.serializeArray();
		var jsonData = convertFormDataToJson(formArray);
		console.log(jsonData);

		$.ajax({
			url: '/signup',
			type: 'POST',
			data: jsonData,
			contentType: 'application/json',
			dataType: 'json',
			success: function(data) {
				window.location.href = data.redirect;
				console.log(data);
			},
			error: function(data) {
				console.log(data.errors);
			}
		})
	});


})
