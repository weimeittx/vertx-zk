package cn.dunn.web;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Service2 {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		Router router = Router.router(vertx);
		BridgeOptions opts = new BridgeOptions().addInboundPermitted(new PermittedOptions()).addOutboundPermitted(new PermittedOptions());

		// Create the event bus bridge and add it to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);
		// Create a router endpoint for the static content.
		router.route().handler(StaticHandler.create());

		// Start the web server and tell it to use the router to handle
		// requests.
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

		EventBus eb = vertx.eventBus();

		// Register to listen for messages coming IN to the server
		eb.<JsonObject> consumer("chat.to.server").handler(message -> {
			System.out.println("接受到消息 : " + message.body());
		});
		eb.<JsonObject> consumer("ssssss").handler(message -> {
			User user = new User();
			user.setUsername("weimei");
			user.setPassword("123");
			user.setAge(55);
			eb.send("weimei", new JsonObject(JSONObject.toJSONString(user)));
			System.out.println("接受到消息 : " + message.body().getString("username"));
		});

	}
}
