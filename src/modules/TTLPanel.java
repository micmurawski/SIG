package modules;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import org.jfree.data.xy.XYSeries;


public class TTLPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTable table;
	JScrollPane scrollPane;
	Object[][] matrix;
	public double start,finish;
	public ArrayList<Object[]> data;
	private List<SelectRecord> listeners = new LinkedList<>();
	public XYSeries growthRateHgTe,growthRateCdTe,NsHgTe,NsCdTe,NormHgTe,NormCdTe;
	
	public TTLPanel(){
		super();
	}
	
	 
	
	public TTLPanel(String path){
		super();
		data = new ArrayList<Object[]>();
		growthRateHgTe=new XYSeries("HgTe growth rate",false);
		growthRateCdTe=new XYSeries("CdTe growth rate",false);
		NsCdTe=new XYSeries("Eff. Ns CdTe",false);
		NsHgTe=new XYSeries("Eff. Ns HgTe",false);
		NormCdTe=new XYSeries("Norm CdTe",false);
		NormHgTe=new XYSeries("Norm HgTe",false);
		
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));         

	        String strLine;
	        String[] str;
	        strLine = br.readLine();
	        strLine = br.readLine();
	        strLine = br.readLine();
	        int i=0;
			  
			  while ((strLine = br.readLine()) != null){
				  
				  str=strLine.split(",",2);
				  
				  if(str[1].equals("0,1,1,1,1,1,1,1,1,1")){
					  str[1]="CdTe/GaAs";
					}
		        	else if(str[1].equals("1,1,0,1,1,1,1,1,1,1")){
		        		str[1]="HgTe/CdTe";
		        		}
		        	else if(str[1].equals("1,0,1,1,1,1,1,1,1,1")){
		        		str[1]="CdTe/HgTe";
		        		}
		        	else{}
				  
				 data.add(new Object[]{str[1],Double.parseDouble(str[0]),0,0,0,0});
				  
			  
	        }
			  br.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		for(int ii=0;ii<data.size()-1;ii++){
			data.get(ii)[2]=data.get(ii+1)[1];
			//if(!data.get(ii)[0].equals("CdTe/HgTe") && !data.get(ii)[0].equals("HgTe/CdTe") && !data.get(ii)[0].equals("CdTe/GaAs")) data.remove(ii);
		}
		
		//for(int ii=0;ii<data.size()-1;ii++){
			//data.get(ii)[2]=data.get(ii+1)[1];
			//if((!data.get(ii)[0].equals("CdTe/HgTe") && !data.get(ii)[0].equals("HgTe/CdTe")) && !data.get(ii)[0].equals("CdTe/GaAs")) data.remove(ii);
		//}

		setLayout(new MigLayout("",
		        "[600:pref, fill, grow][]"));
		
		
		matrix = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
				matrix[i] = data.get(i);
				//if(matrix[i][2].equals("0")){
				//	matrix[i][2]=matrix[i][1]*
				//}
		    
		}

        
table=new JTable(new NonEditableModel(matrix, new String[]{"TTL","start","finish","growth rate[um/h]","Norm","Ns"}));
table.setPreferredScrollableViewportSize(new Dimension(1200,800));

table.setPreferredScrollableViewportSize(table.getPreferredSize());
table.setFillsViewportHeight(true);
JScrollPane scrollPane = new JScrollPane(table);
table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

add(scrollPane, "alignx right");




table.addMouseListener(new MouseAdapter() {
	public void mousePressed(MouseEvent me) {
        JTable table =(JTable) me.getSource();
        Point p = me.getPoint();
        table.rowAtPoint(p);
        if (me.getClickCount() == 2) {
            
        	{if (table.getSelectedRow() > -1) {
                // print first column value from selected row
        		start=Double.parseDouble(table.getValueAt(table.getSelectedRow(), 1).toString());
        		finish=Double.parseDouble(table.getValueAt(table.getSelectedRow(), 2).toString());
                //System.out.println(selected);
                fireOptionChangeEvent();
            }}
        }
    }
    
});


		
	}
	
	public void update(XYSeries s){
		int j=0;
		for(int i=0;i<table.getRowCount();i++){
			if(s.getKey().equals(table.getValueAt(0, i))){
				table.setValueAt(s.getY(j), 3, i);
				j++;
			}
		}
	}

	public void setValueAt(Object s,int i,int j){
		table.setValueAt(s, i, j);
	}
	
	public Object getValueAt(int i,int j){
		return table.getValueAt(i, j);
	}
	
	public Object getLabel(int j){
		return table.getValueAt(j,0);
	}
	
	public double getStart(int i){
		return Double.parseDouble(table.getValueAt(i,1).toString());
	}
	
	public double getFinish(int i){
		return Double.parseDouble(table.getValueAt(i,2).toString());
	}
	
	public int getLen(){
		return data.size();
	}
	
	public void addSelectRecordListener(SelectRecord selectRecord){
		listeners.add(selectRecord);
	}

	private void fireOptionChangeEvent(){
		for(SelectRecord oc: listeners)
			oc.selectRecord();
	}
	
	public void reset(){
		for(int ii=0;ii<data.size();ii++){
			table.setValueAt(0, ii, 3);
			table.setValueAt(0, ii, 4);
			table.setValueAt(0, ii, 5);
		}
		growthRateHgTe=new XYSeries("HgTe growth rate",false);
		growthRateCdTe=new XYSeries("CdTe growth rate",false);
		NsCdTe=new XYSeries("Eff. Ns CdTe",false);
		NsHgTe=new XYSeries("Eff. Ns HgTe",false);
		NormCdTe=new XYSeries("Norm CdTe",false);
		NormHgTe=new XYSeries("Norm HgTe",false);
		
	}
	
	
	public void SaveToFile(){
		JFileChooser fileSaveChooser = new JFileChooser();
		fileSaveChooser.setDialogTitle("Save File");
		fileSaveChooser.addChoosableFileFilter(new FileFilter() {
			
			public String getDescription() {
				// TODO Auto-generated method stub
				return "CSV file";
			}
			
			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.getName().endsWith(".csv");
			}
		});
		int result = fileSaveChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			File file = fileSaveChooser.getSelectedFile();
			try{
				PrintStream fileWriter = new PrintStream(file.getPath());
				fileWriter.println("Type \t Start[s] \t Finish[s] \t Growth rate[um/h] \t Norm \t Ns");
				for(int jj=0;jj<data.size();jj++){
					fileWriter.println(matrix[jj][0]+"\t"+matrix[jj][1]+"\t"+matrix[jj][2]+"\t"+matrix[jj][3]+"\t"+matrix[jj][4]+"\t"+matrix[jj][5]);
				}
				
				fileWriter.flush();
				fileWriter.close();
				
				
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
				
			}
			
		}
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			System.out.println(chooser.getSelectedFile());
			long startTime = System.currentTimeMillis();
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime - startTime)+" ms");
			

		JFrame program =new JFrame();
		TTLPanel options=new TTLPanel(chooser.getSelectedFile().getPath());
		program.getContentPane().setLayout(new MigLayout("", "[12000.00][]"));
		program.getContentPane().add(options, "cell 0 0");
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.setSize(600, 600);
        program.setTitle("Simple Interferogram Generator");
        program.setVisible(true);
        options.setValueAt("ss", 3, 3);
        System.out.println(options.getLen());
		
	}}

}
