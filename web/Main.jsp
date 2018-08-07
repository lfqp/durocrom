<%@page import="java.sql.Types"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="DAL.conexionBD"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<title>PORTAL DUROCROM</title>
<link rel="icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />   
<link rel="stylesheet" type="text/css" href="css/estilo_modulo1.css" />
<link href="css/style_tabla.css" type="text/css" rel="STYLESHEET" />
<link href="css/solutel.css" type="text/css" rel="STYLESHEET" />
<!--Codigo Sistemas SA-->
<link href="css/calendario.css" type="text/css" rel="stylesheet" />
<script src="js/calendar.js" type="text/javascript"></script>
<script src="js/calendar-es.js" type="text/javascript"></script>
<script src="js/calendar-setup.js" type="text/javascript"></script>
<script src="js/label.js" type="text/javascript"></script>
<script src ="js/jquery-1.10.2.js" type="text/javascript "></script>
<!--<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>-->
<!--<script src="js/jquery.validate.js" type="text/javascript"></script>-->
<!--<script src="js/jquery-2.1.3.js" type="text/javascript"></script>-->
<!--<script src="js/jquery.validate.min.js" type="text/javascript"></script>-->
<script src="js/jquery.validate.js" type="text/javascript"></script>
<!--<script src="js/jquery.min.js" type="text/javascript"></script>-->
<script src="js/messages_es.js" type="text/javascript" ></script>
<script src="js/Funcion_Errores.js" type="text/javascript"></script>

<!--Codigo Sistemas SA-->

<%
    HttpSession s = request.getSession();  
    Connection _connMy = null;
    CallableStatement funcion = null;
    String tipoUser = "";
    String timeOut = "";
    int tiempoRestante = 0;
    try{
        if(s.getAttribute("nom")==null)
        {
            response.sendRedirect("login.jsp");
        }
        
        if(s.getAttribute("tipo") != null)
        {
            tipoUser =(String)s.getAttribute("tipo");
        }
        _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion")); 
        Context env = (Context)new InitialContext().lookup("java:comp/env");
        timeOut = (String)env.lookup("TimeOut");
        tiempoRestante = Integer.parseInt(timeOut);  
        
        //funcion = _connMy.prepareCall(" ? = call fn_controlAcceso(?, ?)");
        //funcion.registerOutParameter(1, Types.INTEGER);
        //funcion.setString(2, "Cotizaciones");
        //funcion.setString(3, "Administrador");
        //funcion.execute();
        //int tienePermiso = funcion.getInt(1);
       //out.print("<script> alert('tiene permiso para cotizaciones: "+tienePermiso+"');</script>");
        
        
    }catch(Exception e)
    {
        e.printStackTrace();
        response.sendRedirect("login.jsp");
    }
%>

