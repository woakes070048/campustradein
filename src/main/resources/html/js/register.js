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

      console.log(username);

      $.ajax({
         type: 'POST',
         url: '/register',
         data: 'username=' + username + '&email=' + email + '&password=' + password + '&college=' + college,
         success: function(text) {
            if(text == 'OK') {
               window.location.href = '/'
            } else {
               // errors will be sent back
               console.log(text);
            }
         }
      });
   }
});
