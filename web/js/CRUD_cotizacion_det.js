function ingresaDetalle(){
    var pieza=$("#select_cotizacion_pieza").val();
    var cantidad=$("#txt_cotizacion_cantidad").val();
    var valorDM=$("#txt_cotizacion_dm").val();
    var valorDiametro=$("#txt_cotizacion_diametro").val();
    var largo=$("#txt_cotizacion_largo").val();
    var valUniCrom=$("#txt_cotizacion_valUniCrom").val();
    var totalCrom=$("#txt_cotizacion_totalCrom").val();
    var valUnitario=$("#txt_cotizacion_valUnitario").val();
    var totales=$("#txt_cotizacion_totalProcesos").val();
    var c_horas=$("#txt_cotizacion_cHora").val();
    var cantHoras=$("#txt_cotizacion_cantHrs").val();
    var totalHoras=$("#txt_cotizacion_totalhoras").val();   
    
    var procesos=$.trim($("#txt_cotizacion_procesos").val());   
    var diametroInterno=$.trim($("#txt_cotizacion_diametroint").val());   
    var diametroExterno=$.trim($("#txt_cotizacion_diametroext").val());   
    var trabajoAbreviado=$.trim($("#select_cotizacion_trabajo").val());   
    var ancho=$.trim($("#txt_cotizacion_ancho").val());   
    var area=$.trim($("#txt_cotizacion_area").val()); 
    var nuevousado=$.trim($("#select_cotizacion_nuevo_usado").val());
    var materialbase=$.trim($("#select_cotizacion_materialbase").val());
    var areatotal=$.trim($("#txt_cotizacion_area_total").val());
    var corriente=$.trim($("#txt_cotizacion_corriente").val());
    var comentarios=$.trim($("#txt_cotizacion_comentarios").val());   
    
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var cantidadProc = 0;
    var matDescripcion1=$.trim($("#txt_cotizacion_materiales_descripcion1").val());
    var matCantidad1=$.trim($("#txt_cotizacion_materiales_cantidad1").val());
    var matPreciounitario1=$.trim($("#txt_cotizacion_materiales_preciounitario1").val());
    var matTotalitem1=$.trim($("#txt_cotizacion_materiales_totalitem1").val());    
    var matDescripcion2=$.trim($("#txt_cotizacion_materiales_descripcion2").val());
    var matCantidad2=$.trim($("#txt_cotizacion_materiales_cantidad2").val());
    var matPreciounitario2=$.trim($("#txt_cotizacion_materiales_preciounitario2").val());
    var matTotalitem2=$.trim($("#txt_cotizacion_materiales_totalitem2").val());    
    var matDescripcion3=$.trim($("#txt_cotizacion_materiales_descripcion3").val());
    var matCantidad13=$.trim($("#txt_cotizacion_materiales_cantidad3").val());
    var matPreciounitario3=$.trim($("#txt_cotizacion_materiales_preciounitario3").val());
    var matTotalitem3=$.trim($("#txt_cotizacion_materiales_totalitem3").val());    
    var matDescripcion4=$.trim($("#txt_cotizacion_materiales_descripcion4").val());
    var matCantidad14=$.trim($("#txt_cotizacion_materiales_cantidad4").val());
    var matPreciounitario4=$.trim($("#txt_cotizacion_materiales_preciounitario4").val());
    var matTotalitem4=$.trim($("#txt_cotizacion_materiales_totalitem4").val());    
    var matDescripcion5=$.trim($("#txt_cotizacion_materiales_descripcion5").val());
    var matCantidad15=$.trim($("#txt_cotizacion_materiales_cantidad5").val());
    var matPreciounitario5=$.trim($("#txt_cotizacion_materiales_preciounitario5").val());
    var matTotalitem5=$.trim($("#txt_cotizacion_materiales_totalitem5").val());    
    
    var totalMateriales=$("#txt_cotizacion_totalMateriales").val();    
    var totalGeneralCot=$("#txt_cotizacion_totalGeneral").val().replace(/\./g, "");    
    
    var iva=0; 
    var totalBruto=0;
    
    largo=$('#txt_cotizacion_largo').autoNumeric('get');
    diametroInterno=$('#txt_cotizacion_diametroint').autoNumeric('get');
    iva=(totalGeneralCot*0.19).toFixed(0);
    totalBruto=parseInt(totalGeneralCot)+parseInt(iva);
    
    var codProc = [];
    var cantProc = [];
    var precioProc = [];
    var totalProc = [];
    
    $('input[name="chk_group[]"]:checked').each(function() {
        cantidadProc = cantidadProc + 1;
    });
    
    if (cantidadProc == 0 || $("#txt_cotizacion_totalGeneral").val() == "0") {
        alert("Debe seleccionar al menos un proceso");
        return;
    }
    
    $('input[name="chk_group[]"]').each(function() {
        codProc.push($(this).val());
        cantProc.push($("#txt_cotizacion_cant"+$(this).val()).val().replace(",","."));
        precioProc.push($("#txt_cotizacion_preciounitario"+$(this).val()).val());
        totalProc.push($("#txt_cotizacion_total"+$(this).val()).val());
    });
    
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: {
            opcion:"insert",
            select_cotizacion_pieza:pieza,
            txt_cotizacion_cantidad:cantidad,
            txt_cotizacion_dm:0,
            txt_cotizacion_diametro:0,
            txt_cotizacion_largo:largo,
            txt_cotizacion_valUniCrom:0,
            txt_cotizacion_totalCrom:0,
            txt_cotizacion_valUnitario:0,
            txt_cotizacion_totales:totales,
            txt_cotizacion_cHora:0,
            txt_cotizacion_cantHrs:0,
            txt_cotizacion_totalhoras:0,
            txt_cotizacion_procesos:null,
            txt_cotizacion_diametroInt:diametroInterno,
            txt_cotizacion_diametroExt:diametroExterno,
            txt_cotizacion_trabajoAbrev:trabajoAbreviado,
            txt_cotizacion_ancho:ancho,
            txt_cotizacion_area:area,
            txt_cotizacion_comentarios:comentarios,
            txt_cotizacion_nuevo_usado:nuevousado,
            txt_cotizacion_materialbase:materialbase,
            txt_cotizacion_area_total:areatotal,
            txt_cotizacion_corriente:corriente,
            txt_cotizacion_materiales_descripcion1:matDescripcion1,
            txt_cotizacion_materiales_cantidad1:matCantidad1,
            txt_cotizacion_materiales_preciounitario1:matPreciounitario1,
            txt_cotizacion_materiales_totalitem1:matTotalitem1,
            txt_cotizacion_materiales_descripcion2:matDescripcion2,
            txt_cotizacion_materiales_cantidad2:matCantidad2,
            txt_cotizacion_materiales_preciounitario2:matPreciounitario2,
            txt_cotizacion_materiales_totalitem2:matTotalitem2,
            txt_cotizacion_materiales_descripcion3:matDescripcion3,
            txt_cotizacion_materiales_cantidad3:matCantidad13,
            txt_cotizacion_materiales_preciounitario3:matPreciounitario3,
            txt_cotizacion_materiales_totalitem3:matTotalitem3,
            txt_cotizacion_materiales_descripcion4:matDescripcion4,
            txt_cotizacion_materiales_cantidad4:matCantidad14,
            txt_cotizacion_materiales_preciounitario4:matPreciounitario4,
            txt_cotizacion_materiales_totalitem4:matTotalitem4,
            txt_cotizacion_materiales_descripcion5:matDescripcion5,
            txt_cotizacion_materiales_cantidad5:matCantidad15,
            txt_cotizacion_materiales_preciounitario5:matPreciounitario5,
            txt_cotizacion_materiales_totalitem5:matTotalitem5,
            txt_cotizacion_totalMateriales:totalMateriales,
            txt_cotizacion_totalGeneral:totalGeneralCot,
            iva:iva,
            totalbruto:totalBruto,
            txt_cotizacion_numero:numeroCotizacion,
            arrayCodProc:JSON.stringify(codProc),
            arrayCantProc:JSON.stringify(cantProc),
            arrayPrecioProc:JSON.stringify(precioProc),
            arrayTotalProc:JSON.stringify(totalProc),
            sequencia:sequence,
            correlativo:null
        },
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            cargaDetallePaso();
            
        }
    });
}

