package example.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetClient;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Client {
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
				NetClient netClient = vertx.createNetClient();
				netClient.connect(8080, "localhost", result -> {
					if (result.succeeded()) {
						result.result().write("client");
//						NetSocket netSocket = result.result();
						vertx.eventBus().consumer("client", message -> {
							System.out.println(message.body());
							vertx.eventBus().send("server", message.body());
						});

					} else {
						System.out.println("服务没有开启!");
					}
				});
			}
		});

	}
}
