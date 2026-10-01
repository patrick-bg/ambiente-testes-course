package io.github.patrickbg.locadora.model;

public class Carro {

    private String modelo;
    private Double valorDiario;

    public Carro(String modelo, Double valorDiario) {
        this.modelo = modelo;
        this.valorDiario = valorDiario;
    }

    public double calcularValorAluguel(int dias){
        double desconto = 0.0;
        if(dias >= 5){
            desconto = 50.0;
        }
        return (valorDiario * dias) - desconto;
    }

    public Double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(Double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
