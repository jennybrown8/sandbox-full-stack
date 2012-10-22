package net.simsa.fullstack.pages;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.ftlines.metagen.wicket.MetaModel;
import net.simsa.fullstack.entity.util.EntityProvider;
import net.simsa.fullstack.model.Clock;
import net.simsa.fullstack.model.User;
import net.simsa.fullstack.model.User.UserSort;
import net.simsa.fullstack.model.UserMeta;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
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
		add(new Label("clock", new PropertyModel<String>(this, "clock.time")));

		add(new Label("count", new CountModel()));

		add(new DataView<User>("users", new UserProvider()) {
			@Override
			protected void populateItem(Item<User> item)
			{
				item.add(new Label("first", MetaModel.of(item.getModel()).get(UserMeta.firstName)));
				item.add(new Label("last", MetaModel.of(item.getModel()).get(UserMeta.lastName)));
				
				PageParameters pageParams = new PageParameters();
				pageParams.add("email", MetaModel.of(item.getModel()).get(UserMeta.email).getObject());
				
				BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", EmailPage.class, pageParams);
				link.add(new Label("email", MetaModel.of(item.getModel()).get(UserMeta.email)));
				item.add(link);
			}
		});

	}

	public final class CountModel extends LoadableDetachableModel<Long> {
		{
			CdiContainer.get().getNonContextualManager().inject(this);
		}
		@Inject
		EntityManager em;

		@Override
		protected Long load()
		{
			return (Long) em.createQuery("SELECT COUNT(*) FROM User").getSingleResult();
		}
	}

	private static class UserProvider extends EntityProvider<User,UserSort> {
		@Inject
		EntityManager em;

		public Iterator<? extends User> iterator(long first, long count)
		{
			return em.createQuery("FROM User", User.class).setFirstResult( (int) first).setMaxResults( (int)count).getResultList()
					.iterator();
		}

		public long size()
		{
			Long count = (Long) em.createQuery("SELECT COUNT(*) FROM User").getSingleResult();
			return count.longValue();
		}
	}

}
