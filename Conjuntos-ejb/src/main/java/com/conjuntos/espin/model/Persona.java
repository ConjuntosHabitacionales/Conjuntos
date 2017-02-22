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
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Persona", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codPersona"))})
public class Persona extends BaseEntity {

    private Integer codPersona;
    private String nombres;
    private String apellidos;
    private String cedula;
    private String movil;
    private String email;
    private Integer habitantes;
    private Boolean propietario;
    private Boolean arrendatario;

    @Reference
    private Usuario usuario;
    @Reference
    private Casa casa;
    @Reference
    private Cuenta cuenta;

    public Persona() {

    }

    public Persona(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public Integer getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(Integer habitantes) {
        this.habitantes = habitantes;
    }

    public Boolean getPropietario() {
        return propietario;
    }

    public void setPropietario(Boolean propietario) {
        this.propietario = propietario;
    }

    public Boolean getArrendatario() {
        return arrendatario;
    }

    public void setArrendatario(Boolean arrendatario) {
        this.arrendatario = arrendatario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.codPersona);
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.codPersona, other.codPersona)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persona{" + "codPersona=" + codPersona + ", nombres=" + nombres + ", apellidos=" + apellidos + ", cedula=" + cedula + ", movil=" + movil + ", email=" + email + ", propietario=" + propietario + ", arrendataro=" + arrendatario + ", usuario=" + usuario + ", casa=" + casa + ", cuenta=" + cuenta + '}';
    }

}
