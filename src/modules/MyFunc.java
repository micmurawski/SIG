package modules;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;



class MyFunc implements ParametricUnivariateFunction {



	@Override
	public double value(double t, double... v) {
		 
		double Norm=v[0];
		double G=v[1]/3.6;
		
		double n=v[2];
		double k=v[3];
		double ns=v[4];
		double ks=v[5];
		double lambda=v[6];
		
		double a,b;
		double r_inf=((1-n)*(1-n) + k*k)/((1+n)*(1+n) + k*k);
		double r_i=((ns-n)*(ns-n)+(ks-k)*(k-k))/((n+ns)*(n+ns)+(k+ks)*(k+ks));
		double sig=Math.atan(2*(n*ks-ns*k)/(n*n-ns*ns+k*k-ks*ks));
		double fi;
		if(n>ns){
			fi=Math.atan(2*k/(1-n*n-k*k))+Math.PI;
		}else{
			fi=Math.atan(2*k/(1-n*n-k*k));
		}
		

			
			double gam=4*Math.PI*k*G/lambda;
			double delta=4*Math.PI*n*G/lambda;
	
				a = r_inf+2*Math.sqrt(r_inf*r_i)*Math.exp(-gam*t)*Math.cos(delta*t-sig+fi)+r_i*Math.exp(-2*gam*t);
				b = 1+2*Math.sqrt(r_inf*r_i)*Math.exp(-gam*t)*Math.cos(delta*t-sig-fi)+r_inf*r_i*Math.exp(-2*gam*t);
				
			
		 
		 
		 
		 
	        return (100000*a/b)+Norm;
	}
	

	

	    // Jacobian matrix of the above. In this case, this is just an array of
	    // partial derivatives of the above function, with one element for each parameter.
	 

		@Override
		public double[] gradient(double t, double... v) {

			//double Norm=v[0];
			double G=v[1]/3.6;
			
			double n=v[2];
			double k=v[3];
			double ns=v[4];
			double ks=v[5];
			double lambda=v[6];
			
			double a=0,b=0,dRdgam=0,dRddelta=0,dRdRi=0,dRdsig,dRidns,dsigdns;
			double r_inf=((1-n)*(1-n) + k*k)/((1+n)*(1+n) + k*k);
			double r_i=((ns-n)*(ns-n)+(ks-k)*(k-k))/((n+ns)*(n+ns)+(k+ks)*(k+ks));
			double sig=Math.atan(2*(n*ks-ns*k)/(n*n-ns*ns+k*k-ks*ks));
			double fi;
			if(n>ns){
				fi=Math.atan(2*k/(1-n*n-k*k))+Math.PI;
			}else{
				fi=Math.atan(2*k/(1-n*n-k*k));
			}

				
				double gam=4*Math.PI*k*G/lambda;
				double delta=4*Math.PI*n*G/lambda;
		
			a=(-2*t*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t+sig-fi)-2*r_i*Math.exp(-2*gam*t));
			b=(2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t+sig+fi)+r_i*r_inf*Math.exp(-2*gam*t)+1);
			
			dRdgam+=(a/b);
			
			a=-(2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t+sig-fi)+r_inf+r_i*Math.exp(-2*gam*t));
			a*=(-2*t*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t+sig+fi)-2*r_i*r_inf*Math.exp(-2*gam*t));

			dRdgam+=(a/b/b);
			dRdgam*=gam/G;
			
			
			a=2*t*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.sin(-delta*t+sig-fi);
			dRddelta+=(a/b);
			
			a=-(2*t*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.sin(-delta*t+fi+sig));
			a*=(2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t-fi+sig)+r_inf+r_i*Math.exp(-2*gam*t));
			dRddelta+=(a/b/b);
			dRddelta*=delta/G;
			
			a=((r_inf*Math.exp(-gam*t)*Math.cos(-delta*t-fi+sig)/(Math.sqrt(r_i*r_inf))))+Math.exp(-gam*2*t);
			b=2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t+fi+sig)+r_i*r_inf*Math.exp(-2*gam*t) +1;
			dRdRi=a/b;
			a=-(2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(-delta*t-fi+sig)+r_inf*r_i*Math.exp(-2*gam*t))*(r_inf*Math.exp(-gam*t)*Math.cos(-delta*t+fi+sig)+r_inf*Math.exp(-2*gam*t));
			dRdRi+=(a/b/b);
			dRidns=(4*ns*ns*n+8*ns*ks*k-4*n*(n*n+ks*ks+k*k))/(ns*ns+2*ns*n+n*n+(k+ks)*(k+ks))/(ns*ns+2*ns*n+n*n+(k+ks)*(k+ks));
	    	a=2*Math.sqrt(r_inf*r_i)*Math.exp(-gam*t)*Math.sin(-delta*t+fi+sig)*(2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.cos(delta*t-sig+fi)+r_inf+r_i*Math.exp(-2*gam*t));
	    	dRdsig=a/b/b;
	    	a=2*Math.sqrt(r_i*r_inf)*Math.exp(-gam*t)*Math.sin(delta*t-sig+fi);
	    	dRdsig+=a/b;
	    	b=(-ns*ns+n*n-ks*ks+k*k);
	    	dsigdns=((4*ns*(n*ks-ns*k)/b/b)-2*k/b)/((4*(n*ks-ns*k)*(n*ks-ns*k)/b/b)+1);
	    	
	    	
	    	
	        return new double[] {
	        	     1, 
	        	     100000*(dRdgam+dRddelta),
	        	     0,
	        	     0,
	        	     100000*(dRdRi*dRidns+dRdsig*dsigdns),
	        	     0,
	        	     0
	        };
		}
	
	
	
}


