package theRanger.cards.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.DensityPower;
import theRanger.powers.brown.DonationPower;

import static theRanger.DefaultMod.makeCardPath;

public class Donation extends AbstractDynamicCard {

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(Donation.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");//makeCardPath("Donation.png")

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public Donation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new DensityPower(p,p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p,p, new DonationPower(p,p,1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.initializeDescription();
        }
    }
}