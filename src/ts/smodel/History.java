package ts.smodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="History")
public class History implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String packageID;
	int uidFrom;
	int uidTo;
	float x;
	float y;
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	public int getUidFrom() {
		return uidFrom;
	}
	public void setUidFrom(int uidFrom) {
		this.uidFrom = uidFrom;
	}
	public int getUidTo() {
		return uidTo;
	}
	public void setUidTo(int uidTo) {
		this.uidTo = uidTo;
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
