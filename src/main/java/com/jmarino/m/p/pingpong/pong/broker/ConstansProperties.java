package com.jmarino.m.p.pingpong.pong.broker;

/**
 * 
 * @author Juan MariÃ±o
 *
 */
public class ConstansProperties {

	/**
	 * Nombre de la cola para almacenar mensajes ping en el broker.
	 */
	public static final String PING_QUEUE_NAME = "ping_messages_queue";
	/**
	 * Nombre de la cola para almacenar mensajes pong en el broker.
	 */
	public static final String PONG_QUEUE_NAME = "pong_messages_queue";
	/**
	 * Mensaje pong.
	 */
	public static final String PONG_MESSAGE = "PONG_MESSAGE";

}
