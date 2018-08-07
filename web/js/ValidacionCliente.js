function FuncionValidaCliente(id){
     var validarRut = new RegExp("[^0-9kK-]"); 
	

    var rutCli = $("#txt_cliente_rut").val();
    var posicion1 = rutCli.indexOf('-');
    var tmp1 = VerificaRut(rutCli);   
    
    //valida rut cliente
   if(rutCli == ""){
        FuncionErrores(1);   
        $("#txt_cliente_rut").focus();
        return false;
    }
    if (validarRut.test(rutCli)) {
        FuncionErrores(16);   
        $("#txt_cliente_rut").focus();
        return false;
    }
     if(posicion1 == -1)
    {
        FuncionErrores(17);   
        $("#txt_cliente_rut").focus();
        return false;
    }
    
    if(tmp1 == false)
    {
        FuncionErrores(18);   
        $("#txt_cliente_rut").focus();
        return false;
    }
    //valida ingreso nombre cliente
     if($("#txt_cliente_nombre").val() == "")
    {
        FuncionErrores(2); 
        $("#txt_cliente_nombre").focus();
        return false;
    }
    
    /*   if($("#txt_sigla_cliente").val() =="")
    {
        FuncionErrores(3); 
        $("#txt_sigla_cliente").focus();
        return false;
    }*/
    
    if($("#txt_cliente_giro").val() =="")
    {
        FuncionErrores(11); 
        $("#txt_cliente_giro").focus();
        return false;
    }
    
     if($("#txt_cliente_direccion").val() == "")
    {
        FuncionErrores(5); 
        $("#txt_cliente_direccion").focus();
        return false;
    }
    
    if($("#txt_cliente_comuna").val() =="")
    {
        FuncionErrores(6); 
        $("#txt_cliente_comuna").focus();
        return false;
    }
    
    if($("#txt_cliente_ciudad").val() =="")
    {
        FuncionErrores(7); 
        $("#txt_cliente_ciudad").focus();
        return false;
    }
    
    /*     if($("#txt_cliente_fono1").val() =="")
    {
        FuncionErrores(8); 
        $("#txt_cliente_fono1").focus();
        return false;
    }
    
    if($("#txt_cliente_fono2").val() =="")
    {
        FuncionErrores(9); 
        $("#txt_cliente_fono2").focus();
        return false;
    }
    
     if($("#txt_cliente_fax").val() =="")
    {
        FuncionErrores(10); 
        $("#txt_cliente_fax").focus();
        return false;
    }*/
    

        
    /*if($("txt_cliente_contacto").val() == "")
    {
        FuncionErrores(12); 
        $("#txt_cliente_contacto").focus();
        return false;
    }
    
       
    if($("#txt_cliente_casilla").val() =="")
    {
        FuncionErrores(13); 
        $("#txt_cliente_casilla").focus();
        return false;
    }*/
    
    if($("#txt_cliente_estado").val() == "")
    {
	FuncionErrores(15);
	$("#txt_cliente_estado").focus();
        return false;
    }    
    
    if($("#txt_cliente_vendedor").val() == "")
    {
        FuncionErrores(14); 
        $("#txt_cliente_vendedor").focus();
        return false;
    }
   
    if($("#txt_cliente_nombreContactoComercial").val() =="")
    {
        FuncionErrores(46); 
        $("#txt_cliente_nombreContactoComercial").focus();
        return false;
    }
    
    if($("#txt_cliente_cargoContactoComercial").val() =="")
    {
        FuncionErrores(47); 
        $("#txt_cliente_cargoContactoComercial").focus();
        return false;
    }
    
    if($("#txt_cliente_celularContactoComercial").val() =="")
    {
        FuncionErrores(48); 
        $("#txt_cliente_celularContactoComercial").focus();
        return false;
    }
    
    if($("#txt_cliente_fijoContactoComercial").val() =="")
    {
        FuncionErrores(49); 
        $("#txt_cliente_fijoContactoComercial").focus();
        return false;
    }
    
    if($("#txt_cliente_emailContactoComercial").val() =="")
    {
        FuncionErrores(50); 
        $("#txt_cliente_emailContactoComercial").focus();
        return false;
    }
    
    if($("#txt_cliente_nombreContactoContable").val() =="")
    {
        FuncionErrores(51); 
        $("#txt_cliente_nombreContactoContable").focus();
        return false;
    }
    
    if($("#txt_cliente_cargoContactoContable").val() =="")
    {
        FuncionErrores(52); 
        $("#txt_cliente_cargoContactoContable").focus();
        return false;
    }
    
    if($("#txt_cliente_celularContactoContable").val() =="")
    {
        FuncionErrores(53); 
        $("#txt_cliente_celularContactoContable").focus();
        return false;
    }
    
    if($("#txt_cliente_fijoContactoContable").val() =="")
    {
        FuncionErrores(54); 
        $("#txt_cliente_fijoContactoContable").focus();
        return false;
    }
    
    if($("#txt_cliente_emailContactoContable").val() =="")
    {
        FuncionErrores(55); 
        $("#txt_cliente_emailContactoContable").focus();
        return false;
    }
            
         Clientes(id);
    }


