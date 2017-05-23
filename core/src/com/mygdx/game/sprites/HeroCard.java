package com.mygdx.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


import java.lang.*;

public class HeroCard extends Card {

    public HeroCard(float x, float y, int numberOfPlayer)
    {

        super(x, y);
        this.numberOfPlayer = numberOfPlayer;
        setStats();
        superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/simpleHit.mp3"));
    }

    public int ANGLETEST;

    public Sound using = Gdx.audio.newSound(Gdx.files.internal("sounds/cardUsing.mp3"));

    private TypeOfHeroCard typeOfHeroCard;

    public Sound superSkillSound;
    public Sound typicalHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/simpleHit.mp3"));

    private Texture manaForUseTexture;

    private Texture name;

    private Texture characterTexture;

    private Texture typeOfHeroSkillTexture;
    public Texture effectTexture = new Texture("skillEffects/dflt.png");


    private int healthPoints;
    private  int healthPool;

    private int armor;
    private int damage;

    private TypeOfSuperSkillsHero superSkill;

    private String superSkillDescription;

    private int intSuperSkill = (int) (Math.random() * 5 + 1);

    private int criticalChance = (int) (Math.random() * 40 + 15);
    private int criticalFactor = 100 + 400 - (criticalChance-15) * 10;
    private int percentOfVampirism = (int) (Math.random() * 10+20);
    private int percentToBlockDamage = (int) (Math.random() * 20 +20);
    private double blockFactor = (300 - (percentToBlockDamage-20)) / 10;
    private double percentOfRage = Math.random() * 10 + 1;

    public float transparency = 100;
    private float velocityOfTransparency = 100;


