package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.CleanIncreaseMiscAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.CamouflagePower;

import static theRanger.DefaultMod.makeCardPath;

public class Mimicry extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Mimicry.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("Mimicry.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int MAGIC = 4;

    private static final int SECONDMAGIC = 2;
    private static final int UPGRADE_PLUS_SECONDMAGIC = 2;

    // /STAT DECLARATION/


    public Mimicry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.misc = 0;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.exhaust = true;
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CleanIncreaseMiscAction(this.uuid, this.defaultSecondMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new CamouflagePower(p,p, this.magicNumber), this.magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECONDMAGIC);
            this.initializeDescription();
        }
    }
}
