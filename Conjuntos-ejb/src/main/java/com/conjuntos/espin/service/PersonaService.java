/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.mail.MailTool;
import com.conjuntos.espin.model.Convocatoria;
import com.conjuntos.espin.model.Persona;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.lang3.RandomStringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class PersonaService implements Serializable {

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public void insert(Persona persona) {
        Persona axu = this.findByCodPersona(persona);
        if (axu.getId() == null) {
            persona.setCodPersona(new Integer(RandomStringUtils.randomNumeric(5)));
            this.ds.save(persona);
        } else {
            this.ds.save(persona);
        }
    }

    public void sendConvocatoria(Convocatoria evento) {
        List<Persona> personas = this.obtenerLista();
        if (personas != null && !personas.isEmpty()) {
            for (int i = 0; i < personas.size(); i++) {
                SimpleDateFormat dateformat = new SimpleDateFormat();
                if (personas.get(i).getEmail() != null) {
                    MailTool mails = new MailTool();
                    mails.sendEmail(personas.get(i).getEmail(),
                            personas.get(i).getNombres() + " " + personas.get(i).getApellidos(),
                            evento.getLugar(), evento.getMotivo(), "", "",
                            dateformat.format(evento.getFechaConvocatoria()));
                }
            }
        }
    }

    public List<Persona> obtenerLista() {
        List<Persona> personas = this.ds.find(Persona.class).asList();
        return personas;
    }

    public List<Persona> obtenerListaArrendatarios() {
        List<Persona> find = new ArrayList<>();
        Query<Persona> result = this.ds.find(Persona.class).
                field("arrendatario").equal(Boolean.TRUE);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    public Persona findByCodPersona(Persona persona) {
        Persona find = new Persona();
        Query<Persona> result = this.ds.find(Persona.class).
                field("codPersona").equal(persona.getCodPersona());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Persona findByUsuario(Persona persona) {
        Persona find = new Persona();
        Query<Persona> query = this.ds.createQuery(Persona.class).
                field("usuario").equal(persona.getUsuario());
        if (query.asList() != null && !query.asList().isEmpty()) {
            find = query.asList().get(0);
        }
        return find;
    }

    public void delete(Persona persona) {
        this.ds.delete(persona);
    }

    public Boolean update(Persona persona) {
        Query<Persona> query = this.ds.createQuery(Persona.class);
        query.and(
                query.criteria("codPersona").equal(persona.getCodPersona())
        );
        UpdateOperations<Persona> update = this.ds.createUpdateOperations(Persona.class).
                set("nombres", persona.getNombres()).
                set("apellidos", persona.getApellidos()).
                set("cedula", persona.getCedula()).
                set("movil", persona.getMovil()).
                set("email", persona.getEmail()).
                set("propietario", persona.getPropietario()).
                set("arrendatario", persona.getArrendatario());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

}
