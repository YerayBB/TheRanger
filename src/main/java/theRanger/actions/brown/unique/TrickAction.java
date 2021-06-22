package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class TrickAction extends AbstractGameAction {

    public TrickAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {
                AbstractCard.CardType cardType = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).type;
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(cardType).makeCopy();
                c.setCostForTurn(-99);
                addToBot(new MakeTempCardInHandAction(c, true));
            }
            this.isDone = true;
        }
    }
}
