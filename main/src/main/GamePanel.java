package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //size for player,npc, entities etc.
    final int scale = 5;
    public int tileSize = originalTileSize * scale; //actual size of tiles

    public int maxScreenCol = 16;
    public int maxScreenRow = 12; //horizontal and vertical nr of tiles displayed
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;


    //World settings

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public int worldWidth = tileSize * maxWorldCol;
    public int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
//game loop
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
//thread starting functionality
    @Override
    public void run() {
//updating and drawing

        double drawInterval = 1000000000/FPS;
        double nextDrawtime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();
            repaint();

            try {
                double remainingTime = nextDrawtime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawtime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){

       player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
