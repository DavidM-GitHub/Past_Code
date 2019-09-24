
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import net.miginfocom.swing.MigLayout;

public class Timer extends JFrame {
	
	// Interface components
	
	// Fonts to be used
	Font countdownFont = new Font("Arial", Font.BOLD, 20);
	Font elapsedFont = new Font("Arial", Font.PLAIN, 14);
	
	// Labels and text fields
	JLabel countdownLabel = new JLabel("Seconds remaining:");
	JTextField countdownField = new JTextField(15);
	JLabel elapsedLabel = new JLabel("Time running:");
	JTextField elapsedField = new JTextField(15);
	JButton startButton = new JButton("START");
	JButton pauseButton = new JButton("PAUSE");
	JButton stopButton = new JButton("STOP");
	File filePath;
	JFileChooser jfc;
	
	// The text area and the scroll pane in which it resides
	JTextArea display;
	
	JScrollPane myPane;
	
	// These represent the menus
	JMenuItem saveData = new JMenuItem("Save data", KeyEvent.VK_S);
	JMenuItem displayData = new JMenuItem("Display data", KeyEvent.VK_D);
	
	JMenu options = new JMenu("Options");
	
	JMenuBar menuBar = new JMenuBar();
	
	// These booleans are used to indicate whether the START button has been clicked
	boolean started;
	
	// and the state of the timer (paused or running)
	boolean paused;
	
	// Number of seconds
	long totalSeconds = 0;
	long secondsToRun = 0;
	long secondsSinceStart = 0;
	
	// This is the thread that performs the countdown and can be started, paused and stopped
	TimerThread countdownThread;
	
	//Timer Dialog object that allows you to enter the details for countdown timer
	TimerDialog td;

