package ts.smodel;

import java.io.Serializable;

public class LocXY implements Serializable{

	@Override
	public String toString() {
		return "LocXY [x=" + x + ", y=" + y + "]";
	}
	/**
	 * @author Zongzan
	 * 用于传输route的坐标信息，用于前端的绘图
	 */
	private static final long serialVersionUID = 1L;
	double x;
	double y;
	public LocXY(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
