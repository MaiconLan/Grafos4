import java.util.Objects;

public class Aresta {

    private String nome;
    private Vertice origem;
    private Vertice destino;
    private int valor;

    public Aresta() {
    }

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public Aresta(Vertice origem, Vertice destino, int valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public Aresta(Vertice origem, Vertice destino, int valor, String nome) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aresta aresta = (Aresta) o;
        return Objects.equals(origem, aresta.origem) &&
                Objects.equals(destino, aresta.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origem, destino);
    }
}
