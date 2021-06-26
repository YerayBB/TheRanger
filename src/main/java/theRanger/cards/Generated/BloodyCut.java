package theRanger.cards.Generated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DualWieldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.HeavyWoundAttackAction;
import theRanger.cards.AbstractDynamicCard;

import static theRanger.DefaultMod.makeCardPath;

public class BloodyCut extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BloodyCut.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("BloodyCut.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS; //TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/


    public BloodyCut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.exhaust = true;
        //this.cardsToPreview = new BloodyCut(true, this.upgraded);
    }

    public BloodyCut(boolean ethereal, boolean isUpgrade){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.exhaust = true;
        this.isEthereal = ethereal;
        //this.cardsToPreview = new BloodyCut(true, this.upgraded);
        if(ethereal){
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
        }
        if(isUpgrade) this.upgrade();
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HeavyWoundAttackAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        AbstractCard c = new BloodyCut(true, this.upgraded);
        addToBot(new MakeTempCardInHandAction(c,1));
    }




    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeBaseCost(UPGRADED_COST);
            //this.cardsToPreview.upgrade();
            this.initializeDescription();
        }
    }
}
