document.addEventListener('DOMContentLoaded', function () {
    const coursesContainer = document.getElementById('coursesContainer');
    const messageDiv = document.getElementById('message');

    // Assuming studentRegistrationId is coming from a hidden field or a session-based value
    const studentRegistrationId = '<%= session.getAttribute("registrationId") %>'; // JSP or fetch it accordingly

    // Fetch available courses and display them as cards
    function loadCourses() {
        fetch('registerCourse')
            .then(response => response.json())
            .then(data => {
                coursesContainer.innerHTML = ''; // Clear existing cards

                if (data.length > 0) {
                    data.forEach(course => {
                        const card = document.createElement('div');
                        card.className = 'card'; // Assign 'card' class

                        card.innerHTML = `
                            <h3>${course.courseName}</h3>
                            <p>${course.courseDescription}</p>
                            <button class="register-btn" data-course-id="${course.courseId}" data-registration-id="${studentRegistrationId}">Register</button>
                        `;

                        coursesContainer.appendChild(card);
                    });

                    // Attach event listeners to all register buttons
                    const registerButtons = document.querySelectorAll('.register-btn');
                    registerButtons.forEach(button => {
                        button.addEventListener('click', registerCourse);
                    });
                } else {
                    coursesContainer.innerHTML = '<p>No courses available</p>';
                }
            })
            .catch(error => {
                console.error('Error fetching courses:', error);
            });
    }

    // Register for a course when a card button is clicked
    function registerCourse(event) {
        const courseId = event.target.getAttribute('data-course-id');
        const registrationId = event.target.getAttribute('data-registration-id'); // Get student's registration ID

        fetch('registerCourse', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `courseId=${courseId}&registrationId=${registrationId}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    messageDiv.innerHTML = `<p class="success">Successfully registered for course ${courseId}</p>`;
                } else if (data.status === 'duplicate') {
                    messageDiv.innerHTML = `<p class="error">You are already registered for course ${courseId}</p>`;
                } else {
                    messageDiv.innerHTML = `<p class="error">Failed to register for course ${courseId}</p>`;
                }
            })
            .catch(error => {
                console.error('Error registering for course:', error);
                messageDiv.innerHTML = '<p class="error">Error registering for course</p>';
            });
    }

    // Load available courses on page load
    loadCourses();
});
