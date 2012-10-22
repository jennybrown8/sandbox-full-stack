package net.simsa.fullstack.entity.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.bound.Bound;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundRequest;
import org.jboss.weld.context.bound.MutableBoundRequest;
import org.jboss.weld.context.http.Http;

/**
 * Wraps a section of code in a Conversation, whether that's a pre-existing one or a new one.
 * See https://www.42lines.net/2011/12/07/conversations-and-transactions-in-worker-threads/ 
 */
@Conversational
@Interceptor
public class ConversationalInterceptor {

	@Inject
	@Http
	ConversationContext context;

	@Inject
	@Bound
	BoundConversationContext boundContext;

	@AroundInvoke
	public Object wrapInConversation(InvocationContext invocation) throws Exception
	{

		BoundRequest storage = null;

		if (!context.isActive() && !boundContext.isActive()) {
			Map<String, Object> session = new HashMap<String, Object>();
			Map<String, Object> request = new HashMap<String, Object>();
			storage = new MutableBoundRequest(request, session);
			boundContext.associate(storage);
			boundContext.activate();
		}

		try {
			return invocation.proceed();
		} finally {
			if (storage != null) {
				boundContext.deactivate();
				boundContext.dissociate(storage);
			}
		}
	}
}