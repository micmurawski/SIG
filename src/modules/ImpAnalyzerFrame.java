package modules;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class ImpAnalyzerFrame extends JFrame{
	
	/**
	 * 
	 */
	double delta=600,lambda=650,CdTe=3.04,HgTe=4.1;
	private static final long serialVersionUID = 1L;
	NumericalAnalysis numericalAnalysis;
	//public XYSeries growthRateHgTe,growthRateCdTe,NsHgTe,NsCdTe,NormHgTe,NormCdTe;
	PlotFrame plotFrame;
	public JButton btnAnalyze;
	JSpinner spinnerCdTe,spinnerHgTe,spinnerLambda;
	boolean[] opt;
	double[] initialCoeffs1,initialCoeffs2;
	public JButton btnReset;
	
	public ImpAnalyzerFrame(final DataSeries data,final TTLPanel ttlPanel){
		super();
		initialCoeffs1= new double[] {0.0,1.0,3.04,0.253,4.1,1.5,650};
		initialCoeffs2= new double[] {0.0,1.0,4.1,1.5,3.04,0.253,650};
		opt= new boolean[]{true,false,false,true,false,false};
		numericalAnalysis = new NumericalAnalysis();
	
		this.getContentPane().setLayout(new MigLayout("", "[][grow][][]", "[][][][][][][][][][][]"));
		//this.setDefaultCloseOperation(ImpAnalyzerFrame.EXIT_ON_CLOSE);
        this.setTitle("IMP analyzer");
        this.setVisible(true);
        this.setSize(396, 550);
        this.setDefaultCloseOperation(ImpAnalyzerFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("","[][]","[][][][]"));
		JLabel lblCdTeOpt = new JLabel("CdTe Options: ");
		panel.add(lblCdTeOpt,"cell 0 0");
		final JRadioButton rBtnCdTe1 = new JRadioButton("use Min Max analyzer");
		panel.add(rBtnCdTe1,"cell 0 1");
		rBtnCdTe1.setSelected(true);
		final JRadioButton rBtnCdTe2 = new JRadioButton("use Levenberg-Marquardt analyzer");
		panel.add(rBtnCdTe2,"cell 0 2");
		final JRadioButton rBtnCdTe3 = new JRadioButton("none");
		panel.add(rBtnCdTe3,"cell 0 3");
		JLabel lblHgTeOpt = new JLabel("HgTe Options: ");
		panel.add(lblHgTeOpt,"cell 0 4");
		
		final JRadioButton rBtnHgTe1 = new JRadioButton("use Min Max analyzer");
		panel.add(rBtnHgTe1,"cell 0 5");
		rBtnHgTe1.setSelected(true);
		final JRadioButton rBtnHgTe2 = new JRadioButton("use Levenberg-Marquardt analyzer");
		panel.add(rBtnHgTe2,"cell 0 6");
		final JRadioButton rBtnHgTe3 = new JRadioButton("none");
		panel.add(rBtnHgTe3,"cell 0 7");
		
		
		getContentPane().add(panel, "cell 0 0,growx,aligny top");
		
		
		//this.getContentPane().add(ttlPanel, "cell 0 0");
		
		JLabel lblHgTe = new JLabel("HgTe");
		getContentPane().add(lblHgTe, "flowx,cell 0 2");
		
		JLabel lblCdTe = new JLabel("CdTe");
		getContentPane().add(lblCdTe, "flowx,cell 0 3");
		
		JLabel lblLambda = new JLabel("Lambda [nm] =");
		getContentPane().add(lblLambda, "flowx,cell 0 4");
		
		spinnerHgTe = new JSpinner(new SpinnerNumberModel(4.1, 0.1, 10, 0.01));
		getContentPane().add(spinnerHgTe, "cell 0 2");
		spinnerHgTe.setMinimumSize(new Dimension(60,10));
		
		spinnerCdTe = new JSpinner(new SpinnerNumberModel(3.04, 0.1, 10, 0.01));
		getContentPane().add(spinnerCdTe, "cell 0 3");
		spinnerCdTe.setMinimumSize(new Dimension(60,10));
		
		spinnerLambda = new JSpinner(new SpinnerNumberModel(650, 1, 1000, 0.01));
		getContentPane().add(spinnerLambda, "cell 0 4");
		
		btnAnalyze = new JButton("Analyze");
		getContentPane().add(btnAnalyze, "flowx,cell 0 5");
		
		JButton btnGetGrowthRate = new JButton("Get Growth Rate Plot");
		getContentPane().add(btnGetGrowthRate, "cell 0 6,growx");
		
		btnGetGrowthRate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				plotFrame=new PlotFrame(ttlPanel.growthRateCdTe,ttlPanel.growthRateHgTe,"Time [s]","Growth rate [um/h]");
						
					}
		});
		JButton btnGetEffN = new JButton("Get Ns Plot");
		getContentPane().add(btnGetEffN, "flowx,cell 0 7,growx");
		
