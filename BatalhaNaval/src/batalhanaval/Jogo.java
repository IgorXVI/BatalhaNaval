/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanaval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Jogo implements Serializable{

    private Jogador jogador;
    private Bot bot;
    private boolean comecou;

    Jogo() {
        this.comecou = false;
        this.iniciarMenu();
    }

    private void iniciarJogo() {
        String s;
        Scanner in = new Scanner(System.in);
        System.out.print("Informe o seu nome: ");
        s = in.nextLine();
        this.jogador = new Jogador(s);
        this.bot = new Bot();
        System.out.println("Almirante " + this.jogador.getNome() + ", esse é o seu tabuleiro:");
        this.jogador.getTabuleiro().mostrarTabuleiroDoJogador();
        System.out.println(
                "As letras a, b e c representam os seus navios, existem 3 navios com tamanhos 2(a), 3(b) e 4(c), o '~' representa a água.\n"
                + "A cada jogada você precisará informar as coordenadas x (-1 < x < 7) e y (-1 < y < 7) da posição que você acha que está um navio inimigo.\n"
                + "Você também pode utilizar uma bomba se você digitar 'bomba' antes de dar as coordenadas do ataque, mas escolha com sabedoria pois você possui apenas uma.\n"
                + "Quando você acertar um navio inimigo aparecerá um 'X' e quando você errar aperecerá um '*'.\n"
                + "Para retornar ao menu digite 'menu' onde é pedido as coordenadas de ataque.\n"
                + "O jogo termina quanto todos os navios de um dos lados estiverem destruídos.\n"
                + "Boa Sorte!\n"
        );
        this.executarJogo();
    }

    private void receberInputDoJogador() {
        int x, y;
        String s;
        boolean bomba = false;
        System.out.print("Almirante " + this.jogador.getNome()
                + ", informe as coordenadas do ataque! ");
        Scanner in = new Scanner(System.in);
        s = in.next();
        if (s.equals("menu")) {
            this.iniciarMenu();
        } else if (s.equals("bomba")) {
            if (!jogador.getTemBomba()) {
                System.out.println("Você não possui mais bombas!");
                this.receberInputDoJogador();
            } else {
                bomba = true;
                x = in.nextInt();
                y = in.nextInt();
                if (x > 6 || x < 0 || y > 6 || y < 0) {
                    System.out.println("Coordenadas inválidas!");
                    this.receberInputDoJogador();
                } else {
                    this.jogador.realizarJogada(x, y, this.bot, bomba);
                }
            }
        } else if (!Character.isDigit(s.charAt(0))) {
            System.out.println("Comando inválido!");
            this.receberInputDoJogador();
        } else {
            x = Character.getNumericValue(s.charAt(0));
            y = in.nextInt();
            if (x > 6 || x < 0 || y > 6 || y < 0) {
                System.out.println("Coordenadas inválidas!");
                this.receberInputDoJogador();
            } else if (this.bot.getTabuleiro().getTabuleiroPublico()[x][y] != '~') {
                System.out.println("Coordenadas já atacadas!");
                this.receberInputDoJogador();
            } else {
                this.jogador.realizarJogada(x, y, this.bot, bomba);
            }
        }
    }

    private void executarJogo() {
        this.comecou = true;
        boolean derrota1, derrota2;
        String s;
        Scanner in = new Scanner(System.in);
        derrota1 = this.jogador.getTabuleiro().todosNaviosDestruidos();
        derrota2 = this.bot.getTabuleiro().todosNaviosDestruidos();
        while (!derrota1 && !derrota2) {
            this.receberInputDoJogador();
            System.out.println("O Almirante " + this.bot.getNome() + " está realizando uma jogada...");
            bot.realizarJogada(jogador);
            derrota1 = this.jogador.getTabuleiro().todosNaviosDestruidos();
            derrota2 = this.bot.getTabuleiro().todosNaviosDestruidos();
        }
        if (derrota2) {
            System.out.println("Você Ganhou!");
        } else if (derrota1) {
            System.out.println("Você Perdeu!");
        } else {
            System.out.println("Empate!");
        }
        System.out.println("Digite 'menu' para voltar ao menu.");
        System.out.println("Digite qualquer outra coisa para terminar o jogo.");
        s = in.next();
        if (s.equals("menu")) {
            this.comecou = false;
            this.iniciarMenu();
        } else {
            System.exit(0);
        }
    }

    private void salvar() {
        System.out.print("Digite o nome do arquivo de save: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        try {
            File file = new File(s+".ser");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            f.close();
            o.close();
            System.out.println("Jogo salvo com sucesso!");
            this.receberInputDoMenu();
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível salvar o jogo.");
            this.receberInputDoMenu();
        }
    }

    public void continuarJogo() {
        System.out.println("Bem vindo de volta, Almirante " + this.jogador.getNome() + "!");
        System.out.println("Essas são as posições dos seus navios:");
        this.jogador.getTabuleiro().mostrarTabuleiroDoJogador();
        System.out.println("Essa é a sua situação na batalha:");
        this.jogador.getTabuleiro().mostrarTabuleiroPublico();
        System.out.println("Essa é a situação do seu inimigo na batalha:");
        this.bot.getTabuleiro().mostrarTabuleiroPublico();
        System.out.println("Boa sorte!");
        this.executarJogo();
    }

    public void iniciarMenu() {
        System.out.println("Bem vindo ao menu do jogo Batalha Naval 9000!");
        System.out.println("Digite 'novo' para começar um novo jogo.");
        System.out.println("Digite 'load' para dar load em um save.");
        System.out.println("Digite 'continuar' para continuar o jogo.");
        System.out.println("Digite 'save' para salvar o jogo.");
        System.out.println("Digite 'sair' para sair do jogo .");
        this.receberInputDoMenu();
    }

    private void receberInputDoMenu() {
        Scanner in = new Scanner(System.in);
        String s;
        s = in.next();
        switch (s) {
            case "novo":
                this.iniciarJogo();
                break;
            case "load":
                this.loadSave();
                break;
            case "save":
                if (this.comecou) {
                    this.salvar();
                } else {
                    System.out.println("Não existe nenhum jogo para salvar.");
                    this.receberInputDoMenu();
                }   break;
            case "continuar":
                if (this.comecou) {
                    this.executarJogo();
                } else {
                    System.out.println("Não existe nenhum jogo para continuar.");
                    this.receberInputDoMenu();
                }   break;
            case "sair":
                System.exit(0);
            default:
                System.out.println("Comando inválido!");
                this.receberInputDoMenu();
                break;
        }
    }

    private void loadSave() {
        System.out.print("Digite o nome do arquivo de save: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        try {
            File file = new File(s+".ser");
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);

            Jogo save = (Jogo) oi.readObject();
            oi.close();
            fi.close();

            this.jogador = save.getJogador();
            this.bot = save.getBot();
            this.comecou = save.getComecou();
            System.out.println("Load realizado com sucesso!");
            this.continuarJogo();
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível fazer load do save.");
            this.receberInputDoMenu();
        }
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public Bot getBot() {
        return this.bot;
    }

    public boolean getComecou(){
        return this.comecou;
    }
}
