package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;

public interface Pedido {

    String descripcion();

    BigDecimal precio();

    String idProducto();

    String tipo();


}
