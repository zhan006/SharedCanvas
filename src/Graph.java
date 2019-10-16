import java.awt.Color;
import java.io.Serializable;

public class Graph implements Serializable {
	private int x1,y1,x2,y2;
	private String name;
	private Color c;
	private String text;
	private int thickness;
	public Graph(int x1,int y1,int x2,int y2,String name, Color inputs, int thickness,String text) {
		this.thickness=thickness;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.name = name;
		this.c = inputs;
		this.text = text;
	}
	//reconstruct a tool from the graph characteristic
	public Tool getTool() {
		return new Tool(this.name,this.c,this.thickness);
	}
	public int getX1() {
		return this.x1;
	}
	public int getX2() {
		return this.x2;
	}
	public int getY1() {
		return this.y1;
	}
	public int getY2() {
		return this.y2;
	}

	public String getName() {
		return this.name;
	}

	public Color getColor() {
		return this.c;
	}

	public int getThinkness() {
		return this.thickness;
	}

	public String getText() {
		return this.text;
	}
	public String toString() {
		return this.name+this.c+" at"+this.x1+","+this.x2+","+this.y1+","+","+this.y2;
	}
}
