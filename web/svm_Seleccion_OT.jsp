<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DecimalFormat"%>
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
<script src="js/CRUD_OrdenTaller.js" type="text/javascript"></script>
<script src="js/CRUD_ordentaller_det.js" type="text/javascript"></script>
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
    CallableStatement sp_actualiza = null;
    int id= 1;
    int cont =0; 
    DecimalFormat nf = new DecimalFormat();
    String supervisor = "";
    String rut = "";
    String tipoUser = "";
    String nroSecuencia="";
    String secu = "";
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
        
        try{
            sp_actualiza = _connMy.prepareCall("{call sp_actualizaCondicion()}");                                            
            sp_actualiza.execute();
        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }            
        
                                
    }catch(Exception e)
    {
        System.out.println("Error: "+ e.getMessage());
        response.sendRedirect("login.jsp");
    }
%>
<script type="text/javascript">
$(document).ready(function(){
    $('#tblOrdenTaller').dataTable( {//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
        "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
        bFilter: false, bInfo: false,
        "bLengthChange": false,
        "bAutoWidth": false,
       "aoColumnDefs": [{ 'bSortable': false, 'aTargets': [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15] }]
    });
    //filtraOrdenTaller();
});

function seleccion_registro_actividadComercial(id){
    var numero = $("#corrOT").val();    
    var secu = $("#secu").val();
    var nroOT = $("#selectorOT").val();    
    var estOT = $("#estadoOT").val();
    if($("#habilitaActCom").val() == 0 || numero == null )
    {
        FuncionErrores(239);
        return false;
    }
    if (id == '2')
    {
        if (estOT == 'Terminado')
        {
            FuncionErrores(261);
            return false;
        }
    }
    
    
    var accion=id==2?"modifica":"consulta";
    
    $("#numeroOT").val(nroOT);
    $("#accion").val(accion);
    document.getElementById("ActualizaOT").submit();
    //location.href='svm_Actualiza_OT.jsp?numeroOT='+nroOT+"&accion="+accion;
}

