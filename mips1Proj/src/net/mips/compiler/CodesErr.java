package net.mips.compiler;

public enum CodesErr {
	CAR_INC_ERR("caractere inconnu"),
	FIC_VIDE_ERR("fichier vide");
	
	private String message;
	
	private CodesErr(String message) {
		this.message = message;
	}
	
	public String getMessage( ) {
		return this.message;
	}
	
	public void setMessage(String msg) {
		this.message = msg;
	}
}
