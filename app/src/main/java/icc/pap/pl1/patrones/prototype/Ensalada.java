package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

public class Ensalada extends ProductoBase {

    public Ensalada(String idProducto) {
        super(idProducto, "Ensalada sencilla", "Ensalada sencilla ", new BigDecimal("3.00"));
    }

    @Override
    public ProductoBase duplicado(String nuevoId) {
        return  new Ensalada(nuevoId);
    
    }
    
}
