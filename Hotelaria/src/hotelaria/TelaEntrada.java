/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelaria;



import javax.swing.*;
import java.awt.*;

public class TelaEntrada extends JFrame {

    public TelaEntrada() {
        // Configurações básicas da janela
        setTitle("Bem-vindo ao Hotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(Color.BLACK);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(painelPrincipal, BorderLayout.CENTER);

        // Ícone do hotel
        JLabel lblIcone = new JLabel();
        ImageIcon iconeOriginal = new ImageIcon("C:\\Users\\kaua\\Desktop\\Hotelaria\\src\\hotelaria\\hotel_icon.png");
        Image iconeRedimensionado = iconeOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Tamanho ajustado (100x100)// Substituir "hotel_icon.png" pelo caminho do ícone real
        lblIcone.setIcon(new ImageIcon(iconeRedimensionado));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nome do hotel
        JLabel lblNomeHotel = new JLabel("Hotelaria Lux");
        lblNomeHotel.setFont(new Font("Sans Serif", Font.BOLD, 32));
        lblNomeHotel.setForeground(Color.WHITE);
        lblNomeHotel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Entrar
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Sans Serif", Font.BOLD, 18));
        btnEntrar.setBackground(new Color(50, 200, 120));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.addActionListener(e -> abrirTelaLogin());

        // Adicionando os componentes ao painel principal
        painelPrincipal.add(Box.createVerticalGlue());
        painelPrincipal.add(lblIcone);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço entre os componentes
        painelPrincipal.add(lblNomeHotel);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        painelPrincipal.add(btnEntrar);
        painelPrincipal.add(Box.createVerticalGlue());
    }

    // Método para abrir a tela de login
    private void abrirTelaLogin() {
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        this.dispose(); // Fecha a tela de entrada
    }

    // Método principal para executar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaEntrada telaEntrada = new TelaEntrada();
            telaEntrada.setVisible(true);
        });
    }
}
