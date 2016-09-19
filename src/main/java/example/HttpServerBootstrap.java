package example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;

public class HttpServerBootstrap {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		router.route("/hehe/:username/:password").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			HttpServerRequest request = routingContext.request();
			String username = request.getParam("username");
			String password = request.getParam("password");
			String xixi = request.getParam("xixi");
			System.err.println(xixi);
			System.err.println("username is " + username + " and password is " + password);
			response.putHeader("content-type", "text/plain");
			response.setChunked(true);
			response.write("Hello World from Vert.x-Web! - hehe");
			response.write("Hello World from Vert.x-Web! - hehe");
			response.sendFile("");
			System.out.println(Thread.currentThread());
			routingContext.next();
		});
		router.route("/hehe/:username/:password").handler(routingContext -> {
			User user = routingContext.user();
			System.out.println(Thread.currentThread());
			HttpServerResponse response = routingContext.response();
			HttpServerRequest request = routingContext.request();
			String username = request.getParam("username");
			String password = request.getParam("password");
			String xixi = request.getParam("xixi");
			System.err.println(xixi);
			System.err.println("username is " + username + " and password is " + password);
			response.putHeader("content-type", "text/plain");
			response.end("Hello World from Vert.x-Web! - hehe");
		});
		// router.route().blockingHandler(routingContext -> {
		// // 这个处理程序将被调用的每个请求
		// HttpServerResponse response = routingContext.response();
		// response.putHeader("content-type", "text/plain");
		// try {
		// Thread.sleep(10000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// // 写入到响应并结束它
		// response.end("Hello World from Vert.x-Web!");
		// });
		server.requestHandler(router::accept).listen(8080);
	}
}
