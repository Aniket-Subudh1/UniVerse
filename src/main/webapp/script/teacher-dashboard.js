document.addEventListener('DOMContentLoaded', function () {
    const profileModal = document.getElementById('profileModal');
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const navbar = document.querySelector('.navbar');
    const profileInfoElements = document.querySelectorAll('.profile-info');
    const cardElements = document.querySelectorAll('.card');
    const modalContentElements = document.querySelectorAll('.modal-content');

    // Show the profile modal
    window.showProfileModal = function () {
        if (profileModal) {
            profileModal.style.display = 'flex';
        }
    };

    // Close the profile modal
    window.closeProfileModal = function () {
        if (profileModal) {
            profileModal.style.display = 'none';
        }
    };

    // Toggle dark mode
    window.toggleDarkMode = function () {
        document.body.classList.toggle('dark-mode');
        if (navbar) {
            navbar.classList.toggle('dark-mode');
        }
        if (darkModeToggle) {
            darkModeToggle.classList.toggle('dark-mode');
        }
        profileInfoElements.forEach(profile => profile.classList.toggle('dark-mode'));
        cardElements.forEach(card => card.classList.toggle('dark-mode'));
        modalContentElements.forEach(modal => modal.classList.toggle('dark-mode'));

        // Save dark mode state to localStorage
        if (document.body.classList.contains('dark-mode')) {
            localStorage.setItem('dark-mode', 'enabled');
        } else {
            localStorage.removeItem('dark-mode');
        }
    };

    // Load dark mode state from localStorage
    if (localStorage.getItem('dark-mode') === 'enabled') {
        document.body.classList.add('dark-mode');
        if (navbar) {
            navbar.classList.add('dark-mode');
        }
        if (darkModeToggle) {
            darkModeToggle.classList.add('dark-mode');
        }
        profileInfoElements.forEach(profile => profile.classList.add('dark-mode'));
        cardElements.forEach(card => card.classList.add('dark-mode'));
        modalContentElements.forEach(modal => modal.classList.add('dark-mode'));
    }
});
