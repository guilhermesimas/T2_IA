
public class Estado {
	
	private int X, Y, S, V, M, O;
	private String D;
	
	public Estado(String estado){
		
		String terms = estado.substring(7, estado.length()-1).trim();
		System.out.println(terms);
		String [] term = terms.split(",");
		
		this.X = Integer.valueOf(term[0].trim());
		this.Y = Integer.valueOf(term[1].trim());
		this.D = term[2];
		this.S = Integer.valueOf(term[3].trim());
		this.V = Integer.valueOf(term[4].trim());
		this.M = Integer.valueOf(term[5].trim());
		this.O = Integer.valueOf(term[6].trim());
		
	}
	
	public Estado(int X,int Y,String D,int S,int V,int M,int O){
		
		this.X = X;
		this.Y = Y;
		this.D = D;
		this.S = S;
		this.V = V;
		this.M = M;
		this.O = O;
		
	}
	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public String getD(){
		return D;
	}
	
	public int getS(){
		return S;
	}
	
	public int getV(){
		return V;
	}
	
	public int getM(){
		return M;
	}
	
	public int getO(){
		return O;
	}
	
	public String toString(){
		return "estado(" + this.getX() + "," + this.getY() + "," + this.getD() + 
				"," + this.getS() + "," + this.getV() + "," + this.getM() + "," + this.getO();
	}

}
