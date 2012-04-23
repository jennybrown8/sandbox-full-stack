package net.simsa.fullstack.entity.util;

import javax.persistence.EntityManagerFactory;

public class EntityManagerFactoryCreatedEvent {
	private final EntityManagerFactory emf;

	public EntityManagerFactoryCreatedEvent(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	public EntityManagerFactory getEntityManagerFactory()
	{
		return emf;
	}
}