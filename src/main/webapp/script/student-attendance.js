document.addEventListener('DOMContentLoaded', function () {
    const attendanceRecordsContainer = document.getElementById('attendanceRecordsContainer');

    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const filterButton = document.getElementById('filterButton');

    // Function to fetch and display attendance records
    function fetchAttendanceRecords(startDate = '', endDate = '') {
        const baseUrl = `${window.location.origin}${window.location.pathname.replace(/\/[^/]*$/, '')}`;
        let url = `${baseUrl}/studentAttendance`;

        // Add date parameters if provided
        const params = new URLSearchParams();
        if (startDate) params.append('startDate', startDate);
        if (endDate) params.append('endDate', endDate);

        if (params.toString()) {
            url += `?${params.toString()}`;
        }

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    // Try to parse error message from JSON, if not, return text
                    return response.text().then(errorText => {
                        let errorMessage;
                        try {
                            const errorJson = JSON.parse(errorText);
                            errorMessage = errorJson.error || 'Unknown error';
                        } catch (e) {
                            errorMessage = errorText;
                        }
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Fetched Attendance Data:', data); // Debugging line
                attendanceRecordsContainer.innerHTML = '';

                if (data.attendanceRecords && data.attendanceRecords.length > 0) {
                    data.attendanceRecords.forEach(record => {
                        const recordCard = document.createElement('div');
                        recordCard.classList.add('attendance-card');

                        const courseName = document.createElement('h3');
                        courseName.textContent = `${record.courseName} (${record.courseId})`;

                        const attendanceDate = document.createElement('p');
                        attendanceDate.textContent = `Date: ${record.date}`;

                        const attendanceTime = document.createElement('p');
                        attendanceTime.textContent = `Time: ${record.timeStart} - ${record.timeEnd}`;

                        const attendanceStatus = document.createElement('p');
                        attendanceStatus.textContent = `Status: ${record.status}`;
                        attendanceStatus.classList.add(record.status.toLowerCase());

                        recordCard.appendChild(courseName);
                        recordCard.appendChild(attendanceDate);
                        recordCard.appendChild(attendanceTime);
                        recordCard.appendChild(attendanceStatus);

                        attendanceRecordsContainer.appendChild(recordCard);
                    });
                } else {
                    attendanceRecordsContainer.innerHTML = '<p>No attendance records found for your registered courses.</p>';
                }
            })
            .catch(error => {
                console.error('Error fetching attendance records:', error);
                attendanceRecordsContainer.innerHTML = `<p>Error loading attendance records: ${error.message}</p>`;
            });
    }

    // Event listener for filter button
    filterButton.addEventListener('click', function () {
        const startDate = startDateInput.value;
        const endDate = endDateInput.value;
        fetchAttendanceRecords(startDate, endDate);
    });

    // Fetch attendance records on page load without date filters
    fetchAttendanceRecords();
});
