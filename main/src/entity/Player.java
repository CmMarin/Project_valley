package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(new File("res/player/up1.png"));
            up2 = ImageIO.read(new File("res/player/up2.png"));
            down = ImageIO.read(new File("res/player/down.png"));
            left1 = ImageIO.read(new File("res/player/left1.png"));
            left2 = ImageIO.read(new File("res/player/left2.png"));
            right1 = ImageIO.read(new File("res/player/right1.png"));
            right2 = ImageIO.read(new File("res/player/right2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.upPressed == true){
            direction = "up";
            y -= speed;
        }else if (keyH.downPressed){
            direction = "down";
            y += speed;
        }else if(keyH.leftPressed){
            direction = "left";
            x -= speed;
        }else if(keyH.rightPressed){
            direction = "right";
            x += speed;
        }

        spriteCounter++;
        if(spriteCounter > 20){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.green);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down;
                }
                if(spriteNum == 2){
                    image = down;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
