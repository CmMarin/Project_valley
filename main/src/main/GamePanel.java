package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //size for player,npc, entities etc.
    final int scale = 3;
    final int tileSize = originalTileSize * scale; //actual size of tiles

    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //horizontal and vertical nr of tiles displayed
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //default pos player
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
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

        if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }else if (keyH.downPressed){
            playerY += playerSpeed;
        }else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }else if(keyH.rightPressed){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.green);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();
    }
}