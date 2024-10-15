document.addEventListener('DOMContentLoaded', function () {
    const registrationId = document.querySelector('body').getAttribute('data-registration-id');
    const tableBody = document.getElementById('coursesTableBody');
    const loadCoursesBtn = document.getElementById('loadCoursesBtn');

    function loadRegisteredCourses() {
        fetch(`viewCourses`)
            .then(response => response.json())
            .then(data => {
                tableBody.innerHTML = '';

                if (data.courses && data.courses.length > 0) {
                    data.courses.forEach(course => {
                        const row = `
                            <tr>
                              <td>${course.courseId}</td>
                              <td>${course.courseName}</td>
                 
                            </tr>
                        `;
                        tableBody.insertAdjacentHTML('beforeend', row);
                    });
                } else {
                    tableBody.innerHTML = '<tr><td colspan="3">No registered courses found</td></tr>';
                }
            })
            .catch(error => {
                console.error('Error fetching registered courses:', error);
            });
    }

    // Add event listener to the load button
    loadCoursesBtn.addEventListener('click', function () {
        loadRegisteredCourses();
    });
});
