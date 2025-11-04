// Modo Oscuro/Claro (HU20)
(function() {
    // Detectar preferencia del sistema
    const prefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    
    // Obtener tema guardado o usar preferencia del sistema
    const savedTheme = localStorage.getItem('theme') || (prefersDark ? 'dark' : 'light');
    
    // Aplicar tema
    document.documentElement.setAttribute('data-theme', savedTheme);
    
    // Botón de cambio de tema
    const themeToggle = document.getElementById('theme-toggle');
    if (themeToggle) {
        themeToggle.addEventListener('click', function() {
            const currentTheme = document.documentElement.getAttribute('data-theme');
            const newTheme = currentTheme === 'light' ? 'dark' : 'light';
            
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
        });
    }
    
    // Escuchar cambios en la preferencia del sistema
    if (window.matchMedia) {
        window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', function(e) {
            if (!localStorage.getItem('theme')) {
                document.documentElement.setAttribute('data-theme', e.matches ? 'dark' : 'light');
            }
        });
    }
})();

