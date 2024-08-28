// Dark Mode Toggle
function toggleDarkMode() {
    const body = document.body;
    const navbar = document.querySelector('.navbar');
    const cards = document.querySelectorAll('.card');
    const fileInput = document.querySelector('input[type="file"]');
    const toggle = document.getElementById('dark-mode-toggle');

    body.classList.toggle('dark-mode');
    navbar.classList.toggle('dark-mode');
    cards.forEach(card => {
        card.classList.toggle('dark-mode');
    });

    if (fileInput) {
        fileInput.classList.toggle('dark-mode');
    }

    toggle.classList.toggle('dark-mode');
}

// Event Listener for Dark Mode Toggle
document.getElementById('dark-mode-toggle').addEventListener('click', toggleDarkMode);
