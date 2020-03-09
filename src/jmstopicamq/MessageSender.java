/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmstopicamq;

/**
 *
 * @author JGUTIERRGARC
 */
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    //private static String subject = "JOGG_TOPIC"; // Topic Name. You can create any/many topic names as per your requirement. 

    public void produceMessages(String subject) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(subject);

            messageProducer = session.createProducer(destination);
            textMessage = session.createTextMessage();

            textMessage.setText("Sending news about: "+ subject);
            System.out.println("Sending the following message: " + textMessage.getText());
            messageProducer.send(textMessage);


            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num;
        String arre[] = new String[5];
        arre[0]="Telecom";
        arre[1]="Educación";
        arre[2]="Banks";
        arre[3]="Food";
        arre[4]="Transp";
        num = (int) ((Math.random()) * 4); 
        Thread.sleep(1000);
        for(int i =0; i < 10 ; i++){
            num = (int) ((Math.random()) * 5);
            if (num != 5){
                new MessageSender().produceMessages(arre[num]);
                Thread.sleep(1000);
            }
            else{
                new MessageSender().produceMessages(arre[num-1]);
                Thread.sleep(1000);
            }
        }
    }

}
