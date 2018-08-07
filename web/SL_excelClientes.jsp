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
    response.setHeader("Content-Disposition", "attachment; filename=\"Clientes.xls\"");
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
                    <td colspan="2" style="font-size: 18px; font-weight: bold">Maestro de clientes</td>
                </tr>
                <tr>
                <td style="font-size: 16px;">Fecha</td>
                <td style="font-size: 16px;"><%=dateFormat.format(date)%></td>
                </tr>
                <tr></tr>
                <tr>
                <td colspan="8" align="center">Información general</td>
                <td colspan="5" align="center">Contacto comercial</td>
                <td colspan="5" align="center">Contacto contable</td>
                </tr>
                <th>Rut cliente</th>
                <th>Razón social</th> 
                <th>giro</th>
                <th>Dirección</th>
                <th>Comuna</th>             
                <th>Ciudad</th>                
                <th>Estado de la empresa</th>
                <th>Vendedor</th>
                <th>Nombre completo</th>
                <th>Cargo</th>
                <th>N° celular</th>
                <th>N° fijo</th>
                <th>eMail</th>         
                <th>Nombre completo</th>
                <th>Cargo</th>
                <th>N° celular</th>
                <th>N° fijo</th>
                <th>eMail</th>                
            </thead>
            <tbody>
                <%=s.getAttribute("dataExcel")%>
            </tbody>
        </table>
    </body>
</html>