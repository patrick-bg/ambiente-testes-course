package io.github.patrickbg.locadora.model;

import io.github.patrickbg.locadora.exceptions.ReservaInvalidaException;

public class Reserva {

    private Carro carro;
    private Cliente cliente;
    private int dias;

    public Reserva(Carro carro, Cliente cliente, int dias) {
        if (dias <1){
            throw new ReservaInvalidaException("A reserva não pode ser menor que 1.");
        }
        this.carro = carro;
        this.cliente = cliente;
        this.dias = dias;
    }

    public double calcularTotal(){
        return this.carro.calcularValorAluguel(this.dias);
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
