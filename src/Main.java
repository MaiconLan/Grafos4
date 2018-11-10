import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        while (valorado.isEmpty() && orientado.isEmpty()){
            valorado = "s";
            orientado = "n";
        }

        grafo.setValorado(valorado.equals("s"));
        grafo.setOrientado(orientado.equals("s"));
    }

    public static String input(String mensagem){
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void output(String mensagem, String titulo) {
        JTextArea textArea = new JTextArea(mensagem);
        JOptionPane.showMessageDialog(null, textArea, titulo, 1);
    }

    public static String getMenu(){
        return "Selecione uma opção" +
                "\n1 - Definir os Vértices" +
                "\n2 - Adicionar Uma aresta" +
                "\n3 - Adicionar várias Arestas" +
                "\n4 - Definir vértice inicial" +
                "\n---------------------------------" +
                "\n5 - Lista de Arestas" +
                "\n6 - Algoritmo de Kruskal" +
                "\n7 - Algoritmo de Prim" +
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

        for(int i = 0; i < nomesVertices.length; i++) {
            Integer idVertice = i+1;
            grafo.getVertices().add(new Vertice(idVertice, nomesVertices[i], NAO_VISITADO));
        }
    }

    public static void definirVerticeInicial(Grafo grafo){
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

            if(grafo.isValorado())
                valorAresta = Integer.parseInt(input("Insira valor para a Aresta: " + origem.getNome() + " - " + destino.getNome())
                        .toUpperCase()
                        .replace(" ", ""));

            Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size()+1));
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

        if(grafo.isValorado())
            valorAresta = Integer.parseInt(input("Insira valor para a Aresta.").toUpperCase().replace(" ", ""));

        Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size()+1));
        grafo.getArestas().add(aresta);

        if(!grafo.isOrientado()) {
            Aresta arestaNaoOrientada = new Aresta(destino, origem, valorAresta, "e" + (grafo.getArestas().size()+1));
            grafo.getArestas().add(arestaNaoOrientada);
        }
    }

    public static Vertice obterVertice(Grafo grafo, String nomeVertice){
        for (Vertice vertice : grafo.getVertices()) {
            if (vertice.getNome().equals(nomeVertice))
                return vertice;
        }
        output("Vértice não encontrado.", "Erro");
        return null;
    }

    public static Vertice obterVertice(Grafo grafo, Integer idVertice){
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
            listaArestas += grafo.isValorado() ?  ", " + aresta.getValor() + "]\n" : "]\n";
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
        for(Aresta aresta : arestas){
            manipularAresta(aresta, filaVerticesOrdemCrescente, resultado, vMenosUm);
            if(vMenosUm == vertices.size()-1)
                break;
        }

        output(resultado.toString(), "Algoritmo de Kruskal");
    }

    private static void manipularAresta(Aresta aresta, List<Vertice[]> filaVerticesOrdemCrescente, StringBuilder resultado, int vMenosum){
        Vertice origem = aresta.getOrigem();
        Vertice destino = aresta.getDestino();

        Vertice[] arrayVerticeOrigem = obterArrayOndeEstaOVertice(origem, filaVerticesOrdemCrescente);
        Vertice[] arrayVerticeDestino = obterArrayOndeEstaOVertice(destino, filaVerticesOrdemCrescente);

        if (!arrayVerticeOrigem.equals(arrayVerticeDestino)) {
            Vertice[] uniaoArrayVertices = new Vertice[(arrayVerticeOrigem.length + arrayVerticeDestino.length)];

            int i=0;

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
            resultado .append("Valor: " + aresta.getValor());
            resultado .append("\tOrigem: " + origem.getNome());
            resultado .append("\tDestino: " + destino.getNome());
            resultado .append("\n");
            vMenosum++;
        }

    }

    private static Vertice[] obterArrayOndeEstaOVertice(Vertice vertice, List<Vertice[]> setVertices){
        for (Vertice[] arrayVertice : setVertices) {
            for (int i = 0; i < arrayVertice.length; i++) {
                if(arrayVertice[i].equals(vertice))
                    return arrayVertice;
            }
        }
        return null;
    }

    private static List<Vertice> obterAdjacentes(boolean orientado, List<Aresta> arestas, Vertice vertice){
        List<Vertice> ajdacentes = new ArrayList<>();

        for (Aresta aresta : arestas) {
            if(aresta.getOrigem().equals(vertice)) {
                ajdacentes.add(aresta.getDestino());

            } else if (!orientado && aresta.getDestino().equals(vertice)) {
                ajdacentes.add(aresta.getOrigem());
            }
        }

        return ajdacentes;
    }

    private static int valorArestaEntreVertices(Vertice origem, Vertice destino, List<Aresta> arestas){

        for (Aresta aresta: arestas) {
            if(aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)){
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
                if(adjacente.getCor().equals(NAO_VISITADO)){
                    int valorVertice = valorArestaEntreVertices(verticeVisitado, adjacente, arestas);
                    adjacente.setValor(valorVertice);

                    if(adjacente.getValor() < verticeMenorValor.getValor())
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
        for (Vertice vertice: verticesVisitados) {
            resultado.append("Vértice: "+vertice.getNome());
            resultado.append(" - Valor: ");
            resultado.append(vertice.getValor());
            resultado.append("\n");
        }
        output(resultado.toString(), "Teste");
    }

    private static Vertice verticeMenorValor(List<Vertice> vertices){
        Vertice verticeMenorValor = new Vertice(Integer.MAX_VALUE);
        for (Vertice vertice : vertices) {
            if(vertice.getValor() < verticeMenorValor.getValor())
                verticeMenorValor = vertice;
        }
        return verticeMenorValor;
    }

}
