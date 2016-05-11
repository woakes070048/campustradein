$(document).ready(function() {
   $('#signupForm').validator().on('submit', function(event) {
      if(event.isDefaultPrevented()) {
         // manual form validation
      } else {
         event.preventDefault();
         submitForm();
      }
   });

   function submitForm() {
      var username = $('#username').val();
      var email = $('#email').val();
      var password = $('#password').val();
      var matchingPassword = $('#matchingPassword').val();
      var college = $('#college').val();

      var dto = {'username' : username,
                    'email' : email,
                    'password' : password,
                    'matchingPassword' : matchingPassword,
                    'college' : college};

      $.ajax({
         type: 'POST',
         url: '/signup',
         data: JSON.stringify(dto),
         dataType: 'json',
         contentType: "application/json",
         success: function(data) {
            window.location.href = data.redirect;
        },
        error: function(data) {
            var errors = data.errors;
            console.error(errors);
        }

      });
   }
});
