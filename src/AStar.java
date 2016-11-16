import java.util.ArrayList;

/**
 * Classe que vai rodar o AStar para achar o safe mais proximo.
 * Vai possuir uma lista de todos os safes(X,Y) conhecidos pelo usuário.
 * Vai receber o estado inicial. O Node será (X,Y,D) onde D é a direçao:
 * 0-norte,1-leste,2-sul,3-oeste. Deste modo é só somar 1 na direção com 
 * wrap-around. Vai retornar uma lista de booleans onde true será anda pra
 * frente e false será vira. Heuristica será distância de manhattan pro safe
 * mais próximo. Safe será do mesmo tipo Node, porém a direção não irá importar.
 * Expandir uma fronteira será expandir só 2 nós: andar pra frente e virar à direita.
 * @author Guilherme Simas
 *
 */
public class AStar {
	
	private int limX;
	private int limY;
	private char c;
	private char mapa[][];
	
	
	private int visitedCost[][][];
	private ArrayList<Node> safe;
	private ArrayList<Node> borders;
	private Node currentBest;
	
	
	public ArrayList<Action> findPath(int x,int y,int d){
		/*
		 * visitedCost inicializado como -1 pra saber que
		 * "não foi visitado ainda"
		 */
		setSafe();
		visitedCost = new int[limY][limX][4];
		for(int i=0; i<limY;i++){
			for(int j=0;j<limX;j++){
				for(int k=0;k<4;k++){
					visitedCost[i][j][k]=-1;
				}
			}
		}
		this.borders = new ArrayList<>();
		borders.add(new Node(x,y,d));
		/*
		 * Loop da busca. A busca deve continuar mesmo depois de
		 * encontrar um dos destinos, pois pode haver um destino
		 * "mais barato" ainda. Deve-se levar isso em consideração.
		 */
		while(!borders.isEmpty()){ 
			currentBest=borders.get(0);
			int minCost = borders.get(0).getCost()+calc_heuristica(currentBest.getX(),
																	currentBest.getY());
			/*
			 * Se o currentBest for um safe então eu não quero expandir ele, pois se ele
			 * fosse realmente o melhor ele já seria o currentBest, e nenhuma outra fronteira
			 * irá gerar um Node melhor. Do contrário, o currentBest será esse novo melhor.
			 * Portanto eu só preciso não expandir o currentBest caso ele seja de fato o melhor.
			 */
			for(Node n:borders){
				/*
				 * Encontra o novo currentBest
				 */
				if(n.getCost()+calc_heuristica(n.getX(),n.getY())<minCost){
					minCost = n.getCost()+calc_heuristica(n.getX(),n.getY());
					currentBest = n;
				}
			}
			borders.remove(currentBest);
			expand(currentBest);
			System.out.println("CurrentBest:"+currentBest.getX()+","+currentBest.getY());
		}
		return toAction(currentBest.getCommands());
	}
	/**
	 * Roda o mapa passado inteiro e cria uma lista com 
	 * os Nodes que são destinos
	 */
	private void setSafe(){
		safe = new ArrayList<Node>();
		for(int i=limY-1;i>=0;i--){
			for(int j=0;j<limX;j++){
				if(mapa[i][j]==c){
					safe.add(new Node(j+1,i+1,0));
				}
			}
		}
	}
	private ArrayList<Action> toAction(ArrayList<Boolean> commands) {
		ArrayList<Action> actionList = new ArrayList<>();
		for(Boolean b:commands){
			if(b){
				actionList.add(Action.mover_para_frente);
			}else{
				actionList.add(Action.virar_a_direita);
			}
		}
		return actionList;
	}
	private void expand(Node n) {
		// TODO Auto-generated method stub
		Node anda = anda(n);
		if(isValid(anda)){
			borders.add(anda);
		}
		Node vira = vira(n);
		
	}
	private Node vira(Node n) {
		// TODO Auto-generated method stub
		Node vira = new Node(n.getX(),n.getY(),(n.getD()+1)%4);
		vira.setCost(n.getCost()+1);
		vira.setCommands(n.getCommands());
		vira.addCommand(Boolean.FALSE);
		return vira;
	}
	private Node anda(Node n) {
		Node novo=new Node(0,0,0);
		switch(n.getD()){
		case 0:
			//Norte
			novo=new Node(n.getX(),n.getY()+1,n.getD());
			break;
		case 1:
			//Leste
			novo=new Node(n.getX()+1,n.getY(),n.getD());
			break;
			
		case 2:
			//Sul
			novo=new Node(n.getX(),n.getY()-1,n.getD());
			break;
		case 3:
			//Oeste
			novo=new Node(n.getX()-1,n.getY(),n.getD());
			break;
		}
		novo.setCost(n.getCost()+1);
		novo.setCommands(n.getCommands());
		novo.addCommand(Boolean.TRUE);
		return novo;
	}
	/**
	 * Checa se o Node não saiu dos limites ou é uma casa
	 * que não gostaríamos de estar
	 * @param novo
	 * @return
	 */
	private boolean isValid(Node novo) {
		// TODO Auto-generated method stub
		if(novo.getX()<=0 || novo.getX()>limX){
			return false;
		}
		if(novo.getY()<=0 || novo.getY()>limY){
			return false;
		}
		char c = mapa[novo.getY()-1][novo.getX()-1];
		/*
		 * Se a casa no mapa não é visitada ou não é safe
		 * ou não é a saída ou não é um "destino"
		 */
		if(    c!='.' 
			&& c!='x' 
			&& c!='U' 
			&& c!=this.c){
			return false;
		}
		int currentCost = visitedCost[novo.getY()-1][novo.getX()-1][novo.getD()];
		if(currentCost==-1){
			visitedCost[novo.getY()-1][novo.getX()-1][novo.getD()] = novo.getCost();
		}else if(novo.getCost()>=currentCost){
			return false;
		}
		return true;
	}
	/**
	 * Esse metodo deve iterar sobre toda a lista de safe
	 * e retornar a menor distancia de manhattan para um
	 * deles
	 * @param x
	 * @param y
	 * @return
	 */
	private int calc_heuristica(int x, int y) {
		// TODO Auto-generated method stub
		int minCost = manhattanDistance(x,y,safe.get(0).getX(),safe.get(0).getY());
		for(Node n:safe){
			int cost = manhattanDistance(x,y,n.getX(),n.getY());
			if(cost<minCost){
				minCost = cost;
			}
		}
		return minCost;
	}
	private int manhattanDistance(int x, int y, int x2, int y2) {
		return Math.abs(x-x2)+Math.abs(y-y2);
	}
	public void setLimX(int limX2) {
		// TODO Auto-generated method stub
		this.limX=limX2;
		
	}
	public void setC(char d) {
		// TODO Auto-generated method stub
		this.c=d;
		
	}
	public void setMapa(char[][] mapaMentalMatrix) {
		// TODO Auto-generated method stub
		this.mapa=mapaMentalMatrix;
	}
	public void setLimY(int limY2) {
		// TODO Auto-generated method stub
		this.limY=limY2;
	}
	public static int DtoInt(String d) {
		// TODO Auto-generated method stub
		switch (d.trim()){
		case "norte":
			return 0;
		case "leste":
			return 1;
		case "sul":
			return 2;
		case "oeste":
			return 3;
		}
		return -1;
	}
}
