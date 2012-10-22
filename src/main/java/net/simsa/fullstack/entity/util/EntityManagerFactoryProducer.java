package net.simsa.fullstack.entity.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerFactoryProducer {

	@Inject
	Event<EntityManagerFactoryCreatedEvent> created;

	@Produces
	@ApplicationScoped
	public EntityManagerFactory create()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cdidemo");
		created.fire(new EntityManagerFactoryCreatedEvent(emf));
		return emf;
	}

	public void destroy(@Disposes EntityManagerFactory factory)
	{
		factory.close();
	}
}
