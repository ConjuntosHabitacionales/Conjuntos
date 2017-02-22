/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.service;

import com.conjuntos.espin.model.Usuario;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.DigestUtils;
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
@LocalBean
@Stateless
public class UsuarioService implements Serializable {

    private final MongoPersistence conn = new MongoPersistence();
    private final Datastore ds = conn.context();

    public String insert(Usuario usuario) {
        usuario.setCodUsuario(new Integer(RandomStringUtils.randomNumeric(5)));
        usuario.setPassword(DigestUtils.md5Hex(usuario.getPassword()));
        usuario.setEstado(Boolean.TRUE);
        usuario.setTipo("user");
        Usuario axu = this.findByCodUsuario(usuario);
        Key<Usuario> key = null;
        if (axu.getId() == null) {
            key = this.ds.save(usuario);
        }
        return key.toString();
    }

    public List<Usuario> obtenerLista() {
        List<Usuario> usuarios = this.ds.find(Usuario.class).asList();
        return usuarios;
    }

    public Usuario findByCodUsuario(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("codUsuario").equal(usuario.getCodUsuario());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findByUsername(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("username").equal(usuario.getUsername());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findLogin(Usuario usuario) {
        Usuario find = this.findByUsername(usuario);
        if (find != null) {
            if (!find.getPassword().equals(
                    DigestUtils.md5Hex(usuario.getPassword()))) {
                find = null;
            }
        }
        return find;
    }

    public void delete(Usuario usuario) {
        this.ds.delete(usuario);
    }

    public Boolean update(Usuario usuario) {
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        query.and(
                query.criteria("codUsuario").equal(usuario.getCodUsuario())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("username", usuario.getUsername()).
                set("password", usuario.getPassword()).
                set("tipo", usuario.getTipo()).
                set("estado", usuario.isEstado());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}
