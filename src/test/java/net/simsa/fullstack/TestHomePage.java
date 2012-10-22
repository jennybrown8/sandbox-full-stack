package net.simsa.fullstack;

import net.simsa.fullstack.pages.HomePage;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{

	//Test
	public void homepageRendersSuccessfully()
	{
		WicketTester tester = new WicketTester(new WicketApplication());
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}
}
