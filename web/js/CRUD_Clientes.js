function grabarCliente(){
    var codigo=$("#txt_cliente_codigo").val();
    var rutCli=$("#txt_cliente_rut").val();
    var razonSocial=$("#txt_cliente_nombre").val();
    var sigla=$("#txt_sigla_cliente").val();
    var direccion=$("#txt_cliente_direccion").val();
    var comuna=$("#txt_cliente_comuna").val();
    var ciudad=$("#txt_cliente_ciudad").val();
    var fono1=$("#txt_cliente_fono1").val();
    var fono2=$("#txt_cliente_fono2").val();
    var fax=$("#txt_cliente_fax").val();
    var rubro=$("#txt_cliente_rubro").val();
    var contacto=$("#txt_cliente_contacto").val();
    var casilla=$("#txt_cliente_casilla").val();
    var vendedor=$("#txt_cliente_vendedor").val();
    var estado=$("#txt_cliente_estado").val();
    var nombreContactoComercial=$("#txt_cliente_nombreContactoComercial").val();
    var cargoContactoComercial=$("#txt_cliente_cargoContactoComercial").val();    
    var celularContactoComercial=$("#txt_cliente_celularContactoComercial").val();    
    var fijoContactoComercial=$("#txt_cliente_fijoContactoComercial").val();
    var emailContactoComercial=$("#txt_cliente_emailContactoComercial").val();
    var nombreContactoContable=$("#txt_cliente_nombreContactoContable").val();
    var cargoContactoContable=$("#txt_cliente_cargoContactoContable").val();    
    var celularContactoContable=$("#txt_cliente_celularContactoContable").val();
    var fijoContactoContable=$("#txt_cliente_fijoContactoContable").val();    
    var emailContactoContable=$("#txt_cliente_emailContactoContable").val();
    var accion="";
    
    if(codigo==""){
        accion="insert";
    }else{
        accion="update";
    }
    
    $.ajax({
    url : 'ServletSPCliente', 
    data:{
        mod: accion,
        txt_cliente_codigo: codigo,
        txt_cliente_rut: rutCli,
        txt_cliente_nombre: razonSocial,
        txt_sigla_cliente: sigla,
        txt_cliente_direccion: direccion,
        txt_cliente_comuna: comuna,
        txt_cliente_ciudad: ciudad,
        txt_cliente_fono1: fono1,
        txt_cliente_fono2: fono2,
        txt_cliente_fax: fax,
        txt_cliente_rubro: rubro,
        txt_cliente_contacto: contacto,
        txt_cliente_casilla: casilla,
        txt_cliente_vendedor: vendedor,
        txt_cliente_estado: estado,
        txt_cliente_nombreContactoComercial: nombreContactoComercial,
        txt_cliente_cargoContactoComercial: cargoContactoComercial,
        txt_cliente_celularContactoComercial: celularContactoComercial,
        txt_cliente_fijoContactoComercial: fijoContactoComercial,
        txt_cliente_emailContactoComercial: emailContactoComercial,
        txt_cliente_nombreContactoContable: nombreContactoContable,
        txt_cliente_cargoContactoContable: cargoContactoContable,
        txt_cliente_celularContactoContable: celularContactoContable,
        txt_cliente_fijoContactoContable: fijoContactoContable,
        txt_cliente_emailContactoContable: emailContactoContable
    },
    
//    data : "mod="+accion+"&txt_cliente_codigo="+codigo+"&txt_cliente_rut="+rutCli+"&txt_cliente_nombre="+razonSocial
//                       +"&txt_sigla_cliente="+sigla+"&txt_cliente_direccion="+direccion+"&txt_cliente_comuna="+comuna
//                       +"&txt_cliente_ciudad="+ciudad+"&txt_cliente_fono1="+fono1+"&txt_cliente_fono2="+fono2
//                       +"&txt_cliente_fax="+fax+"&txt_cliente_rubro="+rubro+"&txt_cliente_contacto="+contacto
//                       +"&txt_cliente_casilla="+casilla+"&txt_cliente_vendedor="+vendedor+"&txt_cliente_estado="+estado
//                       +"&txt_cliente_nombreContactoComercial="+nombreContactoComercial+"&txt_cliente_cargoContactoComercial="+cargoContactoComercial+"&txt_cliente_celularContactoComercial="+celularContactoComercial
//                       +"&txt_cliente_fijoContactoComercial="+fijoContactoComercial+"&txt_cliente_emailContactoComercial="+emailContactoComercial+"&txt_cliente_nombreContactoContable="+nombreContactoContable
//                       +"&txt_cliente_cargoContactoContable="+cargoContactoContable+"&txt_cliente_celularContactoContable="+celularContactoContable+"&txt_cliente_fijoContactoContable="+fijoContactoContable
//                       +"&txt_cliente_emailContactoContable="+emailContactoContable,
    type : 'POST',
    dataType : "html",
    success : function(data){
        if(accion=="insert"){
            $("#txt_cliente_codigo").val(data);
        }
    }});
}


