package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

public class Pizza extends ProductoBase{

    public Pizza(String idProducto) {
        super(idProducto, "Pizza sencilla", "Pizza sencilla ", new BigDecimal("5.00"));

    }

    @Override
    public ProductoBase duplicado(String nuevoId) {
        return new Pizza(nuevoId);
    }

    
}
