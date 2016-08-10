package modules;


import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;


public class OptionPanel  extends JPanel{
	
	/*
	 * @author Micha� Murawski
	 * @param
	 * @return
	 * This class extends JPanel to provide element of GUI which takes all
	 * parameters of wave function and potential given by user. It consist method
	 * to take given parameters.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double[] parameters;
	public boolean[] checkList;
	public JSpinner spinnerNmin, spinnerNmax,spinnerKmin, spinnerKmax,spinnerNsmin, spinnerNsmax,spinnerKsmin, spinnerKsmax,spinnerGmin, spinnerGmax,spinnerTmin, spinnerTmax,spinnerNormmin, spinnerNormmax,spinnerLambda;
	public JSlider sliderN,sliderK,sliderNs,sliderKs,sliderG,sliderT,sliderNorm;
	public JSpinner spinnerXMAX;
	public JSpinner spinnerXMIN;
	private JButton btnCdTe_GaAs,btnCdTe_HgTe,btnHgTe_CdTe;
	private List<OptionChange> listeners = new LinkedList<>();
	public JButton btnFit;
	public JLabel lblN,lblNs,lblK,lblKs,lblG,lblT,lblLam,lblNorm;
	NumberFormat formatter = new DecimalFormat("#0.000");  
	
	OptionPanel(){
		super();
		parameters = new double[] {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,650};	//wave function parameters	//potential parameters
		checkList = new boolean[8];	//list of boolean which is being use to check if all parameters are set
		checkList[4]=true;
		
		setLayout(new MigLayout("", "[30][180][30]", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		
		
		lblN = new JLabel("n=1");
		add(lblN, "cell 1 0,alignx center");
		
		spinnerNmin = new JSpinner(new SpinnerNumberModel(1, 0, 10, 0.01));
		spinnerNmin.setMinimumSize(new Dimension(60,10));
		add(spinnerNmin, "flowx,cell 0 1,alignx left");
		
		sliderN = new JSlider();
		sliderN.setValue(0);
		sliderN.setMaximum(1000);
		sliderN.setMinimum(1);
		add(sliderN, "cell 1 1");
		
		sliderN.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerNmin.getValue().toString());
				double b = Double.parseDouble(spinnerNmax.getValue().toString());
				double c = (double)sliderN.getValue();
				parameters[3]=a+(b-a)*c/1000.0;
				lblN.setText("n="+formatter.format(parameters[3]));
				fireOptionChangeEvent();
		        //System.out.println(c);
		        
			}}
			);
		
		spinnerNmax = new JSpinner(new SpinnerNumberModel(1.5, 0, 10, 0.01));
		spinnerNmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerNmax, "cell 2 1");
		
		
		lblK = new JLabel("k=1");
		add(lblK, "cell 1 2,alignx center");
		
		spinnerKmin = new JSpinner(new SpinnerNumberModel(1, 0, 4, 0.01));
		spinnerKmin.setMinimumSize(new Dimension(60,10));
		add(spinnerKmin, "flowx,cell 0 3,alignx left");
		
		sliderK = new JSlider();
		sliderK.setValue(0);
		sliderK.setMaximum(1000);
		sliderK.setMinimum(1);
		add(sliderK, "cell 1 3");
		
		sliderK.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerKmin.getValue().toString());
				double b = Double.parseDouble(spinnerKmax.getValue().toString());
				double c = (double)sliderK.getValue();
				parameters[4]=a+(b-a)*c/1000.0;
				lblK.setText("k="+formatter.format(parameters[4]));
				fireOptionChangeEvent();
				
		        
			}}
			);
		
		spinnerKmax = new JSpinner(new SpinnerNumberModel(1.5, 0, 4, 0.01));
		spinnerKmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerKmax, "cell 2 3");
		
		
		lblNs = new JLabel("ns=1");
		add(lblNs, "cell 1 4,alignx center");
		
		spinnerNsmin = new JSpinner(new SpinnerNumberModel(1, 0, 10, 0.01));
		spinnerNsmin.setMinimumSize(new Dimension(60,10));
		add(spinnerNsmin, "flowx,cell 0 5,alignx left");
		
		sliderNs = new JSlider();
		sliderNs.setValue(0);
		sliderNs.setMaximum(1000);
		sliderNs.setMinimum(1);
		add(sliderNs, "cell 1 5");
		
		spinnerNsmax = new JSpinner(new SpinnerNumberModel(1.5, 0, 10, 0.01));
		spinnerNsmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerNsmax, "cell 2 5");
		
		sliderNs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerNsmin.getValue().toString());
				double b = Double.parseDouble(spinnerNsmax.getValue().toString());
				double c = (double)sliderNs.getValue();
				parameters[5]=a+(b-a)*c/1000.0;
				lblNs.setText("ns="+formatter.format(parameters[5]));
				fireOptionChangeEvent();
		        //System.out.println(c);
				
		        
			}}
			);
		
		lblKs = new JLabel("ks=1");
		add(lblKs, "cell 1 6,alignx center");
		
		spinnerKsmin = new JSpinner(new SpinnerNumberModel(1, 0, 10, 0.01));
		spinnerKsmin.setMinimumSize(new Dimension(60,10));
		add(spinnerKsmin, "flowx,cell 0 7,alignx left");
		
		sliderKs = new JSlider();
		sliderKs.setValue(0);
		sliderKs.setMaximum(1000);
		sliderKs.setMinimum(1);
		add(sliderKs, "cell 1 7");
		
		spinnerKsmax = new JSpinner(new SpinnerNumberModel(1.5, 0, 10, 0.01));
		spinnerKsmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerKsmax, "cell 2 7");
		
		sliderKs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerKsmin.getValue().toString());
				double b = Double.parseDouble(spinnerKsmax.getValue().toString());
				double c = (double)sliderKs.getValue();
				parameters[6]=a+(b-a)*c/1000.0;
				lblKs.setText("ks="+formatter.format(parameters[6]));
				fireOptionChangeEvent();
				
		        //System.out.println(c);
				
		        
			}}
			);
		
		lblT = new JLabel("t0..");
		add(lblT, "cell 1 8,alignx center");
		
		sliderT = new JSlider();
		sliderT.setValue(0);
		sliderT.setMaximum(10000);
		sliderT.setMinimum(0);
		add(sliderT, "cell 1 9");
		
		spinnerTmax = new JSpinner(new SpinnerNumberModel(10000, 0, 30000, 0.1));
		spinnerTmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerTmax, "cell 2 9");
		
		sliderT.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerTmin.getValue().toString());
				double b = Double.parseDouble(spinnerTmax.getValue().toString());
				double c = (double)sliderT.getValue();
				parameters[1]=a+(b-a)*c/10000.0;
				parameters[2]=b;
				lblT.setText("t0="+formatter.format(parameters[1]));
				fireOptionChangeEvent();
		        //System.out.println(c);
				
		        
			}}
			);
		
		spinnerTmin = new JSpinner(new SpinnerNumberModel(0,0,30000, 0.1));
		spinnerTmin.setMinimumSize(new Dimension(60,10));
		add(spinnerTmin, "flowx,cell 0 9,alignx left");
		
		lblNorm = new JLabel("Norm=1");
		add(lblNorm, "cell 1 10,alignx center");
		
		spinnerNormmin = new JSpinner(new SpinnerNumberModel(0, -1000000, 1000000, 0.001));
		spinnerNormmin.setMinimumSize(new Dimension(60,10));
		add(spinnerNormmin, "flowx,cell 0 11,alignx left");
		
		sliderNorm = new JSlider();
		sliderNorm.setValue(100);
		sliderNorm.setMaximum(100000);
		sliderNorm.setMinimum(1);
		add(sliderNorm, "cell 1 11");
		
		sliderNorm.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerNormmin.getValue().toString());
				double b = Double.parseDouble(spinnerNormmax.getValue().toString());
				double c = (double)sliderNorm.getValue();
				parameters[0]=a+(b-a)*(c)/100000.0;
				lblNorm.setText("Norm="+formatter.format(parameters[0]));
				fireOptionChangeEvent();
		        //System.out.println(c);
				
		        
			}}
			);
		
		spinnerNormmax = new JSpinner(new SpinnerNumberModel(1, -1000000, 1000000, 0.01));
		spinnerNormmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerNormmax, "cell 2 11");
		
		lblG = new JLabel("G[nm/s]=1");
		add(lblG, "cell 1 12,alignx center");
		
		spinnerGmin = new JSpinner(new SpinnerNumberModel(0, 0, 5, 0.01));
		spinnerGmin.setMinimumSize(new Dimension(60,10));
		add(spinnerGmin, "flowx,cell 0 13,alignx left");
		
		sliderG = new JSlider();
		sliderG.setValue(0);
		sliderG.setMaximum(1000);
		sliderG.setMinimum(0);
		add(sliderG, "cell 1 13");
		
		sliderG.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double a = Double.parseDouble(spinnerGmin.getValue().toString());
				double b = Double.parseDouble(spinnerGmax.getValue().toString());
				double c = (double)sliderG.getValue();
				parameters[7]=a+(b-a)*c/1000.0;
				lblG.setText("G[nm/s]="+formatter.format(parameters[7]));
				fireOptionChangeEvent();
		        //System.out.println(c);
				
		        
			}}
			);
		
		spinnerGmax = new JSpinner(new SpinnerNumberModel(1, 0, 5, 0.01));
		spinnerGmax.setMinimumSize(new Dimension(60, 10));
		add(spinnerGmax, "cell 2 13");
		
		lblLam = new JLabel("Lambda [nm]=");
		add(lblLam, "flowx,cell 1 18,alignx left");
		spinnerLambda = new JSpinner(new SpinnerNumberModel(650, 100, 900, 1));
		spinnerLambda.setMinimumSize(new Dimension(60, 10));
		add(spinnerLambda, "cell 1 18");
		
		spinnerLambda.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				parameters[8]=Double.parseDouble(spinnerLambda.getValue().toString());
				fireOptionChangeEvent();
				
			}
		});
		
		btnCdTe_GaAs = new JButton("CdTe/GaAs");
		
		btnCdTe_GaAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setN(3.04);
				setK(0.2503);
				setNs(3.87);
				setKs(0.304);
				
				fireOptionChangeEvent();
			}
		});
		add(btnCdTe_GaAs, "cell 0 20");
		
		btnCdTe_HgTe = new JButton("CdTe/HgTe");
		btnCdTe_HgTe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setN(3.04);
				setK(0.2503);
				setNs(4.1);
				setKs(1.5);
				
				fireOptionChangeEvent();
			}
		});
		
		add(btnCdTe_HgTe, "cell 0 21");
		
		btnHgTe_CdTe = new JButton("HgTe/CdTe");
		btnHgTe_CdTe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNs(3.04);
				setKs(0.2503);
				setN(4.1);
				setK(1.5);
				
				
				fireOptionChangeEvent();
			}
		});
		
		add(btnHgTe_CdTe, "cell 0 22");
		
		
		setN(3.04);
		setK(0.2503);
		setNs(3.87);
		setKs(0.304);
		setNorm(1);
		
		btnFit = new JButton("Fit");
		add(btnFit, "cell 0 23,growx");
		
		btnFit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireOptionChangeEvent();
				
			}
		});
		
		
		
	}
	
	/*
	 * @author Micha� Murawski
	 * @param null
	 * @return boolean(true if its ready, false if its not)
	 */
		public void setVariables(int[] variables1, int[] variables2){

		
		
	}
	
		
	public void addOptionChangeListener(OptionChange optionChange){
		listeners.add(optionChange);
	}
	
