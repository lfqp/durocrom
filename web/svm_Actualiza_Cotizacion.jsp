<%@page import="java.sql.Types"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="DAL.conexionBD"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" /> 
<title>Ingreso Actividad Comercial</title>
<link rel="icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />   
<link href="css/style_tabla.css" type="text/css" rel="stylesheet" />
<link href="css/solutel.css" type="text/css" rel="stylesheet" />
<!--Codigo Sistemas SA-->
<link href="css/calendario.css" type="text/css" rel="stylesheet" /> 
<!--<link type="text/css" rel="stylesheet" media="screen" href="css/jquery.ui.all.css"/>-->
<link type="text/css" rel="stylesheet" media="screen" href="css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" media="screen" href="css/bootstrap-theme.min.css"/>
<link type="text/css" rel="stylesheet" media="screen" href="css/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="css/bootstrap-3.3.5.min.css"/>

<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="js/bootstrap-3.3.5.min.js"></script>
<script type="text/javascript" src="js/jquery.validate-1.14.0.min.js"></script>
<script type="text/javascript" src="js/jquery-validate.bootstrap-tooltip.js"></script>        

<script src="js/calendar.js" type="text/javascript"></script>
<script src="js/calendar-es.js" type="text/javascript"></script>
<script src="js/calendar-setup.js" type="text/javascript"></script>
<script src="js/Funcion_Errores.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<!-- Librerias Jquery -->
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>

<script src="js/autoNumeric.js" type="text/javascript"></script>
<script src="js/jquery.lazzynumeric.js" type="text/javascript"></script>
<script src="js/jquery.lazzynumeric.min.js" type="text/javascript"></script>

<script src="js/messages_es.js" type="text/javascript" ></script>
<script src="js/CargarCombo.js" type="text/javascript" ></script>
<script src="js/CRUD_cotizacion.js" type="text/javascript"></script>
<script src="js/CRUD_cotizacion_det.js" type="text/javascript"></script>
<%    
    //TipoUser, Rut y Nombre Vendedor PAra actualiza Actividad comercial.    
    HttpSession s = request.getSession();
    Connection _connMy = null;    
    CallableStatement sp_usu = null;   
    String rut = "";
    String nom = "";
    String id= "";
    String secuencia = "";
    String var = "";
    String fecha = "";
    String Estado = "";
    String rutCli = "";
    String corrCotiza = "0";
    String corrCotizaDet = "0";
    String accion = "";
    String valorUF = "";
    
    corrCotiza=request.getParameter("secuencia") != null ? request.getParameter("secuencia") : "0";
    
    try
    {
        _connMy = conexionBD.Conectar((String)s.getAttribute("organizacion"));
        
        nom  = (String)s.getAttribute("nom"); 
        accion = request.getParameter("par");
        
        Statement stmt = null;
        ResultSet rsQuery = null;                                       
        String q = "select descripcion from svm_mae_tablas where tablas = 'Valor_UF'";                                        
        stmt = _connMy.createStatement();
        rsQuery = stmt.executeQuery(q);
        
        rsQuery.first();
        
        valorUF = rsQuery.getString("descripcion");
        
        if(s.getAttribute("nom")== null)
        {                                  
            response.sendRedirect("login.jsp");
        }
        
        if(request.getParameter("par") != null)
        {
            id = request.getParameter("par");
        }
        if(request.getParameter("secuencia") != null)
        {
            secuencia = request.getParameter("secuencia");
        }
       
    }catch(Exception e)
    {
        out.println("Error:" + e.getMessage());
    }
%>
<style type="text/css">
        select[disabled] { background-color: rgb(235, 235, 228); }
        select[enabled] { background-color: white; }
        .integer 
        {
            text-align: right;            
        }
        .decimal
        {
            text-align: right;                        
        }
        
        input[readonly]:not([type="button"]){
            background-color: #f0f0f0;
            border: solid 1px #cccccc;
        }
</style>
    
