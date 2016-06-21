package artifactid;

import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnectionFactory;

  class ConsumerConnection /*implements Runnable*/{
	//public void run() {
    
	
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
            
        	// Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.30.78.89:61616");

            // Create a Connection
            connection = connectionFactory.createConnection("user","password");
            connection.start();
            
            
		}
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
		return connection;
    }

	
}