	private void fireOptionChangeEvent(){
		for(OptionChange oc: listeners)
			oc.change();
	}
	
	
	
	
	public void setVariables(double... v){
		setNorm(v[0]);
		setG(v[1]);
		setN(v[2]);
		setK(v[3]);
		setKs(v[4]);
		//setKs()
		
		
	}
	
	public void setN(double n){
		this.spinnerNmin.setValue(n*0.9);
		this.spinnerNmax.setValue(n*1.1);
		this.sliderN.setValue(500);
		double a = Double.parseDouble(spinnerNmin.getValue().toString());
		double b = Double.parseDouble(spinnerNmax.getValue().toString());
		double c = (double)sliderN.getValue();
		parameters[3]=a+(b-a)*c/1000.0;
		this.lblN.setText("n="+formatter.format(parameters[3]));
	}
	
	public void setNs(double n){
		this.spinnerNsmin.setValue(n*0.9);
		this.spinnerNsmax.setValue(n*1.1);
		this.sliderNs.setValue(500);
		double a = Double.parseDouble(spinnerNsmin.getValue().toString());
		double b = Double.parseDouble(spinnerNsmax.getValue().toString());
		double c = (double)sliderNs.getValue();
		parameters[5]=a+(b-a)*c/1000.0;
		this.lblNs.setText("ns="+formatter.format(parameters[5]));
	}
	
