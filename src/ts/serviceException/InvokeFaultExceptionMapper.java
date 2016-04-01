package ts.serviceException;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.service.spi.ServiceException;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

@Provider  
public class InvokeFaultExceptionMapper implements ExceptionMapper {  
  
    public Response toResponse(Throwable ex) {  
        StackTraceElement[] trace = new StackTraceElement[1];  
           trace[0] = ex.getStackTrace()[0];  
           ex.setStackTrace(trace);  
           ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);  
           rb.type("application/json;charset=UTF-8");  
           
           if (ex instanceof HibernateObjectRetrievalFailureException) {//�Զ�����쳣��  
        	   HibernateObjectRetrievalFailureException e = (HibernateObjectRetrievalFailureException) ex;  
//        	   rb = Response.status(Response.Status.NOT_FOUND);  
//               rb.type("application/json;charset=UTF-8");
        	   //������Ҫд�ĸ���һ��,��Ӣ�ĵĴ���ת��
               rb.entity(e.getLocalizedMessage());  
           }else if (ex instanceof ServiceException) {//�Զ�����쳣��  
               ServiceException e = (ServiceException) ex;  
               rb.entity(e.getMessage());  
           }else{  
               rb.entity(ex);  
           }  
           rb.language(Locale.SIMPLIFIED_CHINESE);  
           Response r = rb.build();  
           return r;  
   }  
}  
