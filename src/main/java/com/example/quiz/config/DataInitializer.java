package com.example.quiz.config;

import com.example.quiz.model.Questao;
import com.example.quiz.repository.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestaoRepository questaoRepository;

    @Override
    public void run(String... args) {
        if (questaoRepository.count() == 20) {
            return;
        }

        questaoRepository.deleteAll();

        List<Questao> questoes = List.of(
                criarQuestao(
                        "Como identificar um e-mail de phishing direcionado ao setor administrativo?",
                        "O remetente usa domínio estranho e pede dados sensíveis com urgência.",
                        "O e-mail tem assinatura colorida.",
                        "O e-mail foi enviado em horário comercial.",
                        "O e-mail tem anexo em PDF.",
                        "A",
                        "Golpes de phishing costumam pressionar a vítima e imitar comunicações legítimas."
                ),
                criarQuestao(
                        "Qual é a melhor prática para proteger documentos corporativos sigilosos?",
                        "Salvar em pastas públicas para facilitar o acesso.",
                        "Usar controle de acesso e permissões por função.",
                        "Enviar por e-mail para todos da equipe.",
                        "Manter cópias soltas na área de trabalho.",
                        "B",
                        "Documentos sensíveis devem ter acesso restrito e rastreável."
                ),
                criarQuestao(
                        "Ao receber um anexo de planilha com dados de clientes, o que fazer antes de abrir?",
                        "Abrir imediatamente para agilizar o trabalho.",
                        "Verificar origem, necessidade e integridade do arquivo.",
                        "Encaminhar para um grupo maior.",
                        "Renomear o arquivo e abrir sem checar.",
                        "B",
                        "Arquivos com dados de clientes exigem validação antes de qualquer abertura."
                ),
                criarQuestao(
                        "Qual prática ajuda a evitar vazamento de informações em e-mails?",
                        "Usar CCO com contatos internos sem necessidade.",
                        "Revisar destinatários, anexos e conteúdo antes de enviar.",
                        "Encaminhar mensagens sem ler.",
                        "Usar sempre resposta para todos.",
                        "B",
                        "A revisão antes do envio reduz erros de destinatário e exposição de dados."
                ),
                criarQuestao(
                        "Qual atitude é mais segura ao acessar o sistema da empresa fora do escritório?",
                        "Usar Wi-Fi público sem proteção.",
                        "Acessar apenas por conexão confiável e, se possível, VPN.",
                        "Salvar a senha no navegador compartilhado.",
                        "Usar computador de terceiros sem cuidado.",
                        "B",
                        "Redes confiáveis e VPN reduzem o risco de interceptação de dados."
                ),
                criarQuestao(
                        "Qual é a melhor prática para senhas corporativas?",
                        "Usar a mesma senha em todos os sistemas.",
                        "Criar senhas longas, exclusivas e com MFA.",
                        "Compartilhar com a equipe por e-mail.",
                        "Usar o nome da empresa com o ano.",
                        "B",
                        "Senhas fortes e autenticação multifator elevam a proteção da conta."
                ),
                criarQuestao(
                        "O que fazer ao perceber um arquivo desconhecido em um pendrive?",
                        "Abrir para identificar o conteúdo.",
                        "Executar o arquivo para testar.",
                        "Não abrir e entregar ao suporte de TI.",
                        "Copiar para o computador principal.",
                        "C",
                        "Mídias desconhecidas podem conter malware e devem ser tratadas com cautela."
                ),
                criarQuestao(
                        "Qual medida protege melhor o computador de trabalho quando ele é deixado sozinho?",
                        "Deixar desbloqueado por poucos minutos.",
                        "Bloquear a tela sempre que sair da estação.",
                        "Fechar apenas o navegador.",
                        "Reduzir o brilho da tela.",
                        "B",
                        "Bloquear a tela evita acesso indevido a informações abertas."
                ),
                criarQuestao(
                        "Como reduzir o risco de fraude ao acessar sites corporativos?",
                        "Clicar em links recebidos sem conferir.",
                        "Verificar endereço, HTTPS e autenticidade do domínio.",
                        "Usar qualquer página que pareça igual.",
                        "Ignorar avisos do navegador.",
                        "B",
                        "Conferir domínio e conexão segura ajuda a evitar sites falsos."
                ),
                criarQuestao(
                        "Qual é a conduta correta ao receber uma solicitação urgente de alteração bancária por e-mail?",
                        "Alterar imediatamente para não atrasar o setor.",
                        "Confirmar por um canal oficial antes de executar.",
                        "Responder ao e-mail pedindo mais detalhes.",
                        "Enviar os dados para outra pessoa resolver.",
                        "B",
                        "Mudanças financeiras devem ser validadas fora do e-mail para evitar fraude."
                ),
                criarQuestao(
                        "O que deve ser feito com impressos contendo dados sensíveis após o uso?",
                        "Jogar no lixo comum.",
                        "Deixar sobre a mesa até o dia seguinte.",
                        "Destruir ou descartar em local apropriado.",
                        "Guardar em qualquer gaveta.",
                        "C",
                        "Documentos físicos com dados confidenciais precisam de descarte seguro."
                ),
                criarQuestao(
                        "Qual prática ajuda a evitar exposição de dados em reuniões virtuais?",
                        "Compartilhar a tela com tudo aberto.",
                        "Fechar arquivos não necessários e mostrar apenas o necessário.",
                        "Enviar a senha da reunião por mensagem aberta.",
                        "Gravar sem avisar ninguém.",
                        "B",
                        "Expor apenas o mínimo reduz risco de vazamento acidental."
                ),
                criarQuestao(
                        "O que fazer ao perceber que uma conta corporativa pode ter sido comprometida?",
                        "Esperar para ver se acontece algo mais.",
                        "Trocar a senha e acionar imediatamente a TI.",
                        "Postar um aviso no grupo geral.",
                        "Criar outra conta igual.",
                        "B",
                        "Resposta rápida reduz danos e permite contenção adequada."
                ),
                criarQuestao(
                        "Qual é o uso mais seguro do e-mail corporativo?",
                        "Enviar dados pessoais e documentos sem revisão.",
                        "Usar somente para assuntos de trabalho e com cuidado nas mensagens.",
                        "Repassar senhas para colegas pelo e-mail.",
                        "Abrir anexos de qualquer remetente.",
                        "B",
                        "O e-mail corporativo deve ser usado com critério e foco profissional."
                ),
                criarQuestao(
                        "Como proteger melhor documentos compartilhados em nuvem?",
                        "Deixar link público sem restrição.",
                        "Aplicar permissão de leitura, edição e expiração quando necessário.",
                        "Enviar o link por qualquer grupo aberto.",
                        "Permitir acesso a todos os usuários da internet.",
                        "B",
                        "Permissões corretas evitam acesso indevido e edição não autorizada."
                ),
                criarQuestao(
                        "O que é um sinal de possível página falsa de login?",
                        "O domínio tem erro de digitação ou aparência incomum.",
                        "A página abre rapidamente.",
                        "A página tem logo da empresa.",
                        "A página está em modo escuro.",
                        "A",
                        "Domínios parecidos e erros sutis são comuns em páginas de golpe."
                ),
                criarQuestao(
                        "Qual prática protege melhor contra perda de dados corporativos?",
                        "Não fazer backup.",
                        "Manter cópias de segurança periódicas em local seguro.",
                        "Salvar tudo apenas no computador local.",
                        "Copiar arquivos para dispositivos pessoais.",
                        "B",
                        "Backups seguros ajudam na recuperação após incidentes ou falhas."
                ),
                criarQuestao(
                        "Qual cuidado é importante ao usar mensageiros instantâneos no trabalho?",
                        "Enviar qualquer arquivo sem verificar o conteúdo.",
                        "Confirmar se a informação pode ser compartilhada naquele canal.",
                        "Copiar mensagens sigilosas para grupos externos.",
                        "Usar o chat para repassar senhas.",
                        "B",
                        "Nem toda informação pode circular em canais informais."
                ),
                criarQuestao(
                        "Como agir diante de um pedido de acesso fora do padrão?",
                        "Conceder para agilizar o processo.",
                        "Validar a necessidade e seguir a política de acesso mínimo.",
                        "Enviar a senha temporária por mensagem.",
                        "Compartilhar a conta de outro funcionário.",
                        "B",
                        "O princípio do menor privilégio limita exposição desnecessária."
                ),
                criarQuestao(
                        "O que ajuda a manter a segurança de documentos impressos no ambiente administrativo?",
                        "Deixar papéis em qualquer mesa.",
                        "Usar armários fechados e recolher documentos ao final do uso.",
                        "Levar documentos para áreas comuns.",
                        "Fotografar e compartilhar sem necessidade.",
                        "B",
                        "Documentos físicos também precisam de controle e armazenamento seguro."
                )
        );

        questaoRepository.saveAll(questoes);
    }

    private Questao criarQuestao(String enunciado,
                                 String opcaoA,
                                 String opcaoB,
                                 String opcaoC,
                                 String opcaoD,
                                 String respostaCorreta,
                                 String explicacao) {
        Questao questao = new Questao();
        questao.setEnunciado(enunciado);
        questao.setOpcaoA(opcaoA);
        questao.setOpcaoB(opcaoB);
        questao.setOpcaoC(opcaoC);
        questao.setOpcaoD(opcaoD);
        questao.setRespostaCorreta(respostaCorreta);
        questao.setExplicacao(explicacao);
        return questao;
    }
}
