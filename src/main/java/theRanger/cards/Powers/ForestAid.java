package theRanger.cards.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.cards.Skills.WillOWisp;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.ForestAidPower;

import static theRanger.DefaultMod.makeCardPath;

public class ForestAid extends AbstractDynamicCard {

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(ForestAid.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");//makeCardPath("ForestAid.png")

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int MAGIC = 2;

    private static final int SECONDMAGIC = 1;

    // /STAT DECLARATION/


    public ForestAid() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        this.cardsToPreview = new WillOWisp();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), this.magicNumber,true,true));
        addToBot(new ApplyPowerAction(p,p,new ForestAidPower(p,p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.initializeDescription();
        }
    }
}