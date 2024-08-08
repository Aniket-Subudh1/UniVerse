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
        event.preventDefault();

        let email = document.getElementById('email').value;
        let passwordValue = password.value;
        let confirmPasswordValue = confirmPassword.value;

        if (passwordValue !== confirmPasswordValue) {
            message.style.color = 'red';
            message.textContent = 'Passwords do not match!';
            return;
        }

        // Prepare form data
        const formData = new FormData(registrationForm);

        // Send the form data using fetch API
        fetch('register', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                if (data === 'success') {
                    showSuccessModal();
                    registrationForm.reset();
                } else {
                    message.style.color = 'red';
                    message.textContent = 'Registration failed. Please try again.';
                }
            })
            .catch(error => {
                message.style.color = 'red';
                message.textContent = 'An error occurred. Please try again.';
            });
    });
});

function showSuccessModal() {
    const modal = document.getElementById('successModal');
    modal.style.display = 'flex';
}

function closeSuccessModal() {
    const modal = document.getElementById('successModal');
    modal.style.display = 'none';
}
