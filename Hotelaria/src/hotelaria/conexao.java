package hotelaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USUARIO = "root";
    private static final String SENHA = "jk";

    /**
     * Cria e retorna uma conexão com o banco de dados.
     * 
     * @return Connection ou null em caso de erro.
     */
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fecha uma conexão ativa.
     * 
     * @param conn Conexão a ser fechada.
     */
    public static void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
