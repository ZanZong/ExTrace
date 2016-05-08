package ts.smodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="LocXY")
public class LocXY implements Serializable{

	@Override
	public String toString() {
		return "LocXY [x=" + x + ", y=" + y + "]";
	}
	/**
	 * @author Zongzan
	 * ���ڴ���route��������Ϣ������ǰ�˵Ļ�ͼ
	 */
	private static final long serialVersionUID = 1L;
	double x;
	double y;
	public LocXY(){
		
	}
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
