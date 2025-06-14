document.addEventListener("DOMContentLoaded", function() {
    let imgInput = document.getElementById("imagesInput");
    let imgBox = document.getElementById("imagePreviewContainer");

    if (imgInput && imgBox) {
        imgInput.addEventListener("change", function() {
            imgBox.textContent = "";
            for (let i = 0; i < imgInput.files.length; i++) {
                let reader = new FileReader();
                reader.onload = function(e) {
                    let img = document.createElement("img");
                    img.src = e.target.result;
                    img.className = "img-fluid rounded-3 mb-2";
                    img.style.maxHeight = "100px";
                    imgBox.appendChild(img);
                };
                reader.readAsDataURL(imgInput.files[i]);
            }
        });
    }

    let vidInput = document.getElementById("videoInput");
    let vidBox = document.getElementById("videoPreviewContainer");

    if (vidInput && vidBox) {
        vidInput.addEventListener("change", function() {
            vidBox.textContent = "";
            if (!vidInput.files.length) return;
            let url = URL.createObjectURL(vidInput.files[0]);
            let video = document.createElement("video");
            video.src = url;
            video.controls = true;
            video.className = "w-100 rounded-3 mb-3";
            video.style.maxHeight = "240px";
            video.onloadeddata = function() { URL.revokeObjectURL(url); };
            vidBox.appendChild(video);
        });
    }
});