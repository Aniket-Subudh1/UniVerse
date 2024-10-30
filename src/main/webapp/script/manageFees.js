document.addEventListener('DOMContentLoaded', () => {
    const registrationIdDropdown = document.getElementById('registrationId');
    const enrolledCourseDropdown = document.getElementById('enrolledCourse');

    const loadDropdowns = () => {
        fetch('FeeManagementServlet?action=loadDropdowns')
            .then(response => response.json())
            .then(data => {
                // Populate Registration IDs
                registrationIdDropdown.innerHTML = '<option value="">Select Registration ID</option>';
                data.registrationIds.forEach(id => {
                    const option = document.createElement('option');
                    option.value = id;
                    option.textContent = id;
                    registrationIdDropdown.appendChild(option);
                });

                // Populate Enrolled Courses
                enrolledCourseDropdown.innerHTML = '<option value="">Select Enrolled Course</option>';
                data.courses.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course;
                    option.textContent = course;
                    enrolledCourseDropdown.appendChild(option);
                });
            });
    };

    // Event listener for searching fees
    document.getElementById('searchFeesButton').addEventListener('click', () => {
        const registrationId = registrationIdDropdown.value;
        if (!registrationId) {
            alert('Please select a Registration ID.');
            return;
        }
        fetch(`FeeManagementServlet?action=getFees&registrationId=${registrationId}`)
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success' && data.fees && Object.keys(data.fees).length > 0) {
                    const fees = data.fees;
                    document.getElementById('academicFeeDisplay').textContent = fees.academicFee;
                    document.getElementById('hostelFeeDisplay').textContent = fees.hostelFee;
                    document.getElementById('examFeeDisplay').textContent = fees.examFee;
                    document.getElementById('totalFeesDisplay').textContent = fees.totalFees;
                    document.getElementById('amountPaidDisplay').textContent = fees.amountPaid;
                    document.getElementById('balanceDisplay').textContent = fees.balance;
                } else {
                    alert("No fee data found.");
                    // Clear displayed fees
                    document.getElementById('academicFeeDisplay').textContent = '';
                    document.getElementById('hostelFeeDisplay').textContent = '';
                    document.getElementById('examFeeDisplay').textContent = '';
                    document.getElementById('totalFeesDisplay').textContent = '';
                    document.getElementById('amountPaidDisplay').textContent = '';
                    document.getElementById('balanceDisplay').textContent = '';
                }
            });
    });

    // Event listener for updating fees for a single student
    document.getElementById('updateFeesButton').addEventListener('click', () => {
        const registrationId = registrationIdDropdown.value;
        if (!registrationId) {
            alert('Please select a Registration ID.');
            return;
        }
        const requestData = {
            registrationId: registrationId,
            academicFee: parseFloat(document.getElementById('academicFee').value) || 0,
            hostelFee: parseFloat(document.getElementById('hostelFee').value) || 0,
            examFee: parseFloat(document.getElementById('examFee').value) || 0,
            amountPaid: parseFloat(document.getElementById('amountPaid').value) || 0
        };
        fetch('FeeManagementServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                // Optionally, refresh the fee details
                document.getElementById('searchFeesButton').click();
            });
    });

    // Event listener for updating fees for multiple students
    document.getElementById('updateCourseFeesButton').addEventListener('click', () => {
        const enrolledCourse = enrolledCourseDropdown.value;
        const registrationIdsInput = document.getElementById('registrationIds').value;
        let registrationIds = [];
        if (registrationIdsInput) {
            registrationIds = registrationIdsInput.split(',').map(id => id.trim()).filter(id => id);
        }

        if (!enrolledCourse && registrationIds.length === 0) {
            alert('Please select an Enrolled Course or enter Registration IDs.');
            return;
        }

        const requestData = {
            enrolledCourse: enrolledCourse,
            registrationIds: registrationIds,
            academicFee: parseFloat(document.getElementById('courseAcademicFee').value) || 0,
            hostelFee: parseFloat(document.getElementById('courseHostelFee').value) || 0,
            examFee: parseFloat(document.getElementById('courseExamFee').value) || 0,
            amountPaid: parseFloat(document.getElementById('courseAmountPaid').value) || 0
        };
        fetch('FeeManagementServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
            });
    });

    loadDropdowns();
});