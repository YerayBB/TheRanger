package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.ApolloPower;
import theRanger.powers.brown.DensityPower;
import theRanger.powers.brown.EssencePower;

import static theRanger.DefaultMod.makeCardPath;

public class ApollosFavor extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ApollosFavor.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("ApollosFavor.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    private static final int DAMAGE = 16;

    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    private static final int SHOTAMOUNT = 5;

    // /STAT DECLARATION/


    public ApollosFavor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int extra = this.baseMagicNumber;
        int density = 0;
        if(AbstractDungeon.player.hasPower(DensityPower.POWER_ID)){
            density = AbstractDungeon.player.getPower(DensityPower.POWER_ID).amount;
        }
        this.baseDamage += extra * density;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        int extra = this.baseMagicNumber;
        int density = 0;
        if(AbstractDungeon.player.hasPower(DensityPower.POWER_ID)){
            density = AbstractDungeon.player.getPower(DensityPower.POWER_ID).amount;
        }
        this.baseDamage += extra * density;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        addToBot(new StandardShotAction(SHOTAMOUNT,new AbstractGameAction[]{new ApplyPowerAction(m,p, new ApolloPower(m,p, this.damage), this.damage)}));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount >= SHOTAMOUNT) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
