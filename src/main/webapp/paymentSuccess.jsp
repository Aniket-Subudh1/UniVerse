<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            padding: 40px 20px;
            max-width: 500px;
            width: 100%;
        }

        h1 {
            color: #28a745;
            font-size: 2.5rem;
            margin-bottom: 20px;
        }

        p {
            font-size: 1.2rem;
            color: #555;
        }

        .container p {
            margin-top: 0;
        }

        .button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Payment Successful</h1>
    <p>Thank you for your payment. Your transaction was successful!</p>
    <a href="student-dashboard.jsp" class="button">Return to Home</a>
</div>
</body>
</html>
