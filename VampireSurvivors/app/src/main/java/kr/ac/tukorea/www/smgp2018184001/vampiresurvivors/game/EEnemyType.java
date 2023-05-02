package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.game;

public enum EEnemyType {
    Bat, Skeleton, Ghost, Mantichana, LizardPawn, Count;

    int[] wave = {1,4,7,10,13};

    int getWave(){
        return wave[this.ordinal()];
    }

}
