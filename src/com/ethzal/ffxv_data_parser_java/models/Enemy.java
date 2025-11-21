/*
 * File: Enemy.java
 * Funcion: Clase de modelo para representar los datos de un enemigo.
 *
 * Autor: Ethzal
 * Version: 1.0
 */

package com.ethzal.ffxv_data_parser_java.models;

public class Enemy {
    private final long id;
    private final String name;
    private final int spirit;
    private final int hp;
    private final int magic;
    private final int attack;
    private final int defense;
    private final int exp;
    private final float speed1;
    private final float speed2;
    private final float speed3;
    private final float poise;

    public Enemy(long id, String name, int spirit, int hp, int magic, int attack, int defense, int exp, float speed1, float speed2, float speed3, float poise) {
        this.id = id;
        this.name = name;
        this.spirit = spirit;
        this.hp = hp;
        this.magic = magic;
        this.attack = attack;
        this.defense = defense;
        this.exp = exp;
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.speed3 = speed3;
        this.poise = poise;
    }

    // Getters para acceder a los datos
    public long getId() { return id; }
    public String getName() { return name; }
    public int getSpirit() { return spirit; }
    public int getHp() { return hp; }
    public int getMagic() { return magic; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getExp() { return exp; }
    public float getSpeed1() { return speed1; }
    public float getSpeed2() { return speed2; }
    public float getSpeed3() { return speed3; }
    public float getPoise() { return poise; }

    // Representación legible del objeto Enemy con sus stats base
    @Override
    public String toString() {
        return "com.ethzal.ffxv_tooling.models.Enemy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", exp=" + exp +
                '}';
    }
}