function asociaDetalle(){
    var pieza=escape($("#select_cotizacion_pieza").val());
    var cantidad=escape($("#txt_cotizacion_cantidad").val());
    var valorDM=escape($("#txt_cotizacion_dm").val());
    var valorDiametro=escape($("#txt_cotizacion_diametroint").val());
    var largo=escape($("#txt_cotizacion_largo").val());
    var valUniCrom=escape($("#txt_cotizacion_valUniCrom").val());
    var totalCrom=escape($("#txt_cotizacion_totalCrom").val());
    var valUnitario=escape($("#txt_cotizacion_valUnitario").val());
    var totales=escape($("#txt_cotizacion_totales").val());    
    var c_horas=escape($("#txt_cotizacion_cHora").val());
    var cantHoras=escape($("#txt_cotizacion_cantHrs").val());
    var totalHoras=escape($("#txt_cotizacion_totalhoras").val());   
    
    var procesos=escape($("#txt_cotizacion_procesos").val());   
    var diametroInterno=escape($("#txt_cotizacion_diametroint").val());   
    var diametroExterno=escape($("#txt_cotizacion_diametroext").val());   
    var trabajoAbreviado=escape($("#select_cotizacion_trabajo").val());   
    var ancho=escape($("#txt_cotizacion_ancho").val());   
    var area=escape($("#txt_cotizacion_area").val());   
    var nuevousado=escape($("#select_cotizacion_nuevo_usado").val());
    var materialbase=escape($("#select_cotizacion_materialbase").val());
    var areatotal=escape($("#txt_cotizacion_area_total").val());
    var corriente=escape($("#txt_cotizacion_corriente").val());    
    var comentarios=escape($("#txt_cotizacion_comentarios").val()); 
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    
    
    
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=asocia_detalle"+"&select_cotizacion_pieza="+pieza+"&txt_cotizacion_cantidad="+0+
                "&txt_cotizacion_dm="+valorDM+"&txt_cotizacion_diametro="+0+
                "&txt_cotizacion_largo="+0+"&txt_cotizacion_valUniCrom="+0+
                "&txt_cotizacion_totalCrom="+0+"&txt_cotizacion_valUnitario="+0+
                "&txt_cotizacion_totales="+0+"&txt_cotizacion_cHora="+0+
                "&txt_cotizacion_cantHrs="+0+"&txt_cotizacion_totalhoras="+0+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt="+0+
                "&txt_cotizacion_diametroExt="+0+"&txt_cotizacion_trabajoAbrev="+0+
                "&txt_cotizacion_ancho="+0+"&txt_cotizacion_area="+0+
                "&txt_cotizacion_nuevo_usado="+0+"&txt_cotizacion_materialbase="+0+
                "&txt_cotizacion_area_total="+0+"&txt_cotizacion_corriente="+0+
                "&txt_cotizacion_materiales_descripcion1="+0+
                "&txt_cotizacion_materiales_cantidad1="+0+"&txt_cotizacion_materiales_preciounitario1="+0+
                "&txt_cotizacion_materiales_totalitem1="+0+"&txt_cotizacion_materiales_descripcion2="+0+                
                "&txt_cotizacion_materiales_cantidad2="+0+"&txt_cotizacion_materiales_preciounitario2="+0+                                
                "&txt_cotizacion_materiales_totalitem2="+0+"&txt_cotizacion_materiales_descripcion3="+0+
                "&txt_cotizacion_materiales_cantidad3="+0+"&txt_cotizacion_materiales_preciounitario3="+0+
                "&txt_cotizacion_materiales_totalitem3="+0+"&txt_cotizacion_materiales_descripcion4="+0+
                "&txt_cotizacion_materiales_cantidad4="+0+"&txt_cotizacion_materiales_preciounitario4="+0+
                "&txt_cotizacion_materiales_totalitem4="+0+"&txt_cotizacion_materiales_descripcion5="+0+
                "&txt_cotizacion_materiales_cantidad5="+0+"&txt_cotizacion_materiales_preciounitario5="+0+
                "&txt_cotizacion_materiales_totalitem5="+0+"&txt_cotizacion_totalMateriales="+0+
                "&txt_cotizacion_totalGeneral="+0+"&iva="+0+"&totalbruto="+0+
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+comentarios+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo=",
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
           
        }
    });
    
    /*$.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=asocia_detalle"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo=",
        type : 'POST',
        dataType : "html",
        success : function(data) {
          
        }
    });*/
}

function guardaDetalle(){
    var pieza=$("#select_cotizacion_pieza").val();
    var cantidad=$("#txt_cotizacion_cantidad").val();
    var valorDM=$("#txt_cotizacion_dm").val();
    var valorDiametro=$("#txt_cotizacion_diametroint").val();
    var largo=$("#txt_cotizacion_largo").val();
    var valUniCrom=$("#txt_cotizacion_valUniCrom").val();
    var totalCrom=$("#txt_cotizacion_totalCrom").val();
    var valUnitario=$("#txt_cotizacion_valUnitario").val();
    var totales=$("#txt_cotizacion_totales").val();    
    var c_horas=$("#txt_cotizacion_cHora").val();
    var cantHoras=$("#txt_cotizacion_cantHrs").val();
    var totalHoras=$("#txt_cotizacion_totalhoras").val();    
    
    var procesos=$("#txt_cotizacion_procesos").val();   
    var diametroInterno=$("#txt_cotizacion_diametroint").val();   
    var diametroExterno=$("#txt_cotizacion_diametroext").val();   
    var trabajoAbreviado=$("#select_cotizacion_trabajo").val();   
    var ancho=$("#txt_cotizacion_ancho").val();   
    var area=$("#txt_cotizacion_area").val();   
    var comentarios=$("#txt_cotizacion_comentarios").val();     
    
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=guarda_detalle"+"&select_cotizacion_pieza="+pieza+"&txt_cotizacion_cantidad="+0+
                "&txt_cotizacion_dm="+valorDM+"&txt_cotizacion_diametro="+0+
                "&txt_cotizacion_largo="+0+"&txt_cotizacion_valUniCrom="+0+
                "&txt_cotizacion_totalCrom="+0+"&txt_cotizacion_valUnitario="+0+
                "&txt_cotizacion_totales="+0+"&txt_cotizacion_cHora="+0+
                "&txt_cotizacion_cantHrs="+0+"&txt_cotizacion_totalhoras="+0+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt="+0+
                "&txt_cotizacion_diametroExt="+0+"&txt_cotizacion_trabajoAbrev="+0+
                "&txt_cotizacion_ancho="+0+"&txt_cotizacion_area="+0+"&txt_cotizacion_nuevo_usado="+0+
                "&txt_cotizacion_materialbase="+0+"&txt_cotizacion_area_total="+0+
                "&txt_cotizacion_corriente="+0+
                "&txt_cotizacion_materiales_descripcion1="+0+
                "&txt_cotizacion_materiales_cantidad1="+0+"&txt_cotizacion_materiales_preciounitario1="+0+
                "&txt_cotizacion_materiales_totalitem1="+0+"&txt_cotizacion_materiales_descripcion2="+0+                
                "&txt_cotizacion_materiales_cantidad2="+0+"&txt_cotizacion_materiales_preciounitario2="+0+                                
                "&txt_cotizacion_materiales_totalitem2="+0+"&txt_cotizacion_materiales_descripcion3="+0+
                "&txt_cotizacion_materiales_cantidad3="+0+"&txt_cotizacion_materiales_preciounitario3="+0+
                "&txt_cotizacion_materiales_totalitem3="+0+"&txt_cotizacion_materiales_descripcion4="+0+
                "&txt_cotizacion_materiales_cantidad4="+0+"&txt_cotizacion_materiales_preciounitario4="+0+
                "&txt_cotizacion_materiales_totalitem4="+0+"&txt_cotizacion_materiales_descripcion5="+0+
                "&txt_cotizacion_materiales_cantidad5="+0+"&txt_cotizacion_materiales_preciounitario5="+0+
                "&txt_cotizacion_materiales_totalitem5="+0+"&txt_cotizacion_totalMateriales="+0+
                "&txt_cotizacion_totalGeneral="+0+"&iva="+0+"&totalbruto="+0+           
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+comentarios+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo=",
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
           
        }
    });
    
    /*$.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=guarda_detalle"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo=",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            alert("Registro Guardado");
        }
    });*/
}


function cargaDetalle(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();

    var sequence =getUrlParameter('secuencia');
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=select"+"&select_cotizacion_pieza=0"+"&txt_cotizacion_cantidad=0"+
                "&txt_cotizacion_dm=0"+"&txt_cotizacion_diametro=0"+
                "&txt_cotizacion_largo=0"+"&txt_cotizacion_valUniCrom=0"+
                "&txt_cotizacion_totalCrom=0"+"&txt_cotizacion_valUnitario=0"+
                "&txt_cotizacion_totales=0"+"&txt_cotizacion_cHora=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt=0"+
                "&txt_cotizacion_diametroExt=0"+"&txt_cotizacion_trabajoAbrev=0"+
                "&txt_cotizacion_ancho=0"+"&txt_cotizacion_area=0"+
                "&txt_cotizacion_nuevo_usado="+"&txt_cotizacion_materialbase="+
                "&txt_cotizacion_area_total"+"&txt_cotizacion_corriente"+
                "&txt_cotizacion_materiales_descripcion1="+
                "&txt_cotizacion_materiales_cantidad1=0"+"&txt_cotizacion_materiales_preciounitario1=0"+
                "&txt_cotizacion_materiales_totalitem1=0"+"&txt_cotizacion_materiales_descripcion2="+                
                "&txt_cotizacion_materiales_cantidad2=0"+"&txt_cotizacion_materiales_preciounitario2=0"+                                
                "&txt_cotizacion_materiales_totalitem2=0"+"&txt_cotizacion_materiales_descripcion3="+
                "&txt_cotizacion_materiales_cantidad3=0"+"&txt_cotizacion_materiales_preciounitario3=0"+
                "&txt_cotizacion_materiales_totalitem3=0"+"&txt_cotizacion_materiales_descripcion4="+
                "&txt_cotizacion_materiales_cantidad4=0"+"&txt_cotizacion_materiales_preciounitario4=0"+
                "&txt_cotizacion_materiales_totalitem4=0"+"&txt_cotizacion_materiales_descripcion5="+
                "&txt_cotizacion_materiales_cantidad5=0"+"&txt_cotizacion_materiales_preciounitario5=0"+
                "&txt_cotizacion_materiales_totalitem5=0"+"&txt_cotizacion_totalMateriales=0"+
                "&txt_cotizacion_totalGeneral=0"+"&iva=0"+"&totalbruto=0"+
                "&txt_cotizacion_comentarios="+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo=",
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#tablaDetalle").find("tbody").html(data);
            $("#txt_cotizacion_cantidad").val("");
            //$("#txt_cotizacion_dm").val("");
            //$("#txt_cotizacion_diametro").val("");
            $("#txt_cotizacion_diametroint").val("");
            //$("#txt_cotizacion_diametroext").val("");
            $("#txt_cotizacion_largo").val("");
            //$("#txt_cotizacion_valUniCrom").val("");
            //$("#txt_cotizacion_cHora").val("");
            //$("#txt_cotizacion_cantHrs").val("");
            $("#select_cotizacion_pieza").val("--Seleccione--");
            $("#txt_cotizacion_procesos").val("");
            $("#select_cotizacion_trabajo").val("--Seleccione--");  
            $("#txt_cotizacion_ancho").val("");   
            $("#txt_cotizacion_area").val("");   
            $("#txt_cotizacion_comentarios").val("");            
            $("#select_cotizacion_nuevo_usado").val("--Seleccione--");  
            $("#select_cotizacion_materialbase").val("--Seleccione--");              
            $("#txt_cotizacion_area_total").val("");   
            $("#txt_cotizacion_corriente").val("");  
            }
    });
}

