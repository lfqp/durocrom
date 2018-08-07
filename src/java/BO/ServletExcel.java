/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import DAL.conexionBD;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Blas Muñoz
 */
@WebServlet(name = "ServletExcel", urlPatterns = {"/ServletExcel"})
public class ServletExcel extends HttpServlet {

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
        Connection _connMy = null;

        String opcion = request.getParameter("slt_Informe");
        String fecha_ini = request.getParameter("txt_filtroComercial_ingreso");
        String fecha_fin = request.getParameter("txt_filtroComercial_final");

        String year_ini = fecha_ini.substring(6);
        String mes_ini = fecha_ini.substring(3, 5);
        String dia_ini = fecha_ini.substring(0, 2);

        String year_fin = fecha_fin.substring(6);
        String mes_fin = fecha_fin.substring(3, 5);
        String dia_fin = fecha_fin.substring(0, 2);

        String fecha_inisql = year_ini + "-" + mes_ini + "-" + dia_ini;
        String fecha_finsql = year_fin + "-" + mes_fin + "-" + dia_fin;
        int totalProceso = 0;
        try {
            _connMy = conexionBD.Conectar("");
            CallableStatement sp_usu;
            sp_usu = _connMy.prepareCall("{call sp_cotizacion_excel(?,?,?)}");
            sp_usu.setString(1, opcion);
            sp_usu.setString(2, fecha_inisql);
            sp_usu.setString(3, fecha_finsql);
            sp_usu.execute();
            final ResultSet rs = sp_usu.getResultSet();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String salida = "";

            salida += "<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>";
            salida += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
            salida += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
            salida += "<head>";
            salida += "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
            salida += "    <style type=\"text/css\">";
            salida += "        .grillaExcel{";
            salida += "            font: normal 14px/150% Calibri, Helvetica, sans-serif;";
            salida += "        }   ";
            salida += "        .grillaExcel thead th{";
            salida += "            border-left: 1px solid;";
//            salida += "            border-bottom: 1px solid;";
            salida += "        }";
            salida += "        .grillaExcel tbody tr td{             ";
            salida += "            border: 1px solid;";
            salida += "        }";
            salida += "        .grillaExcel thead tr td{             ";
            salida += "            border: 1px solid;";
            salida += "        }        ";
            salida += "    </style>    ";
            salida += "    <title>JSP Page</title>";
            salida += "    </head>";
            salida += "    <body>";

            if (opcion.equals("clientes")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"Clientes"+fecha_ini+"-"+fecha_fin+".xls\"");
                salida += "        <table class=\"grillaExcel\">";
                salida += "            <thead> ";
                salida += "                <tr>";
                salida += "                    <td colspan=\"2\" style=\"font-size: 18px; font-weight: bold\">Maestro de clientes</td>";
                salida += "                </tr>";
                salida += "                <tr>";
                salida += "                    <td style=\"font-size: 16px;\">Fecha</td>";
                salida += "                    <td style=\"font-size: 16px;\">" + dateFormat.format(date) + "</td>";
                salida += "                </tr>";
                salida += "                <tr></tr>";
                salida += "                <tr>";
                salida += "                    <td colspan=\"8\" align=\"center\">Información general</td>";
                salida += "                    <td colspan=\"5\" align=\"center\">Contacto comercial</td>";
                salida += "                    <td colspan=\"5\" align=\"center\">Contacto contable</td>";
                salida += "                </tr>";
                salida += "                <th>Rut cliente</th>";
                salida += "                <th>Razón social</th> ";
                salida += "                <th>giro</th>";
                salida += "                <th>Dirección</th>";
                salida += "                <th>Comuna</th>     ";
                salida += "                <th>Ciudad</th>       ";
                salida += "                <th>Estado de la empresa</th>";
                salida += "                <th>Vendedor</th>";
                salida += "                <th>Nombre completo</th>";
                salida += "                <th>Cargo</th>";
                salida += "                <th>N° celular</th>";
                salida += "                <th>N° fijo</th>";
                salida += "                <th>eMail</th>    ";
                salida += "                <th>Nombre completo</th>";
                salida += "                <th>Cargo</th>";
                salida += "                <th>N° celular</th>";
                salida += "                <th>N° fijo</th>";
                salida += "                <th>eMail</th>    ";
                salida += "            </thead>";
                salida += "            <tbody>";
                while (rs.next()) {
                    salida += "<tr>";
                    salida += "<td>" + rs.getString("rut_cliente") + "</td>";
                    salida += "<td>" + rs.getString("razon_social") + "</td>";
                    salida += "<td>" + rs.getString("rubro") + "</td>";
                    salida += "<td>" + rs.getString("direccion") + "</td>";
                    salida += "<td>" + rs.getString("comuna") + "</td>";
                    salida += "<td>" + rs.getString("ciudad") + "</td>";
                    salida += "<td>" + rs.getString("estado") + "</td>";
                    salida += "<td>" + rs.getString("nombre_user") + "</td>";
                    salida += "<td>" + rs.getString("nombre_contacto_comercial") + "</td>";
                    salida += "<td>" + rs.getString("cargo_contacto_comercial") + "</td>";
                    salida += "<td>" + rs.getString("celular_contacto_comercial") + "</td>";
                    salida += "<td>" + rs.getString("fijo_contacto_comercial") + "</td>";
                    salida += "<td>" + rs.getString("email_contacto_comercial") + "</td>";
                    salida += "<td>" + rs.getString("nombre_contacto_contable") + "</td>";
                    salida += "<td>" + rs.getString("cargo_contacto_contable") + "</td>";
                    salida += "<td>" + rs.getString("celular_contacto_contable") + "</td>";
                    salida += "<td>" + rs.getString("fijo_contacto_contable") + "</td>";
                    salida += "<td>" + rs.getString("email_contacto_contable") + "</td>";
                    salida += "</tr>";
                }
            }

