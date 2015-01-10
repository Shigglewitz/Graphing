package org.shigglewitz.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.tilemap.TileMap;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    private boolean firing;
    private final int fireCost;
    private final int fireballDamage;
    private final List<FireBall> fireBalls;

    private boolean scratching;
    private final int scratchDamage;
    private final int scratchRange;

    private boolean gliding;

    private final List<BufferedImage[]> sprites;
    private final int[] numFrames = { 2, 8, 1, 2, 4, 2, 5 };

    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;

    public Player(TileMap tm) {
        super(tm);

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;
        fire = maxFire = 2500;

        fireCost = 200;
        fireballDamage = 5;
        fireBalls = new ArrayList<>();

        scratchDamage = 8;
        scratchRange = 40;

        sprites = new ArrayList<>();

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass()
                    .getResourceAsStream(Config.PLAYER_SPRITE));

            for (int i = 0; i < 7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for (int j = 0; j < numFrames[i]; j++) {
                    if (i != 6) {
                        bi[j] = spriteSheet.getSubimage(j * width, i * height,
                                width, height);
                    } else {
                        bi[j] = spriteSheet.getSubimage(j * width * 2, i
                                * height, width * 2, height);
                    }
                }
                sprites.add(bi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
        currentAction = IDLE;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getFire() {
        return fire;
    }

    public int getMaxFire() {
        return maxFire;
    }

    public void setFiring() {
        firing = true;
    }

    public void setScratching() {
        scratching = true;
    }

    public void setGliding(boolean b) {
        gliding = b;
    }

    public void hit(int damage) {
        if (dead || flinching) {
            return;
        }

        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }

        flinching = true;
        flinchTimer = System.nanoTime();
    }

    private void getNextPosition() {
        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        // cannot move while attacking except if in air
        if ((currentAction == SCRATCHING || currentAction == FIREBALL)
                && !(jumping || falling)) {
            dx = 0;
        }

        // jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        if (falling) {
            if (dy > 0 && gliding) {
                dy += fallSpeed * 0.1;
            } else {
                dy += fallSpeed;
            }

            if (dy > 0) {
                jumping = false;
            }
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
    }

    public void update() {
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check attack has stopped
        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) {
                scratching = false;
            }
        }
        if (currentAction == FIREBALL) {
            if (animation.hasPlayedOnce()) {
                firing = false;
            }
        }

        // fire attack
        fire += 1;
        if (fire > maxFire) {
            fire = maxFire;
        }
        if (firing && currentAction != FIREBALL) {
            if (fire > fireCost) {
                fire -= fireCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                fireBalls.add(fb);
            }
        }

        // update fireballs
        for (int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).update();
            if (fireBalls.get(i).shouldRemove()) {
                fireBalls.remove(i);
                i--;
            }
        }

        // check flinching
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }

        // set animations
        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 60;
            }
        } else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dy > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            } else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }

        animation.update();

        // set direction
        if (currentAction != FIREBALL && currentAction != SCRATCHING) {
            if (right) {
                facingRight = true;
            }
            if (left) {
                facingRight = false;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        setMapPosition();

        // draw fireballs
        for (FireBall f : fireBalls) {
            f.draw(g);
        }

        // draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);
    }

    public void checkAttack(List<Enemy> enemies) {
        for (Enemy e : enemies) {
            // check scratch attack
            if (scratching) {
                if (facingRight) {
                    if (e.getX() > x && e.getX() < x + scratchRange
                            && e.getY() > y - height / 2
                            && e.getY() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (e.getX() < x && e.getX() > x - scratchRange
                            && e.getY() > y - height / 2
                            && e.getY() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }

            // check fireballs
            for (FireBall f : fireBalls) {
                if (f.intersects(e)) {
                    e.hit(fireballDamage);
                    f.setHit();
                }
            }

            // check enemy collision
            if (intersects(e)) {
                hit(e.getDamage());
            }
        }
    }
}
