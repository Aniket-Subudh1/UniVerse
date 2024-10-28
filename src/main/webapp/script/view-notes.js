document.addEventListener('DOMContentLoaded', function () {
    const notesContainer = document.getElementById('notesContainer');

    function loadNotes() {
        fetch('viewNotes')
            .then(response => response.json())
            .then(data => {
                notesContainer.innerHTML = ''; // Clear previous notes

                if (data.status === 'success' && data.notes.length > 0) {
                    data.notes.forEach(note => {
                        const noteCard = document.createElement('div');
                        noteCard.classList.add('note-card');
                        noteCard.innerHTML = `
                            <p><strong>Teacher:</strong> ${note.teacherName}</p>
                            <p><strong>Subject:</strong> ${note.subjectName}</p>
                            <p><strong>Topic:</strong> ${note.topicName}</p>
                            <a href="viewNotes?action=download&filePath=${encodeURIComponent(note.filePath)}" target="_blank" class="view-link">View Notes</a>
                        `;
                        notesContainer.appendChild(noteCard);
                    });
                } else {
                    notesContainer.innerHTML = '<p>No notes available at the moment.</p>';
                }
            })
            .catch(error => {
                console.error('Error loading notes:', error);
                notesContainer.innerHTML = '<p>Error loading notes. Please try again later.</p>';
            });
    }

    loadNotes();
});
