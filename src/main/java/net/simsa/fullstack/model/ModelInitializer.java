package net.simsa.fullstack.model;

import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.simsa.fullstack.entity.util.EntityManagerFactoryCreatedEvent;

/**
 * Dummy class to pre-populate the database with some fake data. 
 */
public class ModelInitializer {
	  private final String[] firstNames = { "Jacob", "Emily" };
	  private final String[] lastNames = { "Smith", "Johnson" };
	 
	  private static <T> T random(T[] values) {
	    return values[(int)(Math.random() * values.length)];
	  }
	 
	  public void initialize(@Observes EntityManagerFactoryCreatedEvent created) { //1
	    EntityManagerFactory emf = created.getEntityManagerFactory(); //2
	    EntityManager em = emf.createEntityManager();
	 
	    em.getTransaction().begin(); //3
	    for (int i = 0; i < 30; i++) {
	      User e = new User();
	      e.setFirstName(random(firstNames));
	      e.setLastName(random(lastNames));
	      e.setEmail(e.getFirstName() + "." + e.getLastName() + "@firm.com");
	      em.persist(e);
	    }
	    em.getTransaction().commit();
	  }
	}