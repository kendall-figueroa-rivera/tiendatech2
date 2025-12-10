// TiendaTech2 - Sistema unificado de Carrito y Favoritos
(function() {
    'use strict';

    // ==================== UTILIDADES ====================

    // Obtener ID de usuario único
    function obtenerUsuarioId() {
        let userId = localStorage.getItem('tiendatech_user_id');
        if (!userId) {
            userId = 'guest_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
            localStorage.setItem('tiendatech_user_id', userId);
        }
        return userId;
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

    // ==================== CARRITO ====================

    const Carrito = {
        cargar: function() {
            const userId = obtenerUsuarioId();
            const key = 'carrito_' + userId;
            const data = localStorage.getItem(key);
            return data ? JSON.parse(data) : [];
        },

        guardar: function(carrito) {
            const userId = obtenerUsuarioId();
            const key = 'carrito_' + userId;
            localStorage.setItem(key, JSON.stringify(carrito));
        },

        actualizarContador: function() {
            const carrito = this.cargar();
            const totalItems = carrito.reduce((sum, item) => sum + item.cantidad, 0);
            const contador = document.getElementById('carrito-count');
            if (contador) {
                contador.textContent = totalItems;
            }
        },

        agregar: function(productoId, cantidad = 1) {
            fetch('/carrito/agregar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `productoId=${productoId}&cantidad=${cantidad}`
            })
            .then(response => {
                if (response.status === 401) {
                    // Usuario no autenticado
                    const carrito = this.cargar();
                    const itemExistente = carrito.find(item => item.productoId === productoId);

                    if (itemExistente) {
                        itemExistente.cantidad += cantidad;
                    } else {
                        carrito.push({ productoId, cantidad });
                    }

                    this.guardar(carrito);
                    this.actualizarContador();
                    mostrarNotificacion('✅ Producto agregado al carrito');
                    return null;
                }
                return response.text();
            })
            .then(data => {
                if (data) {
                    // Usuario autenticado
                    const carrito = this.cargar();
                    const itemExistente = carrito.find(item => item.productoId === productoId);

                    if (itemExistente) {
                        itemExistente.cantidad += cantidad;
                    } else {
                        carrito.push({ productoId, cantidad });
                    }

                    this.guardar(carrito);
                    this.actualizarContador();
                    mostrarNotificacion('✅ Producto agregado al carrito');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                // Fallback local
                const carrito = this.cargar();
                const itemExistente = carrito.find(item => item.productoId === productoId);

                if (itemExistente) {
                    itemExistente.cantidad += cantidad;
                } else {
                    carrito.push({ productoId, cantidad });
                }

                this.guardar(carrito);
                this.actualizarContador();
                mostrarNotificacion('✅ Producto agregado al carrito');
            });
        }
    };

    // ==================== FAVORITOS ====================

    const Favoritos = {
        cargar: function() {
            const userId = obtenerUsuarioId();
            const key = 'favoritos_' + userId;
            const data = localStorage.getItem(key);
            return data ? JSON.parse(data) : [];
        },

        guardar: function(favoritos) {
            const userId = obtenerUsuarioId();
            const key = 'favoritos_' + userId;
            localStorage.setItem(key, JSON.stringify(favoritos));
        },

        actualizarContador: function() {
            const favoritos = this.cargar();
            const contador = document.getElementById('favoritos-count');
            if (contador) {
                contador.textContent = favoritos.length;
                contador.style.display = favoritos.length > 0 ? 'inline-block' : 'none';
            }
        },

        actualizarIconos: function() {
            const favoritos = this.cargar();
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
        },

        toggle: function(productoId) {
            fetch('/favoritos/toggle', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `productoId=${productoId}`
            })
            .then(response => {
                if (response.status === 401) {
                    // Usuario no autenticado
                    let favoritos = this.cargar();
                    const index = favoritos.indexOf(productoId);

                    if (index > -1) {
                        favoritos.splice(index, 1);
                        mostrarNotificacion('❌ Eliminado de favoritos');
                    } else {
                        favoritos.push(productoId);
                        mostrarNotificacion('❤️ Agregado a favoritos');
                    }

                    this.guardar(favoritos);
                    this.actualizarIconos();
                    this.actualizarContador();
                    return null;
                }
                return response.text();
            })
            .then(data => {
                if (data) {
                    // Usuario autenticado
                    let favoritos = this.cargar();
                    const index = favoritos.indexOf(productoId);

                    if (index > -1) {
                        favoritos.splice(index, 1);
                        mostrarNotificacion('❌ Eliminado de favoritos');
                    } else {
                        favoritos.push(productoId);
                        mostrarNotificacion('❤️ Agregado a favoritos');
                    }

                    this.guardar(favoritos);
                    this.actualizarIconos();
                    this.actualizarContador();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                // Fallback local
                let favoritos = this.cargar();
                const index = favoritos.indexOf(productoId);

                if (index > -1) {
                    favoritos.splice(index, 1);
                    mostrarNotificacion('❌ Eliminado de favoritos');
                } else {
                    favoritos.push(productoId);
                    mostrarNotificacion('❤️ Agregado a favoritos');
                }

                this.guardar(favoritos);
                this.actualizarIconos();
                this.actualizarContador();
            });
        }
    };

    // ==================== INICIALIZACIÓN ====================

    document.addEventListener('DOMContentLoaded', function() {
        console.log('🚀 TiendaTech2 App inicializada');

        // Actualizar contadores iniciales
        Carrito.actualizarContador();
        Favoritos.actualizarContador();
        Favoritos.actualizarIconos();

        // Event listeners para botones de carrito
        document.querySelectorAll('.btn-agregar-carrito').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                const productoId = parseInt(this.getAttribute('data-producto-id'));
                if (productoId) {
                    console.log('➕ Agregando al carrito:', productoId);
                    Carrito.agregar(productoId, 1);
                }
            });
        });

        // Event listeners para botones de favoritos
        document.querySelectorAll('.btn-favorito').forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                const productoId = parseInt(this.getAttribute('data-producto-id'));
                if (productoId) {
                    console.log('❤️ Toggle favorito:', productoId);
                    Favoritos.toggle(productoId);
                }
            });
        });
    });

    // Exponer funciones globalmente para uso en inline handlers
    window.TiendaTech = {
        Carrito: Carrito,
        Favoritos: Favoritos
    };

})();
