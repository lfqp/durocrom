/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

/**
 *
 * @author Ivan
 */
import java.sql.Date;
import java.text.SimpleDateFormat;
import DAL.conexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author Felix
 */
@WebServlet(name = "ServletSPProcesos", urlPatterns = {"/ServletSPProcesos"})
public class ServletSPProcesos extends HttpServlet {

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

            String pieza=request.getParameter("pieza");
            String uf=request.getParameter("valorUF");
            String area=request.getParameter("area");
            String cantidad=request.getParameter("cantidad");
            
            try{
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion")); 
                CallableStatement sp_usu=null;
                
                sp_usu = _connMy.prepareCall("{call sp_carga_procesos(?)}");
                sp_usu.setDouble(1,Double.parseDouble(uf));
                sp_usu.execute();
                
                final ResultSet rs = sp_usu.getResultSet();
                String salida = "";
                int cont = 1;                
                salida += "<table id=\"procesos\">";
                salida += "<tbody>";
                while(rs.next())
                    {
                        
                        salida += "<tr>";
                        salida += "<td style=\"width:240px;\"><input type=\"checkbox\" name=\"chk_group[]\" value=\"" + rs.getString("codigo") + "\" onchange=\"calculaTotalProcesos(" + cont + ")\"/>\"" + rs.getString("nombre")+ "\" <br /></td>";
                        if (rs.getString("unidad").equals("$/DM2")) {
                            salida += "<td style=\"width:120px\"><input style=\"width:120px; text-align: right\" type=\"text\" readonly maxlength=\"9\" value=\"" + area + "\" id=\"txt_cotizacion_cant" + cont + "\" name=\"txt_cotizacion_cant" + cont + "\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalProcesos(" + cont + ")\"/></td>";
                        }
                        else {
                            salida += "<td style=\"width:120px\"><input style=\"width:120px; text-align: right\" type=\"text\" maxlength=\"9\" value=\"0\" id=\"txt_cotizacion_cant" + cont + "\" name=\"txt_cotizacion_cant" + cont + "\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalProcesos(" + cont + ")\"/></td>";
                        } 
                        salida += "<td style=\"width:10px\">&nbsp;X&nbsp;</td>";
                        salida += "<td style=\"width:160px;\"><input style=\"width:120px; text-align: right\" type=\"text\" readonly maxlength=\"9\" value=\"" + cantidad + "\" id=\"txt_cotizacion_cantidad" + cont + "\" name=\"txt_cotizacion_cantidad" + cont + "\"/></td>";                       
                        salida += "<td style=\"width:130px;\"><input class=\"numeric\" style=\"width:120px; text-align: right\" type=\"text\" maxlength=\"9\" value=\"" + rs.getString("valor") + "\" id=\"txt_cotizacion_preciounitario" + cont + "\" name=\"txt_cotizacion_preciounitario" + cont + "\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalProcesos(" + cont + ");numberWithCommas(" + cont + ")\"/></td>";
                        salida += "<td style=\"width:80px;\">&nbsp;" + rs.getString("unidad") +"</td>";   
                        salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" readonly value=\"0\" id=\"txt_cotizacion_total" + cont + "\" name=\"txt_cotizacion_total" + cont + "\" /></td>";
                        salida += "</tr>";
                        cont ++;                                    
                    }
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Total Procesos</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_totalProcesos\" name=\"txt_cotizacion_totalProcesos\" /></td>";
                salida += "</tr>";
                
                 //INICIO: se agregan campos (%margen, utilidad, total) 02-01-2019
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>% Margen</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\"  name=\"txt_cotizacion_margenvo\" onchange=\"calculaCampoTotal()\" /></td>";
                salida += "</tr>";
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Utilidad</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_utilidad\" name=\"txt_cotizacion_utilidad\" /></td>";                
                salida += "</tr>";
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Total</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_totalnvo\" name=\"txt_cotizacion_totalnvo\" /></td>";                
                salida += "</tr>";
                //FIN : : se agregan campos (%margen, utilidad, total) 02-01-2019      
                
                salida += "<tr>&nbsp;</tr>";
                salida += "</tbody>";
                salida += "</table>";
                salida += "<table id=\"materiales\">";
                salida += "<tbody>";
                salida += "<tr>";
                salida += "</tr>";
                salida += "<tr>";
                salida += "<td bgcolor=\"#2798D9\" style=\"color:white; width:120px;\"><b>MATERIALES</b></td>";
                salida += "<td colspan=\"3\" bgcolor=\"#2798D9\" style=\"color:white\" >Descripcion</td>";
                salida += "<td bgcolor=\"#2798D9\" style=\"color:white\" >Cantidad</td>";
                salida += "<td bgcolor=\"#2798D9\" style=\"color:white\" >Precio Unitario</td>";
                salida += "<td bgcolor=\"#2798D9\" style=\"color:white\" >Total Item</td>";
                salida += "</tr>";                
                salida += "<tr><td>&nbsp;</td></tr>";
                salida += "<tr>";                
                salida += "<td></td>";                                
                salida += "<td colspan=\"3\"><input style=\"width:260px; text-align: left\" type=\"text\" maxlength=\"45\" id=\"txt_cotizacion_materiales_descripcion1\" name=\"txt_cotizacion_materiales_descripcion1\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"6\" id=\"txt_cotizacion_materiales_cantidad1\" name=\"txt_cotizacion_materiales_cantidad1\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(1)\" /></td>";
                salida += "<td><input style=\"width:110px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" id=\"txt_cotizacion_materiales_preciounitario1\" name=\"txt_cotizacion_materiales_preciounitario1\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(1)\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" readonly id=\"txt_cotizacion_materiales_totalitem1\" name=\"txt_cotizacion_materiales_totalitem1\" /></td>";                
                salida += "</tr>";                                
                salida += "<tr>";                
                salida += "<td></td>";                                
                salida += "<td colspan=\"3\"><input style=\"width:260px; text-align: left\" type=\"text\" maxlength=\"45\" id=\"txt_cotizacion_materiales_descripcion2\" name=\"txt_cotizacion_materiales_descripcion2\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"6\" id=\"txt_cotizacion_materiales_cantidad2\" name=\"txt_cotizacion_materiales_cantidad2\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(2)\" /></td>";
                salida += "<td><input style=\"width:110px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" id=\"txt_cotizacion_materiales_preciounitario2\" name=\"txt_cotizacion_materiales_preciounitario2\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(2)\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" readonly id=\"txt_cotizacion_materiales_totalitem2\" name=\"txt_cotizacion_materiales_totalitem2\" /></td>";                
                salida += "</tr>";
                salida += "<tr>";                
                salida += "<td></td>";                                
                salida += "<td colspan=\"3\"><input style=\"width:260px; text-align: left\" type=\"text\" maxlength=\"45\" id=\"txt_cotizacion_materiales_descripcion3\" name=\"txt_cotizacion_materiales_descripcion3\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"6\" id=\"txt_cotizacion_materiales_cantidad3\" name=\"txt_cotizacion_materiales_cantidad3\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(3)\" /></td>";
                salida += "<td><input style=\"width:110px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" id=\"txt_cotizacion_materiales_preciounitario3\" name=\"txt_cotizacion_materiales_preciounitario3\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(3)\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" readonly id=\"txt_cotizacion_materiales_totalitem3\" name=\"txt_cotizacion_materiales_totalitem3\" /></td>";                
                salida += "</tr>";
                salida += "<tr>";                
                salida += "<td></td>";                                
                salida += "<td colspan=\"3\"><input style=\"width:260px; text-align: left\" type=\"text\" maxlength=\"45\" id=\"txt_cotizacion_materiales_descripcion4\" name=\"txt_cotizacion_materiales_descripcion4\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"6\" id=\"txt_cotizacion_materiales_cantidad4\" name=\"txt_cotizacion_materiales_cantidad4\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(4)\" /></td>";
                salida += "<td><input style=\"width:110px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" id=\"txt_cotizacion_materiales_preciounitario4\" name=\"txt_cotizacion_materiales_preciounitario4\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(4)\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" readonly id=\"txt_cotizacion_materiales_totalitem4\" name=\"txt_cotizacion_materiales_totalitem4\" /></td>";                
                salida += "</tr>";
                salida += "<tr>";                
                salida += "<td></td>";                                
                salida += "<td colspan=\"3\"><input style=\"width:260px; text-align: left\" type=\"text\" maxlength=\"45\" id=\"txt_cotizacion_materiales_descripcion5\" name=\"txt_cotizacion_materiales_descripcion5\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"6\" id=\"txt_cotizacion_materiales_cantidad5\" name=\"txt_cotizacion_materiales_cantidad5\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(5)\" /></td>";
                salida += "<td><input style=\"width:110px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" id=\"txt_cotizacion_materiales_preciounitario5\" name=\"txt_cotizacion_materiales_preciounitario5\" onfocus=\"limpiaZeros(this);quitarSeparadoII(this);\" onblur=\"seteaZeros(this);separadorDeMilesII(this);\" onkeypress=\"return validarSiNumero(event)\" onchange=\"calculaTotalMateriales(5)\" /></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" maxlength=\"11\" readonly id=\"txt_cotizacion_materiales_totalitem5\" name=\"txt_cotizacion_materiales_totalitem5\" /></td>";                
                salida += "</tr>";
                salida += "<tr><td>&nbsp;</td></tr>";
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Total Materiales</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_totalMateriales\" name=\"txt_cotizacion_totalMateriales\" /></td>";
                salida += "</tr>";   
                
                //INICIO: se agregan campos (%margen, utilidad, total) 02-01-2019
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>% Margen</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" id=\"txt_cotizacion_margen2\" name=\"txt_cotizacion_margen2\" onchange=\"calculaCampoTotal('materiales')\" /></td>";
                salida += "</tr>";   
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Utilidad</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_utilidad2\" name=\"txt_cotizacion_utilidad2\" /></td>";
                salida += "</tr>";  
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Total</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" value=\"0\" readonly id=\"txt_cotizacion_total_nvo_2\" name=\"txt_cotizacion_total_nvo_2\" /></td>";
                salida += "</tr>";  
                //FIN: se agregan campos (%margen, utilidad, total) 02-01-2019
                
                
                
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td colspan=\"2\"><b>Total General Pieza</b></td>";
                salida += "<td><input style=\"width:120px; text-align: right\" type=\"text\" readonly id=\"txt_cotizacion_totalGeneral\" name=\"txt_cotizacion_totalGeneral\" /></td>";
                salida += "</tr>";                 
                salida += "<tr>&nbsp;</tr>";
                salida += "<tr>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";
                salida += "<td></td>";                
                salida += "<td>";
                salida += "<input id =\"btn_AceptarProceso\" class =\"botonera\" type=\"button\" name=\"btn_AceptarProceso\" value=\"Aceptar\" onClick=\"AceptaDialogoProcesos();\" />";
                salida += "</td>";
                salida += "<td>";
                salida += "<input id =\"btn_CancelarProceso\" class =\"botonera\" type=\"button\" name=\"btn_CancelarProceso\" value=\"Cancelar\" onClick=\"CancelaDialogoProcesos();\" />";
                salida += "</td>";
                salida += "</tr>";                
                salida += "</tbody>";
                salida += "</table>";
                out.println(salida);
            }catch(Exception e){
                //_connMy.rollback();
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

