# LITTLE VENTURE F712
TRABALHO PARA DISCIPLINA DE PROGRAMAÇÃO DE SISTEMAS - COMPUTAÇÃO - UFPEL
Grupo: Dauan Ghisleni Zolinger (líder), Laura Quevedo (vice-líder), Cássio Fehlberg, Darlei Schmegel, Heitor Matozo, Letícia Garcez e Miguel Gomes.
Entrega 1: Simulador de Sistema Computacional Hipotético (apresentação dispoível em https://youtu.be/oJINPRXeoCk).
Entrega 2: Sistema de Programação para um Computador Hipotético.

## FUNCIONAMENTO
   Interface temática e bastante intuitiva.
   Iniciando a execução, o usuário é convidado a inserir seu nome e logo escolher se quer inserir arquivos ou criá-los.
   Escolhida a opção de criar, pode-se programar ali mesmo na interface (instruções de montagem e de máquina).
   Inserindo, o código já vai abrir na janela de montagem.
   'Processar macros' para acionar o Processador de Macros e expandir as suas chamadas (.txt -> .asm).
   'Montar' para acionar o Montador e montar seus arquivos .lst e .obj.
   'Ligar' para gerar o código executável com base nos .objs (.objs -> .hpx).
   'Carregar' para inserir o programa em binário (.hpx) inicializando a máquina (entrega 1).
   O controle da execução do código se dá através da interface gráfica onde o usuário pode optar pela opção RUN (que executa o código sem pausas), ou pela opção STEP (que faz a execução passo a passo). Após o fim da execução do código, o reset pode ser acionado e uma das opções previamente citadas pode ser novamente utilizada para executar o código novamente. Na interface também podem ser acompanhados todos os valores dos registradores durante a execução do programa.
   *Dois arquivos de teste disponíveis na raiz do projeto, nomeados como cod1.txt e cod2.txt.

## CRITÉRIOS DE NOMECLATURA

   ### Funções e métodos
   A primeira palavra do método é iniciada com letra minúscula e as demais palavras, se existirem, terão sua primeira letra maiúscula, sendo que não há qualquer tipo de separação entre as palavra (traço,underline,etc). Sua nomeclatura deve ser descritiva, de modo que possa ser observado pelo seu cabeçalho o que a função faz. Os métodos getters e setters devem começar por get ou set, respectivamente seguido pelo nome da variável a ser modificada.
   Ex: *funcaoFazTalCoisa*, *getVariavel*, *setVariavel*
   
   ### Classes
   A primeira palavra da classe é iniciada com letra maiúscula e as demais palavras, se existirem, terão sua primeira letra maiúscula, sendo que não há qualquer tipo de separação entre as palavra (traço,underline,etc).
   Ex: *Classe*, *ClasseFazTalCoisa*

   ### Variáveis
   A primeira palavra do método é iniciada com letra minúscula e as demais palavras, se existirem, terão sua primeira letra maiúscula, sendo que não há qualquer tipo de separação entre as palavra (traço,underline,etc). Devem ter nomes descritivos de modo que fique claro o que aquela variável guarda.
   Ex: *voltas*, *tempoDecorrido*

   ### Constantes 
   Devem ser escritas sempre em letras maísculas, usando underscore para separar as palavras.
   Ex: *CONSTANTE*, *CONSTANTE_DE_TEMPO*
