function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    localStorage.username = username;
    $(location).attr('href', 'game.html');
}