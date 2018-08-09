/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function grabarOrdenTaller() {
    var numeroOrden = $("#txt_orden_numero").val();
    var numeroCotizacion = $("#txt_cotizacion_numero").val();
    var fechaEmision2 = $("#txt_orden_fecha").val();
    var fechaDespacho = $("#txt_orden_fecha_desp").val();
    var fechaTermino = $("#txt_orden_fecha_term").val();
    var detalle = $("#txt_detalle").val();
    var numeroFactura = $("#txt_factura_numero").val();
    var numeroGuia = $("#txt_guia_despacho").val();
    var especial = $("#select_especial").val();
    var aceptaCon = $("#txt_acepta_con").val();
    var rutCli = $("#txt_rutcli").val();
    var estado = $("#select_estado").val();
    var sequence = getUrlParameter('secuencia');
    var accion = getUrlParameter('accion');
    
    var dia=fechaEmision2.substring(0, 2);
    var mes=fechaEmision2.substring(3, 5);
    var year=fechaEmision2.substring(6, 10);
    
    var fechaEmision = year + "-" + mes + "-" + dia;
    
    if ($("#txt_rutcli").val() == "") {
        FuncionErrores(19);
        $("#txt_rutcli").focus();
        return false;
    }
    
    if ($("#select_especial").val() == -1) {
        FuncionErrores(24);
        $("#select_especial").focus();
        return false;
    }       

    if ($("#txt_cotizacion_numero").val() == "") {
        FuncionErrores(241);
        $("#txt_cotizacion_numero").focus();
        return false;
    } 
    
    if ($("#txt_detalle").val() == "") {
        FuncionErrores(242);
        $("#txt_detalle").focus();
        return false;
    } 
    if (accion == "modifica" || numeroOrden != "") {
        accion = "update";
    } else {
        accion = "insert";
    }
    
    $.ajax({
        url: 'ServletSPOrdenTaller',
        data: "opcion=" + accion + "&txt_orden_numero=" + numeroOrden + "&txt_ordentaller_fecha=" + fechaEmision
                + "&txt_cotizacion_numero=" + numeroCotizacion
                + "&txt_ordentaller_fechatermino=" + fechaTermino + "&txt_ordentaller_fechadespacho=" + fechaDespacho
                + "&txt_detalle=" + detalle + "&txt_factura_numero=" + numeroFactura + "&txt_guia_despacho=" + numeroGuia
                + "&txt_especial=" + especial + "&txt_acepta_con=" + aceptaCon + "&txt_rutcli=" + rutCli
                + "&select_estado=" + estado + "&sequencia=" + sequence + "&fecha_desde=&fecha_hasta"+"&estado=" + estado,
        type: 'POST',
        dataType: "html",
        success: function (data) {
            $("#txt_orden_numero").val(data);
            ingresaDetalleOT();
//            location.href="svm_Seleccion_OT.jsp";
        }
    });
}
function filtraOrdenTaller() {
    var numeroOrden      = $("#txt_filtro_numeroOt").val();
    var numeroCotizacion = $("#txt_filtro_numeroCot").val();    
    var desde            = $("#txt_filtroComercial_ingreso").val();
    var hasta            = $("#txt_filtroComercial_final").val();    
    var estado           = $("#slt_filtroComercial_estado").val();
    
    var diaDesde=desde.substring(0, 2);
    var mesDesde=desde.substring(3, 5);
    var yearDesde=desde.substring(6, 10);
    
    var filtroDesde = yearDesde + "-" + mesDesde + "-" + diaDesde;
    
    var diaHasta=hasta.substring(0, 2);
    var mesHasta=hasta.substring(3, 5);
    var yearHasta=hasta.substring(6, 10);
    
    var filtroHasta = yearHasta + "-" + mesHasta + "-" + diaHasta;        
    
    $.ajax({
        url: 'ServletSPOrdenTaller',
                data: "opcion=select_all" + "&txt_orden_numero=" + numeroOrden 
                + "&txt_cotizacion_numero=" + numeroCotizacion + "&txt_estado=" + estado
                + "&fecha_desde=" + filtroDesde + "&fecha_hasta="+ filtroHasta,
        type: 'POST',
        dataType: "html",
        async:false,
        success: function (data) {                        
            $('#tblOrdenTaller').dataTable().fnDestroy();           
            $("#tblOrdenTaller").find("tbody").html(data);
            var total = 0;
            var tabla = $('#tblOrdenTaller').DataTable({//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
                "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
                bFilter: false, bInfo: false,
                "bLengthChange": false,
                 "bAutoWidth": false,
                "aoColumnDefs": [{'bSortable': false, 'aTargets': [1, 2, 3, 4, 5, 6,7,8,9,10,11,12,13,14,15] }]
            });
            //Se recorre cada fila del resultado
            $.each(tabla.fnGetNodes(), function(i, row){
                $row = $(row);
                total += parseInt($row.find("td.columnaTotal").text().replace(/\./g, "").replace(/\$/g, "").trim());
            });
            $(".thTotal").html("$ "+total.toLocaleString('de-DE'));
        }
    });
}

