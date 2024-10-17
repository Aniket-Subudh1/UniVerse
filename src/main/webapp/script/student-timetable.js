document.addEventListener('DOMContentLoaded', function () {
    const timetableContainer = document.getElementById('timetableContainer');

    // Fetch and display the student's timetable
    function loadStudentTimetable() {
        fetch('viewStudentTimetable')
            .then(response => {
                console.log('Fetch Response:', response); // Log the response object
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data received from server:', data); // Log the data
                timetableContainer.innerHTML = ''; // Clear previous data

                // Check if timetable data exists
                if (data && data.timetable && data.timetable.length > 0) {
                    // Group timetable entries by day
                    const timetableByDay = {};
                    data.timetable.forEach(item => {
                        if (!timetableByDay[item.dayOfWeek]) {
                            timetableByDay[item.dayOfWeek] = [];
                        }
                        timetableByDay[item.dayOfWeek].push(item);
                    });

                    // Iterate through each day and display the timetable
                    for (const [day, entries] of Object.entries(timetableByDay)) {
                        const dayRow = `
                            <div class="day-section">
                                <h2>${day}</h2>
                                <div class="day-timetable">
                                    ${entries.map(item => `
                                        <div class="timetable-entry">
                                            <h3>${item.courseName}</h3>
                                            <p><strong>Time:</strong> ${item.timeStart} - ${item.timeEnd}</p>
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        `;
                        timetableContainer.insertAdjacentHTML('beforeend', dayRow);
                    }
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
