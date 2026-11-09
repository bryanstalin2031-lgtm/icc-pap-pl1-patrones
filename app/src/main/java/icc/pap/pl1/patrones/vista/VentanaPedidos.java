package icc.pap.pl1.patrones.vista;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

public class VentanaPedidos extends JFrame {

    private static final String RUTA_IMG = "/Users/bryan/Documents/icc-pap-pl1-patrones/app/src/imagenes/";

    private record Producto(TipoProducto tipo, String nombre, String precio, String imagen, String[][] ingredientes) {
    }

    private static final Producto[] PRODUCTOS = {
            new Producto(TipoProducto.CAFE, "Café", "$2.20", "cafe.jpeg",
                    new String[][] { { "Azúcar", "$0.50" }, { "Crema", "$1.20" } }),
            new Producto(TipoProducto.ENSALADA, "Ensalada", "$3.00", "ensalada.jpeg",
                    new String[][] { { "Tomate", "$1.10" }, { "Pollo", "$1.50" } }),
            new Producto(TipoProducto.PIZZA, "Pizza", "$5.00", "pizza.jpeg",
                    new String[][] { { "Queso", "$1.00" }, { "Pepperoni", "$1.10" } })
    };

    private final ServicioPedidos servicio = new ServicioPedidos();
    private final RegistroPrototipos registro = new RegistroPrototipos();
    private final Map<String, Pedido> pedidosActivos = new HashMap<>();
    private int contadorPedidos = 1;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pnlIngredientes = new JPanel(cardLayout);
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[] { "ID", "Producto", "Composicion", "Total" }, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tablaPedidos = new JTable(modeloTabla);
    private final JLabel lblCantidad = new JLabel("0");
    private final JLabel lblTotal = new JLabel("$0.00");

    public VentanaPedidos() {
        setTitle("Cafetería UPS by Bryam");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panelProductos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos disponibles"));
        pnlIngredientes.setBorder(BorderFactory.createTitledBorder("Ingredientes compatibles"));

        for (Producto p : PRODUCTOS) {
            JButton btn = crearBoton(p.nombre(), p.precio(), RUTA_IMG + p.imagen());
            btn.setPreferredSize(new Dimension(110, 100));
            btn.addActionListener(e -> agregarPedido(p.tipo()));
            panelProductos.add(btn);

            pnlIngredientes.add(crearPanelIngredientes(p.ingredientes()), p.tipo().name());
        }

        JButton btnDuplicar = new JButton("Duplicar producto seleccionado");
        btnDuplicar.addActionListener(e -> duplicarSeleccionado());

        JButton btnReiniciar = new JButton("Reiniciar pedidos");
        btnReiniciar.addActionListener(e -> {
            servicio.reiniciarPedidos();
            pedidosActivos.clear();
            modeloTabla.setRowCount(0);
            actualizarPanelDetalles();
        });

        JPanel arriba = new JPanel(new BorderLayout(0, 10));
        arriba.add(panelProductos, BorderLayout.CENTER);
        arriba.add(btnDuplicar, BorderLayout.SOUTH);

        JPanel abajo = new JPanel(new BorderLayout(0, 10));
        abajo.add(pnlIngredientes, BorderLayout.CENTER);
        abajo.add(btnReiniciar, BorderLayout.SOUTH);

        JPanel izquierdo = new JPanel(new BorderLayout(5, 15));
        izquierdo.setPreferredSize(new Dimension(300, 0));
        izquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        izquierdo.add(arriba, BorderLayout.NORTH);
        izquierdo.add(abajo, BorderLayout.CENTER);
        return izquierdo;
    }

    private JPanel crearPanelCentral() {
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarPanelDetalles();
            }
        });
        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createTitledBorder("Ordenes en curso - Barra Principal"));

        lblCantidad.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTotal.setForeground(new Color(41, 128, 185));

        JPanel pnlCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCantidad.add(new JLabel("Total de artículos: "));
        pnlCantidad.add(lblCantidad);

        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlTotal.add(new JLabel("Total a cobrar: "));
        pnlTotal.add(lblTotal);

        JPanel detalles = new JPanel(new BorderLayout(20, 10));
        detalles.setBorder(BorderFactory.createTitledBorder("Resumen de la Cuenta"));
        detalles.add(pnlCantidad, BorderLayout.WEST);
        detalles.add(pnlTotal, BorderLayout.EAST);

        JPanel central = new JPanel(new BorderLayout(10, 10));
        central.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        central.add(scroll, BorderLayout.CENTER);
        central.add(detalles, BorderLayout.SOUTH);
        return central;
    }

    private JPanel crearPanelIngredientes(String[][] ingredientes) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (String[] ing : ingredientes) {
            JButton btn = crearBoton(ing[0], ing[1], null);
            btn.setPreferredSize(new Dimension(100, 50));
            btn.addActionListener(e -> procesarIngrediente(ing[0]));
            panel.add(btn);
        }
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.NORTH);
        return contenedor;
    }

    private JButton crearBoton(String nombre, String precio, String rutaIcono) {
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        if (rutaIcono != null) {
            Image img = new ImageIcon(rutaIcono).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            lblNombre.setIcon(new ImageIcon(img));
            lblNombre.setVerticalTextPosition(SwingConstants.BOTTOM);
            lblNombre.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.add(lblNombre, BorderLayout.CENTER);
        btn.add(new JLabel(precio, SwingConstants.CENTER), BorderLayout.SOUTH);
        return btn;
    }

    private void agregarPedido(TipoProducto tipo) {
        cardLayout.show(pnlIngredientes, tipo.name());

        Pedido nuevo = registro.clonar(tipo, String.format("P-%03d", contadorPedidos++));
        if (nuevo == null) {
            return;
        }
        pedidosActivos.put(nuevo.idProducto(), nuevo);
        modeloTabla.addRow(new Object[] {
                nuevo.idProducto(), tipo, nuevo.descripcion(), String.format("$%.2f", nuevo.precio())
        });
        int ultima = modeloTabla.getRowCount() - 1;
        tablaPedidos.setRowSelectionInterval(ultima, ultima);
    }

    private void duplicarSeleccionado() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla para duplicarlo.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        agregarPedido((TipoProducto) modeloTabla.getValueAt(fila, 1));
    }

    private void procesarIngrediente(String ingrediente) {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Error: No hay pedido seleccionado en la tabla.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        try {
            Pedido decorado = servicio.personalizarPedido(pedidosActivos.get(id), ingrediente);
            pedidosActivos.put(id, decorado);
            modeloTabla.setValueAt(decorado.descripcion(), fila, 2);
            modeloTabla.setValueAt(String.format("$%.2f", decorado.precio()), fila, 3);
            actualizarPanelDetalles();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Incompatible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPanelDetalles() {
        double total = pedidosActivos.values().stream().mapToDouble(p -> p.precio().doubleValue()).sum();
        lblTotal.setText(String.format("$%.2f", total));
        lblCantidad.setText(String.valueOf(modeloTabla.getRowCount()));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new VentanaPedidos().setVisible(true));
    }
}