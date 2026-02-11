package components.payment;

public class PaymentComponent {
    private final PaymentService paymentService;

    public PaymentComponent(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentService paymentService() {
        return paymentService;
    }
}
