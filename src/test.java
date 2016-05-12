import java.util.ArrayList;
import java.util.List;

import ts.smodel.LocXY;

public class test extends A{
	public static void main(String[] args){
		A a = new A();
		a.setAdapt(3);
		System.out.println(a.adapt);
	}
}

class A{
	int adapt;
	public void setAdapt(int a){
		adapt = a;
	}
}
