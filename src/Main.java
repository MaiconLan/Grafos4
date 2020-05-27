import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String NAO_VISITADO = "Branco";
    private static final String VISITADO = "Cinza";

    public static void main(String[] args) throws CloneNotSupportedException {
        Grafo grafo = new Grafo();

        configuracoesIniciais(grafo);

        int opt = -1;

        while (opt != 0) {
            opt = Integer.parseInt(input(getMenu()));

            switch (opt) {
                case 1:
                    definirVertices(grafo);
                    break;

                case 2:
                    adicionarAresta(grafo);
                    break;

                case 3:
                    adicionarArestaDireto(grafo);
                    definirVerticeInicial(grafo);
                    break;

                case 4:
                    definirVerticeInicial(grafo);
                    break;

                case 5:
                    listaArestas(grafo);
                    break;

                case 6:
                    algoritmoKruskal(grafo);
                    break;

                case 7:

                    algoritmoPrimJarnik(grafo);
                    break;

                case 8:
                    algoritmoDijsktra(grafo);
                    break;

                case 9:
                    algoritmoDijsktraDadosPreCadastrados();
                    break;

                default:
                    break;
            }
        }
    }

    private static String getConfiguracoes(Grafo grafo) {
        String retorno = "Grafo: ";
        retorno += grafo.isOrientado() ? "Orientado" : "Não-Orientado";
        retorno += ", ";
        retorno += grafo.isValorado() ? "Valorado" : "Não-Valorado";
        return retorno + "\t";
    }

    private static void configuracoesIniciais(Grafo grafo) {
        String valorado = "";
        String orientado = "";

        while (valorado.isEmpty() && orientado.isEmpty()) {
            valorado = "s";
            orientado = "n";
        }

        grafo.setValorado(valorado.equals("s"));
        grafo.setOrientado(orientado.equals("s"));
    }

    public static String input(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void output(String mensagem, String titulo) {
        JTextArea textArea = new JTextArea(mensagem);
        JOptionPane.showMessageDialog(null, textArea, titulo, 1);
    }

    public static String getMenu() {
        return "Selecione uma opção" +
                "\n1 - Definir os Vértices" +
                "\n2 - Adicionar Uma aresta" +
                "\n3 - Adicionar várias Arestas" +
                "\n4 - Definir vértice inicial" +
                "\n---------------------------------" +
                "\n5 - Lista de Arestas" +
                "\n6 - Algoritmo de Kruskal" +
                "\n7 - Algoritmo de Prim" +
                "\n8 - Algoritmo de Dijkstra" +
                "\n9 - Algoritmo de Dijkstra com dados pré-cadastrado" +
                "\n---------------------------------" +
                "\n0 - Sair";
    }

    public static void definirVertices(Grafo grafo) {
        String[] nomesVertices = input("Insira os vértices do grafo separados por vírgula. \nExemplo: A, B, C, D")
                .toUpperCase()
                .replace(" ", "")
                .split(",");

        grafo.getVertices().clear();
        grafo.getArestas().clear();

        for (int i = 0; i < nomesVertices.length; i++) {
            Integer idVertice = i + 1;
            grafo.getVertices().add(new Vertice(idVertice, nomesVertices[i], NAO_VISITADO));
        }
    }

    public static void definirVerticeInicial(Grafo grafo) {
        String resultado = "\n";
        for (Vertice vertice : grafo.getVertices()) {
            resultado += "\n" + vertice.getIdVertice() + " - " + vertice.getNome();
        }
        Vertice vertice = obterVertice(grafo, Integer.parseInt(input("Selecione o código do vértice inicial: \n" + resultado)));
        vertice.setInicial(true);
        vertice.setValor(0);
    }

    public static void adicionarArestaDireto(Grafo grafo) {
        String mensagem = "Insira as arestas pelo nome do vértice, utilizando vírgula entre origem e destino, \nalém de ponto e vírgula para separar os vértices, Conforme exemplo:\n";
        String exemplo = "(ORIGEM,DESTINO;ORIGEM,DESTINO) \n\n0,1;0,3;1,4\n\n";
        String verticesCadastrados = "";

        for (Vertice vertice : grafo.getVertices()) {
            verticesCadastrados += vertice.getNome() + "\n";
        }
        String input = input(mensagem + exemplo + verticesCadastrados);
        String[] arrayVertices = input.replace(" ", "")
                .replace("(", "")
                .replace(")", "")
                .split(";");

        for (String vertice : arrayVertices) {
            String[] verticesOrigemDestino = vertice.split(",");
            Vertice origem = obterVertice(grafo, verticesOrigemDestino[0]);
            Vertice destino = obterVertice(grafo, verticesOrigemDestino[1]);

            int valorAresta = 0;

            if (grafo.isValorado())
                valorAresta = Integer.parseInt(input("Insira valor para a Aresta: " + origem.getNome() + " - " + destino.getNome())
                        .toUpperCase()
                        .replace(" ", ""));

            Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size() + 1));
            grafo.getArestas().add(aresta);
        }
    }

    public static void adicionarAresta(Grafo grafo) {
        String verticeOrigem = "Escolha pelo nome, o vértice de ORIGEM!\n";
        String verticeDestino = "Agora escolha pelo nome, o vértice de DESTINO!\n";
        String verticesCadastrados = "";

        for (Vertice vertice : grafo.getVertices()) {
            verticesCadastrados += vertice.getIdVertice() + " - " + vertice.getNome() + "\n";
        }
        Vertice origem = null;
        Vertice destino = null;

        while (origem == null && destino == null) {
            origem = obterVertice(grafo, input(verticeOrigem + verticesCadastrados));
            destino = obterVertice(grafo, input(verticeDestino + verticesCadastrados));
        }

        int valorAresta = 0;

        if (grafo.isValorado())
            valorAresta = Integer.parseInt(input("Insira valor para a Aresta.").toUpperCase().replace(" ", ""));

        Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size() + 1));
        grafo.getArestas().add(aresta);

        if (!grafo.isOrientado()) {
            Aresta arestaNaoOrientada = new Aresta(destino, origem, valorAresta, "e" + (grafo.getArestas().size() + 1));
            grafo.getArestas().add(arestaNaoOrientada);
        }
    }

    public static Vertice obterVertice(Grafo grafo, String nomeVertice) {
        for (Vertice vertice : grafo.getVertices()) {
            if (vertice.getNome().equals(nomeVertice))
                return vertice;
        }
        output("Vértice não encontrado.", "Erro");
        return null;
    }

    public static Vertice obterVertice(Grafo grafo, Integer idVertice) {
        for (Vertice vertice : grafo.getVertices()) {
            if (vertice.getIdVertice().equals(idVertice))
                return vertice;
        }
        output("Vértice não encontrado.", "Erro");
        return null;
    }

    private static void listaArestas(Grafo grafo) {
        String listaArestas = getConfiguracoes(grafo) + " \n\n";

        for (Aresta aresta : grafo.getArestas()) {
            listaArestas += "[" + aresta.getOrigem() + ", " + aresta.getDestino();
            listaArestas += grafo.isValorado() ? ", " + aresta.getValor() + "]\n" : "]\n";
        }

        output(listaArestas, "Lista de Arestas");
    }

    private static void algoritmoKruskal(Grafo grafo) {
        List<Aresta> arestas = grafo.getArestas();
        List<Vertice> vertices = grafo.getVertices();
        arestas.sort(Comparator.comparing(Aresta::getValor));

        List<Vertice[]> filaVerticesOrdemCrescente = new ArrayList<>();
        for (Vertice vertice : vertices) {
            Vertice[] arrayVertice = new Vertice[1];
            arrayVertice[0] = vertice;
            filaVerticesOrdemCrescente.add(arrayVertice);
        }

        StringBuilder resultado = new StringBuilder("Tabela\n\n");

        int vMenosUm = 0;
        for (Aresta aresta : arestas) {
            manipularAresta(aresta, filaVerticesOrdemCrescente, resultado, vMenosUm);
            if (vMenosUm == vertices.size() - 1)
                break;
        }

        output(resultado.toString(), "Algoritmo de Kruskal");
    }

    private static void manipularAresta(Aresta aresta, List<Vertice[]> filaVerticesOrdemCrescente, StringBuilder resultado, int vMenosum) {
        Vertice origem = aresta.getOrigem();
        Vertice destino = aresta.getDestino();

        Vertice[] arrayVerticeOrigem = obterArrayOndeEstaOVertice(origem, filaVerticesOrdemCrescente);
        Vertice[] arrayVerticeDestino = obterArrayOndeEstaOVertice(destino, filaVerticesOrdemCrescente);

        if (!arrayVerticeOrigem.equals(arrayVerticeDestino)) {
            Vertice[] uniaoArrayVertices = new Vertice[(arrayVerticeOrigem.length + arrayVerticeDestino.length)];

            int i = 0;

            for (int j = 0; j < arrayVerticeOrigem.length; j++) {
                uniaoArrayVertices[i] = arrayVerticeOrigem[j];
                i++;
            }

            for (int j = 0; j < arrayVerticeDestino.length; j++) {
                uniaoArrayVertices[i] = arrayVerticeDestino[j];
                i++;
            }

            filaVerticesOrdemCrescente.remove(arrayVerticeOrigem);
            filaVerticesOrdemCrescente.remove(arrayVerticeDestino);
            filaVerticesOrdemCrescente.add(uniaoArrayVertices);
            resultado.append("Valor: " + aresta.getValor());
            resultado.append("\tOrigem: " + origem.getNome());
            resultado.append("\tDestino: " + destino.getNome());
            resultado.append("\n");
            vMenosum++;
        }

    }

    private static Vertice[] obterArrayOndeEstaOVertice(Vertice vertice, List<Vertice[]> setVertices) {
        for (Vertice[] arrayVertice : setVertices) {
            for (int i = 0; i < arrayVertice.length; i++) {
                if (arrayVertice[i].equals(vertice))
                    return arrayVertice;
            }
        }
        return null;
    }

    private static List<Vertice> obterAdjacentes(boolean orientado, List<Aresta> arestas, Vertice vertice) {
        List<Vertice> ajdacentes = new ArrayList<>();

        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(vertice)) {
                ajdacentes.add(aresta.getDestino());

            } else if (!orientado && aresta.getDestino().equals(vertice)) {
                ajdacentes.add(aresta.getOrigem());
            }
        }

        return ajdacentes;
    }

    private static List<Vertice> obterAdjacentes(boolean orientado, List<Aresta> arestas, List<Vertice> vertices, Vertice vertice) {
        List<Vertice> ajdacentes = new ArrayList<>();

        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(vertice)) {
                ajdacentes.add(aresta.getDestino());

            } else if (!orientado && aresta.getDestino().equals(vertice)) {
                ajdacentes.add(aresta.getOrigem());
            }
        }

        return vertices.stream().filter(v -> ajdacentes.contains(v)).collect(Collectors.toList());
    }

    private static int valorArestaEntreVertices(Vertice origem, Vertice destino, List<Aresta> arestas) {

        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                return aresta.getValor();
            }
        }

        return 0;
    }

    private static void algoritmoPrimJarnik(Grafo grafo) throws CloneNotSupportedException {
        List<Aresta> arestas = grafo.getArestas();
        List<Vertice> vertices = grafo.getVertices();
        List<Vertice> verticeQueue = new ArrayList<>(vertices);
        List<Vertice> verticesVisitados = new ArrayList<>();
        Vertice verticeVisitado = verticeMenorValor(verticeQueue);

        while (!verticeQueue.isEmpty()) {

            Vertice verticeMenorValor = new Vertice(Integer.MAX_VALUE);
            for (Vertice adjacente : obterAdjacentes(false, arestas, verticeVisitado)) {
                if (adjacente.getCor().equals(NAO_VISITADO)) {
                    int valorVertice = valorArestaEntreVertices(verticeVisitado, adjacente, arestas);
                    adjacente.setValor(valorVertice);

                    if (adjacente.getValor() < verticeMenorValor.getValor())
                        verticeMenorValor = adjacente;
                }
            }

            verticeMenorValor.setCaminho(verticeVisitado.clone());
            verticesVisitados.add(verticeVisitado.clone());
            verticeQueue.remove(verticeVisitado.clone());
            verticeVisitado = verticeMenorValor;
            verticeVisitado.setCor(VISITADO);
        }

        StringBuilder resultado = new StringBuilder("Tabela\n\n");
        for (Vertice vertice : verticesVisitados) {
            resultado.append("Vértice: " + vertice.getNome());
            resultado.append(" - Valor: ");
            resultado.append(vertice.getValor());
            resultado.append("\n");
        }
        output(resultado.toString(), "Teste");
    }

    private static Vertice verticeMenorValor(List<Vertice> vertices) {
        Vertice verticeMenorValor = new Vertice(Integer.MAX_VALUE);
        for (Vertice vertice : vertices) {
            if (vertice.getValor() < verticeMenorValor.getValor())
                verticeMenorValor = vertice;
        }
        return verticeMenorValor;
    }

    private static void algoritmoDijsktraDadosPreCadastrados() throws CloneNotSupportedException {
        Grafo grafo = new Grafo();
        grafo.getVertices().clear();
        grafo.getArestas().clear();

        Vertice vertice1 = new Vertice(1, "1", NAO_VISITADO);
        Vertice vertice2 = new Vertice(2, "2", NAO_VISITADO);
        Vertice vertice3 = new Vertice(3, "3", NAO_VISITADO);
        Vertice vertice4 = new Vertice(4, "4", NAO_VISITADO);
        Vertice vertice5 = new Vertice(5, "5", NAO_VISITADO);
        Vertice vertice6 = new Vertice(6, "6", NAO_VISITADO);
        Vertice vertice7 = new Vertice(7, "7", NAO_VISITADO);
        Vertice vertice8 = new Vertice(8, "8", NAO_VISITADO);
        Vertice vertice9 = new Vertice(9, "9", NAO_VISITADO);
        Vertice vertice10 = new Vertice(10, "10", NAO_VISITADO);
        Vertice vertice11 = new Vertice(11, "11", NAO_VISITADO);
        Vertice vertice12 = new Vertice(12, "12", NAO_VISITADO);
        Vertice vertice13 = new Vertice(13, "13", NAO_VISITADO);
        Vertice vertice14 = new Vertice(14, "14", NAO_VISITADO);
        Vertice vertice15 = new Vertice(15, "15", NAO_VISITADO);

        grafo.setVerticeInicial(vertice1);
        grafo.setVerticeFinal(vertice13);

        grafo.getVertices().add(vertice1);
        grafo.getVertices().add(vertice2);
        grafo.getVertices().add(vertice3);
        grafo.getVertices().add(vertice4);
        grafo.getVertices().add(vertice5);
        grafo.getVertices().add(vertice6);
        grafo.getVertices().add(vertice7);
        grafo.getVertices().add(vertice8);
        grafo.getVertices().add(vertice9);
        grafo.getVertices().add(vertice10);
        grafo.getVertices().add(vertice11);
        grafo.getVertices().add(vertice12);
        grafo.getVertices().add(vertice13);
        grafo.getVertices().add(vertice14);
        grafo.getVertices().add(vertice15);

        //Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size() + 1));
        Aresta aresta1 = new Aresta(vertice1, vertice2, 2, "e1");
        Aresta aresta2 = new Aresta(vertice1, vertice3, 6, "e2");
        Aresta aresta3 = new Aresta(vertice1, vertice4, 4, "e3");
        Aresta aresta4 = new Aresta(vertice2, vertice5, 4, "e4");
        Aresta aresta5 = new Aresta(vertice2, vertice6, 3, "e5");
        Aresta aresta6 = new Aresta(vertice2, vertice7, 1, "e6");
        Aresta aresta7 = new Aresta(vertice3, vertice7, 4, "e7");
        Aresta aresta8 = new Aresta(vertice3, vertice8, 3, "e8");
        Aresta aresta9 = new Aresta(vertice4, vertice9, 7, "e9");
        Aresta aresta10 = new Aresta(vertice5, vertice10, 7, "e10");
        Aresta aresta11 = new Aresta(vertice6, vertice10, 2, "e11");
        Aresta aresta12 = new Aresta(vertice7, vertice11, 5, "e12");
        Aresta aresta13 = new Aresta(vertice8, vertice11, 1, "e13");
        Aresta aresta14 = new Aresta(vertice9, vertice12, 3, "e14");
        Aresta aresta15 = new Aresta(vertice9, vertice15, 5, "e15");
        Aresta aresta16 = new Aresta(vertice10, vertice13, 5, "e16");
        Aresta aresta17 = new Aresta(vertice11, vertice14, 6, "e17");
        Aresta aresta18 = new Aresta(vertice12, vertice14, 6, "e18");

        grafo.getArestas().add(aresta1);
        grafo.getArestas().add(aresta2);
        grafo.getArestas().add(aresta3);
        grafo.getArestas().add(aresta4);
        grafo.getArestas().add(aresta5);
        grafo.getArestas().add(aresta6);
        grafo.getArestas().add(aresta7);
        grafo.getArestas().add(aresta8);
        grafo.getArestas().add(aresta9);
        grafo.getArestas().add(aresta10);
        grafo.getArestas().add(aresta11);
        grafo.getArestas().add(aresta12);
        grafo.getArestas().add(aresta13);
        grafo.getArestas().add(aresta14);
        grafo.getArestas().add(aresta15);
        grafo.getArestas().add(aresta16);
        grafo.getArestas().add(aresta17);
        grafo.getArestas().add(aresta18);

        algoritmoDijsktra(grafo);
    }

    private static void algoritmoDijsktra(Grafo grafo) throws CloneNotSupportedException {
        List<Aresta> arestas = grafo.getArestas();
        List<Vertice> vertices = grafo.getVertices();

        vertices.forEach(v -> v.setValor(Integer.MAX_VALUE)); // define valor maximo do vértice para posterior cálculo do real valor
        List<Vertice> verticesNaoVisitados = new ArrayList<>();
        verticesNaoVisitados.addAll(vertices); // cria lista de vértices não visitados
        Vertice verticeInicial = grafo.getVerticeInicial(); // pega o Vértice de origem
        Vertice verticeFinal = grafo.getVerticeFinal(); // pega o Vértice de origem

        verticeInicial.setValor(0); // define valor 0 por ser o vértice inicial

        while (!verticesNaoVisitados.isEmpty()) {
            Vertice origem = menorValorNaoVisitado(verticesNaoVisitados);

            if(origem == null)
                return;

            Vertice verticeMenorValor = new Vertice(Integer.MAX_VALUE);
            for (Vertice destino : obterAdjacentes(true, arestas, vertices, origem)) {
                int valorAresta = valorArestaEntreVertices(origem, destino, arestas);
                int custo = origem.getValor() + valorAresta;

                if(custo < 0)
                    break;

                if (custo < verticeMenorValor.getValor() && custo < destino.getValor()) {
                    verticeMenorValor = destino;
                    verticeMenorValor.setCaminho(origem);
                    verticeMenorValor.setValor(custo);
                }

            }

            verticesNaoVisitados.remove(origem);

        }

        mostrarCaminho(verticeFinal);
    }

    private static void mostrarCaminho(Vertice verticeFinal) {
        Vertice verticeAtual = verticeFinal;
        while (verticeAtual != null) {
            System.out.println(verticeAtual.getNome() + " - " + verticeAtual.getValor());

            verticeAtual = verticeAtual.getCaminho();
        }
    }

    private static Vertice menorValorNaoVisitado(List<Vertice> verticesNaoVisitados) {
        if(verticesNaoVisitados.size() > 0) {
            verticesNaoVisitados.sort(Comparator.comparing(Vertice::getValor));
            return verticesNaoVisitados.get(0);
        }

        return null;
    }
}