function ModificaOrdenTaller(id,ot,estado)
{   
    desmarca_registro_ordentaller();
    
    if($("#habilitaActCom").val() == 0)
    {
        $("#filaTablaOrdenTaller"+id).css("background-color","#58FAF4").removeClass("alt");        
        $("#habilitaActCom").val("1");        
        $("#selectorOT").val(ot);
        $("#estadoOT").val(estado);                        
    }
}

function desmarca_registro_ordentaller()
{
    $("#corrOT").val("");
    var td = $('#tblOrdenTaller').children('tbody').children('tr').length;           

    for(var i = 0; i<=td;i++)
    {                
        if(i % 2 === 0)
        {
            $("#filaTablaOrdenTaller"+i).addClass("alt");
        }
        if(i % 2 != 0)
        {                    
            $("#filaTablaOrdenTaller"+i).css("background-color","white");
        }
    }
    $("#btn_actComercial_Modifica").show();
    $("#habilitaActCom").val("0");
}

function ModificaRebaja(id,rebaja,cant)
{   
    desmarca_registro_ordenHist();
    
    if($("#habilitaRebaja").val() == 0)
    {
        $("#filaTablaHistOT"+id).css("background-color","#58FAF4").removeClass("alt");        
        $("#habilitaRebaja").val("1");       
        $("#idRebaja").val(rebaja);
        $("#cantElim").val(cant);
    }
}

function desmarca_registro_ordenHist()
{
    $("#corrOT").val("");
    var td = $('#tbl_orden_hist').children('tbody').children('tr').length;           

    for(var i = 0; i<=td;i++)
    {                
        if(i % 2 === 0)
        {
            $("#filaTablaHistOT"+i).addClass("alt");
        }
        if(i % 2 != 0)
        {                    
            $("#filaTablaHistOT"+i).css("background-color","white");
        }
    }
//    $("#btn_actComercial_Modifica").show();
    $("#habilitaRebaja").val("0");
}

function cargaOrdenTaller(){
    var desde =$("#txt_filtroComercial_ingreso").val();
    var hasta =$("#txt_filtroComercial_final").val();
    
    var sequence =getUrlParameter('secuencia');
    var accion=getUrlParameter('accion');    
    var date = new Date();
    var dd=("0" + date.getDate()).slice(-2)
    var mm=("0" + (date.getMonth() + 1)).slice(-2);
    var yy=date.getFullYear();
    
    var fecha=dd+"-"+mm+"-"+yy;
    
    $.ajax({
        url : 'ServletSPOrdenTaller', 
        data: "opcion=select"+"&txt_orden_numero=0"+"&txt_ordentaller_fecha="+fecha
                +"&txt_cotizacion_numero=0"
                + "&txt_ordentaller_fechatermino=0" + "&txt_ordentaller_fechadespacho=0"
                + "&txt_detalle=" + "&txt_factura_numero=0" + "&txt_guia_despacho=0"
                + "&txt_especial=" + "&txt_acepta_con=" + "&txt_rutcli=0"
                + "&select_estado=0" + "&sequencia=" + sequence + "&fecha_desde=&fecha_hasta"+"&estado=",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            var arrResult=data.split("|");
            
            if(accion=="consulta"){
                $("#btn_ordentaller_grabar").hide();
                $("#btnClientes").hide();
                $("#lanzador").hide();
                $("#txt_orden_fecha").attr("readonly","readonly");
                $("#select_especial").prop("disabled",true);
                $("#txt_rutcli").attr("readonly","readonly");
                $("#txt_cliente").attr("readonly","readonly");
                $("#txt_acepta_con").attr("readonly","readonly");
                $("#txt_cotizacion_numero").attr("readonly","readonly");
                $("#txt_detalle").attr("readonly","readonly");
            }
                       
            if(accion=="modifica" || accion=="consulta") {
                var dia=arrResult[2].substring(8, 10);
                var mes=arrResult[2].substring(5, 7);
                var year=arrResult[2].substring(0, 4);
                var fechaOrden = dia + "-" + mes + "-" + year;                
                $("#txt_orden_fecha").val(fechaOrden);
            }            
            else {
                $("#txt_orden_fecha").val(arrResult[2]);
            }                       
                       
            $("#txt_orden_numero").val(arrResult[0]);
            $("#txt_cotizacion_numero").val(arrResult[1]);
            if (arrResult[11] == "1900-01-01") {
                $("#txt_orden_fecha_desp").val("");
            }
            else {
                $("#txt_orden_fecha_desp").val(arrResult[11]);
            }
            if (arrResult[10] == "1900-01-01") {
                $("#txt_orden_fecha_term").val("");
            }
            else {
                $("#txt_orden_fecha_term").val(arrResult[10]);
            }
            $("#txt_factura_numero").val(arrResult[7]);
            $("#txt_detalle").val(arrResult[9]);            
            $("#txt_guia_despacho").val(arrResult[8]);
            $("#select_especial").val(arrResult[12]);
            $("#txt_acepta_con").val(arrResult[13]);            
            $("#txt_rutcli").val(arrResult[5]);            
            $("#txt_cliente").val(arrResult[6]);                        
            
            if($("#txt_orden_fecha").val()==""){
                $("#txt_orden_fecha").val(fecha);
            }
            cargaDetalleOT();
        }
    });
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
        success : function(data) {
            $("#select_cliente_filter").html(data);
        }
    });
}

