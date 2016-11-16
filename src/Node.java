import java.util.ArrayList;

public class Node {
	private int X;
	private int Y;
	private int D;
	private ArrayList<Boolean> commands;
	private int cost;
	public Node (int X, int Y, int D){
		this.X=X;
		this.Y=Y;
		this.D=D;
		this.commands=new ArrayList<>();
		this.cost=0;
	}
	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public int getD(){
		return this.D;
	}
	public int getCost(){
		return this.cost;
	}
	public void setCost(int cost){
		this.cost=cost;
	}
	public void setCommands(ArrayList<Boolean> toCopy){
		for(Boolean b:toCopy){
			this.commands.add(b);
		}
	}
	public ArrayList<Boolean> getCommands() {
		// TODO Auto-generated method stub
		return this.commands;
	}
	public void addCommand(Boolean b) {
		// TODO Auto-generated method stub
		this.commands.add(b);
	}
}
