import java.awt.Color;
import java.io.Serializable;

public class Graph implements Serializable {
	private int x1,y1,x2,y2;
	private String name;
	private Color c;
	private String text;
	public Graph(int x1,int y1,int x2,int y2,String name, Color inputs, String text) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.name = name;
		this.c = inputs;
		this.text = text;
	}
}
