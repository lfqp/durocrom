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
        <title>Ingreso Orden Taller</title>
        <link rel="icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />
        <link rel="shortcut icon" href="images/logotipos/logo_solutel.ico" type="image/vnd.microsoft.icon" />   
        <link href="css/style_tabla.css" type="text/css" rel="stylesheet" />
        <link href="css/solutel.css" type="text/css" rel="stylesheet" />
        <!--Codigo Sistemas SA-->
        <link href="css/calendario.css" type="text/css" rel="stylesheet" /> 

        <!--<link type="text/css" rel="stylesheet" media="screen" href="css/jquery.ui.all.css"/>-->
        <link type="text/css" rel="stylesheet" media="screen" href="css/bootstrap-theme.min.css"/>
        <link type="text/css" rel="stylesheet" media="screen" href="css/jquery-ui.css"/>
        <link type="text/css" rel="stylesheet" href="css/bootstrap-3.3.5.min.css"/>
        

        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui.js" type="text/javascript"></script>

        <script src="js/calendar.js" type="text/javascript"></script>
        <script src="js/calendar-es.js" type="text/javascript"></script>
        <script src="js/calendar-setup.js" type="text/javascript"></script>
        <script src="js/Funcion_Errores.js" type="text/javascript"></script>
        <script src="js/validaciones.js" type="text/javascript"></script>        
        <!-- Librerias Jquery -->
        <script type="text/javascript" src="js/bootstrap-3.3.5.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate-1.14.0.min.js"></script>
        <script type="text/javascript" src="js/jquery-validate.bootstrap-tooltip.js"></script>        
        <script src="js/jquery.validate.js" type="text/javascript"></script>
        <script src="js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="js/messages_es.js" type="text/javascript" ></script>
        <script src="js/CargarCombo.js" type="text/javascript" ></script>
        <script src="js/CRUD_cotizacion_det.js" type="text/javascript"></script>
        <script src="js/CRUD_OrdenTaller.js" type="text/javascript"></script>
        <script src="js/CRUD_ordentaller_det.js" type="text/javascript"></script>
        <%
            //TipoUser, Rut y Nombre Vendedor PAra actualiza Actividad comercial.    
            HttpSession s = request.getSession();
            Connection _connMy = null;
            CallableStatement sp_usu = null;            
            String rut = "";
            String nom = "";
            String secuencia = "";  
            String numeroOT = "";
            String accion = "";
            String numero_cotiza = "";
            String nombre_user = "";
            String dias_habiles = "";
            String presup_valido = "";
            String cond_pago = "";
            String rut_cli = "";
            String razon_social = "";
            String estado_cli = "";
            String nom_contacto_com = "";
            String email_contacto_com = "";
            String fono = "";
            String desc_pieza = "";
            String nuevo_usado = "";
            String material_base = "";
            String trabajo_abrev = "";
            String cant = "";
            String ancho = "";
            String largo = "";
            String diametro = "";
            String area = "";
            String super_total = "";
            String corriente = "";
            String comentarios = "";
            String fecha_termino = "";
            String fecha_emision = "";
            String estado = "";
            String condicion = "";
            String cantidad = "";
            String dias_restantes = "";
            String fecha_hoy = "";
            String valorUF = "";
            String fecha_facturacion = "";
            String numero_factura = "";
            //corrOT = request.getParameter("secuencia") != null ? request.getParameter("secuencia") : "0";

            try {
                _connMy = conexionBD.Conectar((String) s.getAttribute("organizacion"));                                 
                
                Statement stmt = null;
                ResultSet rsQuery = null;                                       
                String q = "select fn_secuencia_otHist() secuencia;";                                        
                stmt = _connMy.createStatement();
                rsQuery = stmt.executeQuery(q);

                rsQuery.first();

                secuencia = rsQuery.getString("secuencia");
                
                q = "select descripcion from svm_mae_tablas where tablas = 'Valor_UF'";                                        
                stmt = _connMy.createStatement();
                rsQuery = stmt.executeQuery(q);

                rsQuery.first();
                
                valorUF = rsQuery.getString("descripcion");
                
                if (s.getAttribute("nom") != null) {
                    nom = (String) s.getAttribute("nom");
                    //response.sendRedirect("login.jsp");
                }                    
                numeroOT = request.getParameter("numeroOT") != null ? request.getParameter("numeroOT") : "";
                accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
                
                sp_usu = _connMy.prepareCall("{call sp_ordentaller(?,?,?,?,?,?,?,?)}");
                sp_usu.setString(1, "select");
                sp_usu.setString(2, numeroOT);
                sp_usu.setInt(3, 0);
                sp_usu.setString(4, "");
                sp_usu.setString(5, "");
                sp_usu.setString(6, "");
                sp_usu.setString(7, "");
                sp_usu.setString(8, "");
                sp_usu.registerOutParameter(1, Types.VARCHAR);                                
                sp_usu.execute();
                
                final ResultSet rs = sp_usu.getResultSet();
                while(rs.next())
                {
                    numero_cotiza = rs.getString("numero_cotizacion");
                    nombre_user = rs.getString("nombre_user");
                    dias_habiles = rs.getString("dias_habiles");
                    presup_valido = rs.getString("presupuesto_valido");
                    cond_pago = rs.getString("condiciones_pago");
                    rut_cli = rs.getString("rut_cli");
                    razon_social = rs.getString("razon_social");
                    estado_cli = rs.getString("estado_cliente");
                    nom_contacto_com = rs.getString("nombre_contacto_comercial");
                    email_contacto_com = rs.getString("email_contacto_comercial");
                    fono = rs.getString("fonos");
                    desc_pieza = rs.getString("desc_pieza");
                    nuevo_usado = rs.getString("nuevo_usado");
                    material_base = rs.getString("material_base");
                    trabajo_abrev = rs.getString("trabajo_abreviado");
                    cant = rs.getString("cantidad");
                    ancho = rs.getString("ancho");
                    largo = rs.getString("largo");
                    diametro = rs.getString("diametro");
                    area = rs.getString("area");
                    super_total = rs.getString("superficie_total");
                    corriente = rs.getString("corriente");
                    comentarios = rs.getString("comentarios");
                    fecha_termino = rs.getString("fecha_termino");
                    fecha_emision = rs.getString("fecha_emision");
                    estado = rs.getString("estado");
                    condicion = rs.getString("condicion");
                    cantidad = rs.getString("cantidad");
                    dias_restantes = rs.getString("dias_restantes");
                    fecha_hoy = rs.getString("fecha_hoy");
                    
                    fecha_facturacion = rs.getString("fecha_facturacion");
                    
                    if(fecha_facturacion.equals("null"))
                       fecha_facturacion = "";
                    
                    numero_factura = rs.getString("numero_factura");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }            
        %>
        <script type="text/javascript">                
        
        $(document).ready(function () {                                             
                
            var accion= $("#txt_accion").val();
            if (accion == "consulta")
            {
                $("#btn_ordentaller_rebaja").hide();
                $("#btn_ordentaller_grabar").hide();
            }
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
            
            historialOT("select_all");
        });
        
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
                }                            

        function goBack(seq,numeroOT) {            
            $.ajax({
                url : 'ServletSPOrdenTallerHist', 
                data:{
                  opcion: "limpia",
                  txt_orden_numeroOT: numeroOT,
                  txt_orden_CantRebaja: "",
                  seq: seq,
                  id: ""
                },
                type : 'POST',
                dataType : "html",
                async:false,
                success : function(data) {
                    location.href = 'svm_Seleccion_OT.jsp';
                }
            });            
        }
        
        function loadDialogProcesos(){
            $( "#dialog_procesos" ).dialog("open");
        }
        
        function guardarOT(parametroAccion){
            var accion = parametroAccion || "update";
            var data = {
                opcion: accion,
                //txt_estado: 1,
                numero_factura: $("#txt_numero_factura").val(),
                txt_orden_numero: $("#txt_orden_numeroOT").val(),
                fecha_factura: $("#txt_fecha_facturacion").val()
            };
            
            $.ajax({
                url : 'ServletSPOrdenTaller', 
                data: data,
                type : 'POST',
                async: false,
                success : function(response){
                    console.log(response);
                }
            });
        }
        
        function historialOT(accion)
        {   
            var numeroOT = "";
            var cantRebaja = "";
            var seq = "";
            var id = "";
            
            seq = $("#txt_orden_secuencia").val();
            numeroOT = $("#txt_orden_numeroOT").val();
            cantRebaja = $("#txt_orden_CantRebaja").val();
            if(accion == 'elimina')
            {
                id = $("#idRebaja").val();      
                if(id == '')
                {
                    FuncionErrores(262);
                    return false;
                }
                cantRebaja = $("#cantElim").val();
            }
            if(accion == 'elimina' || accion == 'insert')
            {
                if(cantRebaja <= 0)
                {
                    FuncionErrores(260);
                    return false;
                }
            }
            $.ajax({
                url : 'ServletSPOrdenTallerHist', 
                data:{
                  opcion: accion,                  
                  txt_orden_CantRebaja: cantRebaja,
                  seq: seq,      
                  id: id,
                  txt_orden_numeroOT: numeroOT
                },
                type : 'POST',
                dataType : "html",
                async:false,
                success : function(data) {
                    if (accion == "insert" )
                    {
                        var arrResult=data.split("~");
                        if(arrResult[0] == "OK")                            
                        {
                            $("#tbl_orden_hist").find("tbody").html(arrResult[2]);
                            $("#lbl_orden_saldo").html(arrResult[1]);
                            $("#txt_orden_CantRebaja").val("");
                        }
                        else
                        {                            
                            FuncionErrores(256);
                            return false;
                        }
                    }
                    if(accion == "elimina" )
                    {
                        var arrResult=data.split("~");                        
                        $("#tbl_orden_hist").find("tbody").html(arrResult[0]);
                        $("#lbl_orden_saldo").html(arrResult[1]);   
                        $("#idRebaja").val("");
                        
                    }
                    if(accion == "select_all" )
                    {
                        var arrResult=data.split("~");                        
                        $("#tbl_orden_hist").find("tbody").html(arrResult[0]);
                        $("#lbl_orden_saldo").html(arrResult[1]);                        
                        
                    }
                    if(accion == "save")
                    {                        
                        if($.trim(data) == "NO_OK")
                        {                            
                            FuncionErrores(257);                            
                        }
                        goBack(seq,numeroOT);
                    }
                }
            });
        }        
        </script>
    </head>
    <body id="principal">
        <input type="hidden" value="<%=secuencia%>" id="txt_orden_secuencia" />
        <input type="hidden" value="<%=valorUF%>" id="txt_orden_valorUF" />
        <input type="hidden" value="<%=accion%>" id="txt_accion" />
        <input type="hidden" value="" id="idRebaja" name="idRebaja" />
        <input type="hidden" value="" id="cantElim" name="cantElim" />
        <div class="formularioIngresar">
            <table id="header">                           
                <tr>
                    <td>
                        <form action="" method="post">
                            <table border="0" style="width:100%">
                                <tr>
                                    <td colspan ="2" >DESCRIPCI&Oacute;N GENERAL<hr style="margin-bottom: 4px;margin-top: 4px;" /></td>
                                    <td colspan = "3">DESCRIPCI&Oacute;N CLIENTES<hr style="margin-bottom: 4px;margin-top: 4px;"/></td>
                                    <td colspan = "4"style="padding-left: 138px;" rowspan="6" >
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
                                                            <th>Rut Usuario</th>                                    
                                                            <th>Nombre Usuario</th>
                                                        </tr>
                                                    </thead>				
                                                    <tbody>
                                                        <%
                                                            int cont = 0;
                                                            String var = "select";
                                                            sp_usu = _connMy.prepareCall("{call sp_historialOT(?,?,'','')}");
                                                            sp_usu.setString(1, var);
                                                            sp_usu.setString(2, numeroOT);
                                                            sp_usu.execute();
                                                            final ResultSet rsHistorial = sp_usu.getResultSet();
                                                            String claseGrilla = "";
                                                            while (rsHistorial.next()) {
                                                                if (cont % 2 == 0) {
                                                                    claseGrilla = "alt";
                                                                }
                                                                out.println("<tr id='filaTablaHistorial" + cont + "' class='" + claseGrilla + "'>");
                                                        %>                                                                                                                                                                    
                                                        <td id ="historial_estAnterior<%=cont%>"><%=rsHistorial.getString("estado_anterior")%></td>
                                                        <td id ="historial_estSiguiente<%=cont%>"><%=rsHistorial.getString("estado_siguiente")%></td>
                                                        <td id ="historial_fecha<%=cont%>"><%=rsHistorial.getString("fecha")%></td>
                                                        <td id ="historial_rutUser<%=cont%>"><%=rsHistorial.getString("rutUser")%></td>
                                                        <td id ="historial_nomUser<%=cont%>"><%=rsHistorial.getString("nomUser")%></td>                                                                                   
                                                        <%
                                                                out.print("</tr>");
                                                                claseGrilla = "";
                                                                cont++;
                                                            }
                                                        %>                               
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </td>
                                </tr>                                 
                                <tr>
                                    <td >N° Cotizaci&oacute;n</td>                            
                                    <td style="padding-bottom: 3px;">                                 
                                        <input type="text" disabled= "disabled" value="<%=numero_cotiza%>" id="txt_orden_numero" maxlength="11" name="txt_orden_numero" />
                                    </td>                                                                                                  
                                    <td  >RUT:</td>
                                    <td colspan="2">
                                        <input disabled type="text" value="<%=rut_cli%>" readonly id="txt_orden_rutcli" maxlength="11" name="txt_orden_rutcli" />                                    
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:8%;">Fecha emisi&oacute;n OT:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input disabled="disabled" type = "text" value="<%=fecha_emision%>" readonly name = "txt_orden_fecha" readonly id= "txt_orden_fecha" size="12" />                                        
                                    </td>                                                              
                                    <td>Cliente:</td>
                                    <td rowspan="3">
                                        <textarea rows="3" disabled cols="27" readonly id="txt_orden_cliente" maxlength="40"><%=razon_social%>
                                        </textarea> 
                                    </td>                                                                                                          
                                </tr>
                                <tr>
                                    <td style="width:10%;">D&iacute;as h&aacute;biles de trabajo:</td>
                                    <td style="padding-bottom: 3px;"><input type="text" value="<%=dias_habiles%>" id="txt_orden_DHabiles" disabled="disabled" name="txt_orden_DHabiles"/></td>
                                </tr>                                
                                <tr>
                                    <td>Vendedor:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type="text" style="width: 150px" id="txt_orden_Vendedor" value="<%=nombre_user%>" disabled="disabled" name="txt_orden_Vendedor"/>
                                    </td>                                    
                                </tr>                                                                
                                <tr>
                                    <td>Condici&oacute;n de pago:</td>
                                    <td style="padding-bottom: 5px;">
                                        <input type="text" id="txt_orden_CondPago" style="width: 200px" value="<%=cond_pago%>" disabled="disabled" name="txt_orden_CondPago"/>
                                    </td>
                                    <td>Estado Cliente:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type="text" id="txt_orden_estCliente" value="<%=estado_cli%>" disabled="disabled" name="txt_orden_estCliente"/>
                                    </td>
                                    
                                </tr>
                                <tr>
                                    <td>D&iacute;as de Validez:</td>
                                    <td style="padding-bottom: 5px;">
                                        <input type="text" style="width: 150px" id="txt_orden_DVAlidez" value="<%=presup_valido%>" disabled="disabled" name="txt_orden_DVAlidez"/>
                                    </td>
                                    <td style="font-size: 14px" colspan ="2"><b>Contacto Comercial</b></td>                                    
                                </tr>
                                <tr>
                                    <td style="font-size: 14px" colspan="2"><b>Facturación</b></td>
                                    <td style="width: 8%">Nombre Completo:</td>
                                    <td style="padding-bottom: 5px;">
                                        <input type="text" id="txt_orden_NomComercial" value="<%=nom_contacto_com%>" disabled="disabled" name="txt_orden_NomComercial"/>
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td>Fecha facturación</td>
                                    <td>
                                        <input type = "text" value="<%=fecha_facturacion%>" name = "txt_fecha_facturacion" id= "txt_fecha_facturacion" size="12" />
                                        <img src="images/calendario.png" width="16" height="16" border="0" title="Fecha Inicial" id="lanza" />
                                        <!-- script que define y configura el calendario--> 
                                        <script type="text/javascript"> 
                                            Calendar.setup({ 
                                            inputField     :    "txt_fecha_facturacion",     // id del campo de texto 
                                            ifFormat     :     "%d-%m-%Y",     // formato de la fecha que se escriba en el campo de texto 
                                            button     :    "lanza"     // el id del botÃ³n que lanzarÃ¡ el calendario 
                                            }); 
                                        </script>
                                        <!--Codigo Sistemas SA-->
                                    </td>
                                    <td>Email:</td>
                                    <td style="padding-bottom: 5px;">
                                        <input type="text" style="width: 200px" value="<%=email_contacto_com%>" id="txt_orden_EmailComercial" disabled="disabled" name="txt_orden_NomComercial"/>
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td>Número factura</td>
                                    <td>
                                        <input type = "text" value="<%=numero_factura%>" name = "txt_numero_factura" id= "txt_numero_factura" size="12" />
                                    </td>
                                    <td>Fonos:</td>
                                    <td style="padding-bottom: 5px;">
                                        <input type="text" style="width: 200px" value="<%=fono%>" id="txt_orden_FonosComercial" disabled="disabled" name="txt_orden_FonosComercial"/>
                                    </td>                                    
                                </tr>
                                <tr id="Titulo_descp_piezas">
                                    <td  colspan = "10" id="margen" bgcolor="#2798D9" style="color: white; font-size: 18px; margin-bottom: 8px" align="center" ><b>DETALLE ORDEN DE TALLER</b></td>
                                </tr>
                                <tr style="height: 10px;">
                                    <td colspan="7"></td>
                                </tr>
                                <tr>
                                    <td >N° Orden de Taller</td>                            
                                    <td style="padding-bottom: 3px;">                                 
                                        <input type="text" disabled= "disabled" value="<%=numeroOT%>" id="txt_orden_numeroOT" maxlength="11" name="txt_orden_numeroOT" />
                                    </td>  
                                    <td>Estado OT:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=estado%>" name = "txt_orden_fechaProm" readonly id= "txt_orden_fechaProm" size="12" />                                        
                                    </td>
                                    <td>Cantidad:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=cantidad%>" name = "txt_orden_cantidad" readonly id= "txt_orden_cantidad" size="12" />                                        
                                    </td>
                                    <td style="width: 5%;">Observaciones:</td>
                                    <td rowspan ="5" style="width: 5%;">
                                        <textarea disabled rows="6" cols="40" id="txt_orden_observaciones" maxlength="200"><%=comentarios%>
                                        </textarea> 
                                    </td>
                                    <td align="right">
                                        <input class = "botonera" readonly style="width: 100px" type="button" name="btnProcesos" id="btnProcesos" value="Procesos" onClick="cargaProcesos();loadDialogProcesos();"/>
                                    </td> 
                                </tr>                                                                                        
                                <tr>
                                    <td>Fecha Prometida:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=fecha_termino%>" name = "txt_orden_fechaProm" readonly id= "txt_orden_fechaProm" size="12" />                                        
                                    </td> 
                                    <td>Condici&oacute;n OT:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=condicion%>" name = "txt_orden_condicion" readonly id= "txt_orden_condicion" size="12" />                                        
                                    </td> 
                                    <td style="width: 8%">D&iacute;as Restantes:</td>
                                    <td style="padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=dias_restantes%>" name = "txt_orden_DRestante" readonly id= "txt_orden_DRestante" size="12" />                                        
                                    </td> 
                                </tr>                                
                                <tr style="height: 10px;">
                                    <td colspan="10"></td>
                                </tr>
                                <tr>
                                    <td>Pieza:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" style="width: 100px" disabled= "disabled" value="<%=desc_pieza%>" name = "txt_orden_pieza" readonly id= "txt_orden_pieza" size="12" />
                                    </td>
                                    <td>Di&aacute;ametro (mm):</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=diametro%>" name = "txt_orden_diametro" readonly id= "txt_orden_diametro" size="12" />
                                    </td>
                                    <td>Superficie (dm2):</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=area%>" name = "txt_orden_super" readonly id= "txt_orden_super" size="12" />
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td>Nuevo / Usado:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=nuevo_usado%>" name = "txt_orden_NoU" readonly id= "txt_orden_NoU" size="12" />
                                    </td>
                                    <td>Largo (mm):</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=largo%>" name = "txt_orden_largo" readonly id= "txt_orden_largo" size="12" />
                                    </td>
                                    <td style="">Superficie Total (dm2):</td>
                                    <td>
                                        <input type = "text" disabled= "disabled" value="<%=super_total%>" name = "txt_orden_SupTotal" readonly id= "txt_orden_SupTotal" size="12" />
                                    </td> 
                                </tr>
                                <tr>
                                    <td>Material Base:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" style="width: 150px" disabled= "disabled" value="<%=material_base%>" name = "txt_orden_MaterialBase" readonly id= "txt_orden_MaterialBase" size="12" />
                                    </td>
                                    <td>Ancho (mm):</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value="<%=ancho%>" name = "txt_orden_ancho" readonly id= "txt_orden_ancho" size="12" />
                                    </td>
                                    <td>Corriente (A):</td>
                                    <td>
                                        <input type = "text" disabled= "disabled" value="<%=corriente%>" name = "txt_orden_Corriente" readonly id= "txt_orden_Corriente" size="12" />
                                    </td> 
                                </tr>
                                <tr>
                                    <td>Trabajo Atrevido:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" style="width: 150px" disabled= "disabled" value="<%=trabajo_abrev%>" name = "txt_orden_TrabAtrevido" readonly id= "txt_orden_TrabAtrevido" size="12" />
                                    </td>                                    
                                </tr>
                                <tr style="height: 10px;">
                                    <td colspan="10"></td>
                                </tr>
                                <tr id="Titulo_descp_piezas">
                                    <td  colspan = "10" id="margen" bgcolor="#2798D9" style="color: white; font-size: 18px; margin-bottom: 8px" align="center" ><b>REBAJA OT</b></td>
                                </tr>
                                <tr style="height: 10px;">
                                    <td colspan="10"></td>
                                </tr>
                                <tr>
                                    <td>Fecha Rebaja:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text" disabled= "disabled" value ="<%=fecha_hoy%>" name = "txt_orden_TrabAtrevido" readonly id= "txt_orden_TrabAtrevido" size="12" />
                                    </td>
                                    <td>Cantidad a Rebajar:</td>
                                    <td style=" padding-bottom: 3px;">
                                        <input type = "text"  name = "txt_orden_CantRebaja" id= "txt_orden_CantRebaja" maxlength="11" />
                                    </td>                                    
                                    <td>
                                        <input class ="botonera" type="button" id="btn_ordentaller_rebaja" name="btn_ordentaller_rebaja" value="REBAJAR" onclick="historialOT('insert')"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>                                                                      
                                    <td colspan ="3" style="text-align: center; font-weight: bold; font-size: 14px;">Cantidad M&aacute;xima: <label id="lbl_orden_saldo"></label></td>                                        
                                </tr>                                                    
                                <tr>
                                    <td style="font-size: 14px; padding-left: 10px;" colspan ="2"><b>Historia de Rebaja</b></td>
                                </tr>
                                <tr>
                                    <td style="padding: 10px;" colspan="5">
                                        <div class="grillaConf">
                                            <table id ="tbl_orden_hist">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>N&uacute;mero OT</th>
                                                        <th>Fecha Rebaja</th>
                                                        <th>Cantidad Rebajado</th>
                                                        <th>Nombre Usuario</th>
                                                    </tr>
                                                </thead>				
                                                <tbody>                                                                                     
                                                </tbody>
                                            </table>
                                        </div>
                                    </td>
                                    <%
                                        if (accion.equals("modifica"))
                                        {
                                    %>
                                    <td style=" padding-top: 40px;" >
                                        <img id="DetalleElimina" title="Elimina Rebaja"  border="0" onclick="historialOT('elimina')" src="images/logotipos/eliminar.png" 
                                        height="25px" width="25px" />
                                    </td>
                                    <%
                                        }
                                    %>
                                </tr>
                                <tr style="height: 10px;">
                                    <td colspan="7"></td>
                                </tr>
                            </table>                                                                                        
                        </form>
                    </td>
                </tr>      
            </table>
        </div>            
                                    <input class ="botonera" type="submit" id="btn_ordentaller_grabar" name="btn_ordentaller_grabar" value="Grabar" onclick="historialOT('save'); guardarOT();"/>
        <input class = "botonera" type="submit" name="btnCancela" value="Cancelar" onClick="goBack(<%=secuencia%>,'<%=numeroOT.toString()%>')" />
        
<div id="dialog_procesos" title="Proceso" style="display:none;">
    <div id="contenido_procesos">
    </div>
    <br/>
</div>             

    </body>
</html>

                