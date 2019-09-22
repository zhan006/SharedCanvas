import java.awt.Color;

public class Graph {
	private int x1,y1,x2,y2;
	private String name;
	private Color c;
	public Graph(int x1,int y1,int x2,int y2,String name, Color inputs) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.name = name;
		this.c = inputs;
	}
}
