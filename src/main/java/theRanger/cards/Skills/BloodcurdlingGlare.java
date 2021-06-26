package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.StandardStealthAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.CamouflagePower;
import theRanger.powers.brown.KukriPower;

import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class BloodcurdlingGlare extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BloodcurdlingGlare.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("BloodcurdlingAppearance.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int MAGIC = 1;
    private static final int SECONDMAGIC = 2;

    // /STAT DECLARATION/


    public BloodcurdlingGlare() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = SECONDMAGIC;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new KukriPower(p,p, this.magicNumber),this.magicNumber));

        Iterator var4 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var4.hasNext()) {
            AbstractMonster m2 = (AbstractMonster)var4.next();
            if (!m2.isDeadOrEscaped()) {
                addToBot(new StandardStealthAction(m2, p, new AbstractGameAction[]{
                        new ApplyPowerAction(m2, p, new VulnerablePower(m2, this.defaultSecondMagicNumber, false), this.defaultSecondMagicNumber)}));
            }
        }

    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
