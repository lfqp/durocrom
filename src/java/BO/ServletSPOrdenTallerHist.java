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
import java.sql.Types;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Blas Muñoz
 */
@WebServlet(name = "ServletSPOrdenTallerHist", urlPatterns = {"/ServletSPOrdenTallerHist"})
public class ServletSPOrdenTallerHist extends HttpServlet {

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
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            
            String opcion=request.getParameter("opcion");            
            String nroOT=request.getParameter("txt_orden_numeroOT");
            String cantidad_reb=!request.getParameter("txt_orden_CantRebaja").equals("")? request.getParameter("txt_orden_CantRebaja") : "0";
            String usuario = (String) s.getAttribute("nom");            
            String rutUsu = (String) s.getAttribute("rut");   
            String seq = request.getParameter("seq");
            String id = !request.getParameter("id").equals("")? request.getParameter("id") : "0";
            
            try{
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion")); 
                CallableStatement sp_usu=null;
                                           
                sp_usu = _connMy.prepareCall("{call sp_ordentaller_hist(?,?,?,?,?,?)}");
                sp_usu.setString(1, opcion);
                sp_usu.setString(2, nroOT);
                sp_usu.setInt(3,Integer.parseInt(cantidad_reb));
                sp_usu.setString(4, usuario);
                sp_usu.setInt(5, Integer.parseInt(seq));                
                sp_usu.setInt(6, Integer.parseInt(id));                
                sp_usu.registerOutParameter(1, Types.VARCHAR);                
                
                sp_usu.execute();
                String salida = "";                
                int cont = 0;
                String cla = "";
                String out_msg = "";
                final ResultSet rs = sp_usu.getResultSet();                                
                
                switch(opcion) {
                case "select_proceso":
                    while(rs.next())
                    {
                        String materiales = "";
                        materiales += rs.getString("mat_descripcion1")+"|";
                        materiales += rs.getString("mat_cantidad1")+"|";
                        materiales += rs.getString("mat_precio1")+"|";
                        materiales += rs.getString("mat_totalitem1")+"|";
                        materiales += rs.getString("mat_descripcion2")+"|";
                        materiales += rs.getString("mat_cantidad2")+"|";
                        materiales += rs.getString("mat_precio2")+"|";
                        materiales += rs.getString("mat_totalitem2")+"|";
                        materiales += rs.getString("mat_descripcion3")+"|";
                        materiales += rs.getString("mat_cantidad3")+"|";
                        materiales += rs.getString("mat_precio3")+"|";
                        materiales += rs.getString("mat_totalitem3")+"|";
                        materiales += rs.getString("mat_descripcion4")+"|";
                        materiales += rs.getString("mat_cantidad4")+"|";
                        materiales += rs.getString("mat_precio4")+"|";
                        materiales += rs.getString("mat_totalitem4")+"|";
                        materiales += rs.getString("mat_descripcion5")+"|";
                        materiales += rs.getString("mat_cantidad5")+"|";
                        materiales += rs.getString("mat_precio5")+"|";
                        materiales += rs.getString("mat_totalitem5")+"|";
                        materiales += rs.getString("totales")+"|";
                        materiales += rs.getString("total_materiales")+"|";
                        materiales += rs.getString("total_pieza")+"|";
                        materiales += rs.getString("iva")+"|";
                        materiales += rs.getString("total_bruto")+"|";

                        salida += "{\"procesos\":{\"codproc\":" + rs.getString("json_codproc") + "," + 
                            "\"cantproc\":"+ rs.getString("json_cantproc")+"," + 
                            "\"precioproc\":" + rs.getString("json_precioproc")+ "," + 
                            "\"totalproc\":" + rs.getString("json_totalproc") +"},\"materiales\":\"||||||||||||||||||||||||||||||||||||||||||||||||"+
                                materiales.replace("\"","\\\"") +"\"}";
                    }
                    out.println(salida);
                    break;
                case "limpia": 
                    salida = sp_usu.getString(1);
                    out.println(salida);
                    break;
                case "save": 
                    salida = sp_usu.getString(1);
                    out.println(salida);
                    try{
                        CallableStatement sp_aux = null;
                        sp_aux =_connMy.prepareCall("{call sp_historialOT(?,?,?,?)}");
                        sp_aux.setString(1, "insert");
                        sp_aux.setString(2, nroOT);
                        sp_aux.setString(3, rutUsu);
                        sp_aux.setString(4, usuario);                        
                        sp_aux.execute();
                        final ResultSet rsAux = sp_aux.getResultSet();
                    }catch(Exception e){
                        e.printStackTrace();
                    }                                                                                                                       
                    break;
                case "select_all":
                    cont = 0;
                    cla = "";
                    out_msg = sp_usu.getString(1);
                    while(rs.next())
                    {
                        if(cont % 2 == 0)
                        {
                            cla = "alt";
                        }else
                        {  
                            cla = "";

                        }
                        salida += "<tr id='filaTablaHistOT"+cont+"' class='"+cla+"'>";
                        salida += "<td><a href=\"javascript: onclick=ModificaRebaja("+cont+","+rs.getString("id")+","
                                    +rs.getString("cantidad_rebajada")+")\"> >></a>\n" +
                                  "<input type=\"hidden\" value=\"0\" id=\"habilitaRebaja\" name=\"habilitaRebaja\" />";                                
                        salida += "<td>"+rs.getString("numero_ordentaller")+"</td>";
                        salida += "<td>"+rs.getString("fecha_modificacion")+"</td>";
                        salida += "<td>"+rs.getString("cantidad_rebajada")+"</td>";
                        salida += "<td>"+rs.getString("nombre_usuario")+"</td>";                        
                        salida += "</tr>";
                        cont ++;
                        
                    }                  
                    salida += "~"+out_msg;
                    out.println(salida);
                    break;
                case "elimina":
                    cont = 0;
                    cla = "";
                    out_msg = sp_usu.getString(1);
                    while(rs.next())
                    {
                        if(cont % 2 == 0)
                        {
                            cla = "alt";
                        }else
                        {  
                            cla = "";

                        }
                        salida += "<tr id='filaTablaHistOT"+cont+"' class='"+cla+"'>";
                        salida += "<td><a href=\"javascript: onclick=ModificaRebaja("+cont+","+rs.getString("id")+","
                                    +rs.getString("cantidad_rebajada")+")\"> >></a>\n" +
                                  "<input type=\"hidden\" value=\"0\" id=\"habilitaRebaja\" name=\"habilitaRebaja\" />";
                        salida += "<td>"+rs.getString("numero_ordentaller")+"</td>";
                        salida += "<td>"+rs.getString("fecha_modificacion")+"</td>";
                        salida += "<td>"+rs.getString("cantidad_rebajada")+"</td>";
                        salida += "<td>"+rs.getString("nombre_usuario")+"</td>";                        
                        salida += "</tr>";
                        cont ++;
                        
                    }                  
                    salida += "~"+out_msg;
                    out.println(salida);
                    break;
                case "insert":
                    out_msg = sp_usu.getString(1);
                    String[] parts = out_msg.split("~");
                    String out1 = parts[0];
                    if(out1.equals("OK"))
                    {
                        cont = 0;
                        
                        cla = "";
                        while(rs.next())
                        {
                            if(cont % 2 == 0)
                            {
                                cla = "alt";
                            }else
                            {  
                                cla = "";

                            }
                            salida += "<tr id='filaTablaHistOT"+cont+"' class='"+cla+"'>";
                            salida += "<td><a href=\"javascript: onclick=ModificaRebaja("+cont+","+rs.getString("id")+","
                                    +rs.getString("cantidad_rebajada")+")\"> >></a>\n" +
                                      "<input type=\"hidden\" value=\"0\" id=\"habilitaRebaja\" name=\"habilitaRebaja\" /></td>";
                            salida += "<td>"+rs.getString("numero_ordentaller")+"</td>";
                            salida += "<td>"+rs.getString("fecha_modificacion")+"</td>";
                            salida += "<td>"+rs.getString("cantidad_rebajada")+"</td>";
                            salida += "<td>"+rs.getString("nombre_usuario")+"</td>";                        
                            salida += "</tr>";
                            cont ++;
                        }                    
                    }
                    out.println(out_msg+"~"+salida);
                    break;                
                default: 
                    salida = "Error de opcion SP_ORDENTALLER_HIST";
                    break;
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
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
