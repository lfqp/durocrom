function VerificaRut(rut) {
   
    if (fnc_trim(rut) != '' && rut.toString().indexOf('-') > 0) {
        var caracteres = new Array();
        var serie = new Array(2, 3, 4, 5, 6, 7);
        var dig = rut.toString().substr(rut.toString().length - 1, 1);
        rut = rut.toString().substr(0, rut.toString().length - 2);

        for (var i = 0; i < rut.length; i++) {
            caracteres[i] = parseInt(rut.charAt((rut.length - (i + 1))));
        }

        var sumatoria = 0;
        var k = 0;
        var resto = 0;

        for (var j = 0; j < caracteres.length; j++) {
            if (k == 6) {
                k = 0;
            }
            sumatoria += parseInt(caracteres[j]) * parseInt(serie[k]);
            k++;
        }

        resto = sumatoria % 11;
        dv = 11 - resto;

        if (dv == 10) {
            dv = "K";
        }
        else if (dv == 11) {
            dv = 0;
        }

        if (dv.toString().trim().toUpperCase() == dig.toString().trim().toUpperCase())
            return true;
        else
            return false;
    }
    else {
        return false;
    }
}

function fnc_trim(data){
    return data.replace(/^\s+|\s+$/g, '');
}

  function validarSiNumero(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8){
        return true;
    }
        
    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
  }
  
    function validarSiDecimal(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8){
        return true;
    }
        
    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9-,-.]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
  }
  
function separadorDeMiles(data) {
    var valor = "";
    var cont = 0;
    var subvalor = "";
    if (data.length == 0 || data.length == 1 || data.length == 2 || data.length == 3) {
        valor = data;
        return valor;
    }
    
    for (var j = data.length; j >= 1; j--) {
        cont = cont + 1;
        subvalor = data.substr(j-1, 1);
        valor = subvalor + valor;
        
        if (cont === 3 && j > 1) {
            cont = 0;
            valor = "." + valor;
        }
    }
    return valor;
  }
  
function quitarSeparado(data) {
    var valor = "";
    valor = data.replace(".","");
    return valor
}