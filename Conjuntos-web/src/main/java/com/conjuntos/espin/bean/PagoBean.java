/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Cuenta;
import com.conjuntos.espin.model.Pago;
import com.conjuntos.espin.service.CuentaService;
import com.conjuntos.espin.util.FacesUtil;
import com.conjuntos.espin.util.StateBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "pagoBean")
@ViewScoped
public class PagoBean extends StateBean implements Serializable {

    private Cuenta cuenta;
    private Pago nuevo;
    private Pago selected;
    private List<Pago> pagos;

    @Inject
    private CuentaService cuentaService;

    private GregorianCalendar calendario = new GregorianCalendar();

    @PostConstruct
    public void init() {
        this.nuevo = new Pago();
        this.pagos = new ArrayList<>();
        this.save();
    }

    public void initData(Cuenta cuenta) {
        this.save();
        this.nuevo = new Pago();
        this.cuenta = this.cuentaService.findByCedula(cuenta);
        if (this.cuenta.getCedula() == null) {
            this.cuenta = new Cuenta();
            this.pagos = new ArrayList<>();
        } else {
            this.pagos = this.cuenta.getPagos();
            if (this.pagos == null) {
                this.pagos = new ArrayList<>();
            }
        }
    }

    public void enviarCuenta(Cuenta cuenta) {
        this.nuevo = new Pago();
        this.cuenta = this.cuentaService.pagosPorCorbrar(cuenta);
        this.pagos = this.cuenta.getPagos();
        if (this.pagos == null) {
            this.pagos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        this.save();
        if (this.nuevo != null) {
            Integer index = this.pagos.size();
            this.nuevo.setIndex(index);
            this.nuevo.setCreationDate(calendario.getTime());
            this.nuevo.setLastChange(calendario.getTime());
            this.nuevo.setEstado(Boolean.FALSE);
            this.nuevo.setTipo("PAGO");
            this.pagos.add(this.nuevo);
            this.cuenta.setPagos(this.pagos);
            this.cuentaService.insert(this.cuenta);
            FacesUtil.addMessageInfo("El se ha registrado, Porfavor espera la confimacion de la admisntracion.");
            this.initData(this.cuenta);
        } else {
            FacesUtil.addMessageError(null, "No se ha registrado el pago.");
            this.initData(this.cuenta);
        }
    }

    public void addMulta(ActionEvent evt) {
        this.save();
        if (this.nuevo != null) {
            if (this.nuevo.getForma() != null && !this.nuevo.getForma().equals("")) {
                Integer index = this.pagos.size();
                this.nuevo.setIndex(index);
                this.nuevo.setCreationDate(calendario.getTime());
                this.nuevo.setLastChange(calendario.getTime());
                this.nuevo.setEstado(Boolean.FALSE);
                this.nuevo.setTipo("MULTA");
                this.pagos.add(this.nuevo);
                this.cuenta.setPagos(this.pagos);
                this.cuentaService.insert(this.cuenta);
                FacesUtil.addMessageInfo("El se ha registrado, Porfavor espera la confimacion de la admisntracion.");
                this.initData(this.cuenta);
            }else{
                FacesUtil.addMessageWarn(null, "El usuario aun no a revisado la multa");
            }
        } else {
            FacesUtil.addMessageError(null, "No se ha registrado el pago.");
            this.initData(this.cuenta);
        }
    }

    public void edit(ActionEvent evt) {
        if (this.nuevo.getIndex() != null) {
            this.nuevo.setLastChange(calendario.getTime());
            Boolean exito = this.cuentaService.updatePago(this.cuenta, this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modificado el pago");
                this.initData(this.cuenta);
            }
        }
    }

    public void confirmPago(ActionEvent evt) {
        if (this.nuevo.getIndex() != null) {
            this.nuevo.setLastChange(calendario.getTime());
            this.nuevo.setEstado(Boolean.TRUE);
            Boolean exito = this.cuentaService.updatePago(this.cuenta, this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modificado el pago");
                this.enviarCuenta(this.cuenta);
            }
        }
    }

    public void remove(ActionEvent evt) {
        if (this.nuevo.getIndex() != null) {
            this.pagos.remove((int) this.nuevo.getIndex());
            this.cuenta.setPagos(this.pagos);
            Boolean exito = this.cuentaService.deletePagos(this.cuenta);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado el pago");
                this.initData(this.cuenta);
            }
        }
    }

    public void filterByMes(String mes) {
        this.cuenta = this.cuentaService.filterByPagoMes(this.cuenta, mes);
        if (this.cuenta == null) {
            this.cuenta = new Cuenta();
            this.pagos = new ArrayList<>();
        }
        this.pagos = this.cuenta.getPagos();
    }

    public void filterByTipo(String tipo) {
        this.cuenta = this.cuentaService.filterByTipo(this.cuenta, tipo);
        if (this.cuenta == null) {
            this.cuenta = new Cuenta();
            this.pagos = new ArrayList<>();
        }
        this.pagos = this.cuenta.getPagos();
    }

    public void filterByEstado(Boolean estado) {
        this.cuenta = this.cuentaService.filterByEstado(this.cuenta, estado);
        if (this.cuenta == null) {
            this.cuenta = new Cuenta();
            this.pagos = new ArrayList<>();
        }
        this.pagos = this.cuenta.getPagos();
    }

    public void filterBetweenDate(Date start, Date finish) {
        this.cuenta = this.cuentaService.filterBetweenDate(cuenta, start, finish);
        if (this.cuenta == null) {
            this.cuenta = new Cuenta();
            this.pagos = new ArrayList<>();
        }
        this.pagos = this.cuenta.getPagos();
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Pago) event.getObject();
        this.nuevo = this.selected;
        this.modify();
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Pago getNuevo() {
        return nuevo;
    }

    public void setNuevo(Pago nuevo) {
        this.nuevo = nuevo;
    }

    public Pago getSelected() {
        return selected;
    }

    public void setSelected(Pago selected) {
        this.selected = selected;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

}
