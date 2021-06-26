package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.DensityPower;
import theRanger.powers.brown.SpiritPower;

import static theRanger.DefaultMod.makeCardPath;

public class Absorption extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Absorption.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("Prune.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;

    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final int SECONDMAGIC = 2;
    private static final int UPGRADE_PLUS_SECONDMAGIC = -1;


    // /STAT DECLARATION/


    public Absorption() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DensityPower(p, p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new SpiritPower(p, -this.defaultSecondMagicNumber), -this.defaultSecondMagicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECONDMAGIC);
            this.initializeDescription();
        }
    }
}
