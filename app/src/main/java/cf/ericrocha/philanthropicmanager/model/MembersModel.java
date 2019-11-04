package cf.ericrocha.philanthropicmanager.model;

import java.util.Date;

public class MembersModel {
    public String nome;
    public String cid;
    public Date dt_nasc;
    public String endereco;
    public String cep;
    public String telefone;
    public Integer nivel;
    /*public Integer cor;*/
    public Integer id;



    public MembersModel(){
    }

    public MembersModel(Integer id , String nome, String cid, Date dt_nasc, String endereco, String cep,String telefone, Integer nivel ) {
        this.id = id;
        this.nome = nome;
        this.cid = cid;
        this.dt_nasc = dt_nasc;
        this.endereco = endereco;
        this.cep = cep;
        this.telefone = telefone;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(Date dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /*public Integer getId() {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/
}
