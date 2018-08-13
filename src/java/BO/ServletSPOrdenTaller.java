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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author Ivan
 */
@WebServlet(name = "ServletSPOrdenTaller", urlPatterns = {"/ServletSPOrdenTaller"})
public class ServletSPOrdenTaller extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession s = request.getSession();
            Connection _connMy = null;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            DecimalFormat nf = new DecimalFormat();
            String opcion = request.getParameter("opcion");

            String numeroOrden = request.getParameter("txt_orden_numero");
            String numeroCotiza = request.getParameter("txt_cotizacion_numero");
            String desde = request.getParameter("fecha_desde");
            String hasta = request.getParameter("fecha_hasta");
            String estado = request.getParameter("txt_estado");
            String numeroFactura = request.getParameter("numero_factura");
            String fechaFactura = request.getParameter("fecha_factura");
            
            if(numeroOrden == null || numeroOrden.isEmpty())
                numeroOrden = "";
            
            if(numeroCotiza == null || numeroCotiza.isEmpty())
                numeroCotiza = "0";

            if(desde == null || desde.isEmpty())
                desde = "";

            if(hasta == null || hasta.isEmpty())
                hasta = "";

            if(estado == null || hasta.isEmpty())
                estado = "";

            if(numeroFactura == null || numeroFactura.isEmpty())
                numeroFactura = "";
            
            if(fechaFactura == null || fechaFactura.isEmpty())
                fechaFactura = "";
            
            if(!fechaFactura.isEmpty()){
                try {
                    fechaFactura = new Date(formato.parse(fechaFactura).getTime()).toString();
                    
                } catch (ParseException ex) {
                    
                }
            }
            
            
            try {
                _connMy = conexionBD.Conectar((String) s.getAttribute("organizacion"));
                CallableStatement sp_usu = _connMy.prepareCall("{call sp_ordentaller(?,?,?,?,?,?,?,?)}");
                sp_usu.setString(1, opcion);
                sp_usu.setString(2, numeroOrden);
                sp_usu.setInt(3, Integer.parseInt(numeroCotiza));
                sp_usu.setString(4, estado);
                sp_usu.setString(5, desde);
                sp_usu.setString(6, hasta);
                sp_usu.setString(7, numeroFactura);
                sp_usu.setString(8, fechaFactura);
                sp_usu.registerOutParameter(1, Types.VARCHAR);

                sp_usu.execute();
                switch(opcion){
                    case "select_all":
                        final ResultSet rs = sp_usu.getResultSet();
                        String salida = "";
                        String cla;
                        int cont = 0;
                        while (rs.next()){
                            if (cont % 2 == 0) {
                                cla = "alt";
                            } else {
                                cla = "";
                            }
                            salida += "<tr id='filaTablaOrdenTaller" + cont + "' class='" + cla + "'>";
                            salida += "<td>";
                            salida += "<a href=\"javascript: onclick=ModificaOrdenTaller(" + cont + ",'" + rs.getString("numero_ordentaller") +"')\"> >></a>";
                            salida += "<input type=\"hidden\" value=\"0\" id=\"habilitaActCom\" name=\"habilitaActCom\" />";
                            salida += "<input type=\"hidden\" value=\"\" id=\"corrOT\" />";
                            salida += "<input type=\"hidden\" value=\"\" id=\"selectorOT\" />";
                            salida += "</td>";

                            salida += "<td id='diasR"+cont+"'>"+rs.getString("dias_restantes")+"</td>";
                            salida += "<td id='numero_ot"+cont+"'>"+rs.getString("numero_ordentaller")+"</td>";
                            salida += "<td id='cliente"+cont+"'>"+rs.getString("razon_social")+"</td>";
                            salida += "<td id='descPieza"+cont+"'>"+rs.getString("desc_pieza")+"</td>";
                            salida += "<td id='diametro"+cont+"'>"+rs.getString("diametro_interno")+"</td>";
                            salida += "<td id='largo"+cont+"'>"+rs.getString("largo")+"</td>";
                            salida += "<td id='fecha_termino"+cont+"'>"+rs.getString("fecha_prometida")+"</td>";
                            salida += "<td id='condicion"+cont+"'>"+rs.getString("condicion")+"</td>";
                            salida += "<td id='num_cotizacion"+cont+"'>"+rs.getString("numero_cotizacion")+"</td>";                        
                            salida += "<td id='fecha_emision"+cont+"'>"+rs.getString("fecha_emision")+"</td>";                                                                        
                            salida += "<td id='estado"+cont+"'>"+rs.getString("estado")+"</td>";
                            salida += "<td id='cantidad"+cont+"'>"+rs.getString("cantidad")+"</td>";
                            salida += "<td id='saldo"+cont+"'>"+rs.getString("saldo")+"</td>";
                            salida += "<td id='totalNeto"+cont+"' class=\"columnaTotal\">$ "+nf.format(Integer.parseInt(rs.getString("total_pieza")))+"</td>";
                            salida += "<td style='display: none' id='secuencia"+cont+"'></td>";
                            salida += "</tr>";
                            cont++;
                        }
                        out.println(salida);
                        break;
                    case "update":
                        out.println(sp_usu.getString(1));
                        out.println(numeroOrden);
                        out.println(numeroFactura);
                        out.println(fechaFactura);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    _connMy.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

}
