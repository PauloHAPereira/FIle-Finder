package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import control.TelaPrincipalController;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TelaPrincipal {

	private JFrame frame;
	private JTextField textFieldNomeArquivo;

	/**
	 * Launch the application.
	 */
	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		TelaPrincipalController controle = new TelaPrincipalController();
		frame = new JFrame();
		frame.setBounds(100, 100, 340, 416);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldNomeArquivo = new JTextField();
		textFieldNomeArquivo.setBounds(12, 50, 189, 22);
		frame.getContentPane().add(textFieldNomeArquivo);
		textFieldNomeArquivo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 85, 298, 228);
		frame.getContentPane().add(scrollPane);
		
		JButton btnProcurar = new JButton("Procurar");
		btnProcurar.setBounds(213, 49, 97, 25);
		btnProcurar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = controle.encontrar(textFieldNomeArquivo);
				scrollPane.setViewportView(table);
			}
			
		});
		frame.getContentPane().add(btnProcurar);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(112, 331, 97, 25);
		frame.getContentPane().add(btnAbrir);
	}
}
