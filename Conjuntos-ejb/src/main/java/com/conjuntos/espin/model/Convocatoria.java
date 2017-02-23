/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.model;

import com.mongo.persistance.BaseEntity;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value="Convocatoria", noClassnameStored = true)
@Indexes({@Index(fields = @Field("codConvocatoria"))})
public class Convocatoria extends BaseEntity{

    private Integer codConvocatoria;
    private Date fechaConvocatoria;
    private String motivo;
    private String lugar;
    private String requisitos;
    @Reference
    private List<Persona> personas;

    public Convocatoria() {
    }

    public Integer getCodConvocatoria() {
        return codConvocatoria;
    }

    public void setCodConvocatoria(Integer codConvocatoria) {
        this.codConvocatoria = codConvocatoria;
    }

    public Date getFechaConvocatoria() {
        return fechaConvocatoria;
    }

    public void setFechaConvocatoria(Date fechaConvocatoria) {
        this.fechaConvocatoria = fechaConvocatoria;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }
    
    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.codConvocatoria);
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
        final Convocatoria other = (Convocatoria) obj;
        if (!Objects.equals(this.codConvocatoria, other.codConvocatoria)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Convocatoria{" + "codConvocatoria=" + codConvocatoria + ", fechaConvocatoria=" + fechaConvocatoria + ", motivo=" + motivo + ", lugar=" + lugar + ", requisitos=" + requisitos + ", personas=" + personas + '}';
    }
    
}

