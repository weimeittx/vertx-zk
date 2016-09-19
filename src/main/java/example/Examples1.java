package example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * Created by stream.
 */
public class Examples1 {

	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1000);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").namespace("io.vertx").sessionTimeoutMs(20000).connectionTimeoutMs(3000)
				.retryPolicy(retryPolicy).build();
		curatorFramework.start();
		ClusterManager mgr = new ZookeeperClusterManager(curatorFramework);
		VertxOptions options = new VertxOptions().setClusterManager(mgr);

		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				System.out.println("启动成功!");
				Vertx vertx = res.result();
				MessageConsumer<Object> consumer = vertx.eventBus().consumer("", Message ->{});
				consumer.completionHandler(voi->{
						
				});
				vertx.sharedData().getClusterWideMap("hehe", resultHandler->{
					AsyncMap<Object, Object> asyncMap = resultHandler.result();
					asyncMap.get("key", resutlValue->{
						Object result = resutlValue.result();
						System.out.println(result);
					});
				});
			} else {
				System.out.println("失败");
			}
		});

	}
}
