<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAL.conexionBD"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>PORTAL DUROCROM</title>
<link rel="icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />   
<link rel="stylesheet" type="text/css" href="css/estilo_modulo1.css" />
<link href="css/style_tabla.css" type="text/css" rel="STYLESHEET" />
<link href="css/solutel.css" type="text/css" rel="STYLESHEET" />

<!--Codigo Sistemas SA-->

<link href="css/calendario.css" type="text/css" rel="stylesheet"/>
<script src="js/calendar.js" type="text/javascript"></script>
<script src="js/calendar-es.js" type="text/javascript"></script>
<script src="js/calendar-setup.js" type="text/javascript"></script>
<script src="js/label.js" type="text/javascript"></script>
<script src="js/CRUD_Clientes.js" type="text/javascript"></script>
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
</style>
<%  
    int id= 1;
    int cont =0;
    HttpSession s = request.getSession();  
    String tipoUser = "";
    String rut = "";
    String nom= "";
    if(s.getAttribute("nom")==null)
    {
        response.sendRedirect("login.jsp");
    }
    nom = (String)s.getAttribute("nom");
    if(s.getAttribute("tipo") != null)
    {
        tipoUser =(String)s.getAttribute("tipo");
    }
    if(s.getAttribute("rut")!= null)
    {
        rut = (String)s.getAttribute("rut");
    }
    Connection _connMy = null;
    _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));
//    if(_connMy == null)
//    {
//        response.sendRedirect("login.jsp");
//    }
%>
<script type="text/javascript">
        $(document).ready(function(){
   $('#tblCliente').dataTable( {//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
        "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
        bFilter: false, bInfo: false,
        "bLengthChange": false,
        //"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [1,2,3,4,5,6,7,8,9,10,11,12,13,14] }]
        
        } );
});

    function seleccion_registro_clientes(id){
    if($("#habilitaCliente").val() == 0)
    {
        alert("No hay un elemento seleccionado");
        return false;
    }
    if($("#habilitaCliente").val()!==0) 
    {        
        var rut = $("#rut").val();
        var accion=id==2?"modifica":"consulta";
        location.href='svm_Actualiza_Clientes.jsp?accion='+accion+'&par='+id+'&opcion='+rut;
    }
   }

</script>

</head>
<body id="principal">
    <table id="header" align="center" >
        <tr>
            <td colspan="5">
                <div class="tablafiltrar">
                    <table align="center" >
                        <tr>
                            <td>Rut Cliente:</td>
                            <td><input type="text" name="filtro_cliente_rut" id="filtro_cliente_rut" /></td>
                            <td>&nbsp;&nbsp;Nombre Cliente:</td>
                            <td><input type="text" name="filtro_cliente_nombre" id="filtro_cliente_nombre" />
                            </td>
                            <!--<td>Vendedor:</td>
                            <td>
                                <select name="filtro_cliente_vendedor" id="filtro_cliente_vendedor">
                                     <option value="">--Seleccione--</option>
                                    <%
                                        Statement stmt = null;
                                        ResultSet rsQuery = null;                                       
                                        stmt = _connMy.createStatement();
                                        String q = "";

                                        q="select rut, nombre_user from svm_mae_usuarios";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("rut")+"'>"+rsQuery.getString("nombre_user")+"</option>");
                                        }
                                    %>
                                </select>
                            </td>-->
                            <td>&nbsp;&nbsp;Estado:</td>
                            <td>					
                                <select name="filtro_cliente_estado" id="filtro_cliente_estado">
                                     <option value="X_X">--Selecione--</option>
                                     <%                                   
                                        stmt = _connMy.createStatement();

                                        q="select descripcion from svm_mae_tablas where tablas='Estado_Cliente'";

                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                        }
                                    %>
                                </select>
                            </td>			
                            <td id="botonBuscar">
                                &nbsp;&nbsp;<input class ="botonera" type="submit" name="btnBuscar"  onClick="filtrarCliente()"value="Buscar"/>
                            </td>
                            <td id="botonEliminarFiltro">
                                &nbsp;&nbsp;<input class ="botonera" type="submit" name="botonEliminarFiltro"  value="Eliminar Filtro" onclick="window.location.reload()"/>
                            </td>
                                
                        </tr>
                    </table>
                </div>
            </td>	
        </tr>
        <tr>
            <td colspan="5">
                <div class="datagrid" align="center">
                    <table class="centerCli" id="tblCliente" width="100%">
                        <thead>
                            <tr>
                                <th>
                                <th id="cliente">Rut Cliente</th>
                                <th id="cliente">Nombre Cliente</th>
                                <th id="cliente">Contacto</th>
                                <th id="cliente">Dirección</th>
                                <th id="cliente">Vendedor</th>
                                <th id="cliente">Estado</th>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                
                                String op = "select_clean";
                                
                                CallableStatement sp_usu = _connMy.prepareCall("{call sp_mae_cliente(?,'','','','','','','','','','','','',0,0,0,0,0,0,'','','','','','','','','','')}");
                                sp_usu.setString(1,op);
                                sp_usu.execute();
                                final ResultSet rs = sp_usu.getResultSet();
                                String estilo = "";
                                while(rs.next())
                                {
                                    if(cont % 2 == 0)
                                    {
                                        estilo = "alt";
                                    }else
                                    {
                                        estilo = "";                                            
                                    }
                                    out.println("<tr id='filaTablaCliente"+cont+"' class='"+estilo+"'>");
                            %>
                            
                            <td>
                                    <a href="javascript: onclick=ModificaCliente(<%=cont%>)"> >> </a>
                                    <input type="hidden" value="0" id="habilitaCliente" name="habilitaCliente" />
                                    <input type="hidden" value="" id="rut" name="rut" />
                                </td>
                                   
                                <td id="Cliente_rut<%=cont%>"><%= rs.getString("rut")%></td>
                                <td id="Cliente_Nombre<%=cont%>"><%= rs.getString("nombre")%></td>
                                <td id="Cliente_Contacto<%=cont%>"><%= rs.getString("nombre_Contacto_Contable")%></td>
                                <td id="Cliente_Direccion<%=cont%>"><%=rs.getString("direccion")%></td>
                                <td id="Cliente_Vendedor<%=cont%>"><%= rs.getString("vendedor")%></td>
                                <td id="Cliente_Estado<%=cont%>"><%= rs.getString("estado")%></td>
                            
                            <%
                                    out.println("</tr>");                                   
                                    cont ++;
                                }
                            
                            %>

                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
        <!--Sistemas sa 20/10/2015-->
        <td>
        <input class ="botonera" type="submit" name="btnIngresa" value="Ingresar" onClick="window.location.href='svm_Actualiza_Clientes.jsp?par=1&accion=ingresar'"/>
        <input class ="botonera" type="submit" name="btnModifica" value="Modificar" onClick="seleccion_registro_clientes(2)"/>  
        <input class ="botonera" type="submit" name="btnConsulta" value="Consultar" onClick="seleccion_registro_clientes(3)"/>
<!--    <input class ="botonera" style="display: none" type="button" onclick="CancelaCliente()" id="btn_Cliente_Cancelar" name="btn_Cliente_Cancelar" value="Desmarcar" />-->
        </td>	
        </tr>

    </table>
</body>
</html>