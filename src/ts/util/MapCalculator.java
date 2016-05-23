package ts.util;

public class MapCalculator {
	
	public static double getDistanceFrom2Point(double lat1, double lng1, double lat2, double lng2){
		if(lat1==0 || lng1==0 || lat2==0 || lng2==0)
			return 0;
		double pk = 180 / 3.14169;
		double a1 = lat1 / pk;
		double a2 = lng1 / pk;
		double b1 = lat2 / pk;
		double b2 = lng2 / pk;
		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2); 
		double t3 = Math.sin(a1) * Math.sin(b1);  
		double tt = Math.acos(t1 + t2 + t3);
		return 6366000 * tt;
	}
	
	public static void main(String args[]){
		System.out.println(MapCalculator.getDistanceFrom2Point(34.82247524804688, 113.54053006591796, 34.82255924804687, 113.54283006591797));
	}
}
