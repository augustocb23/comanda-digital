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
  const ajax = $.post('/comandas/salvar', getFormData(form), function (data) {
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

function atualizaMesas() {
  $('#div-mesas').load('/mesas', function () {
    swal.close()
  });
}

function buscaComandas(mesa) {
  $('#div-comandas').load('/mesas/' + mesa, function () {
    $('#mesa').text('Mesa ' + mesa).addClass('list-group-item-primary').removeClass('list-group-item-light');
    $('#btn-comandas').removeClass('disabled');
    swal.close()
  });
}

function buscaComanda(comanda) {
  $('#div-comanda').load('/comandas/' + comanda, function () {
    $('#content').removeClass('d-none');
    $('.alert').alert('close');
    autoCompleteProduto();
    swal.close();
    $('#tbl-pedidos').DataTable({
      dom: 't',
      //tradução
      language: {
        url: '//cdn.datatables.net/plug-ins/1.10.16/i18n/Portuguese-Brasil.json'
      },
      paging: false,
      columnDefs: [{
        targets: 0,
        type: 'num'
      }]
    });
  });
}

function atualizaComandas() {
  buscaComandas($('#mesa').text().split(' ')[1])
}

function autoCompleteProduto() {
  const btn = $('#btn-addProd');
  const codigo = $('#cod-prod');
  $('#produto').autocomplete({
    delay: 400,
    source: function (request, response) {
      const fa = $('#fa-addProd');
      codigo.val(null);
      fa.removeClass('fa-plus').addClass('fa-hourglass-half');
      btn.attr('disabled', true);
      jQuery.ajax({
        url: "produtos/buscar",
        data: {term: removeAcentos(request.term)},
        dataType: "json",
        success: function (data) {
          response(data);
          fa.addClass('fa-plus').removeClass('fa-hourglass-half');
        }
      })
    },
    select: function (e, ui) {
      /** o código do item, recebido através do JSON
       * @property {int} item.codigo
       */
      $('#quant').focus();
      codigo.val(ui.item.codigo);
      btn.removeAttr('disabled');
    }
  });
}

function blurProduto() {
  if (!$('#cod-prod').val()) {
    $('#produto').val(null);
    $('#btn-addProd').attr('disabled', true);
  }
}

function adicionaProduto() {
  let dt = $('#tbl-pedidos').DataTable();
  let produto = $('#cod-prod');
  let desc = $('#produto');
  let quant = $('#quant');
  let data = {quantidade: quant.val(), produto: produto.val(), comanda: $('#comanda').text()};
  //adiciona na tabela
  let row = dt.row.add([null, desc.val(), quant.val(), 'S']).draw();
  row.node().classList.add("font-italic");
  swal_carregando({title: 'Salvando...'});
  //envia os dados
  $.post('/pedidos/adicionar', JSON.stringify(data), function (data) {
    row.cell.data(data.codigo);
    row.node().classList.remove("font-italic");
    toast({title: 'Pedido salvo', type: 'success'});
  }, 'json').always(function () {
    produto.val(null);
    desc.val(null);
    quant.val(1);
    $('#btn-addProd').attr('disabled', true);
  });
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