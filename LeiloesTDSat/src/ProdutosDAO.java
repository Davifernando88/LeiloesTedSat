/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            conn = new conectaDAO().connectDB(); // Conecta ao banco
            
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor()); // Alterado para aceitar valores decimais
            prep.setString(3, produto.getStatus());

            prep.executeUpdate(); // Executa a inserção

          
              JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException erro) {
                System.out.println("Erro ao fechar conexão: " + erro.getMessage());
            }
        } 
    }
   public void venderProduto(int id) {
    Connection conn = null;
    PreparedStatement stmtSelect = null;
    PreparedStatement stmtUpdate = null;
    ResultSet rs = null;

    try {
        conn = new conectaDAO().connectDB(); // Conexão com o banco

        // 1. Verifica a quantidade disponível
        String sqlSelect = "SELECT quantidade FROM produtos WHERE id = ?";
        stmtSelect = conn.prepareStatement(sqlSelect);
        stmtSelect.setInt(1, id);
        rs = stmtSelect.executeQuery();

        if (rs.next()) {
            int quantidadeAtual = rs.getInt("quantidade");

            if (quantidadeAtual > 0) {
                // 2. Atualiza a quantidade do produto
                String sqlUpdate = "UPDATE produtos SET quantidade = quantidade - 1 WHERE id = ?";
                stmtUpdate = conn.prepareStatement(sqlUpdate);
                stmtUpdate.setInt(1, id);
                int linhasAfetadas = stmtUpdate.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao vender o produto.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Produto sem estoque.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmtSelect != null) stmtSelect.close();
            if (stmtUpdate != null) stmtUpdate.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



   public ArrayList<ProdutosDTO> listarProdutos() {
    ArrayList<ProdutosDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM produtos"; // Certifique-se de que a tabela está correta

    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            lista.add(produto);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Mostra o erro no console
    }

    return lista;
}

}

    
    
        


