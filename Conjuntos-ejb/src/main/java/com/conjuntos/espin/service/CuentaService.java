/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Cuenta;
import com.conjuntos.espin.model.Pago;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
public class CuentaService implements Serializable {

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public void insert(Cuenta cuenta) {
        Cuenta axu = this.findByCedula(cuenta);
        if (axu.getId() == null) {
            this.ds.save(cuenta);
        } else {
            this.ds.save(cuenta);
        }
    }

    public List<Cuenta> obtenerLista() {
        List<Cuenta> cuentas = this.ds.find(Cuenta.class).asList();
        return cuentas;
    }

    public Cuenta pagosPorCorbrar(Cuenta cuenta) {
        Cuenta find = null;
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            if (find != null && find.getPagos() != null && !find.getPagos().isEmpty()) {
                List<Pago> filter = new ArrayList<>();
                for (int i = 0; i < find.getPagos().size(); i++) {
                    if (find.getPagos().get(i).getEstado().equals(Boolean.FALSE)) {
                        filter.add(find.getPagos().get(i));
                    }
                }
                find.setPagos(filter);
            } else {
                find = cuenta;
            }
        }
        return find;
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

    public Cuenta filterByPagoMes(Cuenta cuenta, String mes) {
        Cuenta find = null;
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            List<Pago> filter = new ArrayList<>();
            for (int i = 0; i < find.getPagos().size(); i++) {
                if (find.getPagos().get(i).getMes().equals(mes)) {
                    filter.add(find.getPagos().get(i));
                }
            }
            find.setPagos(filter);
        }
        return find;
    }

    public Cuenta filterByTipo(Cuenta cuenta, String tipo) {
        Cuenta find = null;
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            List<Pago> filter = new ArrayList<>();
            for (int i = 0; i < find.getPagos().size(); i++) {
                if (find.getPagos().get(i).getTipo().equals(tipo)) {
                    filter.add(find.getPagos().get(i));
                }
            }
            find.setPagos(filter);
        }
        return find;
    }

    public Cuenta filterByEstado(Cuenta cuenta, Boolean estado) {
        Cuenta find = null;
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            List<Pago> filter = new ArrayList<>();
            for (int i = 0; i < find.getPagos().size(); i++) {
                if (find.getPagos().get(i).getEstado().equals(estado)) {
                    filter.add(find.getPagos().get(i));
                }
            }
            find.setPagos(filter);
        }
        return find;
    }

    public Cuenta filterBetweenDate(Cuenta cuenta, Date start, Date finish) {
        Cuenta find = null;
        Query<Cuenta> result = this.ds.find(Cuenta.class).
                field("cedula").equal(cuenta.getCedula());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            List<Pago> filter = new ArrayList<>();
            for (int i = 0; i < find.getPagos().size(); i++) {
                if (!((find.getPagos().get(i).getLastChange().before(start)) || (find.getPagos().get(i).getLastChange().after(finish)))) {
                    filter.add(find.getPagos().get(i));
                }
            }
            find.setPagos(filter);
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
        update.set("cedula", cuenta.getCedula());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean updatePago(Cuenta cuenta, Pago pago) {
        Query<Cuenta> query = this.ds.createQuery(Cuenta.class);
        query.and(
                query.criteria("cedula").equal(cuenta.getCedula()),
                query.criteria("pagos.index").equal(pago.getIndex())
        );
        UpdateOperations<Cuenta> update = this.ds.createUpdateOperations(Cuenta.class);
        update.set("pagos.$.valor", pago.getValor()).
                set("pagos.$.asunto", pago.getAsunto()).
                set("pagos.$.forma", pago.getForma()).
                set("pagos.$.mes", pago.getMes()).
                set("pagos.$.estado", pago.getEstado()).
                set("pagos.$.lastChange", pago.getLastChange());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean deletePagos(Cuenta cuenta) {
        Query<Cuenta> query = this.ds.createQuery(Cuenta.class);
        query.and(
                query.criteria("cedula").equal(cuenta.getCedula())
        );
        UpdateOperations<Cuenta> update = this.ds.createUpdateOperations(Cuenta.class);
        update.set("pagos", cuenta.getPagos());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}
