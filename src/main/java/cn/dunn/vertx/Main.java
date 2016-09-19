package cn.dunn.vertx;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class Main {
	public static void main(String[] args) throws Exception {
		Vertx vertx = Vertx.vertx();

		EventBus eventBus = vertx.eventBus();
		MessageConsumer<Object> consumer = eventBus.consumer("", message -> {
			MultiMap headers = message.headers();
		});
		consumer.completionHandler(v -> {

		});
		consumer.unregister(v -> {

		});
		consumer.handler(result -> {

		});

		vertx.setPeriodic(100, timeId -> {
			System.out.println(timeId);
			vertx.cancelTimer(timeId);
		});
		vertx.setTimer(100, timeId -> {
			System.out.println(timeId);
		});
		Context context = vertx.getOrCreateContext();
		context.put("key", "value");
		context.runOnContext(v -> {
			Object object = context.get("key");
		});
		// HttpServer httpServer = vertx.createHttpServer();
		Future<String> stringFuture = Future.future();
		Future<Integer> intFuture = Future.future();

		stringFuture.compose(r -> {
			return intFuture;
		});

		CompositeFuture.any(stringFuture, intFuture).setHandler(ar -> {
			if (ar.succeeded()) {
				System.out.println("成功");
			} else {
				System.out.println("失败");
			}
		});
		System.err.println("-----------");
		new Thread(() -> {
			try {
				Thread.sleep(1000);
				stringFuture.complete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				Thread.sleep(2000);
				intFuture.complete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		Thread.sleep(100000);
		// httpServer.listen(httpServerfuture.completer());

	}
}
