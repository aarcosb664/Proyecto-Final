document.addEventListener('DOMContentLoaded', function() {
    // Toggle para el formulario de login
    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#passwordLogin');
    
    if (togglePassword && password) {
        togglePassword.addEventListener('click', function() {
            // Toggle del tipo de atributo
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            
            // Toggle del icono
            this.querySelector('i').classList.toggle('bi-eye');
            this.querySelector('i').classList.toggle('bi-eye-slash');
        });
    }
});