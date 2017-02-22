/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Cuenta;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author luis
 */
@LocalBean
@Stateless
public class CuentaService implements Serializable{
    
    private final MongoPersistence conn = new MongoPersistence();
    private final Datastore ds = conn.context();

    public String insert(Cuenta cuenta) {
        Cuenta axu = this.findByCedula(cuenta);
        Key<Cuenta> key = null;
        if (axu.getId() == null) {
            key = this.ds.save(cuenta);
        }
        return key.toString();
    }

    public List<Cuenta> obtenerLista() {
        List<Cuenta> cuentas = this.ds.find(Cuenta.class).asList();
        return cuentas;
    }

    public Cuenta findByCedula(Cuenta cuenta) {
        Cuenta find = new Cuenta();
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Cuenta cuenta) {
        this.ds.delete(cuenta);
    }

    public Boolean update(Cuenta cuenta) {
        Query<Cuenta> query = this.ds.createQuery(Cuenta.class);
        query.and(
                query.criteria("cedula").equal(cuenta.getCedula())
        );
        UpdateOperations<Cuenta> update = this.ds.createUpdateOperations(Cuenta.class);
        update.set("cedula", cuenta.getCedula()).
                set("total", cuenta.getTotal()).
                set("saldo", cuenta.getSaldo());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}
