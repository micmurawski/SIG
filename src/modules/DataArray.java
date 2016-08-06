package modules;
import java.io.*;

import javax.swing.JFileChooser;

import org.jfree.data.xy.XYSeries;


public class DataArray {
	XYSeries data;
	public int length;
	
	public DataArray(String path,int L){
		int i =0;

			  // Open the file that is the first 
			  // command line parameter
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));         
	        

	        String strLine;
	        String[] str;
	        data= new XYSeries("data",false);
	        strLine = br.readLine();
			  
			  while ((strLine = br.readLine()) != null){
			 str=strLine.split(",");
			 if(i%L==0)this.data.add(Double.parseDouble(str[0]),Double.parseDouble(str[1]));
			  //System.out.println(time[i]+" "+refl[i]+" "+i);
			  i++;
	        }
			  
	        br.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
			  
	
			 
	}
	
	public DataArray(String s){
		data= new XYSeries(s,false);
		
		
	}
	
	public DataArray(double Norm,double tmin,double tmax,double n,double k, double ns, double ks,double G, double lambda,int N){
		data= new XYSeries("fit",false);
		double a,b,t,fi;
		double r_inf=((1-n)*(1-n) + k*k)/((1+n)*(1+n) + k*k);
		double r_i=((ns-n)*(ns-n)+(ks-k)*(k-k))/((n+ns)*(n+ns)+(k+ks)*(k+ks));
		double sig=Math.atan(2*(n*ks-ns*k)/(n*n-ns*ns+k*k-ks*ks));
		if(n>ns){
			fi=Math.atan(2*k/(1-n*n-k*k))+Math.PI;
		}else{
			fi=Math.atan(2*k/(1-n*n-k*k));
		}
		
		double gam=4*Math.PI*k*G/lambda;
		double delta=4*Math.PI*n*G/lambda;
		double dt=(tmax-tmin)/(double)N;
		
		
	
		for(int ii=0;ii<N+1;ii++){
			t=(ii*dt);
			a = r_inf+2*Math.sqrt(r_inf*r_i)*Math.exp(-gam*t)*Math.cos(delta*t-sig+fi)+r_i*Math.exp(-2*gam*t);
			b = 1+2*Math.sqrt(r_inf*r_i)*Math.exp(-gam*t)*Math.cos(delta*t-sig-fi)+r_inf*r_i*Math.exp(-2*gam*t);
			data.add(tmin+t,Norm*a/b);
			
		}
		
	}
	
	public DataArray(double Norm,double tmin,double tmax,double n, double k,double ns,double G,double lambda,int N){
		data= new XYSeries("fit",false);
		double a,b,t;
		double r1=(n-1)/(n+1);
		double r2=(ns-n)/(ns+n);
		double alfa=4*Math.PI*k*G/lambda;
		double delta=4*Math.PI*n*G/lambda;
		double dt=(tmax-tmin)/(double)N;
		
		for(int ii=0;ii<N+1;ii++){
			t=(ii*dt);
			a = r1*r1+(r2*r2*Math.exp(-alfa*t)*Math.cos(delta*t))+2*r1*r2*Math.cos(delta*t)*Math.exp(-alfa*t);
			b = 1-(r2*r2*Math.exp(-alfa*t)*Math.cos(delta*t))+2*r1*r2*Math.cos(delta*t)*Math.exp(-alfa*t);
			data.add(tmin+t,Norm*a/b);
			
		}
		
	}
	
	
	
	public void listout(){
		for(int ii=0;ii<this.data.getItemCount()-1;ii++){
			System.out.println(data.getDataItem(ii));
		}
		
			}
			  

	
	public static void main(String [] args)
	{
		JFileChooser chooser = new JFileChooser();
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			System.out.println(chooser.getSelectedFile());
			
			 DataArray data = new DataArray(chooser.getSelectedFile().getPath(),1);
			 
			 long startTime = System.currentTimeMillis();
			 data.listout();
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime - startTime)+" ms");
			}
		//DataArray dataArray=new DataArray();
		
	}
		
		
	}
