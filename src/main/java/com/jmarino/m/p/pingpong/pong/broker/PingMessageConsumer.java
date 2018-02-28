package com.jmarino.m.p.pingpong.pong.broker;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class PingMessageConsumer {
	private BrokerConnMngmt brokerConnMngmt;
	private PongMessagePublisher pongMessagePublisher;

	public PingMessageConsumer(BrokerConnMngmt brokerMessage, PongMessagePublisher pongMessagePublisher) {
		this.brokerConnMngmt = brokerMessage;
		this.pongMessagePublisher = pongMessagePublisher;
		for (int i = 0; i <= 7; i++) {
			this.initConsumer();
		}
	}

	private void initConsumer() {
		Channel channel = this.brokerConnMngmt.getChannel();
		try {
			channel.queueDeclare(ConstansProperties.PING_QUEUE_NAME, false, false, false, null);
			channel.basicConsume(ConstansProperties.PING_QUEUE_NAME, true, this.new Consumer(channel));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Consumer extends DefaultConsumer {

		public Consumer(Channel channel) {
			super(channel);
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			ObjectMapper mapper = new ObjectMapper();
			BrokerMessage brokerMessage = mapper.readValue(body, BrokerMessage.class);
			brokerMessage.message = ConstansProperties.PONG_MESSAGE;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pongMessagePublisher.sendPongMessage(brokerMessage);
		}
	}
}
