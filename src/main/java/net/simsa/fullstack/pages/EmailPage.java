package net.simsa.fullstack.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Just a place to demonstrate page mounting and linking.
 * @author Jenny Brown
 *
 */
public class EmailPage extends WebPage {

	public EmailPage(PageParameters parameters)
	{
		super(parameters);
		add(new Label("email", parameters.get("email").toString("(none provided)")));
	}

}
