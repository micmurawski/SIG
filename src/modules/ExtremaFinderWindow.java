package modules;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.data.xy.XYSeries;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class ExtremaFinderWindow extends JFrame{
	public double delta=0;
	JLabel lblDelta;
	JSpinner spinnerDeltaMax,spinnerDeltaMin;
	JSlider sliderDelta;
	private List<ExtremaChange> listeners = new LinkedList<>();
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerFrom;
	private JSpinner spinnerTo;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblRange;
	private JButton btnFind;
	public JButton btnSaveAs;
	public JButton btnGrowthSpeed;
	
	
	public ExtremaFinderWindow(final XYSeries data){
				
		
		
		this.getContentPane().setLayout(new MigLayout("", "[100][250][100]", "[][][][][][][]"));
		
		lblDelta = new JLabel("Delta=100");
		getContentPane().add(lblDelta, "cell 1 1,alignx center");
		
		spinnerDeltaMin = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 0.01));
		spinnerDeltaMin.setMinimumSize(new Dimension(60,10));
		getContentPane().add(spinnerDeltaMin, "cell 0 2");
		
		
		
		sliderDelta = new JSlider();
		sliderDelta.setValue(0);
		sliderDelta.setMaximum(1000);
		sliderDelta.setMinimum(1);
		getContentPane().add(sliderDelta, "cell 1 2,alignx center");
		
		spinnerDeltaMax = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 0.01));
		spinnerDeltaMax.setMinimumSize(new Dimension(60, 10));
		getContentPane().add(spinnerDeltaMax, "cell 2 2");
		
		lblRange = new JLabel("Range");
		getContentPane().add(lblRange, "cell 1 3,alignx center");
		
		lblFrom = new JLabel("From");
		getContentPane().add(lblFrom, "flowx,cell 1 4,alignx center");
		
		spinnerFrom = new JSpinner();
		spinnerFrom = new JSpinner(new SpinnerNumberModel(0, 0,Double.MAX_VALUE, 0.01));
		spinnerFrom.setMinimumSize(new Dimension(60,10));
		getContentPane().add(spinnerFrom, "cell 1 4,alignx center");
		
		lblTo = new JLabel("To");
		getContentPane().add(lblTo, "cell 1 4,alignx center");
		
		spinnerTo = new JSpinner();
		spinnerTo = new JSpinner(new SpinnerNumberModel(10000, 0,Double.MAX_VALUE, 0.01));
		spinnerTo.setMinimumSize(new Dimension(60,10));
		getContentPane().add(spinnerTo, "cell 1 4,alignx right");
		
		btnFind = new JButton("Find Extremas");
		getContentPane().add(btnFind, "flowx,cell 1 5");
		
		btnFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fireExtremaChangeEvent();
				
			}
		});
		
		btnSaveAs= new JButton("Save As");
		getContentPane().add(btnSaveAs, "cell 1 5,alignx center");
		
		btnGrowthSpeed = new JButton("GrowthSpeed");
		getContentPane().add(btnGrowthSpeed, "cell 2 5");
		
		btnSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fireSaveBtn();
				
			}
		});
		
		
		sliderDelta.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerDeltaMin.getValue().toString());
				double b = Double.parseDouble(spinnerDeltaMax.getValue().toString());
				double c = (double)sliderDelta.getValue();
				delta=a+(b-a)*c/1000.0;
				lblDelta.setText("Delta="+delta);
				//System.out.println(delta);
		        
			}}
			);
		
		
		
		
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(448, 180);
        this.setTitle("Extrema Finder");
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
      
        
      
		
	}
	
	public void addExtremaChangeListener(ExtremaChange extremaChange){
		listeners.add(extremaChange);
	}
	
	
	
	private void fireExtremaChangeEvent(){
		for(ExtremaChange oc: listeners)
			oc.extremaChange();
	}
	
	public void addSaveBtnListener(ExtremaChange extremaChange){
		listeners.add(extremaChange);
	}
	
	private void fireSaveBtn(){
		for(ExtremaChange oc: listeners)
			oc.extremaChange();
	}
	
	public double getFrom(){
		return Double.parseDouble(spinnerFrom.getValue().toString());
	}
	public double getTo(){
		return Double.parseDouble(spinnerTo.getValue().toString());
	}

	 
	 
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
		    public void run() {
		//ExtremaFinderWindow  program =new ExtremaFinderWindow();
		//program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //program.setSize(500, 200);
        //program.setTitle("Simple Interferogram Generator");
        //program.setVisible(true);
		    }
	      });
		 
		
	}
}
