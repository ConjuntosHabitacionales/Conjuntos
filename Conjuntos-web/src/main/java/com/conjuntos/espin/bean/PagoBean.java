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

    private Pago nuevo;
    private Pago selected;
    private List<Pago> pagos;

    @Inject
    private CuentaService cuentaService;

    @PostConstruct
    public void init() {
        this.nuevo = new Pago();
        this.pagos = new ArrayList<>();
        this.save();
    }

    public void enviarCuenta(Cuenta cuenta) {
        this.pagos = cuenta.getPagos();
        if (this.pagos == null) {
            this.pagos = new ArrayList<>();
        }else{
            Integer index = this.pagos.size();
            this.nuevo.setIndex(index);
            this.pagos.add(this.nuevo);
            String cuentaId = this.cuentaService.insert(cuenta);
            if(!cuentaId.equals("") && cuentaId != null){
                FacesUtil.addMessageInfo("Se ha registrado su pago, \n Porfavor espera la confimacion de la admisntracion.");
            }
        }
        
    }

    public void add(ActionEvent evt) {
        this.save();

    }

    public void edit(ActionEvent evt) {
    
    }

    public void remove(ActionEvent evt) {

    }

    public void onRowSelect(SelectEvent event) {
        event.getObject();
        this.modify();
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