            if (opcion.equals("cotizacionNew")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"CotizacionNew"+fecha_ini+"-"+fecha_fin+".xls\"");
                salida += "        <table class=\"grillaExcel\">";
                salida += "            <thead> ";
                salida += "                <tr>";
                salida += "                <td colspan=\"2\" style=\"font-size: 18px; font-weight: bold\">Control cotización</td>";
                salida += "                </tr>";
                salida += "                <tr>";
                salida += "                <td style=\"font-size: 16px;\">Fecha</td>";
                salida += "                <td style=\"font-size: 16px;\">" + dateFormat.format(date) + "</td>";
                salida += "                </tr>";
                salida += "                <tr></tr>";
                salida += "                <tr>";
                salida += "                <td colspan=\"5\" align=\"center\">Información general cotización</td>";
                salida += "                <td colspan=\"2\" align=\"center\">Estados cotización</td>";
                salida += "                <td colspan=\"12\" align=\"center\">Descripción pieza</td>";
                salida += "                <td colspan=\"13\" align=\"center\">Descripción procesos</td>";
                salida += "                <td colspan=\"5\" align=\"center\">Montos</td>";
                salida += "                <td colspan=\"9\" align=\"center\">Orden Taller</td>";
                salida += "                </tr>";
                salida += "                <th>Rut cliente</th>";
                salida += "                <th>Nombre cliente</th>";
                salida += "                <th>N° cotización</th>";                
                salida += "                <th>Fecha de emisión</th>";
                salida += "                <th>D&iacute;as H&aacute;biles</th>";
                salida += "                <th>Emitida por:</th>         ";
                salida += "                <th>Fecha Aprobaci&oacute;</th>";
                salida += "                <th>OC aprobación</th>";
                salida += "                <th>Pieza</th>         ";
                salida += "                <th>Nuevo/Usado</th>";
                salida += "                <th>Material Base</th>";
                salida += "                <th>Trabajo abreviado</th>";
                salida += "                <th>Diametro</th>                ";
                salida += "                <th>Largo</th>         ";
                salida += "                <th>Ancho</th>";
                salida += "                <th>Superficie</th>";
                salida += "                <th>Superficie total</th>";
                salida += "                <th>Corriente</th>";
                salida += "                <th>Oservaciones</th>";
                salida += "                <th>Rectificado previo</th>         ";
                salida += "                <th>Cromado</th>";
                salida += "                <th>Rectificado final</th>";
                salida += "                <th>Bandeo</th>";
                salida += "                <th>Bruñido</th>";
                salida += "                <th>Torno</th>                ";
                salida += "                <th>Fresa</th>         ";
                salida += "                <th>Soldadura</th>";
                salida += "                <th>Cambio de sellos</th>";
                salida += "                <th>Descromado</th>";
                salida += "                <th>Horas hombre</th>";
                salida += "                <th>Tratamiento termico</th>";
                salida += "                <th>Proceso Nuevo</th>";
                salida += "                <th>Total procesos</th>";
                salida += "                <th>Total materiales</th>";
                salida += "                <th>Total general pieza (neto)</th>";
                salida += "                <th>Iva</th>";
                salida += "                <th>Total bruto</th>";
                salida += "                <th>N° OT</th>";
                salida += "                <th>Fecha Emision OT</th>";
                salida += "                <th>Estado OT</th>";
                salida += "                <th>Condici&oacute;n OT</th>";
                salida += "                <th>D&iacute;as Restantes</th>";
                salida += "                <th>Suma Rebaja</th>";
                salida += "                <th>Saldo</th>";
                salida += "                <th>Fecha Ultima Rebaja</th>";
                salida += "                <th>Fecha Prometida</th>";
                salida += "            </thead>";
                salida += "            <tbody>";

