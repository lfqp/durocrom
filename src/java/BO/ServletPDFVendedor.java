/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import DAL.conexionBD;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
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
@WebServlet(name = "ServletPDFVendedor", urlPatterns = {"/ServletPDFVendedor"})
public class ServletPDFVendedor extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nro = request.getParameter("txt_filtroImprimir_ingreso");
        int random = (int) (Math.random() * 1000000000);
        //String pdfFileName = "C:\\pdfdurocrom/"+nro+"_"+random+".pdf"; //LOCAL
        String pdfFileName = conexionBD.getRutaPaso() + nro + "_" + random + ".pdf"; //SERVIDOR
        
        StringBuffer URL = request.getRequestURL();
        String url = URL.delete(URL.lastIndexOf("/"), URL.length()).toString();
        FileOutputStream archivo = new FileOutputStream(pdfFileName);

        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession s = request.getSession();
            Connection _connMy = null;

            String opcion = request.getParameter("opcionPDF");
            String k = "";
            String registro;
            Integer cantRegistros = 0;

            Double espacios6 = 3.0;
            Double espacios7 = 3.7;

            try {
                _connMy = conexionBD.Conectar((String) s.getAttribute("organizacion"));
                CallableStatement sp_usu = _connMy.prepareCall("{call sp_cotizacion_pdf(?,?)}");
                sp_usu.setString(1, opcion);
                sp_usu.setString(2, nro);
                sp_usu.execute();
                final ResultSet rs = sp_usu.getResultSet();

                while (rs.next()) {
                    cantRegistros += 1;
                }

                try {

                    if (cantRegistros <= 2) {

                        //PdfReader reader = new PdfReader("http://localhost:8080/Durocrom/images/Formato_COT_vendedor_1Pag.pdf"); // LOCAL 
                        PdfReader reader = new PdfReader(url + "/images/Formato_COT_vendedor_1Pag.pdf"); // SERVIDOR
                        PdfStamper stamper = new PdfStamper(reader, archivo); // output PDF
                        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font

                        //loop on pages (1-based)
                        rs.beforeFirst();
                        while (rs.next()) {

                        // get object for writing over the existing content;
                            // you can also use getUnderContent for writing in the bottom layer
                            PdfContentByte over = stamper.getOverContent(1);

                            
                            // write text
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(280, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("numero_cotizacion"));  // set text
                            over.endText();
                                    
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(424, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("dias_habiles"));  // set text
                            over.endText();
                            
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(475, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fecha_emision"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 659);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("nombre_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 646);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("razon_social"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 634);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("direccion"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 622);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("comuna"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 659);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("email_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 646);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("celular_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 634);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fijo_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 622);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("rut_cli"));  // set text
                            over.endText();

                            if (rs.getRow() == 1) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 606);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 595);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 581);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 569);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 555);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 542-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 595);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 581);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 569);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 555);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                
                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 501;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 501);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 501);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 489);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 489);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 477);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 477);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 465);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 465);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 453);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 453);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 441);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 441);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 429);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 429);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 417);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 417);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 406);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 405);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 393);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 393);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 381);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 381);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 369);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 369);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 393);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 393);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }
                            if (rs.getRow() == 2) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 355);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 346);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 333);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 320);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 307);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 294-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 346);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 333);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 320);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 307);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 251;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 251);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 251);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 239);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 239);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 227);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 227);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 215);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 215);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 203);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 203);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 191);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 191);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 179);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 179);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 167);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 167);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 156);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 156);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 143);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 143);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 131);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 131);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 119);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 119);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 143);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 143);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(119, espacios6, rs.getString("total_procesos")), 91);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_procesos"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(210, espacios6, rs.getString("total_materiales")), 91);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_materiales"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(313, espacios6, rs.getString("total_neto")), 91);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_neto"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(390, espacios6, rs.getString("iva")), 91);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("iva"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(496, espacios6, rs.getString("total_bruto")), 91);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_bruto"));  // set text
                            over.endText();

                        }
                        //response.setContentLength((int) stamper.getReader().getFileLength());
                        stamper.close();
                        stamper.flush();
                    }

                    if (cantRegistros > 2) {

                        //PdfReader reader = new PdfReader("http://localhost:8080/Durocrom/images/Formato_COT_vendedor_2Pag.pdf"); // LOCAL 
                        PdfReader reader = new PdfReader(url + "/images/Formato_COT_vendedor_2Pag.pdf"); // SERVIDOR
                        PdfStamper stamper = new PdfStamper(reader, archivo); // output PDF
                        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font

                        //loop on pages (1-based)
                        rs.beforeFirst();
                        while (rs.next()) {

                        // get object for writing over the existing content;
                            // you can also use getUnderContent for writing in the bottom layer
                            PdfContentByte over = stamper.getOverContent(1);

                            // write text
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(280, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("numero_cotizacion"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(424, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("dias_habiles"));  // set text
                            over.endText();
                            
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(475, 685);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fecha_emision"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 659);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("nombre_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 646);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("razon_social"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 634);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("direccion"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 622);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("comuna"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 659);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("email_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 646);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("celular_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 634);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fijo_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 622);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("rut_cli"));  // set text
                            over.endText();
                            if (rs.getRow() == 1) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 606);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 595);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 581);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 569);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 555);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 542-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 595);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 581);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 569);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 555);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 542);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 501;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 501);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 501);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 489);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 489);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 477);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 477);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 465);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 465);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 453);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 453);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 441);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 441);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 429);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 429);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 417);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 417);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 406);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 405);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 393);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 393);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 381);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 381);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 369);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 369);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 477);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 465);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 453);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 441);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 429);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 393);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 393);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }
                            if (rs.getRow() == 2) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 355);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 346);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 333);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 320);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 307);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 294-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 346);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 333);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 320);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 307);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 294);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 251;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 251);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 251);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 239);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 239);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 227);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 227);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 215);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 215);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 203);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 203);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 191);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 191);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 179);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 179);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 167);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 167);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 156);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 156);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 143);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 143);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 131);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 131);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 119);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 119);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 227);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 215);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 203);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 191);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 179);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 143);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 143);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }

                            over = stamper.getOverContent(2);

                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(280, 688);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("numero_cotizacion"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(424, 688);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("dias_habiles"));  // set text
                            over.endText();
                            
                            over.beginText();
                            over.setFontAndSize(bf, 8);    // set font and size
                            over.setTextMatrix(475, 688);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fecha_emision"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 662);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("nombre_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 649);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("razon_social"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 637);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("direccion"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(122, 625);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("comuna"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 662);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("email_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 649);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("celular_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 637);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("fijo_contacto_comercial"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 7);    // set font and size
                            over.setTextMatrix(393, 625);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("rut_cli"));  // set text
                            over.endText();

                            if (rs.getRow() == 3) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 609);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 598);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 584);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 572);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 558);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 545);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 545-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 598);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 584);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 572);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 558);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 545);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 545);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 504;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 504);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 504);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 492);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 492);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 480);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 480);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 468);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 468);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 456);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 456);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 444);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 444);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 432);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 432);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 420);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 420);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 409);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 409);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 396);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 396);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 384);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 384);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 372);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 372);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 480);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 480);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 480);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 480);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 468);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 468);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 468);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 468);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 456);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 456);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 456);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 456);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 444);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 444);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 444);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 444);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 432);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 432);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 432);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 432);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 396);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 396);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }
                            if (rs.getRow() == 4) {

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(228, 358);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("correlativo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 349);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("desc_pieza"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 336);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("nuevo_usado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 323);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("material_base"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 310);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("trabajo_abreviado"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(122, 297);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("cantidad"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(125, 297-13);   // set x,y position (0,0 is at the bottom left)
                                String comentarios = rs.getString("comentarios");
                                if(comentarios.length() > 100){
                                    comentarios = comentarios.substring(0, 97);
                                }
                                over.showText(comentarios);  // set text
                                over.endText();
                                
                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("diametro_interno")), 349);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("diametro_interno"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("largo")), 336);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("largo"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("ancho")), 323);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("ancho"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie")), 310);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(retornaX(468, espacios7, rs.getString("superficie_total")), 297);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("superficie_total"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 7);    // set font and size
                                over.setTextMatrix(250, 297);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("corriente"));  // set text
                                over.endText();

                                String json_codproc = rs.getString("json_codproc");
                                String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_cantproc = rs.getString("json_cantproc");
                                String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_precioproc = rs.getString("json_precioproc");
                                String[] arr_json_precioproc = json_precioproc.replace("\"","").replace("[","").replace("]","").split(",");
                                String json_totalproc = rs.getString("json_totalproc");
                                String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                                
                                int y_inicial = 254;
                                for (int x = 0; x < arr_json_codproc.length-1; x++){
                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(150, espacios6, arr_json_precioproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_precioproc[x]);  // set text
                                    over.endText();

                                    over.beginText();
                                    over.setFontAndSize(bf, 6);    // set font and size
                                    over.setTextMatrix(retornaX(212, espacios6, arr_json_totalproc[x]), y_inicial);   // set x,y position (0,0 is at the bottom left)
                                    over.showText(arr_json_totalproc[x]);  // set text
                                    over.endText();
                                    
                                    y_inicial = y_inicial - 12;
                                }
