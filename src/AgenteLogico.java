import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AgenteLogico {
	Mapa mapa = null;
	Mapa mapaMental = null;
	char mapaMatrix[][];
	char mapaMentalMatrix[][];
	
	private static final String PROLOG_FILE = "T2.pl";
	private static final String MAP_FILE = "IA_2016.2_mapa.txt";
	
	public void setMapa(Mapa mapa){
		this.mapa=mapa;
	}
	public void setMapaMental(Mapa mapaMental){
		this.mapaMental = mapaMental;
	}
	/**
	 * Metodo que vai ler o arquivo do mapa e dar consult no
	 * arquivo do prolog
	 * @throws FileNotFoundException 
	 */
	public void init() {
		Scanner s = null;
		try {
			s = new Scanner(new File(MAP_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = s.nextLine();
		mapa.limX=line.length();
		mapaMental.limX=line.length();
		int i=1;
		while(s.hasNextLine()){
			i++;
			s.nextLine();
		}
		mapa.limY=i;
		mapaMental.limY=i;
		
		mapaMatrix = new char[mapa.limY][mapa.limX];
		mapaMentalMatrix = new char[mapa.limY][mapa.limX];
		
		try {
			s=new Scanner(new File(MAP_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Tile> toModify = new ArrayList<>();
		for(i=0;i<mapa.limY;i++){
			line = s.nextLine();
			for(int j=0;j<mapa.limX;j++){
				mapaMatrix[i][j] = line.charAt(j);
				mapaMentalMatrix[i][j]='x';
				toModify.add(new Tile(j+1,i+1,mapaMatrix[i][j]));
			}
		}
		mapa.setToModify(toModify);
		mapa.repaint();
		
		
		
	}

}
