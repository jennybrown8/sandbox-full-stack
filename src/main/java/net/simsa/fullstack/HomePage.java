package net.simsa.fullstack;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.ftlines.wicket.cdi.CdiContainer;
import net.simsa.fullstack.entity.util.EntityProvider;
import net.simsa.fullstack.model.Clock;
import net.simsa.fullstack.model.User;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@Inject
	Clock clock;

	public HomePage(final PageParameters parameters)
	{
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
		add(new Label("clock", new PropertyModel(this, "clock.time")));

		add(new Label("count", new LoadableDetachableModel() {
			{
				CdiContainer.get().getNonContextualManager().inject(this); // 1
			}

			@Inject
			EntityManager em;

			@Override
			protected Long load()
			{
				return (Long) em.createQuery("SELECT COUNT(*) FROM User").getSingleResult(); // 3
			}
		}));

		add(new DataView<User>("users", new UserProvider()) {
			@Override
			protected void populateItem(Item<User> item)
			{
				IModel<User> user = item.getModel();
				item.add(new Label("first", new PropertyModel<String>(user, "firstName")));
				item.add(new Label("last", new PropertyModel<String>(user, "lastName")));
				item.add(new Label("email", new PropertyModel<String>(user, "email")));
			}
		});

	}

	private static class UserProvider extends EntityProvider<User> {
		@Inject
		EntityManager em;

		public Iterator<User> iterator(int first, int count)
		{
			return em.createQuery("FROM User").setFirstResult(first).setMaxResults(count).getResultList()
					.iterator();
		}

		public int size()
		{
			Long count = (Long) em.createQuery("SELECT COUNT(*) FROM User").getSingleResult();
			return count.intValue();
		}
	}

}
