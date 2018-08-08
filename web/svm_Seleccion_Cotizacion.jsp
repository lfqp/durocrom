
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
<script src="js/CRUD_cotizacion.js" type="text/javascript"></script>
<script src="js/CRUD_cotizacion_det.js" type="text/javascript"></script>
<style type="text/css">
.paging_full_numbers a.paginate_button,
.paging_full_numbers a.paginate_active {
	border: 1px solid #aaa;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}
.paging_full_numbers a.paginate_button {
	background-color: #ddd;
}

.paging_full_numbers a.paginate_button:hover {
	background-color: #ccc;
	text-decoration: none !important;
}

.paging_full_numbers a.paginate_active {
	background-color: #99B3FF;
}

.paging_full_numbers a.paginate_button {
    color: #fff !important;
}
.paging_full_numbers a.paginate_active {
    color: #fff !important;
}

table tfoot tr th{
    border-top: 1px solid #585858;
    background: #DFFFDE;
}

table tfoot tr th.pull-right{
    text-align: right;
}

table tfoot tr th.borderLeft{
    border-left: 1px solid #6E6E6E;
}
</style>
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
$(document).ready(function(){
   $('#tblActComercial').DataTable( {//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
        "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
        bFilter: false, bInfo: false,
        "bLengthChange": false,
        "bAutoWidth": false,
       "aoColumnDefs": [{ 'bSortable': false, 'aTargets': [1,2,3,4,5,6,7,8] }]
    });
    filtraCotizacion();
});
function seleccion_registro_actividadComercial(id){
    var numero = $("#corrCotiza").val();   
    var secu = $("#secu").val();        
    var ots     = $("#nroOts").val();
    var estado  = $("#estadoCoti").val();    
    if($("#habilitaActCom").val() == 0 || numero == null )
    {
        FuncionErrores(239);
        return false;
    }
    if (id == 2)
    {
        if (ots >0 ){
            FuncionErrores(259);
            return false;
        }    
        
        if (estado == "Aprobado por cliente"){
            FuncionErrores(258);
            return false;
        }
    }    
    
    var accion=id==2?"modifica":"consulta";
    
    location.href='svm_Actualiza_Cotizacion.jsp?par='+id+'&correlativo='+numero+'&secuencia='+numero+"&accion="+accion;
}
function SelectOT()
{
    var numero = $("#corrCotiza").val();   
    var cotiza = $("#nroCoti").val();
    var ots = $("#nroOts").val();
    var estado = $("#estadoCoti").val();
    var habilita = $("#habilitaActCom").val();        
        
    if (habilita == 0)
    {
        FuncionErrores(239);
        return false;
    }
        
    if (ots > 0 || estado != "Aprobado por cliente"){
        FuncionErrores(254);
        return false;
    }else{        
        GeneraOrdenTaller(cotiza);        
        return false;
    }    
    
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};
</script>
</head>
<body id="principal"> 
<input type="hidden" id="secu" value="<%=secu%>" />
    <table id="header2">
        <tr>
            <td colspan="5">
                <div class="tablafiltrar">
                    <table align="center">
                        <tr>
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
                                <!-- script que define y configura el calendario--> 
                                <script type="text/javascript"> 
                                    Calendar.setup({ 
                                    inputField     :    "txt_filtroComercial_ingreso",     // id del campo de texto 
                                    ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                    button     :    "lanzador"     // el id del bot�n que lanzar� el calendario 
                                    }); 
                                </script>
                            </td>
                            <td align="right">&nbsp;&nbsp;Fecha&nbsp; Hasta:</td>
                            <td>
                                <input type = "text" readonly value="<%out.print(ultimoDiaMes);%>" name = "txt_filtroComercial_final" id = "txt_filtroComercial_final" size="12"/>
                                <img src="images/calendario.png" width="16" height="16" border="0" title="Fecha Inicial" id="lanza" />
                                <!-- script que define y configura el calendario--> 
                                <script type="text/javascript"> 
                                    Calendar.setup({ 
                                    inputField     :    "txt_filtroComercial_final",     // id del campo de texto 
                                    ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                    button     :    "lanza"     // el id del bot�n que lanzar� el calendario 
                                    }); 
                                </script>
                                <!--Codigo Sistemas SA-->
                            </td>
                            <td>&nbsp;&nbsp;Vendedor:</td>
                            <td>
                                <select id="slt_filtroComercial_vendedor" name="slt_filtroComercial_vendedor"/>
                                    <option value="">--Seleccione--</option>     
                                   <%
                                        Statement stmt = null;
                                        ResultSet rsQuery = null;                                       
                                        stmt = _connMy.createStatement();
                                        String q = "";

                                        q="select rut, nombre_user from svm_mae_usuarios WHERE tipo = 'Vendedor'";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("rut")+"'>"+rsQuery.getString("nombre_user")+"</option>");
                                        }
                                    %>
                            </td>
                            <td>&nbsp;&nbsp;Estado:</td>
                            <td>
                                <select id="slt_filtroComercial_estado" name="slt_filtroComercial_estado">
                                     <option value="">--Seleccione--</option>
                                    <%                                   
                                        stmt = _connMy.createStatement();

                                        q="select id, descripcion from svm_mae_tablas where tablas='Estado_Cotizacion'";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("id")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                                <td> &nbsp;N�&nbsp;Cotizaci�n</td>
                                <td> 
                                    <select id="slt_filtroComercial_nrocotizacion" name="slt_filtroComercial_nrocotizacion">
                                     <option value="">--Seleccione--</option>
                                       <%                                   
                                        stmt = _connMy.createStatement();

                                        q="select numero_cotizacion from svm_cotizaciones_head ";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("numero_cotizacion")+"'>"+rsQuery.getString("numero_cotizacion")+"</option>");
                                        }
                                    %>
                                    </select>
                                </td>
                                
                                <td> &nbsp;N�&nbsp;OT</td>
                                <td>
                                    <select id="slt_filtroComercial_nrOT" name="slt_filtroComercial_nrOT">
                                     <option value="">--Seleccione--</option>
                                      <%                                   
                                        stmt = _connMy.createStatement();

                                        q="select numero_cotizacion from svm_cotizaciones_head ";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("numero_cotizacion")+"'>"+rsQuery.getString("numero_cotizacion")+"</option>");
                                        }
                                    %>
                                    </select>
                                </td>
                            <td colspan="2">
                               &nbsp;&nbsp;
                                <input class ="botonera" type="submit" name="btnBuscar" onclick="filtraCotizacion()" value="Buscar" />                                                            
                                <input class ="botonera" type="submit" name="btnEliminaFiltro" onclick="javascript:document.location.href='svm_Seleccion_Cotizacion.jsp';" value="Limpiar Filtro" />                                
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="10">
                <div class="datagrid">
                    <table id="tblActComercial">
                        <thead>
                            <tr>
                                <th width="2.4%"></th>
                                <th width="5%">N&uacute;mero Cotizaci&oacute;n</th>
                                <th width="7%">Vendedor</th>
                                <th width="5.34%">Fecha Emisi&oacute;n</th>
                                <th width="6.17%">Rut Cliente</th>
                                <th width="19.03%">Raz&oacute;n</th>
                                <th width="7.26%">Estado</th>
                                <th width="7.14%">Total</th>
                            </tr>
                        </thead>                        
                        <tbody>                            
                            <%                                
                                String var = "select_all";
                                
                                CallableStatement sp_usu = _connMy.prepareCall("{call sp_cotizacion(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                sp_usu.setString(1,var);
                                sp_usu.setInt(2,0);
                                sp_usu.setDate(3,null);
                                sp_usu.setString(4,"");
                                sp_usu.setInt(5,0);
                                sp_usu.setInt(6,0);
                                sp_usu.setString(7,"");
                                sp_usu.setString(8,"");
                                sp_usu.setString(9,"");
                                sp_usu.setString(10,"");
                                sp_usu.setDate(11,null);
                                sp_usu.setInt(12,0);
                                sp_usu.setString(13,"");
                                sp_usu.setString(14,"");
                                sp_usu.setString(15,"");
                                sp_usu.setString(16,"");
                                sp_usu.setString(17,"");
                                sp_usu.setString(18,"");
                                sp_usu.setString(19,"");
                                sp_usu.setString(20,"");
                                sp_usu.setString(21,"");
                                sp_usu.setString(22,"");
                                sp_usu.setString(23,"");
                                sp_usu.setString(24,"");
                                sp_usu.setInt(25,0);
                                sp_usu.setString(26,"");
                                sp_usu.setString(27,"");
                                sp_usu.setString(28,"");
                                sp_usu.registerOutParameter(1, Types.VARCHAR);                                                          
                                sp_usu.execute();
                                
                                final ResultSet rs = sp_usu.getResultSet();
                                
                                String cla = "";
                                Integer total = 0;
                                while(rs.next())
                                {
                                    if(cont % 2 == 0)
                                    {
                                        cla = "alt";
                                    }else
                                    {  
                                        cla = "";

                                    }
                                    out.println("<tr id='filaTablaActComercial"+cont+"' class='"+cla+"'>");
                                    
                                    String totalBruto = rs.getString("total_bruto");
                            %>
                            <td>
                                <!--a href="javascript: onclick=ModificaActComercial(<%--cont--%>)"> >></a-->
                                <a href="javascript: onclick=ModificaActComercial(<%=cont%>,<%= rs.getString("ots")%>,<%= rs.getString("numero_cotizacion")%>,'<%= rs.getString("estado")%>')"> >></a>
                                <input type="hidden" value="0" id="habilitaActCom" name="habilitaActCom" />
                                <input type="hidden" value="" id="corrCotiza" />
                                <input type="hidden" value="" id="nroOts" />
                                <input type="hidden" value="" id="nroCoti" />
                                <input type="hidden" value="" id="estadoCoti" />
                            </td>
                            <td id="num_cotizacion<%=cont%>"><%= rs.getString("numero_cotizacion")%></td>
                            <td id="emititda_por<%=cont%>"><%= rs.getString("vendedor")%></td>
                            <td id="fecha_emision<%=cont%>"><%=rs.getString("fecha_emision")%></td>
                            <td id="rut_cli<%=cont%>"><%= rs.getString("rut_cli")%></td>
                            <td id="razon_cli<%=cont%>"><%= rs.getString("razon_social")%></td>
                            <td id="estado<%=cont%>"><%= rs.getString("estado")%></td>
                            <td id="total<%=cont%>"><%= totalBruto%></td>
                            <td style="display: none" id="secuencia<%=cont%>"><%= rs.getString("dias_habiles")%></td>
                            <%
                                    total += Integer.parseInt(totalBruto.replace(".", ""));
                                    out.println("</tr>");                                   
                                    cont ++;                                    
                                }   
                            %>     
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="7" class="pull-right">Total</th>
                                <th class="borderLeft thTotal"><%=total.toString()%></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <!--Sistemas sa 20/10/2015-->
            <td>
                <%
                if(!tipoUser.equals("Backoffice")){
                    %><input class ="botonera" type="button" name="btn_actComercial_Ingresa" value="Ingresar" onClick="window.location.href='svm_Actualiza_Cotizacion.jsp?accion=ingresar&par=1&secuencia='+<%=secu%>"/>	<%;
                }%> 
                <input class ="botonera" type="submit" name="btn_actComercial_Modifica" id="btn_actComercial_Modifica" value="Modificar" onClick="seleccion_registro_actividadComercial(2)" />	
                <input class ="botonera" type="submit" name="btn_actComercial_Consulta" value="Consultar" onClick="seleccion_registro_actividadComercial(3)"  />
                <input class ="botonera" type="submit" name="btn_actComercial_OT" value="Crea OT" onClick="SelectOT()"  />
<!--                <input class ="botonera" style="display: none" type="button" onclick="desmarca_registro_actividadComercial()" id="btn_actComercial_cancela" name="btn_actComercial_cancela" value="Desmarcar" />-->
            </td>	
        </tr>
    </table>                            
</body>                
<script src="js/autoNumeric.js" type="text/javascript" ></script>
</html>
    