function cargaDetallePaso(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var accion=getUrlParameter('accion');
    
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=select_paso"+"&select_cotizacion_pieza=0"+"&txt_cotizacion_cantidad=0"+
                "&txt_cotizacion_dm=0"+"&txt_cotizacion_diametroint=0"+
                "&txt_cotizacion_largo=0"+"&txt_cotizacion_valUniCrom=0"+
                "&txt_cotizacion_totalCrom=0"+"&txt_cotizacion_valUnitario=0"+
                "&txt_cotizacion_totales=0"+"&txt_cotizacion_cHora=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt=0"+
                "&txt_cotizacion_diametroExt=0"+"&txt_cotizacion_trabajoAbrev=0"+
                "&txt_cotizacion_ancho=0"+"&txt_cotizacion_area=0"+
                "&txt_cotizacion_nuevo_usado="+"&txt_cotizacion_materialbase="+
                "&txt_cotizacion_area_total=0"+"&txt_cotizacion_corriente=0"+
                "&txt_cotizacion_materiales_descripcion1="+
                "&txt_cotizacion_materiales_cantidad1=0"+"&txt_cotizacion_materiales_preciounitario1=0"+
                "&txt_cotizacion_materiales_totalitem1=0"+"&txt_cotizacion_materiales_descripcion2="+                
                "&txt_cotizacion_materiales_cantidad2=0"+"&txt_cotizacion_materiales_preciounitario2=0"+                                
                "&txt_cotizacion_materiales_totalitem2=0"+"&txt_cotizacion_materiales_descripcion3="+
                "&txt_cotizacion_materiales_cantidad3=0"+"&txt_cotizacion_materiales_preciounitario3=0"+
                "&txt_cotizacion_materiales_totalitem3=0"+"&txt_cotizacion_materiales_descripcion4="+
                "&txt_cotizacion_materiales_cantidad4=0"+"&txt_cotizacion_materiales_preciounitario4=0"+
                "&txt_cotizacion_materiales_totalitem4=0"+"&txt_cotizacion_materiales_descripcion5="+
                "&txt_cotizacion_materiales_cantidad5=0"+"&txt_cotizacion_materiales_preciounitario5=0"+
                "&txt_cotizacion_materiales_totalitem5=0"+"&txt_cotizacion_totalMateriales=0"+
                "&txt_cotizacion_totalGeneral=0"+"&iva=0"+"&totalbruto=0"+              
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo=&accion="+accion,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#tablaDetalle").find("tbody").html(data);
            calculaTotalCoti();
            $("#txt_cotizacion_cantidad").val("");
            //$("#txt_cotizacion_dm").val("");
            //$("#txt_cotizacion_diametro").val("");
            $("#txt_cotizacion_diametroint").val("");
            //$("#txt_cotizacion_diametroext").val("");
            $("#txt_cotizacion_largo").val("");
            //$("#txt_cotizacion_valUniCrom").val("");
            //$("#txt_cotizacion_cHora").val("");
            //$("#txt_cotizacion_cantHrs").val("");
            $("#select_cotizacion_pieza").val("--Seleccione--");
            $("#txt_cotizacion_procesos").val("");
            $("#select_cotizacion_trabajo").val("--Seleccione--");  
            $("#txt_cotizacion_ancho").val("");   
            $("#txt_cotizacion_area").val("");   
            $("#txt_cotizacion_comentarios").val("");  
            $("#select_cotizacion_nuevo_usado").val("--Seleccione--");  
            $("#select_cotizacion_materialbase").val("--Seleccione--");              
            $("#txt_cotizacion_area_total").val("");   
            $("#txt_cotizacion_corriente").val("");             
            $("#btn_AceptarProceso").hide();
        }
    });
}

function ModificaDetalleComercial(id) 
{
    CancelaDetalle();
    if($("#habilitaDetCom").val() == 0)
    {
        $("#filaTablaDetalle"+id).css("background-color","#58FAF4").removeClass("alt");
        $("#select_cotizacion_pieza").val($("#cotazacionCodPieza"+id).text());
        $("#txt_cotizacion_cantidad").val($("#cotazacionDet_cantidad"+id).text());
        $("#txt_cotizacion_cantidadnuevo").val($("#cotazacionDet_cantidad"+id).text());
        $("#txt_cotizacion_largo").val($("#cotazacionDet_largo"+id).text());
        $("#txt_cotizacion_diametroint").val($("#cotazacionDet_diametroInterno"+id).text());   
        $("#select_cotizacion_trabajo").val($("#cotazacionDet_trabajoAbreviado"+id).text());   
        $("#txt_cotizacion_ancho").val($("#cotazacionDet_ancho"+id).text());   
        $("#txt_cotizacion_area").val($("#cotazacionDet_area"+id).text());   
        $("#txt_cotizacion_superficie").val($("#cotazacionDet_area"+id).text());
        $("#txt_cotizacion_area_total").val($("#cotazacionDet_areatotal"+id).text());   
        $("#txt_cotizacion_corriente").val($("#cotazacionCorriente"+id).text());       
        $("#txt_cotizacion_comentarios").val($("#cotazacionDet_comentarios"+id).text());   
        $("#txt_cotizacion_procesos").val($("#cotazacionDet_procesos"+id).text()); 
        
        $("#select_cotizacion_nuevo_usado").val($("#cotazacionDet_nuevousado"+id).text());
        $("#select_cotizacion_materialbase").val($("#cotazacionDetmaterialbase"+id).text());        
        $("#txt_cotizacion_cantidad").prop("disabled",false);
        $("#txt_cotizacion_diametroint").prop("disabled",false);
        $("#txt_cotizacion_largo").prop("disabled",false);
        $("#txt_cotizacion_ancho").prop("disabled",false);
        $("#txt_cotizacion_comentarios").prop("disabled",false);
        $("#select_cotizacion_pieza").prop("disabled",false);
        $("#select_cotizacion_nuevo_usado").prop("disabled",false);
        $("#select_cotizacion_materialbase").prop("disabled",false);
        $("#select_cotizacion_trabajo").prop("disabled",false);
        
        var accion=getUrlParameter('accion');
        
        if(accion=="consulta"){
            $("#DetalleIngreso").hide(); 
            $("#txt_correlativo").val($("#cotazacionDet_correlativo"+id).text());
            $("#txt_correlativo2").val($("#cotazacionDet_correlativo"+id).text());            
            $("#hidtemp").val(id);
            return;
        }
        $("#SubDetalleIngreso").show();
        $("#SubDetalleModifica").hide();
        $("#SubDetalleElimina").hide();        
        $("#SubDetalleCancela").hide(); 
        $("#DetalleModifica").show();
        $("#CancelaModifica").show();
        $("#DetalleElimina").show();        
        $("#DetalleIngreso").hide();
        $("#habilitaDetCom").val("1");
        $("#txt_correlativo").val($("#cotazacionDet_correlativo"+id).text());
        $("#txt_correlativo2").val($("#cotazacionDet_correlativo"+id).text());
        $("#txt_cotizacion_valor").val("");
        $("#txt_cotizacion_margen").val("");
        $("#hidtemp").val(id);
        $("#estadoDetalle").val("Modifica");
        var texto = "<td colspan = \"10\" id=\"margen\" bgcolor=\"#2798D9\" style=\"color: white; font-size: 18px\" align=\"center\"><b>DESCRIPCI&Oacute;N PIEZAS (MODIFICANDO)</b></td>";
        $("#Titulo_descp_piezas").html(texto);
            

    }
}

