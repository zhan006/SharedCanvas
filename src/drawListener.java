import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.BasicStroke;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class drawListener implements MouseListener,MouseMotionListener{
	private JPanel canvas;
	private Graphics2D graph;
	private Color color = Color.black; //default color is black
	private Tool tool;
	private int x1,y1,x2,y2; //starting point(x1,y1) ending point(x2,y2)
	private ArrayList<String> usersList;
	private ArrayList<Graph> shapes;
	public drawListener(JPanel canvas, Graphics g,ArrayList<Graph> shapes,Tool tool,ArrayList<String> usersList) {
		this.canvas = canvas;
		this.usersList = usersList;
		this.graph = (Graphics2D)g;
		this.shapes = shapes;
		this.tool=tool;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX();
		y2 = e.getY();
//		System.out.println("x1 is:"+ x1);System.out.println("y1 is:"+y1);System.out.println("x2 is:"+x2);System.out.println("y2 is:"+y2);
//		x1=x2;
//		y1=y2;
		assert tool!=null;
		assert graph!=null;
		switch(tool.getType()) {
			case "pencil":				
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
//							Registry registry = LocateRegistry.getRegistry("localhost");
//							System.out.print(registry.lookup(user).getClass());
//							System.out.print(registry.lookup(user));
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawLine(x1, y1, x2, y2, tool);
							
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "line",tool.getColor(), "not text"));
							
//							System.out.println(shapes.size());
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						
					}
					x1=x2;
					y1=y2;
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				break;
			case "eraser":
				
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					System.out.println("User list size is: "+usersList.size());
					for(String user:temp) {
						RemoteSharedCanvas remoteHub;
						try {
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawEraser(x1, y1, x2, y2, tool);
							
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "eraser",Color.WHITE, "not text"));
							
//							System.out.println(shapes.size());
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						remoteHub = (RemoteSharedCanvas) registry.lookup(user);
						remoteHub.drawLine(x1, y1, x2, y2, tool);
						System.out.println(shapes.size());
					}
				}
				
					catch (RemoteException | NotBoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				x1=x2;
				y1=y2;
				
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				break;
			case "smallEraser":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawSmallEraser(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "smallEraser",Color.WHITE, "not text"));
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					x1=x2;
					y1=y2;
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				break;
			case "midEraser":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawMediumEraser(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "midEraser",Color.WHITE, "not text"));
						} catch (RemoteException | NotBoundException e2) {
							e2.printStackTrace();
						}
					}
					x1=x2;
					y1=y2;
				} catch (RemoteException | NotBoundException e1) {
					e1.printStackTrace();
				}
				
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				break;
			case "largeEraser":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawLargeEraser(x1, y1, x2, y2, tool);					
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "largeEraser",Color.WHITE, "not text"));
							
						} catch (RemoteException | NotBoundException e2) {
							e2.printStackTrace();
						}
					}
					x1=x2;
					y1=y2;
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x1 = e.getX();
		this.y1 = e.getY();	
		switch(this.tool.getType()) {
		case "text":
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry();
				RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
				ArrayList<String> temp = manager.getUserList();
				System.out.println("User list size is: "+usersList.size());
				
				Map<Point, String> pointTextMap = new LinkedHashMap<>();
				String prompt = "Please add text to display";
		        String input = JOptionPane.showInputDialog(canvas, prompt);
		        pointTextMap.put(e.getPoint(), input);
		        
				for(String user:temp) {
					
					RemoteSharedCanvas remoteHub;
					try {
						
						remoteHub = (RemoteSharedCanvas) registry.lookup(user);
						
						for (Point p : pointTextMap.keySet()) {
				             String text = pointTextMap.get(p);
							 if (text!= null) {
								 
								 remoteHub.drawString(text, p.x, p.y, tool);
								 remoteHub.AddShapes(new Graph(p.x, p.y, p.x, p.y, "text",tool.getColor(), "is text"));
							 }
				             
				         }
					} catch (RemoteException | NotBoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x2 = e.getX();
		this.y2 = e.getY();
		assert this.tool!=null;
		switch(this.tool.getType()) {
			case "line":
								
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawLine(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "line",tool.getColor(), "not text"));
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				
				break;
			case "oval":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);				
							remoteHub.drawOval(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "oval",tool.getColor(), "not text"));
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "rect":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawRect(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2),"rect",tool.getColor(), "not text"));
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "circle":
				try {
					registry = LocateRegistry.getRegistry();
					RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
					ArrayList<String> temp = manager.getUserList();
					
					System.out.println("User list size is: "+usersList.size());
					
					for(String user:temp) {
						
						RemoteSharedCanvas remoteHub;
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);	
							remoteHub.drawCircle(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(Math.min(x1,x2),Math.min(y1,y2),(Math.abs(x1-x2)+Math.abs(x1-x2))/2,(Math.abs(x1-x2)+Math.abs(x1-x2))/2,"circle",tool.getColor(), "not text"));
						} catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.print("after add the shape size is: "+this.shapes.size());
				
				break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
