package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private Integer idCliente;
    //De esta forma se cumple que al crear un objeto se inicializza las funciones de clientes servicio
    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
        iniciarForma();

        btnGuardar.addActionListener(e ->guardarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        clienteTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        btnLimpiar.addActionListener(e -> limpiarFormulario());
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
        //this.tableModel = new DefaultTableModel(0,4);
        //Se evita la edicion de los valores de las celdas de las tablas
        this.tableModel = new DefaultTableModel(0,4){
          @Override
          public boolean isCellEditable(int row, int column) {
              return false;
          }
        };
        String[] cabeceros = {"Id","Nombre","Apellidos","Membresia"};
        this.tableModel.setColumnIdentifiers(cabeceros);
        this.clienteTabla = new JTable(this.tableModel);
        //Se restringe la seleccion de la tabla a un solo registro
        this.clienteTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
    private void guardarCliente(){
        if(txtNombre.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            txtNombre.requestFocusInWindow();
            return;
        }
        if(txtApellido.getText().equals("")){
            mostrarMensaje("Proporciona un apellido");
            txtApellido.requestFocusInWindow();
            return;
        }
        if(txtMembresia.getText().equals("")){
            mostrarMensaje("Proporciona una membresia");
            txtMembresia.requestFocusInWindow();
            return;
        }
        //Recuperamos los valores del formulario
        var nombre = txtNombre.getText();
        var apellido = txtApellido.getText();
        var membresia = Integer.parseInt(txtMembresia.getText());

        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);

        this.clienteServicio.guardarCliente(cliente); //inserta de manera automatica si es nulo o no se puede insertar y modificar
        if (this.idCliente == null){
            mostrarMensaje("se agrego el nuevo cliente");
        }else
            mostrarMensaje("se modifico el nuevo cliente");

        limpiarFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado(){
        var renglon = clienteTabla.getSelectedRow(); //Es -1 cuando no se selecciono ningun registro
        if(renglon != -1){
            var id = clienteTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clienteTabla.getModel().getValueAt(renglon,1).toString();
            this.txtNombre.setText(nombre);
            var apellido = clienteTabla.getModel().getValueAt(renglon,2).toString();
            this.txtApellido.setText(apellido);
            var membresia = clienteTabla.getModel().getValueAt(renglon,3).toString();
            this.txtMembresia.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = clienteTabla.getSelectedRow();
        if(renglon != -1){

            var id = clienteTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(id);
            Cliente cliente = new Cliente(idCliente, null, null, null);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Eliminado el nuevo cliente con id " + this.idCliente);
            limpiarFormulario();
            listarClientes();

        }else
            mostrarMensaje("Debe seleccionar un cliente a Seleccionar");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtMembresia.setText("");
        // se limpia el id
        this.idCliente = null;
        // deseleccionamos el registro de la tabla
        this.clienteTabla.getSelectionModel().clearSelection();
    }
}
