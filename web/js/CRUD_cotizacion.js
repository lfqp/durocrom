function grabarCotizacion(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var fechaEmision2=$("#txt_cotizacion_fecha").val();
    var dia=fechaEmision2.substring(0, 2);
    var mes=fechaEmision2.substring(3, 5);
    var year=fechaEmision2.substring(6, 10);
    
    var fechaEmision = year + "-" + mes + "-" + dia;
    //var emitidaPor=$("#txt_cotizacion_emitida_por").val();
    var emitidaPor=$("#rutUsuario").val();
    
    var moneda=$("#select_cotizacion_moneda").val();
    var presupuestoValido=$("#select_cotizacion_presupuesto_valido").val();
    var plazoEntrega=$("#select_cotizacion_plazo_entrega").val();
    var condicionesPago=$("#select_cotizacion_condicion_pago").val();
    var estado=$("#select_cotizacion_estado").val();
    var total=$("#txt_cotizacion_total").val();
    
    var rutCli=$("#txt_cotizacion_rutcli").val();
    var nombreCli=escape($("#txt_cotizacion_cli").val());
    var diasHabiles=$("#txt_cotizacion_dias_habiles").val();
    var estadoCliente=$("#select_cotizacion_estado_cliente").val();
    var nombreContactoComercial=$("#txt_cotizacion_contacto_nombre").val();    
    var emailContactoComercial=$("#txt_cotizacion_contacto_email").val();        
    var valorUF=$("#txt_cotizacion_valorUF").val();
    var ocAprobacion=$("#select_cotizacion_oc").val();    
    var totalProcesos=$("#select_cotizacion_total_procesos").val();
    var totalMateriales=$("#select_cotizacion_total_materiales").val();    
    var totalNeto=$("#txt_cotizacion_total_neto").val();        
    var iva=$("#txt_cotizacion_iva").val();
    var totalBruto=$("#txt_cotizacion_total_bruto").val(); 
    var vendedor=$("#txt_cotizacion_emitida_por").val(); 
    var fonos=$("#txt_cotizacion_contacto_fonos").val(); 
    
    var sequence =getUrlParameter('secuencia');
    var accion=getUrlParameter('accion');
    
    var detalle_unico_cliente = $("#txt_detalleUnicoCliente").val();
    if ($("#txt_cotizacion_rutcli").val() == "") {
        FuncionErrores(19);
        $("#txt_cotizacion_rutcli").focus();
        return false;
    }
    
    if ($("#txt_cotizacion_cli").val() == "") {
        FuncionErrores(241);
        $("#txt_cotizacion_cli").focus();
        return false;
    }    
    
    if ($("#txt_cotizacion_emitida_por").val() == "") {
        FuncionErrores(22);
        $("#txt_cotizacion_emitida_por").focus();
        return false;
    }  
    
    if ($("#select_cotizacion_moneda").val() == "") {
        FuncionErrores(23);
        $("#select_cotizacion_moneda").focus();
        return false;
    }         
    
    if ($("#select_cotizacion_presupuesto_valido").val() == "") {
        FuncionErrores(25);
        $("#select_cotizacion_presupuesto_valido").focus();
        return false;
    }     
    
    if ($("#select_cotizacion_plazo_entrega").val() == "") {
        FuncionErrores(26);
        $("#select_cotizacion_plazo_entrega").focus();
        return false;
    }    
    
    if ($("#select_cotizacion_condicion_pago").val() == "") {
        FuncionErrores(27);
        $("#select_cotizacion_condicion_pago").focus();
        return false;
    }     
    
    if ($("#txt_cotizacion_dias_habiles").val() == "") {
        FuncionErrores(44);
        $("#txt_cotizacion_dias_habiles").focus();
        return false;
    }
    
    if ($("#select_cotizacion_estado").val() == "") {
        FuncionErrores(250);
        $("#select_cotizacion_estado").focus();
        return false;
    }       
    
    if ($("#cantidad_detalle").val() == "0") {
        FuncionErrores(39);
        $("#select_cotizacion_pieza").focus();
        return false;
    }         

    if(accion=="modifica"||numeroCotizacion!=""){
        accion="update";
    }else{
        accion="insert";
    }
    
    $.ajax({
        url : 'ServletSPCotizacion', 
        data:{
          opcion: accion,
          txt_cotizacion_numero: numeroCotizacion,
          txt_cotizacion_fecha: fechaEmision,
          txt_cotizacion_dias_habiles: diasHabiles,
          txt_cotizacion_ruteje: vendedor,
          select_cotizacion_presupuesto_valido: presupuestoValido,
          select_cotizacion_plazo_entrega: plazoEntrega,
          select_cotizacion_condicion_pago: condicionesPago,
          txt_cotizacion_rutcli: rutCli,
          txt_cotizacion_moneda: moneda,
          txt_cotizacion_total: total,
          txt_cotizacion_estado: estado,
          txt_cotizacion_cli: nombreCli,
          select_cotizacion_estado_cliente: estadoCliente,
          txt_cotizacion_contacto_nombre: nombreContactoComercial,
          txt_cotizacion_contacto_email: emailContactoComercial,
          txt_cotizacion_valorUF: valorUF,
          select_cotizacion_oc: ocAprobacion,
          select_cotizacion_total_procesos: totalProcesos,
          select_cotizacion_total_materiales: totalMateriales,
          txt_cotizacion_total_neto: totalNeto,
          txt_cotizacion_iva: iva,
          txt_cotizacion_total_bruto: totalBruto,
          txt_cotizacion_emitida_por: vendedor,
          txt_cotizacion_contacto_fonos: fonos,
          sequencia: sequence,
          detalle_unico_cliente: detalle_unico_cliente,
          fecha_desde: '',
          fecha_hasta: '',
          select_nro_ot: numero_ot 
          
          
        },
//        data: "opcion="+accion+"&txt_cotizacion_numero="+numeroCotizacion+"&txt_cotizacion_fecha="+fechaEmision
//                +"&txt_cotizacion_dias_habiles="+diasHabiles+"&txt_cotizacion_ruteje="+vendedor
//                +"&select_cotizacion_presupuesto_valido="+presupuestoValido+"&select_cotizacion_plazo_entrega="+plazoEntrega
//                +"&select_cotizacion_condicion_pago="+condicionesPago+"&txt_cotizacion_rutcli="+rutCli+"&txt_cotizacion_moneda="+moneda
//                +"&txt_cotizacion_total="+total+"&txt_cotizacion_estado="+estado
//                +"&txt_cotizacion_cli="+nombreCli+"&select_cotizacion_estado_cliente="+estadoCliente+"&txt_cotizacion_contacto_nombre="+nombreContactoComercial
//                +"&txt_cotizacion_contacto_email="+emailContactoComercial
//                +"&txt_cotizacion_valorUF="+valorUF+"&select_cotizacion_oc="+ocAprobacion+"&select_cotizacion_total_procesos="+totalProcesos
//                +"&select_cotizacion_total_materiales="+totalMateriales+"&txt_cotizacion_total_neto="+totalNeto+"&txt_cotizacion_iva="+iva
//                +"&txt_cotizacion_total_bruto="+totalBruto+"&txt_cotizacion_emitida_por="+vendedor+"&txt_cotizacion_contacto_fonos="+fonos
//                +"&sequencia="+sequence+"&fecha_desde=&fecha_hasta&detalle_unico_cliente="+detalle_unico_cliente,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#txt_cotizacion_numero").val(data);
            asociaDetalle();
            guardaDetalle();
            if (accion == "insert") {
                openDialog("Se ha creado la cotizaci&oacute;n N&ordm; " + data);
            }
            $(window).unbind('beforeunload');
            location.href="svm_Seleccion_Cotizacion.jsp";
        }
    });
}

