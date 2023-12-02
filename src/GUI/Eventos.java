
package GUI;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import LAP.conectar;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;

public class Eventos extends MouseAdapter implements EventDrawer,ActionListener, FocusListener, MouseListener{
    MotorInterfaz motint;
    public Eventos(MotorInterfaz motint){
        this.motint=motint;
    }
    public int column=0;
    public int fila=0;
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // BOTON DE INICIAR SESIÓN
                
        if(e.getSource()==motint.obj.BotonAceptar || e.getSource()==motint.obj.TextContraseña){
            motint.obj.usuario=(String) motint.obj.TipoUsuario.getSelectedItem();
            motint.obj.contra=motint.obj.TextContraseña.getText();
            if(((String) motint.obj.TipoUsuario.getSelectedItem()).equals("estudiante") ||((String) motint.obj.TipoUsuario.getSelectedItem()).equals("acudiente") || ((String) motint.obj.TipoUsuario.getSelectedItem()).equals("cliente")){
                motint.obj.contra="";
            }if(motint.obj.usuario.equals("Elige tu usuario") || motint.obj.contra.equals("Ingresa tu contraseña")){
                JOptionPane.showMessageDialog(null, "Elige un usuario e ingresa una contraseña");
            }else{
                motint.obj.tablas.removeAll(motint.obj.tablas);
                Connection con=conectar.conect(motint.obj.usuario,motint.obj.contra);
                if(con==null){
                    JOptionPane.showMessageDialog(null, "El usuario y la contraseña no coinciden");
                    motint.PrimeraPantallaIngreso();
                }else{
                    java.sql.DatabaseMetaData dbmd;
                    ResultSet rs;
                     try {
                        dbmd=con.getMetaData();
                        rs = dbmd.getTables("mydb", null, null, null);
                        while (rs.next()) {
                            motint.obj.tablas.add(rs.getString ("TABLE_NAME"));
                    }
                    rs.close();
                    con.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                        System.exit(0);
                    }
                    motint.obj.ventana.setVisible(false);
                    motint.ElegirTabla();
                }
            }
        }
        
        // PERMITIR INGRESAR CONTRASEÑA SEGÚN EL USUSARIO
                
        else if(e.getSource()==motint.obj.TipoUsuario){
               String text=((String) motint.obj.TipoUsuario.getSelectedItem());
               if(text!=null){
                if(text.equals("Elige tu usuario")){
                    motint.obj.TipoUsuario.setFont(new Font("arial",2,15));
                    motint.obj.TextContraseña.setText("Ingresa tu contraseña");
                }else if(!text.equals("acudiente") && !text.equals("estudiante") && !text.equals("cliente")){
                    motint.obj.TipoUsuario.setFont(new Font("arial",0,15));
                    motint.obj.TextContraseña.setEnabled(true);
                    motint.obj.TextContraseña.setEchoChar((char)0);
                    motint.obj.TextContraseña.setForeground(Color.gray);
                    motint.obj.TextContraseña.setText("Ingresa tu contraseña");
                }else{
                    motint.obj.TipoUsuario.setFont(new Font("arial",0,15));
                    motint.obj.TextContraseña.setEnabled(false);
                    motint.obj.TextContraseña.setEchoChar((char)0);
                    motint.obj.TextContraseña.setText("Este perfil no requiere contraseña");
                }
               }
        }
        
        // BOTON DE ACEPTAR LA TABLA E LA PANTALLA DE ELECCIÓN TABLA                
        
        else if(e.getSource()==motint.obj.BotonAceptarTab){
               String text=((String) motint.obj.BoxTabla.getSelectedItem());
                if(text==null || text.equals("")){
                    JOptionPane.showMessageDialog(null, "Debes elegir una tabla");
                }else{
                    motint.ReiniciarTabla(text);
                    motint.obj.ventanaTab.dispose();
                    motint.obj.ventana.remove(motint.obj.PanelIngreso);
                    motint.pantallaMenu();
                    motint.obj.ventana.revalidate();
                    motint.obj.ventana.repaint();
                }
        }
        
        // BOTON DE CERRAR SESIÓN
                
