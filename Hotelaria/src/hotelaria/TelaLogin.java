package hotelaria;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TelaLogin extends JFrame {

    private JTextField txtUsuarioLogin, txtCpfLogin;
    private JButton btnLogin;

    private JTextField txtNomeCadastro, txtCpfCadastro, txtCargoCadastro, txtSalarioCadastro;
    private JButton btnSalvarCadastro;

    public TelaLogin() {
        setTitle("Login e Cadastro - Sistema de Hotelaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Adicionando abas (Login e Cadastro)
        JTabbedPane tabbedPane = new JTabbedPane();

        // Aba de Login
        JPanel painelLogin = criarPainelLogin();
        tabbedPane.addTab("Login", painelLogin);

        // Aba de Cadastro
        JPanel painelCadastro = criarPainelCadastro();
        tabbedPane.addTab("Cadastro", painelCadastro);

        add(tabbedPane);
    }

    private JPanel criarPainelLogin() {
        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = criarLabel("Login");
        JLabel lblUsuario = criarLabel("Usuário:");
        txtUsuarioLogin = criarCampoTexto();

        JLabel lblCpf = criarLabel("CPF:");
        txtCpfLogin = criarCampoTexto();

        btnLogin = criarBotao("Login", new Color(50, 200, 120));
        btnLogin.addActionListener(e -> realizarLogin());

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelLogin.add(lblTitulo, gbc);

        adicionarComponente(painelLogin, lblUsuario, txtUsuarioLogin, gbc, 1);
        adicionarComponente(painelLogin, lblCpf, txtCpfLogin, gbc, 2);

        gbc.gridy = 3;
        painelLogin.add(btnLogin, gbc);

        return painelLogin;
    }

    private JPanel criarPainelCadastro() {
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloCadastro = criarLabel("Cadastro");
        JLabel lblNome = criarLabel("Nome:");
        txtNomeCadastro = criarCampoTexto();

        JLabel lblCpf = criarLabel("CPF:");
        txtCpfCadastro = criarCampoTexto();

        JLabel lblCargo = criarLabel("Cargo:");
        txtCargoCadastro = criarCampoTexto();

        JLabel lblSalario = criarLabel("Salário:");
        txtSalarioCadastro = criarCampoTexto();

        btnSalvarCadastro = criarBotao("Salvar", new Color(50, 120, 200));
        btnSalvarCadastro.addActionListener(e -> salvarCadastro());

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCadastro.add(lblTituloCadastro, gbc);

        adicionarComponente(painelCadastro, lblNome, txtNomeCadastro, gbc, 1);
        adicionarComponente(painelCadastro, lblCpf, txtCpfCadastro, gbc, 2);
        adicionarComponente(painelCadastro, lblCargo, txtCargoCadastro, gbc, 3);
        adicionarComponente(painelCadastro, lblSalario, txtSalarioCadastro, gbc, 4);

        gbc.gridy = 5;
        painelCadastro.add(btnSalvarCadastro, gbc);

        return painelCadastro;
    }

    private void adicionarComponente(JPanel painel, JLabel label, JTextField campo, GridBagConstraints gbc, int y) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(label, gbc);

        gbc.gridx = 1;
        painel.add(campo, gbc);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        label.setForeground(Color.LIGHT_GRAY);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        return campo;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Sans Serif", Font.BOLD, 14));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void realizarLogin() {
        String usuario = txtUsuarioLogin.getText().trim();
        String cpf = txtCpfLogin.getText().trim();

        if (usuario.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o usuário e o CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conexao.conectar()) {
            String query = "SELECT * FROM funcionarios WHERE nome = ? AND cpf = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, " + rs.getString("nome"), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Fecha a tela de login
                new TelaPrincipalSistema().setVisible(true); // Abre a tela principal
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou CPF incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarCadastro() {
        String nome = txtNomeCadastro.getText().trim();
        String cpf = txtCpfCadastro.getText().trim();
        String cargo = txtCargoCadastro.getText().trim();
        String salario = txtSalarioCadastro.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || salario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO funcionarios (nome, cpf, cargo, salario) VALUES (?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, cargo);
            stmt.setString(4, salario);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cadastro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
