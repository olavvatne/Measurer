package logic;




import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import panel.HelpPanel;
import panel.OpenPanel;
import panel.ThreePhasePanel;

public class MeasurerWindow extends JFrame {
	private JLabel imageLabel;
	private ThreePhasePanel threePhasePanel;
	private MeasurerLogic logic;
	private JMenuBar menuBar;
	private JMenu fileMenu, optionMenu, helpMenu;
	private JMenuItem saveAndQuitItem, colorItem, measurementItem, saveItem, helpItem;
	private JCheckBoxMenuItem scaledItem;
	
	private static String THREEPHASE_PANEL_STRING = "threePhasePanel";
	private static String HELP_PANEL_STRING = "hjelp";
	private static String OPEN_PANEL_STRING = "open";
	private JPanel cards;
	private OpenPanel openPanel;
	private HelpPanel helpPanel;
	
	
	
	public MeasurerWindow(MeasurerLogic logic) {
		this.logic = logic;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(sz);
		this.setMinimumSize(new Dimension((int)(sz.width*0.5), (int)(sz.height*0.5)));
		this.setLocation(sz.width/10 , sz.height/10);
		this.setTitle("innlesning");
		this.setIconImage(new ImageIcon("resources/measure.png").getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		createMenuBar();
		init();

	}
	
	

	public void setBackgroundImage(ImageIcon img) {
		System.out.println(img.getIconWidth());
		if(img != null) {
			this.imageLabel.setIcon(img);
			this.repaint();
		}
		System.out.println("etter imagelabel");
		
	}
	
	
	public boolean isThereAThreePhaseValue() {
		return threePhasePanel.isPosOGActive() || threePhasePanel.isPosOWActive();
	}
	
	
	public double getOW() {
		return threePhasePanel.getValueOW();
	}
	
	
	public double getOG() {
		return threePhasePanel.getValueOG();
	}
	
	
	public ThreePhasePanel getTPPanel() {
		return this.threePhasePanel;
	}
	
	
	public void init() {
		this.threePhasePanel = new ThreePhasePanel(this.getWidth()+10, (int)(this.getWidth()*1.9), 
												this.getWidth()-10, this.getWidth()/16);
		this.threePhasePanel.setOpaque(false);	
		this.openPanel = new OpenPanel(this.logic);
		this.helpPanel = new HelpPanel();
		this.cards = new JPanel();
		this.cards.setOpaque(false);
		this.addMouseListener(threePhasePanel);
		this.addMouseMotionListener(threePhasePanel);
		this.imageLabel = new JLabel();
		setLayout();
		/*
		
		setContentPane(imageLabel);
		setLayout();
		*/
		threePhasePanel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_LEFT == e.getKeyCode()) {
					logic.iterate(-1);
				
				}
				else if(KeyEvent.VK_RIGHT == e.getKeyCode()) {
					logic.iterate(1);
					
				}
				else if(KeyEvent.VK_SPACE == e.getKeyCode()) {
					logic.logValue();
					logic.iterate(1);
				}
				else if(KeyEvent.VK_L == e.getKeyCode()) {
					getTPPanel().deactivatePos();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               logic.exitApplication();
            }
      });
	}

	public void showHelpPanel() {
		((CardLayout)cards.getLayout()).show(cards, HELP_PANEL_STRING);
	}
	
	public void showThreePhasePanel() {
		((CardLayout)cards.getLayout()).show(cards, THREEPHASE_PANEL_STRING);
		this.openPanel.setFocusable(false);
		this.threePhasePanel.requestFocus();
	}
	
	
	
	public void setEnableMenuBar(boolean value) {
		this.fileMenu.setEnabled(value);
		this.optionMenu.setEnabled(value);
		this.helpMenu.setEnabled(value);
	}

	
	
	private void createMenuBar() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Fil");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		saveAndQuitItem = new JMenuItem("Lagre og avslutt");
		saveAndQuitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		saveAndQuitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.exitApplication();
			}
		});
		fileMenu.add(saveAndQuitItem);
		saveItem = new JMenuItem("lagre");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.save();
				
			}
		});
		fileMenu.add(saveItem);
		
		
		
		//second menu
		optionMenu = new JMenu("valg");
		optionMenu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(optionMenu);
		measurementItem = new JMenuItem("endre måleverdier");
		measurementItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		measurementItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.changeMeasurementDialog(threePhasePanel.getStartValueOW(), threePhasePanel.getEndValueOW(),
						threePhasePanel.getStartValueOG(), threePhasePanel.getEndValueOG());
			}
		});
		optionMenu.add(measurementItem);
		/*
		colorItem = new JMenuItem("Endre farge på markører maybee???"); //kanskje ikke nødvendig denne her
		colorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		optionMenu.add(colorItem);
		*/
		scaledItem = new JCheckBoxMenuItem("skaler bildet");
		scaledItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		scaledItem.setSelected(false);
		scaledItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.setScaled(scaledItem.isSelected());
				
			}
		});
		optionMenu.add(scaledItem);
		
		//third menu
		helpMenu = new JMenu("hjelp");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);
		
		helpItem = new JMenuItem("hjelp");
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					logic.helpDialog();
			}
		});
		helpMenu.add(helpItem);
		
		this.setJMenuBar(menuBar);
		this.setEnableMenuBar(false);
	}
	
	
	
	private void setLayout() {
		
		setContentPane(imageLabel);
		cards.setLayout(new CardLayout());
		cards.add(OPEN_PANEL_STRING, openPanel);
		cards.add(THREEPHASE_PANEL_STRING, threePhasePanel);
		cards.add(HELP_PANEL_STRING, helpPanel);
		add(cards);
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addComponent(cards)
        );
        
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addComponent(cards)
        );
         pack();  
          
	}
}