	public void setK(double k){
		this.spinnerKmin.setValue(k*0.9);
		this.spinnerKmax.setValue(k*1.1);
		this.sliderK.setValue(500);
		double a = Double.parseDouble(spinnerKmin.getValue().toString());
		double b = Double.parseDouble(spinnerKmax.getValue().toString());
		double c = (double)sliderK.getValue();
		parameters[4]=a+(b-a)*c/1000.0;
		this.lblK.setText("k="+formatter.format(parameters[4]));
	}
	
	public void setKs(double k){
		this.spinnerKsmin.setValue(k*0.9);
		this.spinnerKsmax.setValue(k*1.1);
		this.sliderK.setValue(500);
		double a = Double.parseDouble(spinnerKsmin.getValue().toString());
		double b = Double.parseDouble(spinnerKsmax.getValue().toString());
		double c = (double)sliderKs.getValue();
		parameters[6]=a+(b-a)*c/1000.0;
		this.lblKs.setText("ks="+formatter.format(parameters[6]));
	}
	
	public void setG(double g){
		this.spinnerGmin.setValue(g*0.9);
		this.spinnerGmax.setValue(g*1.1);
		this.sliderG.setValue(500);
		double a = Double.parseDouble(spinnerGmin.getValue().toString());
		double b = Double.parseDouble(spinnerGmax.getValue().toString());
		double c = (double)sliderG.getValue();
		parameters[7]=a+(b-a)*c/1000.0;
		lblG.setText("G[nm/s]="+formatter.format(parameters[7]));

	}
	
