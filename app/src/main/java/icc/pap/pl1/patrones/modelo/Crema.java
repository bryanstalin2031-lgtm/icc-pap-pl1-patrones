package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;

public class Crema extends DecoradorIngrediente {

    public Crema(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + "+ Crema";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("1.20"));
    }

}
