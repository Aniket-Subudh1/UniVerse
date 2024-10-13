document.addEventListener('DOMContentLoaded', function () {
    const coursesTableBody = document.getElementById('coursesTableBody');
    const errorMessage = document.getElementById('error-message');
    const loadCoursesButton = document.getElementById('loadCoursesButton');

    // Function to fetch assigned courses for the teacher
    function loadAssignedCourses() {
        fetch('teacherDashboard?action=fetchAssignedCourses')
            .then(response => response.json())
            .then(data => {
                coursesTableBody.innerHTML = ''; // Clear existing rows

                if (data.courses && data.courses.length > 0) {
                    data.courses.forEach(course => {
                        const row = `
                            <tr>
                                <td>${course.courseId}</td>
                                <td>${course.courseName}</td>
                            </tr>
                        `;
                        coursesTableBody.insertAdjacentHTML('beforeend', row);
                    });
                } else {
                    errorMessage.style.display = 'block';
                    coursesTableBody.innerHTML = '<tr><td colspan="2">No courses found</td></tr>';
                }
            })
            .catch(error => {
                console.error('Error loading courses:', error);
                errorMessage.style.display = 'block';
                coursesTableBody.innerHTML = '<tr><td colspan="2">Error loading courses</td></tr>';
            });
    }

    // Load assigned courses when the "Load Assigned Courses" button is clicked
    loadCoursesButton.addEventListener('click', loadAssignedCourses);
});
