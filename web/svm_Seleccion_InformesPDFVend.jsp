<%@page import="com.itextpdf.tool.xml.XMLWorkerHelper"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.PageSize"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Types"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAL.conexionBD"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Seleccion Orden Taller</title>
<link rel="icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon"/>   
<link rel="stylesheet" type="text/css" href="css/estilo_modulo1.css"/>
<link href="css/style_tabla.css" type="text/css" rel="STYLESHEET"/>
<link href="css/solutel.css" type="text/css" rel="STYLESHEET"/>
<!--Codigo Sistemas SA-->
<link href="css/calendario.css" type="text/css" rel="stylesheet"/>
<script src="js/calendar.js" type="text/javascript"></script>
<script src="js/calendar-es.js" type="text/javascript"></script>
<script src="js/calendar-setup.js" type="text/javascript"></script>
<script src="js/Funcion_Errores.js" type="text/javascript"></script>
<!-- Librerias Jquery -->
<script src ="js/jquery-1.10.2.js" type="text/javascript "></script>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery-2.1.3.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.dataTables.js" type="text/javascript"></script>
<script src="js/messages_es.js" type="text/javascript" ></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<%  
    HttpSession s = request.getSession();
    Connection _connMy = null;
    int id= 1;
    int cont =0; 
    String supervisor = "";
    String rut = "";
    String tipoUser = "";
    String nroSecuencia="";
    String secu = "";
    String k = "";
    
    try
    {
        if(s.getAttribute("nom")==null)
        {
            response.sendRedirect("login.jsp");
        }
        if(s.getAttribute("tipo") != null)
        {
            tipoUser =(String)s.getAttribute("tipo");
        }
        if(s.getAttribute("supervisor")!= null)
        {
             supervisor = (String)s.getAttribute("supervisor");
        }
        if(s.getAttribute("rut")!= null)
        {
             rut = (String)s.getAttribute("rut");
        }
        

    }catch(Exception e)
    {
        System.out.println("Error: "+ e.getMessage());
        response.sendRedirect("login.jsp");
    }
          
%>
<script type="text/javascript">


</script>
</head>
<body id="principal"> 
    <table id="header2">
        <tr>
            <td colspan="5">
                <div class="tablafiltrar">
                    <table align="center">
                        <tr>
                            <td align="right">Nro. Cotizaci&oacute;n:</td>	
                            <td>
                                
                            </td>
                            <td colspan="2">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <form action="ServletPDFVendedor" method="post" style="float:right;width:1020px">
                                <input type="text" name="txt_filtroImprimir_ingreso" id="txt_filtroImprimir_ingreso" size="12" maxlength="11" onkeypress="return validarSiNumero(event)"/>                                    
                                <input class="botonera" type="submit" id="btnImprimir" name="btnImprimir" value="Imprimir" /> 
                                <input type="hidden" id="opcionPDF" name="opcionPDF" value="vendedor" />
                                </form> 
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>                            
</body>
</html>
    