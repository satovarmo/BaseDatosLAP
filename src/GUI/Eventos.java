
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
import java.awt.Point;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class Eventos implements ActionListener, FocusListener, MouseListener{
    MotorInterfaz motint;
    public Eventos(MotorInterfaz motint){
        this.motint=motint;
    }
    public int column=0;
    public int fila=0;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==motint.obj.BotonAceptar || e.getSource()==motint.obj.TextContraseña){
            motint.obj.usuario=(String) motint.obj.TipoUsuario.getSelectedItem();
            motint.obj.contra=motint.obj.TextContraseña.getText();
            if(((String) motint.obj.TipoUsuario.getSelectedItem()).equals("estudiante") ||((String) motint.obj.TipoUsuario.getSelectedItem()).equals("acudiente") || ((String) motint.obj.TipoUsuario.getSelectedItem()).equals("cliente")){
                motint.obj.contra="";
            }
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
                motint.seleccionTabla();
                motint.obj.ventana.revalidate();
                motint.obj.ventana.repaint();
            }
        }
        else if(e.getSource()==motint.obj.TipoUsuario){
               String text=((String) motint.obj.TipoUsuario.getSelectedItem());
                if(text==null || (!text.equals("acudiente") && !text.equals("estudiante") && !text.equals("cliente"))){
                    motint.obj.TextContraseña.setEnabled(true);
                    motint.obj.TextContraseña.setText("Ingresa tu contraseña");
                }else{
                    motint.obj.TextContraseña.setEnabled(false);
                    motint.obj.TextContraseña.setText("Este perfil no requiere contraseña");
                }
        }
        
        else if(e.getSource()==motint.obj.BoxTabla){
               String text=((String) motint.obj.BoxTabla.getSelectedItem());
                if(text==null || text.equals("")){
                    motint.obj.BotonInsertar.setEnabled(false);
                    motint.obj.BotonEliminar.setEnabled(false);
                    motint.obj.BotonActualizar.setEnabled(false);
                    motint.obj.BotonVisualizar.setEnabled(false);
                }else{
                    motint.ReiniciarTabla();
                    motint.InfoTabla(text);
                    motint.MostrarTabla();
                    motint.HabilitarBotones(text);
                    motint.obj.ventana.revalidate();
                    motint.obj.ventana.repaint();
                }
        }
        
        
        else if(e.getSource()==motint.obj.CerrarSesión){
            motint.obj.usuario="";
            motint.obj.contra="";
            motint.obj.tablas.clear();
            motint.ReiniciarTabla();
            motint.PrimeraPantallaIngreso();
            motint.obj.ventana.revalidate();
            motint.obj.ventana.repaint();
        }
        
        
        else if(e.getSource()==motint.obj.BotonVisualizar){
            if(motint.obj.TablaVisual.getSelectedRow()>=0){
                String text=motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), motint.obj.TablaVisual.getSelectedColumn()).toString();
                JOptionPane.showMessageDialog(null,text);
            }else{
                JOptionPane.showMessageDialog(null,"No has seleccionado ninguna fila para ver");
            }
        }
        
        else if(e.getSource()==motint.obj.BotonEliminar){
            motint.MostrarTabla();
            motint.obj.Botones.removeAll();
            motint.obj.Botones.add(motint.obj.BotonEliminarFila);
            motint.obj.ventana.revalidate();
            motint.obj.ventana.repaint();
        }


        
        else if(e.getSource()==motint.obj.BotonActualizar){
            motint.MostrarTabla();
            motint.obj.Botones.removeAll();
            motint.obj.Botones.add(motint.obj.BotonActualizarFila);
            
            motint.obj.ventana.revalidate();
            motint.obj.ventana.repaint();
        }        
                
        else if(e.getSource()==motint.obj.BotonInsertar){
            motint.obj.Botones.removeAll();
            motint.MostrarTabla();
            motint.pantallaInsertar();
        }       
         
                
                
                
        else if(e.getSource()==motint.obj.BotonInsertarFila){
            int n=motint.obj.NombreColumnas.size();
            boolean comprobacion=true;
            for (int i=0;i<n;i++){
                if(motint.obj.listText[i].getText().equals("Ingresa "+motint.obj.NombreColumnas.get(i)) || motint.obj.listText[i].getText().equals("")){
                    comprobacion=false;
                }
            }
            if (comprobacion){
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
                    JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
                }
                motint.obj.ventanaInsert.dispose();
                String text=((String) motint.obj.BoxTabla.getSelectedItem());
                motint.ReiniciarTabla();
                motint.InfoTabla(text);
                motint.MostrarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "Rellene los campos completamente");
            } 
        }   
        
        
        else if(e.getSource()==motint.obj.BotonVerFila){
            
        }
        
        
        else if(e.getSource()==motint.obj.BotonEliminarFila){
            if(motint.obj.TablaVisual.getSelectedRow()>=0){
                Object[] col=new Object[motint.obj.TablaVisual.getColumnCount()];
                String text="";
                for(int i=0;i<motint.obj.TablaVisual.getColumnCount();i++){
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
                        if(i!=col.length-1){
                            text=text+" AND ";
                        }
                      
                }    
                PreparedStatement ps;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    sql = "delete from "+motint.obj.BoxTabla.getSelectedItem().toString()+" where ("+text+")";
                    ps = con.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se han eliminado los datos");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
                }
                String texttabla=((String) motint.obj.BoxTabla.getSelectedItem());
                motint.ReiniciarTabla();
                motint.InfoTabla(texttabla);
                motint.MostrarTabla();
            }else{
                JOptionPane.showMessageDialog(null,"No has seleccionado ninguna fila para ver");
            }
        }
        
        
        
        
        else if(e.getSource()==motint.obj.BotonAceptarFila){
            if (!motint.obj.TextAct.getText().equals("Ingresa el dato")){
                PreparedStatement ps;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    String text="";
                for(int i=0;i<motint.obj.TablaVisual.getColumnCount();i++){
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
                        if(i!=motint.obj.NombreColumnas.size()-1){
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
                motint.ReiniciarTabla();
                motint.InfoTabla(text);
                motint.MostrarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "Inserte los datos");
            } 
        }   
        
        
        else if(e.getSource()==motint.obj.BotonActualizarFila){
            column=motint.obj.TablaVisual.getSelectedColumn();
            fila=motint.obj.TablaVisual.getSelectedRow();
            motint.pantallaActualiza();
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
        }
        if(e.getSource()==motint.obj.TextContraseña && motint.obj.TextContraseña.getText().equals("")){
                motint.obj.TextContraseña.setEchoChar((char)0);
                motint.obj.TextContraseña.setText("Ingresa tu contraseña");
                motint.obj.TextContraseña.setForeground(Color.gray);
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
        
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
