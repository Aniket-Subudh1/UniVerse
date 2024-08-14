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

// Simulate successful registration
registrationForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the form from submitting normally
    // Show the modal
    successModal.style.display = 'block';
});

// Close modal and reset form
closeModalBtn.addEventListener('click', function() {
    successModal.style.display = 'none';
    registrationForm.reset(); // Reset form fields
    container.classList.remove("active"); // Switch to login form
});