    private void setStats() {
        int typeOfHero = (int) (Math.random() * 3 + 1);
        switch (typeOfHero) {
            case 1:
                typeOfHeroCard = TypeOfHeroCard.FORCE;
                cardTexture = new Texture("cards/forceCards.png");
                healthPoints = (int) (Math.random() * 420);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 23);
                damage = (int) (Math.random() * 75);
                manaForUse = (healthPoints / 40 + armor / 2 + damage / 7) / 3;
                healthPoints += 60;
                armor += 5;
                damage += 20;
                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/force_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                            name = new Texture("cards/namesOfCards/meleeWarrior.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/force_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        name = new Texture("cards/namesOfCards/meleeKnight.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/force_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        name = new Texture("cards/namesOfCards/meleeBerserk.png");
                        break;
                    default: characterTexture = new Texture("characters/people_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        name = new Texture("cards/namesOfCards/peasant.png");
                }
                break;
            case 2:
                typeOfHeroCard = TypeOfHeroCard.AGILITY;
                cardTexture = new Texture("cards/agilityCards.png");
                healthPoints =(int) (Math.random() * 260);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 55);
                damage = (int) (Math.random() * 105);
                manaForUse = (healthPoints / 25 + armor / 5 + damage / 10) / 3;
                healthPoints += 40;
                armor += 20;
                damage += 30;
                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/agility_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                            name = new Texture("cards/namesOfCards/thiefJostler.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/agility_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        name = new Texture("cards/namesOfCards/thiefAssasin.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/agility_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        name = new Texture("cards/namesOfCards/thiefRinglader.png");
                        break;
                    default: characterTexture = new Texture("characters/people_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        name = new Texture("cards/namesOfCards/peasant.png");
                }

                break;
            case 3:
                typeOfHeroCard = TypeOfHeroCard.INTELLIGENCE;
                cardTexture = new Texture("cards/intelligenceCards.png");
                healthPoints = (int) (Math.random() * 150);
                healthPool = healthPoints;
                armor = (int) (Math.random() * 23);
                damage = (int) (Math.random() * 190);
                manaForUse=(healthPoints / 14 + armor / 2 + damage / 18) / 3;
                healthPoints += 25;
                armor += 7;
                damage += 50;
                switch (manaForUse){
                    case 1:
                    case 2:
                    case 3:
                    case 4: characterTexture = new Texture("characters/intelegence_character1.png");
                            cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                            name = new Texture("cards/namesOfCards/wizardRokkie.png");
                        break;
                    case 5:
                    case 6:
                    case 7: characterTexture = new Texture("characters/intelegence_character2.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/rareWallCard.png");
                        name = new Texture("cards/namesOfCards/wizardMaster.png");
                        break;
                    case 8:
                    case 9:
                    case 10: characterTexture = new Texture("characters/intelegence_character3.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/mythticalWallCard.png");
                        name = new Texture("cards/namesOfCards/wizardWisest.png");
                        break;
                    default: characterTexture = new Texture("characters/people_character.png");
                        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
                        name = new Texture("cards/namesOfCards/peasant.png");
                }

                break;
        }
        switch (intSuperSkill) {
            case 1: superSkill = TypeOfSuperSkillsHero.VAMPIRE;
                    superSkillDescription = "Return " + percentOfVampirism + "% of \ninflicted damage.";
                    typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_VAMPIRE.png");
                    superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/vampireSound.mp3"));
                break;
            case 2: superSkill = TypeOfSuperSkillsHero.BLOCKINGDAMAGE;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_BLOCKINGDAMAGE.png");
                superSkillDescription = "Have " + percentToBlockDamage + "% chance \nto block " + blockFactor + "%\nof taken damage.";
                superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/blockDamage.mp3"));
                break;
            case 3: superSkill = TypeOfSuperSkillsHero.INCONSTANTOFSTATS;
                superSkillDescription = "Change shape\nevery hit.";
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_INCONSTANTSTATS.png");
                superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/inconstanSound.mp3"));
                break;
            case 4: superSkill = TypeOfSuperSkillsHero.ADDDAMAGEBYLOWHEALTHPOINT;
                superSkillDescription = "Health decreases,\ndamage increases.";
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_ADDDAMAGEBYLOWHP.png");
                superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/blockDamage.mp3"));
                break;
            case 5: superSkill = TypeOfSuperSkillsHero.CRITICALHIT;
                typeOfHeroSkillTexture = new Texture("typeOfHeroSkill_pins/TypeOfHeroSkill_CRITICALHIT.png");
                superSkillDescription = "Have " + criticalChance + "% to inflict \n" + criticalFactor + "% increased \ndamage.";
                superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/criticalHit.mp3"));
                break;
        }
        if (numberOfPlayer == 2)
            switch (manaForUse){
                case 0: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/0mana.png");break;
                case 1: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/1mana.png");break;
                case 2: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/2mana.png");break;
                case 3: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/3mana.png");break;
                case 4: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/4mana.png");break;
                case 5: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/5mana.png");break;
                case 6: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/6mana.png");break;
                case 7: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/7mana.png");break;
                case 8: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/8mana.png");break;
                case 9: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/9mana.png");break;
                case 10: manaForUseTexture = new Texture("manaFolder/cardManaPhoenix/10mana.png");break;
            }
                   else
            switch (manaForUse){
                case 0: manaForUseTexture = new Texture("manaFolder/cardManaTiger/0mana.png");break;
                case 1: manaForUseTexture = new Texture("manaFolder/cardManaTiger/1mana.png");break;
                case 2: manaForUseTexture = new Texture("manaFolder/cardManaTiger/2mana.png");break;
                case 3: manaForUseTexture = new Texture("manaFolder/cardManaTiger/3mana.png");break;
                case 4: manaForUseTexture = new Texture("manaFolder/cardManaTiger/4mana.png");break;
                case 5: manaForUseTexture = new Texture("manaFolder/cardManaTiger/5mana.png");break;
                case 6: manaForUseTexture = new Texture("manaFolder/cardManaTiger/6mana.png");break;
                case 7: manaForUseTexture = new Texture("manaFolder/cardManaTiger/7mana.png");break;
                case 8: manaForUseTexture = new Texture("manaFolder/cardManaTiger/8mana.png");break;
                case 9: manaForUseTexture = new Texture("manaFolder/cardManaTiger/9mana.png");break;
                case 10: manaForUseTexture = new Texture("manaFolder/cardManaTiger/10mana.png");break;
            }
    }