btnGetEffN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				plotFrame= new PlotFrame(ttlPanel.NsCdTe,ttlPanel.NsHgTe,"Time [s]","eff. Ns");			
			}
		});
		
		JButton btnGetNorm = new JButton("Get Norm Plot");
		getContentPane().add(btnGetNorm, "cell 0 8,growx");
		
		btnReset = new JButton("Reset");
		getContentPane().add(btnReset, "cell 0 5");
		
		btnGetNorm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				plotFrame= new PlotFrame(ttlPanel.NormCdTe,ttlPanel.NormHgTe,"Time [s]","Norm");			
			}
		});
		
		
	btnAnalyze.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			analyze(data,ttlPanel);
			
		}
	});
		
		
		rBtnCdTe1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(rBtnCdTe1.isSelected()){
				opt[0]=true;
				opt[1]=false;
				opt[2]=false;
				rBtnCdTe1.setSelected(true);
				rBtnCdTe2.setSelected(false);
				rBtnCdTe3.setSelected(false);
			}else{
				rBtnCdTe1.setSelected(true);
				rBtnCdTe2.setSelected(false);
				rBtnCdTe3.setSelected(false);
			}
				System.out.println(Arrays.toString(opt));
			}
		});
		
		
		rBtnCdTe2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rBtnCdTe2.isSelected()){
					opt[0]=false;
					opt[1]=true;
					opt[2]=false;
					rBtnCdTe2.setSelected(true);
					rBtnCdTe1.setSelected(false);
					rBtnCdTe3.setSelected(false);
				}else{
					rBtnCdTe2.setSelected(true);
					rBtnCdTe1.setSelected(false);
					rBtnCdTe3.setSelected(false);
				}
				System.out.println(Arrays.toString(opt));
			}
		});
		
		rBtnCdTe3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rBtnCdTe3.isSelected()){
					opt[0]=false;
					opt[1]=false;
					opt[2]=true;
					rBtnCdTe2.setSelected(false);
					rBtnCdTe1.setSelected(false);
					rBtnCdTe3.setSelected(true);
				}else{
					rBtnCdTe2.setSelected(false);
					rBtnCdTe1.setSelected(false);
					rBtnCdTe3.setSelected(true);
				}
				System.out.println(Arrays.toString(opt));
			}
		});
		
		rBtnHgTe1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(rBtnHgTe1.isSelected()){
				opt[3]=true;
				opt[4]=false;
				opt[5]=false;
				rBtnHgTe1.setSelected(true);
				rBtnHgTe2.setSelected(false);
				rBtnHgTe3.setSelected(false);
			}else{
				rBtnHgTe1.setSelected(true);
				rBtnHgTe2.setSelected(false);
				rBtnHgTe3.setSelected(false);
			}
			System.out.println(Arrays.toString(opt));
			}
		});
		
		
		rBtnHgTe2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rBtnHgTe2.isSelected()){
					opt[3]=false;
					opt[4]=true;
					opt[5]=false;
					rBtnHgTe1.setSelected(false);
					rBtnHgTe2.setSelected(true);
					rBtnHgTe3.setSelected(false);
				}else{
					rBtnHgTe1.setSelected(false);
					rBtnHgTe2.setSelected(true);
					rBtnHgTe3.setSelected(false);
				}
				System.out.println(Arrays.toString(opt));
			}
		});

	rBtnHgTe3.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(rBtnHgTe3.isSelected()){
			opt[3]=false;
			opt[4]=false;
			opt[5]=true;
			rBtnHgTe1.setSelected(false);
			rBtnHgTe2.setSelected(false);
			rBtnHgTe3.setSelected(true);
		}else{
			rBtnHgTe1.setSelected(false);
			rBtnHgTe2.setSelected(false);
			rBtnHgTe3.setSelected(true);
		}
		System.out.println(Arrays.toString(opt));
	}
});
		
	}
	
	
	
	public void analyze(final DataSeries data,final TTLPanel ttlPanel){
		
		ttlPanel.resetSeries();
		initialCoeffs1[6]=Double.parseDouble(spinnerLambda.getValue().toString());
		initialCoeffs2[6]=Double.parseDouble(spinnerLambda.getValue().toString());
		lambda=initialCoeffs1[6]=Double.parseDouble(spinnerLambda.getValue().toString());
		HgTe=Double.parseDouble(spinnerHgTe.getValue().toString());
		CdTe=Double.parseDouble(spinnerCdTe.getValue().toString());
		initialCoeffs1[2]=CdTe;
		initialCoeffs2[2]=HgTe;
		final LoadingWindow loadingWindow = new LoadingWindow("Calculating...");
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try{
				
				
				for(int ii=0;ii<ttlPanel.getLen();ii++){
					if(ttlPanel.getLabel(ii).equals("HgTe/CdTe")){
						if(opt[3]){
							double a=numericalAnalysis.findXforMinY(data,
									ttlPanel.getStart(ii), 
									ttlPanel.getFinish(ii));
							double b=numericalAnalysis.findXforMaxY(data,
									ttlPanel.getStart(ii), 
									ttlPanel.getFinish(ii));
							ttlPanel.growthRateHgTe.add(a,3.6*(lambda/(4*HgTe*(b-a))));
							ttlPanel.setValueAt(3.6*lambda/(4*HgTe*(b-a)),ii,3);
							
					}else if(opt[4]){
						 System.out.println("znaleziono HgTe dopasowanie MNK!");
						
						 if(Double.parseDouble(ttlPanel.getValueAt(ii,4).toString())!=0){
							   initialCoeffs2[0]=Double.parseDouble(ttlPanel.getValueAt(ii,4).toString());
						   }else{
							   initialCoeffs2[0]=0.0;
						   }
						   if(Double.parseDouble(ttlPanel.getValueAt(ii,3).toString())!=0){
							   initialCoeffs2[1]=Double.parseDouble(ttlPanel.getValueAt(ii,3).toString());
						   }else{
							   initialCoeffs2[1]=1.8;
						   }
						   if(Double.parseDouble(ttlPanel.getValueAt(ii,5).toString())!=0){
							   initialCoeffs2[4]=Double.parseDouble(ttlPanel.getValueAt(ii,5).toString());  
						   }else{
							   initialCoeffs2[4]=3.04;
						   }
						 
						 
							MyFuncFitter myFuncFitter = new MyFuncFitter();
							initialCoeffs2=myFuncFitter.fit(data,
									ttlPanel.getStart(ii),
									ttlPanel.getFinish(ii),
									initialCoeffs2
									);
							System.out.print(Arrays.toString(initialCoeffs2));
							ttlPanel.setValueAt(initialCoeffs2[1],ii,3);
							ttlPanel.growthRateHgTe.add(ttlPanel.getStart(ii),initialCoeffs2[1]);
							ttlPanel.setValueAt(initialCoeffs2[4],ii,5);
							ttlPanel.NsHgTe.add(ttlPanel.getStart(ii),initialCoeffs2[4]);
							ttlPanel.setValueAt(initialCoeffs2[0],ii,4);
							ttlPanel.NormHgTe.add(ttlPanel.getStart(ii),initialCoeffs2[0]);
						
					}
						
						
					}else if(ttlPanel.getLabel(ii).equals("CdTe/HgTe")){
						if(opt[0]){
						double a=numericalAnalysis.findXforMaxY(data,
								ttlPanel.getStart(ii), 
								ttlPanel.getFinish(ii));
						double b=numericalAnalysis.findXforMinY(data,
								ttlPanel.getStart(ii), 
								ttlPanel.getFinish(ii));
						ttlPanel.growthRateCdTe.add(a,3.6*(lambda/(4*CdTe*(b-a))));
						ttlPanel.setValueAt(3.6*lambda/(4*CdTe*(b-a)),ii,3);
						System.out.println("znaleziono CdTE "+"Min: "+b+"Max: "+a);
					}else if(opt[1]){
						
						   System.out.println("znaleziono CdTE dopasowanie MNK!");
						   
						   if(Double.parseDouble(ttlPanel.getValueAt(ii,4).toString())!=0){
							   initialCoeffs1[0]=Double.parseDouble(ttlPanel.getValueAt(ii,4).toString());
						   }else{
							   initialCoeffs1[0]=0.0;
						   }
						   if(Double.parseDouble(ttlPanel.getValueAt(ii,3).toString())!=0){
							   initialCoeffs1[1]=Double.parseDouble(ttlPanel.getValueAt(ii,3).toString());
						   }else{
							   initialCoeffs1[1]=1.8;
						   }
						   if(Double.parseDouble(ttlPanel.getValueAt(ii,5).toString())!=0){
							   initialCoeffs1[4]=Double.parseDouble(ttlPanel.getValueAt(ii,5).toString());  
						   }else{
							   initialCoeffs1[4]=4.1;
						   }
							 
							 
						   
							MyFuncFitter myFuncFitter = new MyFuncFitter();
						
							initialCoeffs1=myFuncFitter.fit(data,
									ttlPanel.getStart(ii),
									ttlPanel.getFinish(ii),
									initialCoeffs1
									);
							System.out.print(Arrays.toString(initialCoeffs1));
							ttlPanel.setValueAt(initialCoeffs1[1],ii,3);
							ttlPanel.growthRateCdTe.add(ttlPanel.getStart(ii),initialCoeffs1[1]);
							ttlPanel.setValueAt(initialCoeffs1[4],ii,5);
							ttlPanel.NsCdTe.add(ttlPanel.getStart(ii),initialCoeffs1[4]);
							ttlPanel.setValueAt(initialCoeffs1[0],ii,4);
							ttlPanel.NormCdTe.add(ttlPanel.getStart(ii),initialCoeffs1[0]);
							
							
						}
						
					}else{
						System.out.println("NIC NIE DODANO");
					}
					initialCoeffs1= new double[] {0.0,1.0,3.04,0.253,4.1,1.5,650};
					initialCoeffs2= new double[] {0.0,1.0,4.1,1.5,3.04,0.253,650};
				}
				}catch(Exception e){
				System.out.println(e.toString());	
				}
				
				
				SwingUtilities.invokeLater(new Runnable() {
	                public void run() {
	                    loadingWindow.setVisible(false);
	                }
	            });
			}
			
			
		};
		
		new Thread(runnable).start();
		
		
		
		
		
	}
	
	public void getPlot(){
		//plotFrame=new PlotFrame(resultCdTe,resultCdTe);
	}


	
	
	
	public static void main(String [] args)
		{
			SwingUtilities.invokeLater(new Runnable() {
			
		   public void run() {
			   final TTLPanel ttl = new TTLPanel("/home/vigo/Pulpit/reflaktancja/3850-MCT L.CSV"); 
				 DataSeries data = new DataSeries("r(t)",false,"/home/vigo/Pulpit/reflaktancja/3850-MCT S1 RPM.CSV",1,1);
				 //data.listout();
				 ImpAnalyzerFrame  program = new ImpAnalyzerFrame(data,ttl);
				 System.out.println(ttl.table.getValueAt(0, 3));
				 program.btnReset.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					ttl.reset();	
					}
				});
		    
	  }
	 });
		 
		}	
}
