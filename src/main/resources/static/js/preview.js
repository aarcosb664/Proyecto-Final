document.addEventListener("DOMContentLoaded", () => {
    // Imágenes
    const imgInput   = document.getElementById("imagesInput");
    const imgBox     = document.getElementById("imagePreviewContainer");

    imgInput?.addEventListener("change", () => {
        // Limpia previas
        imgBox.innerHTML = "";                                
        [...imgInput.files].forEach(file => {
            // Lee cada imagen
            const reader = new FileReader();                    
            reader.onload = e => {
            imgBox.insertAdjacentHTML(
                "beforeend",
                `<img src="${e.target.result}"
                    class="img-fluid rounded-3 mb-2"
                    style="max-height:110px">`);
            };
            // Dispara onload
            reader.readAsDataURL(file);
        });
    });

    // Video
    const vidInput  = document.getElementById("videoInput");
    const vidBox    = document.getElementById("videoPreviewContainer");

    vidInput?.addEventListener("change", () => {
        // Limpia previa
        vidBox.innerHTML = "";                                
        if (!vidInput.files.length) return;
    
        // Crea blob URL
        const url = URL.createObjectURL(vidInput.files[0]);   
        vidBox.innerHTML =
            `<video src="${url}" controls
                    class="w-100 rounded-3 mb-3"
                    style="max-height:240px"></video>`;
        // Liberar memoria cuando el vídeo ya no se necesite
        vidBox.querySelector("video").onload = () => URL.revokeObjectURL(url);
    });
});