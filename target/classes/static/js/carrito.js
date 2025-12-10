// Carrito de Compras - Integración con Base de Datos
(function() {
    // Actualizar contador de carrito
    function actualizarContadorCarrito() {
        fetch('/api/carrito')
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                return { items: [] };
            })
            .then(data => {
                const contador = document.getElementById('carrito-count');
                if (contador) {
                    const totalItems = data.items ? data.items.reduce((sum, item) => sum + item.cantidad, 0) : 0;
                    contador.textContent = totalItems;
                    if (totalItems > 0) {
                        contador.style.display = 'inline-block';
                    } else {
                        contador.style.display = 'none';
                    }
                }
            })
            .catch(error => {
                console.error('Error al actualizar contador de carrito:', error);
            });
    }

    // Agregar producto al carrito
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
                mostrarNotificacion('⚠️ Debes iniciar sesión para agregar productos al carrito', 'error');
                return;
            }
            return response.text();
        })
        .then(data => {
            if (data) {
                mostrarNotificacion('✅ Producto agregado al carrito');
                actualizarContadorCarrito();
            }
        })
        .catch(error => {
            console.error('Error al agregar al carrito:', error);
            mostrarNotificacion('❌ Error al agregar producto al carrito', 'error');
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

    // Event listeners para botones de agregar al carrito
    document.addEventListener('DOMContentLoaded', function() {
        actualizarContadorCarrito();

        // Agregar event listeners a todos los botones de agregar al carrito
        document.querySelectorAll('.btn-agregar-carrito, .btn-add-cart').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                const productoId = this.getAttribute('data-producto-id') || this.getAttribute('data-id');
                if (productoId) {
                    agregarAlCarrito(parseInt(productoId), 1);
                }
            });
        });
    });
})();