function CancelaDetalle()
{
    var td = $('#tblDetalleComer').children('tbody').children('tr').length;           
    for(var i = 0; i<=td;i++){                
        if(i % 2 === 0){
            $("#filaTablaDetalle"+i).addClass("alt");
        }else {                    
            $("#filaTablaDetalle"+i).css("background-color","white");
        }
    }
    
    $("#txt_cotizacion_cantidad").prop("disabled",true);
    $("#txt_cotizacion_diametroint").prop("disabled",true);
    $("#txt_cotizacion_largo").prop("disabled",true);
    $("#txt_cotizacion_ancho").prop("disabled",true);
    $("#txt_cotizacion_comentarios").prop("disabled",true);
    $("#select_cotizacion_pieza").prop("disabled",true);
    $("#select_cotizacion_nuevo_usado").prop("disabled",true);
    $("#select_cotizacion_materialbase").prop("disabled",true);
    $("#select_cotizacion_trabajo").prop("disabled",true);
    $("#SubDetalleIngreso").hide();
    $("#DetalleIngreso").show();
    $("#DetalleModifica").hide();
    $("#DetalleElimina").hide();    
    $("#CancelaModifica").hide(); 
    $("#habilitaDetCom").val("0");
    $("#txt_cotizacion_cantidad").val("");
    //$("#txt_cotizacion_dm").val("");
    $("#txt_cotizacion_diametroint").val("");
    $("#txt_cotizacion_diametroext").val("");
    $("#txt_cotizacion_largo").val("");
    //$("#txt_cotizacion_valUniCrom").val("");
    //$("#txt_cotizacion_cHora").val("");
    //$("#txt_cotizacion_cantHrs").val("");
    $("#select_cotizacion_pieza").val("--Seleccione--");    
    $("#txt_cotizacion_procesos").val("");
    $("#select_cotizacion_trabajo").val("--Seleccione--");
    $("#txt_cotizacion_ancho").val("");
    $("#txt_cotizacion_comentarios").val("");
    $("#select_cotizacion_nuevo_usado").val("--Seleccione--");  
    $("#select_cotizacion_materialbase").val("--Seleccione--");      
    $("#txt_cotizacion_area").val("");
    $("#txt_cotizacion_area_total").val("");   
    $("#txt_cotizacion_corriente").val(""); 
    $("#estadoDetalle").val("");

    var texto = "<td colspan = \"10\" id=\"margen\" bgcolor=\"#2798D9\" style=\"color: white; font-size: 18px\" align=\"center\"><b>DESCRIPCI&Oacute;N PIEZAS</b></td>";
    $("#Titulo_descp_piezas").html(texto);

}

function modificaDetalle(){
    var pieza=$("#select_cotizacion_pieza").val();
    var cantidad=$("#txt_cotizacion_cantidad").val();
    var valorDM=$("#txt_cotizacion_dm").val();
    var valorDiametro=$("#txt_cotizacion_diametro").val();
    
    var valUniCrom=$("#txt_cotizacion_valUniCrom").val();
    var totalCrom=$("#txt_cotizacion_totalCrom").val();
    var valUnitario=$("#txt_cotizacion_valUnitario").val();
    var totales=$("#txt_cotizacion_totalProcesos").val();    
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var c_horas=$("#txt_cotizacion_cHora").val();
    var cantHoras=$("#txt_cotizacion_cantHrs").val();   
    
    var procesos=$("#txt_cotizacion_procesos").val();   
    
    
    var trabajoAbreviado=$("#select_cotizacion_trabajo").val();   
    
    var nuevousado=$("#select_cotizacion_nuevo_usado").val();
    var materialbase=$("#select_cotizacion_materialbase").val();
    var areatotal=$("#txt_cotizacion_area_total").val();
    var corriente=$("#txt_cotizacion_corriente").val();
    var comentarios=$.trim($.trim($("#txt_cotizacion_comentarios").val()));
    
    var sequence =getUrlParameter('secuencia');
    var correlativo=$("#txt_correlativo").val();
    var cantidadProc = 0;
    
    var matDescripcion1=$.trim($("#txt_cotizacion_materiales_descripcion1").val());
    var matCantidad1=$("#txt_cotizacion_materiales_cantidad1").val();
    var matPreciounitario1=$("#txt_cotizacion_materiales_preciounitario1").val();
    var matTotalitem1=$("#txt_cotizacion_materiales_totalitem1").val();    
    var matDescripcion2=$.trim($("#txt_cotizacion_materiales_descripcion2").val());
    var matCantidad2=$("#txt_cotizacion_materiales_cantidad2").val();
    var matPreciounitario2=$("#txt_cotizacion_materiales_preciounitario2").val();
    var matTotalitem2=$("#txt_cotizacion_materiales_totalitem2").val();    
    var matDescripcion3=$.trim($("#txt_cotizacion_materiales_descripcion3").val());
    var matCantidad13=$("#txt_cotizacion_materiales_cantidad3").val();
    var matPreciounitario3=$("#txt_cotizacion_materiales_preciounitario3").val();
    var matTotalitem3=$("#txt_cotizacion_materiales_totalitem3").val();    
    var matDescripcion4=$.trim($("#txt_cotizacion_materiales_descripcion4").val());
    var matCantidad14=$("#txt_cotizacion_materiales_cantidad4").val();
    var matPreciounitario4=$("#txt_cotizacion_materiales_preciounitario4").val();
    var matTotalitem4=$("#txt_cotizacion_materiales_totalitem4").val();    
    var matDescripcion5=$.trim($("#txt_cotizacion_materiales_descripcion5").val());
    var matCantidad15=$("#txt_cotizacion_materiales_cantidad5").val();
    var matPreciounitario5=$("#txt_cotizacion_materiales_preciounitario5").val();
    var matTotalitem5=$("#txt_cotizacion_materiales_totalitem5").val();    
    
    var totalMateriales=$("#txt_cotizacion_totalMateriales").val();    
    //var totalGeneralCot=$("#txt_cotizacion_totalGeneral").val();  
    var totalGeneralCot=$("#contenido_procesos").find("#txt_cotizacion_totalGeneral").val();  
    totalGeneralCot=totalGeneralCot.replace(/\./g, "");
    var iva=0; 
    var totalBruto=0;
    
    //largo=$('#txt_cotizacion_largo').autoNumeric('get');
    //largo=$("#txt_cotizacion_largo").val().replace(/\./g, "");
    var largo=$("#txt_cotizacion_largo").val();
    var ancho=$("#txt_cotizacion_ancho").val();   
    var area=$("#txt_cotizacion_area").val();   
    var diametroInterno=$("#txt_cotizacion_diametroint").val();   
    var diametroExterno=$("#txt_cotizacion_diametroext").val();   
    //diametroInterno=$('#txt_cotizacion_diametroint').autoNumeric('get');
    //diametroInterno=$('#txt_cotizacion_diametroint').val().replace(/\./g, "");
    
    iva=(totalGeneralCot*0.19).toFixed(0);
    totalBruto=parseInt(totalGeneralCot)+parseInt(iva);
    
    var codProc = [];
    var cantProc = [];
    var precioProc = [];
    var totalProc = [];
    
    $('input[name="chk_group[]"]:checked').each(function() {
        cantidadProc = cantidadProc + 1;
    });
    
    if (cantidadProc == 0 || $("#txt_cotizacion_totalGeneral").val() == "0") {
        alert("Debe seleccionar al menos un proceso");
        return;
    }
    
    $('input[name="chk_group[]"]').each(function() {
        codProc.push($(this).val());
        cantProc.push($("#txt_cotizacion_cant"+$(this).val()).val().replace(",","."));
        precioProc.push($("#txt_cotizacion_preciounitario"+$(this).val()).val());
        totalProc.push($("#txt_cotizacion_total"+$(this).val()).val());
    });

    $("#estadoDetalle").val("");
/*    if ($("#select_cotizacion_pieza").val() == "") {
        FuncionErrores(28);
        $("#select_cotizacion_pieza").focus();
        return false;
    }

    if ($("#txt_cotizacion_cantidad").val() == "") {
        FuncionErrores(29);
        $("#txt_cotizacion_cantidad").focus();
        return false;
    }    

    if ($("#txt_cotizacion_dm").val() == "") {
        FuncionErrores(30);
        $("#txt_cotizacion_dm").focus();
        return false;
    }    

    if ($("#txt_cotizacion_diametro").val() == "") {
        FuncionErrores(31);
        $("#txt_cotizacion_diametro").focus();
        return false;
    }    

    if ($("#txt_cotizacion_largo").val() == "") {
        FuncionErrores(32);
        $("#txt_cotizacion_largo").focus();
        return false;
    }    
    
    
    if ($("#txt_cotizacion_valUniCrom").val() == "") {
        FuncionErrores(33);
        $("#txt_cotizacion_valUniCrom").focus();
        return false;
    }
    
    if ($("#txt_cotizacion_totalCrom").val() == "") {
        FuncionErrores(34);
        $("#txt_cotizacion_totalCrom").focus();
        return false;
    }    */
    $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: {
            opcion:"update",
            select_cotizacion_pieza:pieza,
            txt_cotizacion_cantidad:cantidad,
            txt_cotizacion_dm:0,
            txt_cotizacion_diametro:0,
            txt_cotizacion_largo:largo,
            txt_cotizacion_valUniCrom:0,
            txt_cotizacion_totalCrom:0,
            txt_cotizacion_valUnitario:0,
            txt_cotizacion_totales:totales,
            txt_cotizacion_cHora:0,
            txt_cotizacion_cantHrs:0,
            txt_cotizacion_totalhoras:0,
            txt_cotizacion_procesos:null,
            txt_cotizacion_diametroInt:diametroInterno,
            txt_cotizacion_diametroExt:diametroExterno,
            txt_cotizacion_trabajoAbrev:trabajoAbreviado,
            txt_cotizacion_ancho:ancho,
            txt_cotizacion_area:area,
            txt_cotizacion_comentarios:comentarios,
            txt_cotizacion_nuevo_usado:nuevousado,
            txt_cotizacion_materialbase:materialbase,
            txt_cotizacion_area_total:areatotal,
            txt_cotizacion_corriente:corriente,
            txt_cotizacion_materiales_descripcion1:matDescripcion1,
            txt_cotizacion_materiales_cantidad1:matCantidad1,
            txt_cotizacion_materiales_preciounitario1:matPreciounitario1,
            txt_cotizacion_materiales_totalitem1:matTotalitem1,
            txt_cotizacion_materiales_descripcion2:matDescripcion2,
            txt_cotizacion_materiales_cantidad2:matCantidad2,
            txt_cotizacion_materiales_preciounitario2:matPreciounitario2,
            txt_cotizacion_materiales_totalitem2:matTotalitem2,
            txt_cotizacion_materiales_descripcion3:matDescripcion3,
            txt_cotizacion_materiales_cantidad3:matCantidad13,
            txt_cotizacion_materiales_preciounitario3:matPreciounitario3,
            txt_cotizacion_materiales_totalitem3:matTotalitem3,
            txt_cotizacion_materiales_descripcion4:matDescripcion4,
            txt_cotizacion_materiales_cantidad4:matCantidad14,
            txt_cotizacion_materiales_preciounitario4:matPreciounitario4,
            txt_cotizacion_materiales_totalitem4:matTotalitem4,
            txt_cotizacion_materiales_descripcion5:matDescripcion5,
            txt_cotizacion_materiales_cantidad5:matCantidad15,
            txt_cotizacion_materiales_preciounitario5:matPreciounitario5,
            txt_cotizacion_materiales_totalitem5:matTotalitem5,
            txt_cotizacion_totalMateriales:totalMateriales,
            txt_cotizacion_totalGeneral:totalGeneralCot,
            iva:iva,
            totalbruto:totalBruto,
            txt_cotizacion_numero:numeroCotizacion,
            arrayCodProc:JSON.stringify(codProc),
            arrayCantProc:JSON.stringify(cantProc),
            arrayPrecioProc:JSON.stringify(precioProc),
            arrayTotalProc:JSON.stringify(totalProc),
            sequencia:sequence,
            correlativo:correlativo
        },
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#DetalleIngreso").show();
            $("#DetalleModifica").hide();
            $("#CancelaModifica").hide();
            $("#DetalleElimina").hide();
            $("#habilitaDetCom").val("0");
            $("#SubDetalleIngreso").hide();
            $("#txt_cotizacion_cantidad").prop("disabled",true);
            $("#txt_cotizacion_diametroint").prop("disabled",true);
            $("#txt_cotizacion_largo").prop("disabled",true);
            $("#txt_cotizacion_ancho").prop("disabled",true);
            $("#txt_cotizacion_comentarios").prop("disabled",true);
            $("#select_cotizacion_pieza").prop("disabled",true);
            $("#select_cotizacion_nuevo_usado").prop("disabled",true);
            $("#select_cotizacion_materialbase").prop("disabled",true);
            $("#select_cotizacion_trabajo").prop("disabled",true);
            var texto = "<td colspan = \"10\" id=\"margen\" bgcolor=\"#2798D9\" style=\"color: white; font-size: 18px\" align=\"center\"><b>DESCRIPCI&Oacute;N PIEZAS</b></td>";
            $("#Titulo_descp_piezas").html(texto);
            cargaDetallePaso();
        }
    });
}

