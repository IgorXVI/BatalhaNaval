/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanaval;

import java.util.Random;

/**
 *
 * @author Alunos
 */
public class Navio {

    private int tamanho;
    private int[] posInicial, posFinal;
    private boolean vertical;

    Navio(int tamanho) {
        this.tamanho = tamanho;
        this.posFinal = new int[]{0, 0};
        this.posInicial = new int[]{0, 0};
        this.gerarPosicoes();
    }
    
    public void gerarPosicoes() {
        Random r = new Random();
        this.vertical = r.nextBoolean();
        this.posInicial[0] = r.nextInt(7);
        this.posInicial[1] = r.nextInt(7);
        if (this.vertical) {
            this.posFinal[0] = this.posInicial[0] + this.tamanho -1;
            if(this.posFinal[0] >= 7){
                this.posFinal[0] = this.posInicial[0] - this.tamanho + 1;
            }
            this.posFinal[1] = this.posInicial[1];
        } else {
            this.posFinal[0] = this.posInicial[0];
            this.posFinal[1] = this.posInicial[1] + this.tamanho -1;
            if(this.posFinal[1] >= 7){
                this.posFinal[1] = this.posInicial[1] - this.tamanho + 1;
            }
        }
    }
    
    public boolean getVertical() {
        return this.vertical;
    }

    public int getTamanho() {
        return this.tamanho;
    }

    public int[] getPosInicial() {
        return this.posInicial;
    }

    public int[] getPosFinal() {
        return this.posFinal;
    }

    public void setVertical(boolean novoVertical) {
        this.vertical = novoVertical;
    }

    public void setTamanho(int novoTamanho) {
        this.tamanho = novoTamanho;
    }

    public void setPosInicial(int[] novaPosInicial) {
        this.posInicial = novaPosInicial;
    }

    public void setPosFinal(int[] novaPosFinal) {
        this.posFinal = novaPosFinal;
    }
}
