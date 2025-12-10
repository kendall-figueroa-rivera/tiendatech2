// Favoritos Mejorado con soporte por usuario
(function() {
    // Obtener ID de usuario único (genera uno si no existe)
    function obtenerUsuarioId() {
        let userId = localStorage.getItem('tiendatech_user_id');
        if (!userId) {
            userId = 'guest_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
            localStorage.setItem('tiendatech_user_id', userId);
        }
        return userId;
    }

    // Cargar favoritos desde localStorage por usuario
    function cargarFavoritosDesdeStorage() {
        const userId = obtenerUsuarioId();
        const key = 'favoritos_' + userId;
        const favoritosStorage = localStorage.getItem(key);
        if (favoritosStorage) {
            return JSON.parse(favoritosStorage);
        }
        return [];
    }

    // Guardar favoritos en localStorage por usuario
    function guardarFavoritosEnStorage(favoritos) {
        const userId = obtenerUsuarioId();
        const key = 'favoritos_' + userId;
        localStorage.setItem(key, JSON.stringify(favoritos));
    }

    // Actualizar contador de favoritos en navbar
    function actualizarContadorFavoritos() {
        const favoritos = cargarFavoritosDesdeStorage();
        const contador = document.getElementById('favoritos-count');
        if (contador) {
            contador.textContent = favoritos.length;
            if (favoritos.length > 0) {
                contador.style.display = 'inline-block';
            } else {
                contador.style.display = 'none';
            }
        }
    }

    // Agregar/Quitar de favoritos
    function toggleFavorito(productoId) {
        fetch('/favoritos/toggle', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `productoId=${productoId}`
        })
        .then(response => {
            if (response.status === 401) {
                // Usuario no autenticado - guardar en localStorage
                let favoritos = cargarFavoritosDesdeStorage();
                const index = favoritos.indexOf(productoId);

                if (index > -1) {
                    favoritos.splice(index, 1);
                    mostrarNotificacion('❌ Eliminado de favoritos');
                } else {
                    favoritos.push(productoId);
                    mostrarNotificacion('❤️ Agregado a favoritos. Inicia sesión para guardar.');
                }

                guardarFavoritosEnStorage(favoritos);
                actualizarIconosFavoritos();
                actualizarContadorFavoritos();
                return;
            }
            return response.text();
        })
        .then(data => {
            if (data) {
                let favoritos = cargarFavoritosDesdeStorage();
                const index = favoritos.indexOf(productoId);

                if (index > -1) {
                    favoritos.splice(index, 1);
                    mostrarNotificacion('❌ Eliminado de favoritos');
                } else {
                    favoritos.push(productoId);
                    mostrarNotificacion('❤️ Agregado a favoritos');
                }

                guardarFavoritosEnStorage(favoritos);
                actualizarIconosFavoritos();
                actualizarContadorFavoritos();
            }
        })
        .catch(error => {
            console.error('Error al manejar favorito:', error);

            // Guardar localmente aunque haya error
            let favoritos = cargarFavoritosDesdeStorage();
            const index = favoritos.indexOf(productoId);

            if (index > -1) {
                favoritos.splice(index, 1);
                mostrarNotificacion('❌ Eliminado de favoritos localmente');
            } else {
                favoritos.push(productoId);
                mostrarNotificacion('❤️ Agregado a favoritos localmente');
            }

            guardarFavoritosEnStorage(favoritos);
            actualizarIconosFavoritos();
            actualizarContadorFavoritos();
        });
    }

    // Actualizar iconos de favoritos
    function actualizarIconosFavoritos() {
        const favoritos = cargarFavoritosDesdeStorage();
        document.querySelectorAll('.btn-favorito').forEach(button => {
            const productoId = parseInt(button.getAttribute('data-producto-id'));
            if (favoritos.includes(productoId)) {
                button.innerHTML = '❤️';
                button.style.color = '#ef4444';
            } else {
                button.innerHTML = '🤍';
                button.style.color = '#9ca3af';
            }
        });
    }

    // Mostrar notificación
    function mostrarNotificacion(mensaje, tipo = 'success') {
        const existentes = document.querySelectorAll('.notificacion-toast');
        existentes.forEach(n => n.remove());

        const notificacion = document.createElement('div');
        notificacion.className = 'notificacion-toast';
        notificacion.textContent = mensaje;
        notificacion.style.cssText = `
            position: fixed;
            top: 100px;
            right: 30px;
            padding: 1.25rem 1.75rem;
            background: ${tipo === 'success' ? 'linear-gradient(135deg, #10b981, #059669)' : 'linear-gradient(135deg, #ef4444, #dc2626)'};
            color: white;
            border-radius: 12px;
            z-index: 10000;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            font-weight: 600;
            font-size: 0.95rem;
            transform: translateX(400px);
            transition: transform 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
        `;
        document.body.appendChild(notificacion);

        setTimeout(() => {
            notificacion.style.transform = 'translateX(0)';
        }, 10);

        setTimeout(() => {
            notificacion.style.transform = 'translateX(400px)';
            setTimeout(() => notificacion.remove(), 300);
        }, 3500);
    }

    // Event listeners
    document.addEventListener('DOMContentLoaded', function() {
        actualizarIconosFavoritos();
        actualizarContadorFavoritos();

        document.querySelectorAll('.btn-favorito').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const productoId = parseInt(this.getAttribute('data-producto-id'));
                if (productoId) {
                    toggleFavorito(productoId);
                }
            });
        });
    });
})();
