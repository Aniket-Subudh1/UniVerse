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
