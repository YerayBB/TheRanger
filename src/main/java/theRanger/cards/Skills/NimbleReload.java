package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;

import static theRanger.DefaultMod.makeCardPath;

public class NimbleReload extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(NimbleReload.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("NimbleReload.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    private static final int SECONDMAGIC = 1;



    // /STAT DECLARATION/


    public NimbleReload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new EssencePower(p, this.magicNumber), this.magicNumber));
        addToBot(new DrawCardAction(p,this.defaultSecondMagicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.initializeDescription();
        }
    }
}
