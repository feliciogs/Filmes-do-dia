# Filme do Dia

Aplicativo Android desenvolvido em Kotlin utilizando Jetpack Compose e arquitetura MVVM, que consome a API do TMDB para recomendar filmes diariamente conforme os gostos e gêneros do usuário. Utiliza Firebase Authentication e Firestore para gerenciar usuários e salvar o histórico de filmes assistidos.

---

## Arquitetura e Tecnologias

- **Linguagem:** Kotlin  
- **UI:** Jetpack Compose para interface declarativa e responsiva  
- **Arquitetura:** MVVM (Model-View-ViewModel) para separação de responsabilidades e fácil testabilidade  
- **Gerenciamento de estado:** StateFlow / LiveData no ViewModel  
- **Rede:** Retrofit com Gson para consumo da API TMDB  
- **Persistência:** Firebase Firestore para armazenamento dos filmes assistidos do usuário  
- **Autenticação:** Firebase Authentication (e-mail/senha)  
- **Injeção de dependências:** Koin
- **Controle de navegação:** Navigation Compose (se implementado)  

---

## Fluxo principal

1. **Autenticação:** Usuário faz login ou cadastro via Firebase Auth  
2. **Requisição API:** ViewModel consulta a API TMDB via Retrofit para obter filmes por gênero  
3. **Exibição:** Composables exibem lista de filmes, cards com imagens, títulos e notas  
4. **Marcar como assistido:** Usuário clica no filme para marcar; o app salva esse status no Firestore ligado ao UID do usuário  
5. **Perfil:** Tela de perfil lista os últimos 5 filmes assistidos, com opção de “Ver mais” para tela completa  
6. **Sincronização:** A cada abertura, o app verifica no Firestore os filmes assistidos e atualiza a UI  

---

## Estrutura do projeto

com.fenixgs.filmedodia/
├── data/ # Modelos, DTOs e repositórios
│ ├── api/ # Definições Retrofit, DTOs de resposta
│ ├── firebase/ # Classes para integração com Firestore
│ └── repository/ # Implementação dos repositórios
├── di/ # Módulos de injeção de dependência (se usar Hilt)
├── ui/ # Composables e telas
│ ├── components/ # Componentes de UI reutilizáveis
│ ├── screens/ # Telas principais (home, perfil, assistidos)
├── viewmodel/ # ViewModels com lógica de negócio e estado
├── navigation/ # Controle da navegação (se usar Navigation Compose)
└── utils/ # Funções utilitárias, extensões e helpers


---

## Configurações importantes

- **API Key TMDB:** Configurar sua chave no arquivo `local.properties` ou diretamente no Retrofit  
- **Firebase:** Configurar projeto no Firebase Console, baixar `google-services.json` e adicionar ao projeto  
- **Dependências:** Retrofit, Gson, Firebase Auth, Firebase Firestore, Jetpack Compose, Kotlin Coroutines, StateFlow  

---

## Como rodar

```bash
git clone https://github.com/seu-usuario/filme-do-dia.git


