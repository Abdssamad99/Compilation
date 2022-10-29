package net.mips.compiler;

import java.io.*;
import java.util.List;

public class Scanner {
	
	List<Symboles> motsCles;
	private Symboles SymbCour;
	private char carCour;
	private FileReader fluxSour;
	static final int EOF = '\0';
	
	Scanner(String f) throws ErreurLexicale,IOException
	{
		File file = new File(f);
		
		if(!file.exists()) {
			System.out.println("le fichier not exist");
		    throw new ErreurLexicale(CodesErr.FIC_VIDE_ERR);
		}
		    
		else {
			System.out.println("fichier exist");
		}
	}
	
	Scanner(FileReader f, List<Symboles>m) {
		this.fluxSour = f;
		this.motsCles = m;
	}
	
	public List<Symboles> getmotsCles() {
		return this.motsCles;
	}
	
	public void setmotsCles(List<Symboles>m) {
		this.motsCles = m;
	}
	
	public char getcarCour() {
		return this.carCour;
	}
	
	public void setcarCour(char car) {
		this.carCour = car;
	}
	
	public FileReader getfluxSour() {
		return this.fluxSour;
	}
	
	public void setfluxSour(FileReader f) {
		this.fluxSour = f;
	}
	
	public Symboles getSymbCour() {
		return SymbCour;
	}
	
	public void setSymCour(Symboles symbCour) {
		SymbCour = symbCour;
	}
	
	public void initMotsCles( ) {
		motsCles.add(new Symboles(Tokens.PROGRAM_TOKEN,"program"));
		motsCles.add(new Symboles(Tokens.CONST_TOKEN,"const"));
		motsCles.add(new Symboles(Tokens.VAR_TOKEN,"var"));
		motsCles.add(new Symboles(Tokens.BEGIN_TOKEN,"begin"));
		motsCles.add(new Symboles(Tokens.END_TOKEN,"end"));
		motsCles.add(new Symboles(Tokens.IF_TOKEN,"if"));
		motsCles.add(new Symboles(Tokens.THEN_TOKEN,"then"));
		motsCles.add(new Symboles(Tokens.WHILE_TOKEN,"while"));
		motsCles.add(new Symboles(Tokens.DO_TOKEN,"do"));
		motsCles.add(new Symboles(Tokens.WRITE_TOKEN,"write"));
		motsCles.add(new Symboles(Tokens.READ_TOKEN,"read"));
	}
	
	public void codageLex() {
		String nom = SymbCour.getNom();
		for(Symboles symb:motsCles) {
			String nom1 = symb.getNom();
			if(nom.equalsIgnoreCase(nom1)) {
				SymbCour.setToken(symb.getToken());
				return;
			}
		}
		SymbCour.setToken(Tokens.ID_TOKEN);
	}
	
	public void LireCar() throws IOException{
		if(fluxSour.ready())
			carCour = (char) fluxSour .read();
		else
			carCour = EOF;
	}
	
	public void LireMot() throws IOException{
		SymbCour.setNom(SymbCour.getNom()+carCour);
		LireCar();
		while(Character.isLetterOrDigit(carCour)) {
			SymbCour.setNom(SymbCour.getNom()+carCour);
			LireCar();
		}
		codageLex();
	}
	public void LireNombre() throws IOException {
		SymbCour.setNom(SymbCour.getNom()+carCour);
		LireCar();
		while(Character.isDigit(carCour)) {
			SymbCour.setNom(SymbCour.getNom()+carCour);
			LireCar();
		}
		SymbCour.setToken(Tokens.NUM_TOKEN);
	}
	
	public void symbSuiv() throws IOException, ErreurCompilation {
		SymbCour=new Symboles();
		while(Character.isWhitespace(carCour))
			LireCar();
		if (Character.isLetter(carCour)) {
			LireMot();
			return;
		}
		if(Character.isDigit(carCour)) {
			LireNombre();
			return;
		}
		switch(carCour) {
		case '+':
			SymbCour.setToken(Tokens.PLUS_TOKEN);
			LireCar();
			break;
		case '-':
			SymbCour.setToken(Tokens.MOINS_TOKEN);
			LireCar();
			break;
		case '*':
			SymbCour.setToken(Tokens.MUL_TOKEN);
			LireCar();
			break;
		case '/':
			SymbCour.setToken(Tokens.DIV_TOKEN);
			LireCar();
			break;
		case '(':
			SymbCour.setToken(Tokens.PARG_TOKEN);
			LireCar();
			break;
		case ')':
			SymbCour.setToken(Tokens.PARD_TOKEN);
			LireCar();
			break;
		case '.':
			SymbCour.setToken(Tokens.PNT_TOKEN);
			LireCar();
			break;
		case ',':
			SymbCour.setToken(Tokens.VIR_TOKEN);
			LireCar();
			break;
		case ';':
			SymbCour.setToken(Tokens.PVIR_TOKEN);
			LireCar();
			break;
		case '=':
			SymbCour.setToken(Tokens.EG_TOKEN);
			LireCar();
			break;
		case EOF:
			SymbCour.setToken(Tokens.EOF_TOKEN);
			break;
		case ':':
			LireCar();
			switch(carCour) {
			case '=':
				SymbCour.setToken(Tokens.AFFEC_TOKEN);
				LireCar();
				break;
			default:
				SymbCour.setToken(Tokens.ERR_TOKEN);
				throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
			}
			break;
		case '>':
			LireCar();
			switch(carCour) {
			case '=':
				SymbCour.setToken(Tokens.SUPEG_TOKEN);
				LireCar();
				break;
			default:
				SymbCour.setToken(Tokens.SUP_TOKEN);
				break;
			}
			break;
		case '<':
			LireCar();
			switch(carCour) {
			case '=':
				SymbCour.setToken(Tokens.INFEG_TOKEN);
				LireCar();
				break;
			case '>':
				SymbCour.setToken(Tokens.DIV_TOKEN);
				LireCar();
				break;
			default:
				SymbCour.setToken(Tokens.INF_TOKEN);
				break;
			}
			break;
		default:
			throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
		}
		
			
	}
	
	public static void main(String args[]) 
		throws IOException, ErreurCompilation {
		Scanner scanner=new Scanner("test1");
		scanner.initMotsCles();
		scanner.LireCar();
		while(scanner.getcarCour()!=EOF) {
			scanner.symbSuiv();
			System.out.println(scanner.getSymbCour().getToken());
		}
	}

}