    public void hit(HeroCard enemy){
        typicalHitSound.play();
        enemy.effectTexture = new Texture("skillEffects/simpleAttack.png");
        if((int)(Math.random() * 100 + 1) > enemy.armor)
        switch (superSkill) {
            case VAMPIRE: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.percentToBlockDamage > Math.random() * 100 + 1)) {
                    enemy.healthPoints =(int) (enemy.healthPoints - this.damage + damage * (enemy.blockFactor / 10));
                    this.healthPoints = this.healthPoints + this.damage * this.percentOfVampirism;
                    if (this.healthPoints > healthPool) healthPoints = healthPool;
                    enemy.effectTexture = new Texture("skillEffects/blockDmgEffect.png");

                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage;
                    this.healthPoints = this.healthPoints + this.damage * this.percentOfVampirism;
                    if (this.healthPoints > healthPool) healthPoints = healthPool;
                }
                 superSkillSound.play();
                 effectTexture = new Texture("skillEffects/vampireEffect.png");
                break;
            }
            case BLOCKINGDAMAGE: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.percentToBlockDamage > Math.random() * 100 + 1)) {
                   enemy.healthPoints = (int) (enemy.healthPoints - this.damage + damage * (enemy.blockFactor / 10));
                    enemy.effectTexture = new Texture("skillEffects/blockDmgEffect.png");
                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage;
                }
                break;
            }
            case INCONSTANTOFSTATS: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.percentToBlockDamage > Math.random() * 100 + 1)) {
                    enemy.healthPoints =(int) (enemy.healthPoints - this.damage + damage * (enemy.blockFactor / 10));
                    enemy.effectTexture = new Texture("skillEffects/blockDmgEffect.png");
                } else {
                    enemy.healthPoints = enemy.healthPoints - this.damage;
                }
                this.setStats();
                superSkillSound.play();
                break;
            }
            case ADDDAMAGEBYLOWHEALTHPOINT: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.percentToBlockDamage > Math.random() * 100 + 1)) {
                    if (healthPool / healthPoints * 100 < 30)
                        enemy.healthPoints =(int) (enemy.healthPoints - (this.damage * (healthPool / healthPoints + 1)) + this.damage * (enemy.blockFactor/10));
                    else
                        enemy.healthPoints = (int)(enemy.healthPoints - this.damage + this.damage * (enemy.blockFactor / 10));
                    enemy.effectTexture = new Texture("skillEffects/blockDmgEffect.png");
                } else {
                    if (healthPool / healthPoints * 100 < 30)
                        enemy.healthPoints = enemy.healthPoints - (this.damage * (healthPool / healthPoints + 1));
                    else enemy.healthPoints = enemy.healthPoints - this.damage;
                }
                break;
            }
            case CRITICALHIT: {
                if ((enemy.superSkill == TypeOfSuperSkillsHero.BLOCKINGDAMAGE) && (enemy.percentToBlockDamage > Math.random() * 100 + 1)) {
                    enemy.effectTexture = new Texture("skillEffects/blockDmgEffect.png");
                    int chance = (int) (Math.random() * 100 + 1);
                    if (chance > criticalChance)
                        enemy.healthPoints = (int) (enemy.healthPoints - this.damage + this.damage * (enemy.blockFactor / 10));
                    else {
                        enemy.healthPoints = (int) (enemy.healthPoints - (this.damage * criticalFactor) + this.damage * (enemy.blockFactor / 10));
                        superSkillSound.play();
                        effectTexture = new Texture("skillEffects/criticalHitEffect.png");
                    }
                } else {
                    int chance = (int) (Math.random() * 100 + 1);
                    if (chance > criticalChance)
                        enemy.healthPoints = enemy.healthPoints - this.damage;
                    else {
                        enemy.healthPoints = enemy.healthPoints - (this.damage * criticalFactor);
                        superSkillSound.play();
                        effectTexture = new Texture("skillEffects/criticalHitEffect.png");
                    }
                }
            }
        }
        else  {
            enemy.superSkillSound = Gdx.audio.newSound(Gdx.files.internal("sounds/blockDamage.mp3"));
            enemy.healthPoints -= damage;
            enemy.effectTexture = new Texture("skillEffects/skillAvoidEffect.png");
            enemy.superSkillSound.play();
         }
        this.haveUsed = true;
        if (enemy.healthPoints <= 0) enemy.setStatusOfCard("Is Dead");
        this.healthPoints -= enemy.damage;
        if (this.healthPoints <= 0) this.setStatusOfCard("Is Dead");
        enemy.transparency = 0;
        enemy.velocityOfTransparency = 0;
        transparency = 0;
        velocityOfTransparency = 0;
        typicalHitSound.play();
    }

    public void setTransparencyForEffects( float dt, float accelerationOfTransparency) {
        if (transparency < 100) {
            velocityOfTransparency = velocityOfTransparency + accelerationOfTransparency;
            velocityOfTransparency = velocityOfTransparency * dt;
            if (transparency + velocityOfTransparency < 100)
                transparency = transparency + velocityOfTransparency;
            else
                transparency = 100;
            velocityOfTransparency = velocityOfTransparency * (1 / dt);

        } else transparency = 100;
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

    public Texture getManaForUseTexture() {return manaForUseTexture;}

    public Texture getName() {return name;}

    public String getSuperSkillDescription() {return superSkillDescription;}




}

