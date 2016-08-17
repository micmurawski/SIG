package modules;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class AskWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSpinner spinner;
	public JButton btnOk;
	private JLabel lblNewLabel_1;
	public JSpinner spinner_1;
	
	
	
	public AskWindow(){
		this.getContentPane().setLayout(new MigLayout("", "[360.00]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Co który rekord wczytać?");
		getContentPane().add(lblNewLabel, "cell 0 1,alignx center");
		
		spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		getContentPane().add(spinner, "cell 0 2,alignx center");
		
		lblNewLabel_1 = new JLabel("Norma: ");
		getContentPane().add(lblNewLabel_1, "cell 0 3,alignx center");
		
		spinner_1 = new JSpinner(new SpinnerNumberModel(1, 1, 1000000, 1));
		getContentPane().add(spinner_1, "cell 0 4,alignx center");
		
		btnOk = new JButton("OK");
		getContentPane().add(btnOk, "cell 0 5,alignx center");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 197);
        this.setTitle("Simple Interferogram Generator");
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
  
        
      
		
	}

	 
	 
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
		    public void run() {
		AskWindow  program =new AskWindow();
		program.getContentPane().setLayout(new MigLayout("", "[360.00]"));
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.setSize(300, 100);
        program.setTitle("Simple Interferogram Generator");
        program.setVisible(true);
       // System.out.println(program.getValue());
		    }
	      });
		 
		
	}
}
