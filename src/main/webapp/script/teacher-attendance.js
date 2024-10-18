document.addEventListener('DOMContentLoaded', function () {
    const courseSelect = document.getElementById('courseSelect');
    const attendanceDate = document.getElementById('attendanceDate');
    const studentsContainer = document.getElementById('studentsContainer');
    const attendanceCardsContainer = document.querySelector('.attendance-cards');

    let selectedTimeStart = null;
    let selectedTimeEnd = null;

    // Fetch and populate current date (India time zone)
    const indianTime = new Date().toLocaleString("en-US", { timeZone: "Asia/Kolkata" });
    const currentDate = new Date(indianTime);
    attendanceDate.value = currentDate.toISOString().split('T')[0];

    // Fetch teacher's assigned courses on page load
    fetchAssignedCourses();

    // Event listeners
    courseSelect.addEventListener('change', loadTimetable);
    attendanceDate.addEventListener('change', loadTimetable);
    document.getElementById('markAttendanceForm').addEventListener('submit', submitAttendance);

    // Fetch teacher's assigned courses from server
    function fetchAssignedCourses() {
        fetch('teacherAttendance?action=viewAssignedCourses')
            .then(response => response.json())
            .then(data => {
                const courses = {};
                data.assignedCourses.forEach(item => {
                    if (!courses[item.courseId]) {
                        courses[item.courseId] = item.courseName;
                    }
                });

                Object.entries(courses).forEach(([id, name]) => {
                    const option = document.createElement('option');
                    option.value = id;
                    option.textContent = name;
                    courseSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading assigned courses:', error));
    }

    // Load timetable based on selected course and date
    function loadTimetable() {
        const courseId = courseSelect.value;
        const selectedDate = new Date(attendanceDate.value);
        const dayOfWeek = selectedDate.toLocaleDateString('en-US', { weekday: 'long' });

        if (courseId) {
            fetch(`teacherAttendance?action=loadTimetable&courseId=${courseId}`)
                .then(response => response.json())
                .then(data => {
                    attendanceCardsContainer.innerHTML = ''; // Clear existing cards
                    const timetableByDay = {};

                    data.timetable.forEach(item => {
                        if (!timetableByDay[item.dayOfWeek]) {
                            timetableByDay[item.dayOfWeek] = [];
                        }
                        timetableByDay[item.dayOfWeek].push(item);
                    });

                    Object.entries(timetableByDay).forEach(([day, slots]) => {
                        const dayCard = document.createElement('div');
                        dayCard.classList.add('card', 'day-card');

                        const dayHeading = document.createElement('h3');
                        dayHeading.textContent = day;
                        dayCard.appendChild(dayHeading);

                        slots.forEach(slot => {
                            const timeSlotButton = document.createElement('button');
                            timeSlotButton.classList.add('time-slot-button');
                            timeSlotButton.textContent = `${slot.timeStart} - ${slot.timeEnd}`;
                            timeSlotButton.dataset.dayOfWeek = day;
                            timeSlotButton.dataset.timeStart = slot.timeStart;
                            timeSlotButton.dataset.timeEnd = slot.timeEnd;
                            timeSlotButton.dataset.courseId = slot.courseId || courseId;

                            // Only clickable if the slot matches the selected date's day
                            if (day === dayOfWeek) {
                                timeSlotButton.addEventListener('click', function () {
                                    loadStudentList(this);
                                });
                                timeSlotButton.classList.add('clickable-slot');
                            }

                            dayCard.appendChild(timeSlotButton);
                        });

                        attendanceCardsContainer.appendChild(dayCard);
                    });
                })
                .catch(error => console.error('Error loading timetable:', error));
        }
    }

    // Load students for the selected time slot
    function loadStudentList(slotButton) {
        const { courseId, dayOfWeek, timeStart, timeEnd } = slotButton.dataset;

        // Store the selected timeStart and timeEnd
        selectedTimeStart = timeStart;
        selectedTimeEnd = timeEnd;

        fetch(`teacherAttendance?action=viewStudents&courseId=${courseId}&dayOfWeek=${dayOfWeek}&timeStart=${timeStart}&timeEnd=${timeEnd}`)
            .then(response => response.json())
            .then(data => {
                studentsContainer.innerHTML = ''; // Clear existing students

                if (data.enrolledStudents.length > 0) {
                    data.enrolledStudents.forEach(student => {
                        const studentCard = document.createElement('div');
                        studentCard.classList.add('student-card');

                        const studentInfo = document.createElement('span');
                        studentInfo.textContent = `${student.studentName} (${student.studentId})`;

                        const selectAttendance = document.createElement('select');
                        selectAttendance.dataset.studentId = student.studentId;
                        selectAttendance.classList.add('select-attendance');

                        ['Present', 'Absent'].forEach(status => {
                            const option = document.createElement('option');
                            option.value = status;
                            option.textContent = status;
                            selectAttendance.appendChild(option);
                        });

                        studentCard.appendChild(studentInfo);
                        studentCard.appendChild(selectAttendance);
                        studentsContainer.appendChild(studentCard);
                    });
                } else {
                    studentsContainer.innerHTML = '<p>No students found for this slot.</p>';
                }
            })
            .catch(error => console.error('Error loading students:', error));
    }

    // Submit attendance
    function submitAttendance(e) {
        e.preventDefault();

        const courseId = courseSelect.value;
        const selectedDate = attendanceDate.value;
        const studentAttendance = [];

        // Collect attendance data
        studentsContainer.querySelectorAll('.select-attendance').forEach(select => {
            studentAttendance.push({
                studentId: select.dataset.studentId,
                status: select.value
            });
        });

        if (studentAttendance.length === 0) {
            alert('Please select a time slot and mark attendance for students.');
            return;
        }

        if (!selectedTimeStart || !selectedTimeEnd) {
            alert('Please select a time slot before submitting attendance.');
            return;
        }

        // Send attendance data to server
        fetch('teacherAttendance?action=markAttendance', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                courseId,
                date: selectedDate,
                timeStart: selectedTimeStart,
                timeEnd: selectedTimeEnd,
                studentAttendance
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    alert('Attendance marked successfully.');
                    studentsContainer.innerHTML = '';
                    selectedTimeStart = null;
                    selectedTimeEnd = null;
                } else {
                    alert(data.message || 'Failed to mark attendance.');
                }
            })
            .catch(error => console.error('Error marking attendance:', error));
    }
});
