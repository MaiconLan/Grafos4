import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private boolean orientado;
    private boolean valorado;
    private List<Vertice> vertices = new ArrayList<>();
    private List<Aresta> arestas = new ArrayList<>();

    public Vertice getVerticeInicial(){
        return vertices.stream()
                .filter(vertice -> vertice.isInicial())
                .findFirst()
                .get();
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    public boolean isOrientado() {
        return orientado;
    }

    public void setOrientado(boolean orientado) {
        this.orientado = orientado;
    }

    public boolean isValorado() {
        return valorado;
    }

    public void setValorado(boolean valorado) {
        this.valorado = valorado;
    }
}
