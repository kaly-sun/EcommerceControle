package br.unitins.tp1.service;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentGatewayFake {

    private final Random random = new Random();

    public boolean processarPagamento(String metodo, Long idPagamento) {

        switch (metodo.toUpperCase()) {

            case "PIX":
                return true;

            case "BOLETO":
                return random.nextBoolean();

            case "CARTAO":
                return random.nextInt(10) < 8;

            default:
                return false;
        }
    }
}
