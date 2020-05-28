import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
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
                    algoritmoDijsktraExemploProfessor();
                    break;

                case 2:
                    algoritmoDijsktraExemploCriado();
                    break;

                default:
                    break;
            }
        }
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
                "\n1 - Executar algoritmo do exemplo do professor!" +
                "\n2 - Executar algoritmo do exemplo criado por mim!" +
                "\n---------------------------------" +
                "\n0 - Sair";
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

    private static void algoritmoDijsktraExemploProfessor() throws CloneNotSupportedException {
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
        grafo.setVerticeFinal(vertice14);

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

    private static void algoritmoDijsktraExemploCriado() throws CloneNotSupportedException {
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

        grafo.setVerticeInicial(vertice1);
        grafo.setVerticeFinal(vertice7);

        grafo.getVertices().add(vertice1);
        grafo.getVertices().add(vertice2);
        grafo.getVertices().add(vertice3);
        grafo.getVertices().add(vertice4);
        grafo.getVertices().add(vertice5);
        grafo.getVertices().add(vertice6);
        grafo.getVertices().add(vertice7);

        //Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size() + 1));
        Aresta aresta1 = new Aresta(vertice1, vertice2, 6, "e1");
        Aresta aresta2 = new Aresta(vertice1, vertice4, 2, "e1");
        Aresta aresta3 = new Aresta(vertice2, vertice5, 3, "e1");
        Aresta aresta4 = new Aresta(vertice3, vertice7, 1, "e1");
        Aresta aresta5 = new Aresta(vertice4, vertice5, 3, "e1");
        Aresta aresta6 = new Aresta(vertice4, vertice6, 5, "e1");
        Aresta aresta7 = new Aresta(vertice5, vertice3, 2, "e1");
        Aresta aresta8 = new Aresta(vertice5, vertice7, 4, "e1");
        Aresta aresta9 = new Aresta(vertice6, vertice5, 4, "e1");

        grafo.getArestas().add(aresta1);
        grafo.getArestas().add(aresta2);
        grafo.getArestas().add(aresta3);
        grafo.getArestas().add(aresta4);
        grafo.getArestas().add(aresta5);
        grafo.getArestas().add(aresta6);
        grafo.getArestas().add(aresta7);
        grafo.getArestas().add(aresta8);
        grafo.getArestas().add(aresta9);

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

            if (origem == null)
                return;

            Vertice verticeMenorValor = new Vertice(Integer.MAX_VALUE);
            for (Vertice destino : obterAdjacentes(true, arestas, vertices, origem)) {
                int valorAresta = valorArestaEntreVertices(origem, destino, arestas);
                int custo = origem.getValor() + valorAresta;

                if (custo < 0)
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

        List<String> caminho = new ArrayList<>();

        StringBuilder resultado = new StringBuilder("Resultado\n\n");
        while (verticeAtual != null) {
            caminho.add(verticeAtual.getNome());
            verticeAtual = verticeAtual.getCaminho();
        }

        Collections.reverse(caminho);

        caminho.forEach(r -> resultado.append(r + " -> "));

        output(resultado.toString(), "Resultado");
    }

    private static Vertice menorValorNaoVisitado(List<Vertice> verticesNaoVisitados) {
        if (verticesNaoVisitados.size() > 0) {
            verticesNaoVisitados.sort(Comparator.comparing(Vertice::getValor));
            return verticesNaoVisitados.get(0);
        }

        return null;
    }
}
