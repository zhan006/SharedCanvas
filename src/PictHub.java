import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import java.awt.Canvas;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

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

public class PictHub {

	private JFrame frame;
	private JMenuBar file;
	private Tool tool = new Tool();
	private JTextArea ChatInput;
	private JButton SendBtn,pencil,eraser,text;
	private JPanel toolPanel,colors,canvas;
	private ArrayList<Graph> shapes = new ArrayList<Graph>();
	private ArrayList<JButton> toolBtn = new ArrayList<JButton>();
	private Color[] Allcolor = new Color[] {Color.BLACK,Color.BLUE,Color.DARK_GRAY,Color.CYAN,Color.GREEN
			,Color.ORANGE,Color.RED,Color.PINK,Color.WHITE,Color.YELLOW,Color.MAGENTA,Color.LIGHT_GRAY};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PictHub() {
		initialize();
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
		
		ChatInput = new JTextArea();
		ChatInput.setBounds(0, 697, 283, 58);
		frame.getContentPane().add(ChatInput);
		
		SendBtn = new JButton("SEND");
		SendBtn.setBounds(168, 755, 113, 35);
		SendBtn.setBackground(new Color(255, 153, 0));
		SendBtn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		frame.getContentPane().add(SendBtn);
		
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
		eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		JTextArea chattingArea = new JTextArea(28,20);
		chattingArea.setSelectedTextColor(SystemColor.control);
		chattingArea.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		chattingArea.setForeground(SystemColor.control);
		chattingArea.setText("hello \n hello hello \nhello \nhello \nhello \nhello \nhello \nhello \nhello \n \n  \n\n\n\n\n\n\n\n\nhello\n\\nnnn\n\n\n\n\n\n\n\n\n\nhellow\n");
		chattingArea.setBackground(Color.DARK_GRAY);
		JScrollPane ChatWindowContainer = new JScrollPane(chattingArea);
		ChatWindowContainer.setAlignmentX(2.0f);
		ChatWindowContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ChatWindowContainer.setBackground(Color.LIGHT_GRAY);
		ChatWindowContainer.setBounds(0, 120, 287, 575);
		frame.getContentPane().add(ChatWindowContainer);
		
		canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(290, 26, 892, 669);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		//add mouse listener to canvas
		Graphics g = canvas.getGraphics();
		
		//make the canvas antialiasing on
		RenderingHints rhints = ((Graphics2D) g).getRenderingHints();
	    boolean antialiasOn = rhints.containsValue(RenderingHints.VALUE_ANTIALIAS_ON);
	    System.out.println(antialiasOn);
	    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		drawListener dl = new drawListener(canvas,g,shapes,tool);
		canvas.addMouseListener(dl);
		canvas.addMouseMotionListener(dl);
		//add button listener to tool box
		for(int i=0;i<toolBtn.size();i++) {
			toolBtn.get(i).addActionListener(new toolButtonListener(tool));
		}
	}
}
