package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

public class Cafe extends ProductoBase {

    public Cafe(String idProducto) {
        super(idProducto, "Café", "Café", new BigDecimal("2.20"));
    }

    @Override
    public ProductoBase duplicado(String nuevoId) {
        return new Cafe(nuevoId);

    }
    
}
