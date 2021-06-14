package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.actions.brown.generic.StandardStealthAction;
import theRanger.actions.brown.unique.CondensedShotAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.DensityPower;
import theRanger.powers.brown.EssencePower;

import static theRanger.DefaultMod.makeCardPath;

public class CondensedShot extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CondensedShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("CondensedShot.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 0;

    private static final int SHOTAMOUNT = 1;

    // /STAT DECLARATION/


    public CondensedShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        addToBot(new StandardShotAction(SHOTAMOUNT, new AbstractGameAction[]{
                new DamageAction(m, new DamageInfo(p, this.damage)),
                new RemoveSpecificPowerAction(p,p, EssencePower.POWER_ID),
                new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F)}));
        //addToBot(new CondensedShotAction(m, this.damage, SHOTAMOUNT));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = 0;
        if(AbstractDungeon.player.hasPower(EssencePower.POWER_ID) && AbstractDungeon.player.hasPower(DensityPower.POWER_ID)){
            this.baseDamage = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
            this.baseDamage *= AbstractDungeon.player.getPower(DensityPower.POWER_ID).amount;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        int realBaseDamage = this.baseDamage;
        this.baseDamage = 0;
        if(AbstractDungeon.player.hasPower(EssencePower.POWER_ID) && AbstractDungeon.player.hasPower(DensityPower.POWER_ID)){
            this.baseDamage = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
            this.baseDamage *= AbstractDungeon.player.getPower(DensityPower.POWER_ID).amount;
        }
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.rawDescription += this.damage + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount > SHOTAMOUNT) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}