<script type="text/javascript">        
function getUrlVars() { // Read a page's GET URL variables and return them as an associative array.
    var vars = [],
        hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

$(document).ready(function (){   
     //cargaMoneda();
     //cargaEstadoCoti();
     
     var vars = getUrlVars();
     if(vars["accion"]!="consulta"){
         $(window).bind('beforeunload', function(){
            return 'Actualmente se está modificando un formulario, si cambia de página las modificaciones del formulario no se guardarán';
        });
     }
     
     cargaPresupuesto();
     cargaCondicion();
     cargaPLazoEntrega();
     $(".decimal").autoNumeric('init', {aSep: '', aDec: ','});
     $(".integer").autoNumeric('init', {aSep: '', aDec: ',', vMin: '0', vMax: '1000000000'});
     //$(".numeric").lazzynumeric({aSep: "", aDec: ",", vMin: "0", vMax: "9999999999"});
     cargaCotizacion();
     
     
     $( "#dialog_procesos" ).dialog({
        autoOpen: false,
        modal: true,
        width: 1040,
        height:460,
        resizable: false,
        title: 'Procesos                               Area por Pieza   Cantidad Piezas     Precio Unitario                           Total Neto',
        buttons: {
        },
        open: function(event, ui) {
            $('select[name=selector]').val()
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });
    
     $( "#dialog_maestrocli" ).dialog({
        autoOpen: false,
        modal: true,
        width: 550,
        height:600,
        resizable: false,
        title: "Maestro Clientes",
        buttons: {
        },
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });    
    
    $( "#dialog_msg" ).dialog({
        modal: true,
        width: 350,
        height: 200,
        resizable: false,
        autoOpen: false,
        position: {
			my: "center",
			at: "center",
			of: window,
			collision: "fit",

			// Ensure the titlebar is always visible
			using: function( pos ) {
				var topOffset = $( this ).css( pos ).offset().top;
				if ( topOffset < 0 ) {
					$( this ).css( "top", pos.top - topOffset );
				}
			}
		},        
        buttons: {
            Cerrar: function() {
                $( this ).dialog("close");
                location.href="svm_Seleccion_Cotizacion.jsp";
            }
        },
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });
});

function goBack(){
    location.href='svm_Seleccion_Cotizacion.jsp';
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

function loadDialogDetalleUnicoCliente(){
    $( "#dialog_detalleUnicoCliente" ).dialog({
        modal: true,
        width: 600,
        height:400,
        resizable: false,
        buttons: {
            Grabar:function() {
                $( this ).dialog( "close" );
                $("#txt_detalleUnicoCliente_hidden").val($("#txt_detalleUnicoCliente").val());
            },Cancelar: function() {
                $("#txt_detalleUnicoCliente").val($("#txt_detalleUnicoCliente_hidden").val());
                $( this ).dialog("close");
            }
        },open: function(event, ui) {
            $("#txt_cotizacion_filtro_trabajo").val("");
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
            filtraTrabajos();
        }
    });
}
function loadDialogTrabajo(){
    $( "#dialog_trabajo" ).dialog({
        modal: true,
        width: 600,
        height:400,
        resizable: false,
        buttons: {
            Seleccionar: 
                function() {
                $("#select_cotizacion_trabajo").val($("#select_cotizacion_trabajo_filter").val());
                $( this ).dialog( "close" );
            },
            Cancelar: function() {
                $( this ).dialog("close");
            }            
        },
        open: function(event, ui) {
            $("#txt_cotizacion_filtro_trabajo").val("");
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
            filtraTrabajos();
        }
    });
}

function loadDialogPiezas(){
    $( "#dialog_pieza" ).dialog({
        modal: true,
        width: 600,
        height:400,
        resizable: false,
        buttons: {
            Seleccionar: 
                function() {
                $("#select_cotizacion_pieza").val($("#select_cotizacion_pieza_filter").val());
                $( this ).dialog( "close" );
            },
            Cancelar: function() {
                $( this ).dialog("close");
            }            
        },
        open: function(event, ui) {
            $("#txt_cotizacion_filtro_pieza").val("");
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
            filtraPiezas();
        }
    });
}

function loadDialogClientes(){
    $( "#dialog_clientes" ).dialog({
        modal: true,
        width: 600,
        height:420,
        resizable: false,
        buttons: {
            Seleccionar: function() {
                var text = $("#select_cliente_filter option:selected").text();
                var parts;
                var ejec;
                var emitida = $("#txt_cotizacion_emitida_por option:selected").text();
                var ejec2;
                parts = text.split("|");
                ejec = parts[5].trim();
                ejec2  = ejec.replace(/\s/g," ");
                if (emitida !== ejec2) {
                    var creaOT=confirm("El usuario de ingreso es distinto al asignado al cliente ¿Desea continuar?");
                    if(creaOT){
                        $("#txt_cotizacion_rutcli").val($("#select_cliente_filter").val());
                        $("#txt_cotizacion_cli").val(parts[1].trim());
                        $("#select_cotizacion_estado_cliente").val(parts[2].trim());
                        $("#txt_cotizacion_contacto_nombre").val(parts[3].trim());
                        $("#txt_cotizacion_contacto_email").val(parts[4].trim());
                        $("#txt_cotizacion_contacto_fonos").val(parts[6].trim());
                        $( this ).dialog( "close" );
                    }
                }
                else {
                    $("#txt_cotizacion_rutcli").val($("#select_cliente_filter").val());
                    $("#txt_cotizacion_cli").val(parts[1].trim());
                    $("#select_cotizacion_estado_cliente").val(parts[2].trim());
                    $("#txt_cotizacion_contacto_nombre").val(parts[3].trim());
                    $("#txt_cotizacion_contacto_email").val(parts[4].trim());
                    $("#txt_cotizacion_contacto_fonos").val(parts[6].trim());
                    $( this ).dialog( "close" );
                }
            },            
            Cancelar: function() {
                $( this ).dialog("close");
            }            
        },
        open: function(event, ui) {
            $("#txt_filtro_cliente_rut").val("");
            $("#txt_filtro_cliente_nombre").val("");
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
            filtraClientes();
        }
    });
}

function loadDialogProcesos(){
    
    var td = $('#tblDetalleComer').children('tbody').children('tr').length;
    var estadoDetalle = $("#estadoDetalle").val();
    
    if (estadoDetalle != "Modifica") {
        if (td == 4) {
            FuncionErrores(56);
            $("#select_cotizacion_pieza").focus();
            return;
    }
    }
    if ($("#txt_cotizacion_cantidad").val() == "") {
        FuncionErrores(29);
        $("#txt_cotizacion_cantidad").focus();
        return;
    }
    if ($("#select_cotizacion_pieza").val() == "") {
        FuncionErrores(42);
        $("#select_cotizacion_pieza").focus();
        return false;
    }    
    
    if ($("#select_cotizacion_trabajo").val() == "") {
        FuncionErrores(43);
        $("#select_cotizacion_trabajo").focus();
        return false;
    }

    if ($("#txt_cotizacion_diametroint").val() == "") {
        FuncionErrores(40);
        $("#txt_cotizacion_diametroint").focus();
        return false;
    }    

    if ($("#txt_cotizacion_largo").val() == "") {
        FuncionErrores(32);
        $("#txt_cotizacion_largo").focus();
        return false;
    }    
    
    $( "#dialog_procesos" ).dialog("open");
}

function loadDialogMaeClientes(){
    $("#dialog_maestrocli").dialog("open");
}

</script>
</head>
<body id="principal">
<input type="hidden" value="<%=id%>" id="parametroActComercial" />
<input type="hidden" value="<%=rut%>" id="rutUsuario" />
<input type="hidden" value="" id="estadoDetalle" />
<input type="hidden" value="" id="superModificado" />
<input type="hidden" value="" id="cantidadModificado" />

<div class="formularioIngresar">
    <table id="header">                           
        <tr>
            <td>
                <form action="" method="post">
                    <table>
                        <tr>
                            <td colspan="2"><b>DESCRIPCI&Oacute;N GENERAL DE LA COTIZACI&Oacute;N</b><hr style="margin-bottom: 4px;margin-top: 4px;" /></td>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="2"><b>DESCRIPCI&Oacute;N DEL CLIENTE</b><hr style="margin-bottom: 4px;margin-top: 4px;"/></td>
                        </tr>
                        <tr>
                            <td>N° Cotizaci&oacute;n:</td>                            
                            <td>                                 
                                <input type="text" style="text-align: right" disabled= "disabled" id="txt_cotizacion_numero" maxlength="11" name="txt_cotizacion_numero" />
                            </td> 
                            <td colspan="2">&nbsp;</td>
                            <td>Rut:</td>
                            <td>
                                <input type="text" readonly id="txt_cotizacion_rutcli" maxlength="11" name="txt_cotizacion_rutcli" />
                            </td>
                            <td>
                                <input class = "botonera" style="width: 70px" type="button" name="btnClientes" id="btnClientes" value="Clientes" onClick="loadDialogClientes()" />
                            </td>
                        </tr>
                        <tr>
                            <td>Fecha emisi&oacute;n:</td>
                            <td>
                                <input type = "text" name = "txt_cotizacion_fecha" readonly id= "txt_cotizacion_fecha" size="12" />
                                <!--<img src="images/calendario.png" width="16" height="16" border="0" title="Fecha Inicial" id="lanzador"/>-->
                                <!-- script que define y configura el calendario--> 
                                <!--<script type="text/javascript"> 
                                    Calendar.setup({
                                        inputField     :    "txt_cotizacion_fecha",     // id del campo de texto 
                                        ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                        button     :    "lanzador"     // el id del boton que lanzará el calendario 
                                    }); 
                                </script>	-->
                            </td>                          
                            <td colspan="2">&nbsp;</td>
                            <td>Nombre</td>
                            <td rowspan="2">
                                 <textarea rows="2" cols="27" readonly id="txt_cotizacion_cli" maxlength="40">
                                 </textarea> 
                                <!--<input type="text" id="txt_cotizacion_cli" maxlength="11" name="txt_cotizacion_cli" />-->
                            </td>
                        </tr>
                        <tr>
                            <td>D&iacute;as h&aacute;biles de trabajo</td>
                            <td>
                                <input type = "text" class="integer" align ="right" name = "txt_cotizacion_dias_habiles" id= "txt_cotizacion_dias_habiles" size="12" />	
                            </td>                          
                        </tr>
                        <tr>
                            <td>Vendedor:</td>
                            <td><select style="height: 22px" name="txt_cotizacion_emitida_por" id="txt_cotizacion_emitida_por">
                                    <option value="">--Seleccione--</option>
                                    <%                                    
                                        Statement stmt = null;
                                        ResultSet rsQuery = null;                                       
                                        String q = "select rut, nombre_user from svm_mae_usuarios WHERE tipo = 'Vendedor'";                                        
                                        stmt = _connMy.createStatement();
                                        rsQuery = stmt.executeQuery(q);

                                        while(rsQuery.next())
                                        {
                                            if (nom.equals(rsQuery.getString("nombre_user"))) {
                                                out.println("<option value='"+rsQuery.getString("rut")+"' selected>"+rsQuery.getString("nombre_user")+"</option>");
                                            }
                                            else {
                                                out.println("<option value='"+rsQuery.getString("rut")+"'>"+rsQuery.getString("nombre_user")+"</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>Estado de Cliente:</td>
                            <td>
                                <input type="text" readonly id="select_cotizacion_estado_cliente" name="select_cotizacion_estado_cliente"/>
                            </td>

                        </tr>
                        <tr>
                            <input type="hidden" readonly id="select_cotizacion_moneda" name="select_cotizacion_moneda" value="CLP"/>
                            <td>Condici&oacute;n de Pago:</td>
                            <td><select style="width: 200px; height: 22px" name="select_cotizacion_condicion_pago" id="select_cotizacion_condicion_pago">
                                    <option value="">--Seleccione--</option>
                                </select></td>
                            <td colspan="2">&nbsp;</td>
                            <td><b>Contacto Comercial</b></td>
                        </tr>
                        <tr>
                            <td>D&iacute;as de Validez:</td>
                            <td><input type="text" style="width: 100px" readonly id="select_cotizacion_presupuesto_valido" maxlength="40" name="select_cotizacion_presupuesto_valido" value="15 DIAS HABILES"/></td>
                            <td colspan="2">&nbsp;</td>
                            <td>Nombre Completo:</td>
                            <td><input type="text" style="width: 200px" readonly id="txt_cotizacion_contacto_nombre" maxlength="100" name="txt_cotizacion_contacto_nombre" /></td>                               
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>eMail:</td>
                            <td><input type="text" style="width: 200px" readonly id="txt_cotizacion_contacto_email" maxlength="100" name="txt_cotizacion_contacto_email" /></td>                               
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>Fonos:</td>
                            <td><input type="text" style="width: 200px" readonly id="txt_cotizacion_contacto_fonos" maxlength="100" name="txt_cotizacion_contacto_fonos" /></td>                               
                        </tr>                            
                        <tr style="height: 10px"></tr>
                    </table>
                </form>
            </td>
            <td rowspan="6" >
                <div id="alineacionEstados" >
                    <div class="etiqueta" >
                        <center><label><b>Historial de Estados</b></label></center>
                    </div>
                    <div class="grillaConf">
                        <table>
                            <thead>
                                <tr>
                                    <th>Estado Anterior</th>
                                    <th>Estado Siguiente</th>
                                    <th>Fecha</th>                                    
                                    <!--<th>Rut Usuario</th>-->                                    
                                    <th>Nombre Usuario</th>
                                </tr>
                            </thead>				
                            <tbody>
                                    <%
                                        int cont = 0;                                        
                                         var = "select";                                        
                                        sp_usu = _connMy.prepareCall("{call sp_historial(?,?,'','')}");
                                        sp_usu.setString(1,var);                                        
                                        sp_usu.setLong(2,Long.parseLong(secuencia));                                          
                                        sp_usu.execute();
                                        final ResultSet rsHistorial = sp_usu.getResultSet();
                                        String claseGrilla = "";
                                        while(rsHistorial.next())
                                        {
                                            if(cont % 2 == 0)
                                            {                                                
                                                claseGrilla = "alt";
                                            }
                                            out.println("<tr id='filaTablaHistorial"+cont+"' class='"+claseGrilla+"'>");
                                    %>                                                                                                                                                                    
                                        <td id ="historial_estAnterior<%=cont%>"><%=rsHistorial.getString("estado_anterior")%></td>
                                        <td id ="historial_estSiguiente<%=cont%>"><%=rsHistorial.getString("estado_siguiente")%></td>
                                        <td id ="historial_fecha<%=cont%>"><%=rsHistorial.getString("fecha")%></td>
                                        <!--<td id ="historial_rutUser<%--cont--%>"><%--rsHistorial.getString("rutUser")--%></td>-->
                                        <td id ="historial_nomUser<%=cont%>"><%=rsHistorial.getString("nomUser")%></td>                                                                                   
                                     <%
                                            out.print("</tr>");
                                            claseGrilla = "";
                                            cont ++;
                                        }
                                     %>                               
                            </tbody>
                        </table>
                    </div>
                </div>
            </td>
        </tr>
        <tr>            
            <table>
                <td>Valor UF:</td>
                <td style="width: 48px;">&nbsp</td>
                <td>
                    <input type="text" class="decimal" readonly maxlength="9" id="txt_cotizacion_valorUF" name="txt_cotizacion_valorUF" value=<%=valorUF%> />
                </td>
                <td style="width: 150px;">&nbsp</td>
                <td style="width: 150px;">&nbsp</td>
                <td style="width: 150px;">&nbsp</td>
                <td style="width: 150px;">&nbsp</td>
                <td>Estado Cotizaci&oacute;n:</td>
                <td><select style="width: 150px; height: 22px" name="select_cotizacion_estado" id="select_cotizacion_estado">
                        <option value="10">En espera de aprobación</option>
                        <%                                    
                            stmt = null;
                            rsQuery = null;                                       
                            q = "select id, descripcion from svm_mae_tablas where tablas = 'Estado_Cotizacion'";                                        
                            stmt = _connMy.createStatement();
                            rsQuery = stmt.executeQuery(q);

                            while(rsQuery.next())
                                {
                                    if (rsQuery.getString("descripcion").equals("En espera de aprobación")) {
                                    }
                                     else {
                                        out.println("<option value='"+rsQuery.getString("id")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                    }
                                }
                        %>                        
                    </select> 
                </td>
                <td style="width: 130px;">&nbsp</td>
                <td>OC de Aprobaci&oacute;n:</td>
                <td>
                    <input type="text" style="text-align: right" maxlength="40" id="select_cotizacion_oc" name="select_cotizacion_oc" maxlength="10"/>
                </td>                
            </table>        
        </tr>
        <tr>
            <td colspan="14">
                <table width="1300px;">
                    <tr style="height: 10px;">
                        <td colspan="14"></td>
                    </tr>
                    <tr>
                        <td colspan="14" id="margen" bgcolor="#2798D9" style="color: white; font-size: 18px" align="center"><b>RESUMEN VALORES COTIZACI&Oacute;N</b></td>
                    </tr>
                    <tr style="height: 10px;">
                        <td colspan="14"></td>
                    </tr>
                    <tr>
                        <td>Total Procesos:</td>
                        <td>
                            <input type="text" class="decimal" style="text-align:right;" readonly id="select_cotizacion_total_procesos" name="select_cotizacion_total_procesos" maxlength="10" onchange="return separadorDeMiles(this)" />
                        </td>
                        <td style="width: 50px;">&nbsp</td>
                        <td>Total Materiales:</td>
                        <td>
                            <input type="text" class="decimal" style="text-align:right;" readonly id="select_cotizacion_total_materiales" name="select_cotizacion_total_materiales" maxlength="10"/>
                        </td>
                        <td style="width: 50px;">&nbsp</td>
                        <td>Total Neto:</td>
                        <td>
                            <input type="text" class="decimal" style="text-align:right;" readonly id="txt_cotizacion_total_neto" name="txt_cotizacion_total_neto" maxlength="10" />
                        </td>
                        <td style="width: 50px;">&nbsp</td>
                        <td>Iva:</td>
                        <td>
                            <input type="text" class="decimal" style="text-align:right;" readonly id="txt_cotizacion_iva" name="txt_cotizacion_iva" maxlength="10" />
                        </td>
                        <td style="width: 50px;">&nbsp</td>
                        <td>Total Bruto:</td>
                        <td>
                            <input type="text" class="decimal" style="text-align:right;" readonly id="txt_cotizacion_total_bruto" name="txt_cotizacion_total_bruto" maxlength="10" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="14">
                <table class="detalle" width="1330px;">
                    <tr style="height: 10px;">
                        <td colspan="14"></td>
                    </tr>
                    <tr id="Titulo_descp_piezas">
                        <td colspan = "10" id="margen" bgcolor="#2798D9" style="color: white; font-size: 18px" align="center"><b>DESCRIPCI&Oacute;N PIEZAS</b></td>
                    </tr>
                    <tr style="height: 10px;">
                        <td colspan="14"></td>
                    </tr>                    
                    <tr>
                        <td>Pieza:</td>
                        <td colspan="1"><select disabled style="width:120px;height:22px" id="select_cotizacion_pieza" onchange="calculaTotal()" name="select_cotizacion_pieza">
                                <option value="">--Seleccione--</option>
                                <%                                    
                                    stmt = _connMy.createStatement();
                                    q="select id, descripcion from svm_mae_tablas where tablas = 'Piezas' order by descripcion";
                                    rsQuery = stmt.executeQuery(q);

                                    while(rsQuery.next())
                                    {
                                        out.println("<option value='"+rsQuery.getString("id")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                    }
                                %>
                            </select> 
                            <!--<input class = "botonera" type="submit" name="btnPiezas" id="btnPiezas" value="Piezas" onClick="loadDialogPiezas()" />-->
                        </td>
                        <!--<td colspan="1"><input type="text" readonly id="txt_cotizacion_procesos" name="txt_cotizacion_procesos" /></td>-->
                        <!--<td>Valor DM:</td>
                        <td colspan="1"><input type="text" maxlength="9" id="txt_cotizacion_dm" name="txt_cotizacion_dm" onkeypress="return validarSiNumero(event)" /></td>         -->
                        <td>Di&aacute;metro (mm):</td>
                        <td colspan="1">
                            <input type="text" class="decimal" disabled style="text-align: right" maxlength="9" id="txt_cotizacion_diametroint" name="txt_cotizacion_diametroint" onchange="calculaTotal()" onkeypress="return validarSiNumero(event)" />
                        </td>         
                        <!--<td>Di&aacute;metro Externo (mm)</td>
                        <td colspan="1"><input type="text" maxlength="9" id="txt_cotizacion_diametroext" name="txt_cotizacion_diametroext" onkeypress="return validarSiNumero(event)" /></td>-->
                        <td>Observaciones:</td>
                        <td rowspan="8">
                            <textarea rows="8" cols="70" disabled id="txt_cotizacion_comentarios" maxlength="200"></textarea> 
                        </td>
                        <td>
                            <input class = "botonera" style="width: 100px" type="button" name="btnArea" id="btnArea" value="Calcular Area" onClick="cargaValorDetalle();calculaTotal();" />
                        </td>                        
                    </tr>
                    <tr>
                        <td>Nuevo / Usado:</td>
                        <td colspan="1"><select disabled style="width:120px;height:22px" id="select_cotizacion_nuevo_usado" name="select_cotizacion_nuevo_usado">
                                <option value="">--Seleccione--</option>
                                <%                                    
                                    stmt = _connMy.createStatement();
                                    q="select descripcion from svm_mae_tablas where tablas = 'Estado Pieza'";
                                    rsQuery = stmt.executeQuery(q);

                                    while(rsQuery.next())
                                    {
                                        out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                    }
                                %>
                            </select> 
                        </td>                        
                        <td>Largo (mm):</td>
                        <td colspan="1">
                            <input type="text" class="decimal" disabled style="text-align: right" maxlength="9" id="txt_cotizacion_largo" name="txt_cotizacion_largo" onchange="calculaTotal()" onkeypress="return validarSiNumero(event)" />
                        </td>
                        <td>&nbsp;</td>     
                        <td>
                            <input class = "botonera" readonly style="width: 100px" type="button" name="btnProcesos" id="btnProcesos" value="Procesos" onClick="cargaProcesos();loadDialogProcesos();" />
                        </td>  
                        
                    </tr>
                    <tr>
                        <td>Material Base:</td>
                        <td colspan="1"><select disabled style="width:120px; height:22px" id="select_cotizacion_materialbase" name="select_cotizacion_materialbase">
                                <option value="">--Seleccione--</option>
                                <%                                    
                                    stmt = _connMy.createStatement();
                                    q="select descripcion from svm_mae_tablas where tablas = 'Metalbase'";
                                    rsQuery = stmt.executeQuery(q);

                                    while(rsQuery.next())
                                    {
                                        out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                    }
                                %>
                            </select> 
                        </td>                        
                        <td>Ancho (mm):</td>
                        <td colspan="1">
                            <input type="text" class="decimal" disabled style="text-align: right" maxlength="9" id="txt_cotizacion_ancho" name="txt_cotizacion_ancho" onchange="calculaTotal()" onkeypress="return validarSiNumero(event)" />
                        </td>  
                        <td></td>
                        <td>
                            <input class="botonera10" type="button" name="btnDetalleUnicoCliente" id="btnDetalleUnicoCliente" value="Detalle Unico Cliente" onClick="loadDialogDetalleUnicoCliente()" />
                        </td>
                    </tr>
                    <tr>
                        <td>Trabajo Abreviado:</td>
                        <td colspan="1"><select  disabled style="width:120px;height:22px" id="select_cotizacion_trabajo" name="select_cotizacion_trabajo">
                                <option value="">--Seleccione--</option>
                                <%                                    
                                    stmt = _connMy.createStatement();
                                    q="select descripcion from svm_mae_tablas where tablas = 'Trabajos_Abreviados'";
                                    rsQuery = stmt.executeQuery(q);

                                    while(rsQuery.next())
                                    {
                                        out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                                    }
                                %>
                            </select> 
                            <!--<input class = "botonera" type="submit" name="btnPiezas" id="btnTrabajo" value="Trabajo Abreviado" onClick="loadDialogTrabajo()" />-->
                            
                        </td>
                      
                    </tr>
                    <tr>
                        <td>Cantidad:</td>
                        <td colspan="1"><input class="number" disabled type="text" style="text-align: right" maxlength="5" id="txt_cotizacion_cantidad" name="txt_cotizacion_cantidad" onchange="calculaTotal()" onkeypress="return validarSiNumero(event)"/></td>
                        <td>Superficie (dm2):</td>
                        <td colspan="1">
                            <input type="text" disabled style="text-align: right" maxlength="20" id="txt_cotizacion_area" name="txt_cotizacion_area" />
                        </td>                        
                    </tr>
                    <tr>
                        <td>&nbsp;</td>     
                        <td>&nbsp;</td>
                        <td>Superficie Total (dm2):</td>
                        <td colspan="1">
                            <input type="text" disabled style="text-align: right" maxlength="20" id="txt_cotizacion_area_total" name="txt_cotizacion_area_total" />
                        </td>                          
                    </tr>                            
                    <tr>
                        <td>&nbsp;</td>     
                        <td>&nbsp;</td>
                        <td>Corriente (A):</td>
                        <td colspan="1">
                            <input type="text" class="decimal" disabled style="text-align: right" maxlength="20" id="txt_cotizacion_corriente" name="txt_cotizacion_corriente" />
                        </td>                          
                    </tr>
                    <tr>

                        

                        <!--<td>ValUniCrom:</td>
                        <td colspan="1"><input type="text" maxlength="9" id="txt_cotizacion_valUniCrom" name="txt_cotizacion_valUniCrom" onkeypress="return validarSiNumero(event)" /></td>         
                        <td>C/Hora: $</td>
                        <td colspan="1"><input type="text" maxlength="9" id="txt_cotizacion_cHora" name="txt_cotizacion_cHora" onkeypress="return validarSiNumero(event)" /></td>         
                        <td>Cant. Hrs:</td>
                        <td colspan="1"><input type="text" maxlength="9" id="txt_cotizacion_cantHrs" name="txt_cotizacion_cantHrs" onkeypress="return validarSiNumero(event)" /></td>  -->
                        <!--<td>Total Crom:</td>-->
                        <td><input type="hidden" id="txt_cotizacion_superficie" name="txt_cotizacion_superficie" /></td>
                        <!--<td>Valor Unitario:</td>-->
                        <td><input type="hidden" id="txt_cotizacion_valUnitario" name="txt_cotizacion_valUnitario" /></td>
                        <!--<td>Totales:</td>-->
                        <td><input type="hidden" id="txt_cotizacion_totales" name="txt_cotizacion_totales" /></td>
                        <!--<td>Total Horas$:</td>-->
                        <!-- <td><input type="hidden" id="txt_cotizacion_totalhoras" name="txt_cotizacion_totalhoras" /></td> -->
                        <td><input type="hidden" value="<%=valorUF%>" id="txt_cotizacion_uf" name="txt_cotizacion_uf" /></td>
                        <td><input type="hidden" id="txt_cotizacion_cantidadnuevo" name="txt_cotizacion_cantidadnuevo" /></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="13" rowspan="5" id='margen'>
                            <div  id = "tablaDetalle" class="grillaConf">
                                <table id="tblDetalleComer">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Numero</th>
                                            <!--<th>Cod. Pieza</th>-->
                                            <th>Pieza</th>
                                            <th>Trabajo Abreviado</th>
                                            <th>Cantidad</th>
                                            <!--<th>Valor DM</th> -->
                                            <!--<th>D&iacute;ametro</th>-->
                                            <!--<th>D&iacute;ametro Interno</th>
                                            <th>D&iacute;ametro Externo</th>-->
                                            <th>Diametro</th>
                                            <th>Largo</th>
                                            <!--<th>ValUniCrom</th>
                                            <th>Total Crom</th>
                                            <th>C/Hora</th>
                                            <th>Cant. Hrs.</th>
                                            <th>Total Hrs. $</th>-->
                                            <th>Ancho</th>
                                            <!--<th>Area</th>-->
                                            <th>Sup. Unitario</th>
                                            <th>Sup. Total</th>
                                            <!--<th>Valor Unitario</th>-->
                                            <th>Total Neto</th>
                                            <th>Iva</th>
                                            <th>Total Bruto</th>
                                            <th>Observacion</th>
                                            <th style="display: none">Obs Oculto</th>
                                            <!--<th>Totales</th>-->
                                        </tr>
                                    </thead>				
                                    <tbody>
                                    <%
                                         cont = 0;
                                         int ultimo =0;
                                         
                                         var = "select";    
                                         System.out.println("hh "+corrCotiza);
                                         sp_usu = _connMy.prepareCall("{call sp_cotizaciones_det(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                        sp_usu.setString(1,var);
                                        sp_usu.setInt(2,0);
                                        sp_usu.setInt(3,0);
                                        sp_usu.setString(4,"");
                                        sp_usu.setString(5,"");
                                        sp_usu.setInt(6,0);
                                        sp_usu.setString(7,"");
                                        sp_usu.setDouble(8,0.0);
                                        sp_usu.setDouble(9,0.0);
                                        sp_usu.setInt(10,0);
                                        sp_usu.setInt(11,0);
                                        sp_usu.setInt(12,0);
                                        sp_usu.setInt(13,0);
                                        sp_usu.setInt(14,0);
                                        sp_usu.setInt(15,0);
                                        sp_usu.setInt(16,0);
                                        sp_usu.setString(17,"");
                                        sp_usu.setInt(18,0);
                                        sp_usu.setInt(19,0);
                                        sp_usu.setString(20,"");
                                        sp_usu.setInt(21,0);
                                        sp_usu.setDouble(22,0.0);                
                                        sp_usu.setString(23,"");
                                        sp_usu.setString(24,"");
                                        sp_usu.setString(25,"");
                                        sp_usu.setDouble(26,0.0);
                                        sp_usu.setDouble(27,0.0);
                                        sp_usu.setString(28,"");
                                        sp_usu.setInt(29,0);
                                        sp_usu.setInt(30,0);
                                        sp_usu.setInt(31,0);
                                        sp_usu.setString(32,"");
                                        sp_usu.setInt(33,0);
                                        sp_usu.setInt(34,0);
                                        sp_usu.setInt(35,0);
                                        sp_usu.setString(36,"");
                                        sp_usu.setInt(37,0);
                                        sp_usu.setInt(38,0);
                                        sp_usu.setInt(39,0);
                                        sp_usu.setString(40,"");
                                        sp_usu.setInt(41,0);
                                        sp_usu.setInt(42,0);
                                        sp_usu.setInt(43,0);
                                        sp_usu.setString(44,"");
                                        sp_usu.setInt(45,0);
                                        sp_usu.setInt(46,0);
                                        sp_usu.setInt(47,0);
                                        sp_usu.setInt(48,0);
                                        sp_usu.setInt(49,0);                                        
                                        sp_usu.setInt(50,0);                                        
                                        sp_usu.setInt(51,0);                                        
                                        sp_usu.setInt(52,Integer.parseInt(corrCotiza));  
                                        
                                        sp_usu.setString(53,"");
                                        sp_usu.setString(54,"");
                                        sp_usu.setString(55,"");
                                        sp_usu.setString(56,"");
                                        sp_usu.setString(57,"");
                                        sp_usu.setString(58,"");
                                        sp_usu.setString(59,"");
                                        sp_usu.setString(60,"");
                                        sp_usu.setString(61,"");
                                        sp_usu.setString(62,"");
                                        sp_usu.setString(63,"");
                                        
                                        sp_usu.registerOutParameter(1, Types.VARCHAR);
                                        sp_usu.execute();
                                        final ResultSet rsDetalle = sp_usu.getResultSet();
                                        System.out.println("detalle RS " );
                                        claseGrilla = "";                                        
                                        while(rsDetalle.next())
                                        {
                                            if(cont % 2 == 0)
                                            {                                                
                                                claseGrilla = "alt";
                                            }
                                            out.println("<tr id='filaTablaDetalle"+cont+"' class='"+claseGrilla+"'>");                                           
                                    %>        
                                        <td>
                                            <a id="seleccion<%=cont%>" href="javascript: onclick=ModificaDetalleComercial(<%=cont%>)"> >></a>
                                            <input type="hidden" value="0" id="habilitaDetCom" name="habilitaDetCom" />
                                        </td>                                                                             
                                        <td id ="cotazacionDet_correlativo<%=cont%>"><%=rsDetalle.getString("correlativo")%></td>
                                        <td id ="cotazacionDet_pieza<%=cont%>"><%=rsDetalle.getString("desc_pieza")%></td>
                                        <td id ="cotazacionDet_trabajoAbreviado<%=cont%>"><%=rsDetalle.getString("trabajo_abreviado")%></td>
                                        <td align="right" id ="cotazacionDet_cantidad<%=cont%>"><%=rsDetalle.getString("cantidad")%></td>
                                        <td align="right" id ="cotazacionDet_diametroInterno<%=cont%>"><%=rsDetalle.getString("diametro_interno")%></td>
                                        <td align="right" id ="cotazacionDet_largo<%=cont%>"><%=rsDetalle.getString("largo")%></td>                                            
                                        <td align="right" id ="cotazacionDet_ancho<%=cont%>"><%=rsDetalle.getString("ancho")%></td>
                                        <td align="right" id ="cotazacionDet_area<%=cont%>"><%=rsDetalle.getString("area")%></td>                                        
                                        <td align="right" id ="cotazacionDet_areatotal<%=cont%>"><%=rsDetalle.getString("superficie_total")%></td>
                                        <td align="right" id ="cotazacionDet_totalNeto<%=cont%>"><%=rsDetalle.getString("total_pieza")%></td>
                                        <td align="right" id ="cotazacionDet_iva<%=cont%>"><%=rsDetalle.getString("iva")%></td>
                                        <td align="right" id ="cotazacionDet_totalBruto<%=cont%>"><%=rsDetalle.getString("total_bruto")%></td>
                                        <td style="width: 20px;" id ="cotazacionDet_comentariosSub<%=cont%>"><%=rsDetalle.getString("comentarios2")%></td>   
                                        
                                        <td style="display: none;" id ="cotazacionDet_comentarios<%=cont%>"><%=rsDetalle.getString("comentarios")%></td>                                        
                                        
                                        <td style="display:none;" id ="cotazacionDet_procesos<%=cont%>"><%=rsDetalle.getString("procesos")%></td>
                                        <td style="display:none;" id ="cotazacionCorriente<%=cont%>"><%=rsDetalle.getString("corriente")%></td>
                                        <td style="display:none;" id ="cotazacionCodPieza<%=cont%>"><%=rsDetalle.getString("cod_pieza")%></td>
                                        <td style="display:none;" id ="cotazacionTotalProcesos<%=cont%>"><%=rsDetalle.getString("totales")%></td>
                                        
                                        <td style="display:none;" id ="cotazacionTotalMateriales<%=cont%>"><%=rsDetalle.getString("total_materiales")%></td>
                                        <td style="display:none;" id ="cotazacionDet_nuevousado<%=cont%>"><%=rsDetalle.getString("nuevo_usado")%></td>
                                        <td style="display:none;" id ="cotazacionDetmaterialbase<%=cont%>"><%=rsDetalle.getString("material_base")%></td>
                                        
                                        <!--<td style="display:none;" id ="cotazacionDetCodPieza<%=cont%>"><%=rsDetalle.getString("cod_pieza")%></td>
                                        <td style="display:none;" id ="cotazacionDetTotalProcesos<%=cont%>"><%=rsDetalle.getString("totales")%></td>
                                        <td style="display:none;" id ="cotazacionDetTotalMateriales<%=cont%>"><%=rsDetalle.getString("total_materiales")%></td>-->
                                        <td style="display:none;" id ="cotazacionMargen<%=cont%>"><%=rsDetalle.getString("json_margen")%></td>
                                        <td style="display:none;" id ="cotazacionUtilidad<%=cont%>"><%=rsDetalle.getString("json_utilidad")%></td>
                                        <td style="display:none;" id ="cotazacionTotalNvo<%=cont%>"><%=rsDetalle.getString("json_totalnvo")%></td>
                                     <%
                                            out.print("</tr>");
                                            claseGrilla = "";
                                            cont ++;
                                            ultimo = Integer.parseInt(rsDetalle.getString("numero_cotizacion"));                                                                                     
                                            
                                        }
                                        out.print("<input type='hidden' id='cantidad_detalle' value="+cont+"></input>");
                                     %>
                                     
                                    </tbody>
                                </table>
                                    <input type="hidden" id="ultimo" value="<%=ultimo+1%>"></input>
                                    
                                    <input type="hidden" id="txt_correlativo" value="<%=corrCotizaDet%>"></input>
                            </div>                                               
                        </td>
                        <td id="bottom" rowspan="1">
                            <img style="cursor: pointer" onclick="HabilitaDescripPiezas();" id="DetalleIngreso" class="ico" border="0" src="images/logotipos/agregar.png" 
                            height="48px" width="25px"/>
                        </td>
                    </tr>

                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="DetalleModifica" title="Grabar Cambios" style="display: none" onclick="cargaProcesos();cargaValorDetalle();" src="images/logotipos/modificar.png" border="0" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="CancelaModifica" title="Desmarcar Seleccion" style="display: none" onclick="CancelaDetalle();LimpiaSubDetalle();" src="images/logotipos/flecha.png" border="0" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>                            
                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="DetalleElimina" title="Elimina Pieza" style="display: none" border="0" onclick="eliminaDetalle()" src="images/logotipos/eliminar.png" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>
                </table>
            </td>
            <!--<tr>                
                <td colspan="6">
                <table class="detalle">
                    <tr>
                        <td colspan="6"><hr style="margin-bottom: 4px;margin-top: 4px;" /></td>
                    </tr>       
                    <tr>
                        <td colspan = "14" id='margen'>MECANIZADO<hr style="margin-bottom: 4px;margin-top: 4px;"/></td>
                    </tr>
                    <tr>
                        <td>Valor: $</td>
                        <td><input type="text" maxlength="9" id="txt_cotizacion_valor" name="txt_cotizacion_valor" onkeypress="return validarSiNumero(event)" /></td>         
                        <td>Margen%:</td>
                        <td><input type="text" maxlength="5" id="txt_cotizacion_margen" name="txt_cotizacion_margen" onkeypress="return validarSiDecimal(event)" /></td>                        
                        <!--<td>Total Material:</td>-->
                        <!--<td><input type="hidden" id="txt_cotizacion_totalmaterial" name="txt_cotizacion_totalmaterial" /></td>                        
                        
                    </tr>
                    <tr>
                        <td colspan="13" rowspan="5" id='margen'>
                            <div  id = "tablaSubDe" class="grillaConf">
                                <table id="tablaSubDetalle">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Numero</th>
                                            <th>Item</th>
                                            <th>Valor$</th>
                                            <th>Margen %</th>
                                            <th>Total $</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>    
                                    <input type="hidden" id="txt_correlativo2" value=""></input>
                                    <input type="hidden" id="txt_subitem" value=""></input>
                            </div>        
                        </td>            
                        <td id="bottom" rowspan="1">
                            <img style="cursor: pointer" onclick="ingresaSubDetalle()" id="SubDetalleIngreso" class="ico" border="0" src="images/logotipos/agregar.png" 
                            height="48px" width="25px"/>
                        </td>
                    </tr>
                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="SubDetalleModifica" style="display: none" onclick="modificaSubDetalle()" src="images/logotipos/modificar.png" border="0" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="SubDetalleCancela" style="display: none" onclick="CancelaSubDetalle()" src="images/logotipos/flecha.png" border="0" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>                      
                    <tr>
                        <td id="bottom">
                            <a href="#">
                                <img id="SubDetalleElimina" style="display: none" border="0" onclick="eliminaSubDetalle()" src="images/logotipos/eliminar.png" 
                                height="25px" width="25px" />
                            </a>
                        </td>
                    </tr>
                </table>    
                </td>    
            </tr> -->
        </tr>
    </table>
</div>            
<input class ="botonera" type="submit" id="btn_cotazacioncial_grabar" name="btn_cotazacioncial_grabar" value="Grabar" onclick="grabarCotizacion();"/>
<input class = "botonera" type="submit" name="btnCancela" value="Cancelar" onClick="goBack()" />
<!--<input class = "botonera" style="width: 150px" id="btn_cotizacion_aprobar" type="submit" name="btnAprobar" id="btnAprobar" value="Aprobar Cotizacion" onClick="aprobarCotiza()" />-->

<div id="dialog_trabajo" title="Piezas" style="display:none;">
    <div id="contenido_trabajo">
        Nombre de pieza:&nbsp;&nbsp;
       <input type="text" maxlength="11" id="txt_cotizacion_filtro_trabajo" name="txt_cotizacion_filtro_trabajo" />
       &nbsp;&nbsp;&nbsp;
       <input style="font-size:12px; height: 23px" class = "botonera" type="submit" name="btnCancela" value="Filtrar Trabajos" onclick="filtraTrabajos()" />
       <br/>
       <br/>
        <select size="10" style="width: 560px;" id="select_cotizacion_trabajo_filter" name="select_cotizacion_trabajo_filter" multiple>
            <%                                    
                stmt = _connMy.createStatement();
                q="select descripcion from svm_mae_tablas where tablas = 'Trabajos_Abreviados'";
                rsQuery = stmt.executeQuery(q);

                while(rsQuery.next())
                {
                    out.println("<option value='"+rsQuery.getString("descripcion")+"'>"+rsQuery.getString("descripcion")+"</option>");
                }
            %>
        </select>
    </div>
</div>

<div id="dialog_pieza" title="Piezas" style="display:none;">
    <div id="contenido_pieza">
        Nombre de pieza:&nbsp;&nbsp;
       <input type="text" maxlength="11" id="txt_cotizacion_filtro_pieza" name="txt_cotizacion_filtro_pieza" />
       &nbsp;&nbsp;&nbsp;
       <input style="font-size:12px; height: 23px" class = "botonera" type="submit" name="btnCancela" value="Filtrar Piezas" onclick="filtraPiezas()" />
       <br/>
       <br/>
        <select size="10" style="width: 560px;" id="select_cotizacion_pieza_filter" name="select_cotizacion_pieza_filter" multiple>
            <%                                    
                stmt = _connMy.createStatement();
                q="select codigo, nombre from svm_mae_pieza ORDER BY nombre";
                rsQuery = stmt.executeQuery(q);

                while(rsQuery.next())
                {
                    out.println("<option value='"+rsQuery.getString("codigo")+"'>"+rsQuery.getString("nombre")+"</option>");
                }
            %>
        </select>
    </div>
</div>
        
        
<div id="dialog_clientes" title="Clientes" style="display:none;" >
    <div id="contenido_clientes">
        <table>
            <tr>
                <td>Rut de cliente:</td>
                <td><input type="text" style="width: 160px" id="txt_filtro_cliente_rut" name="txt_filtro_cliente_rut" /></td>
                <td>&nbsp;&nbsp;<input style="font-size:12px; height: 23px; width: 120px" class = "botonera" type="submit" name="btnCancela" value="Filtrar Clientes" onclick="filtraClientes()" /></td>
            </tr>
            <tr>
                <td>Nombre de cliente</td>
                <td><input type="text" style="width: 160px" id="txt_filtro_cliente_nombre" name="txt_filtro_cliente_nombre" /></td>
                <td></td>
            </tr>
        </table>
       <br/>
        <select size="10" style="width: 560px;" id="select_cliente_filter" name="select_cliente_filter" multiple ondblclick="mainLink2(this);loadDialogMaeClientes();">
            <%                                    
                stmt = _connMy.createStatement();
                //q="SELECT concat(rut,'-',dv) rut, replace(concat(rpad(razon_social,20,' '),'|'),' ','&nbsp;') razon_social,replace(concat(rpad(concat(rut,'-',dv),20,' '),'|'),' ','&nbsp;') rut_pad, replace(concat(rpad(estado,20,' '),'|'),' ','&nbsp;') estado, nombre_contacto_comercial, email_contacto_comercial FROM svm_mae_clientes";
                
                
                q="select concat(cli.rut,'-',cli.dv) rut,replace(concat(rpad(cli.razon_social,40,' '),'|'),' ','&nbsp;') nombre,";
                q+="replace(concat(rpad(cli.estado,25,' '),'|'),' ',' ') estado,";
                q+="replace(concat(rpad(concat(cli.rut,'-',cli.dv),20,' '),'|'),' ','&nbsp;') rut_pad, cli.nombre_Contacto_Contable,";
                q+="replace(concat(rpad(cli.nombre_contacto_comercial,100,' '),'|'),' ','&nbsp;') nombre_contacto_comercial,";
                q+="replace(concat(rpad(cli.email_contacto_comercial,100,' '),'|'),' ','&nbsp;') email_contacto_comercial,";
                q+="replace(concat(rpad(us.nombre_user,40,' '),'|'),' ','&nbsp;') vendedor,";
                q+="replace(concat(rpad(concat(cli.celular_contacto_comercial, ' / ', cli.fijo_contacto_comercial),40,' '),'|'),' ','&nbsp;') fonos ";
                q+="from svm_mae_clientes cli inner join svm_mae_usuarios us ";
                q+="on cli.rut_vendedor=us.rut";


                rsQuery = stmt.executeQuery(q);
                while(rsQuery.next())
                {
                    out.println("<option value='"+rsQuery.getString("rut")+"'>"+rsQuery.getString("rut_pad")+rsQuery.getString("nombre")+rsQuery.getString("estado")+rsQuery.getString("vendedor")+rsQuery.getString("fonos")+"</option>");
                }
            %>
        </select>
    </div>
</div>
<div id="dialog_procesos" title="Proceso" style="display:none;">
    <div id="contenido_procesos">
    </div>
    <br/>
</div>
        
<div id="dialog_maestrocli" title="Maestro Clientes" style="display:none;">
    <div id="contenido_maeclientes">
        <table></table>
    <br/>
    </div>
</div>        
<div id="dialog_msg" title="Mensaje" style="display:none;" ></div>
    <input type="hidden" value="<%out.print(secuencia);%>" id="secuencia" name="secuencia" />
</body> 

<div id="dialog_detalleUnicoCliente" title="Detalle Unico Cliente" style="display:none;">
    <div id="contenido_detalleUnicoCliente">
        <textarea id="txt_detalleUnicoCliente" maxlength="500" style="width:555px;height:250px;"></textarea>
        <input type="hidden" id="txt_detalleUnicoCliente_hidden" name="txt_detalleUnicoCliente_hidden" value=""/>
    </div>
    <br/>
</div>

</html>