$(document).ready(function() {

    $("#registerForm").submit(function(e) {
        var passwordInput = $("#passwordInput");
        var repeatPasswordInput = $("#repeatPasswordInput");
        if(passwordInput.val() !== repeatPasswordInput.val()){
            $("#unmatchedPasswordError").show();
            return false;
        }
        else{
            $("#unmatchedPasswordError").hide();
            return true;
        }
    });
});