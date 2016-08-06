package modules;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import javax.swing.JLabel;

public class Main extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChartPanel chartContainer;
	public ExtremaFinderWindow extremaFinderWindow;
	//NumericalAnalysis numericalAnalysis=new NumericalAnalysis();
	JFreeChart chart;
	public DataArray data,fit;
	TTLArray ttl;
	TTLPanel ttlPanel;
	XYSeriesCollection collection;
	JMenuItem openMenuItem,programMenuItem,extrema;
	JMenuBar menuBar;
	JMenu fileMenu,analysisMenu;
	JFileChooser chooser;
	ValueMarker marker;
	AskWindow askWin;
	XYSeries min,max;
	Object syncroot=new Object();
	NumericalAnalysis numericalAnalysis;
	PlotFrame plotFrame;
	private JLabel lblCord;
	
	
	public Main(){
		numericalAnalysis=new NumericalAnalysis();
		chooser = new JFileChooser();
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Load File");
		analysisMenu = new JMenu("Analysis");
		extrema = new JMenuItem("Extrema Finder");
		openMenuItem = new JMenuItem("Data File");
	    openMenuItem.setActionCommand("Data File");
	    programMenuItem = new JMenuItem("Program File");
	    programMenuItem.setActionCommand("Program File");
	    fileMenu.add(openMenuItem);
	    fileMenu.add(programMenuItem);
	    menuBar.add(fileMenu);
	    menuBar.add(analysisMenu);
	    analysisMenu.add(extrema);
	    setJMenuBar(menuBar);
	    fit = new DataArray("fit");
	    data= new DataArray("data");
	    min= new XYSeries("min",false);
	    max= new XYSeries("max",false);
	    collection = new XYSeriesCollection();
	    this.setVisible(true);
	   
	    
	    
	    getContentPane().setLayout(new MigLayout("", "[240.00][grow]", "[240.00,grow][41.00,grow][43.00][][][]"));
	    setVisible(true);
	    
	    
	    ttlPanel = new TTLPanel();
	    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
	    
	//inicjalizacja wykresu
	collection.addSeries(data.data);
	collection.addSeries(fit.data);
	collection.addSeries(min);
	collection.addSeries(max);
	final JFreeChart chart = ChartFactory.createXYLineChart(" ","Time[s]","Reflectance", collection,PlotOrientation.VERTICAL,true,true,false);
	chart.getXYPlot().setBackgroundPaint(Color.WHITE); //kolor obszaru kre�lenia
	chart.getXYPlot().setRangeGridlinePaint(Color.black); //kolor poziomych linii
	chart.getXYPlot().setDomainGridlinePaint(Color.black); //kolor pionowych linii
	XYPlot plot = (XYPlot) chart.getPlot();
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    renderer.setSeriesShapesVisible(1, false);
    renderer.setSeriesShapesVisible(0, false);
    renderer.setSeriesShapesVisible(2, true);
    renderer.setSeriesShapesVisible(3, true); 
    renderer.setSeriesLinesVisible(2, false);
    renderer.setSeriesLinesVisible(3, false);
    renderer.setSeriesShape(3, ShapeUtilities.createUpTriangle(5));
    renderer.setSeriesShape(3, ShapeUtilities.createDownTriangle(5));
    plot.setRenderer(renderer);
	


	
chartContainer = new ChartPanel(chart);
chartContainer.setDisplayToolTips(false);
getContentPane().add(chartContainer, "cell 1 0,grow");
	
chart.addProgressListener(new ChartProgressListener() {

    
    public void chartProgress(ChartProgressEvent cpe) {
    if(cpe.getType()==ChartProgressEvent.DRAWING_FINISHED){
     System.out.println("Click event!");
    XYPlot xyPlot2 = chartContainer.getChart().getXYPlot();
    lblCord.setText("X="+xyPlot2.getDomainCrosshairValue() + " "
        +"Y="+xyPlot2.getRangeCrosshairValue());
    }
    }
});
lblCord = new JLabel("X=... Y=...");
getContentPane().add(lblCord, "cell 0 1,alignx right");
	
		
		final OptionPanel options = new OptionPanel();
		options.addOptionChangeListener(new OptionChange() {
			
			
			@Override
			public void change() {
				
				 synchronized(syncroot){
					//PRERYSOWANIE
					 collection.removeSeries(max);
					 collection.removeSeries(min);
					 collection.removeSeries(fit.data);
					 //collection.addSeries(data.data);
					 
						fit = new DataArray(
								options.parameters[5],//Norma
								options.parameters[4],//tmin
								Double.parseDouble(options.spinnerTmax.getValue().toString()),//tmax
								options.parameters[0],//n
								options.parameters[1],//k
								options.parameters[2],//ns
								options.parameters[3],//ks
								options.parameters[6],//G
								Double.parseDouble(options.spinnerLambda.getValue().toString()),//Lam
								10000);
						//System.out.println(options.parameters[5]);
						//chart.getXYPlot().getDomainAxis().setRange(Double.parseDouble(options.spinnerXMIN.getValue().toString()),
						//		Double.parseDouble(options.spinnerXMAX.getValue().toString()));
						//chart.getXYPlot().getRangeAxis().setRange(data.data.getMinY(),data.data.getMaxY());
						
						//collection.addSeries(data.data);
						collection.addSeries(fit.data);
						collection.addSeries(min);
						collection.addSeries(max);
						
						
						
						
				 }
				
			}
		});
		
		getContentPane().add(options, "cell 0 0,grow");
		
		
		chartContainer.setMinimumDrawHeight(250);
		chartContainer.setMaximumDrawHeight(1200);
		chartContainer.setMaximumDrawWidth(1600);
		
		
		
		
	
		
	   openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					//rysowaanie
					
					askWin = new AskWindow();
				
					askWin.btnOk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int L=Integer.parseInt(askWin.spinner.getValue().toString());
			            	File selectedFile=chooser.getSelectedFile();
							 
			            	 collection.removeSeries(max);
			            	 collection.removeSeries(min);
			            	 collection.removeSeries(fit.data);
							 collection.removeSeries(data.data);
							 data = new DataArray(selectedFile.getPath(),L);
							 collection.addSeries(data.data);
							 collection.addSeries(fit.data);
							 collection.addSeries(min);
							 collection.addSeries(max);
				                
				                try{
				                	getContentPane().remove(ttlPanel);
					                String path =selectedFile.getParent()+"/"+selectedFile.getName().substring(0,8)+" L.CSV";
				                	ttl = new TTLArray(path);
				                    ttlPanel = new TTLPanel(ttl);
				                    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
									getContentPane().revalidate();
									getContentPane().repaint();
									for(TTL item : ttl.data){
										marker = new ValueMarker(item.start);  // position is the value on the axis
										marker.setPaint(Color.green);
										//marker.
										XYPlot plot = (XYPlot) chart.getPlot();
										plot.addDomainMarker(marker);
									
										ttlPanel.addSelectRecordListener(new SelectRecord() {
											
											@Override
											public void selectRecord() {
												//options.spinnerXMIN.setValue(ttlPanel.selected);
												//options.spinnerXMAX.setValue(ttlPanel.selected+5000.0);
												chart.getXYPlot().getDomainAxis().setRange(ttlPanel.start,ttlPanel.finish);
											}
										});

									
				                }
									}catch(Exception e2){
				                	JOptionPane.showMessageDialog(null, "ups",null,JOptionPane.INFORMATION_MESSAGE);
				                	
				                }
				                askWin.dispose();
							
						}
					});
		                
						 
		                
				 

				}
				
			}});
	   
	   programMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					//rysowaanie
					
					getContentPane().remove(ttlPanel);
					File selectedFile=chooser.getSelectedFile();
					ttl = new TTLArray(selectedFile.getPath());
                    ttlPanel = new TTLPanel(ttl);
                    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
					getContentPane().revalidate();
					getContentPane().repaint();
					for(TTL item : ttl.data){
						marker = new ValueMarker(item.start);  // position is the value on the axis
						marker.setPaint(Color.green);
						//marker.
						XYPlot plot = (XYPlot) chart.getPlot();
						plot.addDomainMarker(marker);

					}
					
					//ttlPanel.setSize(900, 300);
					ttlPanel.addSelectRecordListener(new SelectRecord() {
						
						@Override
						public void selectRecord() {
							//options.spinnerXMIN.setValue(ttlPanel.selected);
							//options.spinnerXMAX.setValue(ttlPanel.selected+5000.0);
							chart.getXYPlot().getDomainAxis().setRange(ttlPanel.finish,ttlPanel.finish);
						}
					});

				}
				
			}});
	   
	   extrema.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			extremaFinderWindow= new ExtremaFinderWindow(data.data);
			
			extremaFinderWindow.btnSaveAs.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					numericalAnalysis.SaveToFile();
					
				}
			});
			
