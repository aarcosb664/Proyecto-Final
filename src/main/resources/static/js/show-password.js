// /js/show-password.js
document.addEventListener('DOMContentLoaded', function() {
    [
        ['togglePassword', 'passwordLogin'],
        ['togglePasswordRegister', 'passwordRegister'],
        ['toggleConfirmPassword', 'confirmPasswordRegister']
    ].forEach(function([toggleId, inputId]) {
        const toggle = document.getElementById(toggleId);
        const input = document.getElementById(inputId);
        if (toggle && input) {
            toggle.onclick = function() {
                const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
                input.setAttribute('type', type);
                this.querySelector('i').classList.toggle('bi-eye');
                this.querySelector('i').classList.toggle('bi-eye-slash');
            };
        }
    });
});
