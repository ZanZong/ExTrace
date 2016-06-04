package ts.smodel;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="History")
public class History implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String packageID;
	String nameFrom;
	String nameTo;
	int idFrom;
	int idTo;
	Date time;
	double x;
	double y;
	/**
	 * @return the idFrom
	 */
	public int getIdFrom() {
		return idFrom;
	}
	/**
	 * @param idFrom the idFrom to set
	 */
	public void setIdFrom(int idFrom) {
		this.idFrom = idFrom;
	}
	/**
	 * @return the idTo
	 */
	public int getIdTo() {
		return idTo;
	}
	/**
	 * @param idTo the idTo to set
	 */
	public void setIdTo(int idTo) {
		this.idTo = idTo;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPackageID() {
		return packageID;
	}
	public void setPackageID(String packageID) {
		this.packageID = packageID;
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
	public String getNameFrom() {
		return nameFrom;
	}
	public void setNameFrom(String nameFrom) {
		this.nameFrom = nameFrom;
	}
	public String getNameTo() {
		return nameTo;
	}
	public void setNameTo(String nameTo) {
		this.nameTo = nameTo;
	}
}
