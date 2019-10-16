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
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<String,ArrayList<String>> usersList;
	private ArrayList<Graph> shapes;
	public drawListener(JPanel canvas, Graphics g,ArrayList<Graph> shapes,Tool tool,HashMap<String,ArrayList<String>> usersList) {
		this.canvas = canvas;
		this.usersList = usersList;
		this.graph = (Graphics2D)g;
		this.shapes = shapes;
		this.tool=tool;
	}
	public void setUserList(HashMap<String,ArrayList<String>> list) {
		this.usersList = list;
	}
	private Registry getUserRegistry(HashMap<String,ArrayList<String>> temp,String username) throws RemoteException {
		String userhost = temp.get(username).get(0);
		int userport = Integer.parseInt(temp.get(username).get(1));
		Registry useregistry = LocateRegistry.getRegistry(userhost, userport);
		return useregistry;
	}
	private HashMap<String,ArrayList<String>> getUserList() throws RemoteException, NotBoundException{
		String managername = "SharedCanvasManager";
		String host = this.usersList.get(managername).get(0);
		int port = Integer.parseInt(this.usersList.get(managername).get(1));
		Registry registry = LocateRegistry.getRegistry(host,port);
		RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
		HashMap<String,ArrayList<String>> temp = manager.getUserList();
		return temp;
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
				try {
					HashMap<String,ArrayList<String>>temp = this.getUserList();

					for(String user:temp.keySet()) {
						RemoteSharedCanvas remoteHub;
						try {
							Registry useregistry = this.getUserRegistry(temp, user);
							remoteHub = (RemoteSharedCanvas) useregistry.lookup(user);
							remoteHub.drawLine(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "line",tool.getColor(),tool.getThickness(), "not text"));

						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}


					}
					x1=x2;
					y1=y2;
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}


//				System.out.print("after add the shape size is: "+this.shapes.size());

				break;
			case "eraser":

				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();
//					System.out.println("User list size is: "+usersList.size());
					for(String user:temp.keySet()) {
						RemoteSharedCanvas remoteHub;
						try {
							Registry useregistry = this.getUserRegistry(temp, user);
							remoteHub = (RemoteSharedCanvas) useregistry.lookup(user);
							remoteHub.drawEraser(x1, y1, x2, y2, tool);

							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "eraser",Color.WHITE,tool.getThickness(), "not text"));

