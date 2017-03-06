/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Casa;
import com.conjuntos.espin.model.Cuenta;
import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.model.Usuario;
import com.conjuntos.espin.service.CasaService;
import com.conjuntos.espin.service.CuentaService;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.service.UsuarioService;
import com.conjuntos.espin.util.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
public class LoginBean implements Serializable {

    private Usuario usuario = new Usuario();

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private PersonaService personaService;
    @Inject
    private CasaService casaService;
    @Inject
    private CuentaService cuentaService;
    @Inject
    private CredencialBean session;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.createAdmin();
    }

    public void logIn(ActionEvent evt) {
        Usuario mlogIn = this.usuarioService.findLogin(this.usuario);
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Boolean LoggedIn = Boolean.FALSE;
        if (mlogIn != null && mlogIn.getId() != null) {
            this.session.sessionStart(mlogIn);
            url = url + "/faces/views/menu.xhtml";
            LoggedIn = Boolean.TRUE;
            this.init();
        } else {
            FacesUtil.addMessageError(null, "Username y/o password incorrectos.");
            this.init();
        }
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    private void createAdmin() {
        Usuario admin = new Usuario();
        admin.setEstado(Boolean.TRUE);
        admin.setUsername("admin.hogar.2017");
        admin.setPassword("admin.hogar.2017");
        Persona pAdmin = new Persona();
        pAdmin.setApellidos("admin");
        pAdmin.setNombres("admin");
        pAdmin.setCedula("000000000");
        Cuenta cAdmin = new Cuenta();
        cAdmin.setCedula("000000000");
        Casa hAdmin = new Casa();
        hAdmin.setNumero(0);
        Usuario user = this.usuarioService.findByUsername(admin);
        if (user.getCodUsuario() == null) {
            this.usuarioService.insertAdmin(admin);
            pAdmin.setUsuario(admin);
            pAdmin.setCuenta(cAdmin);
            pAdmin.setCasa(hAdmin);
            this.cuentaService.insert(cAdmin);
            this.casaService.insert(hAdmin);
            this.personaService.insert(pAdmin);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
