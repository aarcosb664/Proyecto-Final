document.addEventListener('DOMContentLoaded', function() {
    var tabs = document.querySelectorAll('.loginWrapper .tabs > li');
    var tabContents = document.querySelectorAll('.loginWrapper .tab__content > li');
    var tabWrapper = document.querySelector('.loginWrapper .tab__content');

    // Mostrar el tab activo al cargar
    var activeTab = document.querySelector('.loginWrapper .tab__content > li.active');
    if (activeTab) {
        activeTab.style.display = 'block';
        tabWrapper.style.height = activeTab.offsetHeight + 'px';
    }

    tabs.forEach(function(tab, idx) {
        tab.addEventListener('click', function() {
            // Quitar clase active de todas las tabs
            tabs.forEach(function(t) { t.classList.remove('active'); });
            // Poner clase active a la tab clickeada
            tab.classList.add('active');

            // Ocultar todos los contenidos
            tabContents.forEach(function(content) {
                content.classList.remove('active');
                content.style.display = 'none';
            });
            // Mostrar el contenido correspondiente
            var content = tabContents[idx];
            content.classList.add('active');
            content.style.display = 'block';

            // Animar la altura del contenedor
            var newHeight = content.offsetHeight;
            tabWrapper.style.height = newHeight + 'px';
        });
    });
}); 