function SelectOT(){
    
    var numero = $("#selectorOT").val();    
    
    if($("#habilitaActCom").val() == 0 || numero == null )
    {
        FuncionErrores(239);
        return false;
    }
    $("#txt_numero_ot").val(numero);
    document.getElementById("pdfOT").submit();
    //informeOT(numero);
    
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
                                    button     :    "lanzador"     // el id del botón que lanzará el calendario 
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
                                    button     :    "lanza"     // el id del botón que lanzará el calendario 
                                    }); 
                                </script>
                                <!--Codigo Sistemas SA-->
                            </td>
                            <td>&nbsp;&nbsp;Estado:</td>
                            <td>
                                <select id="slt_filtroComercial_estado" name="slt_filtroComercial_estado">
                                     <option value="">--Seleccione--</option>
                                    <%                                   
                                        Statement stmt = null;
                                        ResultSet rsQuery = null;                                       
                                        stmt = _connMy.createStatement();
                                        String q = "";                                        

                                        q="select descripcion from svm_mae_tablas where tablas='Estado_OrdenTaller'";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                            <td>
                                &nbsp;&nbsp;Numero OT: <input type="text" maxlength="11" id="txt_filtro_numeroOt" name="txt_filtro_numeroOt"/>
                            </td>
                            <td>
                                &nbsp;&nbsp;Numero Cotizacion: <input maxlength="11" type="text" id="txt_filtro_numeroCot" name="txt_filtro_numeroCot"/>
                            </td>
                            <td>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input class ="botonera" type="submit" name="btnBuscar" onclick="filtraOrdenTaller()" value="Buscar" />                                                            
                                <input class ="botonera" type="submit" name="btnEliminaFiltro" onclick="javascript:document.location.href='svm_Seleccion_OT.jsp';" value="Limpiar Filtro" />                                
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="5">
                <div class="datagrid">
                    <table id="tblOrdenTaller" >
                        <thead>
                            <tr>
                                <th ></th>
                                <th>D&iacute;as Restantes</th>
                                <th >N&uacute;mero OT</th>
                                <th >Cliente</th>
                                <th>Pieza</th>
                                <th>Di&aacute;metro</th>  
                                <th>Largo</th>
                                <th >Fecha Prometida</th>
                                <th>Condici&oacute;n</th>                                                                  
                                <th >N&uacute;mero Cotizaci&oacute;n</th>                                
                                <th >Fecha Emisi&oacute;n</th>
                                <th >Estado</th>
                                <th >Cantidad</th>
                                <th >Saldo</th>
                                <th>Total Neto</th>
                            </tr>
                        </thead>                        
                        <tbody>                            
                            <%                                
                                String var = "select_all";
                                Integer total = 0;
                                
                                CallableStatement sp_usu = _connMy.prepareCall("{call sp_ordentaller(?,?,?,?,?,?)}");
                                sp_usu.setString(1,var);
                                sp_usu.setString(2,"");
                                sp_usu.setInt(3,0);
                                sp_usu.setString(4,"");
                                sp_usu.setString(5,"");
                                sp_usu.setString(6,"");
                                sp_usu.registerOutParameter(1, Types.VARCHAR);                                                          
                                sp_usu.execute();
                                
                                final ResultSet rs = sp_usu.getResultSet();
                                String cla = "";
                                while(rs.next())
                                {
                                    if(cont % 2 == 0)
                                        cla = "alt";
                                    else
                                        cla = "";
                                    out.println("<tr id='filaTablaOrdenTaller"+cont+"' class='"+cla+"'>");
                            %>
                            <td>
                                <a href="javascript: onclick=ModificaOrdenTaller(<%=cont%>,'<%= rs.getString("numero_ordentaller")%>','<%= rs.getString("estado")%>')"> >></a>
                                <input type="hidden" value="0" id="habilitaActCom" name="habilitaActCom" />
                                <input type="hidden" value="" id="corrOT" />
                                <input type="hidden" id="selectorOT" />
                                <input type="hidden" id="estadoOT" />
                            </td>
                            <td id="diasR<%=cont%>"><%= rs.getString("dias_restantes")%></td>
                            <td id="numero_ot<%=cont%>"><%= rs.getString("numero_ordentaller")%></td>
                            <td id="cliente<%=cont%>"><%= rs.getString("razon_social")%></td> 
                            <td id="descPieza<%=cont%>"><%= rs.getString("desc_pieza")%></td>
                            <td id="diametro<%=cont%>"><%= rs.getString("diametro_interno")%></td>
                            <td id="largo<%=cont%>"><%= rs.getString("largo")%></td>
                            <td id="fecha_termino<%=cont%>"><%= rs.getString("fecha_prometida")%></td>
                            <td id="condicion<%=cont%>"><%= rs.getString("condicion")%></td>                                                                                    
                            <td id="num_cotizacion<%=cont%>"><%= rs.getString("numero_cotizacion")%></td>                              
                            <td id="fecha_emision<%=cont%>"><%=rs.getString("fecha_emision")%></td>                                                                                    
                            <td id="estado<%=cont%>"><%= rs.getString("estado")%></td>                            
                            <td id="cantidad<%=cont%>"><%= rs.getString("cantidad")%></td>
                            <td id="saldo<%=cont%>"><%= rs.getString("saldo")%></td>
                            <td id="totalNeto<%=cont%>" class="columnaTotal">$ <%=nf.format(Integer.parseInt(rs.getString("total_pieza")))%></td>
                            <td style="display: none" id="secuencia<%=cont%>"></td>
                            <%
                                    total += Integer.parseInt(rs.getString("total_pieza"));
                                    //rs.getString("secuencia");
                                    out.println("</tr>");                                   
                                    cont ++;                                    
                                }   
                            %>     
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="14" class="pull-right">Total</th>
                                <th class="borderLeft thTotal">$ <%=nf.format(total)%></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <!--Sistemas sa 20/10/2015-->
            <td>                
                <input class ="botonera" type="submit" name="btn_actComercial_Modifica" id="btn_actComercial_Modifica" value="Modificar" onClick="seleccion_registro_actividadComercial(2)" />	
                <input class ="botonera" type="submit" name="btn_actComercial_Consulta" value="Consultar" onClick="seleccion_registro_actividadComercial(3)"  />                
                <input class ="botonera" type="button" name="btn_informeOT" id="btn_informeOT"  onclick="SelectOT()"  value="Informe OT" />
            </td>	
        </tr>
    </table>     
    <form action="ServletPDFOT" method="post" id="pdfOT">
        <input name="opcion" value="select" type="hidden"></input>
        <input name="txt_numero_ot" id="txt_numero_ot" value="" type="hidden"></input>
    </form>
    <form action="svm_Actualiza_OT.jsp" method="post" id="ActualizaOT">
        <input name="accion" id="accion" type="hidden"></input>
        <input name="numeroOT" id="numeroOT" value="" type="hidden"></input>
    </form>                                                
</body>
</html>
    