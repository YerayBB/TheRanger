package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.FutureExtraDrawPower;

import static theRanger.DefaultMod.makeCardPath;

public class DriveBy extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DriveBy.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("DriveBy.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int EXTRADRAW = 1;
    private static final int SHOTAMOUNT = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/


    public DriveBy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction[] queue;
        if(this.upgraded){
            queue = new AbstractGameAction[]{
                    new ApplyPowerAction(p,p, new FutureExtraDrawPower(p,p,EXTRADRAW),EXTRADRAW),
                    new DrawCardAction(EXTRADRAW),
                    new DamageAction(m,new DamageInfo(p, this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY)};
        }
        else{
            queue = new AbstractGameAction[]{
                    new ApplyPowerAction(p,p, new FutureExtraDrawPower(p,p,EXTRADRAW),EXTRADRAW),
                    new DamageAction(m,new DamageInfo(p, this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY)};
        }
        addToBot(new StandardShotAction(SHOTAMOUNT, queue));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeBaseCost(UPGRADED_COST);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
