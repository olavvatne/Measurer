package helper;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class MultipleInputJOptionPane {
	private JTextField startOWField = new JTextField(5);
    private JTextField endOWField = new JTextField(5);
    private JTextField startOGField = new JTextField(5);
    private JTextField endOGField = new JTextField(5);
    private double sOW;
    private double eOW;
    private double sOG;
    private double eOG;
    
    
    
    public MultipleInputJOptionPane(double startOW, double endOW, double startOG, double endOG) {
		startOWField.setText(startOW + "");
		endOWField.setText(endOW + "");
		startOGField.setText(startOG + "");
		endOGField.setText(endOG + "");
		
		JPanel windowPanel = new JPanel();
		JPanel owPanel = new JPanel();
		JPanel ogPanel = new JPanel();
		
		owPanel.add(new JLabel("startverdi OW:"));
		owPanel.add(startOWField);
		owPanel.add(Box.createHorizontalStrut(15));
		owPanel.add(new JLabel("endeverdi OW:"));
		owPanel.add(endOWField);
		ogPanel.add(new JLabel("startverdi OG:"));
		ogPanel.add(startOGField);
		ogPanel.add(Box.createHorizontalStrut(15));
		ogPanel.add(new JLabel("endeverdi OG:"));
		ogPanel.add(endOGField);
		windowPanel.add(owPanel);
		windowPanel.add(ogPanel);
		
		owPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		ogPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		GroupLayout layout = new GroupLayout(windowPanel);
		windowPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(owPanel)
				.addComponent(ogPanel)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(owPanel)
				.addComponent(ogPanel)
		);
		do {
			JOptionPane.showConfirmDialog(null, windowPanel, "start og slutt mål", JOptionPane.OK_CANCEL_OPTION);
			
		} while(!isCorrectValues(startOWField.getText(), endOWField.getText(), startOGField.getText(), endOGField.getText()));   	
	}
    
    
    
    public double getsOW() {
		return sOW;
	}



	public double geteOW() {
		return eOW;
	}



	public double getsOG() {
		return sOG;
	}



	public double geteOG() {
		return eOG;
	}


	
	private boolean isCorrectValues(String sOW, String eOW, String sOG,
			String eOG) {
		try {
			this.sOW = Double.parseDouble(sOW);
			this.eOW = Double.parseDouble(eOW);
			this.sOG = Double.parseDouble(sOG);
			this.eOG = Double.parseDouble(eOG);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
