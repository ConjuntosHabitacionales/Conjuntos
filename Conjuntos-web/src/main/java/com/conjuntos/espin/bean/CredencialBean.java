/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.menu.AccsessMenu;
import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.model.Usuario;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.util.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "credencialBean")
@SessionScoped
public class CredencialBean implements Serializable {

    private Persona persona;
    private Boolean startSession;
    private AccsessMenu menu;

    @Inject
    private PersonaService personaService;

    public void sessionStart(Usuario usuario) {
        if (usuario.getId() != null) {
            this.persona = new Persona();
            this.persona.setUsuario(usuario);
            this.persona = this.personaService.findByUsuario(persona);
            if (this.persona.getId() != null) {
                this.LoadDataSession(this.persona);
                this.LoadMenu(this.persona.getUsuario().getTipo());
            }
        }
    }

    public void sessionStart(Persona persona) {
        if (persona != null) {
            this.persona = persona;
            this.LoadDataSession(this.persona);
            this.LoadMenu(this.persona.getUsuario().getTipo());
        }
    }

    private void LoadDataSession(Persona persona) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("codPersona", persona.getCodPersona());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("codUsuario", persona.getUsuario().getCodUsuario());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("tipo", persona.getUsuario().getTipo());
    }

    private void LoadMenu(String Tipo) {
        this.menu = new AccsessMenu();
        switch (Tipo) {
            case "user":
                this.menu.LoandUser();
                break;
            case "admin":
                this.menu.LoandAdmin();
                break;
            default:

        }
    }

    public void logout(ActionEvent event) {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/index.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute(SessionUtil.sessionVarAlfanumeric("codPersona"));
        session.invalidate();
        this.init();
        context.addCallbackParam("loggerOut", true);
        context.addCallbackParam("ruta", url);
    }

    private void init() {
        this.persona = new Persona();
        this.startSession = Boolean.FALSE;
        this.menu = new AccsessMenu();
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Boolean getStartSession() {
        return startSession;
    }

    public void setStartSession(Boolean startSession) {
        this.startSession = startSession;
    }

    public AccsessMenu getMenu() {
        return menu;
    }

    public void setMenu(AccsessMenu menu) {
        this.menu = menu;
    }

}
