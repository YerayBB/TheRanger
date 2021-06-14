package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.HeavyWoundAttackAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.KukriPower;

import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class Bloodbath extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Bloodbath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("Bloodbath.png");
    public static final UIStrings UITEXT = CardCrawlGame.languagePack.getUIString("TheRanger:MissingKukri");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;

    private static final int KUKRICOST = 1;

    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DMG = 7;

    // /STAT DECLARATION/


    public Bloodbath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(KukriPower.POWER_ID)) {
            if (p.getPower(KukriPower.POWER_ID).amount >= KUKRICOST) {
                addToBot(new ReducePowerAction(p,p,KukriPower.POWER_ID,KUKRICOST));
                Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                while (var.hasNext()) {
                    AbstractMonster mo = (AbstractMonster) var.next();
                    addToBot(new HeavyWoundAttackAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                }
            }
            else{
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[2], true));
            }
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[0], true));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount >= KUKRICOST){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }
}
