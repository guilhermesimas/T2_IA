import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class AgenteLogico implements Runnable{
	Mapa mapa = null;
	Mapa mapaMental = null;
	char mapaMatrix[][];
	char mapaMentalMatrix[][];
	private ArrayList<Tile> toModify;
	private int refreshRate = 1000;
	
	private static final String PROLOG_FILE = "T2.pl";
	private static final String MAP_FILE = "IA_2016.2_mapa.txt";
	
	public void setMapa(Mapa mapa){
		this.mapa=mapa;
	}
	public void setMapaMental(Mapa mapaMental){
		this.mapaMental = mapaMental;
	}
	public void setRefreshRate(int refreshRate){
		this.refreshRate=refreshRate;
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
		
		Consult.init(mapa.limX,mapa.limY);
		Tile.setLim(mapa.limX,mapa.limY);
		mapaMatrix = new char[mapa.limY][mapa.limX];
		mapaMentalMatrix = new char[mapa.limY][mapa.limX];
		
		try {
			s=new Scanner(new File(MAP_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.toModify = new ArrayList<>();
		for(i=0;i<mapa.limY;i++){
			line = s.nextLine();
			for(int j=0;j<mapa.limX;j++){
				mapaMatrix[i][j] = line.charAt(j);
				Consult.set(j+1, mapa.limY-(i), line.charAt(j));
				mapaMentalMatrix[i][j]='X';
				this.toModify.add(new Tile(j+1,mapa.limY-(i),mapaMatrix[i][j]));
			}
		}
		
		
		
	}
	public void updateMapa() {
		mapa.update(this.toModify);		
	}
	/**
	 * Esse metodo run irá fazer as ações e consultas usando o Consult
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//inicializa o consult.
		Queue commands = new LinkedList<Action>();
		/*
		 * A principio ele roda para sempre, darei um break quando a soluçao
		 * for "sair" ou algo do tipo. Ir para a saida, etc.
		 */
		while(true){
			ArrayList<Tile> toModify = Consult.observa();
			if(commands.isEmpty()){
				commands.add(Consult.getSugestao());
			}
			/*
			 * Pegar estado atual para apagar no mapa e pegar estado novo para desenhar no mapa.
			 */
			Estado atual = Consult.getE();
			int oldX = atual.getX();
			int oldY = atual.getY();
			char oldC = mapaMatrix[oldY-1][oldX-1];
			Action acao = Action.valueOf((String) commands.poll());
			if(acao==Action.pegar_ouro || acao==Action.pegar_powerup){
				acao = Action.pegar_objeto;
			}
			else if(acao==Action.astar_safe)
				acao = Action.virar_a_direita;
			Consult.agir(acao);
			if(acao == Action.pegar_objeto){
				mapaMatrix[oldY-1][oldX-1] = '.';
				oldC = '.';
			}
			Estado novo = Consult.getE();			
			
			Tile old = new Tile(oldX,oldY,oldC);
			Tile next = new Tile(novo.getX(),novo.getY(),novo.getD());
			
			ArrayList<Tile> mapaUpdate = new ArrayList<>();
			mapaUpdate.add(old);
			mapaUpdate.add(next);
			
			mapa.update(mapaUpdate);
			mapaMental.update(toModify);
			for(Tile t:toModify){
				System.out.println("toModify:<"+t.getX()+"/"+t.getY()+">:"+t.getC());
			}
			
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
