package com.jmarino.m.p.pingpong.pong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmarino.m.p.pingpong.pong.broker.Client;

@RestController
public class PongService {
	@Autowired
	private Client client;

	@RequestMapping
	public PongStatistics pongStatistics() {
		PongStatistics pongStatistics = new PongStatistics();
		pongStatistics.totalPingMessageRead = 5;
		pongStatistics.totalPingMessageReplied = 4;
		if (this.client != null) {
			pongStatistics = this.client.getPongStatistics();
		}
		return pongStatistics;
	}
}
