/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.menu;

/**
 *
 * @author luis
 */
public class AccsessMenu {

    private Boolean anministracion;
    private Boolean convocatoria;
    private Boolean cuenta;
    private Boolean reporte;
    private Boolean usuario;

    public AccsessMenu() {
    }

    public void LoandUser() {
        this.usuario = Boolean.FALSE;
        this.convocatoria = Boolean.FALSE;
        this.cuenta = Boolean.FALSE;
        this.reporte = Boolean.TRUE;
        this.anministracion = Boolean.TRUE;
    }

    public void LoandAdmin() {
        this.usuario = Boolean.FALSE;
        this.convocatoria = Boolean.FALSE;
        this.cuenta = Boolean.FALSE;
        this.reporte = Boolean.FALSE;
        this.anministracion = Boolean.FALSE;
    }

    public Boolean getAnministracion() {
        return anministracion;
    }

    public Boolean getConvocatoria() {
        return convocatoria;
    }

    public Boolean getCuenta() {
        return cuenta;
    }

    public Boolean getReporte() {
        return reporte;
    }

    public Boolean getUsuario() {
        return usuario;
    }

}
