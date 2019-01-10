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
import java.text.SimpleDateFormat;
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
@WebServlet(name = "ServletSPCotizacionDet", urlPatterns = {"/ServletSPCotizacionDet"})
public class ServletSPCotizacionDet extends HttpServlet {

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
            String codPieza=request.getParameter("select_cotizacion_pieza");
            String descPieza=".";
            String cantidad=request.getParameter("txt_cotizacion_cantidad").isEmpty()?"0":request.getParameter("txt_cotizacion_cantidad").replace(".", "");
            String valorDM="0";//request.getParameter("txt_cotizacion_dm");
            String diametro="0";//request.getParameter("txt_cotizacion_diametro");
            
            String valorUniCrom="0";//request.getParameter("txt_cotizacion_valUniCrom");
            String valorTotalCrom="0";//request.getParameter("txt_cotizacion_totalCrom");
            String valorUnitario=request.getParameter("txt_cotizacion_valUnitario").isEmpty()?"0":request.getParameter("txt_cotizacion_valUnitario").replace(".", "");
            String totales=request.getParameter("txt_cotizacion_totales").isEmpty()?"0":request.getParameter("txt_cotizacion_totales").replace(".", "");
            String costoHoras="0";//request.getParameter("txt_cotizacion_cHora").isEmpty()?"0":request.getParameter("txt_cotizacion_cHora");
            String cantHoras="0";//request.getParameter("txt_cotizacion_cantHrs").isEmpty()?"0":request.getParameter("txt_cotizacion_cantHrs");
            //String totalHoras=String.valueOf(Integer.parseInt(costoHoras) * Integer.parseInt(cantHoras)); 
            String totalHoras="0";             
            String procesos=request.getParameter("txt_cotizacion_procesos");
            
            String diametroext="0";
            String trabajoAbreviado=request.getParameter("txt_cotizacion_trabajoAbrev");
            
            String comentarios=request.getParameter("txt_cotizacion_comentarios");            
            String nuevousado=request.getParameter("txt_cotizacion_nuevo_usado");
            String materialbase=request.getParameter("txt_cotizacion_materialbase");
            
            String sequencia=request.getParameter("sequencia");
            String correlativo=request.getParameter("correlativo").isEmpty()?"0":request.getParameter("correlativo");
            
            String codProc=request.getParameter("arrayCodProc");
            String cantProc=request.getParameter("arrayCantProc");
            String precioProc=request.getParameter("arrayPrecioProc");
            String totalProc=request.getParameter("arrayTotalProc");
            
