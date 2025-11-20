package models;

public class Categoria {
    private Long id;
    private String nombre;
    private String descripcion;
    private int estado;

    public Categoria() {
    }

    public Categoria(Long id, int estado, String nombre, String descripcion) {
        this.id = id;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    //implementamos los metodos getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
