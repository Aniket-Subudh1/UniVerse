document.addEventListener('DOMContentLoaded', () => {
    const messagesDiv = document.getElementById('messages');
    const feeDetailsDiv = document.getElementById('feeDetails');
    const academicFeeSpan = document.getElementById('academicFee');
    const hostelFeeSpan = document.getElementById('hostelFee');
    const examFeeSpan = document.getElementById('examFee');
    const totalFeesSpan = document.getElementById('totalFees');
    const amountPaidSpan = document.getElementById('amountPaid');
    const balanceSpan = document.getElementById('balance');
    const payButton = document.getElementById('payButton');

    let amountDue = 0;
    let razorpayKeyId = '';

    // Fetch fee details on page load
    fetchFeeDetails();

    payButton.addEventListener('click', () => {
        if (amountDue <= 0) {
            showMessage('No balance amount to pay.', 'error');
            return;
        }

        // Create Razorpay order via AJAX
        fetch('StudentFeePaymentServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `action=createOrder&amount=${amountDue}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    const orderId = data.orderData.orderId;
                    openRazorpayCheckout(orderId);
                } else {
                    showMessage(data.message, 'error');
                }
            })
            .catch(error => {
                console.error('Error creating order:', error);
                showMessage('Error initiating payment. Please try again later.', 'error');
            });
    });

    function fetchFeeDetails() {
        fetch('StudentFeePaymentServlet?action=getFeeDetails')
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    const feeDetails = data.feeDetails;
                    // Populate the fields
                    academicFeeSpan.textContent = feeDetails.academicFee;
                    hostelFeeSpan.textContent = feeDetails.hostelFee;
                    examFeeSpan.textContent = feeDetails.examFee;
                    totalFeesSpan.textContent = feeDetails.totalFees;
                    amountPaidSpan.textContent = feeDetails.amountPaid;
                    balanceSpan.textContent = feeDetails.balance;
                    amountDue = feeDetails.balance;
                    razorpayKeyId = data.razorpayKeyId;

                    feeDetailsDiv.style.display = 'block';
                    showMessage('', ''); // Clear any previous messages
                } else {
                    showMessage(data.message, 'error');
                    feeDetailsDiv.style.display = 'none';
                }
            })
            .catch(error => {
                console.error('Error fetching fee details:', error);
                showMessage('Error fetching fee details. Please try again later.', 'error');
            });
    }

    function openRazorpayCheckout(orderId) {
        const options = {
            "key": razorpayKeyId,
            "amount": amountDue * 100, // Convert to paise
            "currency": "INR",
            "name": "UniVerse",
            "description": "Fee Payment",
            "order_id": orderId,
            "handler": function (response) {
                handlePaymentSuccess(response);
            },
            "prefill": {
                "name": "", // Optional: Student's name
                "email": "", // Optional: Student's email
                "contact": "" // Optional: Student's contact number
            },
            "theme": {
                "color": "#3399cc"
            }
        };
        const rzp = new Razorpay(options);
        rzp.open();
    }

    function handlePaymentSuccess(paymentResponse) {
        paymentResponse.amount = amountDue.toString();

        // Send payment details to server for verification and updating database
        fetch('StudentFeePaymentServlet?action=paymentSuccess', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paymentResponse)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    showMessage(data.message, 'success');
                    // Refresh the fee details to show updated balance
                    fetchFeeDetails();
                } else {
                    showMessage(data.message, 'error');
                }
            })
            .catch(error => {
                console.error('Error verifying payment:', error);
                showMessage('Error verifying payment. Please contact support.', 'error');
            });
    }

    function showMessage(message, type) {
        messagesDiv.innerHTML = message ? `<p class="${type}">${message}</p>` : '';
    }
});
