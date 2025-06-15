document.addEventListener("DOMContentLoaded", function() {
    // Función para previsualizar los archivos
    function previewFiles(inputId, containerId, createElementFn) {
        const input = document.getElementById(inputId);
        const container = document.getElementById(containerId);
        if (!input || !container) {
            return;
        }
        input.addEventListener("change", function() {
            container.textContent = "";
            if (!input.files.length) {
                return;
            }
            createElementFn(input.files, container);
        });
    }

    // Función para previsualizar las imágenes
    previewFiles("imagesInput", "imagePreviewContainer", (files, container) => {
        Array.from(files).forEach(file => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                img.className = "img-fluid rounded-3 mb-2";
                img.style.maxHeight = "100px";
                container.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    });

    // Función para previsualizar los videos
    previewFiles("videoInput", "videoPreviewContainer", (files, container) => {
        const url = URL.createObjectURL(files[0]);
        const video = document.createElement("video");
        video.src = url;
        video.controls = true;
        video.className = "w-100 rounded-3 mb-3";
        video.style.maxHeight = "240px";
        video.onloadeddata = function() { URL.revokeObjectURL(url); };
        container.appendChild(video);
    });
});