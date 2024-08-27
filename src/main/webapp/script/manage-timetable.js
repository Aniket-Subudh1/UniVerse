// Function to toggle dark mode
function toggleDarkMode() {
    const body = document.body;
    const darkModeToggle = document.getElementById('dark-mode-toggle');

    // Toggle dark mode class on body
    body.classList.toggle('dark-mode');

    // Toggle dark mode class on the dark mode toggle button
    darkModeToggle.classList.toggle('dark-mode');

    // Save the dark mode preference to local storage
    if (body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
}

// Function to load dark mode preference from local storage
function loadDarkModePreference() {
    const body = document.body;
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const darkMode = localStorage.getItem('darkMode');

    if (darkMode === 'enabled') {
        body.classList.add('dark-mode');
        darkModeToggle.classList.add('dark-mode');
    } else {
        body.classList.remove('dark-mode');
        darkModeToggle.classList.remove('dark-mode');
    }
}

// Function to add hover effect on cards
function addCardHoverEffect() {
    const cards = document.querySelectorAll('.card');

    cards.forEach(card => {
        card.addEventListener('mouseover', () => {
            card.classList.add('hovered');
        });

        card.addEventListener('mouseout', () => {
            card.classList.remove('hovered');
        });
    });
}

// Function to initialize the dashboard functionalities
function initializeDashboard() {
    loadDarkModePreference();  // Load dark mode setting from local storage
    addCardHoverEffect();      // Add hover effects to the cards
}

// Run the initialize function once the page content is loaded
document.addEventListener('DOMContentLoaded', initializeDashboard);
