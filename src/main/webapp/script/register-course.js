document.addEventListener('DOMContentLoaded', function () {
    const coursesContainer = document.getElementById('coursesContainer');
    const modalMessage = document.getElementById('modalMessage');
    const successModal = document.getElementById('successModal');
    const closeModalButton = document.getElementById('closeModalButton');


    const studentRegistrationId = '<%= session.getAttribute("registrationId") %>';

    // Fetch available courses and display them as cards
    function loadCourses() {
        fetch('registerCourse')
            .then(response => response.json())
            .then(data => {
                coursesContainer.innerHTML = ''; // Clear existing cards

                if (data.length > 0) {
                    data.forEach(course => {
                        const card = document.createElement('div');
                        card.className = 'course-card'; // Assign 'card' class

                        card.innerHTML = `
                           <h3>${course.courseName}</h3>
                            <h4>${course.courseId}</h4>
                            <button  class="register-btn" data-course-id="${course.courseId}" data-registration-id="${studentRegistrationId}">  Register </button>
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
                    modalMessage.innerHTML = `Successfully registered for course ${courseId}`;
                } else if (data.status === 'duplicate') {
                    modalMessage.innerHTML = `You are already registered for course ${courseId}`;
                } else {
                    modalMessage.innerHTML = `Failed to register for course ${courseId}`;
                }
                successModal.style.display = 'flex'; // Show modal
            })
            .catch(error => {
                console.error('Error registering for course:', error);
                modalMessage.innerHTML = 'Error registering for course';
                successModal.style.display = 'flex'; // Show modal
            });
    }

    // Close modal
    closeModalButton.addEventListener('click', function () {
        successModal.style.display = 'none'; // Hide modal on button click
    });

    // Load available courses on page load
    loadCourses();
});
