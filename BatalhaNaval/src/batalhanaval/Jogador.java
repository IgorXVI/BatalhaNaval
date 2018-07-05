/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanaval;

import java.io.Serializable;

/**
 *
 * @author Alunos
 */
public class Jogador implements Serializable{

    private String nome;
    private Tabuleiro tabuleiro;
    private boolean temBomba;

    Jogador(String nome) {
        this.nome = nome;
        this.temBomba = true;
        this.tabuleiro = new Tabuleiro();
    }

    public void realizarJogada(int x, int y, Jogador adversario, boolean bomba) {
        if (bomba) {
            usarBomba(x, y, adversario);
        } else {
            atirar(x, y, adversario);
        }
        adversario.getTabuleiro().mostrarTabuleiroPublico();
    }

    private void usarBomba(int x, int y, Jogador adversario) {
        System.out.println("O Almirante " + this.nome + " usou uma bomba!");
        this.temBomba = false;
        int xc, yc, xb, yb, xe, ye, xd, yd;
        xc = x - 1;
        yc = y;
        xb = x + 1;
        yb = y;
        xe = x;
        ye = y - 1;
        xd = x;
        yd = y + 1;
        atirar(x, y, adversario);
        if (xc > -1) {
            atirar(xc, yc, adversario);
        }
        if (xb < 7) {
            atirar(xb, yb, adversario);
        }
        if (ye > -1) {
            atirar(xe, ye, adversario);
        }
        if (yd < 7) {
            atirar(xd, yd, adversario);
        }
    }

    private void atirar(int x, int y, Jogador adversario) {
        if (adversario.getTabuleiro().getTabuleiroDoJogador()[x][y] == '~') {
            adversario.getTabuleiro().setErro(x, y);
            System.out.println("Fracasso no ataque às coordenadas (" + x + ", " + y + ")!");
        } else {
            adversario.getTabuleiro().setAcerto(x, y);
            System.out.println("Successo no ataque às coordenadas (" + x + ", " + y + ")!");
        }
    }

    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean getTemBomba() {
        return this.temBomba;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }
    
    public void setTabuleiro(Tabuleiro t){
        this.tabuleiro = t;
    }
}
