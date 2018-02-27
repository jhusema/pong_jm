package com.jmarino.m.p.pingpong.pong.broker;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class PongMessagePublisher {
	private BrokerConnMngmt brokerConnMngmt;

	public PongMessagePublisher(BrokerConnMngmt brokerMessage) {
		this.brokerConnMngmt = brokerMessage;
	}

	public void sendPingMessage(BrokerMessage message) {
		message.message = ConstansProperties.PONG_MESSAGE;
		ObjectMapper mapper = new ObjectMapper();
		String jsonMessage = "";
		try {
			jsonMessage = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		try {
			Channel channel = this.brokerConnMngmt.getChannel();
			channel.queueDeclare(ConstansProperties.PONG_QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", ConstansProperties.PONG_QUEUE_NAME, null, jsonMessage.getBytes());
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
