package services;

import models.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Clase ProductosServicesImplement.
 *
 * @author Genesis Escobar
 * @version 1.0
 * @since 2025-11-06
 *
 * Descripción:
 * Implementa la interfaz {ProductoServices} y define el comportamiento
 * del metodo {listar()} para devolver una lista predefinida de objetos
 * {Producto}.
 *
 * Esta clase simula la obtención de datos desde una fuente estática,
 * como si provinieran de una base de datos o servicio externo.
 * Es útil para pruebas, demostraciones o cuando aún no se integra
 * una capa de persistencia real.
 */
public class ProductoServiceImplement implements ProductoService {

    @Override
    public List<Producto> listar() {
        return List.of();
    }
    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals((id))).findAny();
    }

}
