/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanaval;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Bot extends Jogador implements Serializable {

    private int[] posUltimo, posInicalAcerto;
    private boolean inimigoVertical, segundo, terceiro,
            erro1, erro2, sentido;

    /*
    sentido = true : positivo
    sentido = flase : negativo
     */
 /*
    Passos do ataque:
    primeiro : atacar posição randômica ->
    
    segundo : se o primeiro passo resultar em sucesso, atacar posição na vertical
    ou na horizontal do último ataque e repetir esse passo até um acerto ->
    
    terceiro : quando o acerto aconter no segundo passo definir o alinhamento do navio
    (vertical ou horizontal) e atacar posições relativas ao ponto de acerto até errar
    pela primeira vez, então inverter o sentido de ataque e atacar até errar uma segunda vez ->
    
    primeiro : atacar posição randômica -> ...
     */
    public Bot() {
        super("Javaligor");
        this.posUltimo = new int[2];
        this.posInicalAcerto = new int[2];
        this.segundo = false;
        this.terceiro = false;
        this.erro1 = false;
        this.erro2 = false;
    }

    public void realizarJogada(Jogador adversario) {
        if(this.terceiro){
            this.terceiroPasso(adversario);
        }
        else if(this.segundo){
            this.segundoPasso(adversario);
        }
        else{
            this.primeiroPasso(adversario);
        }
    }
    
    private void terceiroPasso(Jogador adversario){
        int x, y;
        if(this.inimigoVertical){
            y = this.posInicalAcerto[1];
            if(this.sentido){
                x = this.posUltimo[0] + 1;
            }
            else{
                x = this.posUltimo[0] - 1;
            }
        }
        else{
            x = this.posInicalAcerto[0];
            if(this.sentido){
                y = this.posUltimo[1] + 1;
            }
            else{
                y = this.posUltimo[1] - 1;
            }
        }
        this.ataqueStandard(x, y, adversario);
        if(!this.erro1){
            this.erro1 = !(adversario.getTabuleiro().getTabuleiroPublico()[x][y] == 'X');
            if(this.erro1){
                this.posUltimo = this.posInicalAcerto;
                this.sentido = !(this.sentido);
            }
        }
        else{
            this.erro2 = !(adversario.getTabuleiro().getTabuleiroPublico()[x][y] == 'X');
        }
        if(this.erro2){
            this.terceiro = false;
            this.erro1 = false;
            this.erro2 = false;
        }
    }

    private void segundoPasso(Jogador adversario) {
        int x, y;
        Random r = new Random();
        x = this.posInicalAcerto[0];
        y = this.posInicalAcerto[1];
        while (this.posJaAtacada(x, y, adversario)) {
            this.inimigoVertical = r.nextBoolean();
            this.sentido = r.nextBoolean();
            if (this.inimigoVertical) {
                y = this.posInicalAcerto[1];
                if (this.posInicalAcerto[0] == 6) {
                    this.sentido = false;
                }
                if (this.posInicalAcerto[0] == 0) {
                    this.sentido = true;
                }
                if (this.sentido) {
                    x = this.posInicalAcerto[0] + 1;
                } else {
                    x = this.posInicalAcerto[0] - 1;
                }
            } else {
                x = this.posInicalAcerto[0];
                if (this.posInicalAcerto[1] == 6) {
                    this.sentido = false;
                }
                if (this.posInicalAcerto[1] == 0) {
                    this.sentido = true;
                }
                if (this.sentido) {
                    y = this.posInicalAcerto[1] + 1;
                } else {
                    y = this.posInicalAcerto[1] - 1;
                }
            }
        }
        this.ataqueStandard(x, y, adversario);
        this.terceiro = adversario.getTabuleiro().getTabuleiroPublico()[x][y] == 'X';
        if(this.terceiro){
            this.segundo = false;
        }
    }

    private void primeiroPasso(Jogador adversario) {
        int x, y;
        Random r = new Random();
        x = r.nextInt(7);
        y = r.nextInt(7);
        while (this.posJaAtacada(x, y, adversario)) {
            x = r.nextInt(7);
            y = r.nextInt(7);
        }

        this.ataqueStandard(x, y, adversario);
        this.segundo = adversario.getTabuleiro().getTabuleiroPublico()[x][y] == 'X';
        if (this.segundo) {
            this.posInicalAcerto = this.posUltimo;
        }
    }

    private void ataqueStandard(int x, int y, Jogador adversario) {
        int[] arr = new int[2];
        super.realizarJogada(x, y, adversario, this.gerarBomba());
        arr[0] = x;
        arr[1] = y;
        this.posUltimo = arr;
    }

    private boolean posJaAtacada(int x, int y, Jogador adversario) {
        char c;
        c = adversario.getTabuleiro().getTabuleiroPublico()[x][y];
        return c != '~';
    }

    private boolean gerarBomba() {
        boolean bomba;
        Random r = new Random();
        if (this.getTemBomba()) {
            bomba = r.nextBoolean();
        } else {
            bomba = false;
        }
        return bomba;
    }

}
