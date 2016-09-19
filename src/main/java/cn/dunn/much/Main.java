package cn.dunn.much;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {
	public static void main(String[] args) {
		VertxOptions option = new VertxOptions();
		option.setEventLoopPoolSize(32);
		Vertx vertx = Vertx.vertx(option);
		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setInstances(32);
		vertx.deployVerticle(MyVerticle1.class.getName(), deploymentOptions);
	}
}

