function cargaPresupuesto(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Presupuesto_Valido",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_cotizacion_presupuesto_valido").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaPLazoEntrega(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Plazo_Entrega",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_cotizacion_plazo_entrega").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaCondicion(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Condiciones_Pago",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_cotizacion_condicion_pago").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaCondicionGuia(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Condiciones_Pago",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_guiadespacho_condicion_pago").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaGuiaEspecial(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Guia_Especial",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_guiadespacho_especial").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaMoneda(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Moneda",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_cotizacion_moneda").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}

function cargaEstadoCoti(){
    $.ajax({
        url : 'ServletCargaParametros', 
        data: "tabla=Estado_Cotizacion",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#select_cotizacion_estado").html('<option value="">--Seleccione--</option>'+data);
        }
    });
}