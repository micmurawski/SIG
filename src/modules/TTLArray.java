package modules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class TTLArray {
	//List<String[]>  data;
	List<TTL>  data;
	String[] legend;
	int length;
	
	public TTLArray(String path){
		data = new ArrayList<TTL>();
		
		
		
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));         

	        String strLine;
	        String[] str;
	        strLine = br.readLine();
	        strLine = br.readLine();
	        legend = br.readLine().split(",",2);
			  
			  while ((strLine = br.readLine()) != null){
				  str=strLine.split(",",2);
				  
				  if(str[1].equals("0,1,1,1,1,1,1,1,1,1")){
					  str[1]="CdTe/GaAs";}
		        	else if(str[1].equals("1,1,0,1,1,1,1,1,1,1")){
		        		str[1]="HgTe/CdTe";
		        	}
		        	else if(str[1].equals("1,0,1,1,1,1,1,1,1,1")){
		        		str[01]="CdTe/HgTe";
		        	}
		        	else{}
				  
			  data.add(new TTL(str[1],Double.parseDouble(str[0])));
	        }
			  br.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		for(int ii=0;ii<data.size()-1;ii++){
			data.get(ii).finish=data.get(ii+1).start;
		}

}
	public void listout(){
		for(TTL oneItem : this.data ){
			System.out.println(oneItem.label+" "+oneItem.start+" "+oneItem.finish);
		}
		System.out.println(this.legend);
	}
	
	public static void main(String [] args)
	{
		JFileChooser chooser = new JFileChooser();
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			System.out.println(chooser.getSelectedFile());
			long startTime = System.currentTimeMillis();
			 TTLArray data = new TTLArray(chooser.getSelectedFile().getPath());
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime - startTime)+" ms");
			
			}
	}}
