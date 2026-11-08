
package icc.pap.pl1.patrones;

import icc.pap.pl1.patrones.decorator.Azucar;
import icc.pap.pl1.patrones.decorator.Crema;
import icc.pap.pl1.patrones.decorator.Pepperoni;
import icc.pap.pl1.patrones.decorator.Pollo;
import icc.pap.pl1.patrones.decorator.Queso;
import icc.pap.pl1.patrones.decorator.Tomate;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

public class App {
    public static void main(String[] args) {
        RegistroPrototipos registro = new RegistroPrototipos();
        ServicioPedidos servicio = new ServicioPedidos();

        System.out.println();
        System.out.println("=== Evidencias: Casos de 1 al 11 ===\n");

        System.out.println("=== CA01 ===\n");
        Pedido cafe1 = registro.clonar(TipoProducto.CAFE, "P-001");
        Pedido cafe2 = registro.clonar(TipoProducto.CAFE, "P-002");
        System.out.println("Pedido 1: " + cafe1.idProducto() + " | " + cafe1.descripcion() + " | $" + cafe1.precio());
        System.out.println(
                "Pedido 2: " + cafe2.idProducto() + " | " + cafe2.descripcion() + " | $" + cafe2.precio() + "\n");

        System.out.println("=== CA02 ===\n");
        System.out.println("Tienen la misma referencia: " + (cafe1 == cafe2) + "\n");

        System.out.println("=== CA03 ===\n");
        cafe1 = new Crema(new Azucar(cafe1));
        System.out.println("Café con extras: " + cafe1.descripcion() + " |  Total: $" + cafe1.precio() + "\n");

        System.out.println("=== CA04 ===\n");
        System.out.println("Café 2 sin extras: " + cafe2.descripcion() + " | Total: $" + cafe2.precio() + "\n");

        System.out.println("=== CA05 ===\n");
        Pedido ensalada = registro.clonar(TipoProducto.ENSALADA, "P-003");
        ensalada = new Pollo(new Tomate(ensalada));
        System.out.println("Ensalada: " + ensalada.descripcion() + " | Total: $" + ensalada.precio() + "\n");

        System.out.println("=== CA06 ===\n");
        Pedido pizza = registro.clonar(TipoProducto.PIZZA, "P-004");
        pizza = new Pepperoni(new Queso(pizza));
        System.out.println("Pizza: " + pizza.descripcion() + "| Total: $" + pizza.precio() + "\n");

        System.out.println("=== CA07 ===\n");
        try {
            servicio.personalizarPedido(cafe1, "Queso");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            System.out.println("Café 1 sin cambios: " + cafe1.descripcion() + " | Total: $" + cafe1.precio() + "\n");
        }

        System.out.println("=== CA08 ===\n");
        try {
            servicio.personalizarPedido(null, "Azúcar");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
        }

        System.out.println("=== CA09 ===\n");
        Pedido invalido = registro.clonar(null, "P-005");
        if (invalido == null) {
            System.out.println("Fallo controlado: no se crea pedido" + "\n");
        }

        System.out.println("=== CA10 ===\n");
        Pedido cafe3 = registro.clonar(TipoProducto.CAFE, "P-006");
        cafe3 = new Azucar(new Azucar(cafe3));
        System.out.println("Café con doble azúcar: " + cafe3.descripcion() + " | Total: $" + cafe3.precio() + "\n");

        System.out.println("=== CA011 ===\n");
        servicio.reiniciarPedidos();

        Pedido cafe4 = servicio.crearPedido(TipoProducto.CAFE);
        System.out.println("Nuevo pedido: " + cafe4.idProducto() + " | " + cafe4.descripcion() + " | Total: $"
                + cafe4.precio() + "\n");

    }
}
