/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entidades.Articulos;
import entidades.Comentarios;
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
@WebServlet(name = "UserController", urlPatterns = {"/User/*"})
public class UserController extends HttpServlet {

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
        TypedQuery<Usuarios> query;
        TypedQuery<Articulos> query_a;
        TypedQuery<Comentarios> query_c;
        TypedQuery<Visitas> query_v;
        List<Visitas> lv;
        List<Usuarios> lu;
        List<Articulos> la;
        List<Comentarios> lc;
        List<Articulos> ult_publi;
        Usuarios u;
        Articulos a;
        String msgerror;
        String msg;
        
        switch (accion) {
            case "/alta":
                //
                vista="/jsp/altaUsuario.jsp";
                break;
            case "/emailValido":
                String e = request.getParameter("email");
                query = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                query.setParameter("email", e);
                lu = query.getResultList();
                if (lu.size() > 0) {
                    request.setAttribute("emailValido", "No");
                } else {
                    request.setAttribute("emailValido", "Ok");
                }

                vista = "/WEB-INF/jspf/emailValido.jsp";
                break;
            case "/add":
                
                String email = request.getParameter("email");
                String clave = request.getParameter("clave");
                String clave2 = request.getParameter("clave2");
                String nombre = request.getParameter("nombre");
                String dire = request.getParameter("dire");
                String cp = request.getParameter("cp");
                String fb = request.getParameter("fb");
                String twt = request.getParameter("twt");
                String tel = request.getParameter("tel");

                if(email != null && clave != null && clave2 != null && nombre != null && dire != null
                        && cp != null && tel != null) {
                    if(clave.equals(clave2)) {
                        try {
                            u = new Usuarios();
                            u.setEmail(email);
                            u.setPassword(clave);
                            u.setNombre(nombre);
                            u.setDireccion(dire);
                            u.setCodPostal(cp);
                            u.setFacebook(fb);
                            u.setTwitter(twt);
                            u.setTelefono(tel);
                            persist(u);
                            System.out.println("Usuario: " + email + " creado");
                            msg = "<p class='ok'>Usuario: " + email + " creado adecuadamente</p>";

                        } catch (Exception ex) {
                            System.out.println(ex);
                            System.out.println("Error: Imposible persistir usuario: " + email);
                            msg = "<p class='error'>Error: Usuario " + email + " no creado</p>";
                        }
                    } else {
                        System.out.println("Error: la contraseña no coincide");
                        msg = "<p class='error'>Error: la contraseña no coincide</p>";
                    }
                } else {
                    System.out.println("Error: faltan datos");
                    msg = "<p class='error'>Error: Faltan datos</p>";
                }

                request.setAttribute("msg", msg);
                
                vista = "/jsp/altaUsuario.jsp";
                break;
            case "/login":
            	String user = request.getParameter("user");
                String pass = request.getParameter("pass");
                
                query = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                query.setParameter("email", user);
                lu = query.getResultList();
                if(lu.size() > 0) { // existe el usuario
                    if(pass.equals(lu.get(0).getPassword())) {
                        session.setAttribute("user", lu.get(0));
                    }
                    else {
                        msgerror = "Contraseña incorrecta";
                        request.setAttribute("msgerror", msgerror);
                    }
                } 
                else{
                    msgerror = "El usuario no existe";
                    request.setAttribute("msgerror", msgerror);
                }
                
                // parametros para todas las posibles vistas desde donde se pueda logear
                // parametros para vista home
                query_a = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query_a.getResultList();
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
                
                // parametros para vista verArticulos
                request.setAttribute("articulos", la);
                
                // parametros para vista alta
                
                // parametros para vista libro visitas
                query_v = em.createQuery("SELECT v FROM Visitas v ORDER BY v.id DESC", Visitas.class);
                lv = query_v.getResultList();
                request.setAttribute("visitas", lv);
                
                // parametros para vista detalle articulo
                if(request.getParameter("idArt") != null) {
                    String idArt = request.getParameter("idArt");
                    query_a = em.createNamedQuery("Articulos.findById", Articulos.class);
                    query_a.setParameter("id", Integer.parseInt(idArt));
                    la = query_a.getResultList();
                    a = la.get(0);

                    query_c = em.createQuery("SELECT c FROM Comentarios c WHERE c.idArt = :art ORDER BY c.id DESC", Comentarios.class);
                    query_c.setParameter("art", new Articulos(Integer.parseInt(idArt)));
                    lc = query_c.getResultList();

                    // de todos los comentarios del artículo, vamos a mostrar solo los visibles para el usuario
                    List<Comentarios> visibles = new ArrayList<>();
                    int myId;
                    if(session.getAttribute("user") != null) {
                        myId = ((Usuarios)session.getAttribute("user")).getId();
                        for(int i = 0; i < lc.size(); i++) {
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
                        for(int i = 0; i < lc.size(); i++) {
                            Comentarios c = lc.get(i);
                            if(c.getPrivacidad().equals("publico")) {
                                visibles.add(c);
                            }
                        }
                    }

                    request.setAttribute("comentarios", visibles);
                    request.setAttribute("articulo", a);
                    request.setAttribute("vendedor", a.getIdUser());
                    request.setAttribute("favorito", "No"); // acaba de loguearse, no hay articulos favoritos 
                }
                
                // va a redigirir a la misma página desde donde se envió la petición
                String pag = request.getParameter("pag");
                String[] v = pag.split("/");
                vista="/jsp/" + v[v.length-1];
                break;
            case "/logout":
                session.invalidate();
                
                query_a = em.createQuery("SELECT a FROM Articulos a ORDER BY a.id DESC", Articulos.class);
                la = query_a.getResultList();
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
                
                vista="/jsp/home.jsp";
                break;
            default:
                vista="/jsp/altaUsuario.jsp";
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
