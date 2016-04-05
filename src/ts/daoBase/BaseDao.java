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
 * 提供hibernate dao的所有操作 * 
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW )
public class BaseDao<T,PK extends Serializable> extends HibernateDaoSupport implements IBaseDao<T,PK> {
	protected Class<T> entityClass;			// DAO所管理的Entity类型.
    
    /**
     *让spring提供构造函数注入
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

	//前两个参数设置排序，后面可以有多个参数，使用Restriction的方法描述对象
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
/*  getHibernateTemplate().findByCriteria(criteria)可以查询不需要分页的数据，
	getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults)可以用来查询需要的分页的属性，
  HibernateTemplate ht=this.getHibernateTemplate();
  DetachedCriteria criteria=DetachedCriteria.forClass(Paper.class);
  ht.findByCriteria(criteria, firstResult, maxResults);
 */
	//选择propertyName的值等于value的项，后两个参数为设置排序
	@Override
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return findBy(orderBy, isAsc, Restrictions.eq(propertyName, value));
        //return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}
	//选择propertyName的值含有value的项，后两个参数为设置排序
	@Override
	public List<T> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return findBy(orderBy, isAsc, Restrictions.like(propertyName, value));
        //return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}
	
	@Override
	public void save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
	
	@Override
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}
	
	@Override
	public void removeById(PK id) {
		remove(get(id));
	}
	
	@Override
	public void evit(T entity) {
		getHibernateTemplate().evict(entity);
	}
	
	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}
	
	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}
}