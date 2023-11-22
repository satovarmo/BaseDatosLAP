package GUI;


 
import java.sql.PreparedStatement;
import LAP.conectar;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;



public class MotorInterfaz {
    // RELACION ENTRE CLASES 
    Eventos evt=new Eventos(this);
    public ObjetosInteractivos obj=new ObjetosInteractivos(this);
    
    //OBJETOS DE DATOS
    
    
    public static void main(String args[]){
        MotorInterfaz mi=new MotorInterfaz();
    }

    public MotorInterfaz(){
        PrimeraPantallaIngreso();
        CreacionVentana();
    }
    
    public JScrollPane scrollPane = new JScrollPane (obj.TablaVisual);;
    // CREAMOS LA VENTANA 
    public void CreacionVentana(){
        obj.ventana.setSize(800, 600);
        obj.ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
        obj.ventana.getContentPane().setBackground(null);
        obj.ventana.setResizable(false);
        obj.ventana.setLayout(null);
        obj.ventana.setVisible(true);
        obj.ventana.setLocationRelativeTo(null);
        obj.ventana.setTitle("CLUB DE TENIS LAP");
    }
    
    
    // FUNCIÓN QUE HABILITA LOS BOTONES BASADO EN LOS PERMISOS D QUIEN INGRESA
    public void HabilitarBotones(String a){
        obj.BotonInsertar.setEnabled(false);
        obj.BotonEliminar.setEnabled(false);
        obj.BotonActualizar.setEnabled(false);
        obj.BotonVisualizar.setEnabled(false);
        ArrayList<String> permisos = new ArrayList<> ();
        Connection con=conectar.conect(obj.usuario,obj.contra);
            ResultSet rs;
             try {
               PreparedStatement st = con.prepareStatement ("select privilege_type from information_schema.table_privileges where table_name = ? and grantee = ?");
               st.setString (1, a);
               st.setString (2, "'"+obj.usuario+"'@'localhost'");

               // Ejecutar la consulta y obtener el resultado
               rs = st.executeQuery ();

               // Recorrer el ResultSet y mostrar los permisos
               while (rs.next ()) {
                 permisos.add(rs.getString ("privilege_type"));
               }
               rs.close();
                st.close(); 
                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                System.exit(0);
            }
            if(permisos.indexOf("SELECT")>=0){
                obj.BotonVisualizar.setEnabled(true);
            } 
            if(permisos.indexOf("INSERT")>=0){
                obj.BotonInsertar.setEnabled(true);
            } 
            if(permisos.indexOf("UPDATE")>=0){
                obj.BotonActualizar.setEnabled(true);
            } 
            if(permisos.indexOf("DELETE")>=0){
                obj.BotonEliminar.setEnabled(true);
            }
            }
    
    
    // CREAMOS EL CODIGO PARA LA PRIMERA PANTALLA QUE VERÁ EL USUARIO
    
