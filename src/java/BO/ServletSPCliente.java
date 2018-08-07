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
 * @author Sistemas-ltda
 */
@WebServlet(name = "ServletSPCliente", urlPatterns = {"/ServletSPCliente"})
public class ServletSPCliente extends HttpServlet {

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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession s = request.getSession();
            Connection _connMy = null;
            
            String variable = request.getParameter("mod");
            
            String rutCli=request.getParameter("txt_cliente_rut");
             
            String razonSocial=request.getParameter("txt_cliente_nombre");
            String contacto=request.getParameter("txt_cliente_contacto");
            String direccion=request.getParameter("txt_cliente_direccion");
            String vendedor=request.getParameter("txt_cliente_vendedor");
            String estado=request.getParameter("txt_cliente_estado");
            String codigo=request.getParameter("txt_cliente_codigo");
            String sigla=request.getParameter("txt_sigla_cliente");
            String comuna=request.getParameter("txt_cliente_comuna");
            String ciudad=request.getParameter("txt_cliente_ciudad");
            String fono1=request.getParameter("txt_cliente_fono1");
            String fono2=request.getParameter("txt_cliente_fono2");
            String fax=request.getParameter("txt_cliente_fax");
            String rubro=request.getParameter("txt_cliente_rubro");
            String casilla=request.getParameter("txt_cliente_casilla");
            String nombreContactoComercial=request.getParameter("txt_cliente_nombreContactoComercial");
            String cargoContactoComercial=request.getParameter("txt_cliente_cargoContactoComercial");    
            String celularContactoComercial=request.getParameter("txt_cliente_celularContactoComercial");    
            String fijoContactoComercial=request.getParameter("txt_cliente_fijoContactoComercial");    
            String emailContactoComercial=request.getParameter("txt_cliente_emailContactoComercial");    
            String nombreContactoContable=request.getParameter("txt_cliente_nombreContactoContable");
            String cargoContactoContable=request.getParameter("txt_cliente_cargoContactoContable");    
            String celularContactoContable=request.getParameter("txt_cliente_celularContactoContable");    
            String fijoContactoContable=request.getParameter("txt_cliente_fijoContactoContable");    
            String emailContactoContable=request.getParameter("txt_cliente_emailContactoContable");              
            String dv="";
            try{
                dv=rutCli.substring(rutCli.indexOf("-")+1,rutCli.length());
                rutCli=rutCli.substring(0,rutCli.indexOf("-"));
            }catch(Exception e){
                dv="0";
            }
            estado=estado.replace("NaN", "");
            String estadoSend=estado.equals("X_X")?"":estado;
            
