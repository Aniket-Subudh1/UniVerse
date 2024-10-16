document.addEventListener('DOMContentLoaded', function () {
    const createRoutineForm = document.getElementById('createRoutineForm');
    const successModal = document.getElementById('successModal');
    const routineTableBody = document.getElementById('routineTableBody');
    const closeModalButton = document.getElementById('closeModalButton');
    const editRoutineForm = document.getElementById('editRoutineForm');


    // Load available courses into the dropdown
    function loadCourses() {
        fetch('adminRoutine?action=loadCourses')
            .then(response => response.json())
            .then(data => {
                const courseSelect = document.getElementById('courseId');
                const filterCourseSelect = document.getElementById('filterCourse');
                courseSelect.innerHTML = '<option value="">-- Select Course --</option>';
                filterCourseSelect.innerHTML = '<option value="">-- Select Course --</option>';

                if (data.courses && data.courses.length > 0) {
                    data.courses.forEach(course => {
                        const option = `<option value="${course.courseId}">${course.name}</option>`;
                        courseSelect.insertAdjacentHTML('beforeend', option);
                        filterCourseSelect.insertAdjacentHTML('beforeend', option);
                    });
                }
            })
            .catch(error => console.error('Error loading courses:', error));
    }

// You can add similar logic for loading enrolled courses into the 'filterEnrolledCourse' dropdown if needed.


    // Load available teachers into the dropdown
    function loadTeachers() {
        fetch('adminRoutine?action=loadTeachers')
            .then(response => response.json())
            .then(data => {
                const teacherSelect = document.getElementById('teacherId');
                teacherSelect.innerHTML = '<option value="">-- Select Teacher --</option>';

                if (data.teachers && data.teachers.length > 0) {
                    data.teachers.forEach(teacher => {
                        const option = `<option value="${teacher.registrationId}">${teacher.name}</option>`;
                        teacherSelect.insertAdjacentHTML('beforeend', option);
                    });
                }
            })
            .catch(error => console.error('Error loading teachers:', error));
    }

    // Load existing routine into the table with filters
    function loadRoutine() {
        const courseFilter = document.getElementById('filterCourse').value;


        const queryParams = new URLSearchParams({
            action: 'loadTimetable',
            courseId: courseFilter
        });

        fetch(`adminRoutine?${queryParams.toString()}`)
            .then(response => response.json())
            .then(data => {
                routineTableBody.innerHTML = '';

                if (data.timetable && data.timetable.length > 0) {
                    data.timetable.forEach(item => {
                        const row = `
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.enrolledCourse}</td>
                            <td>${item.dayOfWeek}</td>
                            <td>${item.timeStart}</td>
                            <td>${item.timeEnd}</td>
                            <td>${item.courseName}</td>
                            <td>${item.teacherName}</td>
                            <td>
                                <button class="btn edit" onclick="editRoutine(${item.id})">Edit</button>
                                <button class="btn delete" onclick="deleteRoutine(${item.id})">Delete</button>
                            </td>
                        </tr>
                    `;
                        routineTableBody.insertAdjacentHTML('beforeend', row);
                    });
                } else {
                    routineTableBody.innerHTML = '<tr><td colspan="8">No routines found</td></tr>';
                }
            })
            .catch(error => console.error('Error loading routine:', error));
    }


    // Handle routine creation form submission
    createRoutineForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(createRoutineForm);
        fetch('adminRoutine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(formData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    successModal.style.display = 'flex';
                    loadRoutine(); // Reload the routine table
                } else {
                    console.error('Error creating routine:', data.message);
                }
            })
            .catch(error => console.error('Error creating routine:', error));
    });

    // Close modals when the "OK" buttons are clicked
    closeModalButton.addEventListener('click', function () {
        successModal.style.display = 'none';
    });

    // Call load functions on page load
    loadCourses();
    loadTeachers();
    loadRoutine();

});


function deleteRoutine(routineId) {
    if (confirm('Are you sure you want to delete this routine?')) {
        fetch(`adminRoutine?action=delete&routineId=${routineId}`, {
            method: 'POST'
        })
            .then(() => {
                alert('Routine deleted successfully');
                loadRoutine();
            })
            .catch(error => console.error('Error deleting routine:', error));
    }
}

// Function to open the edit modal and pre-fill form with routine data
function editRoutine(routineId) {
    fetch(`adminRoutine?action=getRoutineById&routineId=${routineId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('editRoutineId').value = data.id;
            document.getElementById('editEnrolledCourse').value = data.enrolledCourse;
            document.getElementById('editDayOfWeek').value = data.dayOfWeek;
            document.getElementById('editTimeStart').value = data.timeStart;
            document.getElementById('editTimeEnd').value = data.timeEnd;
            document.getElementById('editCourseId').value = data.courseId;
            document.getElementById('editTeacherId').value = data.teacherId;

            document.getElementById('editModal').style.display = 'flex';
        })
        .catch(error => console.error('Error fetching routine details:', error));
}

// Handle routine editing
editRoutineForm.addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = new FormData(editRoutineForm);
    fetch('adminRoutine', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                alert('Routine updated successfully');
                loadRoutine(); // Reload the routine table
            } else {
                console.error('Error updating routine:', data.message);
            }
        })
        .catch(error => console.error('Error updating routine:', error));
});
