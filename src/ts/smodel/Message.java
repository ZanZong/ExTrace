package ts.smodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Message")
public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5557006407206577835L;
	private int cid;
	private String sender;
	private String expId;
	private String tel;
	private String loc;
	
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getSender() {
		return sender;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	@Override
	public String toString() {
		return "Message [sender=" + sender + ", tel=" + tel + ", loc=" + loc + "]";
	}
	
	
	
}
