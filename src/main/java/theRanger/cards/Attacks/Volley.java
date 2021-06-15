package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.RandomEnemyDmgWithInfusion;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;

import static theRanger.DefaultMod.makeCardPath;

public class Volley extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Volley.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("Volley.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 4;

    private static final int INFUSE = 2;
    private static final int UPGRADE_PLUS_INFUSE = 2;

    private static final int SHOTAMOUNT = 1;
    private static final int TIMES = 3;

    // /STAT DECLARATION/


    public Volley() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseInfuseNumber = INFUSE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = TIMES; i>0;--i){
            addToBot(new StandardShotAction(SHOTAMOUNT,new AbstractGameAction[]{
                    new RandomEnemyDmgWithInfusion(this,this.infuseNumber)}));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeInfuseNumber(UPGRADE_PLUS_INFUSE);
            this.initializeDescription();
        }
    }
}
