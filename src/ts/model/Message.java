package ts.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.*;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Message")
@XmlRootElement(name="Message")

public class Message implements Serializable{
	public Message(){
	
	}
	private static final long serialVersionUID = 6902534661725331437L;
	@Column(name="SN")	
	@Id
	@GeneratedValue(generator="MODEL_MESSAGE_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_MESSAGE_SN_GENERATOR", strategy="native")
	private int SN;
	
	@Column(name="sender", nullable=false)
	private int sender;
	
	@Column(name="accepter", nullable=true)
	private int accepter;
	
	@Column(name="expId", nullable=true)
	private String expId;
	
	@Column(name="isrecv", nullable=true)
	private boolean isrecv;
	
	@Column(name="time", nullable=true)
	private Date time;
	
	@Column(name="x", nullable=false)
	private double x;
	
	@Column(name="y", nullable=false)
	private double y;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return SN;
	}

	public void setId(int SN) {
		this.SN = SN;
	}


	public int getSender() {
		return sender;
	}


	public void setSender(int sender) {
		this.sender = sender;
	}


	public int getAccepter() {
		return accepter;
	}


	public void setAccepter(int accepter) {
		this.accepter = accepter;
	}


	public String getExpId() {
		return expId;
	}


	public void setExpId(String expId) {
		this.expId = expId;
	}


	public boolean isIsrecv() {
		return isrecv;
	}


	public void setIsrecv(boolean isrecv) {
		this.isrecv = isrecv;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
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
	public int getSN() {
		return SN;
	}
	public void setSN(int sN) {
		SN = sN;
	}

	
	/*
	 * 下面两个变量是为了使客户端不用再查询一次name，在数据库里没有这个字段
	 */
	@Transient	
	private String senderName;
	@Transient	
	private String accepterName;
	@Transient
	private String tel;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getAccepterName() {
		return accepterName;
	}
	public String setAccepterName(String accepterName) {
		return this.accepterName = accepterName;
	}
	
	@Override
	public String toString() {
		return "Message [SN=" + SN + ", sender=" + sender + ", accepter=" + accepter + ", expId=" + expId + ", isrecv="
				+ isrecv + ", time=" + time + ", x=" + x + ", y=" + y + ", senderName=" + senderName + ", accepterName="
				+ accepterName + ", tel=" + tel + "]";
	}
}
