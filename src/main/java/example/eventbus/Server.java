package example.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Server {
	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1000);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").namespace("io.vertx").sessionTimeoutMs(20000).connectionTimeoutMs(3000)
				.retryPolicy(retryPolicy).build();
		curatorFramework.start();
		ClusterManager mgr = new ZookeeperClusterManager(curatorFramework);
		VertxOptions options = new VertxOptions().setClusterManager(mgr);
		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				System.out.println("连接成功!");
				Vertx vertx = res.result();
				NetServer netServer = vertx.createNetServer();
				List<NetSocket> list = new ArrayList<>();
				netServer.connectHandler(netSocket -> {
					list.add(netSocket);
					netSocket.handler(buffer -> {
						if (buffer.equals("client")) {

							MessageConsumer<String> consumer = vertx.eventBus().<String> consumer("", message -> {
								String body = message.body();
							});

							vertx.eventBus().consumer("server", message -> {
								Object body = message.body();
								list.forEach(s -> s.write(body.toString()));
							});
						}
						vertx.eventBus().send("client", buffer.toString());
					});

				});
				netServer.listen(8080);
			}
		});
	}
}
