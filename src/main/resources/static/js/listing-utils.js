document.addEventListener('DOMContentLoaded', function() {
    const thumbSwiper = new Swiper(".thumbSwiper", {
        spaceBetween: 10,
        slidesPerView: 'auto',
        freeMode: true,
        rewind: true,
        watchSlidesProgress: true,
        slideToClickedSlide: true
    });

    const mainSwiper = new Swiper(".mainSwiper", {
        spaceBetween: 10,
        rewind: true,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: thumbSwiper,
        },
    });

    // Rating functionality
    const input = document.getElementById('ratingInput');
    const form = document.getElementById('ratingForm');
    let rating = parseFloat(input.value) || 0;
    
    function paint(val) {
        document.querySelectorAll('#customRating label').forEach(lab => {
            let v = parseFloat(lab.dataset.value);
            let icon = lab.querySelector('i');
            icon.className = val >= v ? 'bi bi-star-fill text-warning'
                : (val >= v - 0.5 ? 'bi bi-star-half text-warning' : 'bi bi-star text-secondary');
        });
        input.value = val || '';
    }
    
    paint(rating);
    
    document.querySelectorAll('#customRating label').forEach(lab => {
        lab.onmousemove = e => {
            let position = getPosition(lab, e);
            paint(position);
        };
        lab.onmouseleave = () => paint(rating);
        lab.onclick = e => {
            let position = getPosition(lab, e);
            rating = position;
            paint(rating);
            form.submit();
        };
    });
    
    function getPosition(lab, e) {
        let w = lab.offsetWidth, x = e.offsetX;
        let v = parseFloat(lab.dataset.value) - (x < w/2 ? 0.5 : 0);
        return v;
    }

});

// Handle share button
document.getElementById('copyUrl').addEventListener('click', function() {
    const url = window.location.href;
    navigator.clipboard.writeText(url);
    this.className = "btn-outline-neon-green";
    this.innerHTML = "<i class='bi bi-check-circle me-2'></i>Copied";

    setTimeout(() => {
        this.className = "btn-outline-neon-orange";
        this.innerHTML = "<i class='bi bi-share me-2'></i>Share";
    }, 2000);
});