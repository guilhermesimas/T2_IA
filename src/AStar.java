import java.util.ArrayList;

/**
 * Classe que vai rodar o AStar para achar o safe mais proximo.
 * Vai possuir uma lista de todos os safes(X,Y) conhecidos pelo usu�rio.
 * Vai receber o estado inicial. O Node ser� (X,Y,D) onde D � a dire�ao:
 * 0-norte,1-leste,2-sul,3-oeste. Deste modo � s� somar 1 na dire��o com 
 * wrap-around. Vai retornar uma lista de booleans onde true ser� anda pra
 * frente e false ser� vira. Heuristica ser� dist�ncia de manhattan pro safe
 * mais pr�ximo. Safe ser� do mesmo tipo Node, por�m a dire��o n�o ir� importar.
 * Expandir uma fronteira ser� expandir s� 2 n�s: andar pra frente e virar � direita.
 * @author Guilherme Simas
 *
 */
public class AStar {
	private ArrayList<Node> safe;
	private ArrayList<Node> borders;
	private Node currentBest;
	private int limX;
	private int limY;
	
	private int visitedCost[][][];
	private char mapa[][];
	
	public ArrayList<Boolean> findPath(int x,int y,int d){
		/*
		 * visitedCost inicializado como -1 pra saber que
		 * "n�o foi visitado ainda"
		 */
		for(int i=0; i<limY;i++){
			for(int j=0;j<limX;j++){
				for(int k=0;j<4;k++){
					visitedCost[j][i][k]=-1;
				}
			}
		}
		this.borders = new ArrayList<>();
		borders.add(new Node(x,y,d));
		boolean found = false;
		/*
		 * Loop da busca. A busca deve continuar mesmo depois de
		 * encontrar um dos destinos, pois pode haver um destino
		 * "mais barato" ainda. Deve-se levar isso em considera��o.
		 */
		while(!borders.isEmpty()){ 
			currentBest=borders.get(0);
			int minCost = borders.get(0).getCost()+calc_heuristica(currentBest.getX(),
																	currentBest.getY());
			/*
			 * Se o currentBest for um safe ent�o eu n�o quero expandir ele, pois se ele
			 * fosse realmente o melhor ele j� seria o currentBest, e nenhuma outra fronteira
			 * ir� gerar um Node melhor. Do contr�rio, o currentBest ser� esse novo melhor.
			 * Portanto eu s� preciso n�o expandir o currentBest caso ele seja de fato o melhor.
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
		}
		return currentBest.getCommands();
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
	 * Checa se o Node n�o saiu dos limites ou � uma casa
	 * que n�o gostar�amos de estar
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
		 * Se a casa no mapa n�o � visitada ou n�o � safe
		 * ou n�o � a sa�da ou n�o � um "destino"
		 */
		if(    c!='.' 
			&& c!='x' 
			&& c!='U' 
			&& c!=mapa[safe.get(0).getY()-1][safe.get(0).getX()-1]){
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
}
