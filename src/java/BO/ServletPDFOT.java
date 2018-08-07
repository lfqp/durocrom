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
 * @author Mauricio Romero
 */
@WebServlet(name = "ServletPDFOT", urlPatterns = {"/ServletPDFOT"})
public class ServletPDFOT extends HttpServlet {

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
        
        String nro = request.getParameter("txt_numero_ot");
        int random = (int) (Math.random() * 1000000000);
        String[] arr = nro.split("|");
        String nroFormat = arr[0]+arr[2]+arr[4];
        //String pdfFileName = "C:\\pdfdurocrom/"+nro+"_"+random+".pdf"; //LOCAL
        String pdfFileName = conexionBD.getRutaPaso() + nroFormat + "_" + random + ".pdf"; //SERVIDOR
        
//        System.out.println("Nro de O.T.: " + nroFormat);
        
        StringBuffer URL = request.getRequestURL();
        String url = URL.delete(URL.lastIndexOf("/"), URL.length()).toString();
        FileOutputStream archivo = new FileOutputStream(pdfFileName);
        
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession s = request.getSession();
            Connection _connMy = null;

            String opcion = request.getParameter("opcion");
            String k = "";            
            try {
                _connMy = conexionBD.Conectar((String) s.getAttribute("organizacion"));
                CallableStatement sp_usu = _connMy.prepareCall("{call sp_informeot_pdf(?,?)}");
                
                sp_usu.setString(1, opcion);
                sp_usu.setString(2, nro);
                
                sp_usu.execute();
                
                final ResultSet rs = sp_usu.getResultSet();                
                
                try {                    
                    
                    //PdfReader reader = new PdfReader("http://localhost:8080/Durocrom/images/Formato_COT_vendedor_1Pag.pdf"); // LOCAL 
                    PdfReader reader = new PdfReader(url + "/images/Formato_COT_InformeOT_1Pag.pdf"); // SERVIDOR
                    PdfStamper stamper = new PdfStamper(reader, archivo); // output PDF
                    BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
                    
                    //loop on pages (1-based)
                    rs.beforeFirst();    

                    while (rs.next()) {                                                                             
                        // get object for writing over the existing content;
                        // you can also use getUnderContent for writing in the bottom layer
                        PdfContentByte over = stamper.getOverContent(1);
                        
                        // HOJA 1
                        
                        // Configura texto "Nro OT"
                        String nroOt = rs.getString("nro_ot"); 
                        over.beginText();
                        over.setFontAndSize(bf, 8);    // set font and size
                        over.setTextMatrix(195, 355);   // set x,y position (0,0 is at the bottom left)
                        over.showText(nroOt);  // set text
                        over.endText();                        
                        
                        // Configura texto "Fecha entrega"
                        String fechaEntrega = rs.getString("fecha_entrega"); 
                        over.beginText();
                        over.setFontAndSize(bf, 6);    // set font and size
                        over.setTextMatrix(130, 340);   // set x,y position (0,0 is at the bottom left)
                        over.showText(fechaEntrega);  // set text
                        over.endText();                        
                        
                        // Configura texto "Nombre Cliente"
                        String cliente = rs.getString("cliente"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 322);   // set x,y position (0,0 is at the bottom left)
                        over.showText(cliente);  // set text
                        over.endText();
                        
                        // Configura texto "Pieza"
                        String pieza = rs.getString("pieza"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 311);   // set x,y position (0,0 is at the bottom left)
                        over.showText(pieza);  // set text
                        over.endText();
                        
                        // Configura texto "Nuevo/Usado"
                        String nuevoUsado = rs.getString("nuevo_usado"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(204, 311);   // set x,y position (0,0 is at the bottom left)
                        over.showText(nuevoUsado);  // set text
                        over.endText();
                        
                        // Configura texto "Material Base"
                        String materialBase = rs.getString("materialbase"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 293);   // set x,y position (0,0 is at the bottom left)
                        over.showText(materialBase);  // set text
                        over.endText();
                        
                        // Configura texto "Corriente"
                        String corriente = rs.getString("corriente"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(203, 293);   // set x,y position (0,0 is at the bottom left)
                        over.showText(corriente);  // set text
                        over.endText();
                        
                        // Configura texto "Trabajo"
                        String trabajo = rs.getString("trabajo"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 283);   // set x,y position (0,0 is at the bottom left)
                        over.showText(trabajo);  // set text
                        over.endText();
                        
                        // Configura texto "Largo"
                        String largo = rs.getString("largo") + " mm"; 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 273);   // set x,y position (0,0 is at the bottom left)
                        over.showText(largo);  // set text
                        over.endText();                        
                        
                        // Configura texto "Ancho"
                        String ancho = rs.getString("ancho") + " mm"; 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(196, 273);   // set x,y position (0,0 is at the bottom left)
                        over.showText(ancho);  // set text
                        over.endText();
                        
                        // Configura texto "Superficie"
                        String superficie = rs.getString("superficie") + " dm2"; 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(123, 264);   // set x,y position (0,0 is at the bottom left)
                        over.showText(superficie);  // set text
                        over.endText();
                        
                        // Configura texto "Superficie Total"
                        String superficieTotal = rs.getString("superficie_total") + " dm2"; 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size
                        over.setTextMatrix(196, 264);   // set x,y position (0,0 is at the bottom left)
                        over.showText(superficieTotal);  // set text
                        over.endText();
                        
                        // Configura texto "Observaciones"
                        String observaciones = rs.getString("observaciones"); 
                        over.beginText();
                        over.setFontAndSize(bf, 5);    // set font and size  
                        FormatObs(observaciones,over);
//                        if(observaciones.length() > 53)
//                        {
//                            over.setTextMatrix(87, 235);   // set x,y position (0,0 is at the bottom left)
//                            over.showText(observaciones.substring(0,53));  // set text
//                            if(observaciones.length()  108)
//                            {
//                                over.setTextMatrix(87, 230);// set x,y position (0,0 is at the bottom left)
//                                over.showText(observaciones.substring(53,107));  // set text
//                            }
//                            if(observaciones.length() > 160)                        
//                            {
//                                over.setTextMatrix(87, 225);   // set x,y position (0,0 is at the bottom left)
//                                over.showText(observaciones.substring(107,160));  // set text
//                            }else
//                                
//                            
//                            over.setTextMatrix(87, 220);   // set x,y position (0,0 is at the bottom left)
//                            over.showText(observaciones.substring(160,observaciones.length()));  // set text
//                            
//                        }
//                        else
//                        {
//                            over.setTextMatrix(87, 234);   // set x,y position (0,0 is at the bottom left)
//                            over.showText(observaciones);  // set text
//                        }
                        
                        over.endText();
                        
                        //  Configuracion de procesos de la O.T.
                        
                        String json_codproc = rs.getString("codigo_procesos");
                        String[] arr_json_codproc = json_codproc.replace("\"","").replace("[","").replace("]","").split(",");
                        
                        String json_cantproc = rs.getString("cantidad_procesos");
                        String[] arr_json_cantproc = json_cantproc.replace("\"","").replace("[","").replace("]","").split(",");
                        
                        String json_totalproc = rs.getString("total_procesos");
                        String[] arr_json_totalproc = json_totalproc.replace("\"","").replace("[","").replace("]","").split(",");
                        
                        for (int x = 0; x < arr_json_codproc.length; x++){
                            System.out.println("X: " + x);                           
                            System.out.println("Codigos de Procesos: " + arr_json_codproc[x] + " - Cantidad de Procesos: " + arr_json_cantproc[x]);                           
                        }
                        
                        String marcaX = "X";
                        
                        
                        // Configura texto "Rectificado Previo"                        
                        if ((arr_json_codproc[0].equals("1")) && (!arr_json_totalproc[0].equals("0"))){                            
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 196);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Cromado"                                                
                        if ((arr_json_codproc[1].equals("2")) && (!arr_json_totalproc[1].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 196);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Rectificado Final"                        
                        if ((arr_json_codproc[2].equals("3")) && (!arr_json_totalproc[2].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 158);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                                                
                        // Configura texto "Bandeo"
                        if ((arr_json_codproc[3].equals("4")) && (!arr_json_totalproc[3].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 139);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Bruñido"
                        if ((arr_json_codproc[4].equals("5")) && (!arr_json_totalproc[4].equals("0"))){ 
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 120);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Torno"
                        if ((arr_json_codproc[5].equals("6")) && (!arr_json_totalproc[5].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 103);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Fresa"
                        if ((arr_json_codproc[6].equals("7")) && (!arr_json_totalproc[6].equals("0"))){ 
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 87);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Soldadura"
                        if ((arr_json_codproc[7].equalsIgnoreCase("8")) && (!arr_json_totalproc[7].equals("0"))){                            
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(133, 196);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Cambio Sellos"                        
                        if ((arr_json_codproc[8].equalsIgnoreCase("9")) && (!arr_json_totalproc[8].equals("0"))){                            
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(208, 177);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Descromado"
                        if ((arr_json_codproc[9].equalsIgnoreCase("10")) && (!arr_json_totalproc[9].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(208, 158);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Horas Hombre"
                        if ((arr_json_codproc[10].equalsIgnoreCase("11")) && (!arr_json_totalproc[10].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(208, 139);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        
                        // Configura texto "Tramiento Termico"
                        if ((arr_json_codproc[11].equalsIgnoreCase("12")) && (!arr_json_totalproc[11].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(208, 120);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();
                        }
                        // Configura texto "Proceso Nuevo"
                        if ((arr_json_codproc[12].equalsIgnoreCase("13")) && (!arr_json_totalproc[12].equals("0"))){
                            over.beginText();
                            over.setFontAndSize(bf, 10);    // set font and size
                            over.setTextMatrix(208, 103);   // set x,y position (0,0 is at the bottom left)
                            over.showText(marcaX);  // set text
                            over.endText();                        
                        }
                        
                    }
                    
                    //response.setContentLength((int) stamper.getReader().getFileLength());
                    stamper.close();
                    stamper.flush();
                    
                    archivo.close();
                    archivo.flush();

                    String fileType = "application/pdf";
                    response.setContentType(fileType);

                    response.setHeader("Content-disposition", "attachment; filename=PDFInformeOT.pdf");

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
    
    private void FormatObs(String observaciones, PdfContentByte over)
    {
        int x = 0;
        int y = 0;
        int z = 0;
                
        if (observaciones.length() > 53)
        {
            over.setTextMatrix(87, 235);   // set x,y position (0,0 is at the bottom left)
            over.showText(observaciones.substring(0,53));  // set text                        
            if(observaciones.length() > 53 && observaciones.length() < 108)
            {                                   
                over.setTextMatrix(87, 230);// set x,y position (0,0 is at the bottom left)            
                over.showText(observaciones.substring(53,107));  // set text
            }                          
            if(observaciones.length() > 107 && observaciones.length() < 161)                        
            {                        
                over.setTextMatrix(87, 230);// set x,y position (0,0 is at the bottom left)         
                over.showText(observaciones.substring(53,107));  // set text                        
                over.setTextMatrix(87, 225);   // set x,y position (0,0 is at the bottom left)
                over.showText(observaciones.substring(107,160));  // set text
            }
            if(observaciones.length() > 160)                        
            {            
                over.setTextMatrix(87, 230);// set x,y position (0,0 is at the bottom left)         
                over.showText(observaciones.substring(53,107));  // set text            
                over.setTextMatrix(87, 225);   // set x,y position (0,0 is at the bottom left)
                over.showText(observaciones.substring(107,160));  // set text
                over.setTextMatrix(87, 220);   // set x,y position (0,0 is at the bottom left)
                over.showText(observaciones.substring(160,observaciones.length()));  // set text
            }
        }else
        {
            over.setTextMatrix(87, 235);   // set x,y position (0,0 is at the bottom left)
            over.showText(observaciones);  // set text        
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
