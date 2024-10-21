<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Razorpay Payment</title>
</head>
<body>
<h1>Complete Your Payment</h1>
<form action="paymentSuccess" method="POST">
    <script
            src="https://checkout.razorpay.com/v1/checkout.js"
            data-key="rzp_test_E96YctEGlH3EcE"
            data-amount="50000"
            data-currency="INR"
            data-order_id="${orderId}"
            data-buttontext="Pay with Razorpay"
            data-name="Your Company Name"
            data-description="Test Transaction"
            data-image="https://example.com/your_logo.jpg"
            data-prefill.name="John Doe"
            data-prefill.email="john.doe@example.com"
            data-theme.color="#F37254">
    </script>
    <input type="hidden" name="hidden" value="Hidden Element">
</form>
</body>
</html>