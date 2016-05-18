package ts.smodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="NamePair")
public class NamePair implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8268610034038653092L;
	/**
	 * 
	 */
	public String A;
	public String B;
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	
}
