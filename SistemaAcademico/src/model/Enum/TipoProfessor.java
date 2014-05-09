package model.Enum;

public enum TipoProfessor {
	Professor(0), 
	Coordenador(1),
	Diretor(2);
	
	public int indice() {
		return indice;
	}
	
	private int indice;
	
	TipoProfessor(int indice){
		this.indice = indice;
	}
	
	public int getValor() {
		return indice;
	}
	
	public static TipoProfessor indice(int i) {
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

