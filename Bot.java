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
public class Bot extends Jogador implements Serializable{
    
    private int xUltimo, yUltimo;
    private boolean acertouUltimo, navioInimigoVertical;
    
    public Bot() {
        super("Javaligor");
        this.xUltimo = -1;
        this.yUltimo = -1;
        this.acertouUltimo = false;
    }

    public void realizarJogada(Jogador adversario) {
        boolean bomba = this.gerarBomba();
        int[] arr = this.gerarPosicoesDeAtaque(adversario, bomba);
        super.realizarJogada(arr[0], arr[1], adversario, bomba);
        this.acertouUltimo = (adversario.getTabuleiro().getTabuleiroPublico()[arr[0]][arr[1]] == 'X');
        this.xUltimo = arr[0];
        this.yUltimo = arr[1];
    }
    
    private int[] gerarPosicoesDeAtaque(Jogador adversario, boolean bomba){
        int[] arr = new int[2];
        int x, y;
        char c;
        Random r = new Random();
        x = r.nextInt(7);
        y = r.nextInt(7);
        c = adversario.getTabuleiro().getTabuleiroPublico()[x][y];
        while (c != '~' && !bomba) {
            x = r.nextInt(7);
            y = r.nextInt(7);
            c = adversario.getTabuleiro().getTabuleiroPublico()[x][y];
        }
        arr[0] = x;
        arr[1] = y;
        return arr;
    }
    
    private boolean gerarBomba(){
        boolean bomba;
        Random r = new Random();
        if (this.getTemBomba()) {
            bomba = r.nextBoolean();
        }
        else{
            bomba = false;
        }
        return bomba;
    }
    
}
