package control;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Documento;
import model.EncontraDoc;

public class TelaPrincipalController {

	public JTable encontrar(JTextField jTNomeArquivo){
		
		EncontraDoc encDoc = new EncontraDoc();
		JTable table = new JTable();
		Object row [] = new Object[2];
		String nomeArquivo = jTNomeArquivo.getText();
		ArrayList<Documento> lDocumentos = new ArrayList<>();
		
		lDocumentos = encDoc.encontrar(nomeArquivo);
		DefaultTableModel dFTM = new DefaultTableModel(
				new Object[][]{

				},
				new String []{
						"Nome","Tipo"
				});
		
		for(Documento documento : lDocumentos){
			row[0] = documento.getNome();
			row[1] = documento.getTipo();
			dFTM.addRow(row);
		}
		table.setModel(dFTM);
		return table;

	}
}
