package icc.pap.pl1.patrones.decorator;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.modelo.Pedido;

public abstract class DecoradorIngrediente implements Pedido {
    protected Pedido pedido;

    public DecoradorIngrediente(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String descripcion() {
        if (pedido != null) {
            return pedido.descripcion();
        }
        return "";

    }

    @Override
    public String idProducto() {
        if (pedido != null) {
            return pedido.descripcion();
        }
        return "";

    }

    @Override
    public BigDecimal precio() {
        if (pedido != null) {
            return pedido.precio();
        }
        return BigDecimal.ZERO;

    }

    @Override
    public String tipo() {
        if (pedido != null) {
            return pedido.tipo();
        }
        return "";

    }

}
