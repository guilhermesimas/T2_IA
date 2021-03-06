import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JLabel;

public class AgenteLogico implements Runnable{
	Mapa mapa = null;
	Mapa mapaMental = null;
	char mapaMatrix[][];
	char mapaMentalMatrix[][];
	private ArrayList<Tile> toModify;
	private int refreshRate = 1000;
	private JLabel label;
	
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
		
		mapaMentalMatrix[mapa.limY-1][0]='x';
		
		
		
	}
	public void updateMapa() {
		mapa.update(this.toModify);		
	}
	/**
	 * Esse metodo run ir� fazer as a��es e consultas usando o Consult
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//inicializa o consult.
		Queue<Action> commands = new LinkedList<Action>();
		/*
		 * A principio ele roda para sempre, darei um break quando a solu�ao
		 * for "sair" ou algo do tipo. Ir para a saida, etc.
		 */
		while(true){
			ArrayList<Tile> toModify = Consult.observa();
			
			mapaMental.update(toModify);
			for(Tile t:toModify){
				System.out.println("toModify:<"+t.getX()+"/"+t.getY()+">:"+t.getC());
				mapaMentalMatrix[t.getY()-1][t.getX()-1]=t.getC();
				if(t.getX()==1 && t.getY()==1){
					mapaMentalMatrix[0][0]='x';
				}
			}
			
			if(commands.isEmpty()){
				commands.add(Consult.getSugestao());
			}
			/*
			 * Pegar estado atual para apagar no mapa e pegar estado novo para desenhar no mapa.
			 */
			Estado atual = Consult.getE();
			updateLabel(atual);
			int oldX = atual.getX();
			int oldY = atual.getY();
			char oldC = mapaMatrix[mapa.limY-oldY][oldX-1];
			Action acao = commands.poll();
			if(acao.getAction()==ActionEnum.pegar_ouro || acao.getAction()==ActionEnum.pegar_powerup){
				acao = new Action(ActionEnum.pegar_objeto);
			}else if(acao.getAction()==ActionEnum.sair){
				return;
			}
			else if(acao.getAction()==ActionEnum.astar_safe){
				/*
				 * Implementar aqui a AStar.
				 * Ela precisa de uma lista de Nodes
				 * dos "destinos" dela (ou s� um char 
				 * e ela encontra o resto no mapaMental)
				 * Ela precisa da matriz do mapa mental e
				 * ela precisa dos dois limites. Acho que
				 * passando os limites ela consegue encontrar
				 */
				
				for(int i=0;i<12;i++){
					for(int j=0;j<12;j++){
						System.out.print(mapaMentalMatrix[i][j]);
					}
					System.out.println();
				}
				AStar busca = new AStar();
				busca.setLimX(mapaMental.limX);
				busca.setLimY(mapaMental.limY);
				busca.setC('S');
				busca.setMapa(mapaMentalMatrix);
				ArrayList<Action> novasAcoes = busca.findPath(atual.getX(), 
												atual.getY(), 
												AStar.DtoInt(atual.getD()));
				if(novasAcoes==null || novasAcoes.isEmpty()){
					System.out.println("NOVAS ACOES NULL");
				}
				for(Action a:novasAcoes){
					commands.add(a);
					System.out.println("COMMAND: "+a.getAction().name());
				}
				acao = commands.poll();
			}else if(acao.getAction()==ActionEnum.astar_saida){
				
//				for(int i=0;i<12;i++){
//					for(int j=0;j<12;j++){
//						System.out.print(mapaMentalMatrix[i][j]);
//					}
//					System.out.println();
//				}
				AStar busca = new AStar();
				busca.setLimX(mapaMental.limX);
				busca.setLimY(mapaMental.limY);
				busca.setC('x');
				busca.setMapa(mapaMentalMatrix);
				ArrayList<Action> novasAcoes = busca.findPath(atual.getX(), 
												atual.getY(), 
												AStar.DtoInt(atual.getD()));
				if(novasAcoes==null || novasAcoes.isEmpty()){
					System.out.println("NOVAS ACOES NULL");
				}
				for(Action a:novasAcoes){
					commands.add(a);
					System.out.println("COMMAND: "+a.getAction().name());
				}
				acao = commands.poll();
			}
			if(acao.getAction() == ActionEnum.procurar_perigo){
				System.out.println("PROCURA SAPORRA");
			}
			Consult.agir(acao);
			if(acao.getAction() == ActionEnum.pegar_objeto){
				mapaMatrix[mapa.limY-oldY][oldX-1] = '.';
				oldC = '.';
			}
			Estado novo = Consult.getE();			
			
			Tile old = new Tile(oldX,oldY,oldC);
			Tile next = new Tile(novo.getX(),novo.getY(),novo.getD());
			
			ArrayList<Tile> mapaUpdate = new ArrayList<>();
			mapaUpdate.add(old);
			mapaUpdate.add(next);
			
			mapa.update(mapaUpdate);
			
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void updateLabel(Estado e) {
		this.label.setText("SCORE:"+e.getS()+" HEALTH:"+e.getV()+" OURO:"+e.getO()+" MUNICAO:"+e.getM());
		
	}
	public void setLabel(JLabel estadoValores) {
		this.label= estadoValores;
		
	}

}
