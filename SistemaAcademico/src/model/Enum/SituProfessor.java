package model.Enum;

public enum SituProfessor {
	Ativo(0), 
	Inativo(1);
	
	public int indice() {
		return indice;
	}
	
	private int indice;
	
	SituProfessor(int indice){
		this.indice = indice;
	}
	
	public int getValor() {
		return indice;
	}
	
	public static SituProfessor indice(int i) {
		switch (i) {
		case 0:
			return Ativo;
		case 1:
			return Inativo;
		default:
			return null;
		}
	}
}

