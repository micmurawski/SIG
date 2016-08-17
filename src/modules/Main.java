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
import java.util.Arrays;

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
	public ImpAnalyzerFrame impAnalyzerFrame;
	JFreeChart chart;
	public DataSeries data;

	DataSeries fit;
	TTLArray ttl;
	TTLPanel ttlPanel;
	XYSeriesCollection collection;
	JMenuItem openMenuItem,programMenuItem,extrema,impAnalyzer,saveTable;
	JMenuBar menuBar;
	JMenu fileMenu,analysisMenu,saveData;
	JFileChooser chooser;
	ValueMarker marker;
	AskWindow askWin;
	XYSeries min,max;
	NumericalAnalysis numericalAnalysis;
	PlotFrame plotFrame;
	LoadingWindow loadingWindow;
	public OptionPanel options;
	private JLabel lblCord;
	public MyFuncFitter myFuncFitter;
	
	
	
	
	
	public Main(){
		
		numericalAnalysis=new NumericalAnalysis();
		chooser = new JFileChooser();
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Load File");
		analysisMenu = new JMenu("Analysis");
		saveData= new JMenu("Save Data");
		saveTable= new JMenuItem("Save Table");
		extrema = new JMenuItem("Extrema Finder");
		impAnalyzer = new JMenuItem("Imp analyzer");
		openMenuItem = new JMenuItem("Data File");
	    openMenuItem.setActionCommand("Data File");
	    programMenuItem = new JMenuItem("Program File");
	    programMenuItem.setActionCommand("Program File");
	    fileMenu.add(openMenuItem);
	    fileMenu.add(programMenuItem);
	    saveData.add(saveTable);
	    menuBar.add(fileMenu);
	    menuBar.add(analysisMenu);
	    menuBar.add(saveData);
	    analysisMenu.add(extrema);
	    analysisMenu.add(impAnalyzer);
	    setJMenuBar(menuBar);
	    fit = new DataSeries("fit",false);
	    data= new DataSeries("data",false);
	    min= new XYSeries("min",false);
	    max= new XYSeries("max",false);
	    collection = new XYSeriesCollection();
	    this.setVisible(true);
	    
	    getContentPane().setLayout(new MigLayout("", "[240.00][grow]", "[240.00,grow][41.00,grow][43.00][][][]"));
	    setVisible(true);
	    
	    
	    ttlPanel = new TTLPanel();
	    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
	    
	//inicjalizacja wykresu
	collection.addSeries(data);
	collection.addSeries(fit);
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
     //System.out.println("Click event!");
    XYPlot xyPlot2 = chartContainer.getChart().getXYPlot();
    lblCord.setText("X="+xyPlot2.getDomainCrosshairValue() + " "
        +"Y="+xyPlot2.getRangeCrosshairValue());
    }
    }
});
lblCord = new JLabel("X=... Y=...");
getContentPane().add(lblCord, "cell 0 1,alignx right");
	





		
		options = new OptionPanel();
		options.addOptionChangeListener(new OptionChange() {
		
			@Override
			public void change() {
				reDraw();
			}
		});
		
		options.btnFit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("fit from "+options.parameters[1]+" to "+options.parameters[2]);
				myFuncFitter=new MyFuncFitter();
				
				//System.out.println(Arrays.toString(options.getVariables()));
				loadingWindow = new LoadingWindow("Calculating...");
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						double[] coeffs=myFuncFitter.fit(data,
								options.parameters[1],
								options.parameters[2],
								options.getVariables());
						System.out.println("startowe parametry:  "+Arrays.toString(options.getVariables()));
						options.setVariables(coeffs);
						System.out.println(Arrays.toString(coeffs));
						SwingUtilities.invokeLater(new Runnable() {
			                public void run() {
			                    loadingWindow.setVisible(false);
			                }
			            });
					}
					
					
				};
				
				new Thread(runnable).start();
				
				
			}
		});
		
		getContentPane().add(options, "cell 0 0,grow");
		
		
		chartContainer.setMinimumDrawHeight(250);
		chartContainer.setMaximumDrawHeight(1200);
		chartContainer.setMaximumDrawWidth(1600);
		
		
		saveTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			ttlPanel.SaveToFile();	
			}
		});
		
		
	
		
	   openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					//rysowaanie
					
					askWin = new AskWindow();
				
					askWin.btnOk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int L=Integer.parseInt(askWin.spinner.getValue().toString());
							double N=Double.parseDouble(askWin.spinner_1.getValue().toString());
			            	File selectedFile=chooser.getSelectedFile();
							 
			            	 collection.removeSeries(max);
			            	 collection.removeSeries(min);
			            	 collection.removeSeries(fit);
							 collection.removeSeries(data);
							 data = new DataSeries("data",false,selectedFile.getPath(),L,N);
							 collection.addSeries(data);
							 collection.addSeries(fit);
							 collection.addSeries(min);
							 collection.addSeries(max);
							 
							 chart.setTitle(selectedFile.getName());
				                
				                try{
				                	getContentPane().remove(ttlPanel);
					                String path =selectedFile.getParent()+"/"+selectedFile.getName().substring(0,8)+" L.CSV";
				                    ttlPanel = new TTLPanel(path);
				                    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
									getContentPane().revalidate();
									getContentPane().repaint();
									for(int ii=0;ii<ttlPanel.getLen();ii++){
										marker = new ValueMarker(ttlPanel.getStart(ii));  // position is the value on the axis
										marker.setPaint(Color.green);
										//marker.
										XYPlot plot = (XYPlot) chart.getPlot();
										plot.addDomainMarker(marker);
									
										ttlPanel.addSelectRecordListener(new SelectRecord() {
											
											@Override
											public void selectRecord() {
												chart.getXYPlot().getDomainAxis().setRange(ttlPanel.start-10,ttlPanel.finish+10);
												//options.setT(ttlPanel.start);
												options.spinnerTmin.setValue(ttlPanel.start);
												options.spinnerTmax.setValue(ttlPanel.finish);
												//reDraw();
											}
										});

									
				                }
									}catch(Exception e2){
				                	JOptionPane.showMessageDialog(null, e2.toString(),null,JOptionPane.INFORMATION_MESSAGE);
				                	
				                }
				                askWin.dispose();
							
						}
					});
		                
						

				}
				
			}});
	   

	   
	   programMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					
					getContentPane().remove(ttlPanel);
					File selectedFile=chooser.getSelectedFile();
                    ttlPanel = new TTLPanel(selectedFile.getPath());
                    getContentPane().add(ttlPanel, "cell 1 1 1 15,grow");
					getContentPane().revalidate();
					getContentPane().repaint();
					for(TTL item : ttl.data){
						marker = new ValueMarker(item.start);
						XYPlot plot = (XYPlot) chart.getPlot();
						plot.addDomainMarker(marker);

					}
					
					ttlPanel.addSelectRecordListener(new SelectRecord() {
						
						@Override
						public void selectRecord() {
							chart.getXYPlot().getDomainAxis().setRange(ttlPanel.finish,ttlPanel.finish);
							options.spinnerTmin.setValue(ttlPanel.start);
							options.spinnerTmax.setValue(ttlPanel.finish);
							options.sliderT.setValue(0);
						}
					});

				}
				
			}});
	   
	   extrema.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			extremaFinderWindow= new ExtremaFinderWindow(data);
			
			extremaFinderWindow.btnSaveAs.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					numericalAnalysis.SaveToFile();
					
				}
			});
			
			
     extremaFinderWindow.btnGrowthSpeed.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					plotFrame= new PlotFrame(numericalAnalysis.calculateGrowthSpeed(options.parameters[3],
							Double.parseDouble(options.spinnerLambda.getValue().toString())),"Time [s]","Growth rate [um/h]");
					plotFrame.btnSave.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
						numericalAnalysis.SaveToFile2(options.parameters[3], 
								Double.parseDouble(options.spinnerLambda.getValue().toString()));	
						}
					});
					
				}
			});
			
			
			 extremaFinderWindow.addExtremaChangeListener(new ExtremaChange() {
					
					@Override
					public void extremaChange() {
						numericalAnalysis.peakDet(data,
								extremaFinderWindow.delta,
								extremaFinderWindow.getFrom(),
								extremaFinderWindow.getTo()
								);
						
						
						collection.removeSeries(max);
						 collection.removeSeries(min);
						 collection.removeSeries(fit);		 
							fit = new DataSeries("fit",false,
									options.parameters);
							max=numericalAnalysis.getMax();
							min=numericalAnalysis.getMin();
							collection.addSeries(fit);
							collection.addSeries(min);
							collection.addSeries(max);
						numericalAnalysis.mergeArray();
						
						
					}
				});
			
		}
	});
	   
	   
	   impAnalyzer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("IMP");
				impAnalyzerFrame = new ImpAnalyzerFrame(data, ttlPanel);
				impAnalyzerFrame.btnReset.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("TTL reset");
						ttlPanel.reset();
						
					}
				});
				
				
			}
		});
		
		
	   
	   
	   
	    
	}
	
	public void reDraw(){
		//PRERYSOWANIE
		 collection.removeSeries(max);
		 collection.removeSeries(min);
		 collection.removeSeries(fit);		 
			fit = new DataSeries("fit",false,
					options.parameters);
			collection.addSeries(fit);
			collection.addSeries(min);
			collection.addSeries(max);
		   
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
