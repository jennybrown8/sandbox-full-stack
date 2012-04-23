package net.simsa.fullstack;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
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
	}
}
