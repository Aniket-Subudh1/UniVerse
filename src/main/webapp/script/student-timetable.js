document.addEventListener('DOMContentLoaded', function () {
    const timetableContainer = document.getElementById('timetableContainer');

    // Fetch and display the student's timetable
    function loadStudentTimetable() {
        fetch('viewStudentTimetable')
            .then(response => response.json())
            .then(data => {
                timetableContainer.innerHTML = '';

                if (data.timetable && data.timetable.length > 0) {
                    data.timetable.forEach(item => {
                        const timetableRow = `
                            <div class="timetable-entry">
                                <h3>${item.courseName}</h3>
                                <p><strong>Day:</strong> ${item.dayOfWeek}</p>
                                <p><strong>Time:</strong> ${item.timeStart} - ${item.timeEnd}</p>
                            </div>
                        `;
                        timetableContainer.insertAdjacentHTML('beforeend', timetableRow);
                    });
                } else {
                    timetableContainer.innerHTML = '<p>No timetable entries found for your enrolled courses.</p>';
                }
            })
            .catch(error => {
                console.error('Error loading timetable:', error);
                timetableContainer.innerHTML = '<p>Error loading timetable. Please try again later.</p>';
            });
    }

    // Load the student's timetable on page load
    loadStudentTimetable();
});
