// Function to toggle the success modal
function toggleSuccessModal(show) {
    const successModal = document.getElementById('successModal');
    successModal.style.display = show ? 'block' : 'none';
}

// Function to toggle the error modal
function toggleErrorModal(show) {
    const errorModal = document.getElementById('errorModal');
    errorModal.style.display = show ? 'block' : 'none';
}

// Close success modal and redirect to the appropriate dashboard
function closeSuccessModal() {
    toggleSuccessModal(false);

    // Check the user's role and redirect accordingly
    const userRole = document.getElementById('userRole').value; // Assuming you have a hidden input or element with the role

    if (userRole === 'student') {
        window.location.href = 'student-dashboard.jsp'; // Redirect to the student dashboard
    } else if (userRole === 'teacher') {
        window.location.href = 'teacher-dashboard.jsp'; // Redirect to the teacher dashboard
    } else {
        window.location.href = 'dashboard.jsp'; // Fallback to a general dashboard or home page
    }
}

// Close error modal
function closeErrorModal() {
    toggleErrorModal(false);
}

// Handle form submission with AJAX
document.getElementById('editProfileForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);

    fetch('editProfile', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(text => {
            if (text === 'success') {
                toggleSuccessModal(true);
            } else {
                toggleErrorModal(true);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            toggleErrorModal(true);
        });
});
