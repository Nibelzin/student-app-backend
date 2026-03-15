# Documentação do TCC — Student App

Documentação do Trabalho de Conclusão de Curso elaborada em LaTeX,
seguindo a **ABNT NBR 14724:2011**, com a classe **abnTeX2**.

## Estrutura

```
doc/
  main.tex                   ← Documento principal (preencha os dados do autor aqui)
  referencias.bib            ← Referências bibliográficas (BibTeX)
  README.md                  ← Este arquivo
  capitulos/
    01-introducao.tex
    02-referencial-teorico.tex
    03-metodologia.tex
    04-desenvolvimento.tex
    05-resultados.tex
    06-conclusao.tex
```

## Instalação do LaTeX no Windows

### Opção 1 — MikTeX (recomendado para Windows)

1. Acesse <https://miktex.org/download> e baixe o instalador
2. Execute o instalador e siga o assistente
3. Marque **"Install missing packages on-the-fly: Yes"** durante a instalação
4. Após instalar, abra o **MikTeX Console** e clique em **Update** para atualizar os pacotes

### Opção 2 — TeX Live (multiplataforma)

1. Acesse <https://tug.org/texlive/acquire-netinstall.html>
2. Baixe e execute `install-tl-windows.exe`
3. Selecione "Full scheme" para instalar todos os pacotes (~7 GB)

### Editor recomendado

- **TeXstudio** — <https://www.texstudio.org/> (open source, integrado com MikTeX/TeX Live)
- **VS Code** + extensão **LaTeX Workshop**

## Como compilar

### Pelo TeXstudio
1. Abra `main.tex`
2. Pressione **F5** (ou clique no botão Build & View)

### Pela linha de comando (no diretório `doc/`)
```bash
# Compilação completa (necessário para referências e sumário)
pdflatex main.tex
bibtex main
pdflatex main.tex
pdflatex main.tex
```

O arquivo `main.pdf` será gerado.

## Preenchimento obrigatório

Abra `main.tex` e preencha os campos marcados com `[...]`:

| Campo              | Exemplo                        |
|--------------------|--------------------------------|
| `\autor`           | `João da Silva`                |
| `\local`           | `São Paulo`                    |
| `\data`            | `2026`                         |
| `\orientador`      | `Prof. Dr. Nome do Orientador` |
| `\instituicao`     | Nome da universidade           |
| `\preambulo`       | Nome do curso e departamento   |
| Folha de aprovação | Membros da banca               |

## Pacote abnTeX2

O abnTeX2 é a classe LaTeX oficial para normas ABNT. Ele implementa
automaticamente formatação de:

- Capa e folha de rosto
- Folha de aprovação
- Resumo e abstract
- Sumário, listas de figuras e tabelas
- Referências no formato ABNT (autor-data ou numérico)
- Apêndices e anexos

Documentação: <https://www.abntex.net.br/>

## Dicas

- Use `\cite{chave}` para citação indireta: (AUTOR, ANO)
- Use `\citeonline{chave}` para citação direta no texto: Autor (ANO)
- Use `\autoref{label}` para referencias cruzadas automáticas
- Figuras devem ter `\fonte{}` abaixo da legenda (exigência ABNT)
- Tabelas devem ter `\fonte{}` abaixo da legenda (exigência ABNT)
