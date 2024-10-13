// Function to load courses from the server and populate the table
function loadCourses() {
    fetch('createCourse?action=load')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('courseTableBody');
            tableBody.innerHTML = ''; // Clear existing rows

            if (data.courses.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="4">No courses found</td></tr>';
            } else {
                data.courses.forEach(course => {
                    const row = `
            <tr>
              <td>${course.courseId}</td>
              <td>${course.courseName}</td>
              <td>${course.courseDescription}</td>
              <td>
                <button class="btn delete" onclick="deleteCourse('${course.courseId}')">Delete</button>
                <button class="btn edit" onclick="editCourse('${course.courseId}', '${course.courseName}', '${course.courseDescription}')">Edit</button>
              </td>
            </tr>
          `;
                    tableBody.insertAdjacentHTML('beforeend', row);
                });
            }
        })
        .catch(error => console.error('Error loading courses:', error));
}

// Function to open the edit modal with course details
function editCourse(courseId, courseName, courseDescription) {
    document.getElementById('editCourseId').value = courseId;
    document.getElementById('editCourseName').value = courseName;
    document.getElementById('editCourseDescription').value = courseDescription;
    document.getElementById('editModal').style.display = 'flex';
}

// Function to delete a course
function deleteCourse(courseId) {
    if (confirm('Are you sure you want to delete this course?')) {
        fetch(`createCourse?action=delete&courseId=${courseId}`, {
            method: 'POST'
        }).then(() => {
            alert('Course deleted successfully');
            loadCourses(); // Reload the table after deletion
        }).catch(error => console.error('Error deleting course:', error));
    }
}

// Function to filter/search courses by name
function filterCourses() {
    const searchValue = document.getElementById('searchCourse').value.toLowerCase();
    const rows = document.querySelectorAll('#courseTable tbody tr');
    rows.forEach(row => {
        const courseName = row.cells[1].textContent.toLowerCase();
        row.style.display = courseName.includes(searchValue) ? '' : 'none';
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const createCourseForm = document.getElementById('createCourseForm');
    const successModal = document.getElementById('successModal');
    const errorModal = document.getElementById('errorModal');
    const modalMessage = document.getElementById('modalMessage');
    const errorMessage = document.getElementById('errorMessage');
    const closeModalButton = document.getElementById('closeModalButton');
    const closeErrorButton = document.getElementById('closeErrorButton');

    // Handle form submission for creating a course
    createCourseForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(createCourseForm);
        const courseId = formData.get('courseId');
        const courseName = formData.get('courseName');
        const courseDescription = formData.get('courseDescription');

        fetch('createCourse', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                courseId: courseId,
                courseName: courseName,
                courseDescription: courseDescription
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    modalMessage.innerHTML = data.message;
                    successModal.style.display = 'flex'; // Show success modal
                    loadCourses(); // Reload courses after creation
                } else if (data.status === 'exists') {
                    errorMessage.innerHTML = data.message;
                    errorModal.style.display = 'flex'; // Show error modal
                }
            })
            .catch(error => {
                console.error('Error creating course:', error);
                errorMessage.innerHTML = 'An error occurred. Please try again.';
                errorModal.style.display = 'flex'; // Show error modal
            });
    });

    // Close modals when the "OK" buttons are clicked
    closeModalButton.addEventListener('click', function () {
        successModal.style.display = 'none';
    });

    closeErrorButton.addEventListener('click', function () {
        errorModal.style.display = 'none';
    });
});
