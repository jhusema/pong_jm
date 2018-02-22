package com.jmarino.m.p.pingpong.pong.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PongService {
	private PongStatistics pongStatistics = new PongStatistics();
	@RequestMapping
	public PongStatistics pongStatistics() {
		this.pongStatistics.totalPingMessageRead =5;
		this.pongStatistics.totalPingMessageReplied = 4;
		return this.pongStatistics;
	}
}
