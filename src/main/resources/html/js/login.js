$(document).ready(function() {
   $('#loginForm').validator().on('submit', function(event) {
      event.preventDefault();
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
         success: function(data, text, xhr) {
            if(xhr.status != 200) {
               var errorMsg;
               if(usernameOrEmail.indexOf('@') === -1) {
                  // user entered username
                  errorMsg = 'Username or password incorrect';
               } else { // email was entered
                  errorMsg = 'Email or password incorrect';
               }
               $("#errorMsg p").text(errorMsg);
               $("#errorMsg").removeClass("hidden");
            }
         }
      });
   }
});
