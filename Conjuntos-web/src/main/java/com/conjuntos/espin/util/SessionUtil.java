/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
public class SessionUtil {
    
    private static final Logger LOG = Logger.getLogger(FacesUtil.class.getName());
    
    public static Integer sessionVarNumeric(String var) {
        Integer code = 0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Object codeSession = session.getAttribute(var);
        if (codeSession != null) {
            code = new Integer(codeSession.toString());
            LOG.log(Level.INFO,"Session var :"+codeSession);
        }
        return code;
    }
    
    public static String sessionVarAlfanumeric(String var) {
        String code = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Object codeSession = session.getAttribute(var);
        if (codeSession != null) {
            code = codeSession.toString();
            LOG.log(Level.INFO,"Session var :"+codeSession);
        }
        return code;
    }
    
    public static Boolean sessionTypeUserProfile() {
        //false = admin
        // true = user
        String code = null;
        Boolean profile = Boolean.TRUE;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Object codeSession = session.getAttribute("tipo");
        if (codeSession != null) {
            code = codeSession.toString();
            if(code.equals("admin")){
                profile = Boolean.FALSE;
            }
            LOG.log(Level.INFO,"Session var :"+codeSession);
        }
        return profile;
    }
    
}
