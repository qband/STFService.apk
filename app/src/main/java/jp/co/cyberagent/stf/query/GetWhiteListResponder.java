package jp.co.cyberagent.stf.query;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import jp.co.cyberagent.stf.Service;
import jp.co.cyberagent.stf.proto.Wire;

public class GetWhiteListResponder extends AbstractResponder {
	public GetWhiteListResponder(Context context) {
		super(context);
	}

	@Override
	public GeneratedMessage respond(Wire.Envelope envelope) throws InvalidProtocolBufferException {
		Wire.GetWhiteListRequest request =
				Wire.GetWhiteListRequest.parseFrom(envelope.getMessage());

		switch (request.getType()) {
			case LIST:
				CharSequence text = getWhiteListText();

				if (text == null) {
					return Wire.Envelope.newBuilder()
							.setId(envelope.getId())
							.setType(Wire.MessageType.GET_WHITELIST)
							.setMessage(Wire.GetWhiteListResponse.newBuilder()
									.setSuccess(true)
									.setType(Wire.WhiteListType.LIST)
									.build()
									.toByteString())
							.build();
				}

				return Wire.Envelope.newBuilder()
						.setId(envelope.getId())
						.setType(Wire.MessageType.GET_WHITELIST)
						.setMessage(Wire.GetWhiteListResponse.newBuilder()
								.setSuccess(true)
								.setType(Wire.WhiteListType.LIST)
								.setText(text.toString())
								.build()
								.toByteString())
						.build();
			default:
				return Wire.Envelope.newBuilder()
						.setId(envelope.getId())
						.setType(Wire.MessageType.GET_WHITELIST)
						.setMessage(Wire.GetWhiteListResponse.newBuilder()
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

	private CharSequence getWhiteListText() {
		return "[\"x\", \"y\"]";
	}
}
