package ts.smodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PackgeNamePair")
public class PackageNamePair implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8268610034038653092L;
	/**
	 * 
	 */
	String packageId;
	String sourceNode;
	String sourceName;
	String targetNode;
	String targetName;
	String userName;
	String sourceRegionCode;
	String targetRegionCode;
	int userId;
	int pkgType;
	String type;
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String pid) {
		packageId=pid;
	}
	public String getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(String snode) {
		sourceNode=snode;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sname) {
		sourceName=sname;
	}
	public String getTargetNode() {
		return targetNode;
	}
	public void setTargetNode(String tnode) {
		targetNode=tnode;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String tname) {
		targetName=tname;
	}
	
	public String getUserName(){
		return userName;
	}
	public void setUserName(String uname){
		userName=uname;
	}
	public int getUserId(){
		return userId;
	}
	public void setUserId(int uid){
		userId=uid;
	}
	public int getPkgType(){
		return pkgType;
	}
	public void setPkgType(int b){
		pkgType=b;
	}
	public String getType(){
		return type;
	}
	public void setType(String b){
		type=b;
	}
	public String getSourceRegionCode(){
		return sourceRegionCode;
	}
	public void setSourceRegionCode(String b){
		sourceRegionCode=b;
	}
	public String getTargetRegionCode(){
		return targetRegionCode;
	}
	public void setTargetRegionCode(String b){
		targetRegionCode=b;
	}

}
