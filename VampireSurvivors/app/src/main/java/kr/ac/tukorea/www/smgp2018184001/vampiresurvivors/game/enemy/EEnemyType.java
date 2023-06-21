package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.enemy;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.R;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.characters.Player;
import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game.flags.SpriteSize;

public enum EEnemyType {
    Bat, Skeleton, Ghost, Mantichana, LizardPawn, Count;

    int[] wave = {1, 4, 7, 10, 13};
    float[] speed = {
            Player.PLAYER_MOVEMENTSPEED * 0.4f,
            Player.PLAYER_MOVEMENTSPEED * 0.3f,
            Player.PLAYER_MOVEMENTSPEED * 0.3f,
            Player.PLAYER_MOVEMENTSPEED * 0.2f,
            Player.PLAYER_MOVEMENTSPEED * 0.4f,
    };

    int[] resIdsLeft = {
            R.mipmap.batdeathleft,
            R.mipmap.skeletondeathleft,
            R.mipmap.ghostdeathleft,
            R.mipmap.mantichanadeathleft,
            R.mipmap.lizardpawndeathleft,
            -1
    };
    int[] resIdsRight = {
            R.mipmap.batdeathright,
            R.mipmap.skeletondeathright,
            R.mipmap.ghostdeathright,
            R.mipmap.mantichanadeathright,
            R.mipmap.lizardpawndeathright,
            -1
    };
    float[] size = {
            SpriteSize.BAT_SIZE,
            SpriteSize.SKELETON_SIZE,
            SpriteSize.GHOST_SIZE,
            SpriteSize.MANTICHANA_SIZE,
            SpriteSize.LIZARDPAWN_SIZE,
            0
    };

    public int getWave() {
        return wave[this.ordinal()];
    }
    public float getSpeed() {
        return speed[this.ordinal()];
    }

    public int getResId(boolean isLeft) {
        if (isLeft) {
            return resIdsLeft[this.ordinal()];
        } else {
            return resIdsRight[this.ordinal()];
        }
    }

    public float getSize() {
        return size[this.ordinal()];
    }
}
