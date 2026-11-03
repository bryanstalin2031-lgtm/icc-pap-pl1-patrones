package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;

public class Pepperoni extends DecoradorIngrediente{

    public Pepperoni(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + "+ Pepperoni";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("1.10"));
    }

    
}
