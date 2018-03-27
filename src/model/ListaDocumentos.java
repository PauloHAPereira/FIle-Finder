package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListaDocumentos implements Runnable{

	private ArrayList<Documento> lDocumentos = new ArrayList<>();
	private String diretorioAtual = "";
	private ArrayList<String> caminhosVisitados = new ArrayList<>();

	public ListaDocumentos (String diretorioAtual, ArrayList<String> caminhosVisitados){
		this.diretorioAtual = diretorioAtual;
		this.caminhosVisitados = caminhosVisitados;
	}

	public ArrayList<Documento> getlDocumentos() {
		return lDocumentos;
	}

	public void run(){

		this.lDocumentos = listarArquivos();

	}	

	private synchronized boolean verificarListaArquvoVisitado(){
		boolean naoVisitado = true;
		for (String caminho : caminhosVisitados){
			if(diretorioAtual.equals(caminho)){
				naoVisitado = false;
				break;
			}
		}
		return naoVisitado;
	}


	public synchronized ArrayList<Documento> listarArquivos(){

		Path path = Paths.get(this.diretorioAtual);		
		String nomeDoc;
		String tipoDoc;
		String caminhoDoc;		
		DirectoryStream<Path> diretorio;

		try {
			diretorio = Files.newDirectoryStream(path);
			for (Path arquivos : diretorio) {		
				nomeDoc = arquivos.getFileName().toString();
				caminhoDoc = this.diretorioAtual + "/" + nomeDoc;
				System.out.println("CaminhoDocumento: " + caminhoDoc);
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
