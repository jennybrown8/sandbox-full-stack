package net.simsa.fullstack.entity.util;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

/**
 * Simplified plumbing for a data provider that just needs to return a list of entities wrapped in entity
 * models.
 * 
 * @param <T>
 */
public abstract class EntityProvider<T,S> extends SortableDataProvider<T,S> {
	public EntityProvider()
	{
		CdiContainer.get().getNonContextualManager().inject(this);
	}

	public IModel<T> model(T entity)
	{
		return new EntityModel<T>(entity);
	}
}