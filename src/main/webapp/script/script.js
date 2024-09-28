const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');
const togglePassword = document.getElementById('togglePassword');
const togglePasswordLogin = document.getElementById('togglePasswordLogin');
const password = document.getElementById('password');
const passwordLogin = document.getElementById('passwordLogin');
const registrationForm = document.getElementById('registrationForm');
const successModal = document.getElementById('successModal');
const closeModalBtn = document.getElementById('closeModalBtn');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

togglePassword.addEventListener('click', function () {
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    this.classList.toggle('bx-show');
});

togglePasswordLogin.addEventListener('click', function () {
    const type = passwordLogin.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordLogin.setAttribute('type', type);
    this.classList.toggle('bx-show');
});

registrationForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const formData = new FormData(registrationForm);

    fetch('reg', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(text => {
            if (text === 'success') {
                // Show the modal
                successModal.style.display = 'block';
            } else {
                // Handle the error (this could be enhanced)
                alert('Registration failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again.');
        });
});

closeModalBtn.addEventListener('click', function() {
    successModal.style.display = 'none';
    registrationForm.reset(); // Reset the form fields
    window.location.href = 'home.jsp'; // Redirect to the index page
});
