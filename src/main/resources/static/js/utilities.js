//CONFIGURAÇÕES PARA AJAX
$(document).ready(function () {
  //salva o token de autenticação
  const token = $("meta[name='_csrf']").attr("content");
  const header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function (e, xhr) {
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
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
});

function editar(codigo) {
  $('#codObj').val(codigo);
  $('#formEdit').submit();
}

function salvaComanda() {
  const modal = $('#mdl-editar');
  const form = modal.find('form');
  const btnSalvar = modal.find('.btn-success').attr('disabled', true);
  const btnCancel = modal.find('.btn-light');
  const ajax = $.post('/comandas/salvar', JSON.stringify(getFormData(form)), function (data) {
    toast({title: 'Comanda salva', type: 'success'})
  }, 'json').always(function () {
    btnSalvar.removeAttr('disabled');
    $('.modal').modal('hide');
  });
  btnCancel.click(function () {
    ajax.abort();
    swal.close();
  });
  toast_carregando({title: 'Salvando...'});
  return false;
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
  return indexed_array;
}

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