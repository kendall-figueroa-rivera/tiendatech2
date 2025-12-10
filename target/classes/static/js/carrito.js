<<<<<<< HEAD
document.addEventListener('DOMContentLoaded', () => {

    const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const headerName = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || 'X-CSRF-TOKEN';

    function toast(msg, ok = true) {
        let t = document.getElementById('toast-msg');
        if (!t) {
            t = document.createElement('div');
            t.id = 'toast-msg';
            t.style.position = 'fixed';
            t.style.top = '20px';
            t.style.right = '20px';
            t.style.zIndex = '99999';
            t.style.padding = '12px 18px';
            t.style.borderRadius = '8px';
            t.style.color = 'white';
            t.style.fontSize = '15px';
            document.body.appendChild(t);
=======
// Carrito de Compras Mejorado
(function() {
    // Cargar carrito desde sessionStorage
    function cargarCarritoDesdeStorage() {
        const carritoStorage = sessionStorage.getItem('carrito');
        if (carritoStorage) {
            return JSON.parse(carritoStorage);
>>>>>>> 922fd2ca23edab38af23357108a30a2728fcfc24
        }
        t.style.background = ok ? '#28a745' : '#dc3545';
        t.textContent = msg;
        t.style.display = 'block';
        setTimeout(() => t.style.display = 'none', 2000);
    }

    document.querySelectorAll('.btn-add-cart').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            e.preventDefault();           // ✔ evita parpadeo
            e.stopPropagation();          // ✔ evita submit de algún form padre

            const id = btn.getAttribute('data-id');

<<<<<<< HEAD
            try {
                const res = await fetch('/api/carrito/agregar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        ...(token ? { [headerName]: token } : {})
                    },
                    body: new URLSearchParams({ productoId: id, cantidad: 1 })
                });

                if (!res.ok) throw new Error('Error HTTP ' + res.status);

                const json = await res.json();
                toast(json.msg || 'Agregado al carrito', json.ok);

            } catch (err) {
                console.error(err);
                toast('No se pudo agregar', false);
            }
        });
    });
});
=======
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
>>>>>>> 922fd2ca23edab38af23357108a30a2728fcfc24
