package ArvoreAvl;


public class No {
	private int info;
    private No pai;
    private No direita;
    private No esquerda;
    private int balanceamento;
    
    public No(int i) {
        this.direita = null;
        this.esquerda = null;
        this.pai = null;
        this.info = i;
    }
    
	public int getInfo() {
		return info;
	}
	
	public void setInfo(int info) {
		this.info = info;
	}
	
	public No getPai() {
		return pai;
	}
	
	public void setPai(No pai) {
		this.pai = pai;
	}
	
	public No getDireita() {
		return direita;
	}
	
	public void setDireita(No direita) {
		this.direita = direita;
	}
	
	public No getEsquerda() {
		return esquerda;
	}
	
	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}
	
	public int getBalanceamento() {
		return balanceamento;
	}
	
	public void setBalanceamento(int balanceamento) {
		this.balanceamento = balanceamento;
	}
}
