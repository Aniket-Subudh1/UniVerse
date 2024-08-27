document.addEventListener('DOMContentLoaded', function () {
    const profileModal = document.getElementById('profileModal');

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

    // Any additional page-specific functionality can go here
});
