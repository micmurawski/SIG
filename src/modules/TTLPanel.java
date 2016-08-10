package modules;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

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
	
	public TTLPanel(){
		super();
	}
	
	 
	
	public TTLPanel(String path){
		super();
		data = new ArrayList<Object[]>();
		
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));         

	        String strLine;
	        String[] str;
	        strLine = br.readLine();
	        strLine = br.readLine();
	        strLine = br.readLine();
			  
			  while ((strLine = br.readLine()) != null){
				  str=strLine.split(",",2);
				  
				  if(str[1].equals("0,1,1,1,1,1,1,1,1,1")){
					  str[1]="CdTe/GaAs";}
		        	else if(str[1].equals("1,1,0,1,1,1,1,1,1,1")){
		        		str[1]="HgTe/CdTe";
		        	}
		        	else if(str[1].equals("1,0,1,1,1,1,1,1,1,1")){
		        		str[1]="CdTe/HgTe";
		        	}
		        	else{}
				  
			  data.add(new Object[]{str[1],Double.parseDouble(str[0]),0,0});
	        }
			  br.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		for(int ii=0;ii<data.size()-1;ii++){
			data.get(ii)[2]=data.get(ii+1)[1];
		}

		setLayout(new MigLayout("",
		        "[600:pref, fill, grow][]"));
		
		
		matrix = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
		    matrix[i] = data.get(i);
		}

        
table=new JTable(new NonEditableModel(matrix, new String[]{"TTL","start","finish","growth rate[um/h]"}));
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
