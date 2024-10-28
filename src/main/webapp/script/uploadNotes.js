document.addEventListener('DOMContentLoaded', () => {
    const uploadForm = document.getElementById('uploadForm');
    const successModal = document.getElementById('successModal');
    const closeModalBtn = document.getElementById('closeModalBtn');

    // Submit form using AJAX to stay on the same page
    uploadForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = new FormData(uploadForm);

        fetch('uploadNotes', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(responseText => {
                if (responseText.includes('successfully')) {
                    showModal();
                } else {
                    alert('Error: ' + responseText);
                }
            })
            .catch(error => {
                console.error('Error uploading notes:', error);
                alert('Error uploading notes. Please try again.');
            });
    });

    // Show modal
    function showModal() {
        successModal.style.display = 'block';
    }

    // Close modal and remain on the same page
    closeModalBtn.addEventListener('click', () => {
        successModal.style.display = 'none';
    });
});
