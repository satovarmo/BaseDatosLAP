
package GUI;


import java.awt.Image;
import java.util.ArrayList;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerItem;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
            ConstructorMenu();
    }
    // GUARDAMOS LA INFORMACION DEL USUARIO
public void ConstructorMenu(){
        drawer.addChild(eliminar);
        drawer.space(5);
        drawer.addChild(actualizar);
        drawer.space(5);
        drawer.addChild(insertar);
        drawer.space(5);
        drawer.addFooter(CerrarSesión);
        drawer.event(motint.evt);    
}        
public ImageIcon AjustarImg(String Rutimg, int w, int h){
    Image logotipo = new ImageIcon(getClass().getResource(   Rutimg)).getImage();
    Image imgEscalada = logotipo.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    ImageIcon icono = new ImageIcon(imgEscalada);
    return icono;
}
    public String usuario;
    public String contra;
    ArrayList<String> tablas = new ArrayList<String> ();
    ArrayList<String> NombreColumnas = new ArrayList<String> ();
    ArrayList<String> TipoColumnas = new ArrayList<String> ();
    ArrayList<String> LlaveColumnas = new ArrayList<String> ();
    DefaultTableModel tabla =  new DefaultTableModel();
    JTableHeader header = new JTableHeader();
    
    //PRIMERA PANTALLA 
    public ImageIcon logo = new ImageIcon(getClass().getResource("/Imagenes/LogoSinFondo.png"));
    public JFrame ventana=new JFrame();
    public JComboBox TipoUsuario=new JComboBox();
    public JButton BotonAceptar=new JButton("Ingresar");
    public JPanel PanelIngreso=new JPanel();
    public JLabel Títulobd=new JLabel();
    public JPasswordField TextContraseña=new JPasswordField();
  

//SEGUNDA PANTALLA 
    
    public JPanel PanelTabla=new JPanel();
    public JTable TablaVisual=new JTable(){
  // Sobrescribir el método isCellEditable
   @Override
            public boolean isCellEditable(int row, int column) {
                // Devolver true solo para la última columna
                return false;
            }
};
    public JLabel TituloTabla=new JLabel();
    
    
    
    public DrawerItem eliminar=new DrawerItem("Eliminar fila").icon(AjustarImg("/Imagenes/Eliminar.png", 30, 30)).build();
    public DrawerItem actualizar=new DrawerItem("Actualizar celda").icon(AjustarImg("/Imagenes/actualizar.png", 30, 30)).build();
    public DrawerItem insertar=(new DrawerItem("Insertar fila").icon(AjustarImg("/Imagenes/insertar.png", 30, 30))).build();
    public DrawerItem CerrarSesión=(new DrawerItem("CERRAR SESIÓN").icon(AjustarImg("/Imagenes/insertar.png", 30, 30))).build();
    
    
    
    
    
    //PANTALLA PARA (SELECCIÓN TABLA)   
    public JFrame ventanaTab=new JFrame();
    public JPanel PanelTab=new JPanel();
    public JComboBox BoxTabla=new JComboBox();
    public JButton BotonAceptarTab=new JButton("Aceptar");
    
    
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
    
    
    
    
    public JButton btnPanel=new JButton("|||");
    public Drawer drawer=Drawer.newDrawer(ventana);
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
