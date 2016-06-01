package ts.smodel;


import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="History")
public class WebHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String packageID;
	String nameFrom;
	String nameTo;
	Date time;
	String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