            //inicio modificacion
            String margen_procesos = request.getParameter("txt_cotizacion_margenvo").replace(".", "");
            String utilidad_procesos = request.getParameter("txt_cotizacion_utilidad").replace(".", "");
            String total_total_procesos = request.getParameter("txt_cotizacion_totalnvo").replace(".", "");
            String total_procesos = request.getParameter("txt_cotizacion_total_proceso").replace(".", "");
            String margen_materiales = request.getParameter("txt_cotizacion_margen_materiales").replace(".", "");
            String utilidad_materiales = request.getParameter("txt_cotizacion_utilidad_materiales").replace(".", "");
            String total_total_materiales = request.getParameter("txt_cotizacion_total_materiales").replace(".", "");
            //fin modificacion
            
//            String arrayCodProc [] = codProc.replace("\"", "").replace("[", "").replace("]", "").split(",");
//            String arrayCantProc [] = cantProc.replace("\"", "").replace("[", "").replace("]", "").split(",");
//            String arrayPrecioProc [] = precioProc.replace("\"", "").replace("[", "").replace("]", "").split(",");            
//            String arrayTotalProc [] = totalProc.replace("\"", "").replace("[", "").replace("]", "").split(","); 
//            
//            String CodProc0 = arrayCodProc[0].isEmpty()?"0":arrayCodProc[0];
//            String CantProc0 = arrayCantProc[0].isEmpty()?"0":arrayCantProc[0];
//            String PrecioProc0 = arrayPrecioProc[0].isEmpty()?"0":arrayPrecioProc[0].replace(".", "");
//            String TotalProc0 = arrayTotalProc[0].isEmpty()?"0":arrayTotalProc[0].replace(".", "");
//            String CodProc1 = arrayCodProc[1].isEmpty()?"0":arrayCodProc[1];
//            String CantProc1 = arrayCantProc[1].isEmpty()?"0":arrayCantProc[1];
//            String PrecioProc1 = arrayPrecioProc[1].isEmpty()?"0":arrayPrecioProc[1].replace(".", "");
//            String TotalProc1 = arrayTotalProc[1].isEmpty()?"0":arrayTotalProc[1].replace(".", "");
//            String CodProc2 = arrayCodProc[2].isEmpty()?"0":arrayCodProc[2];
//            String CantProc2 = arrayCantProc[2].isEmpty()?"0":arrayCantProc[2];
//            String PrecioProc2 = arrayPrecioProc[2].isEmpty()?"0":arrayPrecioProc[2].replace(".", "");
//            String TotalProc2 = arrayTotalProc[2].isEmpty()?"0":arrayTotalProc[2].replace(".", "");
//            String CodProc3 = arrayCodProc[3].isEmpty()?"0":arrayCodProc[3];
//            String CantProc3 = arrayCantProc[3].isEmpty()?"0":arrayCantProc[3];
//            String PrecioProc3 = arrayPrecioProc[3].isEmpty()?"0":arrayPrecioProc[3].replace(".", "");
//            String TotalProc3 = arrayTotalProc[3].isEmpty()?"0":arrayTotalProc[3].replace(".", "");
//            String CodProc4 = arrayCodProc[4].isEmpty()?"0":arrayCodProc[4];
//            String CantProc4 = arrayCantProc[4].isEmpty()?"0":arrayCantProc[4];
//            String PrecioProc4 = arrayPrecioProc[4].isEmpty()?"0":arrayPrecioProc[4].replace(".", "");
//            String TotalProc4 = arrayTotalProc[4].isEmpty()?"0":arrayTotalProc[4].replace(".", "");
//            String CodProc5 = arrayCodProc[5].isEmpty()?"0":arrayCodProc[5];
//            String CantProc5 = arrayCantProc[5].isEmpty()?"0":arrayCantProc[5];
//            String PrecioProc5 = arrayPrecioProc[5].isEmpty()?"0":arrayPrecioProc[5].replace(".", "");
//            String TotalProc5 = arrayTotalProc[5].isEmpty()?"0":arrayTotalProc[5].replace(".", "");
//            String CodProc6 = arrayCodProc[6].isEmpty()?"0":arrayCodProc[6];
//            String CantProc6 = arrayCantProc[6].isEmpty()?"0":arrayCantProc[6];
//            String PrecioProc6 = arrayPrecioProc[6].isEmpty()?"0":arrayPrecioProc[6].replace(".", "");
//            String TotalProc6 = arrayTotalProc[6].isEmpty()?"0":arrayTotalProc[6].replace(".", "");
//            String CodProc7 = arrayCodProc[7].isEmpty()?"0":arrayCodProc[7];
//            String CantProc7 = arrayCantProc[7].isEmpty()?"0":arrayCantProc[7];
//            String PrecioProc7 = arrayPrecioProc[7].isEmpty()?"0":arrayPrecioProc[7].replace(".", "");
//            String TotalProc7 = arrayTotalProc[7].isEmpty()?"0":arrayTotalProc[7].replace(".", "");
//            String CodProc8 = arrayCodProc[8].isEmpty()?"0":arrayCodProc[8];
//            String CantProc8 = arrayCantProc[8].isEmpty()?"0":arrayCantProc[8];
//            String PrecioProc8 = arrayPrecioProc[8].isEmpty()?"0":arrayPrecioProc[8].replace(".", "");
//            String TotalProc8 = arrayTotalProc[8].isEmpty()?"0":arrayTotalProc[8].replace(".", "");
//            String CodProc9 = arrayCodProc[9].isEmpty()?"0":arrayCodProc[9];
//            String CantProc9 = arrayCantProc[9].isEmpty()?"0":arrayCantProc[9];
//            String PrecioProc9 = arrayPrecioProc[9].isEmpty()?"0":arrayPrecioProc[9].replace(".", "");
//            String TotalProc9 = arrayTotalProc[9].isEmpty()?"0":arrayTotalProc[9].replace(".", "");
//            String CodProc10 = arrayCodProc[10].isEmpty()?"0":arrayCodProc[10];
//            String CantProc10 = arrayCantProc[10].isEmpty()?"0":arrayCantProc[10];
//            String PrecioProc10 = arrayPrecioProc[10].isEmpty()?"0":arrayPrecioProc[10].replace(".", "");
//            String TotalProc10 = arrayTotalProc[10].isEmpty()?"0":arrayTotalProc[10].replace(".", "");
//            String CodProc11 = arrayCodProc[11].isEmpty()?"0":arrayCodProc[11];
//            String CantProc11 = arrayCantProc[11].isEmpty()?"0":arrayCantProc[11];
//            String PrecioProc11 = arrayPrecioProc[11].isEmpty()?"0":arrayPrecioProc[11].replace(".", "");
//            String TotalProc11 = arrayTotalProc[11].isEmpty()?"0":arrayTotalProc[11].replace(".", "");
//            String CodProc12 = arrayCodProc[11].isEmpty()?"0":arrayCodProc[12];
//            String CantProc12 = arrayCantProc[11].isEmpty()?"0":arrayCantProc[12];
//            String PrecioProc12 = arrayPrecioProc[11].isEmpty()?"0":arrayPrecioProc[12].replace(".", "");
//            String TotalProc12 = arrayTotalProc[11].isEmpty()?"0":arrayTotalProc[12].replace(".", "");
            