function Clientes(id){
    var mod = "";
    if (id === 1)
    {	
        mod = "insert";
    } 
    if(id==3){
        mod="Consultar";
        var boton=$("#btn_Cliente_Ingresar");
        var rutCli = $("#txt_cliente_rut").val();
	var nomCli = $("#txt_cliente_nombre").val();
        var contacto = $("#txt_cliente_contacto").val();
	var direccion = $("#txt_cliente_direccion").val();
	var nomEje = $("#txt_cliente_vendedor").val();
	var estado = $("#txt_cliente_estado").val();
        $.ajax({
		url : 'ServletSPCliente', 
                data:{
                    mod: mod,
                    txt_cliente_rut: rutCli,
                    txt_cliente_nombre: nomCli,
                    txt_cliente_contacto: contacto,
                    txt_cliente_direccion: direccion,
                    txt_cliente_vendedor: nomEje,
                    txt_cliente_estado: estado,                    
                },
//		data : "mod="+mod+"&txt_cliente_rut="+rutCli+"&txt_cliente_nombre="+nomCli+"&txt_cliente_contacto="+contacto+
//                        "&txt_cliente_direccion="+direccion+"&txt_cliente_vendedor="+nomEje+"&txt_cliente_estado="+estado,
		type : 'POST',
		dataType : "html"});
            
    }
    if(id === 2)
    {
        mod= "update";
    }
	//var rut = "";
    var codigo=$("#txt_cliente_codigo").val();
    var rutCli=$("#txt_cliente_rut").val();
    var razonSocial=$("#txt_cliente_nombre").val();
    var sigla=$("#txt_sigla_cliente").val();
    var direccion=$("#txt_cliente_direccion").val();
    var comuna=$("#txt_cliente_comuna").val();
    var ciudad=$("#txt_cliente_ciudad").val();
    var fono1=$("#txt_cliente_fono1").val();
    var fono2=$("#txt_cliente_fono2").val();
    var fax=$("#txt_cliente_fax").val();
    var rubro=$("#txt_cliente_giro").val();
    var contacto=$("#txt_cliente_contacto").val();
    var casilla=$("#txt_cliente_casilla").val();
    var vendedor=$("#txt_cliente_vendedor").val();
    var estado=$("#txt_cliente_estado").val();
    var nombreContactoComercial=$("#txt_cliente_nombreContactoComercial").val();
    var cargoContactoComercial=$("#txt_cliente_cargoContactoComercial").val();    
    var celularContactoComercial=$("#txt_cliente_celularContactoComercial").val();
    var fijoContactoComercial=$("#txt_cliente_fijoContactoComercial").val();
    var emailContactoComercial=$("#txt_cliente_emailContactoComercial").val();
    var nombreContactoContable=$("#txt_cliente_nombreContactoContable").val();
    var cargoContactoContable=$("#txt_cliente_cargoContactoContable").val();    
    var celularContactoContable=$("#txt_cliente_celularContactoContable").val();
    var fijoContactoContable=$("#txt_cliente_fijoContactoContable").val();
    var emailContactoContable=$("#txt_cliente_emailContactoContable").val();
        
    $.ajax({
	url : 'ServletSPCliente', 
        data:{
            mod: mod,
            txt_cliente_codigo: codigo,
            txt_cliente_rut: rutCli,
            txt_cliente_nombre: razonSocial,
            txt_sigla_cliente: sigla,
            txt_cliente_direccion: direccion,
            txt_cliente_comuna: comuna,
            txt_cliente_ciudad: ciudad,
            txt_cliente_fono1: fono1,
            txt_cliente_fono2: fono2,
            txt_cliente_fax: fax,
            txt_cliente_rubro: rubro,
            txt_cliente_contacto: contacto,
            txt_cliente_casilla: casilla,
            txt_cliente_vendedor: vendedor,
            txt_cliente_estado: estado,
            txt_cliente_nombreContactoComercial: nombreContactoComercial,
            txt_cliente_cargoContactoComercial: cargoContactoComercial,
            txt_cliente_celularContactoComercial: celularContactoComercial,
            txt_cliente_fijoContactoComercial: fijoContactoComercial,
            txt_cliente_emailContactoComercial: emailContactoComercial,
            txt_cliente_nombreContactoContable: nombreContactoContable,
            txt_cliente_cargoContactoContable: cargoContactoContable,
            txt_cliente_celularContactoContable: celularContactoContable,
            txt_cliente_fijoContactoContable: fijoContactoContable,
            txt_cliente_emailContactoContable: emailContactoContable
        },
//	data : "mod="+mod+"&txt_cliente_codigo="+codigo+"&txt_cliente_rut="+rutCli+"&txt_cliente_nombre="+razonSocial
//                       +"&txt_sigla_cliente="+sigla+"&txt_cliente_direccion="+direccion+"&txt_cliente_comuna="+comuna
//                       +"&txt_cliente_ciudad="+ciudad+"&txt_cliente_fono1="+fono1+"&txt_cliente_fono2="+fono2
//                       +"&txt_cliente_fax="+fax+"&txt_cliente_rubro="+rubro+"&txt_cliente_contacto="+contacto
//                       +"&txt_cliente_casilla="+casilla+"&txt_cliente_vendedor="+vendedor+"&txt_cliente_estado="+estado
//                       +"&txt_cliente_nombreContactoComercial="+nombreContactoComercial+"&txt_cliente_cargoContactoComercial="+cargoContactoComercial+"&txt_cliente_celularContactoComercial="+celularContactoComercial
//                       +"&txt_cliente_fijoContactoComercial="+fijoContactoComercial+"&txt_cliente_emailContactoComercial="+emailContactoComercial+"&txt_cliente_nombreContactoContable="+nombreContactoContable
//                       +"&txt_cliente_cargoContactoContable="+cargoContactoContable+"&txt_cliente_celularContactoContable="+celularContactoContable+"&txt_cliente_fijoContactoContable="+fijoContactoContable
//                       +"&txt_cliente_emailContactoContable="+emailContactoContable,
	type : 'POST',
	dataType : "html",
	success : function(data){            
            if(!isNaN(parseInt(data))){
                if(parseInt(data) == 1062 ){
                    alert("Rut Existente");
                }else{
                    alert("Error al procesar la solicitud");
                }
            }else{
                $(window).unbind('beforeunload');
                window.location.href="svm_Seleccion_Clientes.jsp";
            }
	},
        error:function(error){
            alert(error.responseText);
            console.log(error);
        }
    });
}
function filtrarCliente()
{
    var filVendedorCli = "";
    var filEstadoCli = "";
    var filRutCli = "";
    var filNombreCli= "";
//    var filContactoCli="";
//    var filDirCli="";
    
    filVendedorCli= $("#filtro_cliente_vendedor").val();
    filEstadoCli= $("#filtro_cliente_estado").val();
    filRutCli = $("#filtro_cliente_rut").val();  
    filNombreCli = $("#filtro_cliente_nombre").val();  
//    filContactoCli =  $("#filtro_cliente_contacto").val();
//    filDirCli = $("#filtro_cliente_direccion").val();
    
    if(filVendedorCli == "" && filEstadoCli == "" && filRutCli == "" && filNombreCli == "")
    {
        FuncionErrores(228);
        return false;       
    }
    
    $.ajax({
        url : 'ServletSPCliente', 
        data : "mod=select_clean&txt_cliente_codigo=&txt_cliente_rut="+filRutCli+"&txt_cliente_estado="+filEstadoCli+"&txt_cliente_nombre="+filNombreCli
                +"&txt_sigla_cliente=&txt_cliente_direccion=&txt_cliente_comuna="+
                +"&txt_cliente_ciudad=&txt_cliente_fono1=&txt_cliente_fono2="+
                +"&txt_cliente_fax=&txt_cliente_rubro=&txt_cliente_contacto="+
                +"&txt_cliente_casilla=&txt_cliente_vendedor=",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $('#tblCliente').dataTable().fnDestroy(); 
            $("#tblCliente").find("tbody").html(data);  
            $('#tblCliente').dataTable( {//CONVERTIMOS NUESTRO LISTADO DE LA FORMA DEL JQUERY.DATATABLES- PASAMOS EL ID DE LA TABLA
                "sPaginationType": "full_numbers", //DAMOS FORMATO A LA PAGINACION(NUMEROS)
                bFilter: false, bInfo: false,
                "bLengthChange": false,
                //"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [1,2,3,4,5,6,7,8,9,10,11,12,13,14] }]
            } );
        }
    });
}
function ModificaCliente(id)
{
    $('#tblCliente').children('tbody').children('tr').each(function(i,obj){
        $(this).css("background-color","white");
    });
    CancelaCliente();
    if($("#habilitaCliente").val() == 0)
    {
        $("#filaTablaCliente"+id).css("background-color","#58FAF4").removeClass("alt");
        $("#habilitaCliente").val("1");
        var rut =  $("#Cliente_rut"+id).text();        
        $("#rut").val(rut);
    }
}
//function ConsultaCliente(id)
//{
//    CancelaCliente();
//    if($("#habilitaCliente").val() == 0)
//    {
//        $("#filaTablaCliente"+id).css("background-color","#58FAF4").removeClass("alt");
//        $("#btn_Cliente_Ingresar").hide();
//        $("#habilitaCliente").val("1");
//        var rut =  $("#Cliente_rut"+id).text();        
//        $("#rut").val(rut);
//        
//    }
//}
function CancelaCliente()
{
    if($("#habilitaCliente").val() == 1)
    {
        $("#rut").val("");
	var td = $('#tblCliente').children('tbody').children('tr').length;           
	for(var i = 0; i<=td;i++){                
		if(i % 2 === 0){
			$("#filaTablaCliente"+i).addClass("alt");
		}else {                    
			$("#filaTablaCliente"+i).css("background-color","white");
		}
	}
	$("#habilitaCliente").val("0");
    }
}


function traeCiudad() {
    var comuna = $("#txt_cliente_comuna").val();
     $.ajax({
        url : 'ServletSPTablas', 
        data : "opcion_MaeTablas=consulta_ciudad"+"&hid_tabla_id=0"+"&slt_tabla_mae=Comunas"+"&slt_tabla_rel1=0"+"&slt_tabla_rel2=0"+"&txt_tabla_descripcion="+comuna+"&txt_tabla_descripcion2=0",
        type : 'POST',
        dataType : "html",
        success : function(data) {
            $("#txt_cliente_ciudad").val(data);
        }
    });
   
    
}