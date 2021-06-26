package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.CamouflagePower;

import static theRanger.DefaultMod.makeCardPath;

public class Disguise extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Disguise.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("Disguise.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    private static final int MAGIC = 10;
    private static final int UPGRADE_PLUS_MAGIC = 5;

    // /STAT DECLARATION/


    public Disguise() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p,p,new CamouflagePower(p,p,this.magicNumber), this.magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.initializeDescription();
        }
    }
}