extremaFinderWindow.btnGrowthSpeed.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					plotFrame= new PlotFrame(numericalAnalysis.calculateGrowthSpeed(options.parameters[0],
							Double.parseDouble(options.spinnerLambda.getValue().toString())));
					plotFrame.btnSave.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
						numericalAnalysis.SaveToFile2(options.parameters[0], 
								Double.parseDouble(options.spinnerLambda.getValue().toString()));	
						}
					});
					
				}
			});
			
			
			   
			
			
			
			 extremaFinderWindow.addExtremaChangeListener(new ExtremaChange() {
					
					@Override
					public void extremaChange() {
						numericalAnalysis.peakDet(data.data,
								extremaFinderWindow.delta,
								extremaFinderWindow.getFrom(),
								extremaFinderWindow.getTo()
								);
						
						
						
						collection.removeSeries(max);
						 collection.removeSeries(min);
						 collection.removeSeries(fit.data);
						 //collection.addSeries(data.data);
						 
							fit = new DataArray(
									options.parameters[5],//Norma
									options.parameters[4],//tmin
									Double.parseDouble(options.spinnerTmax.getValue().toString()),//tmax
									options.parameters[0],//n
									options.parameters[1],//k
									options.parameters[2],//ns
									options.parameters[3],//ks
									options.parameters[6],//G
									Double.parseDouble(options.spinnerLambda.getValue().toString()),//Lam
									10000);
							
							max=numericalAnalysis.getMax();
							min=numericalAnalysis.getMin();
							
							//chart.getXYPlot().getDomainAxis().setRange(Double.parseDouble(options.spinnerXMIN.getValue().toString()),
							//		Double.parseDouble(options.spinnerXMAX.getValue().toString()));
							//chart.getXYPlot().getRangeAxis().setRange(data.data.getMinY(),data.data.getMaxY());
							
							
							//collection.addSeries(data.data);
							collection.addSeries(fit.data);
							collection.addSeries(min);
							collection.addSeries(max);
							numericalAnalysis.mergeArray();
						
						
					}
				});
			
		}
	});
	   
	  
	  
	   
	   
	}
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
		    public void run() {
		Main program =new Main();
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.setSize(1200, 800);
        program.setTitle("Simple Interferogram Generator");
        program.setVisible(true);
		    }
	      });
		
	}

}
