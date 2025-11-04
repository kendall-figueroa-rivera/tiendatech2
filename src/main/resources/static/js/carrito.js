// Carrito de Compras Dinámico (HU5)
(function() {
    // Cargar carrito desde sessionStorage
    function cargarCarritoDesdeStorage() {
        const carritoStorage = sessionStorage.getItem('carrito');
        if (carritoStorage) {
            return JSON.parse(carritoStorage);
        }
        return [];
    }

    // Guardar carrito en sessionStorage
    function guardarCarritoEnStorage(carrito) {
        sessionStorage.setItem('carrito', JSON.stringify(carrito));
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
        .then(response => response.json())
        .then(data => {
            // Actualizar carrito en sessionStorage
            const carrito = cargarCarritoDesdeStorage();
            const itemExistente = carrito.find(item => item.productoId === productoId);
            
            if (itemExistente) {
                itemExistente.cantidad += cantidad;
            } else {
                carrito.push({ productoId, cantidad });
            }
            
            guardarCarritoEnStorage(carrito);
            actualizarContadorCarrito();
            
            // Mostrar notificación
            mostrarNotificacion('Producto agregado al carrito');
            
            // Actualizar carrito desde el servidor
            actualizarCarritoDesdeServidor();
        })
        .catch(error => {
            console.error('Error al agregar al carrito:', error);
            mostrarNotificacion('Error al agregar producto', 'error');
        });
    }

    // Actualizar carrito desde el servidor
    function actualizarCarritoDesdeServidor() {
        fetch('/api/carrito')
            .then(response => response.json())
            .then(data => {
                if (data.items) {
                    const carrito = data.items.map(item => ({
                        productoId: item.producto.id,
                        cantidad: item.cantidad
                    }));
                    guardarCarritoEnStorage(carrito);
                    actualizarContadorCarrito();
                }
            })
            .catch(error => console.error('Error al actualizar carrito:', error));
    }

    // Mostrar notificación
    function mostrarNotificacion(mensaje, tipo = 'success') {
        const notificacion = document.createElement('div');
        notificacion.className = `notificacion ${tipo}`;
        notificacion.textContent = mensaje;
        notificacion.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 1rem;
            background-color: ${tipo === 'success' ? '#28a745' : '#dc3545'};
            color: white;
            border-radius: 5px;
            z-index: 1000;
            animation: slideIn 0.3s ease;
        `;
        document.body.appendChild(notificacion);
        
        setTimeout(() => {
            notificacion.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => notificacion.remove(), 300);
        }, 3000);
    }

    // Event listeners para botones de agregar al carrito
    document.addEventListener('DOMContentLoaded', function() {
        actualizarContadorCarrito();
        
        // Actualizar carrito desde servidor si el usuario está autenticado
        if (document.querySelector('[sec\\:authorize="isAuthenticated()"]')) {
            actualizarCarritoDesdeServidor();
        }

        // Agregar event listeners a todos los botones de agregar al carrito
        document.querySelectorAll('.btn-agregar-carrito').forEach(button => {
            button.addEventListener('click', function() {
                const productoId = this.getAttribute('data-producto-id');
                if (productoId) {
                    agregarAlCarrito(parseInt(productoId), 1);
                }
            });
        });
    });
})();

