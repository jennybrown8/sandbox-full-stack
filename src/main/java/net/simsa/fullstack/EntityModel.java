package net.simsa.fullstack;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.ftlines.wicket.cdi.CdiContainer;

import org.apache.wicket.model.IModel;

public class EntityModel<T> implements IModel<T> {

	@Inject
	private EntityManager em; // 1

	private Object id; // 2
	private Class type;

	private transient T entity; // 3

	public EntityModel(T entity)
	{
		CdiContainer.get().getNonContextualManager().inject(this); // 4
		setObject(entity);
	}

	public T getObject()
	{
		if (entity == null) {
			entity = (T) em.find(type, id);
		} // 5
		return entity;
	}

	public final void setObject(T other)
	{
		type = other.getClass();
		id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(other); // 6
		entity = other;
	}

	public void detach()
	{
		entity = null;
	} // 5
}