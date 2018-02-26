package com.jmarino.m.p.pingpong.pong.broker;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class PingMessageConsumer extends DefaultConsumer {

	private Client client;

	public PingMessageConsumer(Channel channel, Client client) {
		super(channel);
		this.client = client;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		try {
			// Simulación de dos segundos de espera.
			Thread.sleep(2000);
			ObjectMapper mapper = new ObjectMapper();
			BrokerMessage brokerMessage = mapper.readValue(body, BrokerMessage.class);
			brokerMessage.message=ConstansProperties.PING_QUEUE_NAME;
			String jsonMessage = "";
			try {
				jsonMessage = mapper.writeValueAsString(brokerMessage);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			this.client.sendPongMessage(jsonMessage);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