            try{
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));             
                CallableStatement sp_usu = _connMy.prepareCall("{call sp_mae_cliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                sp_usu.setString(1,variable);
                sp_usu.setString(2, "");
                sp_usu.setString(3,"");
                sp_usu.setString(4,vendedor);
                sp_usu.setString(5, rutCli);
                sp_usu.setString(6,razonSocial);               
                sp_usu.setString(7,contacto);
                sp_usu.setString(8,direccion);                
                sp_usu.setString(9,estadoSend);
                sp_usu.setString(10,dv);
                sp_usu.setString(11,codigo);
                sp_usu.setString(12,sigla);
                sp_usu.setString(13,comuna);
                sp_usu.setString(14,ciudad);
                sp_usu.setString(15,fono1);
                sp_usu.setString(16,fono2);
                sp_usu.setString(17,fax);
                sp_usu.setString(18,rubro);
                sp_usu.setString(19,casilla);
                sp_usu.setString(20,nombreContactoComercial);
                sp_usu.setString(21,cargoContactoComercial);
                sp_usu.setString(22,celularContactoComercial);
                sp_usu.setString(23,fijoContactoComercial);
                sp_usu.setString(24,emailContactoComercial);
                sp_usu.setString(25,nombreContactoContable);
                sp_usu.setString(26,cargoContactoContable);
                sp_usu.setString(27,celularContactoContable);
                sp_usu.setString(28,fijoContactoContable);
                sp_usu.setString(29,emailContactoContable);
                sp_usu.registerOutParameter(1, Types.VARCHAR);
                
                sp_usu.execute();  
                String valorSalida = sp_usu.getString(1);                
                
                final ResultSet rs = sp_usu.getResultSet();
                String salida="";
                int cont = 0;
                String estilo = "";
                if(variable.equals("select_all")){
                    while(rs.next()){
                        if(!estado.isEmpty()){
                            if(cont % 2 == 0)
                            {                    
                                estilo = "alt";
                            }else
                            {  
                                estilo = "";                    
                            }
                            salida += "<tr id='filaTablaCliente"+cont+"' class='"+estilo+"'>";

                            salida += "<td><a id=\"seleccion"+cont+"\" href=\"javascript: onclick=ModificaCliente("+cont+")\"> >></a>\n" +
                                        " <input type=\"hidden\" value=\"0\" id=\"habilitaCliente\" name=\"habilitaCliente\" />\n" +
                                        "<input type=\"hidden\" value=\"\" id=\"rut\" /></td> ";
                            salida += "<td id =\"Cliente_rut"+cont+"\">"+rs.getString("rut")+"</td>";
                            salida += "<td id =\"Cliente_Nombre"+cont+"\">"+rs.getString("nombre")+"</td>"; 
                            salida += "<td id =\"Cliente_Contacto"+cont+"\">"+rs.getString("contacto")+"</td>"; 
                            salida += "<td id =\"Cliente_Direccion"+cont+"\">"+rs.getString("direccion")+"</td>"; 
                            salida += "<td id =\"Cliente_Vendedor"+cont+"\">"+rs.getString("vendedor")+"</td>";   
                            salida += "<td id =\"Cliente_Estado"+cont+"\">"+rs.getString("estado")+"</td>";   


                            salida += "</tr>";
                            cont++;
                            salida += "<input type=\"hidden\" id=\"contador\" value=\""+cont+"\"/>";
                        }else{
                            salida+="<option value='"+rs.getString("rut")+"'>"+rs.getString("rut_pad")+rs.getString("nombre")+rs.getString("estado")+rs.getString("nombre_contacto_comercial")+rs.getString("email_contacto_comercial")+rs.getString("vendedor")+rs.getString("fonos")+"</option>";
                            
                        }
                    }
                    out.println(salida);
                }
                else {
                    if(variable.equals("insert")){
                        salida=valorSalida;
                        out.println("OK");
                    }
                    else {
                        if (variable.equals("Consultar")) {
                            while(rs.next())
                            {
                                salida += "<tr>";
                                salida += "<td colspan=\"2\">";
                                salida += "<div class=\"formularioIngresar\">";
                                salida += "<form>";
                                salida += "<table class=\"tblCliActualiza\">";
                                salida += "<tr>";
                                salida += "<td colspan = 2><big><b>INFORMACION GENERAL CLIENTE</b></big><hr/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Rut Cliente: </td>";
                                salida += "<td><input readonly maxlength=\"11\" readonly type=\"text\" name=\"txt_cliente_rut\" id=\"txt_cliente_rut\" value=" + rutCli + " /></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                        	salida += "<td>Raz&oacute;n Social: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_nombre\" name=\"txt_cliente_nombre\" maxlength=\"40\" value=\"" + rs.getString("razon_social") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Giro: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_giro\" name=\"txt_cliente_giro\" maxlength=\"50\" value=\"" + rs.getString("rubro") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Direcci&oacute;n: </td>";
                                salida += "<td><input class=\"direccion\" type=\"text\" readonly id=\"txt_cliente_direccion\" name=\"txt_cliente_direccion\" maxlength=\"50\" value=\"" + rs.getString("direccion") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Comuna: </td> ";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_comuna\" name=\"txt_cliente_comuna\" value=\"" + rs.getString("comuna") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Ciudad: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_ciudad\" name=\"txt_cliente_ciudad\" maxlength=\"50\" value=\"" + rs.getString("ciudad") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Estado de la Empresa: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_estado\" name=\"txt_cliente_estado\" maxlength=\"50\" value=\"" + rs.getString("estado") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Vendedor: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_vendedor\" name=\"txt_cliente_vendedor\" maxlength=\"50\" value=\"" + rs.getString("vendedor") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td colspan = 2><big><b>CONTACTO COMERCIAL</b></big><hr/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Nombre Completo: </td>";
                            	salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_nombreContactoComercial\" name=\"txt_cliente_nombreContactoComercial\" maxlength=100\" value=\"" + rs.getString("nombre_Contacto_Comercial") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Cargo: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_cargoContactoComercial\" name=\"txt_cliente_cargoContactoComercial\" maxlength=\"100\" value=\"" + rs.getString("cargo_Contacto_Comercial") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>N° Celular: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_celularContactoComercial\" name=\"txt_cliente_celularContactoComercial\" maxlength=\"10\" value=\"" + rs.getString("celular_Contacto_Comercial") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>N° Fijo: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_fijoContactoComercial\" name=\"txt_cliente_fijoContactoComercial\" maxlength=\"10\" value=\"" + rs.getString("fijo_Contacto_Comercial") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>eMail: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_emailContactoComercial\" name=\"txt_cliente_emailContactoComercial\" maxlength=\"100\" value=\"" + rs.getString("email_Contacto_Comercial") + "\"/></td>";
                                salida += "</td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td colspan = 2><big><b>CONTACTO CONTABLE</b></big><hr/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>Nombre Completo: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_nombreContactoContable\" name=\"txt_cliente_nombreContactoContable\" maxlength=\"100\" value=\"" + rs.getString("nombre_Contacto_Contable") + "\"/></td>";
                                salida += "</tr>";
                            	salida += "<tr>";
                                salida += "<td>Cargo: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_cargoContactoContable\" name=\"txt_cliente_cargoContactoContable\" maxlength=\"100\" value=\"" + rs.getString("cargo_Contacto_Contable") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>N° Celular: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_celularContactoContable\" name=\"txt_cliente_celularContactoContable\" maxlength=\"10\" value=\"" + rs.getString("celular_Contacto_Contable") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>N° Fijo: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_fijoContactoContable\" name=\"txt_cliente_fijoContactoContable\" maxlength=\"10\" value=\"" + rs.getString("fijo_Contacto_Contable") + "\"/></td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>eMail: </td>";
                                salida += "<td><input class=\"contacto\" type=\"text\" readonly id=\"txt_cliente_emailContactoContable\" name=\"txt_cliente_emailContactoContable\" maxlength=\"100\" value=\"" + rs.getString("email_Contacto_Contable") + "\"/></td>";
                                salida += "</td>";
                                salida += "</tr>";
                                salida += "</table>";
                                salida += "</form>";
                                salida += "</div>";
                                salida += "</td>";
                                salida += "</tr>";
                                salida += "<tr>";
                                salida += "<td>";
                                salida += "<input id =\"btn_Cliente_Salir\" class =\"botonera\" type=\"button\" name=\"btn_Cliente_Salir\" value=\"Salir\" onClick=\"SalirCliente();\" />";
                                salida += "</td>";
                                salida += "</tr>";
                            }
                            out.println(salida);
                        }
                        else
                            if(variable.equals("select_clean")){
                                while(rs.next()){
                                    if(!estado.isEmpty()){
                                        if(cont % 2 == 0)
                                        {                    
                                            estilo = "alt";
                                        }else
                                        {  
                                            estilo = "";                    
                                        }
                                        salida += "<tr id='filaTablaCliente"+cont+"' class='"+estilo+"'>";

                                        salida += "<td><a id=\"seleccion"+cont+"\" href=\"javascript: onclick=ModificaCliente("+cont+")\"> >></a>\n" +
                                                    " <input type=\"hidden\" value=\"0\" id=\"habilitaCliente\" name=\"habilitaCliente\" />\n" +
                                                    "<input type=\"hidden\" value=\"\" id=\"rut\" /></td> ";
                                        salida += "<td id =\"Cliente_rut"+cont+"\">"+rs.getString("rut")+"</td>";
                                        salida += "<td id =\"Cliente_Nombre"+cont+"\">"+rs.getString("nombre")+"</td>"; 
                                        salida += "<td id =\"Cliente_Contacto"+cont+"\">"+rs.getString("nombre_Contacto_Contable")+"</td>"; 
                                        salida += "<td id =\"Cliente_Direccion"+cont+"\">"+rs.getString("direccion")+"</td>"; 
                                        salida += "<td id =\"Cliente_Vendedor"+cont+"\">"+rs.getString("vendedor")+"</td>";   
                                        salida += "<td id =\"Cliente_Estado"+cont+"\">"+rs.getString("estado")+"</td>";   


                                        salida += "</tr>";
                                        cont++;
                                        salida += "<input type=\"hidden\" id=\"contador\" value=\""+cont+"\"/>";
                                    }else{
                                        salida+="<option value='"+rs.getString("rut")+"'>"+rs.getString("rut_pad")+rs.getString("nombre")+rs.getString("estado")+rs.getString("nombre_contacto_comercial")+rs.getString("email_contacto_comercial")+rs.getString("vendedor")+rs.getString("fonos")+"</option>";

                                    }
                                }
                                out.println(salida);
                            }
                    }
                }
                
            }
            catch (SQLException ex) {
                Logger.getLogger(ServletSPCliente.class.getName()).log(Level.SEVERE, null, ex);
                if(ex.getErrorCode() == 1062)
                {
                    out.println(ex.getErrorCode());                    
                }else
                {
                    out.println("0");                    
                }
            } catch (Exception ex) {
                out.println("1");
                Logger.getLogger(ServletSPCliente.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    _connMy.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServletSPCliente.class.getName()).log(Level.SEVERE, null, ex);
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
