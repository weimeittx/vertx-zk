package example;

import io.vertx.core.Vertx;

public class TestMain {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.createNetServer().connectHandler(netSocket -> {
			netSocket.handler(bufferHandler -> {
				byte[] bytes = bufferHandler.getBytes();
			});
		}).listen(8080);
	}
}
