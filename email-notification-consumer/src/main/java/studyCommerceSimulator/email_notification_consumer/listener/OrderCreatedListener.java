package studyCommerceSimulator.email_notification_consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import studyCommerceSimulator.email_notification_consumer.event.OrderCreatedEvent;

@Component
public class OrderCreatedListener {

    /*
    * serve para transformar um metodo comum em um consumidor automatico de mensagens
    * topics = "pedido-criado" — diz exatamente qual "canal" esse metodo está escutando
    * groupId = "email-notification-group" — de qual grupo esse consumer faz parte (bate com o do application.properties)
    * */
    @KafkaListener(topics = "pedido-criado", groupId = "email-notification-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Email enviado para o cliente " + event.customerId()
                + " sobre o pedido #" + event.orderId()
                + " no valor de R$ " + event.totalAmount());
    }

}