        else if(e.getSource()==motint.obj.BotonCerrar){
            motint.obj.ventanaTab.dispose();
            motint.obj.usuario="";
            motint.obj.contra="";
            motint.obj.tablas.clear();
            motint.ReiniciarTabla("");
            motint.obj.ventana.remove(motint.obj.PanelTabla);
            motint.obj.drawer.hide();
            motint.PrimeraPantallaIngreso();
            motint.obj.ventana.revalidate();
            motint.obj.ventana.repaint();
            
        }
        
        // BOTON ACEPTAR INSERTAR FILA                
        
        else if(e.getSource()==motint.obj.BotonInsertarFila){
        int n=motint.obj.NombreColumnas.size();
            for (int i=0;i<n;i++){
                if(motint.obj.listText[i].getText().equals("Ingresa "+motint.obj.NombreColumnas.get(i)) || motint.obj.listText[i].getText().equals("")){
                    motint.obj.listText[i].setText("null");
                }
            }
                PreparedStatement ps;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    String signos="?";
                    for(int i=1;i<n;i++){
                        signos=signos+",?";
                    }
                    sql = "insert into "+motint.obj.BoxTabla.getSelectedItem().toString()+" values ("+signos+")";
                    ps = con.prepareStatement(sql);
                    for(int i=1;i<=n;i++){
                        motint.tipoPS(i,ps,motint.obj.TipoColumnas.get(i-1),motint.obj.listText[i-1].getText());
                    }
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se han insertado los datos");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
                }
                motint.obj.ventanaInsert.dispose();
                String text=((String) motint.obj.BoxTabla.getSelectedItem());
                motint.ReiniciarTabla(text);
            
        }
        
        // BOTON PARA ACEPTAR LOS DATOS DE ACTUALIZACIÓN
        
        else if(e.getSource()==motint.obj.BotonAceptarFila || e.getSource()==motint.obj.TextAct){
            if (!motint.obj.TextAct.getText().equals("Ingresa el dato")){
                PreparedStatement ps;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    String text="";
                int n=motint.obj.TablaVisual.getColumnCount();    
                for(int i=0;i<n;i++){
                    if(i==motint.obj.TablaVisual.getSelectedColumn() || motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i)==null){
                        if(i==n-1){
                            StringBuilder textP=new StringBuilder();
                            for(int k=0;k<text.length()-5;k++){
                                textP.append(text.charAt(k));
                            }
                            text=textP.toString();
                            n=n-1;
                        }
                        continue;
                    }
                String c=motint.obj.TipoColumnas.get(i);
                switch(c){
                    case "int":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "bigint":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "long":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "varchar(45)":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = '"+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString()+"'";
                        break;
                }
                        if(i!=n-1){
                            text=text+" AND ";
                        }
                      
                }    
                    sql = "UPDATE "+motint.obj.BoxTabla.getSelectedItem().toString()+" SET `"+motint.obj.NombreColumnas.get(column)+"` = ? WHERE "+text;
                    ps = con.prepareStatement(sql);
                    System.out.println(sql);
                    motint.tipoPS(1,ps,motint.obj.TipoColumnas.get(column),motint.obj.TextAct.getText());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se han actualizado los datos");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
                }
                motint.obj.ventanaAct.dispose();
                String text=((String) motint.obj.BoxTabla.getSelectedItem());
                motint.ReiniciarTabla(text);
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            } 
        }
        
        // MENÚ
           
        else if(e.getSource()==motint.obj.btnPanel){
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }else{
                motint.obj.drawer.show();
            }
        }
        
        
        // BOTON NUEVA CONTRASEÑA
        
        else  if(e.getSource()==motint.obj.BotonAceptarCont || e.getSource()==motint.obj.TextCont){
            if (!motint.obj.TextCont.getText().equals("Nueva contraseña") && !motint.obj.TextCont.getText().equals(motint.obj.contra)){
                motint.cambiarCont(motint.obj.TextCont.getText());
                motint.obj.ventanaCont.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Inserte una NUEVA contraseña válida");
            }
        }
        
        //BOTON BUSQUEDA
        
        
        else if(e.getSource()==motint.obj.BotonBuscarFila|| e.getSource()==motint.obj.TextBus){
            if (!motint.obj.TextBus.getText().equals("Ingresa el ID")){
                String t=motint.obj.BoxTabla.getSelectedItem().toString();
                switch(t){
                    case "personaclub":
                        motint.pro.BuscarPersona(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "estudiante_costo":
                        if(motint.obj.usuario.equals("acudiente")){
                            motint.pro.BuscarEstAcu(Integer.parseInt(motint.obj.TextBus.getText()));
                        }else{
                            motint.pro.BuscarEstudiante(Integer.parseInt(motint.obj.TextBus.getText()));
                        }    
                        break;
                    case "entrenadores":
                        motint.pro.BuscarEntrenador(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "clases_entrenadores":
                        motint.pro.BuscarClaseEntr(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "articulos":
                        motint.pro.BuscarArticulo(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "acudiente":
                        motint.pro.BuscarAcu(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "acudiente_costo":
                        motint.pro.BuscarAcuCos(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "caddie":
                        motint.pro.BuscarCad(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "acudientes_estudiantes":
                        motint.pro.BuscarAcuEst(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                    case "clase":
                        motint.pro.BuscarClaseID(Integer.parseInt(motint.obj.TextBus.getText()));
                        break;
                }
                motint.obj.ventanaBus.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            }
        }
        
        
        // BTON BUSQUEDA NOMBRE
        else if(e.getSource()==motint.obj.BotonBuscarFilaN|| e.getSource()==motint.obj.TextBusA){
            if (!motint.obj.TextBusN.getText().equals("Ingresa el nombre") && !motint.obj.TextBusA.getText().equals("Ingresa el apellido") ){
                motint.pro.BuscarNombre(motint.obj.TextBusN.getText(),motint.obj.TextBusA.getText());
                motint.obj.ventanaBusN.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            }
        }
        
        // BTON BUSQUEDA NOMBRE
        else if(e.getSource()==motint.obj.BotonBuscarCad ){
            if (!motint.obj.TextBus.getText().equals("Ingresa el ID") ){
                motint.pro.BuscarCadClas(Integer.parseInt(motint.obj.TextBus.getText()));
                motint.obj.ventanaBus.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            }
        }
        
        //BOTON PARA BUSCAR CLASE POR ENT
        
        else if(e.getSource()==motint.obj.BotonBuscarE|| e.getSource()==motint.obj.TextBusE){
            if (!motint.obj.TextBusE.getText().equals("Ingresa el ID")){
                motint.pro.BuscarEntClas(Integer.parseInt(motint.obj.TextBusE.getText()));
                motint.obj.ventanaBus.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            }
        }
        
        // BOTON FILTRAR
        else if(e.getSource()==motint.obj.BotonFiltrar|| e.getSource()==motint.obj.TextPrec){
            if (!motint.obj.TextPrec.getText().equals("Ingresa el valor máximo")){
                motint.pro.TablaFiltrar(motint.obj.TextPrec.getText());
                motint.obj.ventanaPrec.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            }
        }
        // BOTON DESFILTRAR
        else if(e.getSource()==motint.obj.BotonDesFiltrar){
                motint.ReiniciarTabla(motint.obj.BoxTabla.getSelectedItem().toString());
                motint.obj.ventanaPrec.dispose();
        }
        
        
    }
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource()==motint.obj.TextAct && motint.obj.TextAct.getText().equals("Ingresa el dato")){
                motint.obj.TextAct.setText("");
                motint.obj.TextAct.setForeground(Color.black);
        }
        if(e.getSource()==motint.obj.TextContraseña && motint.obj.TextContraseña.getText().equals("Ingresa tu contraseña")){
                motint.obj.TextContraseña.setEchoChar('\u25CF');
                motint.obj.TextContraseña.setText("");
                motint.obj.TextContraseña.setForeground(new Color(0,0,0));
        }
        if(e.getSource()==motint.obj.TextCont && motint.obj.TextCont.getText().equals("Nueva contraseña")){
                motint.obj.TextCont.setEchoChar('\u25CF');
                motint.obj.TextCont.setText("");
                motint.obj.TextCont.setForeground(new Color(0,0,0));
        }
        if(e.getSource()==motint.obj.TextBus && motint.obj.TextBus.getText().equals("Ingresa el ID")){
                motint.obj.TextBus.setText("");
                motint.obj.TextBus.setForeground(Color.black);
        }
        if(e.getSource()==motint.obj.TextBusN && motint.obj.TextBusN.getText().equals("Ingresa el nombre")){
                motint.obj.TextBusN.setText("");
                motint.obj.TextBusN.setForeground(Color.black);
        }
        if(e.getSource()==motint.obj.TextBusA && motint.obj.TextBusA.getText().equals("Ingresa el apellido")){
                motint.obj.TextBusA.setText("");
                motint.obj.TextBusA.setForeground(Color.black);
        }
        if(e.getSource()==motint.obj.TextBusE && motint.obj.TextBusE.getText().equals("Ingresa el ID")){
                motint.obj.TextBusE.setText("");
                motint.obj.TextBusE.setForeground(Color.black);
        }
        if(e.getSource()==motint.obj.TextPrec && motint.obj.TextPrec.getText().equals("Ingresa el valor máximo")){
                motint.obj.TextPrec.setText("");
                motint.obj.TextPrec.setForeground(Color.black);
        }
        if(motint.obj.listText!=null){
                for(int i=0;i<motint.obj.tabla.getColumnCount();i++){
                    if(e.getSource()==motint.obj.listText[i] && motint.obj.listText[i].getText().equals("Ingresa "+motint.obj.NombreColumnas.get(i))){
                        motint.obj.listText[i].setText("");
                        motint.obj.listText[i].setForeground(new Color(0,0,0));
            }   }
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource()==motint.obj.TextAct && motint.obj.TextAct.getText().equals("")){
                motint.obj.TextAct.setText("Ingresa el dato");
                motint.obj.TextAct.setForeground(Color.gray);
        }if(e.getSource()==motint.obj.TextAct && motint.obj.TextAct.getText().equals("")){
                motint.obj.TextAct.setText("Ingresa el dato");
                motint.obj.TextAct.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextContraseña && motint.obj.TextContraseña.getText().equals("")){
                motint.obj.TextContraseña.setEchoChar((char)0);
                motint.obj.TextContraseña.setText("Ingresa tu contraseña");
                motint.obj.TextContraseña.setForeground(Color.gray);
        }
       if(e.getSource()==motint.obj.TextCont && motint.obj.TextCont.getText().equals("")){
                motint.obj.TextCont.setEchoChar((char)0);
                motint.obj.TextCont.setText("Nueva contraseña");
                motint.obj.TextCont.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextBus && motint.obj.TextBus.getText().equals("")){
                motint.obj.TextBus.setText("Ingresa el ID");
                motint.obj.TextBus.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextBusN && motint.obj.TextBusN.getText().equals("")){
                motint.obj.TextBusN.setText("Ingresa el nombre");
                motint.obj.TextBusN.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextBusA && motint.obj.TextBusA.getText().equals("")){
                motint.obj.TextBusA.setText("Ingresa el apellido");
                motint.obj.TextBusA.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextBusE && motint.obj.TextBusE.getText().equals("")){
                motint.obj.TextBusE.setText("Ingresa el ID");
                motint.obj.TextBusE.setForeground(Color.gray);
        }
        if(e.getSource()==motint.obj.TextPrec && motint.obj.TextPrec.getText().equals("")){
                motint.obj.TextPrec.setText("Ingresa el valor máximo");
                motint.obj.TextPrec.setForeground(Color.gray);
        }
       if(motint.obj.listText!=null){
            for(int i=0;i<motint.obj.tabla.getColumnCount();i++){
                    if(e.getSource()==motint.obj.listText[i] && motint.obj.listText[i].getText().equals("")){
                        motint.obj.listText[i].setText("Ingresa "+motint.obj.NombreColumnas.get(i));
                        motint.obj.listText[i].setForeground(Color.gray);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         if(e.getSource()==motint.obj.TituloTabla && e.getClickCount()==2){
            motint.obj.ventana.setVisible(false);
            motint.ElegirTabla();
         }
         else if(e.getSource()==motint.obj.TablaVisual && e.getClickCount()==2){
         if(motint.obj.TablaVisual.getSelectedRow()>=0){
                String text=motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), motint.obj.TablaVisual.getSelectedColumn()).toString();
                int fila=motint.obj.TablaVisual.getSelectedRow()+1;
                String m=motint.obj.NombreColumnas.get(motint.obj.TablaVisual.getSelectedColumn())+" para la fila "+fila+" es: ";
                JOptionPane.showMessageDialog(null,m+text);
            }else{
                JOptionPane.showMessageDialog(null,"No has seleccionado ninguna celda para ver");
            }
         }
        else if(e.getSource()==motint.obj.cambiarContraseña){
            motint.ventanaCont();
        }
        
    }
    

    @Override
    public void selected(int j, DrawerItem di) {
        if(di==motint.obj.CerrarSesión){
            motint.obj.usuario="";
            motint.obj.contra="";
            motint.obj.tablas.clear();
            motint.ReiniciarTabla("");
            motint.obj.ventana.remove(motint.obj.PanelTabla);
            motint.obj.drawer.hide();
            motint.PrimeraPantallaIngreso();
            motint.obj.ventana.revalidate();
            motint.obj.ventana.repaint();
        }
        
        else if(di==motint.obj.insertar){
            motint.pantallaInsertar();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }
        }   
        
        else if(di==motint.obj.eliminar){
            if(motint.obj.TablaVisual.getSelectedRow()>=0){
                Object[] col=new Object[motint.obj.TablaVisual.getColumnCount()];
                int n=col.length;
                String text="";
                for(int i=0;i<n;i++){
                    if(motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i)==null){
                        if(i==n-1){
                            StringBuilder textP=new StringBuilder();
                            for(int k=0;k<text.length()-5;k++){
                                textP.append(text.charAt(k));
                            }
                            text=textP.toString();
                            n=n-1;
                        }
                        continue;
                    }
                String c=motint.obj.TipoColumnas.get(i);
                switch(c){
                    case "int":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "bigint":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "long":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = "+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        break;
                    case "varchar(45)":
                        text=text+"`"+(motint.obj.NombreColumnas.get(i))+"` = '"+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString()+"'";
                        break;
                }
                        if(i!=n-1){
                            text=text+" AND ";
                        }
                      
                }    
                PreparedStatement ps;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    sql = "delete from "+motint.obj.BoxTabla.getSelectedItem().toString()+" where ("+text+")";
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se han eliminado los datos");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
                }
                String texttabla=((String) motint.obj.BoxTabla.getSelectedItem());
                motint.ReiniciarTabla(texttabla);
            }else{
                JOptionPane.showMessageDialog(null,"No has seleccionado ninguna fila para eliminar");
            }
        }
        
        else if(di==motint.obj.actualizar){
             if(motint.obj.TablaVisual.getSelectedRow()>=0){
                column=motint.obj.TablaVisual.getSelectedColumn();
                fila=motint.obj.TablaVisual.getSelectedRow();
                motint.pantallaActualiza();
                if(motint.obj.drawer.isShow()){
                    motint.obj.drawer.hide();
            }
                }
             else{
                 JOptionPane.showMessageDialog(null,"No has seleccionado ninguna casilla para actualizar");
             }
        }
        else if(di==motint.obj.buscar){
            motint.pantallaBusca();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }
        else if(di==motint.obj.BuscarN){
            motint.pantallaBuscaNombre();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }
        else if(di==motint.obj.Filtrar){
            motint.pantallaPrec();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }
        else if(di==motint.obj.OrganizarStock){
            motint.pro.OrganizarS();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }
        
        else if(di==motint.obj.ClaseCadd){
            motint.pantallaBuscaCadClas();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }        
        
        else if(di==motint.obj.ClaseEnt){
            motint.pantallaBuscaEntClas();
            if(motint.obj.drawer.isShow()){
                motint.obj.drawer.hide();
            }    
            
        }               
        else if(di==motint.obj.calcularDescuentoEstudiante){
             if(motint.obj.TablaVisual.getSelectedRow()>=0){
                column=motint.obj.TablaVisual.getSelectedColumn();
                fila=motint.obj.TablaVisual.getSelectedRow();
                motint.pro.calcularCosto();
                if(motint.obj.drawer.isShow()){
                    motint.obj.drawer.hide();
            }
                }
             else{
                 JOptionPane.showMessageDialog(null,"No has seleccionado ninguna casilla para actualizar");
             }
        }
    }


}
