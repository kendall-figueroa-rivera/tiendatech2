// Funciones generales de la aplicación

// Búsqueda directa con autocompletado (HU13)
(function() {
    const busquedaInput = document.getElementById('busqueda-input');
    if (busquedaInput) {
        let timeoutId;
        
        busquedaInput.addEventListener('input', function() {
            clearTimeout(timeoutId);
            const query = this.value.trim();
            
            if (query.length >= 2) {
                timeoutId = setTimeout(() => {
                    buscarProductos(query);
                }, 500);
            }
        });
    }

    function buscarProductos(query) {
        fetch(`/api/busqueda?q=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                // Aquí puedes mostrar los resultados en tiempo real
                console.log('Resultados de búsqueda:', data);
            })
            .catch(error => console.error('Error en búsqueda:', error));
    }
})();

// Actualizar filtros en memoria (HU7)
(function() {
    const filtros = JSON.parse(sessionStorage.getItem('filtros') || '{}');
    
    // Restaurar filtros al cargar la página
    if (Object.keys(filtros).length > 0) {
        // Aplicar filtros guardados
        const urlParams = new URLSearchParams(window.location.search);
        Object.keys(filtros).forEach(key => {
            if (!urlParams.has(key)) {
                urlParams.set(key, filtros[key]);
            }
        });
        // window.location.search = urlParams.toString();
    }
    
    // Guardar filtros cuando cambien
    document.querySelectorAll('select, input[type="checkbox"]').forEach(input => {
        input.addEventListener('change', function() {
            const formData = new FormData(this.form || this.closest('form'));
            const filtrosActuales = {};
            for (let [key, value] of formData.entries()) {
                filtrosActuales[key] = value;
            }
            sessionStorage.setItem('filtros', JSON.stringify(filtrosActuales));
        });
    });
})();

