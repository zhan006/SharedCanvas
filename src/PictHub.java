import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import java.awt.Canvas;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;

public class PictHub {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					window.frame.setVisible(true);
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("./logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel logo = new JLabel(new ImageIcon(image));
		logo.setBounds(0, 26, 289, 94);
		frame.getContentPane().add(logo);
		
		JMenuBar file = new JMenuBar();
		file.setBounds(0, 0, 1182, 26);
		frame.getContentPane().add(file);
		
		JPanel ChatWindow = new JPanel();
		ChatWindow.setBackground(Color.GRAY);
		ChatWindow.setBounds(0, 122, 283, 573);
		frame.getContentPane().add(ChatWindow);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(289, 32, 888, 663);
		frame.getContentPane().add(canvas);
		
		JTextArea ChatInput = new JTextArea();
		ChatInput.setBounds(0, 697, 283, 58);
		frame.getContentPane().add(ChatInput);
		
		JButton SendBtn = new JButton("SEND");
		SendBtn.setBackground(new Color(255, 153, 0));
		SendBtn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		SendBtn.setBounds(168, 755, 113, 35);
		frame.getContentPane().add(SendBtn);
		
		JPanel toolPanel = new JPanel();
		toolPanel.setBounds(402, 701, 125, 98);
		frame.getContentPane().add(toolPanel);
		toolPanel.setLayout(null);
		
		JButton pencil = new JButton();
		pencil.setIcon(new ImageIcon("./pencil.png"));
		pencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pencil.setBounds(14, 0, 44, 44);
		toolPanel.add(pencil);
		
		JButton eraser = new JButton();
		eraser.setIcon(new ImageIcon("./eraser.png"));
		eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		eraser.setBounds(14, 50, 44, 44);
		toolPanel.add(eraser);
		
		JButton text = new JButton();
		text.setBounds(61, 0, 44, 44);
		text.setIcon(new ImageIcon("./text.png"));
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolPanel.add(text);
		
		JPanel colors = new JPanel();
		colors.setBounds(931, 701, 175, 98);
		frame.getContentPane().add(colors);
		colors.setLayout(null);
		
		JButton black = new JButton("");
		black.setBackground(Color.BLACK);
		black.setContentAreaFilled(false);
		black.setOpaque(true);
		black.setBounds(14, 0, 40, 40);
		colors.add(black);
		
		JButton white = new JButton("");
		white.setBackground(Color.WHITE);
		white.setContentAreaFilled(false);
		white.setOpaque(true);
		white.setBounds(14, 53, 40, 40);
		colors.add(white);
		
		JButton yellow = new JButton("");
		yellow.setBackground(Color.YELLOW);
		yellow.setContentAreaFilled(false);
		yellow.setOpaque(true);
		yellow.setBounds(68, 0, 40, 40);
		colors.add(yellow);
		
		JButton blue = new JButton("");
		blue.setBackground(Color.BLUE);
		blue.setContentAreaFilled(false);
		blue.setOpaque(true);
		blue.setBounds(122, 0, 40, 40);
		colors.add(blue);
		
		JButton green = new JButton("");
		green.setBackground(Color.GREEN);
		green.setContentAreaFilled(false);
		green.setOpaque(true);
		green.setBounds(68, 53, 40, 40);
		colors.add(green);
		
		JButton orange = new JButton("");
		orange.setBackground(Color.ORANGE);
		orange.setContentAreaFilled(false);
		orange.setOpaque(true);
		orange.setBounds(122, 53, 40, 40);
		colors.add(orange);
		
		JLabel toolTag = new JLabel("Tool");
		toolTag.setForeground(Color.LIGHT_GRAY);
		toolTag.setBackground(Color.LIGHT_GRAY);
		toolTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		toolTag.setHorizontalAlignment(SwingConstants.CENTER);
		toolTag.setBounds(316, 701, 72, 98);
		frame.getContentPane().add(toolTag);
		
		JPanel panel = new JPanel();
		panel.setBounds(661, 701, 156, 98);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton circle = new JButton();
		circle.setBounds(30, 0, 44, 44);
		panel.add(circle);
		circle.setIcon(new ImageIcon("./circle.png"));
		
		JButton line = new JButton();
		line.setBounds(88, 0, 44, 44);
		panel.add(line);
		line.setIcon(new ImageIcon("./line.png"));
		
		JButton oval = new JButton();
		oval.setBounds(30, 54, 44, 44);
		panel.add(oval);
		oval.setIcon(new ImageIcon("./oval.png"));
		
		JButton rect = new JButton();
		rect.setBounds(88, 54, 44, 44);
		panel.add(rect);
		rect.setIcon(new ImageIcon("./rect.png"));
		
		JLabel shapeTag = new JLabel("Shapes");
		shapeTag.setHorizontalAlignment(SwingConstants.CENTER);
		shapeTag.setForeground(Color.LIGHT_GRAY);
		shapeTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		shapeTag.setBounds(544, 701, 92, 98);
		frame.getContentPane().add(shapeTag);
		
		JLabel colorTag = new JLabel("Colors");
		colorTag.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		colorTag.setHorizontalAlignment(SwingConstants.CENTER);
		colorTag.setForeground(Color.LIGHT_GRAY);
		colorTag.setBounds(831, 701, 86, 102);
		frame.getContentPane().add(colorTag);
		rect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
