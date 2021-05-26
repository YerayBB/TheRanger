package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.RandomHeavyWoundAttackAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.KukriPower;

import static theRanger.DefaultMod.makeCardPath;

public class Bloodlust extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Bloodlust.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("Bloodlust.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 3;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final UIStrings UITEXT = CardCrawlGame.languagePack.getUIString("TheRanger:MissingKukri");

    // /STAT DECLARATION/


    public Bloodlust() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(KukriPower.POWER_ID)){
            int aux = p.getPower(KukriPower.POWER_ID).amount;
            if(aux > 0){

                for(int i = 0; i<aux; i++){
                    addToBot((new ReducePowerAction(p,p,KukriPower.POWER_ID,1)));
                    addToBot(new RandomHeavyWoundAttackAction(this.baseDamage));
                }
            }
            else {
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[0],true));
            }
        }
        else{
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[0],true));
        }

    }


    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount > 0){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
