package modules;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class PlotFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChartPanel chartContainer;
	JFreeChart chart;
	XYSeriesCollection collection;
	JButton btnSave;
	
	public PlotFrame(XYSeries series,String x,String y){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Growth Speed");
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(new MigLayout("", "[500]", "[][][][][][][][]"));
		collection = new XYSeriesCollection();
		collection.addSeries(series);
		chart = ChartFactory.createXYLineChart(" ","x","y",
				collection,PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().setBackgroundPaint(Color.WHITE); //kolor obszaru kre�lenia
		chart.getXYPlot().setRangeGridlinePaint(Color.black); //kolor poziomych linii
		chart.getXYPlot().setDomainGridlinePaint(Color.black); //kolor pionowych linii
		
		chartContainer = new ChartPanel(chart);
		chartContainer.setDisplayToolTips(false);
		XYPlot plot = (XYPlot) chart.getPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, false);
	    renderer.setSeriesShapesVisible(0, true);
	    renderer.setSeriesShape(0, ShapeUtilities.createDiamond(5));
	    plot.setRenderer(renderer);
		getContentPane().add(chartContainer, "cell 0 0 1 5,grow");
		
		btnSave = new JButton("Save Data");
		getContentPane().add(btnSave, "cell 0 6,alignx center");
		
		
	}
	
	public PlotFrame(XYSeries series1, XYSeries series2,String x,String y){
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Growth Speed");
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(new MigLayout("", "[500]", "[][][][][][][][]"));
		collection = new XYSeriesCollection();
		collection.addSeries(series1);
		collection.addSeries(series2);
		chart = ChartFactory.createXYLineChart(" ",x,y,
				collection,PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().setBackgroundPaint(Color.WHITE); //kolor obszaru kre�lenia
		chart.getXYPlot().setRangeGridlinePaint(Color.black); //kolor poziomych linii
		chart.getXYPlot().setDomainGridlinePaint(Color.black); //kolor pionowych linii
		
		chartContainer = new ChartPanel(chart);
		chartContainer.setDisplayToolTips(false);
		XYPlot plot = (XYPlot) chart.getPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesLinesVisible(0, false);
	    renderer.setSeriesShapesVisible(0, true);
	    renderer.setSeriesShape(0, ShapeUtilities.createDiamond(5));
	    renderer.setSeriesLinesVisible(1, false);
	    renderer.setSeriesShapesVisible(1, true);
	    renderer.setSeriesShape(1, ShapeUtilities.createDiamond(5));
	    plot.setRenderer(renderer);
		getContentPane().add(chartContainer, "cell 0 0 1 5,grow");
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
