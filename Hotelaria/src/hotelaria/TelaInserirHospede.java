package hotelaria;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;

public class TelaInserirHospede extends JFrame {

    private JTextField txtNomeCompleto, txtCPF, txtDataNascimento, txtTelefone, txtEmail, txtPesquisaCPF;
    private JTextArea txtEndereco, txtResultadoPesquisa;
    private JButton btnSalvar, btnPesquisar, btnVoltarInsercao, btnVoltarPesquisa;

    public TelaInserirHospede() {
        setTitle("Gerenciamento de Hóspedes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // Criar painel de abas
        JTabbedPane abas = new JTabbedPane();

        // Adicionar as abas
        abas.addTab("Inserir Hóspede", criarPainelInsercao());
        abas.addTab("Pesquisar Hóspede", criarPainelPesquisa());

        add(abas);
    }

    private JPanel criarPainelInsercao() {
        JPanel painelInsercao = new JPanel(new GridBagLayout());
        painelInsercao.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        JLabel lblTitulo = criarLabel("Inserção de Hóspedes");
        JLabel lblNomeCompleto = criarLabel("Nome Completo:");
        JLabel lblCPF = criarLabel("CPF:");
        JLabel lblDataNascimento = criarLabel("Data de Nascimento (AAAA-MM-DD):");
        JLabel lblTelefone = criarLabel("Telefone:");
        JLabel lblEmail = criarLabel("Email:");
        JLabel lblEndereco = criarLabel("Endereço:");

        // Campos de texto
        txtNomeCompleto = criarCampoTexto();
        txtCPF = criarCampoTexto();
        txtDataNascimento = criarCampoTexto();
        txtTelefone = criarCampoTexto();
        txtEmail = criarCampoTexto();
        txtEndereco = criarCampoTextoArea();

        // Botões
        btnSalvar = criarBotao("Salvar", new Color(50, 200, 120));
        btnSalvar.addActionListener(e -> salvarHospede());

        btnVoltarInsercao = criarBotao("Voltar", new Color(100, 150, 220));
        btnVoltarInsercao.addActionListener(e -> dispose());

        // Layout
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelInsercao.add(lblTitulo, gbc);

        adicionarComponente(painelInsercao, lblNomeCompleto, txtNomeCompleto, gbc, 1);
        adicionarComponente(painelInsercao, lblCPF, txtCPF, gbc, 2);
        adicionarComponente(painelInsercao, lblDataNascimento, txtDataNascimento, gbc, 3);
        adicionarComponente(painelInsercao, lblTelefone, txtTelefone, gbc, 4);
        adicionarComponente(painelInsercao, lblEmail, txtEmail, gbc, 5);

        gbc.gridx = 0;
        gbc.gridy = 6;
        painelInsercao.add(lblEndereco, gbc);
        gbc.gridx = 1;
        painelInsercao.add(new JScrollPane(txtEndereco), gbc);

        gbc.gridy = 7;
        gbc.gridwidth = 2;
        painelInsercao.add(btnSalvar, gbc);

        gbc.gridy = 8;
        painelInsercao.add(btnVoltarInsercao, gbc);

        return painelInsercao;
    }

    private JPanel criarPainelPesquisa() {
        JPanel painelPesquisa = new JPanel(new GridBagLayout());
        painelPesquisa.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e campos de pesquisa
        JLabel lblTituloPesquisa = criarLabel("Pesquisar Hóspede");
        JLabel lblCPF = criarLabel("CPF do Hóspede:");
        txtPesquisaCPF = criarCampoTexto();
        btnPesquisar = criarBotao("Pesquisar", new Color(100, 150, 220));
        txtResultadoPesquisa = criarCampoTextoArea();
        txtResultadoPesquisa.setEditable(false);

        btnPesquisar.addActionListener(e -> pesquisarHospede());

        btnVoltarPesquisa = criarBotao("Voltar", new Color(100, 150, 220));
        btnVoltarPesquisa.addActionListener(e -> dispose());

        // Layout
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPesquisa.add(lblTituloPesquisa, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        painelPesquisa.add(lblCPF, gbc);

        gbc.gridx = 1;
        painelPesquisa.add(txtPesquisaCPF, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelPesquisa.add(btnPesquisar, gbc);

        gbc.gridy = 3;
        painelPesquisa.add(new JScrollPane(txtResultadoPesquisa), gbc);

        gbc.gridy = 4;
        painelPesquisa.add(btnVoltarPesquisa, gbc);

        return painelPesquisa;
    }

    private void salvarHospede() {
        String nome = txtNomeCompleto.getText().trim();
        String cpf = txtCPF.getText().trim();
        String dataNascimento = txtDataNascimento.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        String endereco = txtEndereco.getText().trim();

        // Verificação de campos
        if (nome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificação de CPF
        if (!Pattern.matches("\\d{11}", cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido! Deve conter 11 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Usando a classe de conexão personalizada
        try (Connection conn = conexao.conectar(); // Aqui usamos a classe de conexão personalizada
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO hospedes (nome_completo, cpf, data_nascimento, telefone, email, endereco) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setDate(3, Date.valueOf(dataNascimento));
            stmt.setString(4, telefone);
            stmt.setString(5, email);
            stmt.setString(6, endereco);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Hóspede salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposInsercao();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar hóspede: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarHospede() {
        String cpf = txtPesquisaCPF.getText().trim();

        // Verificação de CPF
        if (cpf.isEmpty() || !Pattern.matches("\\d{11}", cpf)) {
            JOptionPane.showMessageDialog(this, "Digite um CPF válido (11 dígitos)!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Usando a classe de conexão personalizada
        try (Connection conn = conexao.conectar(); // Aqui usamos a classe de conexão personalizada
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM hospedes WHERE cpf = ?")) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtResultadoPesquisa.setText("Nome: " + rs.getString("nome_completo") +
                        "\nCPF: " + rs.getString("cpf") +
                        "\nData de Nascimento: " + rs.getDate("data_nascimento") +
                        "\nTelefone: " + rs.getString("telefone") +
                        "\nEmail: " + rs.getString("email") +
                        "\nEndereço: " + rs.getString("endereco"));
            } else {
                txtResultadoPesquisa.setText("Nenhum hóspede encontrado com o CPF informado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar hóspede: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCamposInsercao() {
        txtNomeCompleto.setText("");
        txtCPF.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campoTexto = new JTextField(20);
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        return campoTexto;
    }

    private JTextArea criarCampoTextoArea() {
        JTextArea campoTextoArea = new JTextArea(5, 20);
        campoTextoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        campoTextoArea.setLineWrap(true);
        campoTextoArea.setWrapStyleWord(true);
        return campoTextoArea;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    private void adicionarComponente(JPanel painel, JLabel label, JTextField campoTexto, GridBagConstraints gbc, int posicao) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = posicao;
        painel.add(label, gbc);

        gbc.gridx = 1;
        painel.add(campoTexto, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInserirHospede().setVisible(true));
    }
}
