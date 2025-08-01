package com.github.spookie6.frozen.utils.skyblock.dungeon;

import cc.polyfrost.oneconfig.config.core.OneColor;
import com.github.spookie6.frozen.utils.render.Color;
import com.github.spookie6.frozen.utils.StringUtils;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class DungeonEnums {
    public static class DungeonPlayer {
        public final String username;
        public EntityPlayer entity;
        public Class clazz;
        public int clazzLevel;
        public String numeral;
        public boolean isDead = false;
        public int deaths = 0;

        public DungeonPlayer(String username, Class clazz, int clazzlvl, EntityPlayer entity) {
            this.username = username;
            this.clazz = clazz;
            this.clazzLevel = clazzlvl;
            this.entity = entity;
            this.isDead = false;
            this.deaths = 0;
        }

        public void setClassName(String className) {
            this.clazz = Class.getClass(className);
        }

        public void setNumeral(String numeral) {
            this.numeral = numeral;
            this.clazzLevel = (byte) StringUtils.romanToDecimal(numeral);
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
    }

    public enum Class {
        ARCHER("Archer", Color.MINECRAFT_GOLD.getColor(), '6'),
        BERSERK("Berserk", Color.MINECRAFT_DARK_RED.getColor(), '4'),
        MAGE("Mage", Color.MINECRAFT_AQUA.getColor(), 'b'),
        TANK("Tank", Color.MINECRAFT_DARK_GREEN.getColor(), '2'),
        HEALER("Healer", Color.MINECRAFT_LIGHT_PURPLE.getColor(), 'd'),
        Unknown("(None)", Color.MINECRAFT_WHITE.getColor(), 'f');

        private String className;

        Class(String className, OneColor color, char colorCode) {
            this.className = className;
        }

        public String toString() {
            return this.className;
        }

        public static Class getClass(String className) {
            return Arrays.stream(Class.values()).filter(x -> x.toString().equals(className)).findFirst().orElse(Class.Unknown);
        }

        public boolean isClass(Class clazz) {
            return this.equals(clazz);
        }
        public boolean isNotClass(Class clazz) {
            return !this.equals(clazz);
        }
    }

    public enum Floor {
        E("E"),
        F1("F1"),
        F2("F2"),
        F3("F3"),
        F4("F4"),
        F5("F5"),
        F6("F6"),
        F7("F7"),
        M1("M1"),
        M2("M2"),
        M3("M3"),
        M4("M4"),
        M5("M5"),
        M6("M6"),
        M7("M7"),
        None(null);

        private String identifier;
        public int floorNumber;
        public boolean isMM;

        Floor(String identifier) {
            this.identifier = identifier;
            if (identifier == null) {
                floorNumber = -1;
            } else {
                this.floorNumber = identifier.replaceAll("^[MFmfE]*", "").isEmpty() ? 0 : Integer.parseInt(identifier.replaceAll("^[MFmfE]*", ""));
                this.isMM = identifier.contains("M");
            }
        }

        public String toString() {
            return this.identifier;
        }

        public static Floor getFloor(String match) {
            return Arrays.stream(Floor.values()).filter(x -> x.toString().equals(match)).findFirst().orElse(Floor.None);
        }

        public boolean isFloor(int n) {
            return this.floorNumber == n;
        }

        public boolean isExactMatch(Floor fl) {
            return this.equals(fl);
        }
    }

    public enum M7Phases {
        P1("P1"),
        P2("P2"),
        P3("P3"),
        P4("P4"),
        P5("P5"),
        UNKOWN("Unknown");

        private String identifier;

        M7Phases(String identifier) {
            this.identifier = identifier;
        }
    }

}