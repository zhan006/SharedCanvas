import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class testFreehand {

  public static void main(String[] args) {
    JFrame aWindow = new JFrame();
    aWindow.setBounds(200, 200, 200, 200);
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container content = aWindow.getContentPane();
    content.add(new MouseMotionEvents());
    aWindow.setVisible(true);
  }
}

class MouseMotionEvents extends JPanel implements MouseListener,
    MouseMotionListener {
  Point p;

  public MouseMotionEvents() {
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void mouseClicked(MouseEvent me) {
  }

  public void mouseEntered(MouseEvent me) {
  }

  public void mouseExited(MouseEvent me) {
  }

  public void mousePressed(MouseEvent me) {
    p = me.getPoint();
    repaint();
  }

  public void mouseReleased(MouseEvent me) {
    p = null;
    repaint();
  }

  public void mouseDragged(MouseEvent me) {
    p = me.getPoint();
    repaint();
  }

  public void mouseMoved(MouseEvent me) {
  }

  public void paint(Graphics g) {
    if (p != null) {
      Dimension d = getSize();
      
      //unused centre line
      int xc = d.width / 2;
      int yc = d.height / 2;
      
      //a trick way to draw points(a line start and end at the same position)
      g.drawLine(p.x, p.y, p.x, p.y);
    }
  }
}