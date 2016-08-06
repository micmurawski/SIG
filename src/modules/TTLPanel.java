package modules;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;


public class TTLPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	JScrollPane scrollPane;
	String [][] list;
	public double start,finish;
	private List<SelectRecord> listeners = new LinkedList<>();
	
	public TTLPanel(){
		super();
	}
	
	 
	
	public TTLPanel(TTLArray data1){
		super();
		setLayout(new MigLayout("",
		        "[600:pref, fill, grow][]"));
		
		String[][] matrix=new String[data1.data.size()][4];
        //matrix=data1.data.toArray(matrix);
        for(int ii=0;ii<data1.data.size();ii++){
        	matrix[ii][0]=data1.data.get(ii).label;
        	matrix[ii][1]=Double.toString(data1.data.get(ii).start);
        	matrix[ii][2]=Double.toString(data1.data.get(ii).finish);
        	matrix[ii][3]="0";
        	
        	
        }
        
        table=new JTable(new NonEditableModel(matrix, new String[]{"TTL","start","finish","growth rate[um/h]"}));
//table = new JTable(matrix, data1.legend);
table.setPreferredScrollableViewportSize(new Dimension(1200,800));

table.setPreferredScrollableViewportSize(table.getPreferredSize());
table.setFillsViewportHeight(true);
JScrollPane scrollPane = new JScrollPane(table);
//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//table.getColumnModel().getColumn(0).setPreferredWidth(100);
//table.getColumnModel().getColumn(1).setPreferredWidth(600);

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
			 TTLArray data = new TTLArray(chooser.getSelectedFile().getPath());
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime - startTime)+" ms");
			 data.listout();
			

		JFrame program =new JFrame();
		TTLPanel options=new TTLPanel(data);
		program.getContentPane().setLayout(new MigLayout("", "[12000.00][]"));
		program.getContentPane().add(options, "cell 0 0");
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.setSize(600, 600);
        program.setTitle("Simple Interferogram Generator");
        program.setVisible(true);
		
	}}

}
