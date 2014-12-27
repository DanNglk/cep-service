package org.jvalue.ceps.notifications.garbage;

import com.google.inject.Inject;

import org.jvalue.ceps.notifications.clients.ClientVisitor;
import org.jvalue.ceps.notifications.clients.GcmClient;
import org.jvalue.ceps.utils.Assert;


public final class GarbageCollectorMapper implements ClientVisitor<Void, CollectionStatus> {

	private final GarbageCollector gcmCollector;

	@Inject
	GarbageCollectorMapper(
			GarbageCollector gcmCollector) {

		Assert.assertNotNull(gcmCollector);
		this.gcmCollector = gcmCollector;
	}


	@Override
	public CollectionStatus visit(GcmClient client, Void param) {
		return gcmCollector.determineStatus(client.getDeviceId());
	}

}
