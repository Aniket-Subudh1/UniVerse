document.addEventListener('DOMContentLoaded', function() {
    const feedbackForm = document.getElementById('feedbackForm');
    const courseTeacherSelect = document.getElementById('courseTeacherSelect');
    const feedbackMessage = document.getElementById('feedbackMessage');

    // Fetch registered courses and teachers when the page loads
    function loadCoursesAndTeachers() {
        fetch('submitFeedback') // Triggers the doGet method of SubmitFeedbackServlet
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    data.feedbackData.forEach(item => {
                        const option = `<option value="${item.courseId}|${item.teacherId}">
                                            ${item.courseName} - ${item.teacherName}
                                        </option>`;
                        courseTeacherSelect.insertAdjacentHTML('beforeend', option);
                    });
                } else {
                    feedbackMessage.textContent = 'Error loading courses and teachers.';
                    feedbackMessage.style.color = 'red';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                feedbackMessage.textContent = 'Error loading data. Please try again later.';
                feedbackMessage.style.color = 'red';
            });
    }

    // Load courses and teachers
    loadCoursesAndTeachers();

    // Handle form submission
    feedbackForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(feedbackForm);

        // Extract courseId and teacherId from the selected option
        const selectedValue = formData.get('courseTeacherSelect');
        const [courseId, teacherId] = selectedValue.split('|');
        formData.delete('courseTeacherSelect');
        formData.append('courseId', courseId);
        formData.append('teacherId', teacherId);

        // Submit feedback via POST request
        fetch('submitFeedback', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    feedbackMessage.textContent = 'Feedback submitted successfully!';
                    feedbackMessage.style.color = 'green';
                    feedbackForm.reset(); // Clear the form
                } else {
                    feedbackMessage.textContent = 'Error: ' + data.message;
                    feedbackMessage.style.color = 'red';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                feedbackMessage.textContent = 'Error submitting feedback. Please try again later.';
                feedbackMessage.style.color = 'red';
            });
    });
});