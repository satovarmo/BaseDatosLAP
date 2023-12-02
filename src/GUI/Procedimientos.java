
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
            System.out.println(indiceFila);
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
}
