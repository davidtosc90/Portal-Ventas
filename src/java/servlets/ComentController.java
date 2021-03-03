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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "ComentController", urlPatterns = {"/Coment/*"})
public class ComentController extends HttpServlet {

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
        String msg;
        Comentarios c;
        TypedQuery<Comentarios> query;
        List<Comentarios> lc;
        
        switch (accion) {
            case "/add":
            	String texto = request.getParameter("texto");
                String priv = request.getParameter("priv");
                String user = request.getParameter("user");
                String art = request.getParameter("art");
                
                if(texto != null && priv != null && user != null && art != null && !texto.equals("")) {
                    try {
                        c = new Comentarios();
                        c.setTexto(texto);
                        c.setPrivacidad(priv);
                        c.setIdUser(new Usuarios(Integer.parseInt(user)));
                        c.setIdArt(new Articulos(Integer.parseInt(art)));
                        persist(c);
                        System.out.println("Comentario creado");
                        msg = "<p class='ok'>Comentario publicado correctamente</p>";
                    } catch(Exception ex) {
                        System.out.println(ex);
                        System.out.println("Error: Imposible persistir  comentario");
                        msg = "<p class='error'>Error: Comentario no creado</p>";
                    }
                } else {
                    System.out.println("Error: datos incorrectos");
                    msg = "<p class='error'>Error: Faltan datos</p>";
                }
                
                query = em.createQuery("SELECT c FROM Comentarios c WHERE c.idArt = :art ORDER BY c.id DESC", Comentarios.class);
                query.setParameter("art", new Articulos(Integer.parseInt(art)));
                lc = query.getResultList();
                
                // de todos los comentarios del artículo, vamos a mostrar solo los visibles para el usuario
                List<Comentarios> visibles = new ArrayList<>();
                int myId;
                if(session.getAttribute("user") != null) {
                    myId = ((Usuarios)session.getAttribute("user")).getId();
                    for(int i = 0; i < lc.size(); i++) {
                        c = lc.get(i);
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
                    for(int i = 0; i < lc.size(); i++) {
                        c = lc.get(i);
                        if(c.getPrivacidad().equals("publico")) {
                            visibles.add(c);
                        }
                    }
                }
                
                request.setAttribute("comentarios", visibles);
                request.setAttribute("msg", msg);
                
                vista="/WEB-INF/jspf/comentarios.jsp";
                break;
            default:
                vista="/jsp/home.jsp";
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