function cargaCotizacion(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var fechaEmision=$("#txt_cotizacion_fecha").val();
    
    var rutCli=$("#txt_cotizacion_rutcli").val();
    var nombreCli=escape($("#txt_cotizacion_cli").val());
    
    var desde =$("#txt_filtroComercial_ingreso").val();
    var hasta =$("#txt_filtroComercial_final").val();
    
    var sequence =getUrlParameter('secuencia');
    var accion=getUrlParameter('accion');
    
    
    
    var date = new Date();
    var dd=pad(date.getDate(),2,'0');
    var mm=pad(date.getMonth()+1,2,'0');
    var yy=date.getFullYear();
    
    var fecha=dd+"-"+mm+"-"+yy;
    
    $.ajax({
        url : 'ServletSPCotizacion', 
        data: "opcion=select"+"&txt_cotizacion_numero=0"+"&txt_cotizacion_fecha="+fecha
                +"&txt_cotizacion_emitida_por="+"&txt_cotizacion_dias_habiles=0"
                +"&select_cotizacion_presupuesto_valido=0"+"&select_cotizacion_plazo_entrega=0"
                +"&select_cotizacion_condicion_pago=0"+"&txt_cotizacion_rutcli=0"+"&txt_cotizacion_moneda=0"+"&txt_cotizacion_total=0"
                +"&select_cotizacion_total_procesos=0"+"&select_cotizacion_total_materiales=0"
                +"&txt_cotizacion_total_neto=0"+"&txt_cotizacion_iva=0"+"&txt_cotizacion_total_bruto=0"
                +"&txt_cotizacion_cli=0"+"&txt_cotizacion_estado=0"+"&sequencia="+sequence+"&fecha_desde="+desde+"&fecha_hasta="+hasta,
        type : 'POST',
        dataType : "html",
//        async:false,
        success : function(data) {
            var arrResult=data.split("|");
            
            if(accion=="consulta"){
                $("#btn_cotazacioncial_grabar").hide();
                $("#btn_cotizacion_aprobar").hide();
                $("#DetalleIngreso").hide();
                $("#SubDetalleIngreso").hide();
                $("#btnClientes").hide();
                $("#lanzador").hide();
                $("#lanzador2").hide();
                $("#btnPiezas").hide();
                $("#btnArea").hide();
                $("#txt_cotizacion_rutcli").attr("readonly","readonly");
                $("#txt_cotizacion_cli").attr("readonly","readonly");
                $("#select_cotizacion_moneda").prop("disabled",true);
                $("#select_cotizacion_estado").prop("disabled",true);
                $("#txt_cotizacion_cantidad").attr("readonly","readonly");
                $("#txt_cotizacion_dm").attr("readonly","readonly");
                $("#txt_cotizacion_diametroint").attr("readonly","readonly");
                $("#txt_cotizacion_largo").attr("readonly","readonly");
                $("#txt_cotizacion_valUniCrom").attr("readonly","readonly");
                $("#txt_cotizacion_cHora").attr("readonly","readonly");
                $("#txt_cotizacion_cantHrs").attr("readonly","readonly");
                $("#txt_cotizacion_valor").attr("readonly","readonly");
                $("#txt_cotizacion_margen").attr("readonly","readonly");
                $("#txt_cotizacion_ancho").attr("readonly","readonly");
                $("#txt_cotizacion_comentarios").attr("readonly","readonly");
                $("#select_cotizacion_oc").attr("readonly","readonly");
                $("#txt_cotizacion_emitida_por").prop("disabled",true);
                $("#select_cotizacion_presupuesto_valido").prop("disabled",true);
                $("#select_cotizacion_plazo_entrega").prop("disabled",true);
                $("#select_cotizacion_condicion_pago").prop("disabled",true);
                $("#select_cotizacion_pieza").prop("disabled",true);
                $("#select_cotizacion_nuevo_usado").prop("disabled",true);
                $("#select_cotizacion_materialbase").prop("disabled",true);
                $("#select_cotizacion_trabajo").prop("disabled",true);
                var dia=arrResult[1].substring(8, 10);
                var mes=arrResult[1].substring(5, 7);
                var year=arrResult[1].substring(0, 4);
                var fechaCoti = dia + "-" + mes + "-" + year;                
                $("#txt_cotizacion_fecha").val(fechaCoti);               
                $("#txt_cotizacion_dias_habiles").val(arrResult[8]);  
                $("#txt_detalleUnicoCliente").prop("disabled", true);
            }
            
            if(accion=="modifica") {
                $("#btnArea").hide();
                var dia=arrResult[1].substring(8, 10);
                var mes=arrResult[1].substring(5, 7);
                var year=arrResult[1].substring(0, 4);
                var fechaCoti = dia + "-" + mes + "-" + year;                
                $("#txt_cotizacion_fecha").val(fechaCoti);              
                $("#txt_cotizacion_dias_habiles").val(arrResult[8]);                  
            }            
            else {
                $("#btnArea").hide();
                //$("#txt_cotizacion_fecha").val(arrResult[1]);
            }
            if (accion=="modifica" || accion=="consulta") {
                $("#txt_cotizacion_numero").val(arrResult[0]);
                $("#txt_cotizacion_emitida_por").val(arrResult[2]);
                $("#select_cotizacion_presupuesto_valido").val(arrResult[3]);
                $("#select_cotizacion_plazo_entrega").val(arrResult[4]);               
                $("#select_cotizacion_condicion_pago").val(arrResult[5]);
                $("#txt_cotizacion_rutcli").val(arrResult[6]);
                $("#txt_cotizacion_cli").val(arrResult[7]);    
                $("#select_cotizacion_moneda").val(arrResult[9]);
                $("#select_cotizacion_estado").val(arrResult[10]);
                $("#select_cotizacion_estado_cliente").val(arrResult[11]);
                $("#txt_cotizacion_contacto_nombre").val(arrResult[12]);
                $("#txt_cotizacion_contacto_email").val(arrResult[13]);
                $("#txt_cotizacion_contacto_fonos").val(arrResult[19]);
                $("#select_cotizacion_total_procesos").val(separadorDeMiles(arrResult[14]));
                $("#select_cotizacion_total_materiales").val(separadorDeMiles(arrResult[15]));
                $("#txt_cotizacion_total_neto").val(separadorDeMiles(arrResult[16]));
                $("#txt_cotizacion_iva").val(separadorDeMiles(arrResult[17]));
                $("#txt_cotizacion_total_bruto").val(separadorDeMiles(arrResult[18])); 
                
                $("#select_cotizacion_oc").val(arrResult[20]); 
                $("#txt_detalleUnicoCliente").val(arrResult[21]);
                $("#txt_detalleUnicoCliente_hidden").val(arrResult[21]);
            }
            
            if($("#txt_cotizacion_fecha").val()==""){
                $("#txt_cotizacion_fecha").val(fecha);
                $("#btn_cotizacion_aprobar").hide();
            }
            $("#SubDetalleIngreso").hide();
            //CargaSubDetModifica();
        }
    });
}

