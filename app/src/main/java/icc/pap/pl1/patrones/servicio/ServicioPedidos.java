package icc.pap.pl1.patrones.servicio;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.Pepperoni;
import icc.pap.pl1.patrones.modelo.Pollo;
import icc.pap.pl1.patrones.modelo.RegistroPrototipos;
import icc.pap.pl1.patrones.modelo.Queso;
import icc.pap.pl1.patrones.modelo.Tomate;
import icc.pap.pl1.patrones.modelo.Azucar;
import icc.pap.pl1.patrones.modelo.Crema;

public class ServicioPedidos {
    private RegistroPrototipos prototipos;
    private int contadorPedidos;

    public ServicioPedidos() {
        this.prototipos = new RegistroPrototipos();
        this.contadorPedidos = 1;
    }

    public Pedido crearPedido(String tipoProducto) {
        String nuevoId = String.format("P-001", contadorPedidos);

        contadorPedidos++;

        return prototipos.clonar(tipoProducto, nuevoId);
    }

    public  Pedido personalizarPedido(Pedido pedido, String ingrediente) throws Exception{
        String base = pedido.tipo();

        if(base.equals("Café")){
            if(!ingrediente.equals("Azúcar") && !ingrediente.equals("Crema")){
                throw new Exception("Error: Ingrediente incompatible con Café");
            }
        }else if(base.equals("Ensalada secilla")){
            if(!ingrediente.equals("Tomate") && !ingrediente.equals("Pollo")){
                throw new Exception("Error: Ingrediente incompatible con Ensalada");
            }
        }else if(base.equals("Pizza sencilla")){
            if(!ingrediente.equals("Queso") && !ingrediente.equals("Pepperoni")){
                throw new Exception("Error: Ingrediente incompatible con Pizza");
            }
        }

        return decoradorPedido(pedido, ingrediente);
    }

    private Pedido decoradorPedido(Pedido pedido, String ingrediente) {
        if(ingrediente.equals("Azúcar")){
            return new Azucar(pedido);
        }else if(ingrediente.equals("Crema")){
            return new Crema(pedido);
        }else if(ingrediente.equals("Tomate")){
            return new Tomate(pedido);
        }else if(ingrediente.equals("Pollo")){
            return new Pollo(pedido);
        }else if(ingrediente.equals("Queso")){
            return new Queso(pedido);
        }else if(ingrediente.equals("Pepperoni")){
            return new Pepperoni(pedido);
        }
        return pedido;
    }


}
