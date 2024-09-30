<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Feedback System</title>
    <link rel="stylesheet" type="text/css" href="styles/feedback.css">
</head>
<body>
<div class="feedback-container">
    <h1>Submit Your Feedback</h1>
    <form action="FeedbackServlet" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <br>

        <label for="feedback">Feedback:</label>
        <textarea id="feedback" name="feedback" rows="4" required></textarea>
        <br>

        <label for="rating">Rating:</label>
        <select id="rating" name="rating" required>
            <option value="5">Excellent</option>
            <option value="4">Very Good</option>
            <option value="3">Good</option>
            <option value="2">Fair</option>
            <option value="1">Poor</option>
        </select>
        <br><br>

        <input type="submit" value="Submit Feedback">
    </form>
</div>
</body>
</html>