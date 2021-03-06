package org.jvalue.ceps.notifications.sender;

import org.jvalue.ceps.api.notifications.Client;


public abstract class NotificationSender<C extends Client> {

	public abstract SenderResult sendEventUpdate(C client);


	protected SenderResult getSuccessResult() {
		return new SenderResult.Builder(SenderResult.Status.SUCCESS).build();
	}


	protected SenderResult getErrorResult(String msg) {
		return new SenderResult.Builder(SenderResult.Status.ERROR).errorMsg(msg).build();
	}


	protected SenderResult getErrorResult(Throwable cause) {
		return new SenderResult.Builder(SenderResult.Status.ERROR).errorCause(cause).build();
	}

	
	protected SenderResult getUpdateClientResult(String oldDeviceId, String newDeviceId) {
		return new SenderResult.Builder(SenderResult.Status.UPDATE_CLIENT)
			.updateDeviceId(oldDeviceId, newDeviceId)
			.build();
	}


	protected SenderResult getRemoveClientResult(String deviceId) {
		return new SenderResult.Builder(SenderResult.Status.REMOVE_CLIENT)
			.removeDeviceId(deviceId)
			.build();
	}

}
