import javax.swing.*;
import java.sql.*;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBCONNECTION {
    String db = "jdbc:postgresql://localhost:5432/FinalProjectJava";

    public void addProduct (String name, String code) {
        try {
            String time = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a").format(LocalDateTime.now());
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(db, "postgres", "secret");
            Statement createStatement = connection.createStatement();
            PreparedStatement newStatement = connection.prepareStatement("insert into producto values (?, ?, ?)");
            newStatement.setString(1, name);
            newStatement.setString(2, code);
            newStatement.setString(3, time);
            newStatement.executeQuery();
            JOptionPane.showMessageDialog(null, "Se ha guardado tu producto");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
        }
    }

    public void export () {
        try {
            FileWriter file = new FileWriter("C:/Users/Public/Documents/ARCHIVO.txt");

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(db, "postgres", "secret");

            Statement createStatement = connection.createStatement();

            ResultSet results = createStatement.executeQuery ("select * from producto");
            int i=0;
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (results.next())
            {
                file.write(i+1 + results.getString(1) + " " + results.getString(2) + results.getString(3)+ "\n");
                i++;
            }
            file.close();
            // Se cierra la conexión con la base de datos.
            connection.close();
            JOptionPane.showMessageDialog(null, "Exportando en un archivo txt");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
        }
    }
}

