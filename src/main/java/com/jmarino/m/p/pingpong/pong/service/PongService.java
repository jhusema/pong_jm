package com.jmarino.m.p.pingpong.pong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmarino.m.p.pingpong.pong.broker.BrokerConnMngmt;

@RestController
public class PongService {
	@Autowired
	private BrokerConnMngmt brokerConnMngmt;

	@RequestMapping(path="pongStatistics")
	public PongStatistics pongStatistics() {
		PongStatistics pongStatistics = this.brokerConnMngmt.getPongStatistics();
		if (pongStatistics == null) {
			pongStatistics = new PongStatistics();
		}
		return pongStatistics;
	}
}
