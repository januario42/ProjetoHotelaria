package hotelaria;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarQuartos extends JFrame {

    private JTextField txtNumeroQuarto, txtTipoQuarto, txtPrecoDiaria, txtAndar;
    private JComboBox<String> cbStatusQuarto;
    private JButton btnSalvarQuarto, btnVoltar, btnAtualizar, btnExcluir, btnExibirQuartos;
    private JTable tabelaQuartos;
    private DefaultTableModel modeloTabela;
    private JTabbedPane tabbedPane;
    private TelaPrincipalSistema telaPrincipal;  // Adiciona uma referência para a tela principal

    public TelaGerenciarQuartos(TelaPrincipalSistema telaPrincipal) {  // Recebe a tela principal como parâmetro
        this.telaPrincipal = telaPrincipal; // Atribui a tela principal
        setTitle("Gerenciamento de Quartos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurando cores e fontes gerais
        getContentPane().setBackground(new Color(45, 45, 45)); // Fundo cinza escuro

        // Título da janela
        JLabel lblTitulo = new JLabel("Gerenciar Quartos");
        lblTitulo.setFont(new Font("Sans Serif", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE); // Texto branco
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Criando o JTabbedPane para dividir em abas
        tabbedPane = new JTabbedPane();

        // Aba 1: Formulário para inserir/quartos
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionando os campos de input
        adicionarCamposFormulario(painelFormulario, gbc);

        // Aba 2: Exibir quartos
        JPanel painelExibir = new JPanel(new BorderLayout());
        painelExibir.setBackground(new Color(45, 45, 45));

        modeloTabela = new DefaultTableModel();
        modeloTabela.setColumnIdentifiers(new String[]{"Número", "Tipo", "Preço", "Andar", "Status"});
        tabelaQuartos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaQuartos);

        // Botão Exibir Quartos
        btnExibirQuartos = criarBotao("Exibir Quartos");
        btnExibirQuartos.addActionListener(e -> carregarQuartos());

        // Botão Atualizar ao lado da tabela
        btnAtualizar = criarBotao("Atualizar");
        btnAtualizar.addActionListener(e -> preencherFormularioParaEdicao());

        // Botão Excluir ao lado da tabela
        btnExcluir = criarBotao("Excluir");
        btnExcluir.addActionListener(e -> excluirQuarto());

        JPanel painelBotoesTabela = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesTabela.setBackground(new Color(45, 45, 45));
        painelBotoesTabela.add(btnExibirQuartos);
        painelBotoesTabela.add(btnAtualizar);
        painelBotoesTabela.add(btnExcluir);

        painelExibir.add(scrollPane, BorderLayout.CENTER);
        painelExibir.add(painelBotoesTabela, BorderLayout.SOUTH);

        // Adicionando as abas ao JTabbedPane
        tabbedPane.addTab("Formulário", painelFormulario);
        tabbedPane.addTab("Exibir Quartos", painelExibir);

        // Adicionando o JTabbedPane à janela principal
        add(lblTitulo, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Botão Voltar
        btnVoltar = criarBotao("Voltar");
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(45, 45, 45));
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void adicionarCamposFormulario(JPanel painel, GridBagConstraints gbc) {
        JLabel lblNumeroQuarto = criarLabel("Número do Quarto:");
        txtNumeroQuarto = criarCampoTexto();

        JLabel lblTipoQuarto = criarLabel("Tipo do Quarto:");
        txtTipoQuarto = criarCampoTexto();

        JLabel lblPrecoDiaria = criarLabel("Preço da Diária:");
        txtPrecoDiaria = criarCampoTexto();

        JLabel lblAndar = criarLabel("Andar:");
        txtAndar = criarCampoTexto();

        JLabel lblStatusQuarto = criarLabel("Status:");
        cbStatusQuarto = new JComboBox<>(new String[]{"Disponível", "Ocupado", "Manutenção"});
        cbStatusQuarto.setBackground(new Color(50, 50, 50));
        cbStatusQuarto.setForeground(Color.WHITE);
        cbStatusQuarto.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        cbStatusQuarto.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        btnSalvarQuarto = criarBotao("Salvar");
        btnSalvarQuarto.addActionListener(e -> salvarOuAtualizarQuarto());  // Alterado para função de salvar ou atualizar

        // Adicionando componentes ao painel
        gbc.gridwidth = 1;
        adicionarComponente(painel, lblNumeroQuarto, txtNumeroQuarto, gbc, 1);
        adicionarComponente(painel, lblTipoQuarto, txtTipoQuarto, gbc, 2);
        adicionarComponente(painel, lblPrecoDiaria, txtPrecoDiaria, gbc, 3);
        adicionarComponente(painel, lblAndar, txtAndar, gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        painel.add(lblStatusQuarto, gbc);

        gbc.gridx = 1;
        painel.add(cbStatusQuarto, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 6;
        painel.add(btnSalvarQuarto, gbc);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        label.setForeground(Color.WHITE); // Texto branco
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        campo.setBackground(new Color(50, 50, 50)); // Fundo cinza escuro
        campo.setForeground(Color.WHITE); // Texto branco
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return campo;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Sans Serif", Font.BOLD, 14));
        botao.setBackground(new Color(50, 150, 250)); // Fundo azul
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void adicionarComponente(JPanel painel, JLabel label, JTextField campo, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(label, gbc);

        gbc.gridx = 1;
        painel.add(campo, gbc);
    }

    private void salvarOuAtualizarQuarto() {
        String numero = txtNumeroQuarto.getText().trim();
        String tipo = txtTipoQuarto.getText().trim();
        String preco = txtPrecoDiaria.getText().trim();
        String andar = txtAndar.getText().trim();
        String status = (String) cbStatusQuarto.getSelectedItem();

        if (numero.isEmpty() || tipo.isEmpty() || preco.isEmpty() || andar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conexao.conectar()) {
            // Verificar se o quarto existe para atualizar ou inserir
            PreparedStatement stmtVerificar = conn.prepareStatement("SELECT * FROM quartos WHERE numero = ?");
            stmtVerificar.setString(1, numero);
            ResultSet rs = stmtVerificar.executeQuery();

            if (rs.next()) {
                // Atualizar o quarto existente
                PreparedStatement stmtAtualizar = conn.prepareStatement("UPDATE quartos SET tipo = ?, preco = ?, andar = ?, status = ? WHERE numero = ?");
                stmtAtualizar.setString(1, tipo);
                stmtAtualizar.setString(2, preco);
                stmtAtualizar.setString(3, andar);
                stmtAtualizar.setString(4, status);
                stmtAtualizar.setString(5, numero);
                stmtAtualizar.executeUpdate();
                JOptionPane.showMessageDialog(this, "Quarto atualizado com sucesso.");
            } else {
                // Inserir um novo quarto
                PreparedStatement stmtInserir = conn.prepareStatement("INSERT INTO quartos (numero, tipo, preco, andar, status) VALUES (?, ?, ?, ?, ?)");
                stmtInserir.setString(1, numero);
                stmtInserir.setString(2, tipo);
                stmtInserir.setString(3, preco);
                stmtInserir.setString(4, andar);
                stmtInserir.setString(5, status);
                stmtInserir.executeUpdate();
                JOptionPane.showMessageDialog(this, "Quarto adicionado com sucesso.");
            }

            // Limpar os campos e recarregar a tabela
            limparCamposFormulario();
            carregarQuartos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar/quarto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarQuartos() {
        try (Connection conn = conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM quartos");
             ResultSet rs = stmt.executeQuery()) {

            modeloTabela.setRowCount(0); // Limpar a tabela

            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                        rs.getString("numero"),
                        rs.getString("tipo"),
                        rs.getDouble("preco"),
                        rs.getInt("andar"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioParaEdicao() {
        int linhaSelecionada = tabelaQuartos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um quarto para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numero = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        String tipo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String preco = modeloTabela.getValueAt(linhaSelecionada, 2).toString();
        String andar = modeloTabela.getValueAt(linhaSelecionada, 3).toString();
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 4);

        txtNumeroQuarto.setText(numero);
        txtTipoQuarto.setText(tipo);
        txtPrecoDiaria.setText(preco);
        txtAndar.setText(andar);
        cbStatusQuarto.setSelectedItem(status);

        tabbedPane.setSelectedIndex(0);  // Mudar para a aba de formulário
    }

    private void excluirQuarto() {
        int linhaSelecionada = tabelaQuartos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um quarto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numero = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o quarto " + numero + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try (Connection conn = conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM quartos WHERE numero = ?")) {

                stmt.setString(1, numero);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Quarto excluído com sucesso.");
                carregarQuartos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir quarto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCamposFormulario() {
        txtNumeroQuarto.setText("");
        txtTipoQuarto.setText("");
        txtPrecoDiaria.setText("");
        txtAndar.setText("");
        cbStatusQuarto.setSelectedIndex(0);
    }

    private void voltarParaTelaPrincipal() {
        this.setVisible(false);  // Ocultar a janela atual
        telaPrincipal.setVisible(true);  // Tornar a tela principal visível
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaGerenciarQuartos(new TelaPrincipalSistema()).setVisible(true));
    }
}
