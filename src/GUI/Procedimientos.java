
package GUI;


import LAP.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Procedimientos {
    MotorInterfaz motint;
      public Procedimientos(MotorInterfaz motint){
          this.motint=motint;
    }
    public void Resaltar(String datos){
        motint.obj.TablaVisual.clearSelection();
       if (motint.obj.mapa.containsKey(datos)){
    // Si la encuentra, resáltala
            int indiceFila = motint.obj.mapa.get(datos);
            motint.obj.TablaVisual.addRowSelectionInterval(indiceFila-1, indiceFila-1);
            motint.obj.TablaVisual.addColumnSelectionInterval(0, motint.obj.TablaVisual.getColumnCount()-1);
        }else{
            JOptionPane.showMessageDialog(null, "No se encontró el ID en esta tabla");
        }
    } 
    
    
    
    public void BuscarPersona(int cedula){
        String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call READ_persona("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("ID")+rs.getString("Nombre")+rs.getString("Apellido")+rs.getString("Edad")+rs.getString("EPS")+rs.getString("Telefono")+rs.getString("Correo");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
        
    }
    
    public void BuscarEstudiante(int cedula){
        String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_EST("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("ID")+rs.getString("valor_sin_descuento")+rs.getString("porcentaje");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
        
    }
            
    public void BuscarEntrenador(int cedula){
        String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_ENTREN_ID("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("ID")+rs.getString("nombre")+rs.getString("apellido")+rs.getString("salario");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
        
    }
    
    public void BuscarClaseEntr(int cedula){
         String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_CLASE("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("CLAID")+rs.getString("nombre")+rs.getString("apellido");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
     }
    
    public void BuscarArticulo(int cedula){
         String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_ARTICULO_NOM("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("ID")+rs.getString("nombre")+rs.getString("PrecioLAP")+rs.getString("Stock");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
     }
     
    public void BuscarNombre(String nombre,String apellido){
         String[] datos=new String[100];
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_ENTREN_NOM('"+nombre+"','"+apellido+"')";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            int contador=0;
            while(rs.next()){
                datos[contador]=rs.getString("CLAID")+rs.getString("nombre")+rs.getString("apellido");
                contador+=1;
            }
            rs.close();
            ps.close();
            con.close();
            motint.obj.TablaVisual.clearSelection();
            if (motint.obj.mapa.containsKey(datos[0])){
                for(int i=0;i<contador;i++){
             // Si la encuentra, resáltala
                     int indiceFila = motint.obj.mapa.get(datos[i]);
                     motint.obj.TablaVisual.addRowSelectionInterval(indiceFila-1, indiceFila-1);
                     motint.obj.TablaVisual.addColumnSelectionInterval(0, motint.obj.TablaVisual.getColumnCount()-1);
                 }
            }else{
                     JOptionPane.showMessageDialog(null, "No se encontró el nombre en esta tabla");
                 }    
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
     }
    
    public void BuscarAcu(int cedula){
        String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_ACU("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("BUSCAR_EST_COSTO")+rs.getString("Estudiantes");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
        
    }
    
    public void BuscarEstAcu(int cedula){
    String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call BUSCAR_EST_COSTO("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("ID")+rs.getString("valor_sin_descuento")+rs.getString("porcentaje");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
    }

    public void BuscarAcuCos(int cedula){
        String datos="";
        try{
            Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call ACUD_COSTO("+cedula+")";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                datos=rs.getString("acudiente")+rs.getString("total_a_pagar");
            }
            rs.close();
            ps.close();
            con.close();
            Resaltar(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
        }
        
    }
    
    public void calcularCosto(){
        if(motint.obj.TablaVisual.getSelectedRow()>=0){
                Object[] col=new Object[motint.obj.TablaVisual.getColumnCount()];
                int n=col.length;
                String text="";
                for(int i=0;i<n;i++){
                    if(motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i)==null){
                        break;
                    }
                text=text+motint.obj.TablaVisual.getValueAt(motint.obj.TablaVisual.getSelectedRow(), i).toString();
                        if(i!=n-1){
                            text=text+",";
                        }
                      
                }    
                PreparedStatement ps,ps2;
                String sql;
                try{
                    Connection con = conectar.conect(motint.obj.usuario,motint.obj.contra);
                    sql = "call CALCULAR_COSTO("+text+",@resultado)";
                    ps = con.prepareStatement(sql);
                    ps.execute();
                    String sql2="select @resultado";
                    ps2 = con.prepareStatement(sql2);
                    ResultSet rs=ps2.executeQuery();
                    String datos="";
                    if(rs.next()){
                        datos=rs.getString("@resultado");
                    }
                    JOptionPane.showMessageDialog(null, "El descuento para el estudiante es de "+datos);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
                }
        }else{
            JOptionPane.showMessageDialog(null,"No has seleccionado ninguna fila para calcular");
        }
   }
    
    
    public void TablaFiltrar(String s){
        motint.ReiniciarTabla("");
        motint.obj.mapa.clear();
        ResultSet rs;
        motint.obj.NombreColumnas.clear();
        motint.obj.TipoColumnas.clear();
        motint.obj.LlaveColumnas.clear();
        motint.obj.NullColumnas.clear();
        motint.obj.mapa.clear();
        try{
            Connection con=conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "DESCRIBE "+motint.obj.BoxTabla.getSelectedItem().toString();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                motint.obj.NombreColumnas.add(rs.getString ("field"));
                motint.obj.TipoColumnas.add(rs.getString ("type"));
                motint.obj.LlaveColumnas.add(rs.getString ("key"));
                motint.obj.NullColumnas.add(rs.getString ("null"));
            }
            rs.close();
            ps.close();
            con.close();
        }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
        for(int i=0;i<motint.obj.NombreColumnas.size();i++){
            motint.obj.tabla.addColumn(motint.obj.NombreColumnas.get(i));
        }
        try{
            Connection con=conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call FILTRAR_PRECIO("+s+")";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            String[] fila = new String[motint.obj.TipoColumnas.size()];
            int i=1;
            while (rs.next()) {
                for(int j=0;j<motint.obj.TipoColumnas.size();j++){
                    fila[j]=rs.getString (motint.obj.NombreColumnas.get(j));
                }   
            motint.obj.mapa.put(String.join("", fila), i);        
            motint.obj.tabla.addRow(fila);
            i=i+1;
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
        motint.obj.TablaVisual.setModel(motint.obj.tabla);
    }
    
    
    
    
    public void OrganizarS(){
        motint.ReiniciarTabla("");
        motint.obj.mapa.clear();
        ResultSet rs;
        motint.obj.NombreColumnas.clear();
        motint.obj.TipoColumnas.clear();
        motint.obj.LlaveColumnas.clear();
        motint.obj.NullColumnas.clear();
        motint.obj.mapa.clear();
        try{
            Connection con=conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "DESCRIBE "+motint.obj.BoxTabla.getSelectedItem().toString();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                motint.obj.NombreColumnas.add(rs.getString ("field"));
                motint.obj.TipoColumnas.add(rs.getString ("type"));
                motint.obj.LlaveColumnas.add(rs.getString ("key"));
                motint.obj.NullColumnas.add(rs.getString ("null"));
            }
            rs.close();
            ps.close();
            con.close();
        }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
        for(int i=0;i<motint.obj.NombreColumnas.size();i++){
            motint.obj.tabla.addColumn(motint.obj.NombreColumnas.get(i));
        }
        try{
            Connection con=conectar.conect(motint.obj.usuario,motint.obj.contra);
            String sql = "call ORGANIZAR_STOCK()";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            String[] fila = new String[motint.obj.TipoColumnas.size()];
            int i=1;
            while (rs.next()) {
                for(int j=0;j<motint.obj.TipoColumnas.size();j++){
                    fila[j]=rs.getString (motint.obj.NombreColumnas.get(j));
                }   
            motint.obj.mapa.put(String.join("", fila), i);        
            motint.obj.tabla.addRow(fila);
            i=i+1;
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Debido a un eror se cerrara el programa"+ex);
                    System.exit(0);
                }
        motint.obj.TablaVisual.setModel(motint.obj.tabla);
    }
}