function informeOT(numero){        
    window.open("ServletPDFOT?opcion=select&txt_numero_ot="+numero);
    location.href = "svm_Seleccion_OT.jsp";    
}

function cargaProcesos() {
    var pieza = $("#txt_orden_pieza").val();
    var valorUF = $("#txt_orden_valorUF").val();
    var area = $("#txt_orden_super").val();
    var cantidad = $("#txt_orden_cantidad").val();
//    var estadoDetalle = $("#estadoDetalle").val();
    var accion=getUrlParameter('accion');          
    
    $.ajax({
        url : 'ServletSPProcesos', 
        data: "pieza="+pieza+"&valorUF="+valorUF+"&area="+area+"&cantidad="+cantidad,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#contenido_procesos").html(data);            
            $("#btn_AceptarProceso").hide();    
            
            
//                $("#cantidadModificado").val("Modificado");                        
//                $("#superModificado").val("Modificado");            
//            var superModificado = $("#superModificado").val();
//            var cantidadModificado = $("#cantidadModificado").val();
            cargaDatosProcesos("Modificado", "cantidadModificado");            
        }
    });
}

function cargaDatosProcesos(superModificado, cantidadModificado) {
    
    //var seq = $("#txt_orden_secuencia").val();
    var numeroOT = getUrlParameter('numeroOT');                                   
    
    
        $.ajax({
        url : 'ServletSPOrdenTallerHist', 
        data:{
          opcion: "select_proceso",
          txt_orden_numeroOT: numeroOT,
          txt_orden_CantRebaja: "",
          seq: "0",
          id: "0"
        },
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            var obj = JSON.parse(data);
            
            for(var i = 0; i <= obj.procesos.cantproc.length;i++){
                $("#txt_cotizacion_cant"+(i+1)).val(obj.procesos.cantproc[i]);
            }
            for(var i = 0; i <= obj.procesos.precioproc.length;i++){
                $("#txt_cotizacion_preciounitario"+(i+1)).val(obj.procesos.precioproc[i]);
            }
            for(var i = 0; i <= obj.procesos.totalproc.length;i++){
                $("#txt_cotizacion_total"+(i+1)).val(obj.procesos.totalproc[i]);
                if(obj.procesos.totalproc[i]!="0"){
                    Checkea(i+1);
                }
            }
            
            var arrResult=obj.materiales.split("|");
//            $("#txt_cotizacion_cant1").val(arrResult[1]);
//            $("#txt_cotizacion_preciounitario1").val(arrResult[2]);
//            $("#txt_cotizacion_total1").val(arrResult[3]);
//            
//            $("#txt_cotizacion_cant2").val(arrResult[5]);
//            $("#txt_cotizacion_preciounitario2").val(arrResult[6]);
//            $("#txt_cotizacion_total2").val(arrResult[7]);
//            
//            $("#txt_cotizacion_cant3").val(arrResult[9]);
//            $("#txt_cotizacion_preciounitario3").val(arrResult[10]);
//            $("#txt_cotizacion_total3").val(arrResult[11]);
//            
//            $("#txt_cotizacion_cant4").val(arrResult[13]);
//            $("#txt_cotizacion_preciounitario4").val(arrResult[14]);
//            $("#txt_cotizacion_total4").val(arrResult[15]);
//            
//            $("#txt_cotizacion_cant5").val(arrResult[17]);
//            $("#txt_cotizacion_preciounitario5").val(arrResult[18]);
//            $("#txt_cotizacion_total5").val(arrResult[19]);
//            
//            $("#txt_cotizacion_cant6").val(arrResult[21]);
//            $("#txt_cotizacion_preciounitario6").val(arrResult[22]);
//            $("#txt_cotizacion_total6").val(arrResult[23]);
//            
//            $("#txt_cotizacion_cant7").val(arrResult[25]);
//            $("#txt_cotizacion_preciounitario7").val(arrResult[26]);
//            $("#txt_cotizacion_total7").val(arrResult[27]);
//            
//            $("#txt_cotizacion_cant8").val(arrResult[29]);
//            $("#txt_cotizacion_preciounitario8").val(arrResult[30]);
//            $("#txt_cotizacion_total8").val(arrResult[31]);
//            
//            $("#txt_cotizacion_cant9").val(arrResult[33]);
//            $("#txt_cotizacion_preciounitario9").val(arrResult[34]);
//            $("#txt_cotizacion_total9").val(arrResult[35]);
//            
//            $("#txt_cotizacion_cant10").val(arrResult[37]);
//            $("#txt_cotizacion_preciounitario10").val(arrResult[38]);
//            $("#txt_cotizacion_total10").val(arrResult[39]);
//            
//            $("#txt_cotizacion_cant11").val(arrResult[41]);
//            $("#txt_cotizacion_preciounitario11").val(arrResult[42]);
//            $("#txt_cotizacion_total11").val(arrResult[43]);
//            
//            $("#txt_cotizacion_cant12").val(arrResult[45]);
//            $("#txt_cotizacion_preciounitario12").val(arrResult[46]);
//            $("#txt_cotizacion_total12").val(arrResult[47]);

//            if ($("#txt_cotizacion_total1").val() != "0") {
//                Checkea("1");
//            }
//            if ($("#txt_cotizacion_total2").val() != "0") {
//                Checkea("2");
//            }
//            if ($("#txt_cotizacion_total3").val() != "0") {
//                Checkea("3");
//            }
//            if ($("#txt_cotizacion_total4").val() != "0") {
//                Checkea("4");
//            }
//            if ($("#txt_cotizacion_total5").val() != "0") {
//                Checkea("5");
//            }
//            if ($("#txt_cotizacion_total6").val() != "0") {
//                Checkea("6");
//            }
//            if ($("#txt_cotizacion_total7").val() != "0") {
//                Checkea("7");
//            }
//            if ($("#txt_cotizacion_total8").val() != "0") {
//                Checkea("8");
//            }
//            if ($("#txt_cotizacion_total9").val() != "0") {
//                Checkea("9");
//            }
//            if ($("#txt_cotizacion_total10").val() != "0") {
//                Checkea("10");
//            }
//            if ($("#txt_cotizacion_total11").val() != "0") {
//                Checkea("11");
//            }
//            if ($("#txt_cotizacion_total12").val() != "0") {
//                Checkea("12");
//            }

            $("#txt_cotizacion_materiales_descripcion1").val(arrResult[48]);
            $("#txt_cotizacion_materiales_cantidad1").val(arrResult[49]);
            $("#txt_cotizacion_materiales_preciounitario1").val(arrResult[50]);
            $("#txt_cotizacion_materiales_totalitem1").val(arrResult[51]);
            $("#txt_cotizacion_materiales_descripcion2").val(arrResult[52]);
            $("#txt_cotizacion_materiales_cantidad2").val(arrResult[53]);
            $("#txt_cotizacion_materiales_preciounitario2").val(arrResult[54]);
            $("#txt_cotizacion_materiales_totalitem2").val(arrResult[55]);
            $("#txt_cotizacion_materiales_descripcion3").val(arrResult[56]);
            $("#txt_cotizacion_materiales_cantidad3").val(arrResult[57]);
            $("#txt_cotizacion_materiales_preciounitario3").val(arrResult[58]);
            $("#txt_cotizacion_materiales_totalitem3").val(arrResult[59]);
            $("#txt_cotizacion_materiales_descripcion4").val(arrResult[60]);
            $("#txt_cotizacion_materiales_cantidad4").val(arrResult[61]);
            $("#txt_cotizacion_materiales_preciounitario4").val(arrResult[62]);
            $("#txt_cotizacion_materiales_totalitem4").val(arrResult[63]);
            $("#txt_cotizacion_materiales_descripcion5").val(arrResult[64]);
            $("#txt_cotizacion_materiales_cantidad5").val(arrResult[65]);
            $("#txt_cotizacion_materiales_preciounitario5").val(arrResult[66]);
            $("#txt_cotizacion_materiales_totalitem5").val(arrResult[67]);
            
            $("#txt_cotizacion_totalProcesos").val(arrResult[68]);
            $("#txt_cotizacion_totalMateriales").val(arrResult[69]);
            $("#txt_cotizacion_totalGeneral").val(arrResult[70]);
            
            if (superModificado == "Modificado" && cantidadModificado == "") {
                $("#txt_cotizacion_cant1").val(area);
                $("#txt_cotizacion_cant2").val(area);
                $("#txt_cotizacion_cant3").val(area);
                $("#txt_cotizacion_cant4").val(area);
                $("#txt_cotizacion_cant5").val(area);
                $("#txt_cotizacion_cant6").val(area);
                $("#txt_cotizacion_cant10").val(area);
                if ($("#txt_cotizacion_total1").val() != "0") {
                    var total1 = areaPunto * $("#txt_cotizacion_cantidad1").val() * $("#txt_cotizacion_preciounitario1").val();
                    $("#txt_cotizacion_total1").val(separadorDeMiles(total1.toFixed(0)));
                    Checkea("1");
                    calculaTotalProcesos(1);
                }
                
                if ($("#txt_cotizacion_total2").val() != "0") {
                    var total2 = areaPunto * $("#txt_cotizacion_cantidad2").val() * $("#txt_cotizacion_preciounitario2").val().replace(".","");
                    $("#txt_cotizacion_total2").val(separadorDeMiles(total2.toFixed(0)));
                    Checkea("2");
                    calculaTotalProcesos(2);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total3 = areaPunto * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario3").val();
                    $("#txt_cotizacion_total3").val(separadorDeMiles(total3.toFixed(0)));
                    Checkea("3");
                    calculaTotalProcesos(3);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total4 = areaPunto * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario4").val();
                    $("#txt_cotizacion_total4").val(separadorDeMiles(total4.toFixed(0)));
                    Checkea("4");
                    calculaTotalProcesos(4);
                }                

                if ($("#txt_cotizacion_total5").val() != "0") {
                    var total5 = areaPunto * $("#txt_cotizacion_cantidad5").val() * $("#txt_cotizacion_preciounitario5").val();
                    $("#txt_cotizacion_total5").val(separadorDeMiles(total5.toFixed(0)));
                    Checkea("5");
                    calculaTotalProcesos(5);
                }                

                if ($("#txt_cotizacion_total6").val() != "0") {
                    var total6 = areaPunto * $("#txt_cotizacion_cantidad6").val() * $("#txt_cotizacion_preciounitario6").val();
                    $("#txt_cotizacion_total6").val(separadorDeMiles(total6.toFixed(0)));
                    Checkea("6");
                    calculaTotalProcesos(6);
                }     
                
                if ($("#txt_cotizacion_total7").val() != "0") {
                    var total7 = $("#txt_cotizacion_cant7").val() * $("#txt_cotizacion_cantidad7").val() * $("#txt_cotizacion_preciounitario7").val();
                    $("#txt_cotizacion_total7").val(separadorDeMiles(total7.toFixed(0)));
                    Checkea("7");
                    calculaTotalProcesos(7);
                }     

                if ($("#txt_cotizacion_total8").val() != "0") {
                    var total8 = $("#txt_cotizacion_cant8").val() * $("#txt_cotizacion_cantidad8").val() * $("#txt_cotizacion_preciounitario8").val();
                    $("#txt_cotizacion_total8").val(separadorDeMiles(total8.toFixed(0)));
                    Checkea("8");
                    calculaTotalProcesos(8);
                }     

                if ($("#txt_cotizacion_total9").val() != "0") {
                    var total9 = $("#txt_cotizacion_cant9").val() * $("#txt_cotizacion_cantidad9").val() * $("#txt_cotizacion_preciounitario9").val();
                    $("#txt_cotizacion_total9").val(separadorDeMiles(total9.toFixed(0)));
                    Checkea("9");
                    calculaTotalProcesos(9);
                }     
                
                if ($("#txt_cotizacion_total10").val() != "0") {
                    var total10 = areaPunto * $("#txt_cotizacion_cantidad10").val() * $("#txt_cotizacion_preciounitario10").val();
                    $("#txt_cotizacion_total10").val(separadorDeMiles(total10.toFixed(0)));
                    Checkea("10");
                    calculaTotalProcesos(10);
                }
                
                if ($("#txt_cotizacion_total11").val() != "0") {
                    var total11 = $("#txt_cotizacion_cant11").val() * $("#txt_cotizacion_cantidad11").val() * $("#txt_cotizacion_preciounitario11").val();
                    $("#txt_cotizacion_total11").val(separadorDeMiles(total11.toFixed(0)));
                    Checkea("11");
                    calculaTotalProcesos(11);
                }     
                
                if ($("#txt_cotizacion_total12").val() != "0") {
                    var total12 = $("#txt_cotizacion_cant12").val() * $("#txt_cotizacion_cantidad12").val() * $("#txt_cotizacion_preciounitario12").val();
                    $("#txt_cotizacion_total12").val(separadorDeMiles(total12.toFixed(0)));
                    Checkea("12");
                    calculaTotalProcesos(12);
                }     
                
                $("#superModificado").val("");
                $("#cantidadModificado").val("");
            }
            if (superModificado == "Modificado" && cantidadModificado == "Modificado") {
                $("#txt_cotizacion_cant1").val(area);
                $("#txt_cotizacion_cant2").val(area);
                $("#txt_cotizacion_cant3").val(area);
                $("#txt_cotizacion_cant4").val(area);
                $("#txt_cotizacion_cant5").val(area);
                $("#txt_cotizacion_cant6").val(area);
                $("#txt_cotizacion_cant10").val(area);
                $("#txt_cotizacion_cantidad1").val(cantidad);
                $("#txt_cotizacion_cantidad2").val(cantidad);
                $("#txt_cotizacion_cantidad3").val(cantidad);
                $("#txt_cotizacion_cantidad4").val(cantidad);
                $("#txt_cotizacion_cantidad5").val(cantidad);
                $("#txt_cotizacion_cantidad6").val(cantidad);
                $("#txt_cotizacion_cantidad7").val(cantidad);
                $("#txt_cotizacion_cantidad8").val(cantidad);
                $("#txt_cotizacion_cantidad9").val(cantidad);
                $("#txt_cotizacion_cantidad10").val(cantidad);
                $("#txt_cotizacion_cantidad11").val(cantidad);
                $("#txt_cotizacion_cantidad12").val(cantidad);
                if ($("#txt_cotizacion_total1").val() != "0") {
                    var total1 = areaPunto * $("#txt_cotizacion_cantidad1").val() * $("#txt_cotizacion_preciounitario1").val();
                    $("#txt_cotizacion_total1").val(separadorDeMiles(total1.toFixed(0)));
                    Checkea("1");
                    calculaTotalProcesos(1);
                }
                
                if ($("#txt_cotizacion_total2").val() != "0") {
                    var total2 = areaPunto * $("#txt_cotizacion_cantidad2").val() * $("#txt_cotizacion_preciounitario2").val().replace(".","");
                    $("#txt_cotizacion_total2").val(separadorDeMiles(total2.toFixed(0)));
                    Checkea("2");
                    calculaTotalProcesos(2);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total3 = areaPunto * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario3").val();
                    $("#txt_cotizacion_total3").val(separadorDeMiles(total3.toFixed(0)));
                    Checkea("3");
                    calculaTotalProcesos(3);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total4 = areaPunto * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario4").val();
                    $("#txt_cotizacion_total4").val(separadorDeMiles(total4.toFixed(0)));
                    Checkea("4");
                    calculaTotalProcesos(4);
                }                

                if ($("#txt_cotizacion_total5").val() != "0") {
                    var total5 = areaPunto * $("#txt_cotizacion_cantidad5").val() * $("#txt_cotizacion_preciounitario5").val();
                    $("#txt_cotizacion_total5").val(separadorDeMiles(total5.toFixed(0)));
                    Checkea("5");
                    calculaTotalProcesos(5);
                }                

                if ($("#txt_cotizacion_total6").val() != "0") {
                    var total6 = areaPunto * $("#txt_cotizacion_cantidad6").val() * $("#txt_cotizacion_preciounitario6").val();
                    $("#txt_cotizacion_total6").val(separadorDeMiles(total6.toFixed(0)));
                    Checkea("6");
                    calculaTotalProcesos(6);
                }     

                if ($("#txt_cotizacion_total7").val() != "0") {
                    var total7 = $("#txt_cotizacion_cant7").val() * $("#txt_cotizacion_cantidad7").val() * $("#txt_cotizacion_preciounitario7").val();
                    $("#txt_cotizacion_total7").val(separadorDeMiles(total7.toFixed(0)));
                    Checkea("7");
                    calculaTotalProcesos(7);
                }     

                if ($("#txt_cotizacion_total8").val() != "0") {
                    var total8 = $("#txt_cotizacion_cant8").val() * $("#txt_cotizacion_cantidad8").val() * $("#txt_cotizacion_preciounitario8").val();
                    $("#txt_cotizacion_total8").val(separadorDeMiles(total8.toFixed(0)));
                    Checkea("8");
                    calculaTotalProcesos(8);
                }     

                if ($("#txt_cotizacion_total9").val() != "0") {
                    var total9 = $("#txt_cotizacion_cant9").val() * $("#txt_cotizacion_cantidad9").val() * $("#txt_cotizacion_preciounitario9").val();
                    $("#txt_cotizacion_total9").val(separadorDeMiles(total9.toFixed(0)));
                    Checkea("9");
                    calculaTotalProcesos(9);
                }     
                
                if ($("#txt_cotizacion_total10").val() != "0") {
                    var total10 = areaPunto * $("#txt_cotizacion_cantidad10").val() * $("#txt_cotizacion_preciounitario10").val();
                    $("#txt_cotizacion_total10").val(separadorDeMiles(total10.toFixed(0)));
                    Checkea("10");
                    calculaTotalProcesos(10);
                }
                
                if ($("#txt_cotizacion_total11").val() != "0") {
                    var total11 = $("#txt_cotizacion_cant11").val() * $("#txt_cotizacion_cantidad11").val() * $("#txt_cotizacion_preciounitario11").val();
                    $("#txt_cotizacion_total11").val(separadorDeMiles(total11.toFixed(0)));
                    Checkea("11");
                    calculaTotalProcesos(11);
                }     
                
                if ($("#txt_cotizacion_total12").val() != "0") {
                    var total12 = $("#txt_cotizacion_cant12").val() * $("#txt_cotizacion_cantidad12").val() * $("#txt_cotizacion_preciounitario12").val();
                    $("#txt_cotizacion_total12").val(separadorDeMiles(total12.toFixed(0)));
                    Checkea("12");
                    calculaTotalProcesos(12);
                }     
                
                
                $("#superModificado").val("");
                $("#cantidadModificado").val("");
            }            
            if (superModificado == "" && cantidadModificado == "Modificado") {
                $("#txt_cotizacion_cantidad1").val(cantidad);
                $("#txt_cotizacion_cantidad2").val(cantidad);
                $("#txt_cotizacion_cantidad3").val(cantidad);
                $("#txt_cotizacion_cantidad4").val(cantidad);
                $("#txt_cotizacion_cantidad5").val(cantidad);
                $("#txt_cotizacion_cantidad6").val(cantidad);
                $("#txt_cotizacion_cantidad7").val(cantidad);
                $("#txt_cotizacion_cantidad8").val(cantidad);
                $("#txt_cotizacion_cantidad9").val(cantidad);
                $("#txt_cotizacion_cantidad10").val(cantidad);
                $("#txt_cotizacion_cantidad11").val(cantidad);
                $("#txt_cotizacion_cantidad12").val(cantidad);
                if ($("#txt_cotizacion_total1").val() != "0") {
                    var total1 = $("#txt_cotizacion_cant1").val() * $("#txt_cotizacion_cantidad1").val() * $("#txt_cotizacion_preciounitario1").val();
                    $("#txt_cotizacion_total1").val(separadorDeMiles(total1.toFixed(0)));
                    Checkea("1");
                    calculaTotalProcesos(1);
                }
                
                if ($("#txt_cotizacion_total2").val() != "0") {
                    var total2 = $("#txt_cotizacion_cant2").val() * $("#txt_cotizacion_cantidad2").val() * $("#txt_cotizacion_preciounitario2").val().replace(".","");
                    $("#txt_cotizacion_total2").val(separadorDeMiles(total2.toFixed(0)));
                    Checkea("2");
                    calculaTotalProcesos(2);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total3 = $("#txt_cotizacion_cant3").val() * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario3").val();
                    $("#txt_cotizacion_total3").val(separadorDeMiles(total3.toFixed(0)));
                    Checkea("3");
                    calculaTotalProcesos(3);
                }                
                
                if ($("#txt_cotizacion_total3").val() != "0") {
                    var total4 = $("#txt_cotizacion_cant4").val() * $("#txt_cotizacion_cantidad3").val() * $("#txt_cotizacion_preciounitario4").val();
                    $("#txt_cotizacion_total4").val(separadorDeMiles(total4.toFixed(0)));
                    Checkea("4");
                    calculaTotalProcesos(4);
                }                

                if ($("#txt_cotizacion_total5").val() != "0") {
                    var total5 = $("#txt_cotizacion_cant5").val() * $("#txt_cotizacion_cantidad5").val() * $("#txt_cotizacion_preciounitario5").val();
                    $("#txt_cotizacion_total5").val(separadorDeMiles(total5.toFixed(0)));
                    Checkea("5");
                    calculaTotalProcesos(5);
                }                

                if ($("#txt_cotizacion_total6").val() != "0") {
                    var total6 = $("#txt_cotizacion_cant6").val() * $("#txt_cotizacion_cantidad6").val() * $("#txt_cotizacion_preciounitario6").val();
                    $("#txt_cotizacion_total6").val(separadorDeMiles(total6.toFixed(0)));
                    Checkea("6");
                    calculaTotalProcesos(6);
                }     

                if ($("#txt_cotizacion_total7").val() != "0") {
                    var total7 = $("#txt_cotizacion_cant7").val() * $("#txt_cotizacion_cantidad7").val() * $("#txt_cotizacion_preciounitario7").val();
                    $("#txt_cotizacion_total7").val(separadorDeMiles(total7.toFixed(0)));
                    Checkea("7");
                    calculaTotalProcesos(7);
                }     

                if ($("#txt_cotizacion_total8").val() != "0") {
                    var total8 = $("#txt_cotizacion_cant8").val() * $("#txt_cotizacion_cantidad8").val() * $("#txt_cotizacion_preciounitario8").val();
                    $("#txt_cotizacion_total8").val(separadorDeMiles(total8.toFixed(0)));
                    Checkea("8");
                    calculaTotalProcesos(8);
                }     

                if ($("#txt_cotizacion_total9").val() != "0") {
                    var total9 = $("#txt_cotizacion_cant9").val() * $("#txt_cotizacion_cantidad9").val() * $("#txt_cotizacion_preciounitario9").val();
                    $("#txt_cotizacion_total9").val(separadorDeMiles(total9.toFixed(0)));
                    Checkea("9");
                    calculaTotalProcesos(9);
                }     
                
                if ($("#txt_cotizacion_total10").val() != "0") {
                    var total10 = $("#txt_cotizacion_cant10").val() * $("#txt_cotizacion_cantidad10").val() * $("#txt_cotizacion_preciounitario10").val();
                    $("#txt_cotizacion_total10").val(separadorDeMiles(total10.toFixed(0)));
                    Checkea("10");
                    calculaTotalProcesos(10);
                }
                
                if ($("#txt_cotizacion_total11").val() != "0") {
                    var total11 = $("#txt_cotizacion_cant11").val() * $("#txt_cotizacion_cantidad11").val() * $("#txt_cotizacion_preciounitario11").val();
                    $("#txt_cotizacion_total11").val(separadorDeMiles(total11.toFixed(0)));
                    Checkea("11");
                    calculaTotalProcesos(11);
                }     
                
                if ($("#txt_cotizacion_total12").val() != "0") {
                    var total12 = $("#txt_cotizacion_cant12").val() * $("#txt_cotizacion_cantidad12").val() * $("#txt_cotizacion_preciounitario12").val();
                    $("#txt_cotizacion_total12").val(separadorDeMiles(total12.toFixed(0)));
                    Checkea("12");
                    calculaTotalProcesos(12);
                }
                $("#superModificado").val("");
                $("#cantidadModificado").val("");
            }            
        }
        });
}
