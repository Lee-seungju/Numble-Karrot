function check() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var username = document.getElementById("username").value;
    var phone_number = document.getElementById("phone_number").value;
    var nickname = document.getElementById("nickname").value;

    if (email.length == 0 || password.length == 0 || username.length == 0 || phone_number.length == 0 || nickname.length == 0)
        alert("입력받지 못한 항목이 있습니다.")
    else
        document.add_user.submit();
}

function login_check() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (email.length == 0 || password.length == 0)
        alert("입력받지 못한 항목이 있습니다.")
    else
        document.add_user.submit();
}