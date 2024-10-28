document.addEventListener('DOMContentLoaded', () => {
    const teacherDropdown = document.getElementById('teacherDropdown');
    const feedbackContainer = document.querySelector('.feedback-list');
    const selectedTeacherName = document.getElementById('selectedTeacherName');

    // Load teachers into the dropdown
    function loadTeachers() {
        fetch('viewTeacherFeedback')
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success' && data.teachers) {
                    teacherDropdown.innerHTML = '<option value="">Select a teacher</option>';
                    data.teachers.forEach(teacher => {
                        const option = document.createElement('option');
                        option.value = teacher.teacherId;
                        option.textContent = teacher.teacherName;
                        teacherDropdown.appendChild(option);
                    });
                } else {
                    console.error('Failed to load teachers:', data.message);
                }
            })
            .catch(error => console.error('Error loading teachers:', error));
    }

    // Load feedback for the selected teacher
    function loadFeedback(teacherId) {
        fetch(`viewTeacherFeedback?teacherId=${teacherId}`)
            .then(response => response.json())
            .then(data => {
                feedbackContainer.innerHTML = ''; // Clear previous feedback
                if (data.status === 'success' && data.feedback.length > 0) {
                    data.feedback.forEach(entry => {
                        const feedbackItem = document.createElement('div');
                        feedbackItem.classList.add('feedback-entry');
                        feedbackItem.innerHTML = `
                            <p><strong>Course:</strong> ${entry.courseName}</p>
                            <p><strong>Rating:</strong> ${entry.rating}</p>
                            <p><strong>Comments:</strong> ${entry.comments}</p>
                            <p><strong>Student ID:</strong> ${entry.studentId}</p>
                            <button class="reviewed-btn" data-feedback-id="${entry.id}">Reviewed</button>
                        `;
                        feedbackContainer.appendChild(feedbackItem);
                    });

                    // Add event listeners to "Reviewed" buttons
                    document.querySelectorAll('.reviewed-btn').forEach(button => {
                        button.addEventListener('click', (event) => {
                            const feedbackId = event.target.getAttribute('data-feedback-id');
                            markAsReviewed(feedbackId);
                        });
                    });
                } else {
                    feedbackContainer.innerHTML = '<p>No feedback available for the selected teacher.</p>';
                }
            })
            .catch(error => {
                console.error('Error loading feedback:', error);
                feedbackContainer.innerHTML = '<p>Error loading feedback. Please try again later.</p>';
            });
    }

    function markAsReviewed(feedbackId) {
        if (!feedbackId) {
            console.error('Invalid feedback ID:', feedbackId);
            alert('Failed to mark feedback as reviewed: invalid feedback ID.');
            return;
        }

        fetch('viewTeacherFeedback', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `feedbackId=${encodeURIComponent(feedbackId)}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    alert('Feedback marked as reviewed and deleted successfully.');
                    const selectedTeacherId = teacherDropdown.value;
                    if (selectedTeacherId) {
                        loadFeedback(selectedTeacherId); // Reload feedback after deletion
                    }
                } else {
                    alert(data.message || 'Failed to mark feedback as reviewed.');
                }
            })
            .catch(error => console.error('Error marking feedback as reviewed:', error));
    }

    // Event listener for teacher selection
    teacherDropdown.addEventListener('change', () => {
        const selectedTeacher = teacherDropdown.options[teacherDropdown.selectedIndex];
        const teacherId = selectedTeacher.value;
        selectedTeacherName.textContent = selectedTeacher.textContent || 'Selected Teacher';

        if (teacherId) {
            loadFeedback(teacherId);
        } else {
            feedbackContainer.innerHTML = '<p>Select a teacher to view feedback.</p>';
        }
    });

    // Initial load
    loadTeachers();
});
