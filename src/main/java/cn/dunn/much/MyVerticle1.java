package cn.dunn.much;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;

import java.util.UUID;

public class MyVerticle1 extends AbstractVerticle {
	@SuppressWarnings("unused")
	private String id;
	{
		id = UUID.randomUUID().toString();
	}

	@Override
	public void start() throws Exception {
		HttpServerOptions option = new HttpServerOptions();
		vertx.createHttpServer(option).requestHandler(request -> {
			try {
				// FileInputStream fileInputStream = new FileInputStream(new
				// File("E:\\test.jpg"));
				// ByteArrayOutputStream byteArrayOutputStream = new
				// ByteArrayOutputStream();
				// IOUtils.copy(fileInputStream, byteArrayOutputStream);
				// fileInputStream.close();
				// Buffer chunk =
				// Buffer.buffer(byteArrayOutputStream.toByteArray());
				// byteArrayOutputStream.close();
				// request.response().end(chunk);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				request.response().end(UUID.randomUUID().toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}).listen(8080);

	}

}