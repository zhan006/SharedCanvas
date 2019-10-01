import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BasicStroke;
import java.awt.Canvas;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.MenuListener;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.JList;

public class PictHub extends UnicastRemoteObject implements RemoteSharedCanvas{

	private JFrame frame;
	private JMenuBar file;
	private Graphics g;
	private Tool tool = new Tool();
	private JTextArea ChatInput, chattingArea;
	private JButton SendBtn,pencil,eraser,text;
	private JPanel toolPanel,colors,canvas;
	private ArrayList<Graph> shapes = new ArrayList<Graph>();
	private ArrayList<JButton> toolBtn = new ArrayList<JButton>();
	private ArrayList<String> users_List = new ArrayList<String>();
	private Color[] Allcolor = new Color[] {Color.BLACK,Color.BLUE,Color.DARK_GRAY,Color.CYAN,Color.GREEN
			,Color.ORANGE,Color.RED,Color.PINK,Color.WHITE,Color.YELLOW,Color.MAGENTA,Color.LIGHT_GRAY};
	private JList list;
	private String username;
	/**
	 * Create the application.
	 * @return 
	 */
	/**
	 * @wbp.parser.constructor
	 */

	public PictHub() throws RemoteException{
		
		try {
			initialize();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public PictHub(String username) throws RemoteException{
		try {
			this.username = username;
			initialize();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Picthub");
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBackground(SystemColor.desktop);
		frame.setBounds(100, 100, 1200, 850);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//close window confirmation
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                     null, "Are You Sure to leave?", 
                     "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
                     JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                   System.exit(0);
                }
            }
        };
        frame.addWindowListener(exitListener);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //called system.exi(0)
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("./logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(null);
		JLabel logo = new JLabel(new ImageIcon(image));
		logo.setBounds(0, 26, 289, 94);
		frame.getContentPane().add(logo);
		
		file = new JMenuBar();
		file.setBounds(0, 0, 1182, 26);
		frame.getContentPane().add(file);
		
		JMenu mnFile = new JMenu("File");
		file.add(mnFile);
		
		JMenuItem itemNew = new JMenuItem("New");
		mnFile.add(itemNew);
		itemNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newPicture();
				//if the user is manager, clear all others board
				remoteNewPicture();
			}
			
		});
		
		JMenuItem savePicture = new JMenuItem("Save picture");
		mnFile.add(savePicture);
		savePicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("clicked save button");
				savePict("./picture.his");
			}
			
		});
		
		JMenuItem saveAs = new JMenuItem("Save as");
		mnFile.add(saveAs);
		saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = JOptionPane.showInputDialog("Input the new path");
				savePict(path);
			}
			
		});
		
		JMenuItem importPicture = new JMenuItem("Import picture");
		mnFile.add(importPicture);
		
		JMenuItem itemClose = new JMenuItem("Close");
		mnFile.add(itemClose);
		itemClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//leave();
			}
			
		});
		importPicture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Graph> history = importPict("./picture.his");
				for(Graph g:history) {
					shapes.add(g);
				}
				repaintPicture(history);
			}
		});
		
		JMenu mnNewMenu = new JMenu("New menu");
		file.add(mnNewMenu);
		
		ChatInput = new JTextArea();
		ChatInput.setBounds(0, 697, 283, 58);
		frame.getContentPane().add(ChatInput);
		ChatInput.setLineWrap(true);
		ChatInput.setWrapStyleWord(true);



		
		SendBtn = new JButton("SEND");
		SendBtn.setBounds(168, 755, 113, 35);
		SendBtn.setBackground(new Color(255, 153, 0));
		SendBtn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		frame.getContentPane().add(SendBtn);
		SendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					sendText();
				}catch (Exception a){
					a.getStackTrace();
				}
			}
		});




		
		toolPanel = new JPanel();
		toolPanel.setBounds(402, 701, 125, 98);
		frame.getContentPane().add(toolPanel);
		toolPanel.setLayout(null);
		
		pencil = new JButton("pencil");
		pencil.setIcon(new ImageIcon("./pencil.png"));
		pencil.setHorizontalTextPosition(SwingConstants.CENTER);
		pencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pencil.setBounds(14, 0, 44, 44);
		toolPanel.add(pencil);
		toolBtn.add(pencil);
		
		eraser = new JButton("eraser");
		eraser.setIcon(new ImageIcon("./eraser.png"));
		eraser.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//eraser popup menu
		JPopupMenu menu = new JPopupMenu("Menu");
		JMenuItem m1 = new JMenuItem("smallEraser");
		m1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type1 = m1.getText();
				tool.setType(type1);
