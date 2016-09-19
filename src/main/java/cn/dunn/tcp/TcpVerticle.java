package cn.dunn.tcp;

import io.vertx.core.AbstractVerticle;

public class TcpVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		vertx.createNetServer().connectHandler(netSocket -> {
			netSocket.handler(buffer -> {
//				try {
//					Thread.sleep(1);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				netSocket.write(buffer);
			});
		}).listen(8090);

	}

}
