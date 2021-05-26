package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.util.StealthHelper;

public class StandardStealthAction extends AbstractGameAction {

    private AbstractGameAction[] action;


    public StandardStealthAction(final AbstractCreature target, final AbstractCreature source, final AbstractGameAction[] action) {
        this.target = target;
        this.source = source;
        this.action = action;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            if(StealthHelper.StealthCheck(this.source, (AbstractMonster) this.target)){
                for (AbstractGameAction abstractGameAction : this.action) {
                    addToTop(abstractGameAction);
                }
            }
            this.isDone = true;
        }
    }
}

