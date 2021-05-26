package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theRanger.util.ShotHelper;

import java.util.Objects;

public class StandardShotAction extends AbstractGameAction {

    private AbstractGameAction[] action;
    private int amount;
    private String failMessage;


    public StandardShotAction(final int amount, final AbstractGameAction[] action, final String failIDUIString) {
        this.amount = amount;
        this.action = action;
        this.failMessage = CardCrawlGame.languagePack.getUIString(failIDUIString).TEXT[0];
        actionType = ActionType.SPECIAL;
    }

    public StandardShotAction(final int amount, final AbstractGameAction[] action) {
        this.amount = amount;
        this.action = action;
        this.failMessage = CardCrawlGame.languagePack.getUIString("theRanger:MissingEssence").TEXT[0];
        actionType = ActionType.SPECIAL;
    }


    @Override
    public void update() {
        if(!this.isDone) {
            if (this.amount == ShotHelper.Shot(this.amount)) {
                for (AbstractGameAction abstractGameAction : this.action) {
                    addToTop(abstractGameAction);
                }
            } else {
                if (!Objects.equals(this.failMessage, "") && this.failMessage != null) {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, failMessage, true));
                }
            }
            this.isDone = true;
        }
    }
}
