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
  });
  $('#mdl-finalizar').on({
    'show.bs.modal': function (event) {
      const modal = $(this);
      modal.find('#finalizar-codigo').val(event.relatedTarget.dataset.codigo);
    },
    'hidden.bs.modal': function () {
      const modal = $(this);
      modal.find('.btn-success').removeAttr('disabled');
      modal.find('form')[0].reset();
    }
  });
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

function flash(obj, classNew, classOld) {
  const promises = [];
  for (let i = 4; i !== 0; i--) {
    promises[i] = setTimeout(function () {
      if (i % 2 === 0)
        obj.addClass(classNew).removeClass(classOld);
      else
        obj.addClass(classOld).removeClass(classNew);
    }, i * 350);
  }
  return Promise.all(promises);
}

function buscaComandas(mesa) {
  return $('#div-comandas').load('/mesas/' + mesa, function () {
    $('#mesa').text('Mesa ' + mesa).addClass('list-group-item-primary').removeClass('list-group-item-light');
    const btn = $('#btn-comandas').removeClass('disabled');
    //se só tiver uma única comanda
    const comanda = $(this).find('section').data('comanda');
    if (comanda)
    //carrega
      buscaComanda(comanda);
    else
    //destaca o botão
      flash(btn, 'btn-outline-primary', 'btn-primary');
    swal.close()
  });
}

function atualizaComandas() {
  return buscaComandas($('#mesa').text().split(' ')[1])
}

function buscaComanda(comanda) {
  if (comanda === null)
    return;
  const promises = [];
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
      }, {
        targets: 3,
        className: 'text-right'
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
  const btnStatus = '<div class="btn-group dropleft">\n  <button class="btn btn-sm dropdown-toggle btn-outline-dark"' +
    ' type="button" data-toggle="dropdown" id="btn-status">\n    Solicitado\n  </button>\n  <div class="dropdown-menu py-1">\n    <button type="button" class="px-2 btn-sm dropdown-item" data-status="S" onclick="atualizaPedido(this)">\n      Solicitado\n    </button>\n    <button type="button" class="px-2 btn-sm dropdown-item" data-status="P" onclick="atualizaPedido(this)">Preparando\n    </button>\n    <button type="button" class="px-2 btn-sm dropdown-item text-success" data-status="E" onclick="atualizaPedido(this)">\n      Entregue\n    </button>\n    <button type="button" class="px-2 btn-sm dropdown-item text-danger" data-status="C" onclick="atualizaPedido(this)">\n      Cancelado\n    </button>\n  </div>\n</div>';
  let dt = $('#tbl-pedidos').DataTable();
  let form = $('#form-produto');
  let produto = form.find('#cod-prod');
  let desc = form.find('#produto');
  let quant = form.find('#quant');
  let data = {quantidade: quant.val(), produto: {codigo: produto.val()}, comanda: {codigo: $('#comanda').text()}};
  //adiciona na tabela
  let row = dt.row.add([null, desc.val(), quant.val(), btnStatus]).draw();
  row.node().classList.add('font-italic');
  toast_carregando({title: 'Salvando...'});
  //envia os dados
  return $.post({
    url: '/pedidos/adicionar',
    data: JSON.stringify(data),
    dataType: 'json',
    row: row,
    form: form[0]
  }).done(function (data) {
    this.form.reset();
    this.row.cell().data(data.codigo).draw();
    let node = this.row.node();
    node.classList.remove("font-italic");
    node.dataset.pedido = data.codigo;
    toast({title: 'Pedido salvo', type: 'success'});
  }).always(function () {
    $('#btn-addProd').attr('disabled', true);
  });
}

function atualizaPedido(button) {
  function converteStatus(key) {
    switch (key) {
      case 'S':
        return {desc: 'Solicitado', class: 'btn-outline-dark'};
      case 'P':
        return {desc: 'Preparando', class: 'btn-outline-dark'};
      case 'E':
        return {desc: 'Entregue', class: 'btn-outline-success'};
      case 'C':
        return {desc: 'Cancelado', class: 'btn-outline-danger'};
    }
  }

  const status = button.dataset.status;
  const div = $(button);
  const btn = div.closest('.btn-group').find('#btn-status').attr('disabled', true);
  const row = div.closest('tr');
  row.addClass('font-italic');
  toast_carregando({title: 'Salvando...'});
  //envia os dados
  return $.post({
    url: '/pedidos/status',
    data: JSON.stringify({codigo: row.data('pedido'), status: status}),
    dataType: 'json',
    row: row,
    btn: btn
  }).done(function (data) {
    this.row.removeClass('font-italic');
    const situacao = converteStatus(data.status);
    this.btn.removeClass('btn-outline-dark btn-outline-success btn-outline-danger').addClass(situacao.class).removeAttr('disabled').html(situacao.desc);
    toast({title: 'Pedido atualizado', type: 'success'});
  }).always(function () {
    this.btn.removeAttr('disabled');
  });
}

function finalizaComanda() {
  const modal = $('#mdl-finalizar');
  modal.find('.btn-success').attr('disabled', true);
  const btnCancel = modal.find('.btn-light');
  const form = modal.find('#form-finalizar');
  const ajax = $.post('/comandas/status', getFormData(form), 'json')
    .done(function () {
      Promise.resolve(atualizaMesas()).then(function () {
        //espera atualiza as mesas (evita problemas no modal)
        setTimeout(function () {
          $('.modal').modal('hide');
          $('#mesa').text('Selecione a mesa').addClass('list-group-item-light').removeClass('list-group-item-primary');
          $('#btn-comandas').addClass('disabled');
          $('#content').addClass('d-none');
          $('#div-info').addClass('d-none');
          toast({title: 'Comanda finalizada', type: 'success'});
        }, 250);
      })
    });
  btnCancel.click(function () {
    ajax.abort();
    swal.close();
  });
  toast_carregando({title: 'Salvando...'});
  return false;
}
