package theRanger.actions.brown.unique;

import basemod.devcommands.hand.HandAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AgilityAction extends AbstractGameAction {

    private boolean upgraded;

    public AgilityAction(int amount ,boolean isupgrade){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = isupgrade;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            if (this.upgraded) {
                int energy = this.amount + AbstractDungeon.player.hand.size() - 10;
                if (energy > 0) {
                    addToTop(new GainEnergyAction(energy));
                }
            }
            addToTop(new DrawCardAction(this.amount));
            this.isDone = true;
        }
    }
}
