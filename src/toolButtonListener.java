import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
public class toolButtonListener implements ActionListener {
	private Tool tool;
	public toolButtonListener(Tool tool) {
		super();
		this.tool = tool;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source =  (JButton)e.getSource();
		String type = source.getText();
		System.out.println(type);
		//if the button is a color button, set color of tools
		if(type.equals("")) {
			Color color = source.getBackground();
			tool.setColor(color);
			System.out.println("tool has been set to color "+color.toString());
		}
		//if the button is a tool button
		else {
			tool.setType(type);
			System.out.println("set to "+tool.getType());
		}
		
	}

}