//                                
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 254);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_previo_total")), 254);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_previo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_crom_precio")), 242);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_crom_total")), 242);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_crom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_rect_previo_precio")), 230);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_rect_final_total")), 230);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_rect_final_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_bandeo_precio")), 218);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_bandeo_total")), 218);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_bandeo_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_brunido_precio")), 206);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_brunido_total")), 206);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_brunido_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_torno_precio")), 194);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_torno_total")), 194);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_torno_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_fresa_precio")), 182);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_fresa_total")), 182);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_fresa_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_soldadura_precio")), 170);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_soldadura_total")), 170);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_soldadura_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_cambiosello_precio")), 159);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_cambiosello_total")), 159);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_cambiosello_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_descrom_precio")), 146);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_descrom_total")), 146);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_descrom_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_horahombre_precio")), 134);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_horahombre_total")), 134);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_horahombre_total"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(150, espacios6, rs.getString("proc_tratamientoterm_precio")), 122);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_precio"));  // set text
//                                over.endText();
//
//                                over.beginText();
//                                over.setFontAndSize(bf, 6);    // set font and size
//                                over.setTextMatrix(retornaX(212, espacios6, rs.getString("proc_tratamientoterm_total")), 122);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(rs.getString("proc_tratamientoterm_total"));  // set text
//                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 230);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad1")), 230);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio1")), 230);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem1")), 230);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem1"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 218);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad2")), 218);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio2")), 218);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem2")), 218);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem2"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 206);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad3")), 206);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio3")), 206);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem3")), 206);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem3"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 194);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad4")), 194);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio4")), 194);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem4")), 194);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem4"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(254, 182);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_descripcion5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(388, espacios6, rs.getString("mat_cantidad5")), 182);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_cantidad5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(433, espacios6, rs.getString("mat_precio5")), 182);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_precio5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(493, espacios6, rs.getString("mat_totalitem5")), 182);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("mat_totalitem5"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(344, espacios6, rs.getString("total_procesos_det")), 146);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_procesos_det"));  // set text
                                over.endText();

                                over.beginText();
                                over.setFontAndSize(bf, 6);    // set font and size
                                over.setTextMatrix(retornaX(465, espacios6, rs.getString("total_materiales_det")), 146);   // set x,y position (0,0 is at the bottom left)
                                over.showText(rs.getString("total_materiales_det"));  // set text
                                over.endText();
                            }

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(119, espacios6, rs.getString("total_procesos")), 94);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_procesos"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(210, espacios6, rs.getString("total_materiales")), 94);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_materiales"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(313, espacios6, rs.getString("total_neto")), 94);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_neto"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(390, espacios6, rs.getString("iva")), 94);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("iva"));  // set text
                            over.endText();

                            over.beginText();
                            over.setFontAndSize(bf, 6);    // set font and size
                            over.setTextMatrix(retornaX(496, espacios6, rs.getString("total_bruto")), 94);   // set x,y position (0,0 is at the bottom left)
                            over.showText(rs.getString("total_bruto"));  // set text
                            over.endText();

                        }
                        //response.setContentLength((int) stamper.getReader().getFileLength());                        
                        stamper.close();
                        stamper.flush();
                    }
                    archivo.close();
                    archivo.flush();

                    String fileType = "application/pdf";
                    response.setContentType(fileType);

                    response.setHeader("Content-disposition", "attachment; filename=PDFVendedor.pdf");

                    File my_file = new File(pdfFileName);
                    OutputStream out = response.getOutputStream();
                    FileInputStream in = new FileInputStream(my_file);
                    //try (FileInputStream in = new FileInputStream(my_file)) {
                        byte[] buffer = new byte[4096];
                        int length;
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                        in.close();
                    //}
                        out.close();
                    out.flush();

                } catch (DocumentException ex) {
                    ex.printStackTrace();
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public int retornaX(int hasta, Double letra, String texto) {
        return (int) Math.round(hasta - texto.length() * letra);
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
