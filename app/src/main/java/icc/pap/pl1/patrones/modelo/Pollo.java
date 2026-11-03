package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;

public class Pollo extends DecoradorIngrediente {

    public Pollo(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + "+ Pollo";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("1.50"));
    }

}
