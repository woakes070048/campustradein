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
						delay: 2000,
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
						delay: 2000,
						url: '/signupok',
						type: 'POST',
						dataType: 'json',
						contentType: 'application/json',
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
	});

	$('#signupForm').submit(function(e) {
		var $form = $(e.target),
			fv = $form.data('formValidation');

		$form.attr('disabled', true);

		var formArray = $form.serializeArray();
		var jsonData = convertFormDataToJson(formArray);

		$.ajax({
			url: '/signup',
			type: 'POST',
			data: JSON.stringify(jsonData),
			contentType: 'application/json',
			dataType: 'json',
		}).success(function(response) {
			$form.attr('disabled', false);
			if(response.result === 'error') {
				// let them know what fields are invalid
				// but it should not be though?? Hmm??
				// user turned off javascript??? WHHHYYYYYYYYY!!
			} else {
				window.location.href = response.redirect;
			}
		});
	});


})
