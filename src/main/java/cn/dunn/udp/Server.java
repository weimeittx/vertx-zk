package cn.dunn.udp;

import java.math.BigDecimal;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.net.SocketAddress;

public class Server {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		DatagramSocket datagramSocket = vertx.createDatagramSocket();
		datagramSocket.handler(data -> {
			Buffer data2 = data.data();
			SocketAddress sender = data.sender();
			datagramSocket.send(data2, sender.port(), sender.host(), handler -> {
			});
		}).listen(8080, "localhost", socket -> {
			if (socket.succeeded()) {
				System.err.println("success");
			} else {
				System.err.println("failure");
			}
		});
		System.out.println(new BigDecimal(" 55.000 ".trim()));
	}
}
