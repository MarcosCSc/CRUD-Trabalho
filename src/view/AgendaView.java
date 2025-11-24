package view;

import controller.AgendaController;
import model.Contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AgendaView extends JFrame {
    private AgendaController controller = new AgendaController();

    private JTextField txtNome, txtTelefone, txtCelular, txtEmail, txtId;
    private JButton btnAdicionar, btnAtualizar, btnRemover, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modelo;

    public AgendaView() {
        initUI();
        carregarTabela();
    }

    private void initUI() {
        setTitle("Agenda Telefônica - CRUD do Marcão");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Campos
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 80, 25);
        add(lblId);
        txtId = new JTextField("Automático");
        txtId.setBounds(110, 20, 80, 25);
        txtId.setEditable(false);
        txtId.setBackground(new java.awt.Color(240, 240, 240));
        add(txtId);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 80, 25);
        add(lblNome);
        txtNome = new JTextField();
        txtNome.setBounds(110, 60, 250, 25);
        add(txtNome);

        JLabel lblTel = new JLabel("Telefone:");
        lblTel.setBounds(20, 100, 80, 25);
        add(lblTel);
        txtTelefone = new JTextField();
        txtTelefone.setBounds(110, 100, 150, 25);
        add(txtTelefone);

        JLabel lblCel = new JLabel("Celular:");
        lblCel.setBounds(20, 140, 80, 25);
        add(lblCel);
        txtCelular = new JTextField();
        txtCelular.setBounds(110, 140, 150, 25);
        add(txtCelular);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(20, 180, 80, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(110, 180, 250, 25);
        add(txtEmail);

        // Botões
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(420, 60, 120, 35);
        add(btnAdicionar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(420, 110, 120, 35);
        add(btnAtualizar);

        btnRemover = new JButton("Remover");
        btnRemover.setBounds(420, 160, 120, 35);
        add(btnRemover);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(420, 210, 120, 35);
        add(btnLimpar);

        // Tabela
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Telefone");
        modelo.addColumn("Celular");
        modelo.addColumn("E-mail");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 260, 740, 220);
        add(scroll);

        // Eventos
        btnAdicionar.addActionListener(e -> adicionar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnRemover.addActionListener(e -> remover());
        btnLimpar.addActionListener(e -> limpar());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(tabela.getValueAt(linha, 0).toString());
                    txtNome.setText(tabela.getValueAt(linha, 1).toString());
                    txtTelefone.setText(tabela.getValueAt(linha, 2).toString());
                    txtCelular.setText(tabela.getValueAt(linha, 3).toString());
                    txtEmail.setText(tabela.getValueAt(linha, 4).toString());
                }
            }
        });
    }

    private void adicionar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome é obrigatório!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Contato c = new Contato(0, txtNome.getText().trim(), txtTelefone.getText(), txtCelular.getText(), txtEmail.getText());
        controller.adicionar(c);
        carregarTabela();
        limpar();
        JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void atualizar() {
        if (txtId.getText().equals("Automático")) {
            JOptionPane.showMessageDialog(this, "Selecione um contato na tabela para atualizar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        Contato c = new Contato(id, txtNome.getText().trim(), txtTelefone.getText(), txtCelular.getText(), txtEmail.getText());

        if (controller.atualizar(id, c)) {
            carregarTabela();
            limpar();
            JOptionPane.showMessageDialog(this, "Contato atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o contato.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover() {
        if (txtId.getText().equals("Automático")) {
            JOptionPane.showMessageDialog(this, "Selecione um contato na tabela para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover este contato?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            controller.remover(id);
            carregarTabela();
            limpar();
            JOptionPane.showMessageDialog(this, "Contato removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpar() {
        txtId.setText("Automático");
        txtNome.setText("");
        txtTelefone.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Contato c : controller.listar()) {
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getTelefone(), c.getCelular(), c.getEmail()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendaView().setVisible(true));
    }
}