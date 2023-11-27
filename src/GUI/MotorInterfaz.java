package GUI;


 
import java.sql.PreparedStatement;
import LAP.conectar;
import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.ImageIcon;

public class MotorInterfaz {
    // RELACION ENTRE CLASES 
    Eventos evt=new Eventos(this);
    public ObjetosInteractivos obj=new ObjetosInteractivos(this);
    
    //OBJETOS DE DATOS
    
    
    public static void main(String args[]){
        try {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception e) {
        // Si Nimbus no está disponible, puedes configurar la GUI con otro aspecto y sensación.
    }
         MotorInterfaz mi=new MotorInterfaz();
    }

    public MotorInterfaz(){
        CreacionVentana();
        PrimeraPantallaIngreso();
        
    }
    
    // CREAMOS LA VENTANA 
    public void CreacionVentana(){
        obj.ventana.setDefaultLookAndFeelDecorated(true);
        obj.ventana.setIconImage(obj.logo.getImage());
        obj.ventana.setSize(450, 700);
        obj.ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
        obj.ventana.setLayout(new BorderLayout());
        obj.ventana.setLocationRelativeTo(null);
        obj.ventana.setTitle("CLUB DE TENIS LAP");
    }
    
    // CREAMOS EL CODIGO PARA LA PRIMERA PANTALLA QUE VERÁ EL USUARIO
    
