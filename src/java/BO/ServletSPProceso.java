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
 * @author YV
 */
@WebServlet(name = "ServletSPProceso", urlPatterns = {"/ServletSPProceso"})
public class ServletSPProceso extends HttpServlet {

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
            String op = request.getParameter("op");
            String id = request.getParameter("id");
            String nombre = request.getParameter("nomb");
            String unidad = request.getParameter("unid");
            String valor = request.getParameter("val");
            
            if(id == null || id.trim().isEmpty()){
                id = "0";
            }
            
            System.err.println("Opcion: "+op);
            System.err.println("Id: "+id);
            System.err.println("Nombre: "+nombre);
            System.err.println("Unidad: "+unidad);
            System.err.println("Valor: "+valor);
            
            try{           
            _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));
            CallableStatement sp_usu = _connMy.prepareCall("{call sp_mae_procesos(?,?,?,?,?)}");
            sp_usu.setString(1,op);
            sp_usu.setInt(2, Integer.parseInt(id));
            sp_usu.setString(3,nombre);
            sp_usu.setString(4,unidad);
            sp_usu.setDouble(5,Double.parseDouble(valor));
            sp_usu.registerOutParameter("op", Types.VARCHAR);
            sp_usu.execute();
            
            String respuesta = sp_usu.getString("op");
            out.println(respuesta);
            }catch(Exception e){
                e.printStackTrace();
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
            Logger.getLogger(ServletSPProceso.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException {
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
