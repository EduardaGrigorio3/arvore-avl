package ArvoreAvl;

public class AVL {
	private No raiz;

	public AVL() {
		raiz = null;
	}

	public boolean isEmpty() {
		return raiz == null;
	}

	public void insere(int info) {
		if (isEmpty()) {// se vazio preenche a raiz
			No novoNo = new No(info);
			raiz = novoNo;
			DefineBal(raiz);// define o fator de balanceamento de cada nó
		} else {
			No atual;
			if (info > atual.getInfo()) {
				if (atual.getDireita() == null) {// se o no a ser inserido for maior que o atual e o da direita do atual
													// for
													// vazio, insere la
					No novo = new No(info);
					atual.setDireita(novo);
					novo.setPai(atual);
					DefineBal(raiz);// define o fator de balanceamento de cada nó
					raiz = balanceamento(raiz);// Balanceia a arvore
				} else { // senão, faz a recursão, passando o nó da direita do atual.
					insere(info, atual.getDireita());
				}
			} else if (info < atual.getInfo()) { // mesma coisa aqui, só que com o nó da esquerda, se for menor
				if (atual.getEsquerda() == null) {
					No novo = new No(info);
					atual.setEsquerda(novo);
					novo.setPai(atual);
					DefineBal(raiz);// define o fator de balanceamento de cada nó
					raiz = balanceamento(raiz);// Balanceia a arvore
				} else {
					insere(info, atual.getEsquerda());
				}
			} else {// se o nó já existir
				System.out.println("Impossível Inserir!");
			}
		}
	}

	public void remove(int info) {
		No atual;
		if (isEmpty()) {// Se a arvore tiver vazia, nao faz nada
			System.out.println("Arvore vazia!");
		} else if (info > atual.getInfo()) {// Se o valor a ser removido for maior q o do no atual, anda pra direita e
											// atribui o retorno do metodo ao no direito passando ele mesmo como
											// parametro
			atual.setDireita(remove(info, atual.getDireita()));
		} else if (info < atual.getInfo()) {// se for menor, faz a mesma coisa do passo anterior, só q indo pra esquerda
			atual.setEsquerda(remove(info, atual.getEsquerda()));
		} else {// Se for igual, faz as verificações de remoção
			if (atual.getDireita() == null && atual.getEsquerda() == null) {// se o nó não tiver subarvores, ou seja,
																			// for uma folha, basta anular ele
				if (atual == raiz) {
					raiz = null;
				} else {
					atual = null;
				}

			} else if (atual.getEsquerda() == null) {// se o nó atual só tiver a subarvore da direita, pega o valor da
														// raiz dessa subarvore e substitui pelo atual
				atual = atual.getDireita();
			} else if (atual.getDireita() == null) {// se o atual tiver só a subarvore da esquerda,faz a mesma coisa ,
													// só que troca o nó pela raiz da direita
				atual = atual.getEsquerda();
			} else {// Se o nó tiver as duas subárvores, o bixo pega um pouco
				/*
				 * Cria um nó auxiliar pra percorrer a arvore até o valor mais a direita da
				 * subarvore da esquerda do nó atual e seta ele como o valor da esquerda do
				 * atual
				 */
				No aux = atual.getEsquerda();
				while (aux.getDireita() != null) {// Faz o aux ir até o valor mais a direita
					aux = aux.getDireita();
				}
				atual.setInfo(aux.getInfo()); // substitui o valor do nó atual pelo valor do auxiliar
				aux.setInfo(info); // substitui o valor do auxiliar pelo atual
				atual.setEsquerda(remove(info, atual.getEsquerda()));// chama a recursão pra remover o valor do atual, q
																		// agora tá no auxiliar, por isso, vai remover o
																		// auxiliar
			}
		}
		if (raiz != null) {
			DefineBal(raiz);// atualiza o fator de balanceamento de cada nó
			raiz = balanceamento(raiz);// Balanceia a arvore
		}

		return atual;// retorna o atual
	}

	public int altura(No atual) {// Verifica a altura de um determinado nó
		if (atual == null) {// Se o nó for nulo, sua altura será -1
			return -1;
		}
		if (atual.getDireita() == null && atual.getEsquerda() == null) {// Se ele não tiver nenhum filho, sua altura
																		// será 0
			return 0;
		} else if (atual.getEsquerda() == null) {// Se o nó tiver apenas um filho, sua altura será 1 + a altura do nó
													// filho
			return 1 + altura(atual.getDireita());
		} else if (atual.getDireita() == null) { // Mesma do passo anterior aqui
			return 1 + altura(atual.getEsquerda());
		} else { // Se ele tiver dois filhos, temos q verificar qual filho é mais "alto"
			if (altura(atual.getEsquerda()) > altura(atual.getDireita())) {// a altura do nó será a soma de 1+ a altura
																			// do filho mais alto
				return 1 + altura(atual.getEsquerda());
			} else {
				return 1 + altura(atual.getDireita());
			}
		} // e assim recursivamente, até chegar nas folhas q não terão filhos, a altura
			// será 0 e assim a recursão para.
	}

