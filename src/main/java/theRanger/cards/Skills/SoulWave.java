package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.unique.SoulWaveAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.KukriPower;

import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class SoulWave extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SoulWave.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("SoulWave.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 3;

    private static final int MAGIC = 1;

    private static final int INFUSE = 2;

    private static final int SECONDMAGIC = 1;
    private static final int UPGRADE_PLUS_SECONDMAGIC = 1;

    // /STAT DECLARATION/


    public SoulWave() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseInfuseNumber = INFUSE;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SoulWaveAction(this.block, this.infuseNumber, this.magicNumber));
        this.baseMagicNumber += this.defaultSecondMagicNumber;
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount >= this.magicNumber){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else{
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        }else{
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECONDMAGIC);
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
