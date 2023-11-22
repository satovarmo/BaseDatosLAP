
package GUI;


import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ObjetosInteractivos {
    MotorInterfaz motint;
      public ObjetosInteractivos(MotorInterfaz motint){
        this.motint=motint;
        
    }
    // GUARDAMOS LA INFORMACION DEL USUARIO
    public String usuario;
    public String contra;
    ArrayList<String> tablas = new ArrayList<String> ();
    ArrayList<String> NombreColumnas = new ArrayList<String> ();
    ArrayList<String> TipoColumnas = new ArrayList<String> ();
    ArrayList<String> LlaveColumnas = new ArrayList<String> ();
    DefaultTableModel tabla =  new DefaultTableModel();
    JTableHeader header = new JTableHeader();
    
    //PRIMERA PANTALLA (INGRESO DEL NUMERO DE JUGADORES)   
    public JFrame ventana=new JFrame();
    public JComboBox TipoUsuario=new JComboBox();
    public JButton BotonAceptar=new JButton("Ingresar");
    public JPanel PanelIngreso=new JPanel();
    public JLabel Títulobd=new JLabel("CLUB LAP TENIS");
    public JPasswordField TextContraseña=new JPasswordField();
    
    
    //SEGUNDA PANTALLA (SELECCIÓN TABLA)   
    
    public JPanel PanelTabla=new JPanel();
    public JLabel LabelTabla=new JLabel();
    public JComboBox BoxTabla=new JComboBox();
    public JTable TablaVisual=new JTable(){
  // Sobrescribir el método isCellEditable
   @Override
            public boolean isCellEditable(int row, int column) {
                // Devolver true solo para la última columna
                return false;
            }
};
    public JButton BotonInsertar=new JButton("Insertar");
    public JButton BotonEliminar=new JButton("Eliminar");
    public JButton BotonActualizar=new JButton("Actualizar");
    public JButton BotonVisualizar=new JButton("Visualizar celda");
    
    public JButton CerrarSesión=new JButton("Cerrar sesión");
    
    public JLabel Botones=new JLabel();
    public JButton BotonVerFila=new JButton("Ver Fila");
    public JButton BotonEliminarFila=new JButton("Eliminar Fila");
    public JButton BotonActualizarFila=new JButton("Actualizar Fila");
    
    //PANTALLA PARA INSERCIÓN
    public JFrame ventanaInsert=new JFrame();
    public JPanel PanelInsert=new JPanel();
    public JTextField[] listText;
    public JButton BotonInsertarFila=new JButton("Insertar Fila");
    
    
    
    //PANTALLA PARA ACTUALIZAR
    public JFrame ventanaAct=new JFrame();
    public JPanel PanelAct=new JPanel();
    public JTextField TextAct;
    public JButton BotonAceptarFila=new JButton("Actualizar celda");
    
    //PANTALLA INSTRUCCIONES 
    /* public JFrame ventanaIns=new JFrame();
    public JTextArea TextIns=new JTextArea(),TitIns=new JTextArea();
    public JScrollPane ScrollIns=new JScrollPane(TextIns);
    public JButton AceptarIns=new JButton("Aceptar");
    public JPanel PanelIns=new JPanel();
    
    
    //SEGUNDA PANTALLA (INGRESO DATOS DE JUGADORES)
    public JLabel TextIngNombre=new JLabel(),TextIngColor=new JLabel(),TextIngGrafo=new JLabel();
    public JTextField IngNombre=new JTextField();
    public JColorChooser IngColor=new JColorChooser();
    public JComboBox IngGraf=new JComboBox();
    public JButton BtnReg=new JButton("Registrar"),BtnClr=new JButton("Color");
    public JPanel PanelDibujo=new JPanel();
    public Color PosibleColor;
    
    //PANTALLA COLOR 
    public JFrame ventanaColor=new JFrame();
    public JButton AceptarColor=new JButton("Aceptar");
    public JPanel PanelColor=new JPanel(),Paleta=new JPanel();
    
    
    //TERCERAPANTALLA (JUEGO)
    public JLabel TurnoJug=new JLabel();
    
    //PANTALLA GANADOR
    public JLabel TextGanador=new JLabel();
    public JButton Salir=new JButton("Salir"),Volver=new JButton("Jugar de Nuevo");
    
    */
}
