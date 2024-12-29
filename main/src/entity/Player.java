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

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        hitbox = new Rectangle(8,25,gp.tileSize/2,gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 20;
        worldY = gp.tileSize * 20;
        speed = 8;
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
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

        if(keyH.upPressed){
            direction = "up";
//            worldY -= speed;
        }else if (keyH.downPressed){
            direction = "down";
//            worldY += speed;
        }else if(keyH.leftPressed){
            direction = "left";
//            worldX -= speed;
        }else if(keyH.rightPressed){
            direction = "right";
//            worldX += speed;
        }

        //Tile collision check
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if(!collisionOn){
            switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
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

        g2.drawImage(image, screenX, screenY, (gp.tileSize/8)*7, (gp.tileSize/8)*7, null);
    }
}
