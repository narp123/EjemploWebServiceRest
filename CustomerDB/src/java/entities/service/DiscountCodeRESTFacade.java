/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.service;

import javax.persistence.EntityManager;
import entities.DiscountCode;
import java.net.URI;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Estudiantes
 */
@Path("entities.discountcode")
@com.sun.jersey.spi.resource.Singleton
@com.sun.jersey.api.spring.Autowire
public class DiscountCodeRESTFacade {

    @PersistenceContext(unitName = "CustomerDBPU")
    @Error("Please fix your project manually, for instructions see http://wiki.netbeans.org/SpringWithAopalliance")
    protected EntityManager entityManager;

    public DiscountCodeRESTFacade() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Transactional
    public Response create(DiscountCode entity) {
        entityManager.persist(entity);
        return Response.created(URI.create(entity.getDiscountCode().toString())).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Transactional
    public void edit(DiscountCode entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remove(@PathParam("id") String id) {
        DiscountCode entity = entityManager.getReference(DiscountCode.class, id);
        entityManager.remove(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Transactional
    public DiscountCode find(@PathParam("id") String id) {
        return entityManager.find(DiscountCode.class, id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Transactional
    public List<DiscountCode> findAll() {
        return find(true, -1, -1);
    }

    @GET
    @Path("{max}/{first}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Transactional
    public List<DiscountCode> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return find(false, max, first);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String count() {
        try {
            Query query = entityManager.createQuery("SELECT count(o) FROM DiscountCode AS o");
            return query.getSingleResult().toString();
        } finally {
            entityManager.close();
        }
    }

    private List<DiscountCode> find(boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createQuery("SELECT object(o) FROM DiscountCode AS o");
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    
}
