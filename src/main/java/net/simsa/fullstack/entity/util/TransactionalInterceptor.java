package net.simsa.fullstack.entity.util;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 * Does the work of beginning, committing, or rolling back a transaction; implementation to match the
 * Transactional annotation.
 */
@Transactional
@Interceptor
public class TransactionalInterceptor {
	@Inject
	EntityManager em;

	@AroundInvoke
	public Object wrapInTransaction(InvocationContext invocation) throws Exception
	{
		boolean owner = !em.getTransaction().isActive();

		if (owner) {
			em.getTransaction().begin();
		}

		try {
			return invocation.proceed();
		} catch (RuntimeException e) {
			em.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if (owner) {
				if (em.getTransaction().getRollbackOnly()) {
					em.getTransaction().rollback();
				} else {
					em.getTransaction().commit();
				}
			}
		}
	}
}