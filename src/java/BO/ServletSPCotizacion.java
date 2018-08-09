/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BO;


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
import java.util.Arrays;
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
@WebServlet(name = "ServletSPCotizacion", urlPatterns = {"/ServletSPCotizacion"})
public class ServletSPCotizacion extends HttpServlet {

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
            
            String numeroCotiza=request.getParameter("txt_cotizacion_numero").isEmpty()? "0":request.getParameter("txt_cotizacion_numero");
            String fechaEmision= request.getParameter("txt_cotizacion_fecha");
            //String rutEje="0";
            String rutEje=request.getParameter("txt_cotizacion_ruteje");
            String rutCli=request.getParameter("txt_cotizacion_rutcli");
            String moneda=request.getParameter("txt_cotizacion_moneda");
            String total="0";//request.getParameter("txt_cotizacion_total").isEmpty()? "0":request.getParameter("txt_cotizacion_total");
            String presupuesto=request.getParameter("select_cotizacion_presupuesto_valido");
            String plazoEntrego=request.getParameter("select_cotizacion_plazo_entrega");
            String condicionPago=request.getParameter("select_cotizacion_condicion_pago");
            String diasHabiles= request.getParameter("txt_cotizacion_dias_habiles");
            if(diasHabiles == null){
                diasHabiles = "0";
            }
            String estadoCliente=request.getParameter("select_cotizacion_estado_cliente");
            String nombreContactoComercial=request.getParameter("txt_cotizacion_contacto_nombre");
            String emailContactoComercial=request.getParameter("txt_cotizacion_contacto_email");
            String valorUF=request.getParameter("txt_cotizacion_valorUF");
            String ocAprobacion=request.getParameter("select_cotizacion_oc");
            String totalProcesos=request.getParameter("select_cotizacion_total_procesos").replace(".", "");
            String totalMateriales=request.getParameter("select_cotizacion_total_materiales").replace(".", "");
            String totalNeto=request.getParameter("txt_cotizacion_total_neto").replace(".", "");
            String iva=request.getParameter("txt_cotizacion_iva").replace(".", "");
            String totalBruto=request.getParameter("txt_cotizacion_total_bruto").replace(".", "");
            String vendedor=request.getParameter("txt_cotizacion_emitida_por");
            if(vendedor.trim().equals("") || vendedor == null){
                vendedor = "0-0";
            }
            String fonos=request.getParameter("txt_cotizacion_contacto_fonos");
            String sequencia=request.getParameter("sequencia").equals("undefined")?"0":request.getParameter("sequencia");
            String desde=request.getParameter("fecha_desde");
            String hasta=request.getParameter("fecha_hasta");
 
