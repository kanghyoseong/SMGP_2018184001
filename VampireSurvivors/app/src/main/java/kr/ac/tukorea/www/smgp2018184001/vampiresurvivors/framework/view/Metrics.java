package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.view;

public class Metrics {
    public static float scale = 1.0f;
    public static float game_width = 1.0f;
    public static float game_height = 1.0f;
    public static int x_offset = 0;
    public static int y_offset = 0;
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    public static void setGameSize(float width, float height) {
        game_width = width;
        game_height = height;
    }

    public static float toGameX(float x) {
        return (x - x_offset) / scale;
    }

    public static float toGameY(float y) {
        return (y - y_offset) / scale;
    }


    public static float toScreenX(float x) {
        return x * scale + x_offset;
    }

    public static float toScreenY(float y) {
        return y * scale + y_offset;
    }
}
