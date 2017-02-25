/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.model;

import com.mongo.persistance.BaseEntity;
import java.util.List;
import java.util.Objects;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 *
 * @author luis
 */
@Entity(value = "Cuenta", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("cedula"))})
public class Cuenta extends BaseEntity {

    private String cedula;

    @Embedded
    private List<Pago> pagos;

    public Cuenta() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public Double obtenerSumaPagos(Boolean estado) {
        Double totalPagos = 0.0;
        if (this.pagos != null && !this.pagos.isEmpty()) {
            for (int i = 0; i < this.pagos.size(); i++) {
                if (this.pagos.get(i).getTipo().equals("PAGO")
                        && this.pagos.get(i).getEstado().equals(estado)) {
                    totalPagos = totalPagos + this.pagos.get(i).getValor();
                }
            }
        }
        return totalPagos;
    }

    public Double obtenerSumaMultas(Boolean estado) {
        Double totalMultas = 0.0;
        if (this.pagos != null && !this.pagos.isEmpty()) {
            for (int i = 0; i < this.pagos.size(); i++) {
                if (this.pagos.get(i).getTipo().equals("MULTA")
                        && this.pagos.get(i).getEstado().equals(estado)) {
                    totalMultas = totalMultas + this.pagos.get(i).getValor();
                }
            }
        }
        return totalMultas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.cedula);
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
        final Cuenta other = (Cuenta) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "cedula=" + cedula + ", pagos=" + pagos + '}';
    }

}
