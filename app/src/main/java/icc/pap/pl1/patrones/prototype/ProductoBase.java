package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public abstract class ProductoBase implements Pedido, Prototipo<ProductoBase> {
    private String idProducto;
    private String tipo;
    private String descripcion;
    private BigDecimal precio;

    public ProductoBase(String idProducto, String tipo, String descripcion, BigDecimal precio) {
        this.idProducto = idProducto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }

    @Override
    public BigDecimal precio() {
        return this.precio;
    }

    @Override
    public String idProducto() {
        return this.idProducto;
    }

    @Override
    public String tipo() {
        return this.tipo;
    }

    @Override
    public abstract ProductoBase duplicado(String nuevoId); 
}
