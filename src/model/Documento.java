package model;

public class Documento {
	private String nome;	//nome do documnto
	private String tipo; 	//arquivo ou diretorio no momento
	private String caminho; //caminho onde esse documento se encontra

	public Documento(){
	}
	
	public Documento(String nome, String tipo, String caminho){
		this.nome 	 = nome;
		this.tipo 	 = tipo;
		this.caminho = caminho;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	
}
