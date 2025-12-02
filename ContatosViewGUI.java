package View;

import Controller.ContatosController;
import Model.Contatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ContatosViewGUI extends JFrame {

    private ContatosController controller;

    
    private JTextField txtNome, txtTelefone, txtEmail;
    private JButton btnAdicionar, btnEditar, btnExcluir;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = -1; 

    public ContatosViewGUI() {
        controller = new ContatosController();
        inicializarComponentes();
        carregarContatos();
    }

    private void inicializarComponentes() {
        
        setTitle(" --Agenda de Contatos-- ");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        
        getContentPane().setBackground(new Color(240, 240, 240));

        
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        painelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "📋 Agenda de Contatos",
                0,
                0,
                new Font("Times New Roman", Font.BOLD, 14),
                new Color(60, 60, 60)
            )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // nome
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        painelFormulario.add(lblNome, gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        txtNome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        painelFormulario.add(txtNome, gbc);

        // tel
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        painelFormulario.add(lblTelefone, gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        txtTelefone = new JTextField(20);
        txtTelefone.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        txtTelefone.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        painelFormulario.add(txtTelefone, gbc);

        // email
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        painelFormulario.add(lblEmail, gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        painelFormulario.add(txtEmail, gbc);

        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(Color.WHITE);

        btnAdicionar = criarBotao("Adicionar", new Color(76, 175, 80));
        btnEditar = criarBotao("Editar", new Color(33, 150, 243));
        btnExcluir = criarBotao("Excluir", new Color(244, 67, 54));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painelFormulario.add(painelBotoes, gbc);

       
        String[] colunas = {"ID", "Nome", "Telefone", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(220, 220, 220));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setSelectionBackground(new Color(184, 207, 229));

       
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200); // nome
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150); // tel
        tabela.getColumnModel().getColumn(3).setPreferredWidth(250); // email

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

       
        btnAdicionar.addActionListener(e -> adicionarContato());
        btnEditar.addActionListener(e -> editarContato());
        btnExcluir.addActionListener(e -> excluirContato());

       
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabela.getSelectedRow();
                if (row >= 0) {
                    idSelecionado = (int) modeloTabela.getValueAt(row, 0);
                    txtNome.setText((String) modeloTabela.getValueAt(row, 1));
                    txtTelefone.setText((String) modeloTabela.getValueAt(row, 2));
                    txtEmail.setText((String) modeloTabela.getValueAt(row, 3));
                }
            }
        });

       
        add(painelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });

        return btn;
    }



    private void adicionarContato() {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha o nome!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Contatos novoContato = new Contatos(nome, telefone, email);

        if (controller.adicionarContato(novoContato)) {
            JOptionPane.showMessageDialog(this, 
                "Contato adicionado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarContatos();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao adicionar contato!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarContato() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um contato na tabela!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha o nome!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Contatos contatoAtualizado = new Contatos(idSelecionado, nome, telefone, email);

        if (controller.atualizarContato(contatoAtualizado)) {
            JOptionPane.showMessageDialog(this, 
                "Contato atualizado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarContatos();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao atualizar contato!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirContato() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um contato na tabela!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este contato?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.excluirContato(idSelecionado)) {
                JOptionPane.showMessageDialog(this, 
                    "Contato excluído com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarContatos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir contato!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarContatos() {
        modeloTabela.setRowCount(0); 

        for (Contatos c : controller.listarContatos()) {
            Object[] linha = {
                c.getId(),
                c.getNome(),
                c.getTelefone(),
                c.getEmail()
            };
            modeloTabela.addRow(linha);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        idSelecionado = -1;
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContatosViewGUI frame = new ContatosViewGUI();
            frame.setVisible(true);
        });
    }
}
