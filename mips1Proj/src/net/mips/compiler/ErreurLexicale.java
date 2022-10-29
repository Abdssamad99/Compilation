package net.mips.compiler;

public class ErreurLexicale extends ErreurCompilation {
	
	private CodesErr erreur;
	
	public ErreurLexicale(CodesErr erreur) {
		this.erreur = erreur;
	}
}