//				tool.setThickness(5);
//				tool.setColor(Color.WHITE);
				System.out.println("set to "+tool.getType());
			}
		});
        JMenuItem m2 = new JMenuItem("midEraser"); 
        m2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type2 = m2.getText();
				tool.setType(type2);
//				tool.setThickness(10);
//				tool.setColor(Color.WHITE);
				System.out.println("set to "+tool.getType());
			}
		});
        JMenuItem m3 = new JMenuItem("largeEraser");
        m3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type3 = m3.getText();
//				tool.setThickness(15);
//				tool.setColor(Color.WHITE);
				tool.setType(type3);
				System.out.println("set to "+tool.getType());
			}
		});
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);

		eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.show(eraser, eraser.getWidth()/2, eraser.getHeight()/2);
			}
		});
		eraser.setBounds(14, 50, 44, 44);
		toolPanel.add(eraser);
		toolBtn.add(eraser);
		
		text = new JButton("text");
		text.setBounds(61, 0, 44, 44);
		text.setIcon(new ImageIcon("./text.png"));
		text.setHorizontalTextPosition(SwingConstants.CENTER);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolPanel.add(text);
		toolBtn.add(text);
		
		colors = new JPanel();
		colors.setBounds(931, 701, 175, 98);
		frame.getContentPane().add(colors);
		colors.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton black = new JButton("");
		black.setBackground(Color.BLACK);
		black.setContentAreaFilled(false);
		black.setOpaque(true);
		colors.add(black);
		toolBtn.add(black);
		
		JButton darkgray = new JButton("");
		darkgray.setBackground(Color.DARK_GRAY);
		darkgray.setContentAreaFilled(false);
		darkgray.setOpaque(true);
		colors.add(darkgray);
		toolBtn.add(darkgray);
		
		JButton lightGray = new JButton("");
		lightGray.setBackground(Color.LIGHT_GRAY);
		lightGray.setContentAreaFilled(false);
		lightGray.setOpaque(true);
		colors.add(lightGray);
		toolBtn.add(lightGray);
		
		JButton white = new JButton("");
		white.setBackground(Color.WHITE);
		white.setContentAreaFilled(false);
		white.setOpaque(true);
		colors.add(white);
		toolBtn.add(white);
		
		JButton yellow = new JButton("");
		yellow.setBackground(Color.YELLOW);
		yellow.setContentAreaFilled(false);
		yellow.setOpaque(true);
		colors.add(yellow);
		toolBtn.add(yellow);
		
		JButton orange = new JButton("");
		orange.setBackground(Color.ORANGE);
		orange.setContentAreaFilled(false);
		orange.setOpaque(true);
		colors.add(orange);
		toolBtn.add(orange);
		
		JButton blue = new JButton("");
		blue.setBackground(Color.BLUE);
		blue.setContentAreaFilled(false);
		blue.setOpaque(true);
		colors.add(blue);
		toolBtn.add(blue);
		
		JButton magenta = new JButton("");
		magenta.setBackground(Color.MAGENTA);
		magenta.setContentAreaFilled(false);
		magenta.setOpaque(true);
		colors.add(magenta);
		toolBtn.add(magenta);
		
		JButton pink = new JButton("");
		pink.setBackground(Color.PINK);
		pink.setContentAreaFilled(false);
		pink.setOpaque(true);
		colors.add(pink);
		toolBtn.add(pink);
		
		JButton red = new JButton("");
		red.setBackground(Color.RED);
		red.setContentAreaFilled(false);
		red.setOpaque(true);
		colors.add(red);
		toolBtn.add(red);
		
		
		JButton green = new JButton("");
		green.setBackground(Color.GREEN);
		green.setContentAreaFilled(false);
		green.setOpaque(true);
		colors.add(green);
		toolBtn.add(green);
				
		JButton cyan = new JButton("");
		cyan.setBackground(Color.CYAN);
		cyan.setContentAreaFilled(false);
		cyan.setOpaque(true);
		colors.add(cyan);
		toolBtn.add(cyan);
		
		JButton customized = new JButton("");
		Color cus = new Color(123, 111, 222);
		customized.setBackground(cus);
		customized.setContentAreaFilled(false);
		customized.setOpaque(true);
		colors.add(customized);
		toolBtn.add(customized);
		
		JButton customized1 = new JButton("");
		Color cus1 = new Color(56, 135, 77);
		customized1.setBackground(cus1);
		customized1.setContentAreaFilled(false);
		customized1.setOpaque(true);
		colors.add(customized1);
		toolBtn.add(customized1);
		
		JButton customized2 = new JButton("");
		Color cus2 = new Color(231, 135, 77);
		customized2.setBackground(cus2);
		customized2.setContentAreaFilled(false);
		customized2.setOpaque(true);
		colors.add(customized2);
		toolBtn.add(customized2);
		
		JButton customized3 = new JButton("");
		Color cus3 = new Color(66, 115, 178);
		customized3.setBackground(cus3);
		customized3.setContentAreaFilled(false);
		customized3.setOpaque(true);
		colors.add(customized3);
		toolBtn.add(customized3);
		
		JButton customized4 = new JButton("");
		Color cus4 = new Color(108, 135, 155);
		customized4.setBackground(cus4);
		customized4.setContentAreaFilled(false);
		customized4.setOpaque(true);
		colors.add(customized4);
		toolBtn.add(customized4);
		
		JButton customized5 = new JButton("");
		Color cus5 = new Color(40, 145, 20);
		customized5.setBackground(cus5);
		customized5.setContentAreaFilled(false);
		customized5.setOpaque(true);
		colors.add(customized5);
		toolBtn.add(customized5);
		
		
		JLabel toolTag = new JLabel("Tool");
		toolTag.setBounds(316, 701, 72, 98);
		toolTag.setForeground(Color.LIGHT_GRAY);
		toolTag.setBackground(Color.LIGHT_GRAY);
		toolTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		toolTag.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(toolTag);
		
		JPanel panel = new JPanel();
		panel.setBounds(661, 701, 156, 98);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton circle = new JButton("circle");
		circle.setBounds(30, 0, 44, 44);
		circle.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(circle);
		circle.setIcon(new ImageIcon("./circle.png"));
		toolBtn.add(circle);
		
		JButton line = new JButton("line");
		line.setHorizontalTextPosition(SwingConstants.CENTER);
		line.setBounds(88, 0, 44, 44);
		line.setIcon(new ImageIcon("./line.png"));
		panel.add(line);
		toolBtn.add(line);

		
		JButton oval = new JButton("oval");
		oval.setBounds(30, 54, 44, 44);
		oval.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(oval);
		oval.setIcon(new ImageIcon("./oval.png"));
		toolBtn.add(oval);
		
		JButton rect = new JButton("rect");
		rect.setBounds(88, 54, 44, 44);
		rect.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(rect);
		rect.setIcon(new ImageIcon("./rect.png"));
		toolBtn.add(rect);
		
		JLabel shapeTag = new JLabel("Shapes");
		shapeTag.setBounds(544, 701, 92, 98);
		shapeTag.setHorizontalAlignment(SwingConstants.CENTER);
		shapeTag.setForeground(Color.LIGHT_GRAY);
		shapeTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		frame.getContentPane().add(shapeTag);
		
		JLabel colorTag = new JLabel("Colors");
		colorTag.setBounds(831, 701, 86, 102);
		colorTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		colorTag.setHorizontalAlignment(SwingConstants.CENTER);
		colorTag.setForeground(Color.LIGHT_GRAY);
		frame.getContentPane().add(colorTag);
		
		chattingArea = new JTextArea(28,20);
		chattingArea.setSelectedTextColor(SystemColor.control);
		chattingArea.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		chattingArea.setForeground(SystemColor.control);
		chattingArea.setBackground(Color.DARK_GRAY);
		JScrollPane ChatWindowContainer = new JScrollPane(chattingArea);
		ChatWindowContainer.setAlignmentX(2.0f);
		ChatWindowContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ChatWindowContainer.setBackground(Color.LIGHT_GRAY);
		ChatWindowContainer.setBounds(0, 242, 287, 453);
		frame.getContentPane().add(ChatWindowContainer);
		chattingArea.setLineWrap(true);
		canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(290, 26, 892, 669);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		//add mouse listener to canvas
		g = canvas.getGraphics();
		drawListener dl = new drawListener(canvas,g,shapes,tool,this.users_List);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 121, 289, 114);
		frame.getContentPane().add(scrollPane);
		
		list = new JList();
		list.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(list);
		canvas.addMouseListener(dl);
		canvas.addMouseMotionListener(dl);
		//add button listener to tool box
		for(int i=0;i<toolBtn.size();i++) {
			toolBtn.get(i).addActionListener(new toolButtonListener(tool));
		}
	}

	@Override
	public void login(String username) throws RemoteException {
		// TODO Auto-generated method stub
		this.users_List.add(username);
	}
	

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		Color c = tool.getColor();
		graph.setColor(c);			
		graph.drawLine(x1, y1, x2, y2);
		
	}

	@Override
	public void drawOval(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		Color c1 = tool.getColor();
		graph.setColor(c1);
		graph.drawOval(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
	}

	@Override
	public void drawString(String text, int x1, int y1, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		Color c1 = tool.getColor();
		graph.setColor(c1);
		graph.drawString(text, x1, y1);
		
	}

	@Override
	public void drawRect(int x1, int y1, int x2, int y2,Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		Color c2 = tool.getColor();
		graph.setColor(c2);
		graph.drawRect(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
	}

	@Override
	public ArrayList<String> getUserList() throws RemoteException {
		// TODO Auto-generated method stub
		return this.users_List;
	}

	@Override
	public void setUserList(ArrayList<String> temp) throws RemoteException {
		System.out.println("setUserList thinks the input size is: "+shapes.size());
		// TODO Auto-generated method stub
		for(String username:temp) {
			this.users_List.add(username);
		}
	}

	@Override
	public ArrayList<Graph> getShapes() throws RemoteException {
		// TODO Auto-generated method stub
//		System.out.println("getShapes size is: "+this.shapes.size());
		return this.shapes;
	}
	public void setShapes(ArrayList<Graph> shapes) {
		System.out.println("setShapes size is: "+this.shapes.size());
		this.shapes = shapes;
	}

	@Override
	public void AddShapes(Graph shape) throws RemoteException {
		// TODO Auto-generated method stub
		this.shapes.add(shape);
		
	}

	@Override
	public void drawEraser(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		graph.setColor(Color.WHITE);			
		graph.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawSmallEraser(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(5));
		graph.setColor(Color.WHITE);			
		graph.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawMediumEraser(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(10));
		graph.setColor(Color.WHITE);			
		graph.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawLargeEraser(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(15));
		graph.setColor(Color.WHITE);			
		graph.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawCircle(int x1, int y1, int x2, int y2, Tool tool) throws RemoteException {
		// TODO Auto-generated method stub
		Graphics2D graph = (Graphics2D)this.g;
		graph.setStroke(new BasicStroke(tool.getThickness()));
		Color c1 = tool.getColor();
		graph.setColor(c1);
		graph.drawOval(Math.min(x1,x2),Math.min(y1,y2),(Math.abs(x1-x2)+Math.abs(x1-x2))/2,(Math.abs(x1-x2)+Math.abs(x1-x2))/2);
	}
	@Override
	public void sendText() throws RemoteException {
		String text = this.ChatInput.getText();
		String newText = this.username + ":\n" + text+"\n";
		this.ChatInput.setText("");


		for(String user:users_List) {
			RemoteSharedCanvas remoteHub;
			try {
				Registry registry = LocateRegistry.getRegistry("localhost");
				remoteHub = (RemoteSharedCanvas) registry.lookup(user);
				//		this.chattingArea.setText(newText);
				remoteHub.setChattingArea(newText);
			} catch (Exception a) {
				a.getStackTrace();
			}
		}
	}
	public void setChattingArea(String text) throws RemoteException{
		this.chattingArea.append(text);
	}
	// save current edit history of picture into a local path
	public void savePict(String path) {
		FileOutputStream file;
		try {
			file = new FileOutputStream(path);
			ObjectOutputStream writer = new ObjectOutputStream(file);
			writer.writeObject(this.shapes);
			JOptionPane.showMessageDialog(this.frame, "Saved!");
			
		} 
		catch(FileNotFoundException e1) {
			JOptionPane.showMessageDialog(this.frame, "File Not Found!");
			e1.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this.frame, "Error occured");
			e.printStackTrace();
		}
	}
	public ArrayList<Graph> importPict(String path) {
		FileInputStream file;
		ArrayList<Graph> shapes = null;
		try {
			file = new FileInputStream(path);
			ObjectInputStream reader = new ObjectInputStream(file);
			try {
				shapes = (ArrayList<Graph>) reader.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch(FileNotFoundException e1) {
			JOptionPane.showMessageDialog(this.frame, "File Not Found!");
			e1.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this.frame, "Error occured");
			e.printStackTrace();
		}
		return shapes;
	}
	@Override
	public void newPicture() {
		this.canvas.repaint();
		this.shapes.clear();
	}
	public void remoteNewPicture() {
		for(String user:users_List) {
			RemoteSharedCanvas remoteHub;
			try {
				Registry registry = LocateRegistry.getRegistry("localhost");
				remoteHub = (RemoteSharedCanvas) registry.lookup(user);
				//		this.chattingArea.setText(newText);
				remoteHub.newPicture();
			} catch (Exception a) {
				a.getStackTrace();
			}
		}
	}
	public void repaintPicture(ArrayList<Graph> shapes) {
		for(Graph graph:shapes) {
			Tool tool = graph.getTool();
			int x1 = graph.getX1();
			int y1 = graph.getY1();
			int x2 = graph.getX2();
			int y2 = graph.getY2();
			switch(tool.getType()) {
			case "pencil":
				try {
					this.drawLine(x1,y1,x2,y2,tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "line":
				try {
					this.drawLine(x1, y1, x2, y2, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "text":
				String text = graph.getText();
				try {
					this.drawString(text, x1, y1, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "circle":
				try {
					this.drawCircle(x1, y1, x2, y2, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Oval":
				try {
					this.drawOval(x1, y1, x2, y2, tool);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "eraser":
				try {
					this.drawEraser(x1, y1, x2, y2, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "smallEraser":
				try {
					this.drawSmallEraser(x1, y1, x2, y2, tool);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "midEraser":
				try {
					this.drawMediumEraser(x1, y1, x2, y2, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "largeEraser":
				try {
					this.drawLargeEraser(x1, y1, x2, y2, tool);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
