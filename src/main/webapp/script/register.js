document.addEventListener('DOMContentLoaded', function () {
    const registrationForm = document.getElementById('registrationForm');
    const togglePassword = document.querySelector('#togglePassword');
    const password = document.querySelector('#password');
    const confirmPassword = document.querySelector('#confirmPassword');
    const message = document.getElementById('message');

    togglePassword.addEventListener('click', function (e) {
        // toggle the type attribute
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        confirmPassword.setAttribute('type', type);
        // toggle the eye icon
        this.classList.toggle('bx-show');
    });

    registrationForm.addEventListener('submit', function (event) {
        let email = document.getElementById('email').value;
        let passwordValue = password.value;
        let confirmPasswordValue = confirmPassword.value;

        if (passwordValue !== confirmPasswordValue) {
            event.preventDefault();
            message.style.color = 'red';
            message.textContent = 'Passwords do not match!';
            return;
        }

        // Assuming server-side validation passes
        event.preventDefault();
        showSuccessModal();
    });
});

function showSuccessModal() {
    const modal = document.getElementById('successModal');
    modal.style.display = 'block';
}

function closeSuccessModal() {
    const modal = document.getElementById('successModal');
    modal.style.display='none';
}