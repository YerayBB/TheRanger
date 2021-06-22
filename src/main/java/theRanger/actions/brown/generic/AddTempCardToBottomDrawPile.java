package theRanger.actions.brown.generic;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class AddTempCardToBottomDrawPile extends AbstractGameAction {

    private AbstractCard c;

    public AddTempCardToBottomDrawPile(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.c = card;
    }

    public void update() {
        if (this.duration == this.startDuration) {

            UnlockTracker.markCardAsSeen(this.c.cardID);
            if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
                this.c.upgrade();
            }

            AbstractDungeon.player.drawPile.addToBottom(this.c);


            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
