$(".toggle-password").click(function() {
    $(this).find("i").toggleClass("bi-eye bi-eye-slash");
    var input = $($(this).attr("toggle"));
    
    if (input.attr("type") == "password") {
        input.attr("type", "text");
    } else {
        input.attr("type", "password");
    }
});
