/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author luis
 */
@Named(value = "calendarioBean")
@ViewScoped
public class CalendarioBean implements Serializable {

    private GregorianCalendar calenadario;

    @PostConstruct
    public void init() {
        this.calenadario = new GregorianCalendar();
    }

    public GregorianCalendar getCalenadario() {
        return calenadario;
    }

    public void setCalenadario(GregorianCalendar calenadario) {
        this.calenadario = calenadario;
    }

}
