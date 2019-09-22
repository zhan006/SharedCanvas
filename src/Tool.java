import java.awt.Color;
import java.io.Serializable;
public class Tool implements Serializable {
	private String type = "pencil"; //default tool
	private Color color = Color.black; //default color
	private int thickness = 3; //default thickness of tool is 1
	public Tool() {
		
	}
	public Tool(String type) {
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public Color getColor() {
		return color;
	}
	public int getThickness() {
		return this.thickness;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setThickness(int thick) {
		this.thickness = thick;
	}
	public boolean equals(String toolType) {
		return this.type.equalsIgnoreCase(toolType);
	}
}