                while (rs.next()) {
                    salida += "<tr>";
                    salida += "<td>" + rs.getString("rut_cli") + "</td>";
                    salida += "<td>" + rs.getString("razon_social") + "</td>";
                    salida += "<td>" + rs.getString("numero_cotizacion") + "</td>";
                    salida += "<td>" + rs.getString("fecha_emision") + "</td>";
                    salida += "<td>" + rs.getString("dias_habiles") + "</td>";
                    salida += "<td>" + rs.getString("nombre_user") + "</td>";                    
                    salida += "<td>" + rs.getString("fechaAprob") + "</td>";                                        
                    salida += "<td>" + rs.getString("oc_aprobacion") + "</td>";
                    salida += "<td>" + rs.getString("desc_pieza") + "</td>";
                    salida += "<td>" + rs.getString("nuevo_usado") + "</td>";
                    salida += "<td>" + rs.getString("material_base") + "</td>";
                    salida += "<td>" + rs.getString("trabajo_abreviado") + "</td>";
                    salida += "<td>" + rs.getString("diametro").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("largo").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("ancho").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("superficie").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("superficie_total").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("corriente").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("comentarios") + "</td>";
                    
                    String json_totalproc = rs.getString("json_totalproc");
                    String json_totalproc2 = rs.getString("json_totalproc");
                    String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                    for (int x = 0; x < arr_json_totalproc.length; x++){ 
                        if (arr_json_totalproc[x].equals("0")) {
                            salida += "<td>Falso</td>";
                        } else {
                            salida += "<td>Verdadero</td>";
                        }
                    }                    
                    String[] arr_json_totalproc2 = json_totalproc2.replace("\"","").replace("[","").replace("]","").split(",");
                    totalProceso = 0;
                    for (int x = 0; x < arr_json_totalproc2.length; x++){ 
                        String hola = arr_json_totalproc2[x].replace(".","");                        
                        totalProceso = totalProceso + Integer.parseInt(hola);                       
                    }                    
                    salida += "<td>" + totalProceso + "</td>";
                    salida += "<td>" + rs.getString("total_materiales") + "</td>";
                    salida += "<td>" + rs.getString("total_pieza") + "</td>";
                    salida += "<td>" + rs.getString("iva") + "</td>";
                    salida += "<td>" + rs.getString("total_bruto") + "</td>";
                    
                    salida += "<td>" + rs.getString("numero_ordentaller") + "</td>";
                    salida += "<td>" + rs.getString("fecha_emiOT") + "</td>";
                    salida += "<td>" + rs.getString("estadoOT") + "</td>";
                    salida += "<td>" + rs.getString("condicionOT") + "</td>";
                    salida += "<td>" + rs.getString("dias_restantesOT") + "</td>";
                    String cant = "";                    
                    if(rs.getString("numero_ordentaller").equals("") || rs.getString("numero_ordentaller").equals(null)) 
                    {
                        cant = "";
                    }
                    else{
                        cant = rs.getString("cantidad_rebaja");
                    }
                    salida += "<td>" + cant + "</td>";
                    salida += "<td>" + rs.getString("saldoOT") + "</td>";
                    salida += "<td>" + rs.getString("fecha_UltimaReb") + "</td>";
                    salida += "<td>" + rs.getString("fecha_PromOT") + "</td>";
                    salida += "</tr>";
                }
                salida += "<tr></tr>"; 
            }
            if (opcion.equals("cotizacion")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"Cotizacion"+fecha_ini+"-"+fecha_fin+".xls\"");
                salida += "        <table class=\"grillaExcel\">";
                salida += "            <thead> ";
                salida += "                <tr>";
                salida += "                <td colspan=\"2\" style=\"font-size: 18px; font-weight: bold\">Control cotización</td>";
                salida += "                </tr>";
                salida += "                <tr>";
                salida += "                <td style=\"font-size: 16px;\">Fecha</td>";
                salida += "                <td style=\"font-size: 16px;\">" + dateFormat.format(date) + "</td>";
                salida += "                </tr>";
                salida += "                <tr></tr>";
                salida += "                <tr>";
                salida += "                <td colspan=\"6\" align=\"center\">Información general cotización</td>";
                salida += "                <td colspan=\"2\" align=\"center\">Estados cotización</td>";
                salida += "                <td colspan=\"12\" align=\"center\">Descripción pieza</td>";
                salida += "                <td colspan=\"13\" align=\"center\">Descripción procesos</td>";
                salida += "                <td colspan=\"5\" align=\"center\">Montos</td>";
                salida += "                </tr>";
                salida += "                <th>Rut cliente</th>";
                salida += "                <th>Nombre cliente</th>";
                salida += "                <th>N° cotización</th>";
                salida += "                <th>Codigo</th> ";
                salida += "                <th>Fecha de emisión</th>";
                salida += "                <th>D&iacute;as H&aacute;biles</th>";
                salida += "                <th>Emitida por:</th>         ";
                salida += "                <th>Fecha Aprobaci&oacute;</th>";
                salida += "                <th>OC aprobación</th>";
                salida += "                <th>Pieza</th>         ";
                salida += "                <th>Nuevo/Usado</th>";
                salida += "                <th>Material Base</th>";
                salida += "                <th>Trabajo abreviado</th>";
                salida += "                <th>Diametro</th>                ";
                salida += "                <th>Largo</th>         ";
                salida += "                <th>Ancho</th>";
                salida += "                <th>Superficie</th>";
                salida += "                <th>Superficie total</th>";
                salida += "                <th>Corriente</th>";
                salida += "                <th>Oservaciones</th>";
                salida += "                <th>Rectificado previo</th>         ";
                salida += "                <th>Cromado</th>";
                salida += "                <th>Rectificado final</th>";
                salida += "                <th>Bandeo</th>";
                salida += "                <th>Bruñido</th>";
                salida += "                <th>Torno</th>                ";
                salida += "                <th>Fresa</th>         ";
                salida += "                <th>Soldadura</th>";
                salida += "                <th>Cambio de sellos</th>";
                salida += "                <th>Descromado</th>";
                salida += "                <th>Horas hombre</th>";
                salida += "                <th>Tratamiento termico</th>";
                salida += "                <th>Proceso Nuevo</th>";
                salida += "                <th>Total procesos</th>";
                salida += "                <th>Total materiales</th>";
                salida += "                <th>Total general pieza (neto)</th>";
                salida += "                <th>Iva</th>";
                salida += "                <th>Total bruto</th>                ";
                salida += "            </thead>";
                salida += "            <tbody>";

                while (rs.next()) {
                    salida += "<tr>";
                    salida += "<td>" + rs.getString("rut_cli") + "</td>";
                    salida += "<td>" + rs.getString("razon_social") + "</td>";
                    salida += "<td>" + rs.getString("numero_cotizacion") + "</td>";
                    salida += "<td>" + rs.getString("numero_cotizacion") + "|" + rs.getString("correlativo") + "|" + rs.getString("cantidad") + "</td>";
                    salida += "<td>" + rs.getString("fecha_emision") + "</td>";
                    salida += "<td>" + rs.getString("dias_habiles") + "</td>";
                    salida += "<td>" + rs.getString("nombre_user") + "</td>";                    
                    salida += "<td>" + rs.getString("fechaAprob") + "</td>";                                        
                    salida += "<td>" + rs.getString("oc_aprobacion") + "</td>";
                    salida += "<td>" + rs.getString("desc_pieza") + "</td>";
                    salida += "<td>" + rs.getString("nuevo_usado") + "</td>";
                    salida += "<td>" + rs.getString("material_base") + "</td>";
                    salida += "<td>" + rs.getString("trabajo_abreviado") + "</td>";
                    salida += "<td>" + rs.getString("diametro").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("largo").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("ancho").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("superficie").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("superficie_total").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("corriente").replace(".", ",") + "</td>";
                    salida += "<td>" + rs.getString("comentarios") + "</td>";
                    
                    String json_totalproc = rs.getString("json_totalproc");
                    String json_totalproc2 = rs.getString("json_totalproc");
                    String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                    for (int x = 0; x < arr_json_totalproc.length; x++){ 
                        if (arr_json_totalproc[x].equals("0")) {
                            salida += "<td>Falso</td>";
                        } else {
                            salida += "<td>Verdadero</td>";
                        }
                    }                    
                    String[] arr_json_totalproc2 = json_totalproc2.replace("\"","").replace("[","").replace("]","").split(",");
                    totalProceso = 0;
                    for (int x = 0; x < arr_json_totalproc2.length; x++){ 
                        String hola = arr_json_totalproc2[x].replace(".","");                        
                        totalProceso = totalProceso + Integer.parseInt(hola);                       
                    }                    
                    salida += "<td>" + totalProceso + "</td>";
                    salida += "<td>" + rs.getString("total_materiales") + "</td>";
                    salida += "<td>" + rs.getString("total_pieza") + "</td>";
                    salida += "<td>" + rs.getString("iva") + "</td>";
                    salida += "<td>" + rs.getString("total_bruto") + "</td>";
                    salida += "</tr>";
                }
                salida += "<tr></tr>"; 

            }
            salida += "            </tbody>";
            salida += "        </table>";
            salida += "    </body>";

            response.setContentType("application/vnd.ms-excel");
            
            response.getWriter().println(salida);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
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
