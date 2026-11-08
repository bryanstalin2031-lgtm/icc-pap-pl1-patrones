package icc.pap.pl1.patrones.modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RegistroPrototipos {
    private final Map<String, Pedido> prototipos = new HashMap<>();

    public RegistroPrototipos() {
        prototipos.put("CAF", new ProductoBase("PROTOTIPOCAFE", "Café", "Café", new BigDecimal("2.20")));
        prototipos.put("ENSALADA",
                new ProductoBase("PROTOTIPOENSALA", "Ensalada sencilla", "Ensalada sencilla", new BigDecimal("3.00")));
        prototipos.put("PIZZA",
                new ProductoBase("PROTOTIPOPIZZA", "Piza sencilla", "Pizza sencilla", new BigDecimal("5.00")));

    }

    public Pedido clonar(String tipo, String nuevoId) {
        Pedido pedidoduplicado = prototipos.get(tipo.toUpperCase());

        if (pedidoduplicado != null) {
            return pedidoduplicado.duplicado(nuevoId);
        }
        return null;

    }

}
