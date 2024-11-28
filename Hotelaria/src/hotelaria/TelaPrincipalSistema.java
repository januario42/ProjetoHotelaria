package hotelaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaPrincipalSistema extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(TelaPrincipalSistema.class.getName());

    public TelaPrincipalSistema() {
        LOGGER.log(Level.INFO, "Inicializando tela principal do sistema...");

        // Configurações básicas da janela
        setTitle("Sistema de Hotelaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768); // Tela maior para visual moderno
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra de menu
        configurarMenu();

        // Painel do cabeçalho para botões
        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(new GridLayout(1, 4, 20, 0)); // Layout com espaçamento uniforme
        painelCabecalho.setOpaque(false); // Fundo transparente
        painelCabecalho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margens externas
        add(painelCabecalho, BorderLayout.NORTH);

        // Botões estilizados
        JButton btnInserirHospedes = criarBotao("Gerenciamento de Hóspedes", "C:\\Users\\kaua\\Documents\\NetBeansProjects\\Hotelaria\\src\\hotelaria\\hospedes_icon.png");
        btnInserirHospedes.addActionListener(e -> abrirTelaInserirHospedes());

        JButton btnInserirQuartos = criarBotao("Inserir Quartos", "C:\\Users\\kaua\\Documents\\NetBeansProjects\\Hotelaria\\src\\hotelaria\\quartos_icon.png");
        btnInserirQuartos.addActionListener(e -> abrirTelaInserirQuartos());

        JButton btnCadastrarFuncionarios = criarBotao("Cadastrar Funcionários", "C:\\Users\\kaua\\Documents\\NetBeansProjects\\Hotelaria\\src\\hotelaria\\funcionarios_icon.png");
        btnCadastrarFuncionarios.addActionListener(e -> abrirTelaCadastrarFuncionarios());

        JButton btnGerenciarReservas = criarBotao("Gerenciar Reservas", "C:\\Users\\kaua\\Documents\\NetBeansProjects\\Hotelaria\\src\\hotelaria\\reservas_icon.png");
        btnGerenciarReservas.addActionListener(e -> abrirTelaReservas());

        // Adicionando botões ao painel
        painelCabecalho.add(btnInserirHospedes);
        painelCabecalho.add(btnInserirQuartos);
        painelCabecalho.add(btnCadastrarFuncionarios);
        painelCabecalho.add(btnGerenciarReservas);

        // Painel de fundo com imagem redimensionável
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("C:\\Users\\kaua\\Desktop\\Hotelaria\\src\\hotelaria\\hotel_background.jpg").getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Redimensiona a imagem ao tamanho do painel
            }
        };
        painelFundo.setLayout(new BorderLayout());
        add(painelFundo, BorderLayout.CENTER);

        // Texto de boas-vindas centralizado
        JLabel textoBemVindo = new JLabel("Bem-vindo ao Sistema de Hotelaria!");
        textoBemVindo.setFont(new Font("Sans Serif", Font.BOLD, 32));
        textoBemVindo.setForeground(Color.WHITE);
        textoBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        textoBemVindo.setVerticalAlignment(SwingConstants.CENTER);
        painelFundo.add(textoBemVindo, BorderLayout.CENTER);
    }

    // Método para criar botões estilizados com ícones
 // Método para criar botões estilizados com ícones
private JButton criarBotao(String texto, String caminhoIcone) {
    JButton botao = new JButton(texto, new ImageIcon(new ImageIcon(caminhoIcone).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
    botao.setFont(new Font("Sans Serif", Font.BOLD, 16));
    botao.setBackground(new Color(50, 50, 50)); // Cor sólida para o fundo
    botao.setForeground(Color.WHITE);
    botao.setFocusPainted(false);
    botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    botao.setHorizontalTextPosition(SwingConstants.CENTER);
    botao.setVerticalTextPosition(SwingConstants.BOTTOM);

    // Adicionando efeito hover
    botao.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            botao.setBackground(new Color(100, 100, 100)); // Alterar para um cinza mais claro ao passar o mouse
            botao.setForeground(Color.BLACK);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            botao.setBackground(new Color(50, 50, 50)); // Restaurar a cor original
            botao.setForeground(Color.WHITE);
        }
    });

    return botao;
}


    // Configurar barra de menu
    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOpcoes = new JMenu("Opções");
        JMenuItem sair = new JMenuItem("Sair");
        sair.addActionListener(e -> {
            LOGGER.log(Level.INFO, "Voltando para a tela de login.");
            voltarParaTelaLogin(); // Volta para o login
        });
        menuOpcoes.add(sair);

        menuBar.add(menuOpcoes);
        setJMenuBar(menuBar);
    }

    // Método para voltar para a tela de login
    private void voltarParaTelaLogin() {
        // Fechar a tela atual
        this.dispose();

        // Criar a tela de login (substitua LoginScreen pelo nome da sua classe de login)
        TelaLogin login = new  TelaLogin(); // A classe LoginScreen deve ser a sua tela de login
        login.setVisible(true);
    }

    // Métodos para abrir as telas específicas
   private void abrirTelaInserirHospedes() {
    LOGGER.log(Level.INFO, "Abrindo tela de inserção de hóspedes.");
    TelaInserirHospede tela = new TelaInserirHospede();
    tela.setVisible(true);
}

private void abrirTelaInserirQuartos() {
    LOGGER.log(Level.INFO, "Abrindo tela de inserção de quartos.");
    
    // Certifique-se de passar a referência da tela principal para a tela de quartos
    TelaGerenciarQuartos telaQuartos = new TelaGerenciarQuartos(this);  // 'this' refere-se à TelaPrincipal
    telaQuartos.setVisible(true);
}


    private void abrirTelaCadastrarFuncionarios() {
        LOGGER.log(Level.INFO, "Abrindo tela de inserção de Funcionario.");
    TelaGerenciarFuncionarios telaFuncionario = new TelaGerenciarFuncionarios();
    telaFuncionario.setVisible(true);
    }
public void abrirTelaReservas() {
    TelaGerenciarReservas telaGerenciar = new TelaGerenciarReservas(this);
    telaGerenciar.setVisible(true);
}


    // Método principal para testar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipalSistema telaPrincipal = new TelaPrincipalSistema();
            telaPrincipal.setVisible(true);
        });
    }
}
