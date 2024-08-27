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

    // Any additional page-specific functionality can go here
});
