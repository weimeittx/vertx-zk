package cn.dunn.tcp;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Server {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(new VertxOptions().setEventLoopPoolSize(8));
		vertx.deployVerticle(TcpVerticle.class.getName(), new DeploymentOptions().setInstances(8));
		// vertx.createNetServer().connectHandler(netSocket -> {
		// // String writeHandlerID = netSocket.writeHandlerID();
		// // System.out.println(writeHandlerID);
		// // System.out.println("新的连接");
		// String writeHandlerID = netSocket.writeHandlerID();
		//
		// vertx.eventBus().<String> consumer(writeHandlerID + "/send", message
		// -> {
		// String body = message.body();
		// });
		// netSocket.closeHandler(handler -> {
		// // System.out.println(writeHandlerID);
		// });
		// netSocket.handler(buffer -> {
		// try {
		// Thread.sleep(500);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // System.out.println("接受到数据-> " + buffer.toString());
		// netSocket.write(buffer);
		// });
		// }).listen(8090);
	}
}
