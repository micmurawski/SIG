package modules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

import org.jfree.data.xy.XYSeries;

public class DataSeries extends XYSeries {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSeries(Comparable<String> key,boolean autosort){
		super(key,autosort);
	}
	
	public DataSeries(Comparable<String> key,boolean autosort,String path,int number){
		super(key,autosort);
	
	try {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));         
        int i=0;
        String strLine;
        String[] str;
        strLine = br.readLine();
		  
		  while ((strLine = br.readLine()) != null){
		 str=strLine.split(",");
		 if(i%number==0)this.add(Double.parseDouble(str[0]),Double.parseDouble(str[1]));
		  //System.out.println(time[i]+" "+refl[i]+" "+i);
		  i++;
        }
        br.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
	
	}
	
	public DataSeries(Comparable<String> key,boolean autosort,
			double Norm,double tmin,double tmax,double n,double k, 
			double ns, double ks,double G, double lambda,int N
			){
		super(key,autosort);
		
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
			this.add(tmin+t,Norm*a/b);
			
		}
		
		
		
	
	}
	
	public void listout(){
		for(int ii=0;ii<this.getItemCount()-1;ii++){
			System.out.println(this.getDataItem(ii));
		}
	}
	
	public static void main(String [] args)
	{
		JFileChooser chooser = new JFileChooser();
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			System.out.println(chooser.getSelectedFile());
			
			 DataSeries data = new DataSeries("dane",false,chooser.getSelectedFile().getPath(),1);
			 
			 long startTime = System.currentTimeMillis();
			 data.listout();
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime - startTime)+" ms");
			 
			}
		//DataArray dataArray=new DataArray();
		
	}
	
	


}
