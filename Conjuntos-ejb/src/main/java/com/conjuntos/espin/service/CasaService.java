/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Casa;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
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
@LocalBean
@Stateless
public class CasaService implements Serializable {

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public void insert(Casa casa) {
        Casa axu = this.findByCodCasa(casa);
        if (axu.getId() == null) {
            casa.setCodCasa(new Integer(RandomStringUtils.randomNumeric(5)));
            this.ds.save(casa);
        }
    }

    public List<Casa> obtenerLista() {
        List<Casa> casas = this.ds.find(Casa.class).asList();
        return casas;
    }

    public Casa findByCodCasa(Casa casa) {
        Casa find = new Casa();
        Query<Casa> result = this.ds.find(Casa.class).
                field("codCasa").equal(casa.getCodCasa());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Casa findByNumero(Casa casa) {
        Casa find = new Casa();
        Query<Casa> result = this.ds.find(Casa.class).
                field("numero").equal(casa.getNumero());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Casa casa) {
        this.ds.delete(casa);
    }

    public Boolean update(Casa casa) {
        Query<Casa> query = this.ds.createQuery(Casa.class);
        query.and(
                query.criteria("codCasa").equal(casa.getCodCasa())
        );
        UpdateOperations<Casa> update = this.ds.createUpdateOperations(Casa.class);
        update.set("codCasa", casa.getCodCasa()).
                set("numero", casa.getNumero()).
                set("bloque", casa.getBloque()).
                set("codeLuz", casa.getCodeLuz()).
                set("codeAgua", casa.getCodeAgua());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}
