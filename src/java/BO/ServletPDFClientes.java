/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import DAL.conexionBD;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import static com.itextpdf.text.pdf.BaseFont.COURIER;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
@WebServlet(name = "ServletPDFClientes", urlPatterns = {"/ServletPDFClientes"})
public class ServletPDFClientes extends HttpServlet {

    private Object htmlContext;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        HttpSession s = request.getSession();
        Connection _connMy = null;
        String nro = request.getParameter("txt_filtroImprimir_ingreso");
        String opcion = request.getParameter("opcionPDF");
        String k = "";
        try {
            _connMy = conexionBD.Conectar((String) s.getAttribute("organizacion"));
            CallableStatement sp_usu = _connMy.prepareCall("{call sp_cotizacion_pdf(?,?)}");
            sp_usu.setString(1, opcion);
            sp_usu.setString(2, nro);
            sp_usu.execute();
            final ResultSet rs = sp_usu.getResultSet();
            int random = (int)(Math.random() * 1000000000);
            if (opcion.equals("cliente")) {
                StringBuffer URL = request.getRequestURL();
                String url = URL.delete(URL.lastIndexOf("/"), URL.length()).toString();
                //PdfReader reader = new PdfReader("http://localhost:8080/Durocrom/images/Formato_COT_cliente.pdf");   //LOCAL
                PdfReader reader = new PdfReader(url + "/images/Formato_COT_cliente.pdf");    //SERVIDOR
                
                //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:\\pdfdurocrom/"+nro+"_"+random+".pdf"));    //LOCAL
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(conexionBD.getRutaPaso()+nro+"_"+random+".pdf")); //SERVIDOR
                BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                Double tamano_letra = 4.8;
                
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    Integer linea = 100;
                    Integer desde = 548;
                    String romano = "I";
                    Integer indice = 0;
                    
                    PdfContentByte over = stamper.getOverContent(i);                                        
                    
                    while (rs.next()) {
                        indice++;
                        
                        for(int j = 0; j<= 1000; j=j+50){
                            for(int l = 0; l<= 1000; l=l+50){
                                over.beginText();
                                over.setFontAndSize(bf, 4);
                                over.setTextMatrix(j, l);
                                //over.showText(j + ":" + l);
                                over.endText();
                            }
                        }
                        
                        if(indice == 1){
                            romano = "I";
                        }
                        if(indice == 2){
                            romano = "II";
                        }
                        if(indice == 3){
                            romano = "III";
                        }
                        if(indice == 4){
                            romano = "IV";
                        }   
                        if (indice == 1)
                        {
                            String numero_cotizacion = rs.getString("numero_cotizacion");
                        
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(270, 673);
                            over.showText(numero_cotizacion);
                            over.endText();

                            String fecha_emision = rs.getString("fecha_emision");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(490, 673);
                            over.showText(fecha_emision);
                            over.endText();

                            String dias_habiles = rs.getString("dias_habiles");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(440, 673);
                            over.showText(dias_habiles);
                            over.endText();

                            String rut_cli = rs.getString("rut_cli");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(413, 592);
                            over.showText(rut_cli);
                            over.endText();

                            String razon_social = rs.getString("razon_social");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(140, 622);
                            over.showText(razon_social);
                            over.endText();

                            String celular_contacto_comercial = rs.getString("celular_contacto_comercial");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(413, 623);
                            over.showText(celular_contacto_comercial);
                            over.endText();

                            String direccion = rs.getString("direccion");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(140, 607);
                            over.showText(direccion);
                            over.endText();

                            String fijo_contacto_comercial = rs.getString("fijo_contacto_comercial");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(413, 607);
                            over.showText(fijo_contacto_comercial);
                            over.endText();

                            String comuna = rs.getString("comuna");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(140, 592);
                            over.showText(comuna);
                            over.endText();

                            String email_contacto_comercial = rs.getString("email_contacto_comercial");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(413, 638);
                            over.showText(email_contacto_comercial);
                            over.endText();

                            String nombre_contacto_comercial = rs.getString("nombre_contacto_comercial");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(140, 638);
                            over.showText(nombre_contacto_comercial);
                            over.endText();

                            String total_neto = rs.getString("total_neto");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(550,tamano_letra, total_neto), 131);
                            over.showText(total_neto);
                            over.endText();

                            String iva = rs.getString("iva");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(550,tamano_letra, iva), 113);
                            over.showText(iva);
                            over.endText();

                            String total_bruto = rs.getString("total_bruto");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(550,tamano_letra, total_bruto), 96);
                            over.showText(total_bruto);
                            over.endText();
                            
