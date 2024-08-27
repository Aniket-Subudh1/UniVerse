document.addEventListener('DOMContentLoaded', function () {
    const profileModal = document.getElementById('profileModal');

    // Show the profile modal
    window.showProfileModal = function () {
        profileModal.style.display = 'flex';
    };

    // Close the profile modal
    window.closeProfileModal = function () {
        profileModal.style.display = 'none';
    };

    // Toggle dark mode
    window.toggleDarkMode = function () {
        document.body.classList.toggle('dark-mode');
        document.querySelector('.navbar').classList.toggle('dark-mode');
        document.querySelectorAll('.profile-info').forEach(profile => profile.classList.toggle('dark-mode'));
        document.getElementById('dark-mode-toggle').classList.toggle('dark-mode');
    };

    // Load dark mode state from localStorage
    if (localStorage.getItem('dark-mode') === 'enabled') {
        document.body.classList.add('dark-mode');
        document.querySelector('.navbar').classList.add('dark-mode');
        document.querySelectorAll('.profile-info').forEach(profile => profile.classList.add('dark-mode'));
        document.getElementById('dark-mode-toggle').classList.add('dark-mode');
    }
});
