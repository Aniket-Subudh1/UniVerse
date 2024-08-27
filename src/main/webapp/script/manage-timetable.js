document.addEventListener('DOMContentLoaded', function () {
    const toggleButton = document.getElementById('dark-mode-toggle');
    const body = document.body;
    const navbar = document.querySelector('.navbar');
    const profileInfo = document.querySelector('.profile-info');
    const cards = document.querySelectorAll('.timetable-card');

    // Function to toggle dark mode
    function toggleDarkMode() {
        body.classList.toggle('dark-mode');
        navbar.classList.toggle('dark-mode');
        profileInfo.classList.toggle('dark-mode');
        toggleButton.classList.toggle('dark-mode');

        // Toggle dark mode class on each card
        cards.forEach(card => {
            card.classList.toggle('dark-mode');
        });

        // Save the dark mode preference to local storage
        if (body.classList.contains('dark-mode')) {
            localStorage.setItem('darkMode', 'enabled');
        } else {
            localStorage.setItem('darkMode', 'disabled');
        }
    }

    // Function to load dark mode preference from local storage
    function loadDarkModePreference() {
        const darkMode = localStorage.getItem('darkMode');

        if (darkMode === 'enabled') {
            body.classList.add('dark-mode');
            navbar.classList.add('dark-mode');
            profileInfo.classList.add('dark-mode');
            toggleButton.classList.add('dark-mode');
            cards.forEach(card => {
                card.classList.add('dark-mode');
            });
        } else {
            body.classList.remove('dark-mode');
            navbar.classList.remove('dark-mode');
            profileInfo.classList.remove('dark-mode');
            toggleButton.classList.remove('dark-mode');
            cards.forEach(card => {
                card.classList.remove('dark-mode');
            });
        }
    }

    // Load dark mode setting from local storage on page load
    loadDarkModePreference();

    // Event listener for toggle button click
    toggleButton.addEventListener('click', toggleDarkMode);
});
