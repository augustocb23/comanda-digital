$(document).ready(function () {
  $('#mdl-editar').on({
    'show.bs.modal': function (event) {
      const data_mesa = event.relatedTarget.dataset.mesa;
      const modal = $(this);
      if (data_mesa === 'true') {
        let mesa = $('#mesa').text().split(' ')[1];
        modal.find('#editar-mesa').val(parseInt(mesa)).attr('readonly', true);
      } else if (parseInt(data_mesa)) {
        modal.find('#editar-mesa').val(parseInt(data_mesa));
        modal.find('#editar-nome').val(event.relatedTarget.dataset.nome);
        modal.find('#editar-codigo').val(event.relatedTarget.dataset.codigo);
        modal.find('.modal-header h5').text('Editar comanda');
      }
    },
    'hidden.bs.modal': function () {
      const modal = $(this);
      modal.find('.modal-header h5').text('Nova comanda');
      modal.find('#editar-mesa').removeAttr('readonly');
      modal.find('.btn-success').removeAttr('disabled');
      modal.find('form')[0].reset();
    }
  })
});

function salvaComanda() {
  const modal = $('#mdl-editar');
  const form = modal.find('form');
  modal.find('.btn-success').attr('disabled', true);
  const btnCancel = modal.find('.btn-light');
  const ajax = $.post('/comandas/salvar', getFormData(form), 'json')
    .done(function (json) {
      let promises = [];
      promises[0] = buscaComanda(json.codigo);
      promises[1] = buscaComandas(json.mesa);
      promises[2] = atualizaMesas();
      Promise.all(promises).then(function () {
        toast({title: 'Comanda salva', type: 'success'});
        $('.modal').modal('hide');
      });
    });
  btnCancel.click(function () {
    ajax.abort();
    swal.close();
  });
  toast_carregando({title: 'Salvando...'});
  return false;
}

function atualizaMesas() {
  return $('#div-mesas').load('/mesas', function () {
    swal.close()
  });
}

function buscaComandas(mesa) {
  return $('#div-comandas').load('/mesas/' + mesa, function () {
    $('#mesa').text('Mesa ' + mesa).addClass('list-group-item-primary').removeClass('list-group-item-light');
    $('#btn-comandas').removeClass('disabled');
    swal.close()
  });
}

function atualizaComandas() {
  return buscaComandas($('#mesa').text().split(' ')[1])
}

function buscaComanda(comanda) {
  let promises = [];
  promises[0] = $('#div-info').load('/comandas/info/' + comanda, function () {
    $(this).removeClass('d-none')
  });
  promises[1] = $('#div-comanda').load('/comandas/' + comanda, function () {
    $('#content').removeClass('d-none');
    $('.alert').alert('close');
    autoCompleteProduto();
    swal.close();
    $('#tbl-pedidos').DataTable({
      dom: 't',
      //tradução
      language: {sEmptyTable: "Nenhum pedido realizado"},
      paging: false,
      columnDefs: [{
        targets: 0,
        type: 'num'
      }]
    });
  });
  return Promise.all(promises)
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
        url: 'produtos/buscar',
        data: {term: removeAcentos(request.term)},
        dataType: 'json',
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

function adicionaPedido() {
  let dt = $('#tbl-pedidos').DataTable();
  let form = $('#form-produto');
  let produto = form.find('#cod-prod');
  let desc = form.find('#produto');
  let quant = form.find('#quant');
  let data = {quantidade: quant.val(), produto: {codigo: produto.val()}, comanda: {codigo: $('#comanda').text()}};
  //adiciona na tabela
  let row = dt.row.add([null, desc.val(), quant.val(), 'Solicitado']).draw();
  row.node().classList.add("font-italic");
  toast_carregando({title: 'Salvando...'});
  //envia os dados
  return $.post({
    url: '/pedidos/adicionar',
    data: JSON.stringify(data),
    dataType: 'json',
    row: row,
    form: form[0]
  }).done(function (data) {
    this.row.cell().data(data.codigo);
    this.row.node().classList.remove("font-italic");
    this.form.reset();
    toast({title: 'Pedido salvo', type: 'success'});
  }).always(function () {
    $('#btn-addProd').attr('disabled', true);
  });
}

function atualizaPedido(button, status) {
  //todo
  const item = $(button);
  const row = item.closest('tr');
  console.log(row.data('pedido'));
}