package net.simsa.fullstack.entity.util;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;

/**
 * A way to begin/apply/cancel a set of entity modifications based on user activity; this handles the entity
 * manager manipulation to clear partially changed entities, or to commit the results, transactionally. See
 * https://www.42lines.net/2011/12/01/simplifying-non-trivial-user-workflows-with-conversations/
 */
@ConversationScoped
public class UserAction implements Serializable {

	@Inject
	Conversation conversation;

	@Inject
	EntityManager em;

	public UserAction begin()
	{
		if (conversation.isTransient()) {
			conversation.begin();
		}
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
		em.setFlushMode(FlushModeType.COMMIT);
		return this;
	}

	public UserAction apply()
	{
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			em.flush();
		} catch (RuntimeException e) {
			txn.rollback();
			throw e;
		}
		txn.commit();
		return this;
	}

	public UserAction undo()
	{
		em.clear();
		return this;
	}
}