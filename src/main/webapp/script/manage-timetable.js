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

    // Additional page-specific functionality (if needed)
    // ...

    // Dark mode toggle and state management are handled universally.
});