	public void balanceamento(No atual) {// Define o valor de balanceamento de cada nó com base na altura (adicionei um
											// atributo pra armazenar o balanceamento na classe nó)
		atual.setBalanceamento(altura(atual.getEsquerda()) - altura(atual.getDireita()));// O valor do balanceamendo
																							// será a altura do filho da
																							// direita menos a altura do
																							// da direita
		if (atual.getDireita() != null) {// verifica todos os nós
			balanceamento(atual.getDireita());
		}
		if (atual.getEsquerda() != null) {// verifica todos os nós
			balanceamento(atual.getEsquerda());
		}
	}

	public No rotacaoSimplesDireita(No atual) {
		No aux = atual.getEsquerda(); // Armazena o valor do nó da esquerda do atual
		aux.setPai(atual.getPai());
		if (aux.getDireita() != null) {// tratamento para quando a árvore é degenerada
			aux.getDireita().setPai(atual);
		}
		atual.setPai(aux);
		atual.setEsquerda(aux.getDireita());// Joga o valor da direita do nó da esquerda do atual, na esquerda do atual
		aux.setDireita(atual);// troca o valor da direita do nó da esquerda pelo atual
		if (aux.getPai() != null) {// Se aux não for a raiz principal, configura o pai pra apontar pro novo nó
			if (aux.getPai().getDireita() == atual) {
				aux.getPai().setDireita(aux);
			} else if (aux.getPai().getEsquerda() == atual) {
				aux.getPai().setEsquerda(aux);
			}
		}
		balanceamento(aux);// atualiza o valor do balanceamento
		return aux; // retorna o valor do nó da esquerda q é o novo pai
	}

	public No rotacaoSimplesEsquerda(No atual) {
		No aux = atual.getDireita();
		aux.setPai(atual.getPai());
		if (aux.getEsquerda() != null) {// tratamento para quando a árvore é degenerada
			aux.getEsquerda().setPai(atual);
		}

		atual.setPai(aux);
		atual.setDireita(aux.getEsquerda());
		aux.setEsquerda(atual);
		if (aux.getPai() != null) {
			if (aux.getPai().getDireita() == atual) {
				aux.getPai().setDireita(aux);
			} else if (aux.getPai().getEsquerda() == atual) {
				aux.getPai().setEsquerda(aux);
			}
		}
		balanceamento(aux);// atualiza o valor do balanceamento
		return aux;
	}

	public No rotacaoDuplaDireita(No atual) {
		No aux = atual.getEsquerda();// Armazena o valor do filho da esquerda
		atual.setEsquerda(rotacaoSimplesEsquerda(aux));// faz uma rotação para a esquerda no filho da esquerda
		No aux2 = rotacaoSimplesDireita(atual); // Faz uma rotação para a direita no atual/pai com o filho da esquerda
												// já rodado
		return aux2; // retorna o nó q será o novo pai com seus filhos
	}

	// mesma coisa do de rotação dupla pra direita, só que invertido pra esquerda
	public No rotacaoDuplaEsquerda(No atual) {
		No aux = atual.getDireita();
		atual.setDireita(rotacaoSimplesDireita(aux));
		No aux2 = rotacaoSimplesEsquerda(atual);
		return aux2;
	}

	public No DefineBal(No atual) {// Recebe como parâmetro a raiz
		/*
		 * Se o nó atual tiver FB=2 e o seu filho da esquerda dele tiver Fb>=0, troca o
		 * valor dele pelo resultado da rotação a direita com ele
		 */
		if (atual.getBalanceamento() == 2 && atual.getEsquerda().getBalanceamento() >= 0) {
			atual = rotacaoSimplesDireita(atual);
			/*
			 * Senão se o nó atual tiver FB=-2 e o filho da direita dele tiver Fb<0, troca o
			 * valor dele pelo resultado da rotação a esquerda com ele
			 */
		} else if (atual.getBalanceamento() == -2 && atual.getDireita().getBalanceamento() <= 0) { // mudat dps
			atual = rotacaoSimplesEsquerda(atual);
			/*
			 * Senão se o nó atual tiver FB=2 e o filho da esquerda dele tiver Fb<0, troca o
			 * valor dele pelo resultado da rotação dupla a direita com ele
			 */
		} else if (atual.getBalanceamento() == 2 && atual.getEsquerda().getBalanceamento() < 0) {
			atual = rotacaoDuplaDireita(atual);
			/*
			 * Senão se o nó atual tiver FB=-2 e o filho da direita dele tiver Fb>0, troca o
			 * valor dele pelo resultado da rotação dupla a esquerda com ele
			 */
		} else if (atual.getBalanceamento() == -2 && atual.getDireita().getBalanceamento() > 0) {
			atual = rotacaoDuplaEsquerda(atual);
		}
		/* Nessa parte aqui a recursão vai procurar por mais nós desbalanceados */
		if (atual.getDireita() != null) {
			DefineBal(atual.getDireita());
		}
		if (atual.getEsquerda() != null) {
			DefineBal(atual.getEsquerda());
		}
		return atual; // Retorna a nova raiz com seus filhotes balanceados
	}

	public void imprimirPreOrdem(){
    	if (!isEmpty()){
    		System.out.print(this.ele.getValor() + " ");
    		if (this.esq != null){
    			this.esq.imprimirPreOrdem();
    			}
    	if (this.dir != null){
    		this.dir.imprimirPreOrdem();
    	}
    	}
    }

	public No getRaiz() {
		return raiz;
	}

	public void setRaiz(No raiz) {
		this.raiz = raiz;
	}

}
