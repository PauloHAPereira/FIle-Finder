package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncontraDoc {


	public ArrayList<Documento> encontrar(String seqCaractere){
		ArrayList<Documento> documentos = new ArrayList<>();
		String caminhoDiretorioAtual = Constantes.HOMEPATH + Constantes.BLENDERPATH;
		//ArrayList<Documento> todosDoc = adcionarArquivos(caminhoDiretorioAtual);
		ArrayList<Documento> todosDoc = listarArquivos(caminhoDiretorioAtual);
		documentos = listarDocumentos(todosDoc);
		
		documentos = combinar(seqCaractere, todosDoc);
		return documentos;
	} 

	private ArrayList<Documento> combinar (String seqCaractere, ArrayList<Documento> lDocumentos){

		ArrayList<Documento> lDocumentoAux = new ArrayList<>(); 
		lDocumentoAux.addAll(lDocumentos);
		Pattern combinacao = Pattern.compile(seqCaractere);
		Matcher combina;
		String nomeDoc = "";

		int quantDocumentos = lDocumentos.size();

		for(int i = 0 ; i < quantDocumentos; i++ ){
			Documento doc = lDocumentos.get(i);
			nomeDoc = doc.getNome();
			combina = combinacao.matcher(nomeDoc);
			if(!combina.find()){
				lDocumentoAux.remove(doc);
			}
		}

		return lDocumentoAux;
	}

	private ArrayList<Documento> listarArquivos(String diretorioAtual){


		Path path = Paths.get(diretorioAtual);
		ArrayList<Documento> lDocumentos = new ArrayList<>();		
		String nomeDoc;
		String tipoDoc;
		String caminhoDoc;		
		DirectoryStream<Path> diretorio;

		try {
			diretorio = Files.newDirectoryStream(path);
			for (Path arquivos : diretorio) {				
				nomeDoc = arquivos.getFileName().toString();
				caminhoDoc = diretorioAtual + "/" + nomeDoc;
				if(Files.isDirectory(arquivos)){
					tipoDoc = "Pasta de arquivos";
				}else{
					tipoDoc = getTipoArquivo(nomeDoc);
				}
				Documento doc = new Documento(nomeDoc, tipoDoc, caminhoDoc);
				lDocumentos.add(doc);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return lDocumentos;
	}

	private ArrayList<Documento> adcionarArquivos (String diretorioAtual){

		ArrayList<Documento> lDocumentos = listarArquivos(diretorioAtual);
		ArrayList<Documento> lDocumentosAux = lDocumentos;
		String diretorio = "";

		int quantDocumento = lDocumentos.size();
		for(int i = 0 ; i < quantDocumento; i++ ){
			Documento doc = lDocumentos.get(i);
			diretorio = doc.getCaminho();
			if(doc.getTipo().equals("Pasta de arquivos")){
				lDocumentosAux.addAll(adcionarArquivos(diretorio));
			}
		}
		return lDocumentosAux;
	}

	private ArrayList<Documento> listarDocumentos(ArrayList<Documento> lDocumentos){

		ArrayList<Documento> lDocumentosAux = new ArrayList<>();
		ArrayList<String> 	 caminhosPastasVisitados = new ArrayList<>();
		ArrayList<String> 	 caminhosPastas = new ArrayList<>();
		ListaDocumentos 	 listDoc  = null;
		int numThread = 2;
		ExecutorService threadPool = Executors.newFixedThreadPool(numThread);
		caminhosPastas.addAll(verificarTipo(lDocumentos, caminhosPastasVisitados));
		lDocumentosAux.addAll(lDocumentos);
		int quantidadeDiretorios = caminhosPastas.size();
		String caminho;
		
		
		while(quantidadeDiretorios>0){
			if(caminhosPastas.get(0) != null){
				caminho = caminhosPastas.get(0);
				listDoc = new ListaDocumentos(caminho,caminhosPastasVisitados);
				threadPool.execute(listDoc);
				caminhosPastasVisitados.add(caminho);
				caminhosPastas.remove(0);
				quantidadeDiretorios--;
			}
			
			if(listDoc!=null){
				lDocumentosAux.addAll(listDoc.getlDocumentos());
			}
			caminhosPastas.addAll(verificarTipo(lDocumentosAux, caminhosPastasVisitados));
			quantidadeDiretorios = caminhosPastas.size();
		}
		threadPool.shutdown();
		
		return lDocumentosAux;
	}

	private ArrayList<String> verificarTipo (ArrayList<Documento> lDocumentos, 
			ArrayList<String> lDiretorioVisitados){
		ArrayList<String> caminhos = new ArrayList<>();

		for (Documento doc : lDocumentos) {
			Path path = Paths.get(doc.getCaminho());
			if(Files.isDirectory(path) && verificarListaArquvoVisitado(lDiretorioVisitados, doc.getCaminho())){
				caminhos.add(doc.getCaminho());
			}
		}
		return caminhos;
	}

	private boolean verificarListaArquvoVisitado(ArrayList<String> lDiretorioVisitados, String caminho){
		boolean naoVisitado = true;
		for (String caminhoDiretorio : lDiretorioVisitados){
			if(caminho.equals(caminhoDiretorio)){
				naoVisitado = false;
				break;
			}
		}
		return naoVisitado;
	}
	
	private String getTipoArquivo(String arquivo){
		String tipoArquivo = "";
		String split[] = arquivo.split(Pattern.quote("."));
		if(split.length <= 1){
			tipoArquivo = "Executavel";
		}else{
			tipoArquivo = split[1];
		}
		return tipoArquivo;
	}
}
