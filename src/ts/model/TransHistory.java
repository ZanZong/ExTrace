/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package ts.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="TransHistory")
@XmlRootElement(name="TransHistory")
public class TransHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6973429316324711538L;

	public TransHistory() {
	}
	
	@Column(name="SN", nullable=true)	
	@Id	
	@GeneratedValue(generator="MODEL_TRANSHISTORY_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_TRANSHISTORY_SN_GENERATOR", strategy="native")	
	private int SN;
	
	@ManyToOne(targetEntity=TransPackage.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="PackageID", referencedColumnName="ID", nullable=false) })	
	private TransPackage pkg;
	
	@Column(name="ActTime", nullable=false)	
	private Date actTime;
	
	@Column(name="UIDFrom", nullable=false, length=10)	
	private String UIDFrom;
	
	@Column(name="UIDTo", nullable=false, length=10)	
	private String UIDTo;
	
	@Column(name="x", nullable=true)	
	private double x;
	
	@Column(name="y", nullable=true)	
	private double y;
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setActTime(Date value) {
		this.actTime = value;
	}
	
	public Date getActTime() {
		return actTime;
	}
	
	public void setUIDFrom(String value) {
		this.UIDFrom = value;
	}
	
	public String getUIDFrom() {
		return UIDFrom;
	}
	
	public void setUIDTo(String value) {
		this.UIDTo = value;
	}
	
	public String getUIDTo() {
		return UIDTo;
	}
	
	public void setX(double value) {
		this.x = value;
	}
	
	public double getX() {
		return x;
	}
	
	public void setY(double value) {
		this.y = value;
	}
	
	public double getY() {
		return y;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("TransHistory[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("ActTime=").append(getActTime()).append(" ");
			sb.append("UIDFrom=").append(getUIDFrom()).append(" ");
			sb.append("UIDTo=").append(getUIDTo()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

}
