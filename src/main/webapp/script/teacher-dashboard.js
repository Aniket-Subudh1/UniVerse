document.addEventListener('DOMContentLoaded', function () {
    const profileModal = document.getElementById('profileModal');

    // Function to show the profile modal
    window.showProfileModal = function () {
        profileModal.style.display = 'flex';
    };

    // Function to close the profile modal
    window.closeProfileModal = function () {
        profileModal.style.display = 'none';
    };

    // Close the modal when the user clicks outside of it
    window.onclick = function(event) {
        if (event.target === profileModal) {
            profileModal.style.display = 'none';
        }
    };
});
