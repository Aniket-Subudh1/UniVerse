document.addEventListener('DOMContentLoaded', function () {
    const toggleButton = document.getElementById('dark-mode-toggle');
    const body = document.body;
    const navbar = document.querySelector('.navbar');
    const profileInfo = document.querySelector('.profile-info');
    const cards = document.querySelectorAll('.card');

    // Function to toggle dark mode
    function toggleDarkMode() {
        body.classList.toggle('dark-mode');
        navbar.classList.toggle('dark-mode');
        profileInfo.classList.toggle('dark-mode');
        toggleButton.classList.toggle('dark-mode');
        cards.forEach(card => card.classList.toggle('dark-mode'));

        // Save preference to localStorage
        if (body.classList.contains('dark-mode')) {
            localStorage.setItem('dark-mode', 'enabled');
        } else {
            localStorage.removeItem('dark-mode');
        }
    }

    // Event listener for toggle button click
    toggleButton.addEventListener('click', toggleDarkMode);

    // Load dark mode state from localStorage
    if (localStorage.getItem('dark-mode') === 'enabled') {
        toggleDarkMode(); // Ensure the correct initial state is applied
    }
});

function showProfileModal() {
    document.getElementById('profileModal').style.display = 'flex';
}

function closeProfileModal() {
    document.getElementById('profileModal').style.display = 'none';
}
