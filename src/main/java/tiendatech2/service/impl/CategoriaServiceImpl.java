package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendatech2.model.Categoria;
import tiendatech2.repository.CategoriaRepository;
import tiendatech2.service.CategoriaService;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    public List<Categoria> listarActivas() {
        return categoriaRepository.findByActivaTrue();
    }

    @Override
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            categoria.setActiva(false);
            categoriaRepository.save(categoria);
        }
    }
}

