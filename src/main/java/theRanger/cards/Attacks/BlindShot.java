package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.RandomEnemyDmgWithInfusion;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;

import static theRanger.DefaultMod.makeCardPath;

public class BlindShot extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BlindShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("BlindShot.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 0;

    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;

    private static final int INFUSE = 3;
    private static final int UPGRADE_PLUS_INFUSE = 1;

    private static final int SHOTAMOUNT = 1;

    // /STAT DECLARATION/


    public BlindShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseInfuseNumber = INFUSE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new StandardShotAction(SHOTAMOUNT, new AbstractGameAction[] {new RandomEnemyDmgWithInfusion(this, this.infuseNumber, AbstractGameAction.AttackEffect.BLUNT_LIGHT)}));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeInfuseNumber(UPGRADE_PLUS_INFUSE);
            initializeDescription();
        }
    }
}