function eliminaDetalle(){
    var pieza=$("#select_cotizacion_pieza").val();
    var cantidad=$("#txt_cotizacion_cantidad").val();
    var valorDM=$("#txt_cotizacion_dm").val();
    var valorDiametro=$("#txt_cotizacion_diametroint").val();
    var largo=$("#txt_cotizacion_largo").val();
    var valUniCrom=$("#txt_cotizacion_valUniCrom").val();
    var totalCrom=$("#txt_cotizacion_totalCrom").val();
    var valUnitario=$("#txt_cotizacion_valUnitario").val();
    var totales=$("#txt_cotizacion_totales").val();    
    var costoHora=$("#txt_cotizacion_cHora").val();        
    var cantHoras=$("#txt_cotizacion_cHora").val();            
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    
    var procesos=$("#txt_cotizacion_procesos").val();   
    var diametroInterno=$("#txt_cotizacion_diametroint").val();   
    var diametroExterno=$("#txt_cotizacion_diametroext").val();   
    var trabajoAbreviado=$("#select_cotizacion_trabajo").val();   
    var ancho=$("#txt_cotizacion_ancho").val();   
    var area=$("#txt_cotizacion_area").val();   
    var nuevousado=$("#select_cotizacion_nuevo_usado").val();
    var materialbase=$("#select_cotizacion_materialbase").val();
    var areatotal=$("#txt_cotizacion_area_total").val();
    var corriente=$("#txt_cotizacion_corriente").val();    
    var comentarios=$("#txt_cotizacion_comentarios").val();      
    
    var sequence =getUrlParameter('secuencia');
    var correlativo=$("#txt_correlativo").val();
    
    $("#estadoDetalle").val("");
    
    $.ajax({
        url : 'ServletSPCotizacionDet', 
                data: "opcion=delete"+"&select_cotizacion_pieza=0"+"&txt_cotizacion_cantidad=0"+
                "&txt_cotizacion_dm=0"+"&txt_cotizacion_diametroint=0"+
                "&txt_cotizacion_largo=0"+"&txt_cotizacion_valUniCrom=0"+
                "&txt_cotizacion_totalCrom=0"+"&txt_cotizacion_valUnitario=0"+
                "&txt_cotizacion_totales=0"+"&txt_cotizacion_cHora=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt=0"+
                "&txt_cotizacion_diametroExt=0"+"&txt_cotizacion_trabajoAbrev=0"+
                "&txt_cotizacion_ancho=0"+"&txt_cotizacion_area=0"+
                "&txt_cotizacion_nuevo_usado="+"&txt_cotizacion_materialbase="+
                "&txt_cotizacion_area_total=0"+"&txt_cotizacion_corriente=0"+
                "&txt_cotizacion_materiales_descripcion1="+
                "&txt_cotizacion_materiales_cantidad1=0"+"&txt_cotizacion_materiales_preciounitario1=0"+
                "&txt_cotizacion_materiales_totalitem1=0"+"&txt_cotizacion_materiales_descripcion2="+                
                "&txt_cotizacion_materiales_cantidad2=0"+"&txt_cotizacion_materiales_preciounitario2=0"+                                
                "&txt_cotizacion_materiales_totalitem2=0"+"&txt_cotizacion_materiales_descripcion3="+
                "&txt_cotizacion_materiales_cantidad3=0"+"&txt_cotizacion_materiales_preciounitario3=0"+
                "&txt_cotizacion_materiales_totalitem3=0"+"&txt_cotizacion_materiales_descripcion4="+
                "&txt_cotizacion_materiales_cantidad4=0"+"&txt_cotizacion_materiales_preciounitario4=0"+
                "&txt_cotizacion_materiales_totalitem4=0"+"&txt_cotizacion_materiales_descripcion5="+
                "&txt_cotizacion_materiales_cantidad5=0"+"&txt_cotizacion_materiales_preciounitario5=0"+
                "&txt_cotizacion_materiales_totalitem5=0"+"&txt_cotizacion_totalMateriales=0"+
                "&txt_cotizacion_totalGeneral=0"+"&iva=0"+"&totalbruto=0"+              
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#DetalleIngreso").show();
            $("#DetalleModifica").hide();
            $("#DetalleElimina").hide();
            $("#CancelaModifica").hide();
            $("#habilitaDetCom").val("0");
            $("#SubDetalleIngreso").hide();
            $("#txt_cotizacion_cantidad").prop("disabled",true);
            $("#txt_cotizacion_diametroint").prop("disabled",true);
            $("#txt_cotizacion_largo").prop("disabled",true);
            $("#txt_cotizacion_ancho").prop("disabled",true);
            $("#txt_cotizacion_comentarios").prop("disabled",true);
            $("#select_cotizacion_pieza").prop("disabled",true);
            $("#select_cotizacion_nuevo_usado").prop("disabled",true);
            $("#select_cotizacion_materialbase").prop("disabled",true);
            $("#select_cotizacion_trabajo").prop("disabled",true);
            var texto = "<td colspan = \"10\" id=\"margen\" bgcolor=\"#2798D9\" style=\"color: white; font-size: 18px\" align=\"center\"><b>DESCRIPCI&Oacute;N PIEZAS</b></td>";
            $("#Titulo_descp_piezas").html(texto);
            cargaDetallePaso();
        }
    });
}

function cargaValorDetalle(){
    var codigo=$("#select_cotizacion_pieza").val();
    var descrip=$("#select_cotizacion_pieza option:selected").text();
    var estadoDetalle = $("#estadoDetalle").val();
    
    if ($("#select_cotizacion_pieza").val() == "") {
        FuncionErrores(42);
        $("#txt_cotizacion_area").val("0");
        $("#select_cotizacion_pieza").focus();
        return;
    }
    if ($("#txt_cotizacion_largo").val() == "") {
        FuncionErrores(32);
        $("#txt_cotizacion_area").val("0");
        $("#txt_cotizacion_largo").focus();
        return;
    }
    
    if ($("#txt_cotizacion_diametroint").val() == "" && $("#txt_cotizacion_ancho").val() == "") {
        FuncionErrores(45);
        $("#txt_cotizacion_area").val("0");
        $("#select_cotizacion_pieza").focus();
        return;
    }    
    if (estadoDetalle == "Modifica"){
        if ($("#txt_cotizacion_area").val() != $("#txt_cotizacion_superficie").val() && $("#txt_cotizacion_cantidad").val() != $("#txt_cotizacion_cantidadnuevo").val()) {
            FuncionErrores(57);
            $("#superModificado").val("Modificado");
            $("#cantidadModificado").val("Modificado");
            
            cargaProcesos();
            $( "#dialog_procesos" ).dialog("open");
        }
        else {
            if ($("#txt_cotizacion_area").val() != $("#txt_cotizacion_superficie").val()) {
                FuncionErrores(58);
                $("#superModificado").val("Modificado");
                $("#cantidadModificado").val("");
            
                cargaProcesos();
                $( "#dialog_procesos" ).dialog("open");
            }
            else {
                if ($("#txt_cotizacion_cantidad").val() != $("#txt_cotizacion_cantidadnuevo").val()) {
                    FuncionErrores(59);
                    $("#superModificado").val("");
                    $("#cantidadModificado").val("Modificado");
            
                    cargaProcesos();
                    $( "#dialog_procesos" ).dialog("open");
                }
                else {
                    modificaDetalle();
                }
            }
            }
        }

    }

