package services;

import models.Categoria;
import models.Producto;
import repositorio.CategoriaRepositoryJdbcImplement;
import repositorio.ProductoRepositoryJdbcImplment;
import repositorio.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImpl implements ProductoService {
    //Declaramos una variable de tipo ProductoRepositoryJdbcImplemen
    //private ProductoRepositoryJdbcImplment repositoryJdbc;
    //Implementamos un constuctor paa traer la conexion
    //creamos un nuevo objeto, genero de la interfaz repository pero pasamos el tipo de objeto que
    //a implementar
    private Repository<Producto> repositoryJdbc;
    private Repository<Categoria> repositoryCategoriaJdbc;

    public ProductoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new ProductoRepositoryJdbcImplment(connection);
        this.repositoryCategoriaJdbc = new CategoriaRepositoryJdbcImplement(connection);
    }
    @Override
    public List<Producto> listar() {
        try{
            return repositoryJdbc.listar();
        }catch(SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try{
            return Optional.ofNullable(repositoryJdbc.porId(id));
        }catch (SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        }catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategorias() {
        try{
            return repositoryCategoriaJdbc.listar();
        }catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try{
            return Optional.ofNullable(repositoryCategoriaJdbc.porId(id));
        }catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
    @Override
    public void activar(Long id) {
        try {
            repositoryJdbc.activar(id);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

}
