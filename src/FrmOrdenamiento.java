import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import entidades.Documento;
import servicios.ServicioDocumento;
import servicios.Util;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import servicios.ServicioDocumento;

public class FrmOrdenamiento extends JFrame {

    private static List<Documento> documentos = new ArrayList<>();

    private JButton btnOrdenarBurbuja;
    private JButton btnOrdenarRapido;
    private JButton btnOrdenarInsercion;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JToolBar tbOrdenamiento;
    private JComboBox cmbCriterio;
    private JTextField txtTiempo;
    private JButton btnBuscar;
    private JTextField txtBuscar;

    private JTable tblDocumentos;

    public FrmOrdenamiento() {

        tbOrdenamiento = new JToolBar();
        btnOrdenarBurbuja = new JButton();
        btnOrdenarInsercion = new JButton();
        btnOrdenarRapido = new JButton();
        btnAnterior = new JButton();
        btnSiguiente = new JButton();
        cmbCriterio = new JComboBox();
        txtTiempo = new JTextField();

        btnBuscar = new JButton();
        txtBuscar = new JTextField();

        tblDocumentos = new JTable();

        setSize(600, 400);
        setTitle("Ordenamiento Documentos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnOrdenarBurbuja.setIcon(new ImageIcon(getClass().getResource("/iconos/Ordenar.png")));
        btnOrdenarBurbuja.setToolTipText("Ordenar Burbuja");
        btnOrdenarBurbuja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarBurbujaClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarBurbuja);

        btnOrdenarRapido.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarRapido.png")));
        btnOrdenarRapido.setToolTipText("Ordenar Rapido");
        btnOrdenarRapido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarRapidoClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarRapido);

        btnOrdenarInsercion.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarInsercion.png")));
        btnOrdenarInsercion.setToolTipText("Ordenar Inserción");
        btnOrdenarInsercion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarInsercionClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarInsercion);

        cmbCriterio.setModel(new DefaultComboBoxModel(
                new String[] { "Nombre Completo, Tipo de Documento", "Tipo de Documento, Nombre Completo" }));
        tbOrdenamiento.add(cmbCriterio);
        tbOrdenamiento.add(txtTiempo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/iconos/Buscar.png")));
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBuscar(evt);
            }
        });
        tbOrdenamiento.add(btnBuscar);
        tbOrdenamiento.add(txtBuscar);

        btnAnterior.setIcon(new ImageIcon(getClass().getResource("/iconos/Anterior.png")));
        btnAnterior.setToolTipText("Buscar Anterior");
        btnAnterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnbuscarAnterior(evt);
            }

        });

        btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/iconos/Siguiente.png")));
        btnSiguiente.setToolTipText("Buscar Siguiente");
        btnSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnbuscarSiguiente(evt);
            }

        });        
        
        tbOrdenamiento.add(btnAnterior);
        tbOrdenamiento.add(btnSiguiente);

        JScrollPane spDocumentos = new JScrollPane(tblDocumentos);

        getContentPane().add(tbOrdenamiento, BorderLayout.NORTH);
        getContentPane().add(spDocumentos, BorderLayout.CENTER);

        String nombreArchivo = System.getProperty("user.dir")
                + "/src/datos/Datos.csv";

        ServicioDocumento.cargar(nombreArchivo);
        ServicioDocumento.mostrar(tblDocumentos);
    }

    private static boolean ordenado = false;

    private void btnOrdenarBurbujaClick(ActionEvent evt) {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            ServicioDocumento.ordenarBurbuja(cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            ServicioDocumento.mostrar(tblDocumentos);
            ordenado = true;
        }
    }

    private void btnOrdenarRapidoClick(ActionEvent evt) {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            ServicioDocumento.ordenarRapido(cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            ServicioDocumento.mostrar(tblDocumentos);
            ordenado = true;
        }
    }

    private void btnOrdenarInsercionClick(ActionEvent evt) {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            ServicioDocumento.ordenarMezcla(cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            ServicioDocumento.mostrar(tblDocumentos);
            ordenado = true;
        }
    }

    private static int orden = -1;

    private void btnBuscar(ActionEvent evt) {

        Util.iniciarCronometro();
        String ingreso = txtBuscar.getText();

        if(!ingreso.equals("")) {
            
            int criterio = cmbCriterio.getSelectedIndex();
            if(criterio >= 0) {
                if(criterio != orden && ordenado == false) {
                    ServicioDocumento.ordenarRapido(criterio);
                    ServicioDocumento.mostrar(tblDocumentos);
                    orden = criterio;
                }
                
                int binario = ServicioDocumento.busquedaBinaria(0, ServicioDocumento.getDocumentos().size() - 1, ingreso, criterio);
                if(binario >= 0) {
                    tblDocumentos.setRowSelectionInterval(binario, binario);
                    tblDocumentos.scrollRectToVisible(tblDocumentos.getCellRect(binario, 0, true));
                } else {
                    JOptionPane.showMessageDialog(null, "El elemento no fue encontrado");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes ingresar un parametro");
        }
        
        txtTiempo.setText(Util.getTextoTiempoCronometro());
    }

    private void btnbuscarAnterior(ActionEvent evt) {
        String ingreso = txtBuscar.getText();
        int criterio = cmbCriterio.getSelectedIndex();
        int binario = ServicioDocumento.busquedaBinaria(0, ServicioDocumento.getDocumentos().size() - 1, ingreso, criterio);
        int anterior = ServicioDocumento.Anterior(binario, ingreso, criterio);
        
        if(anterior >= 0) {
            tblDocumentos.setRowSelectionInterval(anterior, anterior);
            tblDocumentos.scrollRectToVisible(tblDocumentos.getCellRect(anterior, 0, true));
        }
    }
    
    private void btnbuscarSiguiente(ActionEvent evt) {
        String ingreso = txtBuscar.getText();
        int criterio = cmbCriterio.getSelectedIndex();
        int binario = ServicioDocumento.busquedaBinaria(0, ServicioDocumento.getDocumentos().size() - 1, ingreso, criterio);
        int siguiente = ServicioDocumento.Siguiente(binario, ingreso, criterio);
        
        if(siguiente >= 0) {
            tblDocumentos.setRowSelectionInterval(siguiente, siguiente);
            tblDocumentos.scrollRectToVisible(tblDocumentos.getCellRect(siguiente, 0, true));
        }
    }

}