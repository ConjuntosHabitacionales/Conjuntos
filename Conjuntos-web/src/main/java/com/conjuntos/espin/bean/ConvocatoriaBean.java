/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Convocatoria;
import com.conjuntos.espin.service.ConvocatoriaService;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.util.FacesUtil;
import com.conjuntos.espin.util.SessionUtil;
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
@Named(value = "convocatoriaBean")
@ViewScoped
public class ConvocatoriaBean extends StateBean implements Serializable{

    private Convocatoria nueva;
    private Convocatoria selected;
    private List<Convocatoria> convocatorias;
    private Boolean admin;
    
    @Inject
    private ConvocatoriaService convocatoriaService;
    @Inject
    private PersonaService personaService;
    
    @PostConstruct
    public void init(){
        this.save();
        this.admin = SessionUtil.sessionTypeUserProfile();
        this.nueva =  new Convocatoria();
        this.convocatorias = this.convocatoriaService.obtenerLista();
        if(this.convocatorias == null || this.convocatorias.size() == 0){
            this.convocatorias = new ArrayList<>();
        }
        if(this.admin == null){
            this.admin = Boolean.TRUE;
        }
    }
    
    public void add(ActionEvent evt){
        this.convocatoriaService.insert(this.nueva);
        this.personaService.sendConvocatoria(this.nueva);
        this.init();
        FacesUtil.addMessageInfo("Se ha enviado la convocatoria evento.");
    }
    
    public void edit(ActionEvent evt){
        this.convocatoriaService.update(this.nueva);
        this.personaService.sendConvocatoria(this.nueva);
        this.init();
        FacesUtil.addMessageInfo("Se ha actualizado la convocatoria evento.");
    }
    
    public void remove(ActionEvent evt){
        this.convocatoriaService.delete(this.nueva);
        this.init();
        FacesUtil.addMessageInfo("Se ha eliminado la convocatoria evento.");
    }
    
    public void onRowSelect(SelectEvent event) {
        this.selected = (Convocatoria) event.getObject();
        this.nueva =  this.selected;
        this.modify();
    }

    public Convocatoria getNueva() {
        return nueva;
    }

    public void setNueva(Convocatoria nueva) {
        this.nueva = nueva;
    }

    public Convocatoria getSelected() {
        return selected;
    }

    public void setSelected(Convocatoria selected) {
        this.selected = selected;
    }

    public List<Convocatoria> getConvocatorias() {
        return convocatorias;
    }

    public void setConvocatorias(List<Convocatoria> convocatorias) {
        this.convocatorias = convocatorias;
    }

    public ConvocatoriaService getConvocatoriaService() {
        return convocatoriaService;
    }

    public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
        this.convocatoriaService = convocatoriaService;
    }

    /**
     * @return the admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
}