	// Interface constructed
	Timer() {
		
		setTitle("Timer Application");
		
    	MigLayout layout = new MigLayout("fillx");
    	JPanel panel = new JPanel(layout);
    	getContentPane().add(panel);
    	
    	options.add(saveData);
    	options.add(displayData);
    	menuBar.add(options);
    	
    	panel.add(menuBar, "spanx, north, wrap");
    	
    	MigLayout centralLayout = new MigLayout("fillx");
    	
    	JPanel centralPanel = new JPanel(centralLayout);
    	
    	GridLayout timeLayout = new GridLayout(2,2);
    	
    	JPanel timePanel = new JPanel(timeLayout);
    	
    	countdownField.setEditable(false);
    	countdownField.setHorizontalAlignment(JTextField.CENTER);
    	countdownField.setFont(countdownFont);
    	countdownField.setText("00:00:00");
    	
    	timePanel.add(countdownLabel);
    	timePanel.add(countdownField);

    	elapsedField.setEditable(false);
    	elapsedField.setHorizontalAlignment(JTextField.CENTER);
    	elapsedField.setFont(elapsedFont);
    	elapsedField.setText("00:00:00");
    	
    	timePanel.add(elapsedLabel);
    	timePanel.add(elapsedField);

    	centralPanel.add(timePanel, "wrap");
    	
    	GridLayout buttonLayout = new GridLayout(1, 3);
    	
    	JPanel buttonPanel = new JPanel(buttonLayout);
    	
    	buttonPanel.add(startButton);
    	buttonPanel.add(pauseButton, "");
    	buttonPanel.add(stopButton, "");
    	
    	centralPanel.add(buttonPanel, "spanx, growx, wrap");
    	
    	panel.add(centralPanel, "wrap");
    	
    	display = new JTextArea(100,150);
        display.setMargin(new Insets(5,5,5,5));
        display.setEditable(false);
        
        JScrollPane myPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(myPane, "alignybottom, h 100:320, wrap");
        
        
        // Initial state of system
        paused = false;
        started = false;
        
        // Allowing interface to be displayed
    	setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        // TODO: SAVE: This method should allow the user to specify a file name to which to save the contents of the text area using a 
        // JFileChooser. You should check to see that the file does not already exist in the system.
        filePath=new File("E:\\OSD2\\ADS2\\OODS3");//remember to put the double slash
		jfc=new JFileChooser(filePath);
		saveData.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(display.getText()==null)
    			{
    				JOptionPane.showMessageDialog(panel,"There is no data to save to the file");
    			}
    			else
    			{
    				int returnVal = jfc.showSaveDialog(panel);
    				if(returnVal == JFileChooser.APPROVE_OPTION){
    					//ObjectOutputStream out = null;
    					//-Set the target file location to be the file indicated by the save file dialog
    					File fileSelected=jfc.getSelectedFile();
    					try {
							writeDataFile(fileSelected);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    					display.setText("");
    				
    			}
    				
    			}
    		}});
		

        
        // TODO: DISPLAY DATa: This method should retrieve the contents of a file representing a previous report using a JFileChooser.
        // The result should be displayed as the contents of a dialog object.
		displayData.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int returnVal =jfc.showOpenDialog(panel);
    			if(returnVal == JFileChooser.APPROVE_OPTION){
    			//ObjectInputStream in=null;
    			File fileSelected=jfc.getSelectedFile();
					try {
						readDataFile(fileSelected);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
}
    			
    			
    		}});


        
        // TODO: START: This method should check to see if the application is already running, and if not, launch a TimerThread object.
		// If the application is running, you may wish to ask the user if the existing thread should be stopped and a new thread started.
        // It should begin by launching a TimerDialog to get the number of seconds to count down, and then pass that number of seconds along
		// with the seconds since the start (0) to the TimerThread constructor.
		// It can then display a message in the text area stating how long the countdown is for.
		startButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
		if(started==false) {
			td=new TimerDialog(Timer.this,totalSeconds,true);
			secondsToRun=td.getSeconds();
			//System.out.println(td.getSeconds());
			display.setText("Countdown for "+td.getSeconds()+" seconds");
			countdownThread=new TimerThread(countdownField,elapsedField,secondsToRun,secondsSinceStart);
			Thread t1=new Thread(countdownThread);
			t1.start();
			started =true;
		}
		else {
			int answer=JOptionPane.showConfirmDialog(panel,"Do you want to restart the time? ","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
			if(answer==0) {
				countdownThread.stop();
				td=new TimerDialog(Timer.this,totalSeconds,true);
				secondsToRun=td.getSeconds();
				//System.out.println(td.getSeconds());
				display.setText("Countdown for "+td.getSeconds()+" seconds");
				countdownThread=new TimerThread(countdownField,elapsedField,secondsToRun,secondsSinceStart);
				Thread t1=new Thread(countdownThread);
				t1.start();
				started =true;
			}
		}
    		}});
		
        
        // TODO: PAUSE: This method should call the TimerThread object's pause method and display a message in the text area
        // indicating whether this represents pausing or restarting the timer.
		pauseButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(paused==false) {
    			countdownThread.pause();
    			pauseButton.setText("Resume");
    			display.setText(display.getText()+"\nCountdown was paused at: "+countdownThread.getElapsedSeconds()+" seconds");
    			paused=true;
    			started=false;
    			}
    			else {
    				countdownThread.pause();
    				pauseButton.setText("Pause");
    				display.setText(display.getText()+"\nCountdown was restarted at: "+countdownThread.getElapsedSeconds()+" seconds");
    				paused=false;
    				started=true;
    			}
    			
    		}});
        
        // TODO: STOP: This method should stop the TimerThread object and use appropriate methods to display the stop time
        // and the total amount of time remaining in the countdown (if any).
		stopButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			countdownThread.stop();
    			display.setText(display.getText()+"\nThe timer was stopped with "+countdownThread.getCountdownSeconds()+" left");
    		}});
		
    	
	}
	
	// TODO: These methods can be used in the action listeners above.
	//displays the contents of a file to the text area
	public synchronized void readDataFile(File f) throws IOException, FileNotFoundException {
		String fileLocation=f.getAbsolutePath();
		Scanner s=null;
		
		try {
		s = new Scanner(new BufferedReader(new FileReader(fileLocation)));
		while (s.hasNext()) { 
        	
			display.append("\n"+s.nextLine()); 
		}
		}finally {
			
			s.close();
        }
	}
	
	// TODO: These methods can be used in the action listeners above.
	public synchronized void writeDataFile(File f) throws IOException, ClassNotFoundException {
		String fileLocation=f.getAbsolutePath();
		BufferedWriter outputStream = null;
		try {
		//	FileOutputStream fileOutput=new FileOutputStream(fileLocation);
			outputStream = new BufferedWriter(new FileWriter(fileLocation));
			//fileOutput.write(display.getText());
			outputStream.write(display.getText());
		}finally {
			outputStream.close();
		}
		
		
		/*String result = new String();
  
		return result;*/
	}

    public static void main(String[] args) {

        Timer timer = new Timer();

    }

}

