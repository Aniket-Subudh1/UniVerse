document.addEventListener('DOMContentLoaded', function () {
    const courseTableBody = document.getElementById('courseTableBody');
    const teacherTableBody = document.getElementById('teacherTableBody');
    const successModal = document.getElementById('successModal');
    const closeModalButton = document.getElementById('closeModalButton');

    // Function to load courses into the dropdown
    function loadCourses() {
        fetch('assignSubjects?action=loadCourses')
            .then(response => response.json())
            .then(data => {
                const courseSelect = document.getElementById('courseId');
                courseSelect.innerHTML = '<option value="">-- Select Course --</option>'; // Clear existing options
                courseTableBody.innerHTML = ''; // Clear table

                if (data.courses.length > 0) {
                    data.courses.forEach(course => {
                        const option = `<option value="${course.courseId}">${course.courseName}</option>`;
                        courseSelect.insertAdjacentHTML('beforeend', option);

                        const row = `
                            <tr>
                                <td>${course.courseId}</td>
                                <td>${course.courseName}</td>
                                <td>${course.courseDescription}</td>
                            </tr>
                        `;
                        courseTableBody.insertAdjacentHTML('beforeend', row);
                    });
                }
            })
            .catch(error => console.error('Error loading courses:', error));
    }

    // Function to load teachers into the dropdown
    function loadTeachers() {
        fetch('assignSubjects?action=loadTeachers')
            .then(response => response.json())
            .then(data => {
                const teacherSelect = document.getElementById('registrationId');
                teacherSelect.innerHTML = '<option value="">-- Select Teacher --</option>'; // Clear existing options
                teacherTableBody.innerHTML = ''; // Clear table

                if (data.teachers.length > 0) {
                    data.teachers.forEach(teacher => {
                        const option = `<option value="${teacher.registrationId}">${teacher.teacherName}</option>`;
                        teacherSelect.insertAdjacentHTML('beforeend', option);

                        const row = `
                            <tr>
                                <td>${teacher.registrationId}</td>
                                <td>${teacher.teacherName}</td>
                                <td><img src="data:image/jpeg;base64,${teacher.photo}" alt="Teacher Photo" width="50" height="50"></td>
                            </tr>
                        `;
                        teacherTableBody.insertAdjacentHTML('beforeend', row);
                    });
                }
            })
            .catch(error => console.error('Error loading teachers:', error));
    }

    // Function to handle success modal appearance
    function checkForSuccess() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('success')) {
            successModal.style.display = 'flex'; // Show modal
        }

        // Close modal when "OK" is clicked
        closeModalButton.addEventListener('click', function () {
            successModal.style.display = 'none';
            window.location.href = 'assign-subjects.jsp'; // Reload page
        });
    }

    // Load both courses and teachers when the page is ready
    loadCourses();
    loadTeachers();
    checkForSuccess();
});
