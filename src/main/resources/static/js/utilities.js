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
    $(e.currentTarget).find('.dataTables_empty').html('Erro ao obter dados');
    const mensagem = message.split(' - ')[1];
    swal({
      title: 'Erro ao obter dados',
      text: (!techNote ? 'Mensagem' : 'DataTables ' + techNote) + ': ' + mensagem,
      type: 'error',
      showCancelButton: false,
      confirmButtonColor: '#DD6B55',
      confirmButtonText: 'OK'
    });
    console.log(message);
  });
  //máscaras
  $('.monetario').mask('0.000,00', {reverse: true});
});

//tradução do DataTables
const dataTablesTranslate = {
  sEmptyTable: 'Nenhum registro encontrado',
  sInfo: 'Mostrando de _START_ até _END_ de _TOTAL_ registros',
  sInfoEmpty: 'Mostrando 0 até 0 de 0 registros',
  sInfoFiltered: '(Filtrados de _MAX_ registros)',
  sInfoPostFix: '',
  sInfoThousands: '.',
  sLengthMenu: '_MENU_ resultados por página',
  sLoadingRecords: 'Carregando...',
  sProcessing: 'Processando...',
  sZeroRecords: 'Nenhum registro encontrado',
  sSearch: 'Pesquisar',
  oPaginate: {
    sNext: 'Próximo',
    sPrevious: 'Anterior',
    sFirst: 'Primeiro',
    sLast: 'Último'
  },
  oAria: {
    sSortAscending: ': Ordenar colunas de forma ascendente',
    sSortDescending: ': Ordenar colunas de forma descendente'
  }
};

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

function Grupos() {
  //ordena os itens de uma lista
  function ordenaLista(list) {
    //cria uma lista de itens e ordena
    const items = list.children('button').get();
    items.sort(function (a, b) {
      const keyA = $(a).text();
      const keyB = $(b).text();
      if (keyA < keyB) return -1;
      if (keyA > keyB) return 1;
      return 0;
    });
    //remove os itens e adiciona na ordem correta
    $.each(items, function (i, child) {
      list.append(child);
    });
  }

  $('#grupo').on('change', function () {
    editar(this.value);
    if (getConfirmarSaida())
      this.value = '';
  });
  $('#nome').on('change', function () {
    setConfirmarSaida(true)
  });
  let permissoes = $('#editar-permissoes').val().split(',').map(function (item) {
    return parseInt(item);
  }).filter(value => {
    return Number.isInteger(value)
  });

  return {
    ativas: new Set(permissoes), grupo: $('#permissoes-grupo'), todas: $('#permissoes-todas'),
    alterna: function (item) {
      setConfirmarSaida(true);
      const obj = $(item);
      const codigo = obj.data('codigo');
      if (this.ativas.has(codigo)) {
        //está ativa
        this.ativas.delete(codigo);
        this.todas.append(obj);
        ordenaLista(this.todas);
      } else {
        //não está ativa
        this.ativas.add(codigo);
        this.grupo.append(obj);
        ordenaLista(this.grupo);
      }
    }
  }
}

//confirma antes de sair da página
function getConfirmarSaida() {
  return window.onbeforeunload != null
}

function setConfirmarSaida(confirmar) {
  if (confirmar)
    window.onbeforeunload = function () {
      return true;
    };
  else
    window.onbeforeunload = null
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

const swal_carregando = swal.mixin({
  title: 'Carregando...',
  onOpen: () => {
    swal.showLoading()
  },
  showConfirmButton: false,
  allowOutsideClick: false,
  width: '16em'
});