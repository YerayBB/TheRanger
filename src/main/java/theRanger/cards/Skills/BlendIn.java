package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.CamouflagePower;

import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class BlendIn extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BlendIn.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("BlendIn.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;


    private static final int MAGIC = 10;
    private static final int UPGRADE_PLUS_MAGIC = 3;

    // /STAT DECLARATION/


    public BlendIn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var4 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var4.hasNext()) {
            AbstractMonster m2 = (AbstractMonster)var4.next();
            if (!m2.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(p, p, new CamouflagePower(p,p,this.magicNumber), this.magicNumber));
            }
        }
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
