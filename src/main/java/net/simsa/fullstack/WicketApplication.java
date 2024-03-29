package net.simsa.fullstack;

import javax.enterprise.inject.spi.BeanManager;

import net.ftlines.wicketsource.WicketSource;
import net.simsa.fullstack.pages.EmailPage;
import net.simsa.fullstack.pages.HomePage;

import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.jboss.weld.environment.servlet.Listener;

/**
 * Application object for your web application. If you want to run this application without deploying, run the
 * Start class in the test/src folder.
 * 
 * @see net.simsa.fullstack.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		BeanManager manager = (BeanManager) getServletContext().getAttribute(Listener.BEAN_MANAGER_ATTRIBUTE_NAME);
		new CdiConfiguration(manager).configure(this);

		WicketSource.configure(this);

		// in application servers you can retrieve the bean manager from JNDI:
		// manager = (BeanManager)new InitialContext().lookup("java:comp/BeanManager");

		// Want to mount any pages?
		mountPage("/send", EmailPage.class);

	}
}
