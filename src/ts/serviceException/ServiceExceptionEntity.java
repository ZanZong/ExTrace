package ts.serviceException;

import java.io.Serializable;

public class ServiceExceptionEntity implements Serializable {
    /** 
     * ע������ 
     */  
    private static final long serialVersionUID = 1L;  
  
    public ServiceExceptionEntity(){}  
      
    public ServiceExceptionEntity(int errCode, String errSubCode, String message) {  
        super();  
        this.errCode = errCode;  
        this.errSubCode = errSubCode;  
        this.message = message;  
    }  
      
    /*�����룬Ĭ��Ϊ0*/  
    private int errCode;  
      
    /*�������룬�Զ����ֵ*/  
    private String errSubCode;  
      
    private String message;  
  
    public int getErrCode() {  
        return errCode;  
    }  
  
    public void setErrCode(int errCode) {  
        this.errCode = errCode;  
    }  
  
    public String getErrSubCode() {  
        return errSubCode;  
    }  
  
    public void setErrSubCode(String errSubCode) {  
        this.errSubCode = errSubCode;  
    }  
  
    public String getMessage() {  
        return message;  
    }  
  
    public void setMessage(String message) {  
        this.message = message;  
    }  
}
