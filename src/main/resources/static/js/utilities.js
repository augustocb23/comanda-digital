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

//exibe uma mensagem de erro para uma requisição Ajax
function erroAjaxQuery(header, voltar) {
    console.log(header);
    //testa se não foi apenas cancelada (status 0)
    if (header.status)
        swal({
            title: 'Erro ao processar requisição',
            text: 'HTTP ' + header.status + ': ' + decode(header.statusText),
            type: 'error',
            showCancelButton: false,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: voltar ? "Voltar" : "OK"
        }).then(function () {
            //volta para a página anterior
            if (voltar)
                window.history.back();
        });
}

function digitarData(title = 'Data de nascimento', destino = '#nasc', modal = '#modal_atleta') {
    const modal_ = $(modal);
    modal_.modal('hide');
    swal({
        title: title,
        input: 'text',
        inputAttributes: {
            id: 'data_swal'
        },
        inputAutoTrim: true,
        inputPlaceholder: 'DD/MM/AAAA',
        inputValidator: (value) => {
            return new Promise((resolve) => {
                if (value) {
                    let data = value.split('/');
                    if (data[0] > 31 || data[1] > 12 || data[2].length < 4)
                        resolve('Insira uma data válida');
                    else
                        resolve()
                } else {
                    resolve('Insira uma data')
                }
            })
        },
        onOpen: () => {
            $('#data_swal').mask('00/00/0000');
        },
        onClose: () => {
            modal_.modal('show');
        },
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then(function (input) {
        if (input.value) {
            const destino_ = $(destino);
            destino_[0].type = 'date';
            let data = input.value.split('/');
            destino_.val(data[2] + '-' + data[1] + '-' + data[0]);
        }
    });
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
    html: '<h1><i class="fas fa-spinner fa-spin text-primary"></i></h1>',
    showConfirmButton: false,
    allowOutsideClick: false,
    width: '16em'
});