    public void PrimeraPantallaIngreso(){
        obj.ventana.setResizable(true);
        obj.PanelIngreso.removeAll();
        obj.ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.ventana.add(obj.PanelIngreso, BorderLayout.CENTER);
        
        obj.PanelIngreso.setBackground(new Color(173,216,230));
        obj.PanelIngreso.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL; // El componente se expande en la dirección horizontal
        constraints.weightx = 0; // El espacio extra se distribuye al componente
        constraints.weighty = 1; // El espacio extra se distribuye al componente
        constraints.gridx = 0;
        
        obj.Títulobd.setBorder(null);
        obj.Títulobd.setBackground(null);
        obj.Títulobd.setVisible(true);
        int w=320;
        int h=200;
        ImageIcon ic=obj.AjustarImg("/Imagenes/LogotipoSinFondo.png", w,h);
        obj.Títulobd.setIcon(ic);
        constraints.gridy = 0; // La posición y del componente
        obj.PanelIngreso.add(obj.Títulobd, constraints);

        
        JLabel TextoEnt=new JLabel();
        TextoEnt.setFont(new Font("arial",1,20));
        TextoEnt.setText("BIENVENIDO A LA BASE DE DATOS");
        TextoEnt.setBorder(null);
        TextoEnt.setBackground(null);
        TextoEnt.setForeground(new Color(31,73,155));
        TextoEnt.setVisible(true);
        constraints.gridy = 1;
        obj.PanelIngreso.add(TextoEnt,constraints);
        
        
        obj.TipoUsuario.setFont(new Font("arial",2,15));
        obj.TipoUsuario.setBorder(null);
        obj.TipoUsuario.setForeground(Color.RED);
        obj.TipoUsuario.removeAllItems();
        obj.TipoUsuario.addItem("Elige tu usuario");
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
        constraints.gridy = 2;
        obj.PanelIngreso.add(obj.TipoUsuario,constraints);
        
        
        obj.TextContraseña.setText("Ingresa tu contraseña");
        obj.TextContraseña.setEditable(true);
        obj.TextContraseña.setFont(new Font("arial",0,12));
        obj.TextContraseña.setEchoChar((char)0);
        obj.TextContraseña.setForeground(Color.gray);
        obj.TextContraseña.removeFocusListener(evt);
        obj.TextContraseña.addFocusListener(evt);
        obj.TextContraseña.addActionListener(evt);
        obj.TextContraseña.setVisible(true);
        constraints.gridy = 3;
        obj.PanelIngreso.add(obj.TextContraseña,constraints);
        
                 
        obj.BotonAceptar.setFont(new Font("arial",3,23));
        obj.BotonAceptar.setBackground(new Color(0,0,255));
        obj.BotonAceptar.setForeground(Color.WHITE);
        obj.BotonAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonAceptar.setFocusPainted(false);
        obj.BotonAceptar.removeActionListener(evt);
        obj.BotonAceptar.addActionListener(evt);
        constraints.gridy=4;
        obj.PanelIngreso.add(obj.BotonAceptar,constraints);
        
        
        obj.ventana.setVisible(true);
    }
    
    
    // CREAMOS EL CODIGO PARA LA SEGUNDA PANTALLA, DONDE TIENE ACCESO A LAS TABLAS Y VISTAS, Y A SUS CORRESPONDIENTES PERMISOS
    public void pantallaMenu(){
        obj.ventana.setResizable(false);
        obj.ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JScrollPane scrollPane = new JScrollPane (obj.TablaVisual);
        
        
        obj.drawer.header(new JLabel("Bienvenido "+obj.usuario));
        obj.drawer.build();
        
        obj.PanelTabla.setBackground(new Color(173,216,230));
        obj.PanelTabla.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL; // El componente se expande en la dirección horizontal
        constraints.gridx = 0; // La posición x del componente
        constraints.gridy = 0; // La posición y del componente
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        
        
        obj.btnPanel.setSize(100,100);
        obj.btnPanel.setFont(new Font("arial",3,10));
        obj.btnPanel.setBackground(new Color(27,180,233));
        obj.btnPanel.setForeground(new Color(0,0,0));
        obj.btnPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.btnPanel.setFocusPainted(false);
        obj.btnPanel.removeActionListener(evt);
        obj.btnPanel.addActionListener(evt);
        obj.PanelTabla.add(obj.btnPanel,constraints);
        
        
        
        
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1; // El espacio extra se distribuye al componente
        constraints.weighty = 1; // El espacio extra se distribuye al componente
        constraints.gridx = 1; // La posición x del componente
        
        
        
        obj.TituloTabla.setFont(new Font("arial",1,50));
        obj.TituloTabla.setText(((String) obj.BoxTabla.getSelectedItem()).toUpperCase());
        obj.TituloTabla.setBorder(null);
        obj.TituloTabla.setBackground(null);
        obj.TituloTabla.setForeground(new Color(31,73,155));
        obj.TituloTabla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        obj.TituloTabla.setVisible(true);
        obj.TituloTabla.removeMouseListener(evt);
        obj.TituloTabla.addMouseListener(evt);
        constraints.gridy = 2;
        obj.PanelTabla.add(obj.TituloTabla,constraints);
        
        
        obj.TablaVisual.setFont(new Font("arial",0,18));
        obj.TablaVisual.setAutoResizeMode (JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        obj.TablaVisual.setRowHeight (50);
        obj.TablaVisual.setRowSelectionAllowed(true);
        obj.TablaVisual.setColumnSelectionAllowed(true);
        obj.TablaVisual.removeMouseListener(evt);
        obj.TablaVisual.addMouseListener(evt);
        constraints.gridy = 4; // La posición y del componente
        obj.PanelTabla.add(scrollPane,constraints);
        
        
        
        constraints.gridy = 3; // La posición y del componente
        obj.header=new JTableHeader();
        obj.header=obj.TablaVisual.getTableHeader();
        obj.header.setFont(new Font("arial",3,25));
        obj.PanelTabla.add(obj.header,constraints);
        
        
        
        
        obj.ventana.add(obj.PanelTabla,BorderLayout.CENTER);
        obj.ventana.setVisible(true);
        
        
        
    }
    
    
    // CREAMOS LA PANTALLA PARA LA ELECCIÓN DE TABLAS
    void ElegirTabla() {
        
        obj.ventanaTab.setIconImage(obj.logo.getImage());
        obj.ventanaTab.setSize(450, 700);
        obj.ventanaTab.getContentPane().setBackground(null);
        obj.ventanaTab.setResizable(false);
        obj.ventanaTab.setLayout(new BorderLayout());
        obj.ventanaTab.setLocationRelativeTo(null);
        obj.ventanaTab.setTitle("Elegir tabla");
        
        
        obj.PanelTab.removeAll();
        obj.PanelTab.setLayout(new GridBagLayout());
        obj.PanelTab.setBackground(new Color(217,235,255));
        
        obj.ventanaTab.add(obj.PanelTab, BorderLayout.CENTER);
        
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL; // El componente se expande en la dirección horizontal
        constraints.weightx = 0; // El espacio extra se distribuye al componente
        constraints.weighty = 1; // El espacio extra se distribuye al componente
        constraints.gridx = 0;
        
        constraints.gridy = 0; // La posición y del componente
        obj.PanelTab.add(obj.Títulobd, constraints);
        
        
        
        JLabel TextoEnt=new JLabel();
        TextoEnt.setFont(new Font("arial",1,20));
        TextoEnt.setText("ELIGE TU TABLA");
        TextoEnt.setBorder(null);
        TextoEnt.setBackground(null);
        TextoEnt.setForeground(new Color(31,73,155));
        TextoEnt.setVisible(true);
        constraints.gridy = 1;
        obj.PanelTab.add(TextoEnt,constraints);
        
        
        obj.BoxTabla.setFont(new Font("arial",0,15));
        obj.BoxTabla.setBorder(null);
        obj.BoxTabla.setForeground(new Color(39, 40, 80));
        obj.BoxTabla.removeAllItems();
        obj.BoxTabla.addItem("");
        for(int i=0;i<obj.tablas.size();i++){
            obj.BoxTabla.addItem(obj.tablas.get(i));
        }
        obj.BoxTabla.setVisible(true);
        constraints.gridy = 2;
        obj.PanelTab.add(obj.BoxTabla,constraints);
                   
            
        obj.BotonAceptarTab.setFont(new Font("arial",3,20));
        obj.BotonAceptarTab.setBackground(new Color(27,180,233));
        obj.BotonAceptarTab.setForeground(new Color(0,0,0));
        obj.BotonAceptarTab.setCursor(new Cursor(Cursor.HAND_CURSOR));
        obj.BotonAceptarTab.setFocusPainted(false);
        obj.BotonAceptarTab.removeActionListener(evt);
        obj.BotonAceptarTab.addActionListener(evt);
        constraints.gridy = 3;
        obj.PanelTab.add(obj.BotonAceptarTab,constraints);
        
        
        obj.ventanaTab.setVisible(true);
    }
    
    
    // CREAMOS LAS FUNCIONES QUE ACTUALIZAN LA TABLA Y CONCEDE PERMISOS
    public void ReiniciarTabla(String t){
            obj.tabla=new DefaultTableModel();
            obj.TablaVisual.setModel(new DefaultTableModel());
            if(t!=null && !t.equals("")){
                InfoTabla(t);
            }
                    
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
        obj.TablaVisual.setModel(obj.tabla);
        HabilitarBotones(t);
  }
    
    // FUNCIÓN QUE HABILITA LOS BOTONES BASADO EN LOS PERMISOS D QUIEN INGRESA
    public void HabilitarBotones(String a){
        obj.insertar.setEnabled(false);
        obj.eliminar.setEnabled(false);
        obj.actualizar.setEnabled(false);
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
            if(permisos.indexOf("INSERT")>=0){
                obj.insertar.setEnabled(true);       
            } 
            if(permisos.indexOf("UPDATE")>=0){
                obj.actualizar.setEnabled(true);
            } 
            if(permisos.indexOf("DELETE")>=0){
                obj.eliminar.setEnabled(true);
            }
            }
    
    
    
    // CREAMOS LA PANTALLA PARA LA INSERCIÓN DE DATOS
    public void pantallaInsertar() {
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
    
    
    // CREAMOS LA PANTALLA PARA LA ACTUALIZACIÓN DE DATOS
    public void pantallaActualiza() {
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
    
    

    public void tipoPS(int i, PreparedStatement ps, String get, String j){
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
