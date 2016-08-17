package modules;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.jfree.data.xy.XYSeries;

public class NumericalAnalysis {
	
	public XYSeries maxArray;
	public XYSeries minArray;
	
	
	
	public double findXforMaxY(XYSeries data, double from, double to){
		int startIndex=0,endIndex=0;
		double mx=Double.NEGATIVE_INFINITY,pick,mxpos = Double.NaN;
		if(from>Double.parseDouble(data.getX(0).toString())){
			for(;from>=Double.parseDouble(data.getX(startIndex).toString());startIndex++);
		}
		//System.out.println("StartIndex"+startIndex);
		if(to<Double.parseDouble(data.getX(data.getItemCount()-1).toString())){
		for(;to>=Double.parseDouble(data.getX(endIndex).toString());endIndex++);
		}else endIndex=data.getItemCount();
		//System.out.println("EndIndex"+endIndex);
		for(int ii=startIndex;ii<endIndex;ii++){
			pick=Double.parseDouble(data.getY(ii).toString());
			if(pick>mx){
				mx=pick;
				mxpos=Double.parseDouble(data.getX(ii).toString());
			}
		}
		
		
		return mxpos;
		
		
	}
	
	public double findXforMinY(XYSeries data, double from, double to){
		int startIndex=0,endIndex=0;
		double mn=Double.POSITIVE_INFINITY,pick,mnpos = Double.NaN;
		if(from>Double.parseDouble(data.getX(0).toString())){
			for(;from>=Double.parseDouble(data.getX(startIndex).toString());startIndex++);
		}
		//System.out.println("StartIndex"+startIndex);
		if(to<Double.parseDouble(data.getX(data.getItemCount()-1).toString())){
		for(;to>=Double.parseDouble(data.getX(endIndex).toString());endIndex++);
		}else endIndex=data.getItemCount();
		//System.out.println("EndIndex"+endIndex);
		for(int ii=startIndex;ii<endIndex;ii++){
			pick=Double.parseDouble(data.getY(ii).toString());
			if(pick<mn){
				mn=pick;
				mnpos=Double.parseDouble(data.getX(ii).toString());
			}
		}
		
		
		return mnpos;
		
		
	}
	
	
	
	void peakDet(XYSeries data, double delta,double from, double to){
		int startIndex=0,endIndex=0;
		if(from>Double.parseDouble(data.getX(0).toString())){
			for(;from>=Double.parseDouble(data.getX(startIndex).toString());startIndex++);
		}
		System.out.println("StartIndex"+startIndex);
		if(to<Double.parseDouble(data.getX(data.getItemCount()-1).toString())){
		for(;to>=Double.parseDouble(data.getX(endIndex).toString());endIndex++);
		}else endIndex=data.getItemCount();
		System.out.println("EndIndex"+endIndex);
		
		
		
		maxArray= new XYSeries("max",false);
		minArray= new XYSeries("min",false);
		double mx,mn,pick,mxpos = Double.NaN,mnpos = Double.NaN;
		boolean lookingForMax;
		mn=Double.POSITIVE_INFINITY;
		mx=Double.NEGATIVE_INFINITY;
		
		lookingForMax=true;
		
		//System.out.println(data.getItemCount());
		
		for(int ii=startIndex;ii<endIndex;ii++){
			pick=Double.parseDouble(data.getY(ii).toString());
			
			if(pick>mx){
				mx=pick;
				mxpos=Double.parseDouble(data.getX(ii).toString());
			}
			
			if(pick<mn){
				mn=pick;
				mnpos=Double.parseDouble(data.getX(ii).toString());
			}
			if(lookingForMax){
				if(pick<mx-delta){
					this.maxArray.add(mxpos,mx);
					mn=pick;
					mnpos=Double.parseDouble(data.getX(ii).toString());
					lookingForMax=false;
				}
				}else{
					if(pick>mn+delta){
						this.minArray.add(mnpos,mn);
						mx=pick;
						mxpos=Double.parseDouble(data.getX(ii).toString());
						lookingForMax=true;
					}
				}
				 
			}
			
			
		}
		
	
	void SaveToFile(){
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
				XYSeries merged = this.mergeArray();
				fileWriter.println("Time[s],Reflectance");
				for(int jj=0;jj<merged.getItemCount();jj++){
					fileWriter.println(merged.getX(jj)+","+merged.getY(jj));
				}
				
				fileWriter.flush();
				fileWriter.close();
				
				
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
				
			}
			
		}
	}
	
	
	
	void SaveToFile2(double n,double lambda){
		double period=lambda/(2*n);
		double T,dT;
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
				XYSeries merged = this.mergeArray();
				fileWriter.println("Time[s],Growth Speed [nm/s]");
				for(int jj=0;jj<merged.getItemCount();jj++){
					dT=Double.parseDouble(merged.getX(jj+1).toString());
					dT-=Double.parseDouble(merged.getX(jj).toString());
					T=Double.parseDouble(merged.getX(jj).toString());
					fileWriter.println(T+","+(period/(2*dT)));
				}
				
				fileWriter.flush();
				fileWriter.close();
				
				
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
				
			}
			
		}
	}
	
	XYSeries calculateGrowthSpeed(double n,double lambda){
		double period=lambda/(2*n);
		double T,dT;
		XYSeries growthSpeed = new XYSeries("growth speed",false);
		XYSeries merged = this.mergeArray();
		for(int jj=0;jj<merged.getItemCount()-1;jj++){
			dT=Double.parseDouble(merged.getX(jj+1).toString());
			dT-=Double.parseDouble(merged.getX(jj).toString());
			T=Double.parseDouble(merged.getX(jj).toString());
			growthSpeed.add(T+dT/2,(period/(2*dT)*3.6));
		}
		return growthSpeed;
	}
	
	XYSeries calculateFilmThickness(double n,double lambda){
		double d,dT,G;
		XYSeries filmThickness = new XYSeries("film thickness",false);
		XYSeries growthSpeed = calculateGrowthSpeed(n, lambda);
		
		for(int jj=0;jj<growthSpeed.getItemCount();jj++){
			d=0;
			for(int ii=0;ii<jj;jj++){
				dT=Double.parseDouble(growthSpeed.getX(ii+1).toString());
				dT-=Double.parseDouble(growthSpeed.getX(ii).toString());
				G=Double.parseDouble(growthSpeed.getY(ii).toString());
				d+=G*dT;
			}
			filmThickness.add(growthSpeed.getX(jj),
					d
					);
			
		}
		
		return filmThickness;
	}
	
	
	
	XYSeries mergeArray(){
		XYSeries merged= new XYSeries("extremas",true);
		
		for(int ii=0;ii<maxArray.getItemCount();ii++)
			merged.add(maxArray.getX(ii),maxArray.getY(ii));
		for(int ii=0;ii<minArray.getItemCount();ii++)
			merged.add(minArray.getX(ii),minArray.getY(ii));
		
		return merged;
		
	}
	
	XYSeries getMax(){
		return maxArray;
	}
	
	XYSeries getMin(){
		return minArray;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
