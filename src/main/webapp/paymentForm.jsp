<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Razorpay Payment</title>
</head>

<style>
    /* Container styles */
    .container {
        background: #fff;
        padding: 120px;
        border-radius: 16px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        max-width: 600px;
        margin: 20px auto;
    }

    /* List styles */
    ul {
        list-style-type: none;
        padding: 0;
        margin: 0;
        display: flex;
        justify-content: space-around;
    }

    ul li {
        height: 100%;
    }

    ul a, ul .dropdown-btn {
        width: 60px;
        height: 60px;
        padding: 0;
        border-radius: 50%;
        justify-content: center;
        display: flex;
        align-items: center;
        background-color: #f4f4f4;
        transition: background-color 0.3s ease;
    }

    ul a:hover, ul .dropdown-btn:hover {
        background-color: #ddd;
    }

    ul li span, ul li:first-child, .dropdown-btn svg:last-child {
        display: none;
    }

    ul li .sub-menu.show {
        position: fixed;
        bottom: 60px;
        left: 0;
        box-sizing: border-box;
        height: 60px;
        width: 100%;
    }

    .bgg{
        background-color: rgb(140, 192, 213);
    }

</style>
<body class="bgg">
<div class="container">
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
</div>
</body>
</html>