            String matDescripcion1=request.getParameter("txt_cotizacion_materiales_descripcion1");
            String matCantidad1=request.getParameter("txt_cotizacion_materiales_cantidad1").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_cantidad1").replace(".", "");
            String matPreciounitario1=request.getParameter("txt_cotizacion_materiales_preciounitario1").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_preciounitario1").replace(".", "");
            String matTotalitem1=request.getParameter("txt_cotizacion_materiales_totalitem1").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_totalitem1").replace(".", "");
            String matDescripcion2=request.getParameter("txt_cotizacion_materiales_descripcion2");
            String matCantidad2=request.getParameter("txt_cotizacion_materiales_cantidad2").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_cantidad2").replace(".", "");
            String matPreciounitario2=request.getParameter("txt_cotizacion_materiales_preciounitario2").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_preciounitario2").replace(".", "");
            String matTotalitem2=request.getParameter("txt_cotizacion_materiales_totalitem2").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_totalitem2").replace(".", "");
            String matDescripcion3=request.getParameter("txt_cotizacion_materiales_descripcion3");
            String matCantidad3=request.getParameter("txt_cotizacion_materiales_cantidad3").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_cantidad3").replace(".", "");
            String matPreciounitario3=request.getParameter("txt_cotizacion_materiales_preciounitario3").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_preciounitario3").replace(".", "");
            String matTotalitem3=request.getParameter("txt_cotizacion_materiales_totalitem3").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_totalitem3").replace(".", "");
            String matDescripcion4=request.getParameter("txt_cotizacion_materiales_descripcion4");
            String matCantidad4=request.getParameter("txt_cotizacion_materiales_cantidad4").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_cantidad4").replace(".", "");
            String matPreciounitario4=request.getParameter("txt_cotizacion_materiales_preciounitario4").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_preciounitario4").replace(".", "");
            String matTotalitem4=request.getParameter("txt_cotizacion_materiales_totalitem4").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_totalitem4").replace(".", "");
            String matDescripcion5=request.getParameter("txt_cotizacion_materiales_descripcion5");
            String matCantidad5=request.getParameter("txt_cotizacion_materiales_cantidad5").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_cantidad5").replace(".", "");
            String matPreciounitario5=request.getParameter("txt_cotizacion_materiales_preciounitario5").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_preciounitario5").replace(".", "");
            String matTotalitem5=request.getParameter("txt_cotizacion_materiales_totalitem5").isEmpty()?"0":request.getParameter("txt_cotizacion_materiales_totalitem5").replace(".", "");
            String totalMateriales=request.getParameter("txt_cotizacion_totalMateriales").isEmpty()?"0":request.getParameter("txt_cotizacion_totalMateriales").replace(".", "");
            String totalGeneralCot=request.getParameter("txt_cotizacion_totalGeneral").isEmpty()?"0":request.getParameter("txt_cotizacion_totalGeneral").replace(".", "");
            String iva=request.getParameter("iva").isEmpty()?"0":request.getParameter("iva").replace(".", "");
            String totalBruto=request.getParameter("totalbruto").isEmpty()?"0":request.getParameter("totalbruto").replace(".", "");            
            
