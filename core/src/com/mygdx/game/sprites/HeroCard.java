package com.mygdx.game.sprites;


import com.badlogic.gdx.graphics.Texture;


import java.lang.*;

public class HeroCard extends Card {

    public HeroCard(float x, float y, int numberOfPlayer)
    {
        super(x, y);
        this.numberOfPlayer = numberOfPlayer;
        setStats();

    }

    public int ANGLETEST;

    private TypeOfHeroCard typeOfHeroCard;


    private Texture characterTexture;

    private Texture typeOfHeroSkillTexture;

    private int healthPoints;
    private  int healthPool;

    private int armor;
    private int damage;

    private TypeOfSuperSkillsHero superSkill;
    private int intSuperSkill = (int) (Math.random() * 5 + 1);

    private int criticalChance = (int) (Math.random() * 65 + 15);
    private int criticalFactor = 800 - (criticalChance-15) * 10;
    private int procentOfVampirism = (int) (Math.random()*60+20);
    private int procentToBlockDamage = (int) (Math.random() * 20 +20);
    public double blockFactor = (70 - (procentToBlockDamage-20)) / 10;


    private void setStats() {
        int typeOfHero = (int) (Math.random() * 3 + 1);
        switch (typeOfHero) {
            case 1:
                typeOfHeroCard = TypeOfHeroCard.FORCE;
                cardTexture = new Texture("cards/forceCards.png");
                healthPoints = (int) (Math.random() * 501 + 50);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 5 + 1);
                damage = (int) (Math.random() * 50 + 1);
                manaForUse = (healthPoints / 100 + armor + damage / 10) / 3 * 2;
                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/force_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/force_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/force_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        break;
                    default: characterTexture = new Texture("characters/lox_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                }
                break;
            case 2:
                typeOfHeroCard = TypeOfHeroCard.AGILITY;
                cardTexture = new Texture("cards/agilityCards.png");
                healthPoints =(int) (Math.random() * 251 + 40);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 21 + 5);
                damage = (int) (Math.random() * 91 + 20);
                manaForUse = (healthPoints / 50 + armor / 5 + damage / 20) / 3 * 2;
                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/agility_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/agility_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/agility_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        break;
                    default: characterTexture = new Texture("characters/lox_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                }

                break;
            case 3:
                typeOfHeroCard = TypeOfHeroCard.INTELLIGENCE;
                cardTexture = new Texture("cards/intelligenceCards.png");
                healthPoints = (int) (Math.random() * 141 + 20);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 5 + 1);
                damage = (int) (Math.random() * 181 + 30);
                manaForUse=(healthPoints / 30 + armor + damage / 40) / 3 * 2;

                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/intelegence_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/intelegence_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/intelegence_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        break;
                    default: characterTexture = new Texture("characters/lox_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                }

                break;
        }
        switch (intSuperSkill) {
            case 1: superSkill = TypeOfSuperSkillsHero.VAMPIRE;
                    typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_VAMPIRE.png");
                break;
            case 2: superSkill = TypeOfSuperSkillsHero.BLOCKINGDAMAGE;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_BLOCKINGDAMAGE.png");
                break;
            case 3: superSkill = TypeOfSuperSkillsHero.INCONSTANTOFSTATS;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_INCONSTANTSTATS.png");
                break;
            case 4: superSkill = TypeOfSuperSkillsHero.ADDDAMAGEBYLOWHEALTHPOINT;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_ADDDAMAGEBYLOWHP.png");
                break;
            case 5: superSkill = TypeOfSuperSkillsHero.CRITICALHIT;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_CRITICALHIT.png");
                break;
        }
    }





