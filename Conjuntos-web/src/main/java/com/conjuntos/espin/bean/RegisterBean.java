/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Cuenta;
import com.conjuntos.espin.model.Pago;
import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.model.Usuario;
import com.conjuntos.espin.service.CuentaService;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.service.UsuarioService;
import com.conjuntos.espin.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    private Usuario usuario;
    private Persona persona;
    private String password;

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private PersonaService personaService;
    @Inject
    private CuentaService cuentaService;
    @Inject
    private CredencialBean session;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.persona = new Persona();
        this.password = "";
    }

    public void register(ActionEvent evt) {
        if (this.password.equals(this.usuario.getPassword())) {
            if (this.persona.getPropietario() != this.persona.getArrendatario()) {
                String usuarioId = this.usuarioService.insert(this.usuario);
                if (usuarioId != null && !usuarioId.equals("")) {
                    this.persona.setUsuario(this.usuario);
                    Cuenta cuenta = new Cuenta();
                    cuenta.setCedula(this.persona.getCedula());
                    cuenta.setSaldo(0.0);
                    cuenta.setTotal(0.0);
                    cuenta.setPagos(new ArrayList<Pago>());
                    String keyCuenta = this.cuentaService.insert(cuenta);
                    String personaId = this.personaService.insert(this.persona);
                    if (personaId != null && !personaId.equals("")) {
                        if (keyCuenta != null && !keyCuenta.equals("")) {
                            this.persona.setCuenta(cuenta);
                            personaId = this.personaService.insert(this.persona);
                        }
                        Persona usersession = this.persona;
                        this.session.sessionStart(usersession);
                        this.init();
                        FacesUtil.addMessageInfo("Se ha registrado con exito.");
                        RequestContext.getCurrentInstance().execute("PF('dlgLogin').show();");
                    }
                }
            } else {
                FacesUtil.addMessageError(null, "Seleccione Propietario o Individuo.");
            }
        } else {
            FacesUtil.addMessageError(null, "Password incorrecto.");
        }
    }

    public void loginStart(ActionEvent evt) {
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        url = url + "/faces/views/menu.xhtml";
        boolean LoggedIn = Boolean.TRUE;
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    public void bloquearArrendatario() {
        this.getPersona().setArrendatario(Boolean.FALSE);
    }

    public void bloquearPropietario() {
        this.getPersona().setPropietario(Boolean.FALSE);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