function filtraPiezas(){
    var nombre=$("#txt_cotizacion_filtro_pieza").val();
    $.ajax({
        url : 'ServletSPPieza', 
        data: "opcion=filter&codigo=&nombre="+nombre,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#select_cotizacion_pieza_filter").html(data);
        }
    });
}

function filtraTrabajos(){
    var nombre=$("#txt_cotizacion_filtro_trabajo").val();
    $.ajax({
        url : 'ServletSPTrabajosAbreviados', 
        
        data: "descripcion="+nombre,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#select_cotizacion_trabajo_filter").html(data);
        }
    });
}

function calculaTotal(){
    var superficie;
    var diametroInt = $("#txt_cotizacion_diametroint").val()==""?"0":$("#txt_cotizacion_diametroint").val();
    var largo = $("#txt_cotizacion_largo").val()==""?"0":$("#txt_cotizacion_largo").val();      
    var ancho = $("#txt_cotizacion_ancho").val()==""?"0":$("#txt_cotizacion_ancho").val();          
    var cantidad = $("#txt_cotizacion_cantidad").val()==""?"0":$("#txt_cotizacion_cantidad").val();
    var area = 0, areatotal = 0, corriente = 0;
    var totalUnitario = 0;
    var total = 0;
    var areaComa, areatotalComa, corrienteComa;
    var codigo=$("#select_cotizacion_pieza").val();
    var descrip=$("#select_cotizacion_pieza option:selected").text();

    $.ajax({
        url : 'ServletSPTablas',
        data : "opcion_MaeTablas=consulta_ciudad"+"&hid_tabla_id=0"+"&slt_tabla_mae=Piezas"+"&slt_tabla_rel1=0"+"&slt_tabla_rel2=0"+"&txt_tabla_descripcion="+descrip+"&txt_tabla_descripcion2=0",
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            superficie = data;
            largo=$('#txt_cotizacion_largo').autoNumeric('get');
            diametroInt=$('#txt_cotizacion_diametroint').autoNumeric('get');
            ancho = $("#txt_cotizacion_ancho").autoNumeric('get')
            if (superficie.substring(0,2) == "SI") {
                if (diametroInt != 0) {
                    area = 6.2832 * (diametroInt / 200) * (largo / 100);
                }
            }
            else {
                area = largo * ancho;                
            }
        corriente = area * 30;
        corrienteComa = corriente.toFixed(2);
        corriente = corrienteComa.toString().replace(".", ",");
        
        $("#txt_cotizacion_corriente").val(corriente);
    
        areatotal = area * cantidad;    
        areaComa = area.toFixed(2);
        area = areaComa.toString().replace(".", ",");
        areatotalComa = areatotal.toFixed(2);
        areatotal = areatotalComa.toString().replace(".", ",");

        $("#txt_cotizacion_area").val(area);
        $("#txt_cotizacion_area_total").val(areatotal);
            
       },
    error:function(error){
    alert(error.responseText);
    console.log(error);
    }
    });
   //ingresaDetalle();
}

function formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
}

function CargaCorrDet(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var correlativo=$("#txt_correlativo2").val();
    var sequence =getUrlParameter('secuencia');

    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=select_detalle"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#tablaSubDetalle").find("tbody").html(data);
        }
    });
}

function CargaSubDetModifica(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var correlativo=$("#txt_correlativo").val();
    var sequence =getUrlParameter('secuencia');

    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=select"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
        }
    });
}

function ingresaSubDetalle(){
    var valor=$("#txt_cotizacion_valor").val();
    var margen=$("#txt_cotizacion_margen").val();
    var totalmaterial=$("#txt_cotizacion_totalmaterial").val();
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var correlativo=$("#txt_correlativo").val();    
    var sequence =getUrlParameter('secuencia');

    if ($("#txt_cotizacion_valor").val() == "") {
        FuncionErrores(37);
        $("#txt_cotizacion_valor").focus();
        return false;
    }    

    if ($("#txt_cotizacion_margen").val() == "") {
        FuncionErrores(38);
        $("#txt_cotizacion_margen").focus();
        return false;
    }    

    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=insert"+"&txt_cotizacion_valor="+valor+"&txt_cotizacion_margen="+margen+
                "&txt_cotizacion_totalmaterial="+totalmaterial+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            cargaSubDetalle();
        }
    });
}

function cargaSubDetalle(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var correlativo=$("#txt_correlativo").val();        
    var sequence =getUrlParameter('secuencia');
    
    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=select_detalle"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#tablaSubDetalle").find("tbody").html(data);
            $("#txt_cotizacion_valor").val("");
            $("#txt_cotizacion_margen").val("");
        }
    });
}

function ModificaSubDetalleComercial(id)
{
    CancelaSubDetalle();
    if($("#habilitaSubDetCom").val() == 0)
    {
        $("#filaTablaSubDetalle"+id).css("background-color","#58FAF4").removeClass("alt");
        $("#txt_cotizacion_valor").val($("#cotazacionSubDet_valor"+id).text());
        $("#txt_cotizacion_margen").val($("#cotazacionSubDet_margen"+id).text());              
        
        var accion=getUrlParameter('accion');
        
        if(accion=="consulta"){
            $("#SubDetalleIngreso").hide(); 
            $("#txt_subitem").val($("#cotazacionSubDet_item"+id).text());
            return;
        }
        $("#SubDetalleIngreso").hide();
        $("#SubDetalleModifica").show();
        $("#SubDetalleElimina").show();        
        $("#SubDetalleCancela").show();                
        $("#DetalleIngreso").hide();
        $("#habilitaSubDetCom").val("1");
        $("#txt_subitem").val($("#cotazacionSubDet_item"+id).text());
        $("#hidtemp").val(id);
    }
}

function CancelaSubDetalle()
{
    var td = $('#tablaSubDetalle').children('tbody').children('tr').length;  
    for(var i = 0; i<=td;i++){                
        if(i % 2 === 0){
            $("#filaTablaSubDetalle"+i).addClass("alt");
        }else {                    
            $("#filaTablaSubDetalle"+i).css("background-color","white");
        }
    }
    
    $("#SubDetalleIngreso").show();
    $("#SubDetalleModifica").hide();
    $("#SubDetalleElimina").hide();    
    $("#SubDetalleCancela").hide();                    
    $("#habilitaSubDetCom").val("0");
    $("#txt_cotizacion_valor").val("");
    $("#txt_cotizacion_margen").val("");
}

function modificaSubDetalle(){
    var valor=$("#txt_cotizacion_valor").val();
    var margen=$("#txt_cotizacion_margen").val(); 
    var totalmaterial=$("#txt_cotizacion_totalmaterial").val();    
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var correlativo=$("#txt_correlativo").val();
    var subitem=$("#txt_subitem").val();
    
    if ($("#txt_cotizacion_valor").val() == "") {
        FuncionErrores(37);
        $("#txt_cotizacion_valor").focus();
        return false;
    }    

    if ($("#txt_cotizacion_margen").val() == "") {
        FuncionErrores(38);
        $("#txt_cotizacion_margen").focus();
        return false;
    }        
    
    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=update"+"&txt_cotizacion_valor="+valor+"&txt_cotizacion_margen="+margen+
                "&txt_cotizacion_totalmaterial=0"+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+subitem+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#SubDetalleIngreso").show();
            $("#SubDetalleModifica").hide();
            $("#SubDetalleElimina").hide();
            $("#SubDetalleCancela").hide();                            
            $("#btn_detalleComercial_cancela").hide();
            $("#habilitaSubDetCom").val("0");
            cargaSubDetalle();
        }
    });
}

function eliminaSubDetalle(){
    var valor=$("#txt_cotizacion_valor").val();
    var margen=$("#txt_cotizacion_margen").val(); 
    var totalmaterial=$("#txt_cotizacion_totalmaterial").val(); 
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var correlativo=$("#txt_correlativo2").val();
    var subitem=$("#txt_subitem").val();    
    
    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=delete"+"&txt_cotizacion_valor=0"+"&txt_cotizacion_margen=0"+
                "&txt_cotizacion_totalmaterial=0"+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+subitem+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#SubDetalleIngreso").show();
            $("#SubDetalleModifica").hide();
            $("#SubDetalleElimina").hide();
            $("#SubDetalleCancela").hide();                            
            $("#habilitaSubDetCom").val("0");
            cargaSubDetalle();
        }
    });
}

function LimpiaSubDetalle(){
    var numeroCotizacion=0;
    var correlativo=0;        
    var sequence =0;
    
    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=select_detalle"+"&txt_cotizacion_valor="+0+"&txt_cotizacion_margen="+0+
                "&txt_cotizacion_totalmaterial="+0+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem="+0+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#tablaSubDetalle").find("tbody").html(data);
            $("#txt_cotizacion_valor").val("");
            $("#txt_cotizacion_margen").val("");
            $("#SubDetalleModifica").hide();
            $("#SubDetalleElimina").hide();
            $("#SubDetalleCancela").hide();                            
            $("#habilitaSubDetCom").val("0");
        }
    });
}