    public void hit(HeroCard enemy){
        switch (superSkill) {
            case VAMPIRE: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.procentToBlockDamage > Math.random() * 100 + 1)) {
                    enemy.healthPoints =(int) (enemy.healthPoints - this.damage + enemy.armor + damage * (enemy.blockFactor / 10));
                    this.healthPoints = this.healthPoints + this.damage * this.procentOfVampirism;
                    if (this.healthPoints > healthPool) healthPoints = healthPool;
                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                    this.healthPoints = this.healthPoints + this.damage * this.procentOfVampirism;
                    if (this.healthPoints > healthPool) healthPoints = healthPool;
                }
                break;
            }
            case BLOCKINGDAMAGE: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.procentToBlockDamage > Math.random() * 100 + 1)) {
                   enemy.healthPoints = (int) (enemy.healthPoints - this.damage + enemy.armor+damage * (enemy.blockFactor / 10));
                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                }
                break;
            }
            case INCONSTANTOFSTATS: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.procentToBlockDamage > Math.random() * 100 + 1)) {
                    enemy.healthPoints =(int) (enemy.healthPoints - this.damage + damage * (enemy.blockFactor / 10));
                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                }
                this.setStats();
                break;
            }
            case ADDDAMAGEBYLOWHEALTHPOINT: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.procentToBlockDamage > Math.random() * 100 + 1)) {
                    if (healthPool / healthPoints * 100 < 30)
                        enemy.healthPoints =(int) (enemy.healthPoints - (this.damage * (healthPool / healthPoints + 1)) + this.damage * (enemy.blockFactor/10));
                    else
                        enemy.healthPoints = (int)(enemy.healthPoints - this.damage + this.armor + this.damage * (enemy.blockFactor / 10));
                } else {
                    if (healthPool / healthPoints * 100 < 30)
                        enemy.healthPoints = enemy.healthPoints - (this.damage * (healthPool / healthPoints + 1));
                    else enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                }
                break;
            }
            case CRITICALHIT: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.procentToBlockDamage > Math.random() * 100 + 1)) {
                    int chance = (int) (Math.random() * 100 + 1);
                    if (chance > criticalChance)
                        enemy.healthPoints = (int)(enemy.healthPoints - this.damage + this.armor + this.damage * (enemy.blockFactor / 10));
                    else
                       enemy.healthPoints =(int) (enemy.healthPoints - (this.damage * criticalFactor) + this.armor + this.damage * (enemy.blockFactor/10));
                } else {
                    int chance = (int) (Math.random() * 100 + 1);
                    if (chance > criticalChance)
                        enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                    else
                        enemy.healthPoints = enemy.healthPoints - (this.damage * criticalFactor) + this.armor;
                }
                break;
            }
        }
        this.haveUsed = true;
        if (enemy.healthPoints<=0) enemy.setStatusOfCard("Is Dead");
    }

    public void hitThePlayer(Player enemy){
        switch (intSuperSkill) {
            case 1: {
                enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                this.healthPoints = this.healthPoints + this.damage * this.procentOfVampirism;
                if (this.healthPoints > healthPool) healthPoints = healthPool;
                break;
            }
            case 3: {
                enemy.healthPoints = enemy.healthPoints - this.damage;
                this.setStats();
                break;
            }
            case 4: {
                if (healthPool / healthPoints * 100 < 30)
                    enemy.healthPoints = enemy.healthPoints - (this.damage * (healthPool / healthPoints + 1));
                else enemy.healthPoints = enemy.healthPoints - this.damage;
                break;
            }
            case 5: {
                int chance = (int) (Math.random() * 100 + 1);
                if (chance > criticalChance)
                    enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                else
                    enemy.healthPoints = enemy.healthPoints - (this.damage * criticalFactor) + this.armor;
                break;
            }
            case 2: {
                enemy.healthPoints = enemy.healthPoints - this.damage + this.armor;
                break;
            }
        }

        this.haveUsed = true;
        if (enemy.healthPoints <= 0) enemy.setStatusOfPlayer("Is Dead");

    }


    public int getHealthPoints() {return healthPoints;}

    public int getDamage() {return damage;}

    public int getHealthPool() {
        return healthPool;
    }

    public Texture getTypeOfHeroSkillTexture() {
        return typeOfHeroSkillTexture;
    }

    public Texture getCharacterTexture() {
        return characterTexture;
    }

    public Texture getCardBackgroundTexture() {return cardBackgroundTexture;}



}

