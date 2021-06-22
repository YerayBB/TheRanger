package theRanger.actions.brown.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.InfusedPower;

import java.util.Iterator;

public class SoulWaveAction extends AbstractGameAction {

    private int times;
    private int infuse;

    public SoulWaveAction(int block, int infuse, int times){
        this.source = AbstractDungeon.player;
        this.amount = block;
        this.times = times;
        this.infuse = infuse;
        this.actionType = ActionType.SPECIAL;
    }



    @Override
    public void update() {
        if(!this.isDone){
            int allowedTimes = 0;
            if(this.source.hasPower(EssencePower.POWER_ID)){
                allowedTimes = Math.min(this.source.getPower(EssencePower.POWER_ID).amount, this.times);
            }
            while(allowedTimes > 0){
                //AOE Infuse
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

                    Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

                    while(var3.hasNext()) {
                        AbstractMonster monster = (AbstractMonster)var3.next();
                        if (!monster.isDead && !monster.isDying) {
                            this.addToTop(new ApplyPowerAction(monster, this.source, new InfusedPower(monster, this.source, this.infuse), this.infuse));
                        }
                    }
                }
                //block
                addToTop(new GainBlockAction(this.source, this.amount));
                //VFX
                if (Settings.FAST_MODE) {
                    addToTop(new VFXAction(this.source, new ShockWaveEffect(this.source.hb.cX, this.source.hb.cY, Color.CYAN, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.3F));
                } else {
                    addToTop(new VFXAction(this.source, new ShockWaveEffect(this.source.hb.cX, this.source.hb.cY, Color.CYAN, ShockWaveEffect.ShockWaveType.ADDITIVE), 1.5F));
                }
                //use essence
                addToTop(new ReducePowerAction(this.source,this.source,EssencePower.POWER_ID,1));

                allowedTimes -= 1;
            }
            this.isDone = true;
        }
    }
}
