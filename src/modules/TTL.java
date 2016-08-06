package modules;

public class TTL {
	public String label="0";
	public double start=0, finish=0;
	double min=0,max=0;
	double halfPeriod=0,growthRate=0;

	
	public TTL(String label,double start){
		this.label=label;
		this.start=start;
	}
	public void setHalfPeriod(double halfPeriod){
		halfPeriod=this.halfPeriod;
	}
	
	public void setMax(double max){
		max=this.max;
	}
	
	public void setMin(double min){
		min=this.min;
	}
	
	

}
