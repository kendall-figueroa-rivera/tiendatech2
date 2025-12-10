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