//							System.out.println(shapes.size());
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (RemoteException | NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				x1=x2;
				y1=y2;


//				System.out.print("after add the shape size is: "+this.shapes.size());

				break;
			case "smallEraser":
				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();
//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {

						RemoteSharedCanvas remoteHub;
						try {
							Registry useregistry = this.getUserRegistry(temp, user);
							remoteHub = (RemoteSharedCanvas) useregistry.lookup(user);
							remoteHub.drawSmallEraser(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "smallEraser",Color.WHITE,tool.getThickness(), "not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
					x1=x2;
					y1=y2;
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}


//				System.out.print("after add the shape size is: "+this.shapes.size());

				break;
			case "midEraser":
				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();
					for(String user:temp.keySet()) {
						RemoteSharedCanvas remoteHub;
						try {
							Registry registry = this.getUserRegistry(temp, user);
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawMediumEraser(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "midEraser",Color.WHITE,tool.getThickness(), "not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
					x1=x2;
					y1=y2;
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}

//				System.out.print("after add the shape size is: "+this.shapes.size());

				break;
			case "largeEraser":
				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();
//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {

						RemoteSharedCanvas remoteHub;
						try {
							Registry registry = this.getUserRegistry(temp, user);
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawLargeEraser(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "largeEraser",Color.WHITE,tool.getThickness(), "not text"));

						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
					x1=x2;
					y1=y2;
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
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
			try {
				HashMap<String,ArrayList<String>> temp = this.getUserList();
//				System.out.println("User list size is: "+usersList.size());

				Map<Point, String> pointTextMap = new LinkedHashMap<>();
				String prompt = "Please add text to display";
		        String input = JOptionPane.showInputDialog(canvas, prompt);
		        pointTextMap.put(e.getPoint(), input);

				for(String user:temp.keySet()) {

					RemoteSharedCanvas remoteHub;
					try {
						Registry registry = this.getUserRegistry(temp, user);
						remoteHub = (RemoteSharedCanvas) registry.lookup(user);

						for (Point p : pointTextMap.keySet()) {
				             String text = pointTextMap.get(p);
							 if (text!= null) {

								 remoteHub.drawString(text, p.x, p.y, tool);
								 remoteHub.AddShapes(new Graph(p.x, p.y, p.x, p.y, "text",tool.getColor(),tool.getThickness(), text));
							 }

				         }
					}
					catch (ConnectException e5) {
//						System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
						JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//						System.exit(0);
					}
					catch (NotBoundException e2) {
						System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
						JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
					}
					catch (RemoteException k) {
						// TODO Auto-generated catch block
						System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
						JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
					}
				}
			}
			catch (ConnectException e5) {
				System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
				JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
				System.exit(0);
			}
			catch (NotBoundException e2) {
				System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
				JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
			}
			catch (RemoteException k) {
				// TODO Auto-generated catch block
				System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
				JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
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
				try {
					HashMap<String,ArrayList<String>> temp =this.getUserList();

//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {
						Registry registry = this.getUserRegistry(temp, user);
						RemoteSharedCanvas remoteHub;
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawLine(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "line",tool.getColor(),tool.getThickness(), "not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}

//				System.out.print("after add the shape size is: "+this.shapes.size());


				break;
			case "oval":
				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();

//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {
						
						RemoteSharedCanvas remoteHub;
						Registry registry = this.getUserRegistry(temp, user);
						try {
							
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawOval(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1, y1, x2, y2, "oval",tool.getColor(),tool.getThickness(), "not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				break;
			case "rect":
				try {
					HashMap<String,ArrayList<String>> temp =this.getUserList();

//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {

						RemoteSharedCanvas remoteHub;
						Registry registry = this.getUserRegistry(temp, user);
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawRect(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1,y1,x2,y2,"rect",tool.getColor(), tool.getThickness(),"not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				break;
			case "circle":
				try {
					HashMap<String,ArrayList<String>> temp = this.getUserList();

//					System.out.println("User list size is: "+usersList.size());

					for(String user:temp.keySet()) {
						Registry registry = this.getUserRegistry(temp, user);
						RemoteSharedCanvas remoteHub;
						try {
							remoteHub = (RemoteSharedCanvas) registry.lookup(user);
							remoteHub.drawCircle(x1, y1, x2, y2, tool);
							remoteHub.AddShapes(new Graph(x1,y1,x2,y2,"circle",tool.getColor(),tool.getThickness(), "not text"));
						}
						catch (ConnectException e5) {
//							System.out.println("Seems like someone's program get terminated by accident. So you failed to draw");
							JOptionPane.showMessageDialog(null, "Seems like someone's program get terminated by accident. So you failed to draw");
//							System.exit(0);
						}
						catch (NotBoundException e2) {
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
						catch (RemoteException k) {
							// TODO Auto-generated catch block
							System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
							JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
						}
					}
				}
				catch (ConnectException e5) {
					System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
					JOptionPane.showMessageDialog(null, "Seems like you failed to connect to the RMI register. You window will be closed");
					System.exit(0);
				}
				catch (NotBoundException e2) {
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}
				catch (RemoteException k) {
					// TODO Auto-generated catch block
					System.out.println("RMI Crashed already. Please stop drawing and close the useless window");
					JOptionPane.showMessageDialog(null, "RMI Crashed already. Please stop drawing and close the useless window");
				}

//				System.out.print("after add the shape size is: "+this.shapes.size());

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
