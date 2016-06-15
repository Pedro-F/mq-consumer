package artifactid;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@EnableJms
/**
 * Clase que produce una entrada en la cola JBoss EAP AMQ
 * @author pedro.alonso.garcia
 *
 */


public class consumer implements ExceptionListener {
	//Variables Globales
	Connection conn = null;
	String consola = "Hello World! by PA --- <BR> MicroservicioA";
	
	@JmsListener(destination = "TEST.HELLOW")
	public void receiveQueue(String text) {
		consola += "<BR>==> RECIBIENDO: " + text;
		System.out.println(text);
	}
	
	@RequestMapping("/")
	String home() {
		
		try {
			if (conn == null){
				conn = ConsumerConnection.getConnection();
				consola += "<BR>==> CONEXION ESTABLECIDA: " + conn.toString();
			}
			else{
				getMessage2Q();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return consola;
	}

	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(consumer.class, args);
	        
	    }
	 
//	    public static void thread(Runnable runnable, boolean daemon) {
//	        Thread brokerThread = new Thread(runnable);
//	        brokerThread.setDaemon(daemon);
//	        brokerThread.start();
//	    }
	 
	    private void getMessage2Q() {
	 
	   
	            try {
	 
	            	conn.setExceptionListener(this);
	 
	                // Create a Session
	                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	 
	                // Point to destination (Topic or Queue)
	                Destination destination = session.createQueue("TEST.HELLOW");
	                
	                // Create a MessageConsumer from the Session to the Topic or Queue
	                MessageConsumer consumer = session.createConsumer(destination);
	 
	                // Wait for a message
	                Message message = consumer.receive(1000);
	 
	                if (message instanceof TextMessage) {
	                    TextMessage textMessage = (TextMessage) message;
	                    String text = textMessage.getText();
	                    System.out.println("Received: " + text);
	                } else {
	                    System.out.println("Received: " + message);
	                }
	 
//	               
	            } catch (Exception e) {
	                System.out.println("Caught: " + e);
	                e.printStackTrace();
	            }
	        }
	 
	        public synchronized void onException(JMSException ex) {
	            System.out.println("JMS Exception occured.  Shutting down client.");
	        }
	}
	