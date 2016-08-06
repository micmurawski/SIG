package modules;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.jfree.data.xy.XYSeries;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImpAnalyzerFrame extends JFrame{
	
	/**
	 * 
	 */
	double delta=600,lambda=670,CdTe=3.04,HgTe=4.1;
	private static final long serialVersionUID = 1L;
	NumericalAnalysis numericalAnalysis;
	TTLPanel ttlPanel;
	JSpinner spinnerDeltaMax,spinnerDeltaMin;
	JSlider sliderDelta;
	JLabel lblDelta;
	TTLArray ttlArray;
	XYSeries resultHgTe,resultCdTe;
	PlotFrame plotFrame;
	
	public ImpAnalyzerFrame(TTLArray ttlArray){
		super();
		this.ttlArray=ttlArray;
		numericalAnalysis = new NumericalAnalysis();
		//this.data=data;
		TTLPanel options=new TTLPanel(ttlArray);
		this.getContentPane().setLayout(new MigLayout("", "[][][]", "[][][][][][]"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple Interferogram Generator");
		this.setSize(800, 600);
		this.getContentPane().add(options, "cell 0 0");
		
		JLabel lblHgTe = new JLabel("HgTe");
		getContentPane().add(lblHgTe, "flowx,cell 0 1");
		
		JLabel lblCdTe = new JLabel("CdTe");
		getContentPane().add(lblCdTe, "flowx,cell 0 2");
		
		JLabel lblLambda = new JLabel("Lambda [nm] =");
		getContentPane().add(lblLambda, "flowx,cell 0 3");
		
		lblDelta = new JLabel("Delta=0");
		getContentPane().add(lblDelta, "cell 0 4");
		
		spinnerDeltaMin = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 0.01));
		getContentPane().add(spinnerDeltaMin, "flowx,cell 0 5,alignx left");
		
		JSpinner spinnerHgTe = new JSpinner(new SpinnerNumberModel(4.1, 0.1, 10, 0.01));
		getContentPane().add(spinnerHgTe, "cell 0 1");
		spinnerHgTe.setMinimumSize(new Dimension(60,10));
		
		JSpinner spinnerCdTe = new JSpinner(new SpinnerNumberModel(3.04, 0.1, 10, 0.01));
		getContentPane().add(spinnerCdTe, "cell 0 2");
		spinnerCdTe.setMinimumSize(new Dimension(60,10));
		
		JSpinner spinnerLambda = new JSpinner(new SpinnerNumberModel(670, 1, 1000, 0.01));
		getContentPane().add(spinnerLambda, "cell 0 3");
		
		sliderDelta = new JSlider();
		sliderDelta.setValue(0);
		sliderDelta.setMaximum(1000);
		sliderDelta.setMinimum(1);
		getContentPane().add(sliderDelta, "cell 0 5");
		
		spinnerDeltaMax = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 0.01));
		getContentPane().add(spinnerDeltaMax, "cell 0 5");
		
		JButton btnGetPlot = new JButton("Get Plot");
		getContentPane().add(btnGetPlot, "flowx,cell 0 6");
		
		JButton btnAnalyze = new JButton("Analyze");
		getContentPane().add(btnAnalyze, "cell 0 6");
		
		sliderDelta.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				double a = Double.parseDouble(spinnerDeltaMin.getValue().toString());
				double b = Double.parseDouble(spinnerDeltaMax.getValue().toString());
				double c = (double)sliderDelta.getValue();
				delta=a+(b-a)*c/1000.0;
				lblDelta.setText("Delta="+delta);
				//System.out.println(delta);
		        
				
			}
		});
		
		btnGetPlot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				plotFrame = new PlotFrame(resultCdTe);
						
					}
		});
		
	}
	
	
	public void analyze(XYSeries data){
		resultHgTe=new XYSeries("HgTe growth rate",false);
		resultCdTe=new XYSeries("CdTe growth rate",false);
		for(int ii=0;ii<ttlArray.data.size();ii++){
			if(ttlArray.data.get(ii).label.equals("HgTe/CdTe")){
				//System.out.println("znaleziono HgTe");
				double a=numericalAnalysis.findXforMinY(data,
						ttlArray.data.get(ii).start, 
						ttlArray.data.get(ii).finish);
				double b=numericalAnalysis.findXforMaxY(data,
						ttlArray.data.get(ii).start, 
						ttlArray.data.get(ii).finish);
				//System.out.println("znaleziono HgTe"+"od "+(b-a) );
				resultHgTe.add(a,(lambda*3.6/(4*HgTe*(b-a))));
				//resultCdTe.add(a,0);
				
			}else if(ttlArray.data.get(ii).label.equals("CdTe/HgTe")){
				
				double a=numericalAnalysis.findXforMaxY(data,
						ttlArray.data.get(ii).start, 
						ttlArray.data.get(ii).finish);
				double b=numericalAnalysis.findXforMinY(data,
						ttlArray.data.get(ii).start, 
						ttlArray.data.get(ii).finish);
				resultCdTe.add(a,(lambda*3.6/(4*CdTe*(b-a))));
				//resultHgTe.add(a,0);
				System.out.println("znaleziono CdTE "+"Min: "+b+"Max: "+a);
			}else{
				System.out.println("NIC NIE DODANO");
			}
		}
	}
	
	public void getPlot(){
		plotFrame=new PlotFrame(resultHgTe,resultCdTe);
	}
	
	
	
	
	public static void main(String [] args)
		{
			SwingUtilities.invokeLater(new Runnable() {
			
		   public void run() {
			   TTLArray ttl = new TTLArray("/home/vigo/Pulpit/reflaktancja/3850-MCT L.CSV"); 
				 DataSeries data = new DataSeries("r(t)",false,"/home/vigo/Pulpit/reflaktancja/3850-MCT S1 RPM.CSV",100);
				 data.listout();
				 ImpAnalyzerFrame  program = new ImpAnalyzerFrame(ttl);
				 program.setVisible(true);
				 program.analyze(data);
				 program.getPlot();
				 //System.out.println(data.data.getY(0));  	
		    
	  }
	 });
		 
		}	
}
