package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.RandomEnemyDmgWithInfusion;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.actions.brown.unique.ArrowHellAction;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;

import static theRanger.DefaultMod.makeCardPath;

public class ArrowHell extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ArrowHell.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("ArrowHell.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;

    private static final int SHOTAMOUNT = 1;

    private static final int DAMAGE = 1;
    private static final int UPGRADE_PLUS_DMG = 1;

    private static final int INFUSED = 1;
    private static final int UPGRADE_PLUS_INFUSED = 1;

    // /STAT DECLARATION/


    public ArrowHell() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseInfuseNumber = INFUSED;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /* V1
        int amount = 0;
        if(p.hasPower(EssencePower.POWER_ID)){
             amount = p.getPower(EssencePower.POWER_ID).amount;
        }
        if(amount != 0){
            applyPowers();
            for(int i = 0; i<amount; i++){
                addToBot(new StandardShotAction(amount,new AbstractGameAction[] {new RandomEnemyDmgWithInfusion(this, this.infuseNumber, AbstractGameAction.AttackEffect.SLASH_DIAGONAL)}));
            }
        }
         */
        addToBot(new ArrowHellAction(this.damage, this.infuseNumber, SHOTAMOUNT, p));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeInfuseNumber(UPGRADE_PLUS_INFUSED);
            initializeDescription();
        }
    }
}
