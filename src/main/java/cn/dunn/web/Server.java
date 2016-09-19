package cn.dunn.web;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class Server {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		Router router = Router.router(vertx);
		// Allow events for the designated addresses in/out of the event bus
		// bridge
//		BridgeOptions opts = new BridgeOptions()
//		.addInboundPermitted(new PermittedOptions().setAddressRegex("s*"))
//		.addOutboundPermitted(new PermittedOptions().setAddressRegex("s*"));// .setAddress("chat.to.client"));
		BridgeOptions opts = new BridgeOptions()
		.addInboundPermitted(new PermittedOptions().setRequiredAuthority("auth"))
		.addOutboundPermitted(new PermittedOptions().setAddressRegex("s*"));// .setAddress("chat.to.client"));

		SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);
		// Create the event bus bridge and add it to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx,options).bridge(opts);
		ebHandler.socketHandler(socket -> {
			System.out.println("----------------");
		});
		router.route("/eventbus/*").handler(ebHandler);


		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options);
		sockJSHandler.socketHandler(sockJSSocket -> {
			System.out.println(sockJSSocket.webSession());
			System.out.println(sockJSSocket.webUser());
			// 只是回波返回的数据
			// sockJSSocket.handler(message->{
			// sockJSSocket.write(message);
			// });
			// long timeId = vertx.setPeriodic(2000, id -> {
			// sockJSSocket.write(Buffer.buffer(UUID.randomUUID().toString()));
			// });
			// sockJSSocket.endHandler(end -> {
			// vertx.cancelTimer(timeId);
			// });
				sockJSSocket.handler(buffer -> {
					System.out.println(buffer.toString());
				});
			});

		router.route("/myapp/*").handler(sockJSHandler);

		// Create a router endpoint for the static content.
		router.route().handler(StaticHandler.create());

		// Start the web server and tell it to use the router to handle
		// requests.
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

		EventBus eb = vertx.eventBus();

		// Register to listen for messages coming IN to the server
		eb.<JsonObject> consumer("chat.to.server").handler(message -> {
			// Create a timestamp string
				String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
				// Send the message back out to all clients with the timestamp
				// prepended.
				eb.publish("chat.to.client", timestamp + ": " + message.body());
				System.out.println("接受到消息 : " + message.body());
			});
		eb.<JsonObject> consumer("ssssss").handler(message -> {
			// Create a timestamp string
				String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
				// Send the message back out to all clients with the timestamp
				// prepended.
				eb.publish("chat.to.client", timestamp + ": " + message.body());
				System.out.println("接受到消息 : " + message.body().getString("username"));
			});
	}
}
