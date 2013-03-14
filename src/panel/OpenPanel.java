package panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.MeasurerLogic;

public class OpenPanel extends GradientPanel {
	
	private JButton openExcelButton;
	private JButton openPicturesButton;
	private JButton continueButton;
	private HelpPanel help = new HelpPanel();
	private MeasurerLogic logic;
	private boolean isExcelReady;
	private boolean isPicturesReady;
	private JPanel cp;
	private static ImageIcon check = new ImageIcon("resources/Check-icon.png");
	private static ImageIcon cross = new ImageIcon("resources/Delete-icon.png");
	private JLabel excelAcceptPic = new JLabel(cross);
	private JLabel picturesAcceptPic = new JLabel(cross);
	
	
	public OpenPanel(MeasurerLogic logic) {
		super();
		this.logic = logic;
		this.setOpaque(false);
		init();
		setLayout();
	}


	private void init() {
		cp = new JPanel();
		cp.setOpaque(false);
		help.setOpaque(false);
		
		
		
		openExcelButton = new JButton("Åpne Excelfil");
		openExcelButton.setOpaque(false);
		openExcelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openExcelFile();
			}
		});
		
		openPicturesButton = new JButton("Åpne bildefiler");
		openPicturesButton.setOpaque(false);
		openPicturesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openPicturesFile();
			}
		});
		
		continueButton = new JButton("Fortsett");
		continueButton.setOpaque(false);
		continueButton.setEnabled(false);
		continueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				initComplete();
				
			}
		});
	}

	
	
	private void openExcelFile() {
		isExcelReady =this.logic.openExcelFile();
		if(isExcelReady) {
			this.excelAcceptPic.setIcon(check);
			this.openPicturesButton.requestFocus();
			if(isPicturesReady) {
				this.continueButton.setEnabled(true);
			}
		}
	}
	
	
	
	private void openPicturesFile() {
		isPicturesReady =this.logic.openPicturesFile();
		if(isPicturesReady) {
			this.picturesAcceptPic.setIcon(check);
			this.continueButton.requestFocus();
			if(isExcelReady) {
				this.continueButton.setEnabled(true);
			}
		}
	}
	
	
	
	public void initComplete() {
		if(isExcelReady && isPicturesReady) {
			this.logic.initComplete();
		}
	}
	
	
	
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		this.add(cp);
		cp.add(continueButton);
		cp.add(help);
		cp.add(openExcelButton);
		cp.add(openPicturesButton);
		
		GroupLayout layout = new GroupLayout(cp);
        cp.setLayout(layout);
        
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
        layout.setHorizontalGroup(layout.createParallelGroup()
        		.addComponent(help)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(openExcelButton)
        			.addComponent(excelAcceptPic)
        			.addGap(10, 20, 30)
                	.addComponent(openPicturesButton)
                	.addComponent(picturesAcceptPic)
                	.addGap(10, 20, 30)
                	.addComponent(continueButton))
            	
            );
            
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addComponent(help)
            		.addGroup(layout.createParallelGroup()
            			.addComponent(openExcelButton)
            			.addComponent(excelAcceptPic)
                    	.addComponent(openPicturesButton)
                    	.addComponent(picturesAcceptPic)
                    	.addComponent(continueButton))
            		
            );
	}
}
