import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
			if(type.equals("eraser")) {
				JPopupMenu menu = new JPopupMenu("Menu");
				JMenuItem m1 = new JMenuItem("smallEraser");
				m1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String type1 = m1.getText();
						tool.setType(type1);
						System.out.println("set to "+tool.getType());
					}
				});
		        JMenuItem m2 = new JMenuItem("midEraser"); 
		        m2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String type2 = m2.getText();
						tool.setType(type2);
						System.out.println("set to "+tool.getType());
					}
				});
		        JMenuItem m3 = new JMenuItem("largeEraser");
		        m3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String type3 = m3.getText();
						tool.setType(type3);
						System.out.println("set to "+tool.getType());
					}
				});
		        menu.add(m1);
		        menu.add(m2);
		        menu.add(m3);
		        menu.show(source, source.getWidth()/2, source.getHeight()/2);	
		        
				tool.setType(type);
				System.out.println("set to "+tool.getType());
			}
			else {
				tool.setType(type);
				System.out.println("set to "+tool.getType());
			}
		}
		
	}

}
