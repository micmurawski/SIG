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

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
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
	JSpinner spinnerDeltaMax,spinnerDeltaMin;
	JSlider sliderDelta;
	JLabel lblDelta;
	public XYSeries resultHgTe,resultCdTe;
	PlotFrame plotFrame;
	boolean opt1=true,opt2=true;
	
	public ImpAnalyzerFrame(TTLPanel ttlPanel){
		super();
		numericalAnalysis = new NumericalAnalysis();
	
		this.getContentPane().setLayout(new MigLayout("", "[][grow][][]", "[grow][][][][][][][]"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple Interferogram Generator");
		this.setSize(800, 600);
		
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("","[][]","[][][][][][][]"));
		JLabel lblCdTeOpt = new JLabel("CdTe Options: ");
		panel.add(lblCdTeOpt,"cell 0 0");
		final JRadioButton rBtnCdTe1 = new JRadioButton("use Min Max analyzer");
		panel.add(rBtnCdTe1,"cell 0 1");
		rBtnCdTe1.setSelected(true);
		final JRadioButton rBtnCdTe2 = new JRadioButton("use Levemberg-Maquartd analyzer");
		panel.add(rBtnCdTe2,"cell 0 2");
		JLabel lblHgTeOpt = new JLabel("HgTe Options: ");
		panel.add(lblHgTeOpt,"cell 0 3");
		
		final JRadioButton rBtnHgTe1 = new JRadioButton("use Min Max analyzer");
		panel.add(rBtnHgTe1,"cell 0 4");
		rBtnHgTe1.setSelected(true);
		final JRadioButton rBtnHgTe2 = new JRadioButton("use Levemberg-Maquartd analyzer");
		panel.add(rBtnHgTe2,"cell 0 5");
		
		
		getContentPane().add(panel, "cell 1 0,grow");
		
		
		this.getContentPane().add(ttlPanel, "cell 0 0");
		
		JLabel lblHgTe = new JLabel("HgTe");
		getContentPane().add(lblHgTe, "flowx,cell 0 2");
		
		JLabel lblCdTe = new JLabel("CdTe");
		getContentPane().add(lblCdTe, "flowx,cell 0 3");
		
		JLabel lblLambda = new JLabel("Lambda [nm] =");
		getContentPane().add(lblLambda, "flowx,cell 0 4");
		
		lblDelta = new JLabel("Delta=0");
		getContentPane().add(lblDelta, "cell 0 5");
		
		spinnerDeltaMin = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 0.01));
		getContentPane().add(spinnerDeltaMin, "flowx,cell 0 6,alignx left");
		
		JSpinner spinnerHgTe = new JSpinner(new SpinnerNumberModel(4.1, 0.1, 10, 0.01));
		getContentPane().add(spinnerHgTe, "cell 0 2");
		spinnerHgTe.setMinimumSize(new Dimension(60,10));
		
		JSpinner spinnerCdTe = new JSpinner(new SpinnerNumberModel(3.04, 0.1, 10, 0.01));
		getContentPane().add(spinnerCdTe, "cell 0 3");
		spinnerCdTe.setMinimumSize(new Dimension(60,10));
		
		JSpinner spinnerLambda = new JSpinner(new SpinnerNumberModel(670, 1, 1000, 0.01));
		getContentPane().add(spinnerLambda, "cell 0 4");
		
		sliderDelta = new JSlider();
		sliderDelta.setValue(0);
		sliderDelta.setMaximum(1000);
		sliderDelta.setMinimum(1);
		getContentPane().add(sliderDelta, "cell 0 6");
		
		spinnerDeltaMax = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 0.01));
		getContentPane().add(spinnerDeltaMax, "cell 0 6");
		
		JButton btnGetPlot = new JButton("Get Plot");
		getContentPane().add(btnGetPlot, "flowx,cell 0 7");
		
		JButton btnAnalyze = new JButton("Analyze");
		getContentPane().add(btnAnalyze, "cell 0 7");
		
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
		
		
		rBtnCdTe1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(rBtnCdTe1.isSelected()){
				opt1=true;
				rBtnCdTe1.setSelected(true);
				rBtnCdTe2.setSelected(false);
			}else{
				rBtnCdTe1.setSelected(true);
				rBtnCdTe2.setSelected(false);
			}
				System.out.println(opt1);
			}
		});
		
		
		rBtnCdTe2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rBtnCdTe2.isSelected()){
					opt1=false;
					rBtnCdTe2.setSelected(true);
					rBtnCdTe1.setSelected(false);
				}else{
					rBtnCdTe2.setSelected(true);
					rBtnCdTe1.setSelected(false);
				}
				System.out.println(opt1);
			}
		});
		
		rBtnHgTe1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(rBtnHgTe1.isSelected()){
				opt2=true;
				rBtnHgTe1.setSelected(true);
				rBtnHgTe2.setSelected(false);
			}else{
				rBtnHgTe1.setSelected(true);
				rBtnHgTe2.setSelected(false);
			}
				System.out.println(opt2);
			}
		});
		
		
		rBtnHgTe2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rBtnHgTe2.isSelected()){
					opt1=false;
					rBtnHgTe2.setSelected(true);
					rBtnHgTe1.setSelected(false);
				}else{
					rBtnHgTe2.setSelected(true);
					rBtnHgTe1.setSelected(false);
				}
				System.out.println(opt2);
			}
		});
		
		
		
	}
	
	
	
	
	public void analyze(DataSeries data,TTLPanel ttlPanel){
		
		
		resultHgTe=new XYSeries("HgTe growth rate",false);
		resultCdTe=new XYSeries("CdTe/HgTe",false);
		//System.out.print(ttlPanel.table.getRowCount());
		for(int ii=0;ii<ttlPanel.getLen();ii++){
			if(ttlPanel.getLabel(ii).equals("HgTe/CdTe")){
				if(opt2){
					double a=numericalAnalysis.findXforMinY(data,
							ttlPanel.getStart(ii), 
							ttlPanel.getFinish(ii));
					double b=numericalAnalysis.findXforMaxY(data,
							ttlPanel.getStart(ii), 
							ttlPanel.getFinish(ii));
					resultHgTe.add(a,(lambda*3.6/(4*HgTe*(b-a))));
					ttlPanel.setValueAt(lambda*3.6/(4*HgTe*(b-a)),ii,3);
					
				}
				
				
			}else if(ttlPanel.getLabel(ii).equals("CdTe/HgTe")){
				if(opt1){
				double a=numericalAnalysis.findXforMaxY(data,
						ttlPanel.getStart(ii), 
						ttlPanel.getFinish(ii));
				double b=numericalAnalysis.findXforMinY(data,
						ttlPanel.getStart(ii), 
						ttlPanel.getFinish(ii));
				resultCdTe.add(a,(lambda*3.6/(4*CdTe*(b-a))));
				ttlPanel.setValueAt(lambda*3.6/(4*CdTe*(b-a)),ii,3);
				System.out.println("znaleziono CdTE "+"Min: "+b+"Max: "+a);
				}else{
					
					MyFuncFitter myFuncFitter = new MyFuncFitter();
					double[] coeffs=myFuncFitter.fit(data,
							ttlPanel.getStart(ii),
							ttlPanel.getFinish(ii),
							new double[]{}
							);
					
					
					
				}
				
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
			   TTLPanel ttl = new TTLPanel("/home/vigo/Pulpit/reflaktancja/3850-MCT L.CSV"); 
				 DataSeries data = new DataSeries("r(t)",false,"/home/vigo/Pulpit/reflaktancja/3850-MCT S1 RPM.CSV",100,1000);
				 data.listout();
				 ImpAnalyzerFrame  program = new ImpAnalyzerFrame(ttl);
				 program.setVisible(true);
				 program.analyze(data,ttl);
				 program.getPlot();
				 //program.ttlPanel.setValueAt("s", 3, 3);
				 //System.out.println(data.data.getY(0));  	
		    
	  }
	 });
		 
		}	
}
