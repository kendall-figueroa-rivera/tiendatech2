// Carrito de Compras Mejorado con soporte por usuario
(function() {
    // Obtener ID de usuario único (mismo que favoritos)
    function obtenerUsuarioId() {
        let userId = localStorage.getItem('tiendatech_user_id');
        if (!userId) {
            userId = 'guest_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
            localStorage.setItem('tiendatech_user_id', userId);
        }
        return userId;
    }

    // Cargar carrito desde localStorage por usuario
    function cargarCarritoDesdeStorage() {
        const userId = obtenerUsuarioId();
        const key = 'carrito_' + userId;
        const carritoStorage = localStorage.getItem(key);
        if (carritoStorage) {
            return JSON.parse(carritoStorage);
        }
        return [];
    }

    // Guardar carrito en localStorage por usuario
    function guardarCarritoEnStorage(carrito) {
        const userId = obtenerUsuarioId();
        const key = 'carrito_' + userId;
        localStorage.setItem(key, JSON.stringify(carrito));
    }

    // Actualizar contador del carrito
    function actualizarContadorCarrito() {
        const carrito = cargarCarritoDesdeStorage();
        const totalItems = carrito.reduce((sum, item) => sum + item.cantidad, 0);
        const carritoCount = document.getElementById('carrito-count');
        if (carritoCount) {
            carritoCount.textContent = totalItems;
        }
    }

    // Agregar producto al carrito (AJAX)
    function agregarAlCarrito(productoId, cantidad = 1) {
        fetch('/carrito/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `productoId=${productoId}&cantidad=${cantidad}`
        })
        .then(response => {
            if (response.status === 401) {
                // Usuario no autenticado - guardar en sessionStorage
                const carrito = cargarCarritoDesdeStorage();
                const itemExistente = carrito.find(item => item.productoId === productoId);

                if (itemExistente) {
                    itemExistente.cantidad += cantidad;
                } else {
                    carrito.push({ productoId, cantidad });
                }

                guardarCarritoEnStorage(carrito);
                actualizarContadorCarrito();

                mostrarNotificacion('✅ Producto agregado al carrito. Inicia sesión para finalizar la compra.');
                return;
            }
            return response.text();
        })
        .then(data => {
            if (data) {
                // Usuario autenticado - actualizar desde servidor
                const carrito = cargarCarritoDesdeStorage();
                const itemExistente = carrito.find(item => item.productoId === productoId);

                if (itemExistente) {
                    itemExistente.cantidad += cantidad;
                } else {
                    carrito.push({ productoId, cantidad });
                }

                guardarCarritoEnStorage(carrito);
                actualizarContadorCarrito();

                mostrarNotificacion('✅ Producto agregado al carrito');
            }
        })
        .catch(error => {
            console.error('Error al agregar al carrito:', error);

            // Guardar localmente aunque haya error
            const carrito = cargarCarritoDesdeStorage();
            const itemExistente = carrito.find(item => item.productoId === productoId);

            if (itemExistente) {
                itemExistente.cantidad += cantidad;
            } else {
                carrito.push({ productoId, cantidad });
            }

            guardarCarritoEnStorage(carrito);
            actualizarContadorCarrito();

            mostrarNotificacion('✅ Producto agregado al carrito localmente');
        });
    }

    // Mostrar notificación mejorada
    function mostrarNotificacion(mensaje, tipo = 'success') {
        // Remover notificaciones existentes
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

        // Animar entrada
        setTimeout(() => {
            notificacion.style.transform = 'translateX(0)';
        }, 10);

        // Animar salida y remover
        setTimeout(() => {
            notificacion.style.transform = 'translateX(400px)';
            setTimeout(() => notificacion.remove(), 300);
        }, 3500);
    }

    // Event listeners para botones de agregar al carrito
    document.addEventListener('DOMContentLoaded', function() {
        actualizarContadorCarrito();

        // Agregar event listeners a todos los botones de agregar al carrito
        document.querySelectorAll('.btn-agregar-carrito').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const productoId = this.getAttribute('data-producto-id');
                if (productoId) {
                    agregarAlCarrito(parseInt(productoId), 1);
                }
            });
        });
    });
})();
