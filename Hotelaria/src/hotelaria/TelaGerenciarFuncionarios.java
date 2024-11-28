package hotelaria;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarFuncionarios extends JFrame {

    private JTextField txtNomeFuncionario, txtCpfFuncionario, txtCargoFuncionario, txtSalarioFuncionario;
    private JButton btnSalvarFuncionario, btnAtualizarFuncionario, btnExcluirFuncionario, btnExibirFuncionarios;
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabelaFuncionarios;
    private JTabbedPane tabbedPane;

    public TelaGerenciarFuncionarios() {
        setTitle("Gerenciamento de Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurando cores e fontes gerais
        getContentPane().setBackground(new Color(45, 45, 45));

        JLabel lblTitulo = new JLabel("Gerenciar Funcionários");
        lblTitulo.setFont(new Font("Sans Serif", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        tabbedPane = new JTabbedPane();

        // Aba 1: Formulário para inserir/editar funcionários
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        adicionarCamposFormularioFuncionario(painelFormulario, gbc);

        // Aba 2: Exibir funcionários
        JPanel painelExibir = new JPanel(new BorderLayout());
        painelExibir.setBackground(new Color(45, 45, 45));

        modeloTabelaFuncionarios = new DefaultTableModel();
        modeloTabelaFuncionarios.setColumnIdentifiers(new String[]{"ID", "Nome", "CPF", "Cargo", "Salário"});
        tabelaFuncionarios = new JTable(modeloTabelaFuncionarios);
        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);

        // Botões na aba de exibição
        btnExibirFuncionarios = criarBotao("Exibir Funcionários");
        btnExibirFuncionarios.addActionListener(e -> carregarFuncionarios());

        btnAtualizarFuncionario = criarBotao("Atualizar");
        btnAtualizarFuncionario.addActionListener(e -> preencherFormularioParaEdicao());

        btnExcluirFuncionario = criarBotao("Excluir");
        btnExcluirFuncionario.addActionListener(e -> excluirFuncionario());

        JPanel painelBotoesTabela = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesTabela.setBackground(new Color(45, 45, 45));
        painelBotoesTabela.add(btnExibirFuncionarios);
        painelBotoesTabela.add(btnAtualizarFuncionario);
        painelBotoesTabela.add(btnExcluirFuncionario);

        painelExibir.add(scrollPane, BorderLayout.CENTER);
        painelExibir.add(painelBotoesTabela, BorderLayout.SOUTH);

        // Adicionando abas ao JTabbedPane
        tabbedPane.addTab("Formulário", painelFormulario);
        tabbedPane.addTab("Exibir Funcionários", painelExibir);

        add(lblTitulo, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Botão Voltar
        JButton btnVoltar = criarBotao("Voltar");
        btnVoltar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(45, 45, 45));
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void adicionarCamposFormularioFuncionario(JPanel painel, GridBagConstraints gbc) {
        JLabel lblNome = criarLabel("Nome:");
        txtNomeFuncionario = criarCampoTexto();

        JLabel lblCpf = criarLabel("CPF:");
        txtCpfFuncionario = criarCampoTexto();

        JLabel lblCargo = criarLabel("Cargo:");
        txtCargoFuncionario = criarCampoTexto();

        JLabel lblSalario = criarLabel("Salário:");
        txtSalarioFuncionario = criarCampoTexto();

        btnSalvarFuncionario = criarBotao("Salvar");
        btnSalvarFuncionario.addActionListener(e -> salvarOuAtualizarFuncionario());

        gbc.gridwidth = 1;
        adicionarComponente(painel, lblNome, txtNomeFuncionario, gbc, 1);
        adicionarComponente(painel, lblCpf, txtCpfFuncionario, gbc, 2);
        adicionarComponente(painel, lblCargo, txtCargoFuncionario, gbc, 3);
        adicionarComponente(painel, lblSalario, txtSalarioFuncionario, gbc, 4);

        gbc.gridwidth = 2;
        gbc.gridy = 5;
        painel.add(btnSalvarFuncionario, gbc);
    }

    private void salvarOuAtualizarFuncionario() {
        String nome = txtNomeFuncionario.getText().trim();
        String cpf = txtCpfFuncionario.getText().trim();
        String cargo = txtCargoFuncionario.getText().trim();
        String salario = txtSalarioFuncionario.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || salario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conexao.conectar()) {
            PreparedStatement stmtVerificar = conn.prepareStatement("SELECT * FROM funcionarios WHERE cpf = ?");
            stmtVerificar.setString(1, cpf);
            ResultSet rs = stmtVerificar.executeQuery();

            if (rs.next()) {
                PreparedStatement stmtAtualizar = conn.prepareStatement("UPDATE funcionarios SET nome = ?, cargo = ?, salario = ? WHERE cpf = ?");
                stmtAtualizar.setString(1, nome);
                stmtAtualizar.setString(2, cargo);
                stmtAtualizar.setString(3, salario);
                stmtAtualizar.setString(4, cpf);
                stmtAtualizar.executeUpdate();
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso.");
            } else {
                PreparedStatement stmtInserir = conn.prepareStatement("INSERT INTO funcionarios (nome, cpf, cargo, salario) VALUES (?, ?, ?, ?)");
                stmtInserir.setString(1, nome);
                stmtInserir.setString(2, cpf);
                stmtInserir.setString(3, cargo);
                stmtInserir.setString(4, salario);
                stmtInserir.executeUpdate();
                JOptionPane.showMessageDialog(this, "Funcionário adicionado com sucesso.");
            }

            limparCamposFormulario();
            carregarFuncionarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar/atualizar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarFuncionarios() {
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM funcionarios");
             ResultSet rs = stmt.executeQuery()) {

            modeloTabelaFuncionarios.setRowCount(0);

            while (rs.next()) {
                modeloTabelaFuncionarios.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFuncionario() {
        int linhaSelecionada = tabelaFuncionarios.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpf = (String) modeloTabelaFuncionarios.getValueAt(linhaSelecionada, 2);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o funcionário com CPF " + cpf + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try (Connection conn = conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM funcionarios WHERE cpf = ?")) {

                stmt.setString(1, cpf);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso.");
                carregarFuncionarios();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherFormularioParaEdicao() {
        int linhaSelecionada = tabelaFuncionarios.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtNomeFuncionario.setText((String) modeloTabelaFuncionarios.getValueAt(linhaSelecionada, 1));
        txtCpfFuncionario.setText((String) modeloTabelaFuncionarios.getValueAt(linhaSelecionada, 2));
        txtCargoFuncionario.setText((String) modeloTabelaFuncionarios.getValueAt(linhaSelecionada, 3));
        txtSalarioFuncionario.setText(modeloTabelaFuncionarios.getValueAt(linhaSelecionada, 4).toString());
    }

    private void limparCamposFormulario() {
        txtNomeFuncionario.setText("");
        txtCpfFuncionario.setText("");
        txtCargoFuncionario.setText("");
        txtSalarioFuncionario.setText("");
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Sans Serif", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campoTexto = new JTextField(20);
        campoTexto.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        return campoTexto;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Sans Serif", Font.BOLD, 14));
        return botao;
    }

    private void adicionarComponente(JPanel painel, JLabel label, JTextField campoTexto, GridBagConstraints gbc, int posicao) {
        gbc.gridx = 0;
        gbc.gridy = posicao;
        painel.add(label, gbc);
        gbc.gridx = 1;
        painel.add(campoTexto, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaGerenciarFuncionarios().setVisible(true));
    }
}
