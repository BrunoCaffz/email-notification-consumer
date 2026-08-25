package studyCommerceSimulator.email_notification_consumer.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        BigDecimal totalAmount,
        String paymentMethod
) {
}