function filtraCotizacion(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var fechaEmision=$("#txt_cotizacion_fecha").val();
    var emitidaPor=$("#slt_filtroComercial_vendedor").val();
    
    var presupuestoValido=$("#select_cotizacion_presupuesto_valido").val();
    var plazoEntrega=$("#select_cotizacion_plazo_entrega").val();
    var condicionesPago=$("#select_cotizacion_condicion_pago").val();
    
    var rutCli=$("#txt_cotizacion_rutcli").val();
    var nombreCli=escape($("#txt_cotizacion_cli").val());
    
    var desde =$("#txt_filtroComercial_ingreso").val();
    var hasta =$("#txt_filtroComercial_final").val();
    
    var selectNroCotizacion = $("#slt_filtroComercial_nrocotizacion").val();
    var selectNroOT = $("#slt_filtroComercial_nrOT").val();
    
    
    var diaDesde=desde.substring(0, 2);
    var mesDesde=desde.substring(3, 5);
    var yearDesde=desde.substring(6, 10);
    
    var filtroDesde = yearDesde + "-" + mesDesde + "-" + diaDesde;
    
    var diaHasta=hasta.substring(0, 2);
    var mesHasta=hasta.substring(3, 5);
    var yearHasta=hasta.substring(6, 10);
    
    var filtroHasta = yearHasta + "-" + mesHasta + "-" + diaHasta;
    
    
    var sequence =getUrlParameter('secuencia');
    var accion=getUrlParameter('accion');
    
    var estado=$("#slt_filtroComercial_estado").val()== "" ? "X_X" : $("#slt_filtroComercial_estado").val();
    var datos = {
        opcion: "select_all",
        txt_cotizacion_numero: selectNroCotizacion,
        txt_cotizacion_fecha: "2016-04-04",
        txt_cotizacion_emitida_por: emitidaPor,
        txt_cotizacion_dias_habiles: 0,
        select_cotizacion_presupuesto_valido:0,
        select_cotizacion_plazo_entrega:0,
        select_cotizacion_condicion_pago:0,
        txt_cotizacion_rutcli:0,
        txt_cotizacion_moneda:0,
        txt_cotizacion_total:0,
        select_cotizacion_total_procesos:0,
        select_cotizacion_total_materiales:0,
        txt_cotizacion_total_neto:0,
        txt_cotizacion_iva:0,
        txt_cotizacion_total_bruto:0,
        txt_cotizacion_cli:0,
        sequencia: sequence,
        fecha_desde: filtroDesde,
        fecha_hasta: filtroHasta,
        txt_cotizacion_estado: estado,
        select_nro_ot: selectNroOT
    };
    $.ajax({
        url : 'ServletSPCotizacion', 
        data: $.param(datos),
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $('#tblActComercial').DataTable().fnDestroy(); 
            $("#tblActComercial").find("tbody").html(data);  
            var total = 0;
            var tabla = $('#tblActComercial').DataTable( {//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
                "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
                bFilter: false, bInfo: false,
                "bLengthChange": false,
                "bAutoWidth": false,
                "aoColumnDefs": [{ 'bSortable': false, 'aTargets': [1,2,3,4,5,6,7,8,9] }]
            });
            //Se recorre cada fila del resultado
            $.each(tabla.fnGetNodes(), function(i, row){
                $row = $(row);
                total += parseInt($row.find("td.columnaTotal").text().replace(/\./g, "").replace(/\$/g, "").trim());
            });
            $(".thTotal").html("$ "+total.toLocaleString('de-DE'));
            //$("#tblActComercial").find("tbody").html(data);  
        }
    });
}