    public void PrimeraPantallaIngreso(){
        obj.ventana.remove(obj.PanelTabla);
        obj.ventana.setSize(800, 600);
        obj.PanelIngreso.removeAll();
        obj.PanelIngreso.setBounds(0,0,800,600);
        obj.PanelIngreso.setLayout(null);
        obj.PanelIngreso.setBackground(new Color(217,235,255));
        obj.ventana.add(obj.PanelIngreso);
        
        obj.PanelIngreso.add(obj.Títulobd);
        
        JLabel TextoEnt=new JLabel();
        TextoEnt.setBounds(185,120,430,30);
        TextoEnt.setFont(new Font("arial",0,23));
        TextoEnt.setText("Ingresa tu tipo de usuario y contraseña");
        TextoEnt.setBorder(null);
        TextoEnt.setBackground(null);
        TextoEnt.setForeground(new Color(0,0,0));
        TextoEnt.setVisible(true);
        obj.PanelIngreso.add(TextoEnt);
        
        obj.TipoUsuario.setBounds(300,200,200,50);
        obj.TipoUsuario.setFont(new Font("arial",0,20));
        obj.TipoUsuario.setBorder(null);
        obj.TipoUsuario.setForeground(new Color(39, 40, 80));
        obj.TipoUsuario.removeAllItems();
        obj.TipoUsuario.addItem("");
        obj.TipoUsuario.addItem("administrador");
        obj.TipoUsuario.addItem("vendedor");
        obj.TipoUsuario.addItem("acudiente");
        obj.TipoUsuario.addItem("estudiante");
        obj.TipoUsuario.addItem("entrenador");
        obj.TipoUsuario.addItem("cliente");
        obj.TipoUsuario.addItem("contratista");
        obj.TipoUsuario.addItem("asignador");
        obj.TipoUsuario.removeActionListener(evt);
        obj.TipoUsuario.addActionListener(evt);
        obj.TipoUsuario.setVisible(true);
        obj.PanelIngreso.add(obj.TipoUsuario);
        
        
        obj.TextContraseña.setBounds(300,300,200,50);
        obj.TextContraseña.setText("Ingresa tu contraseña");
        obj.TextContraseña.setEditable(true);
        obj.TextContraseña.setFont(new Font("arial",0,12));
        obj.TextContraseña.setEchoChar((char)0);
        obj.TextContraseña.setForeground(Color.gray);
        obj.TextContraseña.removeFocusListener(evt);
        obj.TextContraseña.addFocusListener(evt);
        obj.TextContraseña.addActionListener(evt);
        obj.TextContraseña.setVisible(true);
        obj.PanelIngreso.add(obj.TextContraseña);
        
                 
        obj.BotonAceptar.setFont(new Font("arial",3,30));
        obj.BotonAceptar.setBackground(new Color(27,180,233));
        obj.BotonAceptar.setBounds(225,370,350,50);
        obj.BotonAceptar.setForeground(new Color(0,0,0));
        obj.BotonAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonAceptar.setFocusPainted(false);
        obj.BotonAceptar.removeActionListener(evt);
        obj.BotonAceptar.addActionListener(evt);
        obj.PanelIngreso.add(obj.BotonAceptar);
        
        
    }
    
    
    // CREAMOS EL CODIGO PARA LA SEGUNDA PANTALLA, DONDE TIENE ACCESO A LAS TABLAS Y VISTAS, Y A SUS CORRESPONDIENTES PERMISOS
    public void seleccionTabla(){
        obj.PanelIngreso.removeAll();
        obj.ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        int x=obj.ventana.getWidth();
        int y=obj.ventana.getHeight();
        obj.PanelIngreso.setBounds(0,0,x/3,y);
        
        
        obj.BoxTabla.setBounds(obj.ventana.getWidth()/6-x/12,50,x/6,50);
        obj.BoxTabla.setFont(new Font("arial",0,15));
        obj.BoxTabla.setBorder(null);
        obj.BoxTabla.setForeground(new Color(39, 40, 80));
        obj.BoxTabla.removeAllItems();
        obj.BoxTabla.removeActionListener(evt);
        obj.BoxTabla.addActionListener(evt);
        obj.BoxTabla.addItem("");
        for(int i=0;i<obj.tablas.size();i++){
            obj.BoxTabla.addItem(obj.tablas.get(i));
        }
        obj.BoxTabla.setVisible(true);
        obj.PanelIngreso.add(obj.BoxTabla);
        
        
        obj.BotonVisualizar.setBounds(obj.ventana.getWidth()/6-x/12,150,x/6,50);
        obj.BotonVisualizar.setEnabled(false);
        obj.BotonVisualizar.setFont(new Font("arial",3,18));
        obj.BotonVisualizar.setBackground(new Color(27,180,233));
        obj.BotonVisualizar.setForeground(new Color(0,0,0));
        obj.BotonVisualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonVisualizar.setFocusPainted(false);
        obj.BotonVisualizar.removeActionListener(evt);
        obj.BotonVisualizar.addActionListener(evt);
        obj.PanelIngreso.add(obj.BotonVisualizar);
        
        obj.BotonEliminar.setBounds(obj.ventana.getWidth()/6-x/12,250,x/6,50);
        obj.BotonEliminar.setEnabled(false);
        obj.BotonEliminar.setFont(new Font("arial",3,30));
        obj.BotonEliminar.setBackground(new Color(27,180,233));
        obj.BotonEliminar.setForeground(new Color(0,0,0));
        obj.BotonEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonEliminar.setFocusPainted(false);
        obj.BotonEliminar.removeActionListener(evt);
        obj.BotonEliminar.addActionListener(evt);
        obj.PanelIngreso.add(obj.BotonEliminar);
        
        obj.BotonActualizar.setBounds(obj.ventana.getWidth()/6-x/12,350,x/6,50);
        obj.BotonActualizar.setEnabled(false);
        obj.BotonActualizar.setFont(new Font("arial",3,30));
        obj.BotonActualizar.setBackground(new Color(27,180,233));
        obj.BotonActualizar.setForeground(new Color(0,0,0));
        obj.BotonActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonActualizar.setFocusPainted(false);
        obj.BotonActualizar.removeActionListener(evt);
        obj.BotonActualizar.addActionListener(evt);
        obj.PanelIngreso.add(obj.BotonActualizar);
        
        obj.BotonInsertar.setBounds(obj.ventana.getWidth()/6-x/12,450,x/6,50);
        obj.BotonInsertar.setEnabled(false);
        obj.BotonInsertar.setFont(new Font("arial",3,30));
        obj.BotonInsertar.setBackground(new Color(27,180,233));
        obj.BotonInsertar.setForeground(new Color(0,0,0));
        obj.BotonInsertar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonInsertar.setFocusPainted(false);
        obj.BotonInsertar.removeActionListener(evt);
        obj.BotonInsertar.addActionListener(evt);
        obj.PanelIngreso.add(obj.BotonInsertar);
        
        
        
        obj.CerrarSesión.setBounds(obj.ventana.getWidth()/6-x/12,650,x/6,50);
        obj.CerrarSesión.setFont(new Font("arial",3,30));
        obj.CerrarSesión.setBackground(new Color(27,180,233));
        obj.CerrarSesión.setForeground(new Color(0,0,0));
        obj.CerrarSesión.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.CerrarSesión.setFocusPainted(false);
        obj.CerrarSesión.removeActionListener(evt);
        obj.CerrarSesión.addActionListener(evt);
        obj.PanelIngreso.add(obj.CerrarSesión);
        
        obj.PanelTabla.setBounds(x/3,0,2*x/3,y);
        obj.PanelTabla.setLayout(null);
        obj.PanelTabla.setBackground(new Color(255,255,204));
        obj.ventana.add(obj.PanelTabla);
        
        
        scrollPane.setBounds(50, 250, 2*x/3-100, y-450);
        obj.TablaVisual.setFont(new Font("arial",0,18));
        obj.TablaVisual.setAutoResizeMode (JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        obj.TablaVisual.setRowHeight (50);
        obj.TablaVisual.setRowSelectionAllowed(true);
        obj.TablaVisual.setColumnSelectionAllowed(true);
        obj.PanelTabla.add(scrollPane);
        obj.header=new JTableHeader();
        obj.header=obj.TablaVisual.getTableHeader();
        obj.header.setBounds(50,200, 2*x/3-100, 50);
        obj.header.setFont(new Font("arial",3,25));
        obj.PanelTabla.add(obj.header);
        
        
        
        obj.Botones.setBounds(x/3-75,y-150,150,50);
        obj.PanelTabla.add(obj.Botones);
        
        obj.BotonVerFila.setBounds(0,0,150,50);
        obj.BotonVerFila.setFont(new Font("arial",3,20));
        obj.BotonVerFila.setBackground(new Color(27,180,233));
        obj.BotonVerFila.setForeground(new Color(0,0,0));
        obj.BotonVerFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonVerFila.setFocusPainted(false);
        obj.BotonVerFila.removeActionListener(evt);
        obj.BotonVerFila.addActionListener(evt);
        
        obj.BotonEliminarFila.setBounds(0,0,150,50);
        obj.BotonEliminarFila.setFont(new Font("arial",3,20));
        obj.BotonEliminarFila.setBackground(new Color(27,180,233));
        obj.BotonEliminarFila.setForeground(new Color(0,0,0));
        obj.BotonEliminarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonEliminarFila.setFocusPainted(false);
        obj.BotonEliminarFila.removeActionListener(evt);
        obj.BotonEliminarFila.addActionListener(evt);
        
        
        obj.BotonActualizarFila.setBounds(0,0,150,50);
        obj.BotonActualizarFila.setFont(new Font("arial",3,20));
        obj.BotonActualizarFila.setBackground(new Color(27,180,233));
        obj.BotonActualizarFila.setForeground(new Color(0,0,0));
        obj.BotonActualizarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonActualizarFila.setFocusPainted(false);
        obj.BotonActualizarFila.removeActionListener(evt);
        obj.BotonActualizarFila.addActionListener(evt);
        
        
    }
    
    public void MostrarTabla(){
        obj.TablaVisual.setModel(obj.tabla);
    
    }

    public void InfoTabla(String t) {
        obj.NombreColumnas.clear();
        obj.TipoColumnas.clear();
        obj.LlaveColumnas.clear();
        ResultSet rs;
        try{
            Connection con=conectar.conect(obj.usuario,obj.contra);
            String sql = "DESCRIBE "+t;
            PreparedStatement ps = con.prepareStatement(sql);
            java.sql.DatabaseMetaData dbmd;
            rs = ps.executeQuery();
            ArrayList<String> permisos = new ArrayList<> ();
            while (rs.next()) {
                obj.NombreColumnas.add(rs.getString ("field"));
                obj.TipoColumnas.add(rs.getString ("type"));
                obj.LlaveColumnas.add(rs.getString ("key"));
            }
            rs.close();
            ps.close();
            con.close();
        }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
        for(int i=0;i<obj.NombreColumnas.size();i++){
            obj.tabla.addColumn(obj.NombreColumnas.get(i));
        }
        
        try{
            Connection con=conectar.conect(obj.usuario,obj.contra);
            String sql = "SELECT * FROM "+t;
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            String[] fila = new String[obj.TipoColumnas.size()];
            while (rs.next()) {
                for(int j=0;j<obj.TipoColumnas.size();j++){
                    fila[j]=rs.getString (obj.NombreColumnas.get(j));
                }
            obj.tabla.addRow(fila);
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
  }
    
  public void ReiniciarTabla(){
            obj.tabla=new DefaultTableModel();
            obj.TablaVisual.setModel(new DefaultTableModel());
  }  

    void pantallaInsertar() {
        int col=obj.tabla.getColumnCount();
        obj.ventanaInsert.setSize(600, 100*col+100);
        obj.ventanaInsert.getContentPane().setBackground(null);
        obj.ventanaInsert.setResizable(false);
        obj.ventanaInsert.setLayout(null);
        obj.ventanaInsert.setVisible(true);
        obj.ventanaInsert.setLocationRelativeTo(null);
        obj.ventanaInsert.setTitle("Insertar Datos");
        obj.ventanaInsert.add(obj.PanelInsert);
        
        
        obj.PanelInsert.removeAll();
        obj.PanelInsert.setBounds(0,0,600,100*col+100);
        obj.PanelInsert.setLayout(null);
        obj.PanelInsert.setBackground(new Color(217,235,255));
        
        
        
        obj.listText=new JTextField[col];
        for(int i=0;i<col;i++){
            obj.listText[i]=new JTextField();
            obj.listText[i].setBounds(100,80*(i+1),400,40);
            obj.listText[i].setText("Ingresa "+obj.NombreColumnas.get(i));
            obj.listText[i].setEditable(true);
            obj.listText[i].setFont(new Font("arial",0,12));
            obj.listText[i].setForeground(Color.gray);
            obj.listText[i].removeFocusListener(evt);
            obj.listText[i].addFocusListener(evt);
            obj.listText[i].setVisible(true);
            obj.PanelInsert.add(obj.listText[i]);
        }
        obj.BotonInsertarFila.setBounds(225,80*(col+1),150,50);
        obj.BotonInsertarFila.setFont(new Font("arial",3,20));
        obj.BotonInsertarFila.setBackground(new Color(27,180,233));
        obj.BotonInsertarFila.setForeground(new Color(0,0,0));
        obj.BotonInsertarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonInsertarFila.setFocusPainted(false);
        obj.BotonInsertarFila.removeActionListener(evt);
        obj.BotonInsertarFila.addActionListener(evt);
            obj.PanelInsert.add(obj.BotonInsertarFila);
    }
    
    
    
    void pantallaActualiza() {
        obj.ventanaAct.setSize(600, 600);
        obj.ventanaAct.getContentPane().setBackground(null);
        obj.ventanaAct.setResizable(false);
        obj.ventanaAct.setLayout(null);
        obj.ventanaAct.setVisible(true);
        obj.ventanaAct.setLocationRelativeTo(null);
        obj.ventanaAct.setTitle("Insertar Datos");
        obj.ventanaAct.add(obj.PanelAct);
        
        
        obj.PanelAct.removeAll();
        obj.PanelAct.setBounds(0,0,600,600);
        obj.PanelAct.setLayout(null);
        obj.PanelAct.setBackground(new Color(217,235,255));
        
        
        
        obj.TextAct=new JTextField();
        obj.TextAct.setBounds(100,200,400,50);
        obj.TextAct.setText("Ingresa el dato");
        obj.TextAct.setEditable(true);
        obj.TextAct.setFont(new Font("arial",0,12));
        obj.TextAct.setForeground(Color.gray);
        obj.TextAct.removeFocusListener(evt);
        obj.TextAct.addFocusListener(evt);
        obj.TextAct.setVisible(true);
        obj.PanelAct.add(obj.TextAct);
            
            
        obj.BotonAceptarFila.setBounds(225,350,150,50);
        obj.BotonAceptarFila.setFont(new Font("arial",3,20));
        obj.BotonAceptarFila.setBackground(new Color(27,180,233));
        obj.BotonAceptarFila.setForeground(new Color(0,0,0));
        obj.BotonAceptarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonAceptarFila.setFocusPainted(false);
        obj.BotonAceptarFila.removeActionListener(evt);
        obj.BotonAceptarFila.addActionListener(evt);
            obj.PanelAct.add(obj.BotonAceptarFila);
    }
    
    

    void tipoPS(int i, PreparedStatement ps, String get, String j){
        try{
                
        switch(get.toLowerCase()){
            case "int":
                int k=Integer.parseInt(j);
                ps.setInt(i, k);
                break;
            case "long":
                long m=Long.parseLong(j);
                ps.setLong(i, m);
                break;
            case "bigint":
                double n=Double.parseDouble(j);
                ps.setDouble(i, n);
                break;
            case "varchar(45)":
                ps.setString(i, j);
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión:" + e.getMessage());
        }
        
    }


}
