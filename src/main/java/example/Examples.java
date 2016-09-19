package example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.core.streams.Pump;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by stream.
 */
public class Examples {

	public static void main(String[] args) {

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").namespace("io.vertx").sessionTimeoutMs(100).connectionTimeoutMs(100)
				.retryPolicy(retryPolicy).build();
		curatorFramework.start();
		ClusterManager mgr = new ZookeeperClusterManager(curatorFramework);
		VertxOptions options = new VertxOptions().setClusterManager(mgr);
		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				Vertx vertx = res.result();
				System.out.println("启动成功");
				vertx.eventBus().<String> consumer("hehe1", message -> {
					String body = message.body();
					System.out.println("接受到消费的消息" + body);
				});
				
			} else {
				System.out.println("失败");
			}
		});

	}
}
