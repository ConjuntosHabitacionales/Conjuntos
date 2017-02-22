/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.model;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 *
 * @author luis
 */
@Entity(value = "Casa", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codCasa")),
    @Index(fields = @Field("numero"))})
public class Casa extends BaseEntity {

    private Integer codCasa;
    private Integer numero;
    private String bloque;
    private String codeLuz;
    private String codeAgua;

    public Casa() {
    }

    public Integer getCodCasa() {
        return codCasa;
    }

    public void setCodCasa(Integer codCasa) {
        this.codCasa = codCasa;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer Numero) {
        this.numero = Numero;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getCodeLuz() {
        return codeLuz;
    }

    public void setCodeLuz(String codeLuz) {
        this.codeLuz = codeLuz;
    }

    public String getCodeAgua() {
        return codeAgua;
    }

    public void setCodeAgua(String codeAgua) {
        this.codeAgua = codeAgua;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codCasa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casa other = (Casa) obj;
        if (!Objects.equals(this.codCasa, other.codCasa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Casa{" + "codCasa=" + codCasa + ", Numero=" + numero + ", bloque=" + bloque + ", codeLuz=" + codeLuz + ", codeAgua=" + codeAgua + '}';
    }

}
