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
    
    public Bot() {
        super("Bot");
    }

    public void realizarJogada(Jogador adversario) {
        int x, y;
        boolean bomba = false;
        char c;
        Random r = new Random();
        if (this.getTemBomba()) {
            bomba = r.nextBoolean();
        }
        x = r.nextInt(7);
        y = r.nextInt(7);
        c = adversario.getTabuleiro().getTabuleiroPublico()[x][y];
        while (c != '~' && !bomba) {
            x = r.nextInt(7);
            y = r.nextInt(7);
            c = adversario.getTabuleiro().getTabuleiroPublico()[x][y];
        }
        super.realizarJogada(x, y, adversario, bomba);
    }
}