	public void setNorm(double n){
		this.spinnerNormmin.setValue(n*0.9);
		this.spinnerNormmax.setValue(n*1.1);
		this.sliderNorm.setValue(50000);
		double a = Double.parseDouble(spinnerNormmin.getValue().toString());
		double b = Double.parseDouble(spinnerNormmax.getValue().toString());
		double c = (double)sliderNorm.getValue();
		parameters[0]=a+(b-a)*(c)/100000.0;
		lblNorm.setText("Norm="+formatter.format(parameters[0]));
	}
	
	public void setT(double t){
		this.spinnerTmin.setValue(t*0.9);
		this.spinnerTmax.setValue(t*1.1);
		this.sliderT.setValue(5000);
		double a = Double.parseDouble(spinnerTmin.getValue().toString());
		double b = Double.parseDouble(spinnerTmax.getValue().toString());
		double c = (double)sliderT.getValue();
		parameters[1]=a+(b-a)*c/10000.0;
		parameters[2]=b;
		lblT.setText("t0="+formatter.format(parameters[1]));
		
	}
	
	

	
	public double[] getVariables(){
		return new double[]{
			parameters[0],
			parameters[7],
			parameters[3],
			parameters[4],
			parameters[5],
			parameters[6],
			parameters[8]
		};
	}
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
		    public void run() {
		JFrame program =new JFrame();
		OptionPanel options=new OptionPanel();
		program.getContentPane().setLayout(new MigLayout("", "[360.00][grow]", "[435.00][41.00][43.00][][][]"));
		program.getContentPane().add(options, "cell 0 0,grow");
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.setSize(1200, 800);
        program.setTitle("Simple Interferogram Generator");
        program.setVisible(true);
		    }
	      });
		
	}
}
