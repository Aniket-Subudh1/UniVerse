document.addEventListener('DOMContentLoaded', function () {
    const profileModal = document.getElementById('profileModal');

    window.showProfileModal = function () {
        profileModal.style.display = 'flex';
    };

    window.closeProfileModal = function () {
        profileModal.style.display = 'none';
    };
});