function eliminaTodoSubDetalle(){
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var correlativo=$("#txt_correlativo2").val();
    var subitem=$("#txt_subitem").val();    
    
    $.ajax({
        url : 'ServletSPCotizacionSubDet', 
        data: "opcion=delete"+"&txt_cotizacion_valor=0"+"&txt_cotizacion_margen=0"+
                "&txt_cotizacion_totalmaterial=0"+"&txt_cotizacion_numero="+numeroCotizacion+
                "&subitem=0"+"&sequencia="+sequence+"&correlativo="+correlativo,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#SubDetalleIngreso").show();
            $("#SubDetalleModifica").hide();
            $("#SubDetalleElimina").hide();
            $("#SubDetalleCancela").hide();                            
            $("#habilitaSubDetCom").val("0");
            LimpiaSubDetalle();
        }
    });
}

function cargaProcesos() {
    var pieza = $("#select_cotizacion_pieza option:selected").text();
    var valorUF = $("#txt_cotizacion_uf").val();
    var area = $("#txt_cotizacion_area").val();
    var cantidad = $("#txt_cotizacion_cantidad").val();
    var estadoDetalle = $("#estadoDetalle").val();
    var accion=getUrlParameter('accion');          
    
    $.ajax({
        url : 'ServletSPProcesos', 
        data: "pieza="+pieza+"&valorUF="+valorUF+"&area="+area+"&cantidad="+cantidad,
        type : 'POST',
        dataType : "html",
        async:false,
        success : function(data) {
            $("#contenido_procesos").html(data);
            if(accion==="consulta"){
                $("#btn_AceptarProceso").hide();
            }
            if (estadoDetalle == "Modifica") {
                if($("#txt_cotizacion_cantidad").val() != $("#txt_cotizacion_cantidadnuevo").val()){
                    $("#cantidadModificado").val("Modificado");
                }
                if ($("#txt_cotizacion_area").val() != $("#txt_cotizacion_superficie").val()) {
                    $("#superModificado").val("Modificado");
                }
                var superModificado = $("#superModificado").val();
                var cantidadModificado = $("#cantidadModificado").val();
                cargaDatosProcesos(superModificado, cantidadModificado);
            }
            else
            {
            $("#txt_cotizacion_total1").val("0");
            $("#txt_cotizacion_total2").val("0");
            $("#txt_cotizacion_total3").val("0");
            $("#txt_cotizacion_total4").val("0");
            $("#txt_cotizacion_total5").val("0");
            $("#txt_cotizacion_total6").val("0");
//            $("#txt_cotizacion_cant7").val("0");
//            $("#txt_cotizacion_preciounitario7").val("0");
            $("#txt_cotizacion_total7").val("0");
            
//            $("#txt_cotizacion_cant8").val("0");
//            $("#txt_cotizacion_preciounitario8").val("0");
            $("#txt_cotizacion_total8").val("0");
            
//            $("#txt_cotizacion_cant9").val("0");
//            $("#txt_cotizacion_preciounitario9").val("0");
            $("#txt_cotizacion_total9").val("0");
            
            $("#txt_cotizacion_total10").val("0");
            
//            $("#txt_cotizacion_cant11").val("0");
//            $("#txt_cotizacion_preciounitario11").val("0");
            $("#txt_cotizacion_total11").val("0");
            
//            $("#txt_cotizacion_cant12").val("0");
//            $("#txt_cotizacion_preciounitario12").val("0");
            $("#txt_cotizacion_total12").val("0");
           
        
            $("#txt_cotizacion_materiales_descripcion1").val("");
            $("#txt_cotizacion_materiales_cantidad1").val("0");
            $("#txt_cotizacion_materiales_preciounitario1").val("0");
            $("#txt_cotizacion_materiales_totalitem1").val("0");
            $("#txt_cotizacion_materiales_descripcion2").val("");
            $("#txt_cotizacion_materiales_cantidad2").val("0");
            $("#txt_cotizacion_materiales_preciounitario2").val("0");
            $("#txt_cotizacion_materiales_totalitem2").val("0");
            $("#txt_cotizacion_materiales_descripcion3").val("");
            $("#txt_cotizacion_materiales_cantidad3").val("0");
            $("#txt_cotizacion_materiales_preciounitario3").val("0");
            $("#txt_cotizacion_materiales_totalitem3").val("0");
            $("#txt_cotizacion_materiales_descripcion4").val("");
            $("#txt_cotizacion_materiales_cantidad4").val("0");
            $("#txt_cotizacion_materiales_preciounitario4").val("0");
            $("#txt_cotizacion_materiales_totalitem4").val("0");
            $("#txt_cotizacion_materiales_descripcion5").val("");
            $("#txt_cotizacion_materiales_cantidad5").val("0");
            $("#txt_cotizacion_materiales_preciounitario5").val("0");
            $("#txt_cotizacion_materiales_totalitem5").val("0");
            
            $("#txt_cotizacion_totalProcesos").val("0");
            $("#txt_cotizacion_totalMateriales").val("0");
            $("#txt_cotizacion_totalGeneral").val("0");

            }
        }
    });
}

function limpiaProcesos() {
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var correlativo =$("#txt_correlativo").val();
        $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=select_detalle"+"&select_cotizacion_pieza=0"+"&txt_cotizacion_cantidad=0"+
                "&txt_cotizacion_dm=0"+"&txt_cotizacion_diametroint=0"+
                "&txt_cotizacion_largo=0"+"&txt_cotizacion_valUniCrom=0"+
                "&txt_cotizacion_totalCrom=0"+"&txt_cotizacion_valUnitario=0"+
                "&txt_cotizacion_totales=0"+"&txt_cotizacion_cHora=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt=0"+
                "&txt_cotizacion_diametroExt=0"+"&txt_cotizacion_trabajoAbrev=0"+
                "&txt_cotizacion_ancho=0"+"&txt_cotizacion_area=0"+
                "&txt_cotizacion_nuevo_usado="+"&txt_cotizacion_materialbase="+
                "&txt_cotizacion_area_total=0"+"&txt_cotizacion_corriente=0"+
                "&txt_cotizacion_materiales_descripcion1="+
                "&txt_cotizacion_materiales_cantidad1=0"+"&txt_cotizacion_materiales_preciounitario1=0"+
                "&txt_cotizacion_materiales_totalitem1=0"+"&txt_cotizacion_materiales_descripcion2="+                
                "&txt_cotizacion_materiales_cantidad2=0"+"&txt_cotizacion_materiales_preciounitario2=0"+                                
                "&txt_cotizacion_materiales_totalitem2=0"+"&txt_cotizacion_materiales_descripcion3="+
                "&txt_cotizacion_materiales_cantidad3=0"+"&txt_cotizacion_materiales_preciounitario3=0"+
                "&txt_cotizacion_materiales_totalitem3=0"+"&txt_cotizacion_materiales_descripcion4="+
                "&txt_cotizacion_materiales_cantidad4=0"+"&txt_cotizacion_materiales_preciounitario4=0"+
                "&txt_cotizacion_materiales_totalitem4=0"+"&txt_cotizacion_materiales_descripcion5="+
                "&txt_cotizacion_materiales_cantidad5=0"+"&txt_cotizacion_materiales_preciounitario5=0"+
                "&txt_cotizacion_materiales_totalitem5=0"+"&txt_cotizacion_totalMateriales=0"+
                "&txt_cotizacion_totalGeneral=0"+"&iva=0"+"&totalbruto=0"+              
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo="+correlativo,
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
//            
//            $("#txt_cotizacion_cant1").val(arrResult[1]);
//            $("#txt_cotizacion_preciounitario1").val(arrResult[2]);
//            $("#txt_cotizacion_total1").val(arrResult[3]);
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
            
            /*if ($("#txt_cotizacion_total2").val() !== "0") {
                $('input[name="chk_group[]"]').prop('checked', true);
            }*/
        }
    });

}

function recorreProcesos() {
    var totalGeneral = 0;

    $('input[name="chk_group[]"]:checked').each(function() {
        totalGeneral = totalGeneral + parseInt($("#txt_cotizacion_total"+ $(this).val()).val(),10);
    });
    
    $("#select_cotizacion_total_materiales").val($("#txt_cotizacion_totalMateriales").val());
    
}

function calculaTotalProcesos(id) {
    var cantidad = $("#txt_cotizacion_cant"+id).val().replace(/,/g ,".");
    var precio = $("#txt_cotizacion_preciounitario"+id).val().replace(/\./g ,"");
    var cantPiezas =  $("#txt_cotizacion_cantidad"+id).val().replace(/\./g ,"");
    var total = 0;
    var totalProcesos = 0;
    var totalMateriales = 0;
    var totalGeneral = 0;
    var totalItem;
    total = cantidad * precio * cantPiezas;
    $("#txt_cotizacion_total"+id).val(separadorDeMiles(total.toFixed(0)));
    $('input[name="chk_group[]"]:not(:checked)').each(function() {
        var value = $(this).attr('value');
        $("#txt_cotizacion_total"+value).val("0");
        totalItem = 0;
        totalProcesos = totalProcesos + parseInt(totalItem);
    });
 
     
    $('input[name="chk_group[]"]:checked').each(function() {
        totalItem = $("#txt_cotizacion_total"+ $(this).val()).val().replace(/\./g ,"");
        totalProcesos = totalProcesos + parseInt(totalItem);
    });
    
    $("#txt_cotizacion_totalProcesos").val(separadorDeMiles(totalProcesos.toString()));
    totalMateriales = parseInt($("#txt_cotizacion_totalMateriales").val().replace(/\./g ,""));
    totalGeneral = totalProcesos + totalMateriales;
    $("#txt_cotizacion_totalGeneral").val(separadorDeMiles(totalGeneral.toString()));
}

