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
	float x;
	float y;
	public LocXY(){
		
	}
	public LocXY(float x, float y){
		this.x = x;
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
