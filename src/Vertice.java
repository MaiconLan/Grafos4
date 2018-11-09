import java.util.Objects;

public class Vertice {

    private Integer idVertice;
    private String nome;
    private String cor;
    private int valor;
    private Vertice caminho;
    private boolean inicial;

    public Vertice() {
    }

    public Vertice(String nome) {
        this.nome = nome;
    }

    public Vertice(Integer idVertice, String nome, String cor) {
        this.idVertice = idVertice;
        this.nome = nome;
        this.cor = cor;
        this.valor = Integer.MAX_VALUE;
    }

    public Integer getIdVertice() {
        return idVertice;
    }

    public void setIdVertice(Integer idVertice) {
        this.idVertice = idVertice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
        this.valor = 0;
    }

    public Vertice getCaminho() {
        return caminho;
    }

    public void setCaminho(Vertice caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(idVertice, vertice.idVertice) &&
                Objects.equals(nome, vertice.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVertice, nome);
    }
}
