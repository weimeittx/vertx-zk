package example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;

public class MyVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		NetServer netServer = vertx.createNetServer();
		netServer.connectHandler(netSocket -> {
			netSocket.handler(buffer -> {
				netSocket.write(buffer);
			});
		});
		Integer port = vertx.getOrCreateContext().config().getInteger("port");
		netServer.listen(port);
	}

}
