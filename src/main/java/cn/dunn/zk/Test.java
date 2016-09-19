package cn.dunn.zk;

import java.util.UUID;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Test {
	public static void main(String[] args) throws Throwable {
		// RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
		// CuratorFramework curatorFramework =
		// CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").namespace("zkTest").sessionTimeoutMs(100).connectionTimeoutMs(100)
		// .retryPolicy(retryPolicy).build();
		// curatorFramework.start();
		// long currentTimeMillis = System.currentTimeMillis();
		// for (int i = 0; i < 1000000; i++) {
		// curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/"
		// + UUID.randomUUID().toString(), "192.168.2.4:8080".getBytes());
		// }
		// System.err.println(System.currentTimeMillis() - currentTimeMillis);
		// Thread.sleep(Long.MAX_VALUE);
		System.out.println(9 % 252);
	}
}
