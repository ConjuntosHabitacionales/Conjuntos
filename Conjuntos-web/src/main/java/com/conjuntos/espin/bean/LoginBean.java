/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Usuario;
import com.conjuntos.espin.service.UsuarioService;
import com.conjuntos.espin.util.FacesUtil;
import javafx.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean {

    private Usuario usuario;

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private CredencialBean session;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
    }

    public void logIn(ActionEvent evt) {
        Usuario mlogIn = this.usuarioService.findLogin(this.usuario);
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Boolean LoggedIn = Boolean.FALSE;
        if (mlogIn != null) {
            this.session.sessionStart(mlogIn);
            url = url + "/faces/views/menu.xhtml";
            LoggedIn = Boolean.TRUE;
        } else {
            FacesUtil.addMessageError(null, "Username y/o password incorrectos.");
        }
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
