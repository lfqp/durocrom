
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Types"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
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
<title>Seleccion Actividad Comercial</title>
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

<%  
    HttpSession s = request.getSession();
    Connection _connMy = null;
    int id= 1;
    String supervisor = "";
    String rut = "";
    String tipoUser = "";
    String nroSecuencia="";
    String secu = "";
    String valorUF = "";
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
        _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));
        String query = "select fn_secuencia() as secuencia";
        ResultSet resultQuery = _connMy.createStatement().executeQuery(query);
        while(resultQuery.next())
        {  
            nroSecuencia = resultQuery.getString("secuencia");
        }        
        s.setAttribute("secu",nroSecuencia);
        secu = (String)s.getAttribute("secu");  

    }catch(Exception e)
    {
        System.out.println("Error: "+ e.getMessage());
        response.sendRedirect("login.jsp");
    }
%>
<script type="text/javascript">
    function validaSubmit(){
        if(document.getElementById("slt_Informe").value != ""){
            document.getElementById("formulario").submit();
        }else{
            alert("Seleccione un informe.")
        }
        
    }
</script>
</head>
<body id="principal"> 
<input type="hidden" id="secu" value="<%=secu%>" />
    <table id="header2">
        <tr>
            <td>

                <form id="formulario" action="ServletExcel" method="post" style="float:center;width:1020px">
                <div class="tablafiltrar">                                
                            <table align="center">
                            <tr>    
                            <td>Informe:</td>
                            <td>
                                <select id="slt_Informe" name="slt_Informe">
                                    <option value="">--Seleccione--</option>  
                                    <option value="clientes">Maestro de Clientes</option>
                                    <option value="cotizacion">Control Cotización</option>
                                    <option value="cotizacionNew">Control Cotización new</option>
                                </select>
                            </td>
                            <td align="right">Fecha&nbsp;Desde:</td>	
                            <td>
                                <%
                                    Date date = new Date();
                                    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                                    Calendar calendario = Calendar.getInstance();
                                    calendario.setTime(date);
                                    
                                    calendario.set(Calendar.DAY_OF_MONTH, calendario.getActualMinimum(Calendar.DAY_OF_MONTH));
                                    String primerDiaMes = formato.format(calendario.getTime());
                                    
                                    calendario.set(Calendar.DAY_OF_MONTH, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
                                    String ultimoDiaMes = formato.format(calendario.getTime());
                                %>
                                <!--Codigo Sistemas SA-->
                                <input type = "text" readonly value="<%out.print(primerDiaMes);%>" name = "txt_filtroComercial_ingreso" id= "txt_filtroComercial_ingreso" size="12" />
                                <img src="images/calendario.png" width="16" height="16" border="0" title="Fecha Inicial" id="lanzador" />
                            </td>
                            <td>
                                <!-- script que define y configura el calendario--> 
                                <script type="text/javascript"> 
                                    Calendar.setup({ 
                                    inputField     :    "txt_filtroComercial_ingreso",     // id del campo de texto 
                                    ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                    button     :    "lanzador"     // el id del botón que lanzará el calendario 
                                    }); 
                                </script>
                            </td>

                            <td>
                                
                            <td align="right">&nbsp;&nbsp;Fecha&nbsp;Hasta:</td>
                            <td>
                                <input type = "text" readonly value="<%out.print(ultimoDiaMes);%>" name = "txt_filtroComercial_final" id = "txt_filtroComercial_final" size="12"/>
                                <img src="images/calendario.png" width="16" height="16" border="0" title="Fecha Inicial" id="lanza" />
                            </td>
                            <td>
                                <!-- script que define y configura el calendario--> 
                                <script type="text/javascript"> 
                                    Calendar.setup({ 
                                    inputField     :    "txt_filtroComercial_final",     // id del campo de texto 
                                    ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                    button     :    "lanza"     // el id del botón que lanzará el calendario 
                                    }); 
                                </script>
                                <!--Codigo Sistemas SA-->
                            </td>
                            <td><input class ="botonera" type="button" name="btnExcel" value="Excel" onclick="validaSubmit();"/>  </td>
                            </td>
                            </tr>
                    </table>                                
                                                </div>
                            </form>
            </td>
        </tr>
    </table>                            
</body>
</html>
    