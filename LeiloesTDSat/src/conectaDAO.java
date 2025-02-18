
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    // Configuração do banco de dados (altere conforme necessário)
    private static final String URL = "jdbc:mysql://localhost:3306/LeiloesTdSet?useSSL=false&allowPublicKeyRetrieval=true"; // Altere para seu banco
    private static final String USUARIO = "root"; // Altere conforme sua configuração
    private static final String SENHA = "0829"; // Altere conforme sua configuração

    
    public Connection connectDB(){
        Connection conn = null;
        
          try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Certifique-se de ter o driver MySQL
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro: Driver do banco de dados não encontrado!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }
    
}
