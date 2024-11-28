/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelaria;

/**
 *
 * @author kaua
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospedeDAO {
    public void cadastrarHospede(Hospede hospede) {
        String sql = "INSERT INTO hospedes (nome, email, telefone, documento) VALUES (?, ?, ?, ?)";
        try (Connection conn =conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getEmail());
            stmt.setString(3, hospede.getTelefone());
            stmt.setString(4, hospede.getDocumento());
            stmt.executeUpdate();
            System.out.println("Hóspede cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar hóspede: " + e.getMessage());
        }
    }
}
