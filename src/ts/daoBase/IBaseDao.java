/**
 * 
 */
package ts.daoBase;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
/**
 * �ṩhibernate dao�����в���,<br>
 * ʵ������springע��HibernateEntityDao��HibernateGenericDao��ʵ��
 * 
 */
public interface IBaseDao<T,PK extends Serializable> {
	
    /**
     * ����ID��ȡ����. ʵ�ʵ���Hibernate��session.load()��������ʵ�����proxy����. ������󲻴��ڣ��׳��쳣.
     */
    public T get(PK id);

    /**
     * ��ȡȫ������
     * 
     * @see HibernateGenericDao#getAll(Class)
     */
    public List<T> getAll();
    
    /**
     * ��ȡȫ������,���������.
     */
    public List<T> getAll(String orderBy, boolean isAsc);
    
    /**
     * ����������������ֵ��ѯ����.
     * 
     * @return ���������Ķ����б�
     */
    public List<T> findBy(String orderBy, boolean isAsc, Criterion... criterions);
   
    /**
     * ����������������ֵ��ѯ����,���������.
     * 
     * @return ���������Ķ����б�
     */
    public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc);
    
    public List<T> findLike(String propertyName, Object value, String orderBy, boolean isAsc);
    
    /**
     * �������.
     */
    public void save(T entity);
    
    /**
     * �ڲ�ͬ��session�й����޸Ĺ����йܶ���
     */
    public void update(T entity);

    /**
     * ɾ������.
     */
    public void remove(T entity);
    
    /**
     * ����ID�Ƴ�����.
     */
    public void removeById(PK id);
    
    /**
     * ������ Hibernate Session �Ĺ���
     * 
     */
    public void evit(T entity);
            
    public void flush();
    
    public void clear();

}
