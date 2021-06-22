package theRanger.cards.Attacks;

import com.badlogic.gdx.math.MathUtils;
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

import java.util.ArrayList;
import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class Bloodshed extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Bloodshed.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("Bloodshed.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int KUKRICOST = 1;

    private static final UIStrings UITEXT = CardCrawlGame.languagePack.getUIString("TheRanger:MissingKukri");

    // /STAT DECLARATION/


    public Bloodshed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(KukriPower.POWER_ID)) {
            int count = p.getPower(KukriPower.POWER_ID).amount;
/*V1
            Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var.hasNext() && count >= KUKRICOST) {
                AbstractMonster m2 = (AbstractMonster) var.next();
                if (!m2.isDeadOrEscaped()) {
                    count -= KUKRICOST;
                    addToBot(new ReducePowerAction(p, p, KukriPower.POWER_ID, KUKRICOST));
                    addToBot(new HeavyWoundAttackAction(m2, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                }
            }
            if(count < KUKRICOST) {
                while (var.hasNext()) {
                    AbstractMonster m3 = (AbstractMonster) var.next();
                    if (!m3.isDeadOrEscaped()){
                        AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[1], true));
                        break;
                    }

                }
            }
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[0], true));
        }
 */
            Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            ArrayList tmp = new ArrayList();
            while (var.hasNext()){
                AbstractMonster mo = (AbstractMonster) var.next();
                if(!mo.halfDead && !mo.isDying && !mo.isEscaping){
                    tmp.add(mo);
                }
            }
            while (!tmp.isEmpty() && count >= KUKRICOST){
                AbstractMonster mo = (AbstractMonster) tmp.get(MathUtils.random(0, tmp.size() - 1));
                addToBot(new HeavyWoundAttackAction(mo,new DamageInfo(p,this.damage,this.damageTypeForTurn)));
                addToBot(new ReducePowerAction(p, p, KukriPower.POWER_ID, KUKRICOST));
                tmp.remove(mo);
            }
            if(!tmp.isEmpty()){
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[1], true));
            }
        }
        else{
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, UITEXT.TEXT[0], true));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            int count = 0;
            Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while(var.hasNext()) {
                AbstractMonster m2 = (AbstractMonster) var.next();
                if (!m2.isDeadOrEscaped()) {
                    ++count;
                }
            }
            if(AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount >= count){
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
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }
}
