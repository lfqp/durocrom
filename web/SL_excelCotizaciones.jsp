<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    HttpSession s = request.getSession();
    if(s.getAttribute("nom")==null)
    {
        response.sendRedirect("login.jsp");
    }
    response.setContentType("application/vnd.ms-excel");
    String mes = request.getParameter("mes");
    String anio = request.getParameter("anio");
    response.setHeader("Content-Disposition", "attachment; filename=\"Cotizaciones.xls\"");
        
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        .grillaExcel{
            font: normal 14px/150% Calibri, Helvetica, sans-serif;
        }   
        .grillaExcel thead th{
            //font-size: 11px;
            //font-family: calibri;
            //font-weight: bold;
            //border-left: 1px solid #36752D;
            border-left: 1px solid;
        }
        .grillaExcel tbody tr td{             
            border: 1px solid;
            //border-bottom: 1px solid #000000;
        }
        .grillaExcel thead tr td{             
            border: 1px solid;
            //border-bottom: 1px solid #000000;
        }        
    </style>    
    <title>JSP Page</title>
    </head>
    <body>
        <table class="grillaExcel">
            <thead> 
                <tr>
                <td colspan="2" style="font-size: 18px; font-weight: bold">Control cotización</td>
                </tr>
                <tr>
                <td style="font-size: 16px;">Fecha</td>
                <td style="font-size: 16px;"><%=dateFormat.format(date)%></td>
                </tr>
                <tr></tr>
                <tr>
                <td colspan="5" align="center">Información general cotización</td>
                <td colspan="7" align="center">Estados cotización</td>
                <td colspan="12" align="center">Descripción pieza</td>
                <td colspan="12" align="center">Descripción procesos</td>
                <td colspan="5" align="center">Montos</td>
                </tr>
                <th>Rut cliente</th>
                <th>N° cotización</th> 
                <th>Codigo</th> 
                <th>Fecha de emisión</th>
                <th>D&iacute;as H&aacute;biles</th>
                <th>Emitida por:</th>             
                <th>En espera de aprobación</th>                
                <th>Fecha1</th>
                <th>Aprobada</th>
                <th>Fecha2</th>
                <th>Rechazada</th>
                <th>Fecha3</th>
                <th>OC aprobación</th>
                <th>Pieza</th>         
                <th>Nuevo/Usado</th>
                <th>Material Base</th>
                <th>Trabajo abreviado</th>
                <th>Diametro</th>                
                <th>Largo</th>         
                <th>Ancho</th>
                <th>Superficie</th>
                <th>Superficie total</th>
                <th>Corriente</th>
                <th>Oservaciones</th>                
                <th>Rectificado previo</th>         
                <th>Cromado</th>
                <th>Rectificado final</th>
                <th>Bandeo</th>
                <th>Bruñido</th>
                <th>Torno</th>                
                <th>Fresa</th>         
                <th>Soldadura</th>
                <th>Cambio de sellos</th>
                <th>Descromado</th>
                <th>Horas hombre</th>
                <th>Tratamiento termico</th>                
                <th>Total procesos</th>
                <th>Total materiales</th>
                <th>Total general pieza (neto)</th>
                <th>Iva</th>
                <th>Total bruto</th>                
            </thead>
            <tbody>
                <%=s.getAttribute("dataExcel")%>
            </tbody>
        </table>
    </body>
</html>