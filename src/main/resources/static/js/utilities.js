//CONFIGURAÇÕES PARA AJAX
$(document).ready(function () {
  $(document).ajaxSend(function (e, xhr, settings) {
    if (settings.type === 'POST') {
      //salva o token de autenticação
      const token = $("meta[name='_csrf']").attr("content");
      const header = $("meta[name='_csrf_header']").attr("content");
      xhr.setRequestHeader(header, token);
      xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    } else if (settings.dataType === 'html') {
      toast_carregando({title: 'Carregando...'});
    }
  });
  //mensagens de erro
  $(document).ajaxError(function (event, jqXHR, settings) {
    console.log(jqXHR);
    console.log(settings);
    let title = 'Erro ao processar requisição';
    let message = jqXHR.statusText;
    //se for json
    if (settings.dataType === 'json') {
      /** @var jqXHR.responseJSON */
      const response = jqXHR.responseJSON;
      title = 'Erro: ' + response.error;
      message = response.message;
    }
    //testa se não foi apenas cancelada (status 0)
    if (jqXHR.status)
      swal({
        title: title,
        text: 'HTTP ' + jqXHR.status + ': ' + message,
        type: 'error',
        showCancelButton: false,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "OK"
      });
  });
  //erros do DataTables
  $.fn.dataTable.ext.errMode = 'none';
  $('table').on('error.dt', function (e, settings, techNote, message) {
    $('.dataTables_empty').html('Erro ao obter dados');
    swal({
      title: 'Erro ao obter dados',
      text: 'DataTables error ' + techNote,
      type: 'error',
      showCancelButton: false,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "OK"
    });
    console.log(message);
  });
  //máscaras
  $('.monetario').mask('0.000,00', {reverse: true});
});

function editar(codigo) {
  $('#codObj').val(codigo);
  $('#formEdit').submit();
}

function removeAcentos(stringComAcento) {
  let string = stringComAcento;
  const mapaAcentosHex = {
    a: /[\xE0-\xE6]/g,
    A: /[\xC0-\xC6]/g,
    e: /[\xE8-\xEB]/g,
    E: /[\xC8-\xCB]/g,
    i: /[\xEC-\xEF]/g,
    I: /[\xCC-\xCF]/g,
    o: /[\xF2-\xF6]/g,
    O: /[\xD2-\xD6]/g,
    u: /[\xF9-\xFC]/g,
    U: /[\xD9-\xDC]/g,
    c: /\xE7/g,
    C: /\xC7/g,
    n: /\xF1/g,
    N: /\xD1/g
  };
  for (let letra in mapaAcentosHex) {
    const expressaoRegular = mapaAcentosHex[letra];
    string = string.replace(expressaoRegular, letra);
  }
  return string;
}

//serializa todos os campos de um formulário
function getFormData(form) {
  const array = form.serializeArray();
  let indexed_array = {};
  $.map(array, function (n) {
    //verifica se é string vazia ou null
    if (n['value'] === 'null' || !n['value'].trim().length)
      n['value'] = null;
    //indexa o vetor
    indexed_array[n['name']] = n['value'];
  });
  return JSON.stringify(indexed_array);
}

//SWEET ALERTS
const toast = swal.mixin({
  toast: true,
  position: 'top',
  showConfirmButton: false,
  timer: 4000
});

const toast_carregando = swal.mixin({
  toast: true,
  position: 'top',
  showConfirmButton: false,
  onOpen: () => {
    swal.showLoading()
  },
  timer: false
});

const swal_centro = swal.mixin({
  showConfirmButton: false,
  timer: 2500
});

const swal_carregando = swal.mixin({
  title: 'Carregando...',
  onOpen: () => {
    swal.showLoading()
  },
  showConfirmButton: false,
  allowOutsideClick: false,
  width: '16em'
});