<%!
    public Boolean tieneAccesoModulo(HttpSession s, String modulo){
        Boolean acceso = false;
        Connection conection = null;
        CallableStatement st = null;
        String rolUsuario = (String)s.getAttribute("tipo");

        try{
            conection = conexionBD.Conectar((String)s.getAttribute("organizacion"));
            st = conection.prepareCall("{? = call fn_controlAcceso(?, ?)}");
            st.registerOutParameter(1, Types.INTEGER);
            st.setString(2, modulo);
            st.setString(3, rolUsuario);
            st.execute();
            int tienePermiso = st.getInt(1);
            if(tienePermiso > 0){
                acceso = true;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return acceso;
    }
%>
<script type="text/javascript">
    var cont = "<%=tiempoRestante%>";
function contador(){        
    cont --;
    if(cont=== 0)
    {
        location.href="login.jsp";
    }else
    {
        setTimeout(contador,1000);
    }
}   
function Actividad()
{
   cont = "<%=tiempoRestante%>";
}


// Set iframe height


// Reset iframe height after window resize
$(function(){
    var content_height=getDocHeight()-200;
    document.getElementById('FrGral').style.height=content_height+"px";
    
    $(window).resize(function(){
    	var content_height=getDocHeight()-200;
        document.getElementById('FrGral').style.height=content_height+"px";
    });
});

function getDocHeight() {
    var D = document;
    return Math.max(
        //Math.max(D.body.scrollHeight, D.documentElement.scrollHeight),
        //Math.max(D.body.offsetHeight, D.documentElement.offsetHeight),
        Math.max(D.body.clientHeight, D.documentElement.clientHeight)
    );
}
</script>
</head>    
<body id="principal" onmouseover="Actividad()" onload="contador()">
<table id="header">
   
    <thead>
        <tr>            
            <td><img id="logo" src="images/logotipos/logo_solutel.png" /></td>
                <td colspan ="4">
                    <div class = "tituloModulo">
                        <label for="male"> <%=s.getAttribute("nom")%></label>
                        <b> - <%= s.getAttribute("organizacion")%></b>
                        <a href="login.jsp" >
                            <img src="images/apagar.jpg" border="0"/>

                        </a>
                    </div>
                </td>
        </tr>
        <!--botones pesta�as -->
        <tr>
            <td colspan="5" > 
              <div id="pestana">
                    <ul class="menu">
                        <li>
                            <a class="link" href="svm_Escritorio.jsp" onclick="CambioEsc(cambio)" target="FrGral">
                                Escritorio
                            </a>
                        </li>
                        <li class="submenu">
                            <a class="link" href="svm_Escritorio.jsp" onclick="CambioEsc(cambio)" target="FrGral">
                                Documentos
                            </a>
                         <div class="submenu-contenido">
                                <%
                                    //Debe repetir este if por cada modulo... :(... :(
                                    if(tieneAccesoModulo(s, "Cotizaciones")){
                                        //Tiene acceso al modulo Cotizaciones
                                        %>
                                        <a class="link" href="svm_Seleccion_Cotizacion.jsp" onclick="CambioAct(cambio)" target="FrGral">
                                            Cotizaciones
                                        </a>
                                        <%
                                    }else{
                                        //NO tiene acceso al modulo.
                                %>
                                        <a class="link"  onclick=FuncionErrores(240); style="cursor: pointer;" target="FrGral">
                                            Cotizaciones
                                        </a>
                                <%
                                    }
                                %>
                                
                                <%
                                    //Debe repetir este if por cada modulo... :(... :(
                                    if(tieneAccesoModulo(s, "Ordenes Taller")){
                                        //Tiene acceso al modulo Ordenes Taller
                                %>
                                        <a class="link" href="svm_Seleccion_OT.jsp" onclick="CambioTaller(cambio)" target="FrGral">
                                            Ordenes Taller 
                                        </a>
                                <%       
                                    }else{
                                        //NO tiene acceso al modulo.
                                %>
                                        <a class="link"  onclick=FuncionErrores(240); style="cursor: pointer;" target="FrGral">
                                            Ordenes Taller 
                                        </a>
                                <%
                                    }
                                %>
                                
                                <%--                                   
                                    if(tieneAccesoModulo(s, "Guias de Despacho")){
                                        //Tiene acceso al modulo Guias de Despacho
                                %>
                                        <a class="link" href="svm_Seleccion_GuiaDespacho.jsp" onclick="CambioGuia(cambio)" target="FrGral">
                                            Gu&iacute;as de Despacho
                                        </a>
                                <%       
                                    }else{
                                        //NO tiene acceso al modulo.
                                %>
                                        <a class="link"  onclick=FuncionErrores(240); style="cursor: pointer;" target="FrGral">
                                            Gu&iacute;as de Despacho 
                                        </a>
                                <%
                                    }
                                --%>
                                
                                <%--                                    
                                    if(tieneAccesoModulo(s, "Facturas")){
                                        //Tiene acceso al modulo Facturas
                                %>
                                        <a class="link" href="svm_Seleccion_Factura.jsp" onclick="CambioFatura(cambio)" target="FrGral">
                                            Facturas 
                                        </a>
                                <%       
                                    }else{
                                        //NO tiene acceso al modulo.
                                %>
                                        <a class="link"  onclick=FuncionErrores(240); style="cursor: pointer;" target="FrGral">
                                           Facturas
                                        </a>
                                <%
                                    }
                                --%>                                                         
                            </div>
                    <li>
                        <a class="link" href="svm_Seleccion_Clientes.jsp" onclick="CambioCli(cambio)" name="clientes"target="FrGral">
                          Clientes
                        </a>
                    </li>
                    <li>
                     <%
                    //Debe repetir este if por cada modulo... :(... :(
                    if(tieneAccesoModulo(s, "Configuracion")){
                    //Tiene acceso al modulo Cotizaciones
                    %>
                        <a class="link" href="svm_Mantencion_Configuracion.jsp"  onclick="CambioConf(cambio)" name="configuracion" target="FrGral">
                          Configuraci&oacute;n
                        </a>
                    <%
                    }else{
                      
                    //NO tiene acceso al modulo.
                    %>
                    
                        <a class="link"    onclick=FuncionErrores(240); style="cursor: pointer;" target="FrGral">
                          Configurac&oacute;n
                        </a>
                    <%
                        }
                    %>
                        
                        
                    </li>
                    <li class="submenu">
                        <a class="link" href="svm_Escritorio.jsp" onclick="CambioEsc(cambio)" target="FrGral">
                          Informes
                        </a>
                        <div class="submenu-contenido">
                            <a class="link" href="svm_Seleccion_InformesPDF.jsp" onclick="CambioImpCliente(cambio)" target="FrGral">
                                            PDF Cotizaciones Cliente
                            </a>
                            <a class="link" href="svm_Seleccion_InformesPDFVend.jsp" onclick="CambioImpVendedor(cambio)" target="FrGral">
                                            PDF Cotizaciones Vendedor
                            </a>     
                            </a>
                            <a class="link" href="svm_Seleccion_InformesExcel.jsp" onclick="CambioExcel(cambio)" target="FrGral">
                                            Informes Excel
                            </a>                             
                        </div>
                    </li>
                    <%--<li>
                        <a class="link" href="svm_Escritorio.jsp" onclick="CambioEsc(cambio)" target="FrGral">
                          Procesos
                        </a>
                    </li>--%>
                    </ul>
            </div>
            </td>
        </tr>
        <tr style="height: 40px">	
            <td colspan ="5" >
                <div class = "tituloSolicitud" id="cuadro" align="left"><label id="cambio"></label></div>	
            </td>
        </tr>
    </thead>
    <tfoot>
        <tr>
            <td colspan="10"><div id="banner" style="background:url(images/banner-index-verde.png);"></div></td>
        </tr>
        <tr>
            <td colspan="2">
                <img src="images/logo_sistemaspng.png" />
            </td>
            <td colspan="2" id="piePagina">
                <b>Direcci�n:</b> General Bari #165, Providencia - Santiago<br />
                <b>Tel�fono:</b> 28267010<br />
                <b>Email:</b> <a class="email" href="mailto:contacto@sistemassa.cl">contacto@sistemassa.cl</a>					
            </td>
        </tr>
        <tr>
            <td colspan="10"><div id="banner" style="background:url(images/banner-index-verde.png);"></div></td>
        </tr>	
    </tfoot>
    <tbody >
        <tr>
            <td colspan="6">
                <iframe name="FrGral" id="FrGral" frameborder="0" src="svm_Escritorio.jsp"></iframe>
            </td>
        </tr>
    </tbody>
</table>
</body>
</html>
