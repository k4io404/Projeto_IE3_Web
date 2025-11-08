package com.controleDeAcesso.view;


public class VeiculoView{

    private String pesNome;
    private String placa;
    private int pesId;
    private String cor;
    private String modelo;
    private String tipoResponsavel;

    public String getPesNome() {
        return pesNome;
    }

    public void setPesNome(String pesNome) {
        this.pesNome = pesNome;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getPesId() {
        return pesId;
    }

    public void setPesId(int pesId) {
        this.pesId = pesId;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoResponsavel() {
        return tipoResponsavel;
    }

    public void setTipoResponsavel(String tipoResponsavel) {
        this.tipoResponsavel = tipoResponsavel;
    }

    @Override
    public String toString() {
        return "VeiculoView{" +
                "pesNome='" + pesNome + '\'' +
                ", placa='" + placa + '\'' +
                ", pesId=" + pesId +
                ", cor='" + cor + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipoResponsavel='" + tipoResponsavel + '\'' +
                '}';
    }
}
