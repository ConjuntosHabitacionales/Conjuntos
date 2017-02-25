/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Cuenta;
import com.conjuntos.espin.model.Pago;
import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.util.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "cuentaBean")
@ViewScoped
public class CuentaBean implements Serializable {

    private Persona persona;
    private Cuenta cuenta;
    private Boolean pagos;
    private Boolean multas;
    private Boolean confirmados;
    private Date fechaIn;
    private Date fechaOut;
    private String mes;
    private List<String> meses;

    @Inject
    private PersonaService personaService;
    @Inject
    private PagoBean pago;

    @PostConstruct
    public void init() {
        this.persona = this.personaService.findByCodPersona(new Persona(
                SessionUtil.sessionVarNumeric("codPersona")));
        if (this.persona.getId() == null) {
            this.persona = new Persona();
            this.cuenta = new Cuenta();
        } else {
            this.cuenta = this.persona.getCuenta();
            if (this.cuenta.getPagos() != null) {
                this.pago.initData(this.cuenta);
            } else {
                this.cuenta.setPagos(new ArrayList<Pago>());
                this.pago.initData(this.cuenta);
            }

        }
        this.meses = new ArrayList<>();
        this.LoadMeses();
        this.pagos = Boolean.FALSE;
        this.multas = Boolean.FALSE;
        this.confirmados = Boolean.FALSE;
    }

    public void enviarPersona(Persona persona) {
        this.persona = persona;
        if (this.persona.getId() == null) {
            this.persona = new Persona();
            this.cuenta = new Cuenta();
        } else {
            this.cuenta = this.persona.getCuenta();
            this.pago.enviarCuenta(this.cuenta);
        }
        this.meses = new ArrayList<>();
        this.LoadMeses();
        this.pagos = Boolean.FALSE;
        this.multas = Boolean.FALSE;
        this.confirmados = Boolean.FALSE;
    }

    private void LoadMeses() {
        String[] strMonths = new String[]{
            "Enero",
            "Febebro",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"};
        for (int i = 0; i < strMonths.length; i++) {
            this.meses.add(strMonths[i]);
        }
    }

    public void filterByMes() {
        this.pago.filterByMes(this.mes);
    }

    public void filterByPago() {
        this.pago.filterByTipo("PAGO");
        this.multas = Boolean.FALSE;
    }

    public void filterByMulta() {
        this.pago.filterByTipo("MULTA");
        this.pagos = Boolean.FALSE;
    }

    public void filterByEstado() {
        this.pago.filterByEstado(this.confirmados);
    }

    public void filterBetweenDate(ActionEvent evt) {
        if (this.fechaIn != null && this.fechaOut != null) {
            this.pago.filterBetweenDate(fechaIn, fechaOut);
        }
    }

    public Boolean getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(Boolean confirmados) {
        this.confirmados = confirmados;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Boolean getPagos() {
        return pagos;
    }

    public void setPagos(Boolean pagos) {
        this.pagos = pagos;
    }

    public Boolean getMultas() {
        return multas;
    }

    public void setMultas(Boolean multas) {
        this.multas = multas;
    }

    public Date getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Date getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(Date fechaOut) {
        this.fechaOut = fechaOut;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<String> getMeses() {
        return meses;
    }

    public void setMeses(List<String> meses) {
        this.meses = meses;
    }

    public PagoBean getPago() {
        return pago;
    }

    public void setPago(PagoBean pago) {
        this.pago = pago;
    }

}
