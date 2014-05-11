package model.enums;

public enum TypeProfessor {
	Professor(0), 
	Coordenador(1),
	Diretor(2);
	
	public int indice() {
		return indice;
	}
	
	private int indice;
	
	TypeProfessor(int indice){
		this.indice = indice;
	}
	
	public int getValor() {
		return indice;
	}
	
	public static TypeProfessor indice(int i) {
		switch (i) {
		case 0:
			return Professor;
		case 1:
			return Coordenador;
		case 2:
			return Diretor;
		default:
			return null;
		}
	}
}

