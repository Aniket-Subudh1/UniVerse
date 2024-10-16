document.addEventListener('DOMContentLoaded', function () {
    const timetableTableBody = document.getElementById('timetableTableBody');

    // Function to load the timetable for the logged-in teacher
    function loadTimetable() {
        fetch('viewTeacherTimetable?action=fetchTimetable')
            .then(response => response.json())
            .then(data => {
                timetableTableBody.innerHTML = '';  // Clear the existing table rows

                if (data.timetable && data.timetable.length > 0) {
                    data.timetable.forEach(item => {
                        const row = `
                            <tr>
                                <td>${item.enrolledCourse}</td>
                                <td>${item.courseName}</td>
                                <td>${item.dayOfWeek}</td>
                                <td>${item.timeStart}</td>
                                <td>${item.timeEnd}</td>
                            </tr>
                        `;
                        timetableTableBody.insertAdjacentHTML('beforeend', row);  // Insert new row
                    });
                } else {
                    timetableTableBody.innerHTML = '<tr><td colspan="5">No timetable entries found.</td></tr>';
                }
            })
            .catch(error => {
                console.error('Error fetching timetable:', error);
                timetableTableBody.innerHTML = '<tr><td colspan="5">Failed to load timetable.</td></tr>';
            });
    }

    // Call the loadTimetable function when the page loads
    loadTimetable();
});
