package repositorio;

import models.Categoria;
import models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplment implements Repository<Producto>{
//obtener la conexion a la bdd
    private Connection conn;

    //obtengo mi conexion mediante el constructor
    public ProductoRepositoryJdbcImplment(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select p.* , c.nombreCategoria as categoria FROM producto as p " +
                     " INNER JOIN categoria as c ON (p.idCategoria=c.id) order by p.id ASC")) {
            while(rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }
    //implementamos un metodo para buscar un registro por un ID

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try(PreparedStatement stmt = conn.prepareStatement("select p.*, c.nombreCategoria as categoria from producto as p " +
                " inner join categoria as c ON (p.idCategoria = c.id)where p.id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        if(producto.getId() !=null && producto.getId() > 0) {
            sql = "update producto set nombreCategoria=?, idCategoria=?, cantidad=?, precio=?, descripcion=?, codigo=? " +
                    " fecha_elaboracion=?, fecha_caducidad=? where id=?";
        }else{
            sql="insert into producto (nombreCategoria, idCategoria, cantidad, precio, descripcion, codigo, fecha_elaboracion, fecha_caducidad, condicion) " +
                    " values (?,?,?,?,?,?,?,?,1)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombreProducto());
            stmt.setInt(2, producto.getCantidad());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getDescripcion());
            stmt.setString(5, producto.getCodigo());
            stmt.setLong(6, producto.getCategoria().getId());

            if(producto.getId()!=null && producto.getId()>0) {
                stmt.setLong(7, producto.getId());
            } else {
                stmt.setDate(8, Date.valueOf(producto.getFechaElaboracion()));
                stmt.setDate(9, Date.valueOf(producto.getFechaCaducidad()));
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        /*String sql= "delete from producto where id=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }*/
        //metodo desactivar
        String sql ="UPDATE producto SET condicion =0 WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    @Override
    public void activar(Long id) throws SQLException {
        String sql = "UPDATE producto SET condicion = 1 WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombreProducto(rs.getString("nombreProducto"));
        p.setCantidad(rs.getInt("cantidad"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setCondicion(rs.getInt("condicion"));
        //creamos un nuevo objeto de tipo categoria
        Categoria c = new Categoria();
        c.setId(rs.getLong("idCategoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
