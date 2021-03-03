/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entidades.Articulos;
import entidades.Comentarios;
import entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@MultipartConfig
@WebServlet(name = "ArtController", urlPatterns = {"/Art/*"})
public class ArtController extends HttpServlet {

    @PersistenceContext(unitName = "TFC_DAWPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion;
        accion = request.getPathInfo();
        String vista;
        HttpSession session = request.getSession();
        TypedQuery<Articulos> query;
        TypedQuery<Comentarios> query_c;
        List<Articulos> la;
        List<Articulos> ult_publi;
        List<Comentarios> lc;
        Articulos a;
        String msg;
        String id_art;
        int i=0;
        
        switch (accion) {
            case "/ver":
                query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query.getResultList();
                request.setAttribute("articulos", la);
                vista="/jsp/verArticulos.jsp";
                break;
            case "/alta":
                if(session.getAttribute("user") != null)
                    vista="/jsp/altaArticulo.jsp";
                else {
                    query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                    la = query.getResultList();
                    if(la != null) {
                        ult_publi = new ArrayList();
                        // ordenados descendientemente por el id, los primeros articulos son las más recientes
                        if(la.size() > 3) {
                        for(i = 0; i < 3; i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    else {
                        for(i = 0; i < la.size(); i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                        request.setAttribute("ult_art", ult_publi);
                    }
                    vista="/jsp/home.jsp";
                }
                break;
            case "/add":
                
                String name = request.getParameter("nombre");
                String desc = request.getParameter("desc");
                String cat = request.getParameter("cat");
                String est = request.getParameter("est");
                String anio = request.getParameter("anio");
                String cp = request.getParameter("cp");
                String pvp = request.getParameter("precio");
                // faltaria recoger la imagen

                if(name != null && pvp != null && cat != null && cp != null) {
                    try {
                        a = new Articulos();
                        a.setNombre(name);
                        a.setDescripcion(desc);
                        a.setCategoria(cat);
                        a.setEstado(est);
                        a.setAnioAdq(anio);
                        a.setCodPostal(cp);
                        a.setPrecio(Double.parseDouble(pvp));
                        a.setIdUser((Usuarios)session.getAttribute("user"));
                        persist(a);
                        System.out.println("Artículo: " + name + " creado");
                        msg = "<p class='ok'>Artículo: " + name + " creado adecuadamente</p>";

                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println("Error: Imposible persistir  articulo: " + name);
                        msg = "<p class='error'>Error: Artículo " + name + " no creado</p>";
                    }
                } else {
                    System.out.println("Error: datos incorrectos");
                    msg = "<p class='error'>Error: Faltan datos</p>";
                }

                request.setAttribute("msg", msg);
                query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query.getResultList();
                request.setAttribute("articulos", la);
                vista="/jsp/verArticulos.jsp";
                break;
            case "/filtro":
                String where = null;
                boolean bn = false, bn1 = false, bn2 = false;
                String n = request.getParameter("cat");
                String n1 = request.getParameter("cod");
                String n2 = request.getParameter("pmenor");
                String n3 = request.getParameter("pmayor");
                
                if(!"todas".equals(n)) {
                    where = "WHERE a.categoria = '" + n + "'";
                    bn = true;
                }
                if (!"".equals(n1)) {
                    if(bn) {
                        where += " AND a.codPostal LIKE '" + n1 + "%'";
                    }
                    else {
                        where = "WHERE a.codPostal LIKE '" + n1 + "%'";
                    }
                    bn1 = true;
                }
                if (!"".equals(n2)) {
                    if (bn || bn1) {
                        where += " AND a.precio > " + n2;
                    } else {
                        where = "WHERE a.precio > " + n2;
                    }
                    bn2 = true;
                }
                if (!"".equals(n3)) {
                    if (bn || bn1 || bn2) {
                        where += " AND a.precio < " + n3;
                    } else {
                        where = "WHERE a.precio < " + n3;
                    }
                }

                query = em.createQuery("SELECT a FROM Articulos a " + (where != null ? where : "") + " ORDER BY a.id DESC", Articulos.class);
                la = (List<Articulos>) query.getResultList();
                request.setAttribute("articulos", la);

                vista = "/WEB-INF/jspf/art_filtro.jsp";
                break;
            case "/filtroFav":
                String categ = request.getParameter("cat");
                String cod = request.getParameter("cod");
                String pmenor = request.getParameter("pmenor");
                String pmayor = request.getParameter("pmayor");
                List<Articulos> f = (List<Articulos>)session.getAttribute("fav");
                
                List<Articulos> favFiltrados = new ArrayList<>();
                boolean valido;
                for(i = 0; i < f.size(); i++) {
                    valido = true;
                    Articulos afav = f.get(i);
                    if(!"todas".equals(categ) && !afav.getCategoria().equals(categ))
                        valido = false;
                    else if(!"".equals(cod) && !afav.getCodPostal().equals(cod))
                        valido = false;
                    else if(!"".equals(pmenor) && afav.getPrecio() < Double.parseDouble(pmenor))
                        valido = false;
                    else if(!"".equals(pmayor) && afav.getPrecio() > Double.parseDouble(pmayor))
                        valido = false;
                    
                    if(valido)
                        favFiltrados.add(afav);
                }
                
                request.setAttribute("articulos", favFiltrados);

                vista = "/WEB-INF/jspf/art_filtro_fav.jsp";
                break;
            case "/detalle":
                List<Articulos> favoritos = (List<Articulos>)session.getAttribute("fav");
                String id = request.getParameter("id");
                query = em.createNamedQuery("Articulos.findById", Articulos.class);
                query.setParameter("id", Integer.parseInt(id));
                la = query.getResultList();
                a = la.get(0);
                
                // comprobamos si el artículo está en favoritos
                
                i=0;
                boolean encontrado = false;
                if(favoritos != null) {
                    while(i < favoritos.size() && !encontrado) {
                        if(favoritos.get(i).getId() == Integer.parseInt(id))
                            encontrado = true;
                        i++;
                    }
                }
                
                if(encontrado)
                    request.setAttribute("favorito", "Si");
                else
                    request.setAttribute("favorito", "No");
                
                
                query_c = em.createQuery("SELECT c FROM Comentarios c WHERE c.idArt = :art ORDER BY c.id DESC", Comentarios.class);
                query_c.setParameter("art", new Articulos(Integer.parseInt(id)));
                lc = query_c.getResultList();
                
                // de todos los comentarios del artículo, vamos a mostrar solo los visibles para el usuario
                List<Comentarios> visibles = new ArrayList<>();
                int myId;
                if(session.getAttribute("user") != null) {
                    myId = ((Usuarios)session.getAttribute("user")).getId();
                    for(i = 0; i < lc.size(); i++) {
                        Comentarios c = lc.get(i);
                        if(c.getPrivacidad().equals("publico")) {
                            visibles.add(c);
                        } 
                        else if(c.getIdUser().getId() == myId) { // si es mío el comentario...
                            visibles.add(c);
                        }
                        else if((c.getPrivacidad().equals("vendedor")) 
                                && (c.getIdArt().getIdUser().getId() == myId)) { // si es para el vendedor y soy yo...
                            visibles.add(c);
                        }
                    }
                }
                else { // si no está logueado, muestro los comentarios públicos
                    for(i = 0; i < lc.size(); i++) {
                        Comentarios c = lc.get(i);
                        if(c.getPrivacidad().equals("publico")) {
                            visibles.add(c);
                        }
                    }
                }
                
                request.setAttribute("comentarios", visibles);
                request.setAttribute("articulo", a);
                request.setAttribute("vendedor", a.getIdUser());
                vista="/jsp/detalleArticulo.jsp";
                break;
            case "/verFav":
                if(session.getAttribute("user") != null)
                    vista="/jsp/verFavoritos.jsp";
                else {
                    query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                    la = query.getResultList();
                    if(la != null) {
                        ult_publi = new ArrayList();
                        // ordenados descendientemente por el id, los primeros articulos son las más recientes
                        if(la.size() > 3) {
                        for(i = 0; i < 3; i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    else {
                        for(i = 0; i < la.size(); i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                        request.setAttribute("ult_art", ult_publi);
                    }
                    vista="/jsp/home.jsp";
                }
                break;
            case "/botonFav":
                id_art = request.getParameter("id");
                
                if(id_art != null) {
                    query = em.createNamedQuery("Articulos.findById", Articulos.class);
                    query.setParameter("id", Integer.parseInt(id_art));
                    la = query.getResultList();
                    
                    if(la != null) {
                        List<Articulos> fav = (List<Articulos>)session.getAttribute("fav");
                        if(fav == null) {
                            fav = new ArrayList<>();
                        }
                        a = la.get(0);
                        fav.add(a);
                        request.setAttribute("addFav", "Ok");
                        session.setAttribute("fav", fav);
                    }
                }
                
                vista = "/WEB-INF/jspf/textoFav.jsp";
                break;
            case "/enlaceFav":
                id_art = request.getParameter("id");
                
                if(id_art != null) {
                    query = em.createNamedQuery("Articulos.findById", Articulos.class);
                    query.setParameter("id", Integer.parseInt(id_art));
                    la = query.getResultList();
                    
                    if(la != null) {
                        List<Articulos> fav = (List<Articulos>)session.getAttribute("fav");
                        if(fav == null) {
                            fav = new ArrayList<>();
                        }
                        a = la.get(0);
                        if(!fav.contains(a)) {
                            fav.add(a);
                            msg = "<p class='ok'>Artículo: " + a.getNombre() + " añadido a favoritos</p>";
                            session.setAttribute("fav", fav);
                        }
                        else {
                            msg = "<p class='error'>Error: Artículo: " + a.getNombre() + " ya estaba en favoritos</p>";
                        }
                    }
                    else {
                        msg = "<p class='error'>Error al añadir artículo a favoritos</p>";
                    }
                }
                else {
                    msg = "<p class='error'>Error al añadir artículo a favoritos</p>";
                }
                request.setAttribute("msg", msg);
                
                // parametros para todas las posibles vistas desde donde pueda añadir a favoritos con el enlace
                // parametros para vista home
                query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query.getResultList();
                if(la != null) {
                    ult_publi = new ArrayList();
                    // ordenados descendientemente por el id, los primeros articulos son las más recientes
                    if(la.size() > 3) {
                        for(i = 0; i < 3; i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    else {
                        for(i = 0; i < la.size(); i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    request.setAttribute("ult_art", ult_publi);
                }
                
                // parametros para vista verArticulos
                request.setAttribute("articulos", la);
                
                // va a redigirir a la misma página desde donde se envió la petición
                String pag = request.getParameter("pag");
                vista="/jsp/" + pag;
                break;
            default:
                query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query.getResultList();
                request.setAttribute("articulos", la);
                vista="/jsp/verArticulos.jsp";
                break;
        }
        RequestDispatcher rd = request.getRequestDispatcher(vista);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
