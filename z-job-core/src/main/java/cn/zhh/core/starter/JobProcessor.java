package cn.zhh.core.starter;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public class JobProcessor extends SimpleChannelInboundHandler<JobRequest> {

	@Autowired
	private JobExecutor jobExecutor;


	/**
	 * 接收消息，处理消息，返回结果
	 */
	@Override
	public void channelRead0(final ChannelHandlerContext ctx, JobRequest request) throws Exception {
		JobResponse response = null;
		try {
			// 根据request来处理具体的业务调用
			response = handle(request);

		} catch (Throwable t) {
			response = new JobResponse(JobResponse.FAIL_CODE, t.getMessage());
		}
		// 写入 out，由RpcEncoder进行下一步编码处理，后发送到channel中给客户端
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * 根据request来调用具体的定时器
	 * 
	 * @param request
	 * @return JobResponse
	 * @throws Throwable
	 */
	private JobResponse handle(JobRequest request) throws Throwable {
		// 查找IJobHandler对象
		IJobHandler jobHandler = jobExecutor.loadJobHandler(request.getName());
		if (Objects.isNull(jobHandler)) {
			return new JobResponse(JobResponse.FAIL_CODE, "IJobHandler对象不存在！");
		}

		// 调用
		return jobHandler.execute(request.getParams());
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("server caught exception!", cause);
		ctx.close();
	}
}