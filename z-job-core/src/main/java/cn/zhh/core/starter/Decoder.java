package cn.zhh.core.starter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.util.SerializationUtils;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {

	public static final int HEAD_LENGTH = 4;

	@Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < HEAD_LENGTH) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			ctx.close();
		}
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		// 将ByteBuf转换为byte[]
		byte[] bytes = new byte[dataLength];
		in.readBytes(bytes);
		// 将data转换成object
		Object obj = SerializationUtils.deserialize(bytes);
		out.add(obj);
	}
}