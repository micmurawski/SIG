package modules;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class LoadingWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar progressBar;
	JPanel contentPane;
	
	public LoadingWindow(String s){
		super(s);
		progressBar=new JProgressBar();
		progressBar.setIndeterminate(true);
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JLabel(s),BorderLayout.NORTH);
		contentPane.add(progressBar,BorderLayout.CENTER);
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}
	public static void main(String[] args){

	    final LoadingWindow win = new LoadingWindow("Obliczanie...");

	  Runnable runnable = new Runnable() {
	        public void run() {
	            // do loading stuff in here
	            // for now, simulate loading task with Thread.sleep(...)
	            try {
	                System.out.println("Doing loading in background step 1");
	                Thread.sleep(1000);
	                System.out.println("Doing loading in background step 2");
	               Thread.sleep(1000);
	                System.out.println("Doing loading in background step 3");
	                Thread.sleep(1000);
	                System.out.println("Doing loading in background step 4");
	                Thread.sleep(1000);
	               System.out.println("Doing loading in background step 5");
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            // when loading is finished, make frame disappear
	            SwingUtilities.invokeLater(new Runnable() {
	                public void run() {
	                    win.setVisible(false);
	                }
	            });

	        }
	    };
	    new Thread(runnable).start();
	}

}
