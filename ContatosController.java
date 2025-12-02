package Controller;

import Db.Database;
import Model.Contatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatosController {

    
    public boolean adicionarContato(Contatos contato) {
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.setString(3, contato.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Contatos> listarContatos() {
        List<Contatos> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos ORDER BY id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contatos c = new Contatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                contatos.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contatos;
    }

    
    public boolean atualizarContato(Contatos contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.setString(3, contato.getEmail());
            pstmt.setInt(4, contato.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean excluirContato(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Contatos buscarPorId(int id) {
        String sql = "SELECT * FROM contatos WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Contatos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
