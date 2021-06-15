package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.KukriPower;

import static theRanger.DefaultMod.makeCardPath;

public class SoulThieve extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SoulThieve.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("SoulThieve.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 0;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public SoulThieve() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        if(disarmedCheck()){
            addToBot(new ApplyPowerAction(p,p,new EssencePower(p,this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if(this.disarmedCheck()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else{
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
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

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.initializeDescription();
        }
    }
}
