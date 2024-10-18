document.addEventListener('DOMContentLoaded', function () {
    const teacherSelect = document.getElementById('teacherSelect');
    const courseSelect = document.getElementById('courseSelect');
    const attendanceDate = document.getElementById('attendanceDate');
    const timetableContainer = document.getElementById('timetableContainer');
    const studentsContainer = document.getElementById('studentsContainer');

    // Global variables to store selected timeStart and timeEnd
    let selectedTimeStart = null;
    let selectedTimeEnd = null;

    // Fetch and populate current date (India time zone)
    const indianTime = new Date().toLocaleString("en-US", { timeZone: "Asia/Kolkata" });
    const currentDate = new Date(indianTime);
    attendanceDate.value = currentDate.toISOString().split('T')[0];

    // Fetch teachers and courses on page load
    fetchTeachersAndCourses();

    // Event listeners
    teacherSelect.addEventListener('change', loadTimetable);
    courseSelect.addEventListener('change', loadTimetable);
    attendanceDate.addEventListener('change', loadTimetable);

    // Fetch teachers and courses from server
    function fetchTeachersAndCourses() {
        fetch('adminAttendance?action=viewCourses')
            .then(response => response.json())
            .then(data => {
                const teachers = {};
                data.assignedCourses.forEach(item => {
                    if (!teachers[item.teacherId]) {
                        teachers[item.teacherId] = item.teacherName;
                    }
                });

                Object.entries(teachers).forEach(([id, name]) => {
                    const option = document.createElement('option');
                    option.value = id;
                    option.textContent = name;
                    teacherSelect.appendChild(option);
                });

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
            .catch(error => console.error('Error loading teachers and courses:', error));
    }

    // Load timetable based on selected teacher, course, and date
    function loadTimetable() {
        const teacherId = teacherSelect.value;
        const courseId = courseSelect.value;
        const selectedDate = new Date(attendanceDate.value);
        const dayOfWeek = selectedDate.toLocaleDateString('en-US', { weekday: 'long' });

        if (teacherId && courseId) {
            fetch(`adminAttendance?action=loadTimetable&teacherId=${teacherId}&courseId=${courseId}`)
                .then(response => response.json())
                .then(data => {
                    timetableContainer.innerHTML = ''; // Clear existing timetable
                    const timetableByDay = {};

                    data.timetable.forEach(item => {
                        if (!timetableByDay[item.dayOfWeek]) {
                            timetableByDay[item.dayOfWeek] = [];
                        }
                        timetableByDay[item.dayOfWeek].push(item);
                    });

                    Object.entries(timetableByDay).forEach(([day, slots]) => {
                        const dayContainer = document.createElement('div');
                        dayContainer.classList.add('day-container');

                        const dayHeading = document.createElement('h3');
                        dayHeading.textContent = day;
                        dayContainer.appendChild(dayHeading);

                        slots.forEach(slot => {
                            const timeSlotDiv = document.createElement('div');
                            timeSlotDiv.classList.add('time-slot');
                            timeSlotDiv.textContent = `${slot.timeStart} - ${slot.timeEnd}`;
                            timeSlotDiv.dataset.dayOfWeek = day;
                            timeSlotDiv.dataset.timeStart = slot.timeStart;
                            timeSlotDiv.dataset.timeEnd = slot.timeEnd;
                            timeSlotDiv.dataset.courseId = slot.courseId || courseId;
                            timeSlotDiv.dataset.teacherId = slot.teacherId || teacherId;

                            // Only clickable if the slot matches the selected date's day
                            if (day === dayOfWeek) {
                                timeSlotDiv.addEventListener('click', function () {
                                    loadStudentList(this);
                                });
                                timeSlotDiv.classList.add('clickable-slot');
                            }

                            dayContainer.appendChild(timeSlotDiv);
                        });

                        timetableContainer.appendChild(dayContainer);
                    });
                })
                .catch(error => console.error('Error loading timetable:', error));
        }
    }

    // Load students for the selected time slot
    function loadStudentList(slotDiv) {
        const { courseId, teacherId, dayOfWeek, timeStart, timeEnd } = slotDiv.dataset;

        // Store the selected timeStart and timeEnd
        selectedTimeStart = timeStart;
        selectedTimeEnd = timeEnd;

        fetch(`adminAttendance?action=viewStudents&courseId=${courseId}&teacherId=${teacherId}&dayOfWeek=${dayOfWeek}&timeStart=${timeStart}&timeEnd=${timeEnd}`)
            .then(response => response.json())
            .then(data => {
                studentsContainer.innerHTML = ''; // Clear existing students

                if (data.enrolledStudents.length > 0) {
                    data.enrolledStudents.forEach(student => {
                        const studentDiv = document.createElement('div');
                        studentDiv.classList.add('student');
                        studentDiv.textContent = `${student.studentName} (${student.studentId})`;

                        const selectAttendance = document.createElement('select');
                        selectAttendance.dataset.studentId = student.studentId;

                        ['Present', 'Absent'].forEach(status => {
                            const option = document.createElement('option');
                            option.value = status;
                            option.textContent = status;
                            selectAttendance.appendChild(option);
                        });

                        studentDiv.appendChild(selectAttendance);
                        studentsContainer.appendChild(studentDiv);
                    });
                } else {
                    studentsContainer.innerHTML = '<p>No students found for this slot.</p>';
                }
            })
            .catch(error => console.error('Error loading students:', error));
    }

    // Submit attendance
    document.getElementById('markAttendanceForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const teacherId = teacherSelect.value;
        const courseId = courseSelect.value;
        const selectedDate = attendanceDate.value;
        const studentAttendance = [];

        // Collect attendance data
        studentsContainer.querySelectorAll('.student select').forEach(select => {
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
        fetch('adminAttendance?action=markAttendance', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                teacherId,
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
                    // Reset selectedTimeStart and selectedTimeEnd
                    selectedTimeStart = null;
                    selectedTimeEnd = null;
                } else {
                    alert(data.message || 'Failed to mark attendance.');
                }
            })
            .catch(error => console.error('Error marking attendance:', error));
    });
});
