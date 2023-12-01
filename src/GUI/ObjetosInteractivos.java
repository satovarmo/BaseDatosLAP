
package GUI;


import java.awt.Image;
import java.util.ArrayList;
import java.util.Hashtable;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerItem;
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
            ConstructorDescripciones();
    }
    // GUARDAMOS LA INFORMACION DEL USUARIO
public void ConstructorMenu(){
        drawer.header(Encabezado);
        drawer.addChild(eliminar);
        drawer.space(5);
        drawer.addChild(actualizar);
        drawer.space(5);
        drawer.addChild(insertar);
        drawer.space(5);
        drawer.addFooter(CerrarSesión);
        drawer.event(motint.evt);    
}

public void ConstructorDescripciones(){
    descripciones.put("cancha","Se muestra la información de cada una de las canchas disponibles para el club.");
    descripciones.put("clase","La información de cada clase, su horario, fecha, entrenador asignado, caddi y cancha.");
    descripciones.put("objeto","Aquí está la información de los objetos, es decir de los artículos que vende el club y de los recursos que necesita, incluyendo la cantidad que se posee.");
    descripciones.put("personaclub","Se muestra la información de todas las personas asociadas al club, entrenadores, estudiantes, acudientes, afiliados, etc.");
    descripciones.put("tienda","En esta tabla esta la información de los proveedores del club.");
    descripciones.put("articulos","Aquí está la información de los productos que vende el club, con el stock y su precio correspondiente.");
    descripciones.put("caddie_recursos","Se muestra quién es el responsable, con nombre y apellido, de manejar cada recurso del que dispone el club.");
    descripciones.put("objetos_baratos","Una tabla donde muestra con cual proveedor es más barato conseguir un objeto determinado.");
    descripciones.put("recursos","Muestra los recursos de los que dispone el club, es decir los objetos necesarios para impartir clase.");
    descripciones.put("clientes_club","La tabla muestra con nombre, los clientes que han comprado y se relacionan con el club, mostrando qué tipo de personaclub son.");
    descripciones.put("cliente","Se ve la tabla sobre los clientes que han comprado en la tienda del club.");
    descripciones.put("compras","En esta tabla se ve cuando un cliente ha comprado cierta cantidad de un artículo, con fecha incluida.");
    descripciones.put("objetos_administrados","Se muestra la información, con ID, nombre y apellido de quién administra cada artículo.");
    descripciones.put("acudiente_costo","Una tabla que muestra cuanto debe pagar cada acudiente con su respectivo número de cédula.");
    descripciones.put("entrenadores","La tabla muestra el ID de cada entrenador con su nombre, apellido, etc.");
    descripciones.put("clases_entrenadores","Se puede ver quién es el entrenador encargado de enseñar cada clase.");
    descripciones.put("estudiante_costo","Estudiante costo muestra cuanto debe pagar cada estudiante y si tiene descuentos. Muestra el porcentaje al que equivalen.");
    descripciones.put("caddie","Muestra el salario del caddie según su ID.");
    descripciones.put("acudientes_estudiantes","Muestra el nombre, apellido, y contacto del acudiente de cada estudiante, en caso de emergencias.");
    descripciones.put("lista_clase","Es la lista de asistencia de cada clase, separadas con el ID de la clase en cuestión.");
    descripciones.put("entrenador","Muestra el ID de entrenadores con la información necesaria para su contratación.");
    descripciones.put("administrativo","Visuzaliza el ID del administrativo con su cargo y horario.");
    descripciones.put("acudiente","La tabla muestra el ID de cada acudiente con la cantidad de estudiantes a cargo.");
    descripciones.put("afiliado","Se ve la información de cada afiliado al club.");
    descripciones.put("carnet","La información de los carnets de cada afiliado.");
    descripciones.put("descuento","La tabla de referencia para los descuentos que se hace a estudiantes según su situación.");
    descripciones.put("estudiante","Información relevante para cuando se recibe a un nuevo estudiante.");
    descripciones.put("estudiante_toma_clase","La tabla que muestra qué estudiantes han tomado o tomarán cierta clase.");

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
    
    
    public JPanel Encabezado=new JPanel();
    public JLabel cambiarContraseña=new JLabel();
    
    public DrawerItem eliminar=new DrawerItem("Eliminar fila").icon(AjustarImg("/Imagenes/Eliminar.png", 30, 30)).build();
    public DrawerItem actualizar=new DrawerItem("Actualizar celda").icon(AjustarImg("/Imagenes/actualizar.png", 30, 30)).build();
    public DrawerItem insertar=(new DrawerItem("Insertar fila").icon(AjustarImg("/Imagenes/insertar.png", 30, 30))).build();
    public DrawerItem CerrarSesión=(new DrawerItem("CERRAR SESIÓN").icon(AjustarImg("/Imagenes/insertar.png", 30, 30))).build();
    
    //PANTALLA PARA CAMBIO DE CONTRASEÑA
    public JFrame ventanaCont=new JFrame();
    public JPanel PanelCont=new JPanel();
    public JPasswordField TextCont=new JPasswordField();
    public JButton BotonAceptarCont=new JButton("Aceptar");
    
    
    
    //PANTALLA PARA (SELECCIÓN TABLA)   
    public JFrame ventanaTab=new JFrame();
    public JPanel PanelTab=new JPanel();
    public JComboBox BoxTabla=new JComboBox();
    public JButton BotonAceptarTab=new JButton("Aceptar");
    public JButton BotonCerrar=new JButton("Cerrar Sesión");
    
    
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
    
    public Hashtable descripciones=new Hashtable();
}
