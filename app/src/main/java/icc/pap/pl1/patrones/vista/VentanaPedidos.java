package icc.pap.pl1.patrones.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

public class VentanaPedidos extends JFrame {
    private ServicioPedidos servicio;
    private RegistroPrototipos registro = new RegistroPrototipos();
    private int contadorPedidos = 1;
    private Map<String, Pedido> pedidosActivos;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JPanel pnlMostrarIngredientes;
    private CardLayout cardLayout;
    private JLabel lblDetalleId;
    private JLabel lblDetalleTotal;

    public VentanaPedidos() {
        this.servicio = new ServicioPedidos();
        this.pedidosActivos = new HashMap<>();

        setTitle("Cafetería UPS by Bryam");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlIzquierdo = new JPanel(new BorderLayout(5, 15));
        pnlIzquierdo.setPreferredSize(new Dimension(300, 0));
        pnlIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel panelProductos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos disponibles"));

        JButton btnCafe = crearBoton("Café", "$2.20",
                "/Users/bryan/Documents/icc-pap-pl1-patrones/app/src/imagenes/cafe.jpeg");
        JButton btnEnsalada = crearBoton("Ensalada", "$3.00",
                "/Users/bryan/Documents/icc-pap-pl1-patrones/app/src/imagenes/ensalada.jpeg");
        JButton btnPizza = crearBoton("Pizza", "$5.00",
                "/Users/bryan/Documents/icc-pap-pl1-patrones/app/src/imagenes/pizza.jpeg");

        panelProductos.add(btnCafe);
        panelProductos.add(btnEnsalada);
        panelProductos.add(btnPizza);

        JButton btnDuplicar = new JButton("Duplicar producto seleccionado");

        JPanel pnlTopIzquierda = new JPanel(new BorderLayout(0, 10));
        pnlTopIzquierda.add(panelProductos, BorderLayout.CENTER);
        pnlTopIzquierda.add(btnDuplicar, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        pnlMostrarIngredientes = new JPanel(cardLayout);
        pnlMostrarIngredientes.setBorder(BorderFactory.createTitledBorder("Ingredientes compatibles"));

        JPanel cartaCafe = crearPanelIngredientes(new String[] { "Azúcar", "Crema" },
                new String[] { "$0.50", "$1.20" });
        JPanel cartaEnsalada = crearPanelIngredientes(new String[] { "Tomate", "Pollo" },
                new String[] { "$1.10", "$1.50" });
        JPanel cartaPizza = crearPanelIngredientes(new String[] { "Queso", "Pepperoni" },
                new String[] { "$1.00", "$1.10" });

        pnlMostrarIngredientes.add(cartaCafe, "CAFÉ");
        pnlMostrarIngredientes.add(cartaEnsalada, "ENSALADA");
        pnlMostrarIngredientes.add(cartaPizza, "PIZZA");

        JButton btnReiniciar = new JButton("Reiniciar pedidos");

        JPanel pnlBotIzquierda = new JPanel(new BorderLayout(0, 10));

        pnlBotIzquierda.add(pnlMostrarIngredientes, BorderLayout.CENTER);
        pnlBotIzquierda.add(btnReiniciar, BorderLayout.SOUTH);

        pnlIzquierdo.add(pnlTopIzquierda, BorderLayout.NORTH);
        pnlIzquierdo.add(pnlBotIzquierda, BorderLayout.CENTER);

        JPanel pnlDetalles = new JPanel(new BorderLayout(20, 10));
        pnlDetalles.setBorder(BorderFactory.createTitledBorder("Resumen de la Cuenta"));

        JPanel pnlInfoBase = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTextoCantidad = new JLabel("Total de artículos: ");
        lblDetalleId = new JLabel("0");
        lblDetalleId.setFont(new Font("SansSerif", Font.BOLD, 16));

        pnlInfoBase.add(lblTextoCantidad);
        pnlInfoBase.add(lblDetalleId);
        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTextoTotal = new JLabel("Total a cobrar: ");
        lblDetalleTotal = new JLabel("$0.00");
        lblDetalleTotal.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblDetalleTotal.setForeground(new Color(41, 128, 185));

        pnlTotal.add(lblTextoTotal);
        pnlTotal.add(lblDetalleTotal);

        pnlDetalles.add(pnlInfoBase, BorderLayout.WEST);
        pnlDetalles.add(pnlTotal, BorderLayout.EAST);

        JPanel pnlCentral = new JPanel(new BorderLayout(10, 10));
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlCentral.add(pnlDetalles, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel(new String[] { "ID", "Producto", "Composicion", "Total" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaPedidos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Ordenes en curso - Barra Principal"));

        pnlCentral.add(scrollTabla, BorderLayout.CENTER);
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarPanelDetalles();
            }
        });

        add(pnlIzquierdo, BorderLayout.WEST);
        add(pnlCentral, BorderLayout.CENTER);
        btnCafe.addActionListener(e -> agregarPedidoDirecto(TipoProducto.CAFE, "CAFE"));
        btnEnsalada.addActionListener(e -> agregarPedidoDirecto(TipoProducto.ENSALADA, "ENSALADA"));
        btnPizza.addActionListener(e -> agregarPedidoDirecto(TipoProducto.PIZZA, "PIZZA"));

        btnDuplicar.addActionListener(e -> {
            int filaSel = tablaPedidos.getSelectedRow();

            if (filaSel == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla para duplicarlo.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            TipoProducto tipo = (TipoProducto) modeloTabla.getValueAt(filaSel, 1);
            agregarPedidoDirecto(tipo, tipo.name());
        });

        btnReiniciar.addActionListener(e -> {
            servicio.reiniciarPedidos();
            pedidosActivos.clear();
            modeloTabla.setRowCount(0);
        });

    }

    private JButton crearBoton(String nombre, String precio, String rutaIcono) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(110, 100));

        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        try {
            ImageIcon iconoOriginal = new ImageIcon(rutaIcono);
            java.awt.Image imgEscalada = iconoOriginal.getImage().getScaledInstance(45, 45,
                    java.awt.Image.SCALE_SMOOTH);
            lblNombre.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception ex) {
            System.err.println("No se pudo cargar la imagen: " + rutaIcono);
        }
        lblNombre.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblNombre.setHorizontalTextPosition(SwingConstants.CENTER);

        JLabel lblPrecio = new JLabel(precio, SwingConstants.CENTER);

        btn.add(lblNombre, BorderLayout.CENTER);
        btn.add(lblPrecio, BorderLayout.SOUTH);

        return btn;

    }