function aprobarCotiza(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var fechaEmision2=$("#txt_cotizacion_fecha").val();
    var emitidaPor=$("#txt_cotizacion_emitida_por").val();
    var moneda=$("#select_cotizacion_moneda").val();
    var presupuestoValido=$("#select_cotizacion_presupuesto_valido").val();
    var plazoEntrega=$("#select_cotizacion_plazo_entrega").val();
    var condicionesPago=$("#select_cotizacion_condicion_pago").val();
    var total=$("#txt_cotizacion_total").val();
    var rutCli=$("#txt_cotizacion_rutcli").val();
    var nombreCli=escape($("#txt_cotizacion_cli").val());
    var diasHabiles=$("#txt_cotizacion_dias_habiles").val();
    
    var sequence =getUrlParameter('secuencia');
    var accion="modifica";
    
    var dia=fechaEmision2.substring(0, 2);
    var mes=fechaEmision2.substring(3, 5);
    var year=fechaEmision2.substring(6, 10);
    
    var fechaEmision = year + "-" + mes + "-" + dia;  
    
    if(accion=="modifica"){
        accion="update"
    }else{
        accion="insert";
    }
    
    if(confirm("Una vez aprobada la cotizacion no puede volver a modificarla")){
        $.ajax({
            url : 'ServletSPCotizacion', 
            data: "opcion="+accion+"&txt_cotizacion_numero="+numeroCotizacion+"&txt_cotizacion_fecha="+fechaEmision
                    +"&txt_cotizacion_emitida_por="+emitidaPor+"&txt_cotizacion_dias_habiles="+diasHabiles
                    +"&select_cotizacion_presupuesto_valido="+presupuestoValido+"&select_cotizacion_plazo_entrega="+plazoEntrega
                    +"&select_cotizacion_condicion_pago="+condicionesPago+"&txt_cotizacion_rutcli="+rutCli+"txt_cotizacion_moneda="+moneda
                    +"txt_cotizacion_total="+total
                    +"&txt_cotizacion_cli="+nombreCli+"&sequencia="+sequence+"&fecha_desde=&fecha_hasta"+"&estado="+"Aprobada",
            type : 'POST',
            dataType : "html",
            async:false,
            success : function(data) {
                $("#btn_cotazacioncial_grabar").hide();
                $("#btn_cotizacion_aprobar").hide();
                
                var creaOT=confirm("Desea crear las OT para esta cotizaci\u00F3n");
                
                if(creaOT){
                    GeneraOrdenTaller();
                    $(window).unbind('beforeunload');
                    location.href="svm_Seleccion_Cotizacion.jsp";
                    
                }else{
                    alert("Cotizacion Aprobada");
                    
                }
            }
        });
    }
}

