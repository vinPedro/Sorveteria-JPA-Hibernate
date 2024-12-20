package View;

import Model.*;
import controller.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaSorvetes extends JFrame {

	private SorveteController sorveteController = new SorveteController();

	protected JTable tabela;
	private DefaultTableModel modelo;
	private JScrollPane painelTabela;

	//private SorveteDAO sorveteDAO = SorveteDAO.getInstance();

	public TelaSorvetes(List<Sorvete> sorvetes) {
		setTitle("Lista de Sorvetes");
		setSize(480, 480);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		addJLabelTitulo();
		addTabelaSorvetes();
		addJButtonAdicionar();
		addJButtonEditar();
		addJButtonDeletar();
		addJButtonVisualizar();
		popularTabela(sorvetes);
		setVisible(true);
	}

	private void addTabelaSorvetes() {
		modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new String[] { "Sabor", "Tipo", "Preço" });
		tabela = new JTable(modelo);
		tabela.setFont(new Font("Arial", Font.PLAIN, 17));
		painelTabela = new JScrollPane(tabela);
		painelTabela.setBounds(30, 80, 410, 290);
		add(painelTabela);
	}

	private void addJLabelTitulo() {
		JLabel jbTitulo = new JLabel("Lista de Gelados");
		jbTitulo.setBounds(0, 30, 480, 30);
		jbTitulo.setHorizontalAlignment(JLabel.CENTER);
		jbTitulo.setFont(new Font("Arial", Font.BOLD, 20));

		add(jbTitulo);
	}

	private void addJButtonAdicionar() {
		JButton jbADD = new JButton("Adicionar");
		jbADD.setBounds(345, 380, 95, 50);
		OuvinteAdicionar ouvinte = new OuvinteAdicionar();
		jbADD.addActionListener(ouvinte);
		add(jbADD);

	}

	private void addJButtonEditar() {
		JButton jbEditar = new JButton("Editar");
		jbEditar.setBounds(240, 380, 95, 50);
		OuvinteEditar ouvinte = new OuvinteEditar();
		jbEditar.addActionListener(ouvinte);
		add(jbEditar);

	}

	private void addJButtonDeletar() {
		JButton jbDeletar = new JButton("Deletar");
		jbDeletar.setBounds(135, 380, 95, 50);
		OuvinteDeletar ouvinte = new OuvinteDeletar();
		jbDeletar.addActionListener(ouvinte);
		add(jbDeletar);

	}

	private void addJButtonVisualizar() {
		JButton jbView = new JButton("Visualizar");
		jbView.setBounds(30, 380, 95, 50);
		OuvinteVisualizar ouvinte = new OuvinteVisualizar();
		jbView.addActionListener(ouvinte);
		add(jbView);

	}

	private class OuvinteVisualizar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Selecione um sorvete");
			} else {
				Sorvete sorveteSelecionado = sorveteController.listarTodosSorvetes().get(tabela.getSelectedRow());
				dispose();
				new TelaViewSorvete(sorveteSelecionado);

			}
		}

	}
	
	private void popularTabela(List<Sorvete> sorvetes) {

		for (Sorvete a : sorvetes) {
			adicionarLinhaTabela(a);
		}

	}

	public void adicionarLinhaTabela(Sorvete sorvete) {

		Object[] linha = new Object[3];
		linha[0] = sorvete.getSabor();
		linha[1] = sorvete.getTipo();
		linha[2] = sorvete.getPreco();

		modelo.addRow(linha);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

		for (int i = 0; i < modelo.getColumnCount(); i++) {
			tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	private class OuvinteEditar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Selecione um sorvete");
			}else {
				Sorvete sorveteSelecionado = sorveteController.listarTodosSorvetes().get(tabela.getSelectedRow());
				dispose();
				new TelaEditSorvete(sorveteSelecionado, tabela);

			}
		}
	}

	
	private class OuvinteAdicionar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			dispose();
			new TelaAddSorvete();

		}
	}

	private class OuvinteDeletar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (tabela.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Selecione um sorvete");
			} else {
				Sorvete sorveteLixo = sorveteController.listarTodosSorvetes().get(tabela.getSelectedRow());
				sorveteController.deletarSorvete(sorveteLixo.getId());
				dispose();
				new TelaSorvetes(sorveteController.listarTodosSorvetes());
			}
		}

	}

}
