/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entidades.Articulos;
import entidades.Usuarios;
import entidades.Visitas;
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
@WebServlet(name = "Controller", urlPatterns = {"/home", "/libro", "/addVisita"})
public class Controller extends HttpServlet {

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
        accion = request.getServletPath();
        String vista;
        HttpSession session = request.getSession();
        TypedQuery<Articulos> query;
        TypedQuery<Visitas> query_v;
        List<Articulos> la;
        List<Visitas> lv;
        List<Articulos> ult_publi;
        Visitas v;
        String msg;

        switch (accion) {
            case "/home":
                query = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query.getResultList();
                if(la != null) {
                    ult_publi = new ArrayList();
                    // ordenados descendientemente por el id, los primeros articulos son las más recientes
                    if(la.size() > 3) {
                        for(int i = 0; i < 3; i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    else {
                        for(int i = 0; i < la.size(); i++) {
                            ult_publi.add(la.get(i));
                        }
                    }
                    request.setAttribute("ult_art", ult_publi);
                }
                vista = "/jsp/home.jsp";
                break;
            case "/libro":
                query_v = em.createQuery("SELECT v FROM Visitas v ORDER BY v.id DESC", Visitas.class);
                lv = query_v.getResultList();
                request.setAttribute("visitas", lv);
                vista="/jsp/libro_firmas.jsp";
                break;
            case "/addVisita":
                String nombre = request.getParameter("nombre");
                String correo = request.getParameter("correo");
                String val;
                if(session.getAttribute("user") != null)
                    val = request.getParameter("val");
                else
                    val = "cero";
                String mensaje = request.getParameter("mensaje");
                
                if(nombre != null && correo != null && val != null && !nombre.equals("") && !correo.equals("") && !val.equals("")) {
                    try {
                        v = new Visitas();
                        v.setNombre(nombre);
                        v.setCorreo(correo);
                        v.setValoracion(val);
                        v.setMensaje(mensaje);
                        persist(v);
                        System.out.println("Visita creada");
                        msg = "<p class='ok'>Comentario publicado correctamente</p>";
                        session.setAttribute("valorado", "Si");
                    } catch(Exception ex) {
                        System.out.println(ex);
                        System.out.println("Error: Imposible persistir  visita");
                        msg = "<p class='error'>Error: Comentario no publicado</p>";
                    }
                } else {
                    System.out.println("Error: datos incorrectos");
                    msg = "<p class='error'>Error: Faltan datos</p>";
                }
                
                query_v = em.createQuery("SELECT v FROM Visitas v ORDER BY v.id DESC", Visitas.class);
                lv = query_v.getResultList();
                request.setAttribute("visitas", lv);
                
                request.setAttribute("msg", msg);
                
                vista="/jsp/libro_firmas.jsp";
                break;
            default:
                vista = "/jsp/home.jsp";
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

    public void persist1(Object object) {
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
