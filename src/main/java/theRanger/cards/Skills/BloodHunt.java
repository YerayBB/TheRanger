package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.cards.Generated.BloodyCut;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.KukriPower;

import static theRanger.DefaultMod.makeCardPath;

public class BloodHunt extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BloodHunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("BloodHunt.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public BloodHunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.cardsToPreview = new BloodyCut(false, this.upgraded);
    }

//TODO
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new KukriPower(p,p,this.magicNumber), this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new BloodyCut(false, this.upgraded),1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.cardsToPreview.upgrade();
            this.initializeDescription();
        }
    }
}
