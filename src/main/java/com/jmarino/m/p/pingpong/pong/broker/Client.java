package com.jmarino.m.p.pingpong.pong.broker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.jmarino.m.p.pingpong.pong.service.PongStatistics;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.StandardMetricsCollector;

@Component
public class Client {

	protected ConnectionFactory connectionFactory;
	protected Connection connection;

	protected StandardMetricsCollector metrics;
	protected PingMessageConsumer pingMessageConsumer;

	public Client() {
		this.connectionFactory = new ConnectionFactory();
		this.metrics = new StandardMetricsCollector();
		this.connectionFactory.setMetricsCollector(metrics);
		this.connectionFactory.setHost("localhost");
		this.connectionFactory.setAutomaticRecoveryEnabled(true);
		try {
			this.connection = this.connectionFactory.newConnection();
			this.initPingMessageRead();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void sendPongMessage(String message) {
		Channel channel = null;
		try {
			channel = this.connection.createChannel();
			channel.queueDeclare(ConstansProperties.PONG_QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", ConstansProperties.PONG_QUEUE_NAME, null, message.getBytes());
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			// Cuando se cierra el canal.
			e.printStackTrace();
		}
	}

	public void initPingMessageRead() {
		try {
			Channel channel = this.connection.createChannel();
			channel.queueDeclare(ConstansProperties.PING_QUEUE_NAME, false, false, false, null);
//			channel.basicConsume(ConstansProperties.PING_QUEUE_NAME, new PingMessageConsumer(channel, this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PongStatistics getPongStatistics() {
		PongStatistics pongStatistics = new PongStatistics();
		pongStatistics.totalPingMessageRead = this.metrics.getConsumedMessages().getCount();
		pongStatistics.totalPingMessageReplied = this.metrics.getPublishedMessages().getCount();
		return pongStatistics;
	}

}
