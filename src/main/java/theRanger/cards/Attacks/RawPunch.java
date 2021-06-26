package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.KukriPower;

import static theRanger.DefaultMod.makeCardPath;

public class RawPunch extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(RawPunch.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("RawPunch.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;

    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int MAGIC = 2;

    private static final int SECONDMAGIC = 2;
    private static final int UPGRADE_PLUS_SECONDMAGIC = 1;

    // /STAT DECLARATION/


    public RawPunch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p,p,new WeakPower(p,this.magicNumber,false),this.magicNumber));
    }


    @Override
    public void applyPowers() {
        if(this.disarmedCheck()) {
            AbstractPower vigor = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
            if (vigor != null) {
                vigor.amount *= this.defaultSecondMagicNumber;
            }

            super.applyPowers();
            if (vigor != null) {
                vigor.amount /= this.defaultSecondMagicNumber;
            }
        }
        else{
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if(this.disarmedCheck()) {
            AbstractPower vigor = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
            if (vigor != null) {
                vigor.amount *= this.defaultSecondMagicNumber;
            }

            super.calculateCardDamage(mo);
            if (vigor != null) {
                vigor.amount /= this.defaultSecondMagicNumber;
            }
        }
        else{
            super.calculateCardDamage(mo);
        }
    }

    private boolean disarmedCheck(){
        int aux1 = 0;
        int aux2 = 0;
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            aux1 = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
        }
        if(AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            aux2 = AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount;
        }
        return !(aux1 > 0 || aux2 > 0);
    }

    @Override
    public void triggerOnGlowCheck() {
        int aux1 = 0;
        int aux2 = 0;
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            aux1 = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
        }
        if(AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            aux2 = AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount;
        }
        if(aux1 > 0 || aux2 > 0) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }else{
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECONDMAGIC);
            this.initializeDescription();
        }
    }
}