function pad(n, width, z) {
  z = z || '0';
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

function filtraClientes(){
    var rut=$("#txt_filtro_cliente_rut").val();
    var nombre=$("#txt_filtro_cliente_nombre").val();
    $.ajax({
        url : 'ServletSPCliente', 
        data : "mod=select_all"+"&txt_cliente_codigo="+"&txt_cliente_rut="+rut+"&txt_cliente_nombre="+nombre
                       +"&txt_sigla_cliente="+"&txt_cliente_direccion="+"&txt_cliente_comuna="
                       +"&txt_cliente_ciudad="+"&txt_cliente_fono1="+"&txt_cliente_fono2="
                       +"&txt_cliente_fax="+"&txt_cliente_rubro="+"&txt_cliente_contacto="
                       +"&txt_cliente_casilla="+"&txt_cliente_vendedor="+"&txt_cliente_estado=",
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#select_cliente_filter").html(data);
        }
    });
}

//function ModificaActComercial(id)
//{   
//    $('#tblActComercial').children('tbody').children('tr').each(function(i,obj){
//        $(this).css("background-color","white");
//    });
//    desmarca_registro_ActComercial();
//    if($("#habilitaActCom").val() == 0)
//    {
//        $("#filaTablaActComercial"+id).css("background-color","#58FAF4").removeClass("alt");        
//        $("#habilitaActCom").val("1");
//        var neg =  $("#secuencia"+id).text();        
//        var caso = $("#ActCom_Caso"+id).text();
//        var estado=$("#estado"+id).text();
//        if($("#ActCom_estadoCierre"+id).text() == "DE")
//        {
//            $("#btn_actComercial_Modifica").hide();
//        }
//        
//        if(estado=="Aprobada"){
//            $("#btn_actComercial_Modifica").hide();
//        }else{
//            $("#btn_actComercial_Modifica").show();
//        }
//        
//        $("#corrCotiza").val(neg);
//        $("#caso").val(caso);
//    }
//}
function ModificaActComercial(id,ots,numero,estado)
{   
    $('#tblActComercial').children('tbody').children('tr').each(function(i,obj){
        $(this).css("background-color","white");
    });
    
    desmarca_registro_ActComercial();
    
    if($("#habilitaActCom").val() == 0)
    {
        $("#filaTablaActComercial"+id).css("background-color","#58FAF4").removeClass("alt");        
        $("#habilitaActCom").val("1");
        var neg =  $("#secuencia"+id).text();        
        var caso = $("#ActCom_Caso"+id).text();
        var estado=$("#estado"+id).text();
        if($("#ActCom_estadoCierre"+id).text() == "DE")
        {
            $("#btn_actComercial_Modifica").hide();
        }
        
        if(estado=="Aprobada"){
            $("#btn_actComercial_Modifica").hide();
        }else{
            $("#btn_actComercial_Modifica").show();
        }
                
        $("#nroCoti").val(numero);
        $("#estadoCoti").val(estado);
        $("#nroOts").val(ots);
        $("#corrCotiza").val(neg);
        $("#caso").val(caso);
        
    }
}
function desmarca_registro_ActComercial()
{    
    $("#corrOT").val("");
    var td = $('#tblActComercial').children('tbody').children('tr').length;           

    for(var i = 0; i<=td;i++)
    {                
        if(i % 2 === 0)
        {
            $("#filaTablaActComercial"+i).addClass("alt");
        }
        if(i % 2 != 0)
        {                    
            $("#filaTablaActComercial"+i).css("background-color","white");
        }
    }
    $("#btn_actComercial_Modifica").show();
    $("#habilitaActCom").val("0");
}

