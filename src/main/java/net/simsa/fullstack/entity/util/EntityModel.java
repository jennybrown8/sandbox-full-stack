package net.simsa.fullstack.entity.util;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.ftlines.wicket.cdi.CdiContainer;

import org.apache.wicket.model.IModel;

/**
 * Understands how to pull the id of an object out while it's serialized to disk in the page store, and then
 * reload the entity by id once the Page is being deserialized for use again. See
 * https://www.42lines.net/2011/11/21/adding-jpahibernate-into-the-cdi-and-wicket-mix/
 * 
 * @param <T>
 */
public class EntityModel<T> implements IModel<T> {

	@Inject
	private EntityManager em;

	private Object id;
	private Class type;

	private transient T entity;

	public EntityModel(T entity)
	{
		CdiContainer.get().getNonContextualManager().inject(this);
		setObject(entity);
	}

	public T getObject()
	{
		if (entity == null) {
			entity = (T) em.find(type, id);
		}
		return entity;
	}

	public final void setObject(T other)
	{
		type = other.getClass();
		id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(other);
		entity = other;
	}

	public void detach()
	{
		entity = null;
	}
}