    private JPanel crearPanelIngredientes(String[] nombres, String[] precios) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < nombres.length; i++) {
            String nombreIngrediente = nombres[i];

            JButton btn = new JButton();
            btn.setLayout(new BorderLayout());
            btn.add(new JLabel(nombreIngrediente, SwingConstants.CENTER), BorderLayout.CENTER);
            btn.add(new JLabel(precios[i], SwingConstants.CENTER), BorderLayout.SOUTH);

            btn.addActionListener(e -> procesarIngrediente(nombreIngrediente));

            btn.setPreferredSize(new Dimension(100, 50));

            panel.add(btn);
        }
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.NORTH);
        return contenedor;

    }

    private void agregarPedidoDirecto(TipoProducto tipo, String nombreCarta) {
        cardLayout.show(pnlMostrarIngredientes, nombreCarta);
        String nuevoId = String.format("P-%03d", contadorPedidos++);
        Pedido nuevo = registro.clonar(tipo, nuevoId);

        if (nuevo != null) {
            pedidosActivos.put(nuevo.idProducto(), nuevo);
            modeloTabla.addRow(new Object[] {
                    nuevo.idProducto(), tipo, nuevo.descripcion(), String.format("$%.2f", nuevo.precio())
            });

            int ultimaFila = modeloTabla.getRowCount() - 1;
            tablaPedidos.setRowSelectionInterval(ultimaFila, ultimaFila);
        }
    }

    private void procesarIngrediente(String nombreIngrediente) {
        int filaSel = tablaPedidos.getSelectedRow();

        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Error: No hay pedido seleccionado en la tabla.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idPedido = (String) modeloTabla.getValueAt(filaSel, 0);
        Pedido pedidoActual = pedidosActivos.get(idPedido);

        try {
            Pedido pedidoDecorado = servicio.personalizarPedido(pedidoActual, nombreIngrediente);

            pedidosActivos.put(idPedido, pedidoDecorado);
            modeloTabla.setValueAt(pedidoDecorado.descripcion(), filaSel, 2);
            modeloTabla.setValueAt(String.format("$%.2f", pedidoDecorado.precio()), filaSel, 3);
            actualizarPanelDetalles();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Incompatible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPanelDetalles() {
        double granTotal = 0.0;
        for (Pedido p : pedidosActivos.values()) {
            granTotal += p.precio().doubleValue();
        }
        lblDetalleTotal.setText(String.format("$%.2f", granTotal));
        int cantidadItems = modeloTabla.getRowCount();
        lblDetalleId.setText(String.valueOf(cantidadItems));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new VentanaPedidos().setVisible(true));
    }

}
