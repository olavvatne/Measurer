package logic;



import helper.MultipleInputJOptionPane;
import image.ImageCommunication;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import panel.HelpPanel;

import log.Logger;
import log.MeasurementLog;

import excel.ExcelCommunication;


public class MeasurerLogic {
	private ExcelCommunication excel;
	private ImageCommunication images;
	private MeasurerWindow window;
	private Logger log;
	public static int DATE_COLUMN = 0;
	public static int TIME_COLUMN = 1;
	public static int OG_COLUMN = 2;
	public static int WO_COLUMN =3;
	
	
	
	public MeasurerLogic() {
		images = new ImageCommunication();
		excel = new ExcelCommunication(DATE_COLUMN, TIME_COLUMN);
		window = new MeasurerWindow(this);
		window.setVisible(true);
		log = new Logger();
		
	}
	
	
	
	public void iterate(int iterateValue) {
		images.iterateIndex(iterateValue);
		if(images.getIndex() >= images.getImageCount()) {
			images.findPictureFiles();
			images.setIndex(0);
			// må returne om cancel eller noe...
		}
		if(iterateValue < 0) {
			setTTPvalues();
		}
		else{
			logTTPvalues();
		}
		images.readImg();
		ImageIcon icon = images.getScaledImage(window.getWidth(), window.getHeight());
		if(icon != null) {
			window.setBackgroundImage(icon);
		}
		else {
			//something something
		}
	}
	
	
	
	public void setTTPvalues() {
		if(!log.isEmpty()) {
			MeasurementLog logItem = log.getMeasurement();
			window.getTPPanel().setStartLineOW(logItem.getX1OW());
			window.getTPPanel().setStartLineOG(logItem.getX1OG());
			window.getTPPanel().setEndLineOW(logItem.getX2OW());
			window.getTPPanel().setEndLineOG(logItem.getX2OG());
			window.getTPPanel().setValueLineOW(logItem.getPosOW());
			window.getTPPanel().setValueLineOG(logItem.getPosOG());
			window.getTPPanel().setPosOWActive(logItem.isPosOWSelected());
			window.getTPPanel().setPosOGActive(logItem.isPosOGSelected());
		}
	}
	
	
	public void logTTPvalues() {
		log.logMeasurment(window.getTPPanel().getStartLineOW(), window.getTPPanel().getEndLineOW(),
				window.getTPPanel().getStartLineOG(), window.getTPPanel().getEndLineOG(),
				window.getTPPanel().getValueLineOW(), window.getTPPanel().getValueLineOG(),
				window.getTPPanel().isPosOWActive(), window.getTPPanel().isPosOGActive());
	}
	
	
	
	public void logValue() {
		if(window.isThereAThreePhaseValue()) {
			int row = -1;
			try {
				row = findMatchingRow();
				System.out.println("logvalue rad" +  row);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(row == -1) {
				return;
			}
			
			excel.setCell(WO_COLUMN, row, window.getOW());
			excel.setCell(OG_COLUMN, row, window.getOG());
		}
	}
	
	
	
	private int findMatchingRow() throws Exception {
		Date imageDate = images.getDate(images.getIndex());

		for(int i = excel.getIndexExcel(); i< excel.getRowLength(); i++) {
			if(imageDate.equals(excel.getDate(i))) {
				excel.setIndexExcel(i);
				System.out.println("setter excel index til, og returerer " + i);
				return i;
			}
			else if(imageDate.before(excel.getDate(i))) {
				Object[] valg={excel.getDate(i-1).toLocaleString(), excel.getDate(i).toLocaleString(), "Ikke log verdi"};
				String message = "Finner ikke nøyaktig sted å legge verdiene i excelfila \n Hvordan vil du lagre verdi";
				int result = JOptionPane.showOptionDialog(null, message, "tittel",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, valg, valg[0]);
				//HER KAN DET BLI STORE FEIL OM i == 0 og man prøver å finne i -1.. .Dette må fikses!
				
				if(result == JOptionPane.YES_OPTION) {
					return i-1;
				}
				else if(result == JOptionPane.NO_OPTION) {
					return i;
				}
				else {
					return -1;
				}
			}
		}
		return -1;
	}
	
	
	//bør gjøre denne bedre bruke.. binærsøk!
	private int findFirstMatchingRow() {
		Date imageDate = images.getDate(0);
		Date excelDate = excel.getDate(0);

		if(excelDate.after(imageDate)) {
			System.out.println("yupp");
			for(int i = images.getIndex(); i< images.getImageCount(); i++) {
				imageDate = images.getDate(i);
				if(excelDate.equals(imageDate) || excelDate.after(imageDate)) {
					System.out.println(i);
					images.setIndex(i);
					return i;
				}
			}
		}
		else {
			try {
				excel.setIndexExcel(findMatchingRow());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	
	public void changeMeasurementDialog(double startOW, double endOW, double startOG, double endOG) {
		MultipleInputJOptionPane dialog = new MultipleInputJOptionPane(startOW, endOW, startOG, endOG);
		window.getTPPanel().setStartValueOG(dialog.getsOG());
		window.getTPPanel().setEndValueOG(dialog.geteOG());
		window.getTPPanel().setStartValueOW(dialog.getsOW());
		window.getTPPanel().setEndValueOW(dialog.geteOW());
		window.getTPPanel().repaint();
	}
	
	
	
	public void helpDialog() {
		JDialog dialog;
		HelpPanel help = new HelpPanel();
		help.setDrawRoundedRect(false);
		dialog = new JDialog(this.window, "Hjelp", true);  
		dialog.setResizable(false);  
		dialog.getContentPane().add(help);  
		dialog.pack();    
		Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();  
		dialog.setLocation(new Double((Size.getWidth()/2) - (dialog.getWidth()/2)).intValue(), new Double((Size.getHeight()/2) - (dialog.getHeight()/2)).intValue()); 
		dialog.setVisible(true);
	}
	
	public void exitApplication() {
		if(excel.isExcelInputStreamOpen()) {
			excel.closeAndWriteExcel();
		}
		System.exit(0);
	}
	
	
	
	public void setScaled(boolean value) {
		images.setScaled(value);
		ImageIcon icon = images.getScaledImage(window.getWidth(), window.getHeight());
		if(icon != null) {
			window.setBackgroundImage(icon);
		}
		else {
			//something something
		}
	}
	
	
	
	public void save() {
		excel.saveExcelFile();
		
	}
	
	
	
	public boolean openExcelFile() {
		return excel.openExcelFile();
	}
	
	
	public boolean openPicturesFile() {
		return images.openPicturesFile();
		
	}
	
	public void initComplete() {
		iterate(0);	
		this.window.setEnableMenuBar(true);
		this.window.showThreePhasePanel();
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new MeasurerLogic();
			}
		});
	}
}