function calculaTotalCoti() {
    var cantTotal = 0;
    var totalNeto = 0;
    var totalBruto = 0;
    var totalProcesos = 0;
    var totalMateriales = 0;    
    var iva = 0;
    var td = $('#tblDetalleComer').children('tbody').children('tr').length; 
    for(var i = 0; i<td;i++){
        cantTotal = cantTotal + parseInt($("#cotazacionDet_totales"+i).text().replace(/\./g ,""),10);
        totalProcesos = totalProcesos + parseInt($("#cotazacionTotalProcesos"+i).text().replace(/\./g ,""),10);
        totalMateriales = totalMateriales + parseInt($("#cotazacionTotalMateriales"+i).text().replace(/\./g ,""),10);
        iva = iva + parseInt($("#cotazacionDet_iva"+i).text().replace(/\./g ,""),10);
        totalBruto = totalBruto + parseInt($("#cotazacionDet_totalBruto"+i).text().replace(/\./g ,""),10);
    }
    
    totalNeto = totalProcesos + totalMateriales;
    $("#select_cotizacion_total_procesos").val(separadorDeMiles(totalProcesos.toFixed(0)));   
    $("#select_cotizacion_total_materiales").val(separadorDeMiles(totalMateriales.toFixed(0)));   
    $("#txt_cotizacion_total_neto").val(separadorDeMiles(totalNeto.toFixed(0)));
    $("#txt_cotizacion_iva").val(separadorDeMiles(iva.toFixed(0)));
    $("#txt_cotizacion_total_bruto").val(separadorDeMiles(totalBruto.toFixed(0)));
    
    $("#txt_cotizacion_total").val(separadorDeMiles(cantTotal));  

}

function numberWithCommas(x) {
    var val = parseInt($("#txt_cotizacion_preciounitario"+x).text());
    val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    $("#txt_cotizacion_preciounitario"+x).text(val);
}

function calculaTotalMateriales(id) {
    var cantidadMat = $("#txt_cotizacion_materiales_cantidad"+id).val().replace(/\./g ,"");
    var precioMat = $("#txt_cotizacion_materiales_preciounitario"+id).val().replace(/\./g ,"");
    var totalMat = 0;
    var totalGeneral = 0;
    var totalMateriales = 0;
    var totalPieza = 0;
    totalMat = cantidadMat * precioMat;
      
    $("#txt_cotizacion_materiales_totalitem"+id).val(separadorDeMiles(totalMat.toFixed(0)));
    
    totalMateriales = parseInt($("#txt_cotizacion_materiales_totalitem1").val().replace(/\./g ,"")) + 
                      parseInt($("#txt_cotizacion_materiales_totalitem2").val().replace(/\./g ,"")) + 
                      parseInt($("#txt_cotizacion_materiales_totalitem3").val().replace(/\./g ,"")) + 
                      parseInt($("#txt_cotizacion_materiales_totalitem4").val().replace(/\./g ,"")) + 
                      parseInt($("#txt_cotizacion_materiales_totalitem5").val().replace(/\./g ,""));

    totalPieza = totalMateriales + parseInt($("#txt_cotizacion_totalProcesos").val().replace(/\./g ,""));
    
    
    $("#txt_cotizacion_totalMateriales").val(separadorDeMiles(totalMateriales.toFixed(0)));
    $("#txt_cotizacion_totalGeneral").val(separadorDeMiles(totalPieza.toFixed(0)));
}

function SalirCliente() {
    $("#dialog_maestrocli").dialog("close");
}

function AceptaDialogoProcesos() {
    var estadoDetalle = $("#estadoDetalle").val();
    recorreProcesos();
    if (estadoDetalle == "Modifica") {
        
        modificaDetalle();
    }
    else {
        ingresaDetalle();
    }
    
    
    $("#dialog_procesos").dialog("close");
}

function CancelaDialogoProcesos() {
    $("#dialog_procesos").dialog("close");
}

function limpiaZeros(data) {
    if (data.value == "0") {
        data.value = "";
    }
}

function seteaZeros(data) {
    if (data.value == "") {
        data.value = "0";
    }
}

function separadorDeMilesII(data) {
    var valor = "";
    var cont = 0;
    var subvalor = "";
    if (data.value.length == 0 || data.value.length == 1 || data.value.length == 2 || data.value.length == 3) {
        return;
    }
    
    for (var j = data.value.length; j >= 1; j--) {
        cont = cont + 1;
        subvalor = data.value.substr(j-1, 1);
        valor = subvalor + valor;
        
        if (cont === 3 && j > 1) {
            cont = 0;
            valor = "." + valor;
        }
    }
    data.value = valor;
  }

function quitarSeparadoII(data) {
    var valor = "";
    valor = data.value.replace(/\./g ,"");
    data.value = valor;
}

function cargaDatosProcesos(superModificado, cantidadModificado) {
    var numeroCotizacion=$("#txt_cotizacion_numero").val();
    var sequence =getUrlParameter('secuencia');
    var correlativo =$("#txt_correlativo").val();
    var area = $("#txt_cotizacion_area").val();
    var areaPunto = $("#txt_cotizacion_area").val().replace(",",".");
    var cantidad = $("#txt_cotizacion_cantidad").val();
    var cantidadPunto = $("#txt_cotizacion_cantidad").val().replace(".","");
    
    
        $.ajax({
        url : 'ServletSPCotizacionDet', 
        data: "opcion=select_detalle"+"&select_cotizacion_pieza=0"+"&txt_cotizacion_cantidad=0"+
                "&txt_cotizacion_dm=0"+"&txt_cotizacion_diametroint=0"+
                "&txt_cotizacion_largo=0"+"&txt_cotizacion_valUniCrom=0"+
                "&txt_cotizacion_totalCrom=0"+"&txt_cotizacion_valUnitario=0"+
                "&txt_cotizacion_totales=0"+"&txt_cotizacion_cHora=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_cantHrs=0"+"&txt_cotizacion_totalhoras=0"+
                "&txt_cotizacion_procesos="+"&txt_cotizacion_diametroInt=0"+
                "&txt_cotizacion_diametroExt=0"+"&txt_cotizacion_trabajoAbrev=0"+
                "&txt_cotizacion_ancho=0"+"&txt_cotizacion_area=0"+
                "&txt_cotizacion_nuevo_usado="+"&txt_cotizacion_materialbase="+
                "&txt_cotizacion_area_total=0"+"&txt_cotizacion_corriente=0"+
                "&txt_cotizacion_materiales_descripcion1="+
                "&txt_cotizacion_materiales_cantidad1=0"+"&txt_cotizacion_materiales_preciounitario1=0"+
                "&txt_cotizacion_materiales_totalitem1=0"+"&txt_cotizacion_materiales_descripcion2="+                
                "&txt_cotizacion_materiales_cantidad2=0"+"&txt_cotizacion_materiales_preciounitario2=0"+                                
                "&txt_cotizacion_materiales_totalitem2=0"+"&txt_cotizacion_materiales_descripcion3="+
                "&txt_cotizacion_materiales_cantidad3=0"+"&txt_cotizacion_materiales_preciounitario3=0"+
                "&txt_cotizacion_materiales_totalitem3=0"+"&txt_cotizacion_materiales_descripcion4="+
                "&txt_cotizacion_materiales_cantidad4=0"+"&txt_cotizacion_materiales_preciounitario4=0"+
                "&txt_cotizacion_materiales_totalitem4=0"+"&txt_cotizacion_materiales_descripcion5="+
                "&txt_cotizacion_materiales_cantidad5=0"+"&txt_cotizacion_materiales_preciounitario5=0"+
                "&txt_cotizacion_materiales_totalitem5=0"+"&txt_cotizacion_totalMateriales=0"+
                "&txt_cotizacion_totalGeneral=0"+"&iva=0"+"&totalbruto=0"+              
                "&arrayCodProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayCantProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&arrayPrecioProc=0,0,0,0,0,0,0,0,0,0,0,0"+"&arrayTotalProc=0,0,0,0,0,0,0,0,0,0,0,0"+
                "&txt_cotizacion_comentarios="+"&txt_cotizacion_numero="+numeroCotizacion+
                "&sequencia="+sequence+"&correlativo="+correlativo,
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

function HabilitaDescripPiezas() {
    $("#txt_cotizacion_cantidad").prop("disabled",false);
    $("#txt_cotizacion_diametroint").prop("disabled",false);
    $("#txt_cotizacion_largo").prop("disabled",false);
    $("#txt_cotizacion_ancho").prop("disabled",false);
    $("#txt_cotizacion_comentarios").prop("disabled",false);
    $("#select_cotizacion_pieza").prop("disabled",false);
    $("#select_cotizacion_nuevo_usado").prop("disabled",false);
    $("#select_cotizacion_materialbase").prop("disabled",false);
    $("#select_cotizacion_trabajo").prop("disabled",false);
}

function Checkea(id) {
    $('input[name="chk_group[]"]').each(function() {
        var name = $(this).attr('name'); // grab name of original
        var value = $(this).attr('value'); // grab value of original
        if (value == id ) {
            $(this).prop('checked', true);
        }
    });
}