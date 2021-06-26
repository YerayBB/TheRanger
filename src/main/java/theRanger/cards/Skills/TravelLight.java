package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;

import static theRanger.DefaultMod.makeCardPath;

public class TravelLight extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TravelLight.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("TravelLight.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    // /STAT DECLARATION/


    public TravelLight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(EssencePower.POWER_ID)){
            int amount = Math.min(p.getPower(EssencePower.POWER_ID).amount, this.magicNumber);
            if(amount > 0){
                addToBot(new ReducePowerAction(p,p,EssencePower.POWER_ID, amount));
                addToBot(new DrawCardAction(amount));
            }
        }
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
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.initializeDescription();
        }
    }
}
