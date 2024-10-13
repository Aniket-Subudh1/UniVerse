document.addEventListener('DOMContentLoaded', function () {
    const toggleButton = document.getElementById('dark-mode-toggle');
    const body = document.body;
    const navbar = document.querySelector('.navbar');
    const profileInfo = document.querySelector('.profile-info');
    const cards = document.querySelectorAll('.card, .timetable-card'); // Handle both card types
    const modals = document.querySelectorAll('.modal, .modal-content'); // Target modals and modal contents

    // Function to apply dark mode
    function applyDarkMode(isDarkMode) {
        if (isDarkMode) {
            body.classList.add('dark-mode');
            navbar.classList.add('dark-mode');
            if (profileInfo) profileInfo.classList.add('dark-mode');
            if (toggleButton) toggleButton.classList.add('dark-mode');
            cards.forEach(card => card.classList.add('dark-mode'));
            modals.forEach(modal => modal.classList.add('dark-mode'));
        } else {
            body.classList.remove('dark-mode');
            navbar.classList.remove('dark-mode');
            if (profileInfo) profileInfo.classList.remove('dark-mode');
            if (toggleButton) toggleButton.classList.remove('dark-mode');
            cards.forEach(card => card.classList.remove('dark-mode'));
            modals.forEach(modal => modal.classList.remove('dark-mode'));
        }
    }

    // Function to toggle dark mode
    function toggleDarkMode() {
        const isDarkMode = !body.classList.contains('dark-mode');
        applyDarkMode(isDarkMode);

        // Save preference to localStorage
        if (isDarkMode) {
            localStorage.setItem('dark-mode', 'enabled');
        } else {
            localStorage.setItem('dark-mode', 'disabled');
        }
    }

    // Load dark mode state from localStorage
    const darkModeEnabled = localStorage.getItem('dark-mode') === 'enabled';
    applyDarkMode(darkModeEnabled); // Ensure dark mode state is consistent on page load

    // Event listener for toggle button click
    if (toggleButton) {
        toggleButton.addEventListener('click', toggleDarkMode);
    }
});
