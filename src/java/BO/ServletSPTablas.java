/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import DAL.conexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "ServletSPTablas", urlPatterns = {"/ServletSPTablas"})
public class ServletSPTablas extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession s = request.getSession();
            Connection _connMy = null;
            String opcion_MaeTablas = request.getParameter("opcion_MaeTablas");
            String tablaMae = request.getParameter("slt_tabla_mae");
            String rel1 = request.getParameter("slt_tabla_rel1");
            String rel2 = request.getParameter("slt_tabla_rel2");
            String descripcion = request.getParameter("txt_tabla_descripcion");
            String descripcion2 = request.getParameter("txt_tabla_descripcion2");
            String id = request.getParameter("hid_tabla_id");    
            String salida="";
            try{
                
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));              
                CallableStatement sp_usu = _connMy.prepareCall("{call sp_mae_tablas(?,?,?,?,?,?,?)}");
                sp_usu.setString(1,opcion_MaeTablas);
                sp_usu.setLong(2,Integer.parseInt(id));
                sp_usu.setString(3,tablaMae);
                sp_usu.setString(4,rel1);
                sp_usu.setString(5,rel2);
                sp_usu.setString(6,descripcion);
                sp_usu.setString(7,descripcion2);
                sp_usu.registerOutParameter("op", Types.VARCHAR);
                sp_usu.execute();
                
                if (opcion_MaeTablas.equals("consulta_ciudad")) {
                    String valorSalida = sp_usu.getString(1);
                    salida=valorSalida;
                    out.println(salida);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                _connMy.close();
            }
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
            throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
    

    
}