//function GeneraOrdenTaller()
//{
//    var numeroCotizacion=$("#txt_cotizacion_numero").val();
//    $.ajax({
//            url : 'ServletSPGeneraOT', 
//            data: "txt_cotizacion_numero="+numeroCotizacion,
//            type : 'POST',
//            dataType : "html",
//            async:false,
//            success : function(data) {
//
//            }
//        });
//}
function GeneraOrdenTaller(numero)
{
    var numeroCotizacion = numero;
    
//    alert("Cotizacion Nro: " + numeroCotizacion);
    
    $.ajax({
            url : 'ServletSPGeneraOT', 
            data: "txt_cotizacion_numero="+numeroCotizacion,
            type : 'POST',
            dataType : "html",
            async:false,
            success : function(data) {
                
            //    alert(data);    
                
                if ((data.toString() == "") || (data.toString() == null)){
                    FuncionErrores(255);                    
                    return false;
                }else{
                    alert("Ordenes de taller creadas exitosamente para la cotizacion Nro "+ numeroCotizacion);
                    location.href="svm_Seleccion_Cotizacion.jsp";
                    return;
                }
                
            }
        });
}
function mainLink2(param) {
    var selectedValue = param.options[param.selectedIndex].value;
    var rutCli = selectedValue.substring(0, selectedValue.length - 2);
    $.ajax({
	url : 'ServletSPCliente', 
	data : "mod=Consultar"+"&txt_cliente_rut="+rutCli+"&txt_cliente_nombre="
                       +"&txt_sigla_cliente=&txt_cliente_direccion=&txt_cliente_comuna="
                       +"&txt_cliente_ciudad=&txt_cliente_fono1=&txt_cliente_fono2="
                       +"&txt_cliente_fax=&txt_cliente_rubro=&txt_cliente_contacto="
                       +"&txt_cliente_casilla=&txt_cliente_vendedor=&txt_cliente_estado="
                       +"&txt_cliente_nombreContactoComercial=&txt_cliente_cargoContactoComercial=&txt_cliente_celularContactoComercial="
                       +"&txt_cliente_fijoContactoComercial=&txt_cliente_emailContactoComercial=&txt_cliente_nombreContactoContable="
                       +"&txt_cliente_cargoContactoContable=&txt_cliente_celularContactoContable=&txt_cliente_fijoContactoContable="
                       +"&txt_cliente_emailContactoContable=&txt_cotizacion_contacto_fonos=",
	type : 'POST',
	dataType : "html",
        async:false,
	success : function(data){
            $("#contenido_maeclientes").html(data);
	}
    });
}

function openDialog(msg){
    $('#dialog_msg').html("");
    $('#dialog_msg').html("<p>"+msg+"</p>");
    $('#dialog_msg').dialog("option", "position", "center");
    $('#dialog_msg').dialog( "open" );
}