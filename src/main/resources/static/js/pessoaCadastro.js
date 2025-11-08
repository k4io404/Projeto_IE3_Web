       /*----------------------------CONTROLES DE TABS------------------------------*/
       // Armazena as IDs de todas as abas para fácil referência
       const ALL_TABS = [
           'btn-autorizacoes', 'autorizacoes',
           'btn-veiculos', 'veiculos',
           'btn-casas', 'casas',
           'btn-servicos', 'servicos'
       ];

       // Mapeamento da lógica de negócio
       const TABS_TO_HIDE = {
           'MORADOR': ['btn-servicos', 'servicos'],
           'PRESTADOR': ['btn-casas', 'casas', 'btn-veiculos', 'veiculos'],
           'VISITANTE': ['btn-casas', 'casas', 'btn-servicos', 'servicos']
       };

       // Função simples para simular a funcionalidade de abas (TabPane)
       function openTab(evt, tabName) {
           var i, tabcontent, tablinks;
           let select;

           // Oculta todo o conteúdo das abas
           tabcontent = document.getElementsByClassName("tab-content");
           for (i = 0; i < tabcontent.length; i++) {
               tabcontent[i].style.display = "none";
               tabcontent[i].classList.remove("active");
           }

           // Remove a classe "active" de todos os botões visíveis
           tablinks = document.getElementById("tab-header").querySelectorAll("button:not([style*='none'])");
           for (i = 0; i < tablinks.length; i++) {
               tablinks[i].classList.remove("active");
           }

           // Mostra a aba atual e adiciona "active" ao botão
           const currentContent = document.getElementById(tabName);
           const currentButton = evt.currentTarget;

           if (currentContent) {
               currentContent.style.display = "block";
               currentContent.classList.add("active");
           }
           if (currentButton) {
               currentButton.classList.add("active");
           }

          select = document.getElementById("localAutorizacao");
           if (select && select.options.length <= 1) {
                carregarLocais(); // só busca se ainda não tiver sido carregado
           }

          select = document.getElementById("numeroCasa");
          if (select && select.options.length <= 1) {
             carregarCasas(); // só busca se ainda não tiver sido carregado
          }

          select = document.getElementById("moradorAssociadoField")
          if(select && select.options.length <= 1){
             carregarMoradores();
          }

          let input =  document.getElementById("servicoDataInicio");
          if(tabName == 'servicos' && input.value == ''){

              const hoje = new Date();

              const dia = hoje.getDate().toString().padStart(2, '0');
              const mes = (hoje.getMonth() + 1).toString().padStart(2, '0');
              const ano = hoje.getFullYear();
              const dataFormatada = `${ano}-${mes}-${dia}`;

              input.value = dataFormatada;
          }

       }

       /**
        * Aplica a lógica de esconder abas com base no tipo de pessoa selecionado.
        * Além disso, exibe/oculta os campos de Visitante/Prestador na aba 'Pessoa'.
        */
       function filterTabsAndFieldsByType(selectedValue) {

           // 1. Mostrar/Esconder Campos na aba 'Pessoa' (Lógica existente)
           const visitantePane = document.getElementById('visitantePane');
           const prestadorPane = document.getElementById('prestadorPane');

           if (visitantePane) visitantePane.style.display = 'none';
           if (prestadorPane) prestadorPane.style.display = 'none';

           if (selectedValue === 'VISITANTE' && visitantePane) {
               visitantePane.style.display = 'flex';
           } else if (selectedValue === 'PRESTADOR' && prestadorPane) {
               prestadorPane.style.display = 'flex';
           }

           // 2. Mostrar todas as abas (Reset)
           ALL_TABS.forEach(id => {
               const element = document.getElementById(id);
               if (element) {
                   element.style.display = ''; // Volta ao default (block ou flex/inline-block)
               }
           });

           // 3. Ocultar as abas específicas para o tipo selecionado
           const hiddenIds = TABS_TO_HIDE[selectedValue];
           if (hiddenIds) {
               hiddenIds.forEach(id => {
                   const element = document.getElementById(id);
                   if (element) {
                       element.style.display = 'none';
                   }
               });
           }

           // 4. Garante que a primeira aba visível seja selecionada após a mudança
           const tabHeader = document.getElementById("tab-header");
           if (tabHeader) {
               const firstVisibleButton = tabHeader.querySelector("button:not([style*='none'])");
               if (firstVisibleButton) {
                   // Simula o clique para garantir que a aba correta e seu conteúdo apareçam
                   firstVisibleButton.click();
               }
           }
       }

       // Garante que o estado inicial e o evento de mudança funcionem
       document.addEventListener("DOMContentLoaded", function() {
           const tipoPessoaSelect = document.getElementById('tipoPessoaChoiceBox');

           // Adiciona o listener para o SELECT
           if (tipoPessoaSelect) {
               tipoPessoaSelect.addEventListener('change', function() {
                   filterTabsAndFieldsByType(this.value);
               });
           }

           // Configura o estado inicial (dispara a lógica ao carregar a página)
           if (tipoPessoaSelect) {
               filterTabsAndFieldsByType(tipoPessoaSelect.value);
           } else {
                // Se o select não existir, garanta que a primeira aba abra
                document.querySelector(".tab-header button").click();
           }
       });

       function handleAnexarFoto() {
           alert('Ação de anexar foto');
       }


    let autorizacoes = [];
    let veiculos = [];
    let casas = [];
    let servicos = [];


    /*------------------------------FORMATAÇÕES DE INPUT-----------------------------*/
    function  formatarCpfEvent(e){
    var v = e.target.value.replace(/\D/g, ""); // Remove todos os caracteres não numéricos
      v = v.replace(/(\d{3})(\d)/g, "$1.$2"); // Adiciona ponto após o 3º dígito
      v = v.replace(/(\d{3})(\d{1,2})$/, "$1.$2"); // Adiciona ponto após o 3º dígito (segundo bloco)
      v = v.replace(/(\d{4})(\d{1,2})$/, "$1-$2"); // Adiciona traço após o 4º dígito (terceiro bloco)
      e.target.value = v;
    }

    function formatarTelefoneEvent(input){

      var valor = input.target.value.replace(/\D/g, "");  // Remove todos os caracteres não numéricos
      // Aplica a máscara de telefone
      if (valor.length > 2) {
        valor = `(${valor.substring(0, 2)}) ${valor.substring(2)}`;
      }
      if (valor.length > 10) {
        valor = `${valor.substring(0, 11)}-${valor.substring(11)}`;
      }
      input.value = valor;
    }

    /*-----------------------ADICIONAR ELEMENTOS NAS TABELAS-----------------------*/
    function adicionarAutorizacao(){
        const local = document.getElementById("localAutorizacao").value

         if(!local){
          alert("Preencha todos os campos");
          return;
         }

        if(autorizacoes.length == 0) {
            limparTabela('tabelaAutorizacoes');
        }
        else{
            if(autorizacoes.some(autorizacao => autorizacao.local == local)){
                alert("Local já inserido na tabela");
                return;
            }
        }

        autorizacoes.push({ local });

        const tbody = document.querySelector("#tabelaAutorizacoes tbody");
        const row = `<tr><td>${local}</td></tr>`;
        tbody.insertAdjacentHTML("beforeend", row);

        document.getElementById("localAutorizacao").value = "";
    }

    function adicionarVeiculo() {
        const placa = document.getElementById("txtPlaca").value;
        const modelo = document.getElementById("txtModelo").value;
        const cor = document.getElementById("txtCor").value;

        if(!placa ){
            alert("Preencha todos os campos obrigatórios");
            return
        }

        if(placa.length < 7){
            alert("Preencha a placa corretamente")
            return
        }

        if(veiculos.length == 0) {
            limparTabela('tabelaVeiculos');
        }
        else{
            if(veiculos.some(veiculo => veiculo.placa == placa)){
               alert("Placa já inserida na tabela");
               return
            }
        }

        veiculos.push({ placa, modelo,cor});

        const tbody = document.querySelector("#tabelaVeiculos tbody");
        const row = `<tr><td>${placa}</td><td>${modelo}</td><td>${cor}</tr>`;
        tbody.insertAdjacentHTML("beforeend", row);

        document.getElementById("txtPlaca").value = "";
        document.getElementById("txtModelo").value = "";
        document.getElementById("txtCor").value = "";
    }


    function adicionarCasa(){

           const endereco = document.getElementById("numeroCasa").value
           const relacionamento = document.getElementById("relacionamentoMorador").value

           if(!endereco || !relacionamento){
           alert("Preencha todos os campos");
           return;
           }
            console.log(endereco);

                if(casas.length == 0) {
                    limparTabela('tabelaCasas');
                }
                else{
                    if(casas.some(casa => casa.endereco == endereco)){
                        alert("Endereço já existente na tabela");
                        return
                    }
                }

                casas.push({ endereco, relacionamento });

                const tbody = document.querySelector("#tabelaCasas tbody");
                const row = `<tr><td>${endereco}</td><td>${relacionamento}</td></tr>`;
                tbody.insertAdjacentHTML("beforeend", row);

                document.getElementById("numeroCasa").value = "";
                document.getElementById("relacionamentoMorador").value = "";
    }

    function adicionarServico(){

               let dataInicio = document.getElementById("servicoDataInicio").value;
               let dataFim = document.getElementById("servicoDataFim").value;
               const tipoServico = document.getElementById("txtTipoServico").value;

               if(!dataInicio){
                   alert("Preencha todos os campos obrigatórios");
                   return;
               }

               let now = new Date().getTime();
               let timeStampDataInicio = new Date(dataInicio+'T00:00').getTime();

               if(timeStampDataInicio > now){
                    alert("Data de início inválida");
                    return;
               }

               if(!dataFim){
                    dataFim = "Não informado";
               }
               else{
                   let timeStampDataFim = new Date(dataFim+'T00:00').getTime();
                   if(timeStampDataInicio > timeStampDataFim){

                        alert("Data de fim do serviço incorreta");
                        return;
                    }
               }

                if(servicos.length == 0) {
                    limparTabela('tabelaServicos');
                }

                servicos.push({ dataInicio, dataFim, tipoServico });

                const tbody = document.querySelector("#tabelaServicos tbody");
                const row = `<tr><td>${dataInicio}</td><td>${dataFim}</td><td>${tipoServico}</td></tr>`;
                tbody.insertAdjacentHTML("beforeend", row);

                document.getElementById("servicoDataFim").value = "";
                document.getElementById("txtTipoServico").value = "";
    }

    function limparTabela(idTabela){

    var tabela = document.getElementById(idTabela);

    var tbody = tabela.getElementsByTagName('tbody')[0] || tabela;
        tbody.innerHTML = '';
    }


    /*-------------------Ao carregar a página ----------------------*/
    window.onload = function() {
        // Oculta a tela de carregamento
        const loaderOverlay = document.getElementById('loader-overlay');
        loaderOverlay.style.display = 'none';

        // Exibe o conteúdo principal
        const mainContent = document.getElementById('content');
        mainContent.classList.remove('hidden');
    };

    /*--------------------CAMPOS UTILIZADOS EM TAGS'S SELECT--------------------------*/
    async function carregarMoradores(){

        const select = document.getElementById("moradorAssociadoField");
        if (!select) return;

        try {
               const response = await fetch("/api/moradores");
               const moradores = await response.json();

               select.innerHTML = '<option value="">Selecione um morador</option>';
               moradores.forEach(morador => {
                        const option = document.createElement("option");
                        option.textContent = morador.nome;
                        select.appendChild(option);
                    });
        }catch (error) {
                    console.error("Erro ao carregar nome de moradores:", error);
                    select.innerHTML = '<option value="">Erro ao carregar</option>';
        }
    }

    async function carregarLocais() {
        const select = document.getElementById("localAutorizacao");
        if (!select) return;

        try {
            const response = await fetch("/api/locais");
            const locais = await response.json();

            select.innerHTML = '<option value="">Selecione um local</option>';
            locais.forEach(local => {
                const option = document.createElement("option");
                option.textContent = local.nome;
                select.appendChild(option);
            });
        } catch (error) {
            console.error("Erro ao carregar locais:", error);
            select.innerHTML = '<option value="">Erro ao carregar</option>';
        }
    }

    async function carregarCasas() {
            const select = document.getElementById("numeroCasa");
            if (!select) return;

            try {
                const response = await fetch("/api/casas");
                const casas = await response.json();

                select.innerHTML = '<option value="">Selecione um número</option>';
                casas.forEach(casa => {
                    const option = document.createElement("option");
                    option.textContent = casa.endereco;
                    select.appendChild(option);
                });
            } catch (error) {
                console.error("Erro ao carregar casas:", error);
                select.innerHTML = '<option value="">Erro ao carregar</option>';
            }
        }

        function erroAoValidarPessoa(){

             const nome = document.getElementById("nomeField").value;
             const cpf = document.getElementById("cpfField").value;
             const dataNascimento = document.getElementById("dataNascimentoPicker").value;

             if(!nome || !cpf || !dataNascimento){
                alert("Preencha todos os dados obrigatórios");
                return 1;
             }

             if(nome.length > 60 ){
                alert("Nome com quantidade de caracteres acioma do permitido");
                return 1;
             }

             if(cpf.length < 11){
                alert("Cpf preenchido de forma incorreta");
                return 1;
             }

             const now = new Date();
             const date = new Date(dataNascimento+'T00:00');

             if(date.getTime() > now.getTime()){
                alert("Data de nascimento inválida");
                return 1;
             }

             const pessoaEscolhida = document.getElementById("tipoPessoaChoiceBox").value
             if(pessoaEscolhida == "MORADOR"){

                 if(casas.length == 0){
                     alert("Nenhuma casa associada ao morador");
                     return 1;
                 }
             }
             else if(pessoaEscolhida == "VISITANTE"){

                 const moradorAssociado  = document.getElementById("moradorAssociadoField")
                 if(moradorAssociado.length == 0){
                    alert("Morador associado ao visitante não preenchido")
                    return 1;
                }
             }
             else if(pessoaEscolhida == "PRESTADOR"){


             }

            /*
             if(autorizacoes.length){
                alert("Nenhuma autorização foi selecionada ");
                return 1;
             }
            */

             return 0;
        }

        /*------------------------ENVIAR DADOS DE CADASTRO------------------------*/

        function enviarJson(endpoint, vetor){



        }

         function salvarCadastro() {


                  if(erroAoValidarPessoa(pessoa)){
                        return;
                   }

                   alert("Cadastro realizado com sucesso");

                   //const pessoaEscolhida = document.getElementById("tipoPessoaChoiceBox").value

              /*

                  fetch('/api/cadastro/pessoa', {
                      method: 'POST',
                      headers: {
                          'Content-Type': 'application/json', // Importante para o Spring Boot entender o tipo de dado
                      },
                      body: JSON.stringify(pessoa), // Converte o array de objetos em string JSON
                  })
                  .then(response => response.json())
                  .then(data => console.log('Sucesso:', data))
                  .catch((error) => console.error('Erro:', error));

                  */
         }