            String areatotal=request.getParameter("txt_cotizacion_area_total").isEmpty()?"0":request.getParameter("txt_cotizacion_area_total");
            String largo=request.getParameter("txt_cotizacion_largo");
            String diametroint=request.getParameter("txt_cotizacion_diametroInt").isEmpty()?"0":request.getParameter("txt_cotizacion_diametroInt");
            String ancho=request.getParameter("txt_cotizacion_ancho").isEmpty()?"0":request.getParameter("txt_cotizacion_ancho");
            String area=request.getParameter("txt_cotizacion_area").isEmpty()?"0":request.getParameter("txt_cotizacion_area");            
            String corriente=request.getParameter("txt_cotizacion_corriente").isEmpty()?"0":request.getParameter("txt_cotizacion_corriente");
            
            diametroint = diametroint.replace(",", ".");
            diametroext = diametroext.replace(",", ".");
            area = area.replace(",", ".");
            areatotal = areatotal.replace(",", ".");
            corriente = corriente.replace(",", ".");

            ancho = ancho.replace(",", ".");
            largo = largo.replace(",", ".");
            diametro = diametro.replace(",", ".");
            try{
                _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion")); 
                CallableStatement sp_usu=null;
                           
                sp_usu = _connMy.prepareCall("{call sp_cotizaciones_det(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                sp_usu.setString(1,opcion);
                sp_usu.setInt(2,Integer.parseInt(numeroCotiza));
                sp_usu.setInt(3,Integer.parseInt(correlativo));
                sp_usu.setString(4,codPieza);
                sp_usu.setString(5,descPieza);
                sp_usu.setInt(6,Integer.parseInt(cantidad));
                sp_usu.setString(7,valorDM);
                sp_usu.setDouble(8,Double.parseDouble(diametro));
                sp_usu.setDouble(9,Double.parseDouble(largo));
                sp_usu.setLong(10,Long.parseLong(valorUniCrom));
                sp_usu.setLong(11,Long.parseLong(valorTotalCrom));
                sp_usu.setLong(12,Long.parseLong(valorUnitario));
                sp_usu.setLong(13,Long.parseLong(totales));
                sp_usu.setLong(14,Long.parseLong(costoHoras));
                sp_usu.setLong(15,Long.parseLong(cantHoras));
                sp_usu.setLong(16,Long.parseLong(totalHoras)); 
                sp_usu.setString(17,procesos);
                sp_usu.setDouble(18,Double.parseDouble(diametroint));
                sp_usu.setDouble(19,Double.parseDouble(diametroext));
                sp_usu.setString(20,trabajoAbreviado);
                sp_usu.setDouble(21,Double.parseDouble(ancho));
                sp_usu.setDouble(22,Double.parseDouble(area));                
                sp_usu.setString(23,comentarios);
//                sp_usu.setInt(24,Integer.parseInt(CodProc0));
//                sp_usu.setDouble(25,Double.parseDouble(CantProc0));
//                sp_usu.setInt(26,Integer.parseInt(PrecioProc0));
//                sp_usu.setInt(27,Integer.parseInt(TotalProc0));
//                sp_usu.setInt(28,Integer.parseInt(CodProc1));
//                sp_usu.setDouble(29,Double.parseDouble(CantProc1));
//                sp_usu.setInt(30,Integer.parseInt(PrecioProc1));
//                sp_usu.setInt(31,Integer.parseInt(TotalProc1));
//                sp_usu.setInt(32,Integer.parseInt(CodProc2));
//                sp_usu.setDouble(33,Double.parseDouble(CantProc2));
//                sp_usu.setInt(34,Integer.parseInt(PrecioProc2));
//                sp_usu.setInt(35,Integer.parseInt(TotalProc2));
//                sp_usu.setInt(36,Integer.parseInt(CodProc3));
//                sp_usu.setDouble(37,Double.parseDouble(CantProc3));
//                sp_usu.setInt(38,Integer.parseInt(PrecioProc3));
//                sp_usu.setInt(39,Integer.parseInt(TotalProc3));
//                sp_usu.setInt(40,Integer.parseInt(CodProc4));
//                sp_usu.setDouble(41,Double.parseDouble(CantProc4));
//                sp_usu.setInt(42,Integer.parseInt(PrecioProc4));
//                sp_usu.setInt(43,Integer.parseInt(TotalProc4));
//                sp_usu.setInt(44,Integer.parseInt(CodProc5));
//                sp_usu.setDouble(45,Double.parseDouble(CantProc5));
//                sp_usu.setInt(46,Integer.parseInt(PrecioProc5));
//                sp_usu.setInt(47,Integer.parseInt(TotalProc5));
//                sp_usu.setInt(48,Integer.parseInt(CodProc6));
//                sp_usu.setDouble(49,Double.parseDouble(CantProc6));
//                sp_usu.setInt(50,Integer.parseInt(PrecioProc6));
//                sp_usu.setInt(51,Integer.parseInt(TotalProc6));
//                sp_usu.setInt(52,Integer.parseInt(CodProc7));
//                sp_usu.setDouble(53,Double.parseDouble(CantProc7));
//                sp_usu.setInt(54,Integer.parseInt(PrecioProc7));
//                sp_usu.setInt(55,Integer.parseInt(TotalProc7));
//                sp_usu.setInt(56,Integer.parseInt(CodProc8));
//                sp_usu.setDouble(57,Double.parseDouble(CantProc8));
//                sp_usu.setInt(58,Integer.parseInt(PrecioProc8));
//                sp_usu.setInt(59,Integer.parseInt(TotalProc8));
//                sp_usu.setInt(60,Integer.parseInt(CodProc9));
//                sp_usu.setDouble(61,Double.parseDouble(CantProc9));
//                sp_usu.setInt(62,Integer.parseInt(PrecioProc9));
//                sp_usu.setInt(63,Integer.parseInt(TotalProc9));
//                sp_usu.setInt(64,Integer.parseInt(CodProc10));
//                sp_usu.setDouble(65,Double.parseDouble(CantProc10));
//                sp_usu.setInt(66,Integer.parseInt(PrecioProc10));
//                sp_usu.setInt(67,Integer.parseInt(TotalProc10));
//                sp_usu.setInt(68,Integer.parseInt(CodProc11));
//                sp_usu.setDouble(69,Double.parseDouble(CantProc11));
//                sp_usu.setInt(70,Integer.parseInt(PrecioProc11));
//                sp_usu.setInt(71,Integer.parseInt(TotalProc11));
                sp_usu.setString(24,nuevousado);
                sp_usu.setString(25,materialbase);
                sp_usu.setDouble(26,Double.parseDouble(areatotal));
                sp_usu.setDouble(27,Double.parseDouble(corriente));
                sp_usu.setString(28,matDescripcion1);
                sp_usu.setInt(29,Integer.parseInt(matCantidad1));
                sp_usu.setInt(30,Integer.parseInt(matPreciounitario1));
                sp_usu.setInt(31,Integer.parseInt(matTotalitem1));
                sp_usu.setString(32,matDescripcion2);
                sp_usu.setInt(33,Integer.parseInt(matCantidad2));
                sp_usu.setInt(34,Integer.parseInt(matPreciounitario2));
                sp_usu.setInt(35,Integer.parseInt(matTotalitem2));
                sp_usu.setString(36,matDescripcion3);
                sp_usu.setInt(37,Integer.parseInt(matCantidad3));
                sp_usu.setInt(38,Integer.parseInt(matPreciounitario3));
                sp_usu.setInt(39,Integer.parseInt(matTotalitem3));
                sp_usu.setString(40,matDescripcion4);
                sp_usu.setInt(41,Integer.parseInt(matCantidad4));
                sp_usu.setInt(42,Integer.parseInt(matPreciounitario4));
                sp_usu.setInt(43,Integer.parseInt(matTotalitem4));
                sp_usu.setString(44,matDescripcion5);
                sp_usu.setInt(45,Integer.parseInt(matCantidad5));
                sp_usu.setInt(46,Integer.parseInt(matPreciounitario5));
                sp_usu.setInt(47,Integer.parseInt(matTotalitem5));
                sp_usu.setLong(48,Long.parseLong(totalMateriales));
                sp_usu.setLong(49,Long.parseLong(totalGeneralCot));
                sp_usu.setLong(50,Long.parseLong(iva));
                sp_usu.setLong(51,Long.parseLong(totalBruto));
                sp_usu.setInt(52,Integer.parseInt(sequencia));
                
                sp_usu.setString(53,codProc);
                sp_usu.setString(54,cantProc);
                sp_usu.setString(55,precioProc);
                sp_usu.setString(56,totalProc);
                
                //inicio modificacion
                sp_usu.setString(57,margen_procesos);
                sp_usu.setString(58,utilidad_procesos);
                sp_usu.setString(59,total_procesos);
                sp_usu.setString(60,total_total_procesos);
                sp_usu.setString(61,margen_materiales);
                sp_usu.setString(62,utilidad_materiales);
                sp_usu.setString(63,total_total_materiales);
                //fin modificacion
                
                sp_usu.registerOutParameter(1, Types.VARCHAR);
                
                sp_usu.execute();
                String valorSalida = sp_usu.getString(1);                
                if(valorSalida.equalsIgnoreCase("error ejecucion"))
                {
                    out.println("Ya existe");
                }
                
                final ResultSet rs = sp_usu.getResultSet();            
                String cla = "";
                int cont = 0;
                String salida = "";
                if (opcion.equals("select") || opcion.equals("select_paso")){
                    while(rs.next())
                    {
                        if(cont % 2 == 0)
                        {                    
                            cla = "alt";
                        }else
                        {  
                            cla = "";                    
                        }
                        salida += "<tr id='filaTablaDetalle"+cont+"' class='"+cla+"'>";

                        salida += "<td><a id=\"seleccion"+cont+"\" href=\"javascript: onclick=ModificaDetalleComercial("+cont+")\"> >></a>\n" +
                                    "<input type=\"hidden\" value=\"0\" id=\"habilitaDetCom\" name=\"habilitaDetCom\" />\n" +
                                    "</td> ";
                        salida += "<td id =\"cotazacionDet_correlativo"+cont+"\">"+rs.getString("correlativo")+"</td>";   
                        salida += "<td id =\"cotazacionDet_pieza"+cont+"\">"+rs.getString("desc_pieza")+"</td>"; 
                        salida += "<td id =\"cotazacionDet_trabajoAbreviado"+cont+"\">"+rs.getString("trabajo_abreviado")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_cantidad"+cont+"\">"+rs.getString("cantidad")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_diametroInterno"+cont+"\">"+rs.getString("diametro_interno")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_largo"+cont+"\">"+rs.getString("largo")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_ancho"+cont+"\">"+rs.getString("ancho")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_area"+cont+"\">"+rs.getString("area")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_areatotal"+cont+"\">"+rs.getString("superficie_total")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_totalNeto"+cont+"\">"+rs.getString("total_pieza")+"</td>"; 
                        salida += "<td align=\"right\" id =\"cotazacionDet_iva"+cont+"\">"+rs.getString("iva")+"</td>";                         
                        salida += "<td align=\"right\" id =\"cotazacionDet_totalBruto"+cont+"\">"+rs.getString("total_bruto")+"</td>"; 
                        salida += "<td style=\"width: 20px;\" id =\"cotazacionDetSub_comentarios"+cont+"\">"+rs.getString("comentarios2")+"</td>"; 
                        salida += "<td style=\"display: none;\" id =\"cotazacionDet_comentarios"+cont+"\">"+rs.getString("comentarios")+"</td>";
                        salida += "<td style=\"display:none;\" id =\"cotazacionDet_procesos"+cont+"\">"+rs.getString("procesos")+"</td>"; 
                        salida += "<td style=\"display:none;\" id =\"cotazacionCorriente"+cont+"\">"+rs.getString("corriente")+"</td>"; 
                        salida += "<td style=\"display:none;\" id =\"cotazacionCodPieza"+cont+"\">"+rs.getString("cod_pieza")+"</td>"; 
                        salida += "<td style=\"display:none;\" id =\"cotazacionTotalProcesos"+cont+"\">"+rs.getString("totales")+"</td>"; 
                       
                        salida += "<td style=\"display:none;\" id =\"cotazacionTotalMateriales"+cont+"\">"+rs.getString("total_materiales")+"</td>"; 
                        salida += "<td style=\"display:none;\" id =\"cotazacionDet_nuevousado"+cont+"\">"+rs.getString("nuevo_usado")+"</td>";
                        salida += "<td style=\"display:none;\" id =\"cotazacionDetmaterialbase"+cont+"\">"+rs.getString("material_base")+"</td>";
                        
                        salida += "</tr>";
                        salida += "<input type=\"hidden\" id=\"cantidad_detalle\" value="+(cont+1)+"></input>";
                        cont++;
                    }
                }
                if (opcion.equals("select_detalle")) {
                    while(rs.next()) {
                        
                              
                        
//                            salida += rs.getString("proc_rect_previo_cod")+"|";  
//                            salida += rs.getString("proc_rect_previo_cant")+"|";  
//                            salida += rs.getString("proc_rect_previo_precio")+"|";  
//                            salida += rs.getString("proc_rect_previo_total")+"|";  
//                            salida += rs.getString("proc_crom_cod")+"|";  
//                            salida += rs.getString("proc_crom_cant")+"|";  
//                            salida += rs.getString("proc_crom_precio")+"|";  
//                            salida += rs.getString("proc_crom_total")+"|";  
//                            salida += rs.getString("proc_rect_final_cod")+"|";  
//                            salida += rs.getString("proc_rect_final_cant")+"|";  
//                            salida += rs.getString("proc_rect_final_precio")+"|";
//                            salida += rs.getString("proc_rect_final_total")+"|";  
//                            salida += rs.getString("proc_bandeo_cod")+"|";  
//                            salida += rs.getString("proc_bandeo_cant")+"|";  
//                            salida += rs.getString("proc_bandeo_precio")+"|";  
//                            salida += rs.getString("proc_bandeo_total")+"|";  
//                            salida += rs.getString("proc_brunido_cod")+"|";  
//                            salida += rs.getString("proc_brunido_cant")+"|";  
//                            salida += rs.getString("proc_brunido_precio")+"|";  
//                            salida += rs.getString("proc_brunido_total")+"|";  
//                            salida += rs.getString("proc_torno_cod")+"|";  
//                            salida += rs.getString("proc_torno_cant")+"|";
//                            salida += rs.getString("proc_torno_precio")+"|";  
//                            salida += rs.getString("proc_torno_total")+"|";  
//                            salida += rs.getString("proc_fresa_cod")+"|";  
//                            salida += rs.getString("proc_fresa_cant")+"|";  
//                            salida += rs.getString("proc_fresa_precio")+"|";  
//                            salida += rs.getString("proc_fresa_total")+"|";  
//                            salida += rs.getString("proc_soldadura_cod")+"|";  
//                            salida += rs.getString("proc_soldadura_cant")+"|";  
//                            salida += rs.getString("proc_soldadura_precio")+"|";  
//                            salida += rs.getString("proc_soldadura_total")+"|";  
//                            salida += rs.getString("proc_cambiosello_cod")+"|";  
//                            salida += rs.getString("proc_cambiosello_cant")+"|";  
//                            salida += rs.getString("proc_cambiosello_precio")+"|";  
//                            salida += rs.getString("proc_cambiosello_total")+"|";  
//                            salida += rs.getString("proc_descrom_cod")+"|";  
//                            salida += rs.getString("proc_descrom_cant")+"|";
//                            salida += rs.getString("proc_descrom_precio")+"|";
//                            salida += rs.getString("proc_descrom_total")+"|";
//                            salida += rs.getString("proc_horahombre_cod")+"|";
//                            salida += rs.getString("proc_horahombre_cant")+"|";
//                            salida += rs.getString("proc_horahombre_precio")+"|";
//                            salida += rs.getString("proc_horahombre_total")+"|";
//                            salida += rs.getString("proc_tratamientoterm_cod")+"|";
//                            salida += rs.getString("proc_tratamientoterm_cant")+"|";
//                            salida += rs.getString("proc_tratamientoterm_precio")+"|";
//                            salida += rs.getString("proc_tratamientoterm_total")+"|";
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
                            
                            //inicio modificacion
                            materiales += rs.getString("margen_procesos")+"|";
                            materiales += rs.getString("utilidad_procesos")+"|";
                            materiales += rs.getString("total_procesos")+"|";
                            materiales += rs.getString("total_total_procesos")+"|";
                            materiales += rs.getString("margen_materiales")+"|";
                            materiales += rs.getString("utilidad_materiales")+"|";
                            materiales += rs.getString("total_total_materiales")+"|";
                            //fin modificacion
                            
                            salida += "{\"procesos\":{\"codproc\":" + rs.getString("json_codproc") + "," + 
                                "\"cantproc\":"+ rs.getString("json_cantproc")+"," + 
                                "\"precioproc\":" + rs.getString("json_precioproc")+ "," + 
                                "\"totalproc\":" + rs.getString("json_totalproc") +"},\"materiales\":\"||||||||||||||||||||||||||||||||||||||||||||||||"+
                                    materiales.replace("\"","\\\"") +"\"}";
                            
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
