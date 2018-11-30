package fall18project.gamecentre.sliding_tiles;

import fall18project.gamecentre.R;

public class TileImage {

    /**
     * Return the id of the appropriate image
     *
     * @param i the background id
     * @return image_num the number of the image to be displayed
     */
    static int getTileimage_num(int i) {
        int image_num;
        switch (i + 1) {
            case 1:
                image_num = R.drawable.tile_1;
                break;
            case 2:
                image_num = R.drawable.tile_2;
                break;
            case 3:
                image_num = R.drawable.tile_3;
                break;
            case 4:
                image_num = R.drawable.tile_4;
                break;
            case 5:
                image_num = R.drawable.tile_5;
                break;
            case 6:
                image_num = R.drawable.tile_6;
                break;
            case 7:
                image_num = R.drawable.tile_7;
                break;
            case 8:
                image_num = R.drawable.tile_8;
                break;
            case 9:
                image_num = R.drawable.tile_9;
                break;
            case 10:
                image_num = R.drawable.tile_10;
                break;
            case 11:
                image_num = R.drawable.tile_11;
                break;
            case 12:
                image_num = R.drawable.tile_12;
                break;
            case 13:
                image_num = R.drawable.tile_13;
                break;
            case 14:
                image_num = R.drawable.tile_14;
                break;
            case 15:
                image_num = R.drawable.tile_15;
                break;
            case 16:
                image_num = R.drawable.tile_16;
                break;
            case 17:
                image_num = R.drawable.tile_17;
                break;
            case 18:
                image_num = R.drawable.tile_18;
                break;
            case 19:
                image_num = R.drawable.tile_19;
                break;
            case 20:
                image_num = R.drawable.tile_20;
                break;
            case 21:
                image_num = R.drawable.tile_21;
                break;
            case 22:
                image_num = R.drawable.tile_22;
                break;
            case 23:
                image_num = R.drawable.tile_23;
                break;
            case 24:
                image_num = R.drawable.tile_24;
                break;
            case 25:
                image_num = R.drawable.tile_25;
                break;
            case 26:
                image_num = R.drawable.tile_26;
                break;
            case 27:
                image_num = R.drawable.tile_27;
                break;
            case 28:
                image_num = R.drawable.tile_28;
                break;
            case 29:
                image_num = R.drawable.tile_29;
                break;
            case 30:
                image_num = R.drawable.tile_30;
                break;
            case 31:
                image_num = R.drawable.tile_31;
                break;
            case 32:
                image_num = R.drawable.tile_32;
                break;
            case 33:
                image_num = R.drawable.tile_33;
                break;
            case 34:
                image_num = R.drawable.tile_34;
                break;
            case 35:
                image_num = R.drawable.tile_35;
                break;
            case 36:
                image_num = R.drawable.tile_36;
                break;
            case 37:
                image_num = R.drawable.tile_37;
                break;
            case 38:
                image_num = R.drawable.tile_38;
                break;
            case 39:
                image_num = R.drawable.tile_39;
                break;
            case 40:
                image_num = R.drawable.tile_40;
                break;
            case 41:
                image_num = R.drawable.tile_41;
                break;
            case 42:
                image_num = R.drawable.tile_42;
                break;
            case 43:
                image_num = R.drawable.tile_43;
                break;
            case 44:
                image_num = R.drawable.tile_44;
                break;
            case 45:
                image_num = R.drawable.tile_45;
                break;
            case 46:
                image_num = R.drawable.tile_46;
                break;
            case 47:
                image_num = R.drawable.tile_47;
                break;
            case 48:
                image_num = R.drawable.tile_48;
                break;
            case 49:
                image_num = R.drawable.tile_empty;
                break;
            default:
                image_num = R.drawable.tile_empty;
        }
        return image_num;
    }
}
