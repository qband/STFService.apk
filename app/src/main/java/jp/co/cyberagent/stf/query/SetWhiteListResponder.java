package jp.co.cyberagent.stf.query;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import jp.co.cyberagent.stf.Service;
import jp.co.cyberagent.stf.proto.Wire;

public class SetWhiteListResponder extends AbstractResponder {
	public static final String TAG = SetWhiteListResponder.class.getSimpleName();
	public SetWhiteListResponder(Context context) {
		super(context);
	}

	@Override
	public GeneratedMessage respond(Wire.Envelope envelope) throws InvalidProtocolBufferException {
		Wire.SetWhiteListRequest request =
				Wire.SetWhiteListRequest.parseFrom(envelope.getMessage());

		switch (request.getType()) {
			case LIST:
				setWhiteListText(request.getText());
				return Wire.Envelope.newBuilder()
						.setId(envelope.getId())
						.setType(Wire.MessageType.SET_WHITELIST)
						.setMessage(Wire.SetWhiteListResponse.newBuilder()
								.setSuccess(true)
								.build()
								.toByteString())
						.build();
			default:
				return Wire.Envelope.newBuilder()
						.setId(envelope.getId())
						.setType(Wire.MessageType.SET_WHITELIST)
						.setMessage(Wire.SetWhiteListResponse.newBuilder()
								.setSuccess(false)
								.build()
								.toByteString())
						.build();
		}
	}

	@Override
	public void cleanup() {
		// No-op
	}

	private void setWhiteListText(String content) {
		Log.i(TAG, content);
	}
}
