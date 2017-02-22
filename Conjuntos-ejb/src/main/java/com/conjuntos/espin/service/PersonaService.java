/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Persona;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.lang3.RandomStringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
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

    private final MongoPersistence conn = new MongoPersistence();
    private final Datastore ds = conn.context();

    public String insert(Persona persona) {
        persona.setCodPersona(new Integer(RandomStringUtils.randomNumeric(5)));
        Persona axu = this.findByCodPersona(persona);
        Key<Persona> key = null;
        if (axu.getId() == null) {
            key = this.ds.save(persona);
        }
        return key.toString();
    }

    public List<Persona> obtenerLista() {
        List<Persona> personas = this.ds.find(Persona.class).asList();
        return personas;
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
