package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

enum AtkType{
    MELEE, RANGE
}

public class Enemy extends Character{
    private int atk;
    private AtkType atkType;
    private int dropExp;
    private int spawnWave;

    public Enemy(float posX, float posY, float sizeX, float sizeY,
                  int resId, int spriteCountX, int spriteCountY, float secToNextFrame) {
        super(posX, posY, sizeX, sizeY,
                resId, spriteCountX, spriteCountY, secToNextFrame);
    }
}
