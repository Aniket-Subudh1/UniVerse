<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Upload Notes</title>
  <link rel="stylesheet" href="styles/managenote.css">
</head>
<body>
<div class="container">
  <h2>Upload Notes</h2>
  <form id="uploadForm" action="uploadNotes" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="subjectName">Subject Name:</label>
      <input type="text" name="subjectName" id="subjectName" required>
    </div>
    <div class="form-group">
      <label for="topicName">Topic Name:</label>
      <input type="text" name="topicName" id="topicName" required>
    </div>
    <div class="form-group">
      <label for="file">Choose Notes (PDF/DOCX):</label>
      <input type="file" name="file" id="file" accept=".pdf, .docx" required>
    </div>
    <div class="form-group">
      <button type="submit">Upload Notes</button>
    </div>
  </form>
</div>

<!-- Modal for Success Message -->
<div id="successModal" class="modal" style="display:none;">
  <div class="modal-content">
    <p>Notes uploaded successfully!</p>
    <button id="closeModalBtn">OK</button>
  </div>
</div>

<script src="script/uploadNotes.js"></script>
</body>
</html>