            rutCli=rutCli.contains("-")?rutCli.substring(0,rutCli.indexOf("-")):rutCli;
            String estado = request.getParameter("txt_cotizacion_estado").equals("X_X")?"":request.getParameter("txt_cotizacion_estado");
            String detalle_unico_cliente=request.getParameter("detalle_unico_cliente");
            try{
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion")); 
                CallableStatement sp_usu=null;
                
                try{
                    sp_usu = _connMy.prepareCall("{call sp_historial(?,?,?,?)}");
                    sp_usu.setString(1,opcion);
                    sp_usu.setInt(2,Integer.parseInt(sequencia));
                    sp_usu.setString(3,estado);
                    sp_usu.setString(4, (String) s.getAttribute("rut"));
                    sp_usu.execute();
                }catch(NumberFormatException | SQLException e){
                    e.printStackTrace();
                }
                
                Date sqlDate = new Date(formato.parse(fechaEmision).getTime());                
                sp_usu = _connMy.prepareCall("{call sp_cotizacion(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                sp_usu.setString(1,opcion);
                sp_usu.setInt(2,Integer.parseInt(numeroCotiza));
                sp_usu.setDate(3,sqlDate);
                sp_usu.setString(4,estado);
                sp_usu.setInt(5,Integer.parseInt(vendedor.split("-")[0]));
                sp_usu.setInt(6,Integer.parseInt(rutCli));
                sp_usu.setString(7,moneda);
                sp_usu.setString(8,presupuesto);
                sp_usu.setString(9,plazoEntrego);
                sp_usu.setString(10,condicionPago);
                sp_usu.setInt(11,Integer.parseInt(diasHabiles));
                sp_usu.setInt(12,Integer.parseInt(total));
                sp_usu.setString(13,estadoCliente);
                sp_usu.setString(14,nombreContactoComercial);
                sp_usu.setString(15,emailContactoComercial);
                sp_usu.setString(16,valorUF);
                sp_usu.setString(17,ocAprobacion);
                sp_usu.setString(18,totalProcesos);
                sp_usu.setString(19,totalMateriales);
                sp_usu.setString(20,totalNeto);
                sp_usu.setString(21,iva);
                sp_usu.setString(22,totalBruto);       
                sp_usu.setString(23,vendedor); 
                sp_usu.setString(24,fonos); 
                sp_usu.setInt(25,Integer.parseInt(sequencia));
                sp_usu.setString(26,desde);
                sp_usu.setString(27,hasta);
                sp_usu.setString("in_detalle_unico_cliente", detalle_unico_cliente);
                sp_usu.registerOutParameter(1, Types.VARCHAR);
                
                sp_usu.execute();
                String valorSalida = sp_usu.getString(1);    

                if(valorSalida.equalsIgnoreCase("error ejecucion"))
                {
                    out.println("Ya existe");
                }
                
                if(!opcion.contains("select")){
                    out.println(valorSalida);
                }
                
                final ResultSet rs = sp_usu.getResultSet();
                String salida = "";
                if(opcion.equals("select")){
                    while(rs.next())
                    {
                        salida += rs.getString("numero_cotizacion")+"|";   
                        salida += rs.getString("fecha_emision")+"|";   
                        salida += rs.getString("vendedor")+"|"; 
                        salida += rs.getString("presupuesto_valido")+"|"; 
                        salida += rs.getString("plazo_entrega")+"|"; 
                        salida += rs.getString("condiciones_pago")+"|"; 
                        salida += rs.getString("rut_cli")+"|"; 
                        salida += rs.getString("razon_social")+"|"; 
                        salida += rs.getString("dias_habiles")+"|";
                        salida += rs.getString("moneda")+"|";
                        salida += rs.getString("estado")+"|";
                        salida += rs.getString("estado_cliente")+"|";
                        salida += rs.getString("nombre_contacto_comercial")+"|";
                        salida += rs.getString("email_contacto_comercial")+"|";
                        salida += rs.getString("total_procesos")+"|";
                        salida += rs.getString("total_materiales")+"|";
                        salida += rs.getString("total_neto")+"|";
                        salida += rs.getString("iva")+"|";
                        salida += rs.getString("total_bruto")+"|";                        
                        salida += rs.getString("fonos")+"|";  
                        salida += rs.getString("oc_aprobacion")+"|";  
                        if(rs.getString("detalle_unico_cliente") == null || rs.getString("detalle_unico_cliente").trim().equals("")){
                            salida += ""+"|";
                        }else{
                            salida += rs.getString("detalle_unico_cliente")+"|";
                        }
                        
                    }
                    
                }else if(opcion.equals("select_all")){
                    
                    String cla = "";
                    int cont =0; 
                    String numeroOT;
                    while(rs.next())
                    {
                        if(cont % 2 == 0)
                        {
                            cla = "alt";
                        }else
                        {  
                            cla = "";

                        }
                        
                        numeroOT = rs.getString("numero_OT");
                        if(numeroOT == null)
                            numeroOT = "-";
                        else{
                            //Separamos el numeroOT por el pipe (el -1, es para que revise todo el string)
                            //Dejándolo en un array de Strings
                            String[] array = numeroOT.split("\\|", -1);
                            //En la posicion 0 nos deja el valor que buscamos, asi que ese lo asignamos a la variable del numeroOT
                            numeroOT = array[0];
                        }
                        
                        salida += "<tr id='filaTablaActComercial"+cont+"' class='"+cla+"'>";
                        salida +="<td>";
                        salida +="<a href=\"javascript: onclick=ModificaActComercial("+cont+","+rs.getString("ots")+","+rs.getString("numero_cotizacion")+",'"+rs.getString("estado")+"')\"> >></a>";
//                        salida +="<a href=\"javascript: onclick=ModificaActComercial("+cont+")\"> >></a>";
                        salida +="<input type=\"hidden\" value=\"0\" id=\"habilitaActCom\" name=\"habilitaActCom\" />";
                        salida +="<input type=\"hidden\" value=\"\" id=\"corrCotiza\" />";
                        salida +="<input type=\"hidden\" value=\"\" id=\"nroOts\" />";
                        salida +="<input type=\"hidden\" value=\"\" id=\"nroCoti\" />";
                        salida +="<input type=\"hidden\" value=\"\" id=\"estadoCoti\" />";
                        salida +="</td>";
                        salida +="<td id=\"num_cotizacion"+cont+"\">"+rs.getString("numero_cotizacion")+"</td>";
                        salida +="<td id=\"emititda_por"+cont+"\">"+ rs.getString("vendedor")+"</td>";
                        salida +="<td id=\"fecha_emision"+cont+"\">"+rs.getString("fecha_emision")+"</td>";
                        salida +="<td id=\"rut_cli"+cont+"\">"+ rs.getString("rut_cli")+"</td>";
                        salida +="<td id=\"razon_cli"+cont+"\">"+rs.getString("razon_social")+"</td>";
                        salida +="<td id=\"estado"+cont+"\">"+rs.getString("estado")+"</td>";
                        salida +="<td id=\"num_ot"+cont+"\">"+numeroOT+"</td>";
                        salida +="<td id=\"total"+cont+"\" class=\"columnaTotal\">$ "+rs.getString("total_bruto")+"</td>";
                        salida +="<td style=\"display: none\" id=\"secuencia"+cont+"\">"+rs.getString("secuencia")+"</td>";
                        salida +="</tr>";
                        
                        cont ++;                                    
                    }
                    
                }
                out.println(salida);
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
