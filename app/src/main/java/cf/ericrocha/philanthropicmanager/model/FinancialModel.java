package cf.ericrocha.philanthropicmanager.model;

import java.util.Date;

public class FinancialModel {
    public Float valor;
    public Date date_lanc;
    public String tipo_lanc;
    public Integer cor;
    public Integer id;



    public FinancialModel(){
    }

    public FinancialModel(Integer id , Float valor, Date date_lanc, String tipo_lanc, Integer cor ) {
        this.id = id;
        this.valor = valor;
        this.date_lanc = date_lanc;
        this.tipo_lanc = tipo_lanc;
        this.cor = cor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCor() {
        return cor;
    }

    public void setCor(Integer cor) {
        this.cor = cor;
    }

    public String getTipo_lanc() {
        return tipo_lanc;
    }

    public void setTipo_lanc(String desc) {
        this.tipo_lanc = desc;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(String extra) {
        this.valor = valor;
    }

    public Date getDate_lanc() {
        return date_lanc;
    }

    public void setDate_lanc(Date date) {
        this.date_lanc = date;
    }
}
