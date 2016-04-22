package ts.daoBase;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ts.daoBase.IBaseDao;

/**
 * �ṩhibernate dao�����в��� * 
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW )
public class BaseDao<T,PK extends Serializable> extends HibernateDaoSupport implements IBaseDao<T,PK> {
	protected Class<T> entityClass;			// DAO�������Entity����.
    
    /**
     *��spring�ṩ���캯��ע��
     */
    public BaseDao(Class<T> type) {
        this.entityClass = type;
    }
    
    public BaseDao(){
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

	@Override
	public T get(PK id) {
		return getHibernateTemplate().load(getEntityClass(), id);
	}
	
	@Override
	public List<T> getAll() {
		return (List<T>)(getHibernateTemplate().loadAll(getEntityClass()));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
        if (isAsc)
            return (List<T>) getHibernateTemplate().findByCriteria(
                    DetachedCriteria.forClass(getEntityClass()).addOrder(Order.asc(orderBy)));
        else
            return (List<T>) getHibernateTemplate().findByCriteria(
                    DetachedCriteria.forClass(getEntityClass()).addOrder(Order.desc(orderBy)));
	}

	//ǰ���������������򣬺�������ж��������ʹ��Restriction�ķ�����������
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findBy(String orderBy, boolean isAsc, Criterion... criterions) {
    	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        if (isAsc)
        	criteria.addOrder(Order.asc(orderBy));
        else
        	criteria.addOrder(Order.desc(orderBy));

        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}
/*  getHibernateTemplate().findByCriteria(criteria)���Բ�ѯ����Ҫ��ҳ�����ݣ�
	getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults)����������ѯ��Ҫ�ķ�ҳ�����ԣ�
  HibernateTemplate ht=this.getHibernateTemplate();
  DetachedCriteria criteria=DetachedCriteria.forClass(Paper.class);
  ht.findByCriteria(criteria, firstResult, maxResults);
 */
	//ѡ��propertyName��ֵ����value�������������Ϊ��������
	@Override
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return findBy(orderBy, isAsc, Restrictions.eq(propertyName, value));
        //return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}
	//ѡ��propertyName��ֵ����value�������������Ϊ��������
	@Override
	public List<T> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return findBy(orderBy, isAsc, Restrictions.like(propertyName, value));
        //return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}
	
	@Override
	public void save(T entity) {
		/*getHibernateTemplate().saveOrUpdate(entity);*/
		 try {
			 getHibernateTemplate().save(entity);
			  } catch (RuntimeException re) {
			  re.printStackTrace();
			  }
		
	}
	
	@Override
	public void update(T entity) {
		try {
		getHibernateTemplate().update(entity);
		 } catch (RuntimeException re) {
			  re.printStackTrace();
			  }
	}
	
	@Override
	public void remove(T entity) {
		try{
		getHibernateTemplate().delete(entity);
	 	} catch (RuntimeException re) {
		  re.printStackTrace();
		  }
	}
	
	@Override
	public void removeById(PK id) {
		try{
		remove(get(id));
		 } catch (RuntimeException re) {
			  re.printStackTrace();
			  }
	}
	
	@Override
	public void evit(T entity) {
		try{		
			getHibernateTemplate().evict(entity);
			} catch (RuntimeException re) {
			  re.printStackTrace();
		}
		
	}
	
	@Override
	public void flush() {
		try{
		getHibernateTemplate().flush();
		 } catch (RuntimeException re) {
			  re.printStackTrace();
			  }
	}
	
	@Override
	public void clear() {
		try{
		getHibernateTemplate().clear();
		 } catch (RuntimeException re) {
			  re.printStackTrace();
			  }
	}
}