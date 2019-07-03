package cn.zhh.core.starter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.SerializationUtils;

public class Encoder extends MessageToByteEncoder<Object> {

	@Override
    public void encode(ChannelHandlerContext ctx, Object inObj, ByteBuf out) throws Exception {
		// 序列化
		byte[] data = SerializationUtils.serialize(inObj);
		out.writeInt(data.length);
		out.writeBytes(data);
	}
}