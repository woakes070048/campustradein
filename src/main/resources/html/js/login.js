$(document).ready(function() {
   $('#loginForm').validator().on('submit', function(event) {
      event.preventDefault();
      $("#errorMsg").addClass("hidden");
      submitForm();
   });

   function submitForm() {
      var usernameOrEmail = $('#usernameOrEmail').val();
      var password = $('#password').val();

      var dto = {'usernameOrEmail' : usernameOrEmail, 'password' : password};

      $.ajax({
         type: 'POST',
         url: '/login',
         data: JSON.stringify(dto),
         dataType: 'json',
         contentType: "application/json",
         success: function(data) {
             window.location.href = data.redirect;
         },
         error: function(data) {
               if(usernameOrEmail.indexOf('@') === -1) {
                  // user entered username
                  errorMsg = 'Username or password incorrect';
               } else { // email was entered
                  errorMsg = 'Email or password incorrect';
               }
               $("#errorMsg").text(errorMsg);
               $("#errorMsg").removeClass("hidden");
        }
      });
   }
});
