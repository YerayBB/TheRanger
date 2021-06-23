package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRanger.powers.brown.InfusedPower;
import theRanger.util.ShotHelper;

import java.util.Iterator;

public class WarningShotAction extends AbstractGameAction {

    private static UIStrings UIstrings = CardCrawlGame.languagePack.getUIString("theRanger:MissingEssence");

    private int weak;
    private int vulnerable;
    private int shot;

    public WarningShotAction(AbstractCreature mainTarget, int weak, int vulnerable, int shot){
        this.weak = weak;
        this.vulnerable = vulnerable;
        this.shot = shot;
        this.target = mainTarget;
        this.actionType = ActionType.DEBUFF;
        this.source = AbstractDungeon.player;

    }

    @Override
    public void update() {
        if(!this.isDone) {
            if (this.shot == ShotHelper.Shot(this.shot)) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

                    Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

                    while (var3.hasNext()) {
                        AbstractMonster monster = (AbstractMonster) var3.next();
                        if (!monster.isDead && !monster.isDying) {
                            this.addToTop(new ApplyPowerAction(monster, this.source, new WeakPower(monster, this.weak, false), this.weak));
                        }
                    }
                }
                addToTop(new ApplyPowerAction(this.target, this.source, new VulnerablePower(this.target, this.vulnerable, false), this.vulnerable));
            } else {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, UIstrings.TEXT[0], true));
            }
            this.isDone = true;
        }
    }
}
