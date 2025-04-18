package gm.zona_fit.gui;

import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component //Se recupera los objetos de Spring
public class ZonaFitForma extends JFrame {
    private JPanel panelPrincipal;
    private JTable clienteTabla;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtMembresia;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    //@Autowired No se usa en esta ubicación, ya que se estaria inyectando antes de la creación del objeto y se necesita a la vez de la
    // creación, es decir, juntos al constructor.
    IClienteServicio clienteServicio;
    private DefaultTableModel tableModel;

    //De esta forma se cumple que al crear un objeto se inicializza las funciones de clientes servicio
    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
        iniciarForma();
    }

    private void iniciarForma() {
        setTitle("Zona Fit");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); //centra la ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tableModel = new DefaultTableModel(0,4);
        String[] cabeceros = {"Id","Nombre","Apellidos","Membresia"};
        this.tableModel.setColumnIdentifiers(cabeceros);
        this.clienteTabla = new JTable(this.tableModel);
        //cargar listado de clientes
        listarClientes();
    }

    private void listarClientes() {
        this.tableModel.setRowCount(0);
        var clientes = this.clienteServicio.listarCliente();
        clientes.forEach(cliente -> {
            Object[] row = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tableModel.addRow(row);
        });
    }
    //No se usa main, ya que de esa manera no se activa @Autowired
}
