package studyCommerceSimulator.email_notification_consumer.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import studyCommerceSimulator.email_notification_consumer.event.OrderCreatedEvent;

import java.util.Random;

@Service
public class EmailService {

    private final Random random = new Random();

    /*
        Sempre colocar o circuitBreaker antes do Retry - Pois O Resilience4j processa as anotações "de fora pra dentro"
        O @CircuitBreaker fica por fora, decidindo se vale a pena tentar de novo;
        Já o @Retry fica por dentro, controlando quantas vezes tentar dentro daquela janela.
    */
    @CircuitBreaker(name = "emailService", fallbackMethod = "sendEmailFallback")
    @Retry(name = "emailService", fallbackMethod = "sendEmailFallback")
    public void sendEmail(OrderCreatedEvent event){

        // Simula 40% de chance do "provedor de email" falhar
        if(random.nextInt(10) < 4){
            throw new RuntimeException("Provedor de email indisponível no momento");
        }

        System.out.println("Email enviado para o cliente " + event.customerId()
                + " sobre o pedido #" + event.orderId()
                + " no valor de R$ " + event.totalAmount());
    }

    public void sendEmailFallback(OrderCreatedEvent event, Exception ex) {
        System.out.println("Falha definitiva ao enviar email para o pedido #"
                + event.orderId() + " — motivo: " + ex.getMessage());
    }
}
