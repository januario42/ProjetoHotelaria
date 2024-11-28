package hotelaria;

import hotelaria.conexao;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarReservas extends JFrame {

    private JTextField txtNumeroReserva, txtNomeHospede, txtNumeroQuarto, txtDataEntrada, txtDataSaida;
    private JComboBox<String> cbStatusReserva;
    private JButton btnSalvarReserva, btnVoltar, btnAtualizar, btnExcluir, btnExibirReservas;
    private JTable tabelaReservas;
    private DefaultTableModel modeloTabela;
    private TelaPrincipalSistema telaPrincipal;

    // Construtor principal para gerenciar reservas
    public TelaGerenciarReservas(TelaPrincipalSistema telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        inicializarComponentes();
    }

    // Método para inicializar a interface
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Reservas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurando cores e fontes gerais
        getContentPane().setBackground(new Color(45, 45, 45));

        // Título da janela
        JLabel lblTitulo = new JLabel("Gerenciar Reservas");
        lblTitulo.setFont(new Font("Sans Serif", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Criando o JTabbedPane para as abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Criando a aba de formulário
        JPanel painelFormulario = criarPainelFormulario();
        tabbedPane.addTab("Formulário", painelFormulario);

        // Criando a aba para exibir reservas
        JPanel painelExibir = criarPainelExibirReservas();
        tabbedPane.addTab("Exibir Reservas", painelExibir);

        // Adicionando o JTabbedPane ao JFrame
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

    // Método para criar o painel do formulário
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumeroReserva = criarLabel("Número da Reserva:");
        txtNumeroReserva = criarCampoTexto();

        JLabel lblNomeHospede = criarLabel("Nome do Hóspede:");
        txtNomeHospede = criarCampoTexto();

        JLabel lblNumeroQuarto = criarLabel("Número do Quarto:");
        txtNumeroQuarto = criarCampoTexto();

        JLabel lblDataEntrada = criarLabel("Data de Entrada:");
        txtDataEntrada = criarCampoTexto();

        JLabel lblDataSaida = criarLabel("Data de Saída:");
        txtDataSaida = criarCampoTexto();

        JLabel lblStatusReserva = criarLabel("Status:");
        cbStatusReserva = new JComboBox<>(new String[]{"Confirmada", "Cancelada", "Em Espera"});
        estilizarComboBox(cbStatusReserva);

        btnSalvarReserva = criarBotao("Salvar");
        btnSalvarReserva.addActionListener(e -> salvarOuAtualizarReserva());

        adicionarComponente(painel, lblNumeroReserva, txtNumeroReserva, gbc, 1);
        adicionarComponente(painel, lblNomeHospede, txtNomeHospede, gbc, 2);
        adicionarComponente(painel, lblNumeroQuarto, txtNumeroQuarto, gbc, 3);
        adicionarComponente(painel, lblDataEntrada, txtDataEntrada, gbc, 4);
        adicionarComponente(painel, lblDataSaida, txtDataSaida, gbc, 5);

        gbc.gridx = 0;
        gbc.gridy = 6;
        painel.add(lblStatusReserva, gbc);

        gbc.gridx = 1;
        painel.add(cbStatusReserva, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 7;
        painel.add(btnSalvarReserva, gbc);

        return painel;
    }

    // Método para criar o painel de exibição das reservas
    private JPanel criarPainelExibirReservas() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 45, 45));

        modeloTabela = new DefaultTableModel();
        modeloTabela.setColumnIdentifiers(new String[]{"Número Reserva", "Nome Hóspede", "Número Quarto", "Data Entrada", "Data Saída", "Status"});
        tabelaReservas = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);

        btnExibirReservas = criarBotao("Exibir Reservas");
        btnExibirReservas.addActionListener(e -> carregarReservas());

        btnAtualizar = criarBotao("Atualizar");
        btnAtualizar.addActionListener(e -> preencherFormularioParaEdicao());

        btnExcluir = criarBotao("Excluir");
        btnExcluir.addActionListener(e -> excluirReserva());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(45, 45, 45));
        painelBotoes.add(btnExibirReservas);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        painel.add(scrollPane, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    // Métodos auxiliares para estilização e criação de componentes
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        campo.setBackground(new Color(50, 50, 50));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return campo;
    }

    private void estilizarComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Sans Serif", Font.BOLD, 14));
        botao.setBackground(new Color(50, 150, 250));
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

    // Métodos principais de ação para reservas
   private void salvarOuAtualizarReserva() {
    String numeroReserva = txtNumeroReserva.getText().trim();
    String nomeHospede = txtNomeHospede.getText().trim();
    String numeroQuarto = txtNumeroQuarto.getText().trim();
    String dataEntrada = txtDataEntrada.getText().trim();
    String dataSaida = txtDataSaida.getText().trim();
    String status = (String) cbStatusReserva.getSelectedItem();

    if (numeroReserva.isEmpty() || nomeHospede.isEmpty() || numeroQuarto.isEmpty() || dataEntrada.isEmpty() || dataSaida.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (Connection conn = conexao.conectar()) {
        conn.setAutoCommit(false);

        // Verificar se o hóspede existe
        String sqlHospede = "SELECT id_hospede FROM hospedes WHERE  nome_completo = ?";
        PreparedStatement stmtHospede = conn.prepareStatement(sqlHospede);
        stmtHospede.setString(1, nomeHospede);
        ResultSet rsHospede = stmtHospede.executeQuery();

        int idHospede;
        if (rsHospede.next()) {
            idHospede = rsHospede.getInt("id_hospede");
        } else {
            JOptionPane.showMessageDialog(this, "Hóspede não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o quarto existe
        String sqlQuarto = "SELECT numero FROM quartos WHERE numero = ?";
        PreparedStatement stmtQuarto = conn.prepareStatement(sqlQuarto);
        stmtQuarto.setString(1, numeroQuarto);
        ResultSet rsQuarto = stmtQuarto.executeQuery();

        if (!rsQuarto.next()) {
            JOptionPane.showMessageDialog(this, "Quarto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Inserir ou atualizar a reserva
        String sqlReserva = "INSERT INTO reservas (numero_reserva, id_hospede, numero, data_entrada, data_saida, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE id_hospede = ?, numero = ?, data_entrada = ?, data_saida = ?, status = ?";
        PreparedStatement stmtReserva = conn.prepareStatement(sqlReserva);
        stmtReserva.setString(1, numeroReserva);
        stmtReserva.setInt(2, idHospede);
        stmtReserva.setString(3, numeroQuarto);
        stmtReserva.setString(4, dataEntrada);
        stmtReserva.setString(5, dataSaida);
        stmtReserva.setString(6, status);
        stmtReserva.setInt(7, idHospede);
        stmtReserva.setString(8, numeroQuarto);
        stmtReserva.setString(9, dataEntrada);
        stmtReserva.setString(10, dataSaida);
        stmtReserva.setString(11, status);

        stmtReserva.executeUpdate();
        conn.commit();

        JOptionPane.showMessageDialog(this, "Reserva salva com sucesso!");
        limparCampos();
        carregarReservas();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar reserva: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


    private void carregarReservas() {
        try (Connection conn = conexao.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservas");

            modeloTabela.setRowCount(0);
            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                    rs.getString("numero_reserva"),
                    rs.getString("id_hospede"),
                    rs.getString("numero"),
                    rs.getString("data_entrada"),
                    rs.getString("data_saida"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioParaEdicao() {
        int row = tabelaReservas.getSelectedRow();
        if (row >= 0) {
            txtNumeroReserva.setText((String) modeloTabela.getValueAt(row, 0));
            txtNomeHospede.setText((String) modeloTabela.getValueAt(row, 1));
            txtNumeroQuarto.setText((String) modeloTabela.getValueAt(row, 2));
            txtDataEntrada.setText((String) modeloTabela.getValueAt(row, 3));
            txtDataSaida.setText((String) modeloTabela.getValueAt(row, 4));
            cbStatusReserva.setSelectedItem(modeloTabela.getValueAt(row, 5));
        }
    }

    private void excluirReserva() {
        int row = tabelaReservas.getSelectedRow();
        if (row >= 0) {
            String numeroReserva = (String) modeloTabela.getValueAt(row, 0);
            try (Connection conn = conexao.conectar()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM reservas WHERE numero_reserva = ?");
                stmt.setString(1, numeroReserva);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Reserva excluída com sucesso!");
                carregarReservas();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir reserva: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void voltarParaTelaPrincipal() {
        this.dispose();
        telaPrincipal.setVisible(true);
    }

    private void limparCampos() {
        txtNumeroReserva.setText("");
        txtNomeHospede.setText("");
        txtNumeroQuarto.setText("");
        txtDataEntrada.setText("");
        txtDataSaida.setText("");
        cbStatusReserva.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipalSistema telaPrincipal = new TelaPrincipalSistema();
            TelaGerenciarReservas telaReservas = new TelaGerenciarReservas(telaPrincipal);
            telaReservas.setVisible(true);
        });
    }
}
