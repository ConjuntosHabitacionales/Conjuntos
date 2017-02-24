package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Convocatoria;
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
public class ConvocatoriaService implements Serializable {

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public void insert(Convocatoria convocatoria) {
        Convocatoria axu = this.findByCodConvocatoria(convocatoria);
        if (axu.getId() == null) {
            convocatoria.setCodConvocatoria(new Integer(RandomStringUtils.randomNumeric(5)));
            this.ds.save(convocatoria);
        }else{
            this.ds.save(convocatoria);
        }
    }

    public List<Convocatoria> obtenerLista() {
        List<Convocatoria> convocatorias = this.ds.find(Convocatoria.class).asList();
        return convocatorias;
    }

    public Convocatoria findByCodConvocatoria(Convocatoria convocatoria) {
        Convocatoria find = new Convocatoria();
        Query<Convocatoria> result = this.ds.find(Convocatoria.class).
                field("codConvocatoria").equal(convocatoria.getCodConvocatoria());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Convocatoria convocatoria) {
        this.ds.delete(convocatoria);
    }

    public Boolean update(Convocatoria convocatoria) {
        Query<Convocatoria> query = this.ds.createQuery(Convocatoria.class);
        query.and(
                query.criteria("codConvocatoria").equal(convocatoria.getCodConvocatoria())
        );
        UpdateOperations<Convocatoria> update = this.ds.createUpdateOperations(Convocatoria.class);
        update.set("codConvocatoria", convocatoria.getCodConvocatoria()).
                set("fechaConvocatoria", convocatoria.getFechaConvocatoria()).
                set("lugar", convocatoria.getLugar()).
                set("motivo", convocatoria.getMotivo()).
                set("requisitos", convocatoria.getRequisitos());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

}
