$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();
        var usernameOrEmail = $('#usernameOrEmail').val();
        var password = $('#password').val();
        var authenticationToken = usernameOrEmail + ':' + password;
        var hash = btoa(authenticationToken);
        var requestHeader = 'Basic ' + hash;

        $.ajax({
            type: 'POST',
            url: '/login',
            beforeSend : function(request) {
                request.setRequestHeader('Authorization', requestHeader);
                console.log(request);
            },
            dataType: 'json',
            contentType: "application/json"
        }).success(function(response) {
            if(response.result === 'ok') {
                window.location.href = response.redirect;
            } else {
                $("#myModal").effect("shake");
            }
        });
    });
});