                            String presupuesto_valido = rs.getString("presupuesto_valido");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(170, 79);
                            over.showText(presupuesto_valido);
                            over.endText();

                            String condiciones_pago = rs.getString("condiciones_pago");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(170, 63);
                            over.showText(condiciones_pago);
                            over.endText();

                            String nombre_user = rs.getString("nombre_user");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(327, 79);
                            over.showText(nombre_user);
                            over.endText();
                        }
                        String detalle_unico_cliente = rs.getString("detalle_unico_cliente");
                        
                        if(detalle_unico_cliente == null || detalle_unico_cliente.equals("")){
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(75, desde);
                            over.showText(romano);
                            over.endText();

                            String cantidad = rs.getString("cantidad");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(113, desde);
                            over.showText(cantidad);
                            over.endText();

                            String desc_pieza = rs.getString("desc_pieza");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(145, desde);
                            over.showText(desc_pieza);
                            over.endText();

                            String trabajo_abreviado = rs.getString("trabajo_abreviado");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(145, desde-10);
                            over.showText(trabajo_abreviado);
                            over.endText();

                            String diametro_interno = rs.getString("diametro_interno");
                            String largo = rs.getString("largo");
                            String ancho = rs.getString("ancho");
                            String desc = diametro_interno + " - " + largo + " - " + ancho;

                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(145, desde-20);
                            over.showText(desc);
                            over.endText();

                            String comentarios = rs.getString("comentarios");                            
                            Rectangle rect = new Rectangle(413,100,145,desde-20);                            
                            rect.setBorderColor(BaseColor.BLACK);
                            rect.setBorder(1);                            
                            ColumnText ct = new ColumnText(over);
                            ct.setSimpleColumn(rect);                                
                            Font fuente = new Font(FontFamily.COURIER);
                            fuente.setSize(8);
                            ct.addElement(new Paragraph(comentarios,fuente));
                            ct.go();                            
                            
                            
                            
//                            over.beginText();
//                            over.setFontAndSize(bf, 8);
//                            over.setTextMatrix(145, desde-30);
//                            over.showText(comentarios);
//                            over.endText();

                            String precio_unitario = rs.getString("precio_unitario");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(475,tamano_letra, precio_unitario), desde);
                            over.showText(precio_unitario);
                            over.endText();

                            String total_pieza = rs.getString("total_pieza");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(550,tamano_letra, total_pieza), desde);
                            over.showText(total_pieza);
                            over.endText();
                        }else{                                                        
                            Rectangle rect = new Rectangle(413,100,142,557);                                                                           
                            ColumnText ct = new ColumnText(over);
                            ct.setSimpleColumn(rect);                                
                            Font fuente = new Font(FontFamily.COURIER);
                            fuente.setSize(8);
                            ct.addElement(new Paragraph(detalle_unico_cliente,fuente));
                            ct.go();
                            
                            String total_neto = rs.getString("total_neto");
                            over.beginText();
                            over.setFontAndSize(bf, 8);
                            over.setTextMatrix(retornaX(550,tamano_letra, total_neto), 542);
                            over.showText(total_neto);
                            over.endText();
                            
                            break;
                        }
                        
                        desde = desde - linea;
                    }
                }
                stamper.close();
                stamper.flush();
                
                String fileName = conexionBD.getRutaPaso()+nro+"_"+random+".pdf";  //SERVIDOR
                String fileType = "application/pdf";
                response.setContentType(fileType);

                response.setHeader("Content-disposition","attachment; filename=Cotizacion_"+nro+".pdf");

                File my_file = new File(fileName);

                OutputStream out = response.getOutputStream();
                FileInputStream in = new FileInputStream(my_file);
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0){
                   out.write(buffer, 0, length);
                }
                in.close();
                out.flush();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public int retornaX(int hasta, Double letra, String texto){
        return (int) Math.round(hasta-texto.length()*letra);
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
