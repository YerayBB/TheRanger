package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SpinKickAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractCard card;
    private AbstractPlayer p;

    private int energyOnUse;

    public SpinKickAction(AbstractPlayer p, AbstractCard card, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.card = card;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;

        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            AttackEffect ae;
            for(int i = 0; i < effect; ++i) {
                ae = i%2==0 ? AttackEffect.BLUNT_LIGHT : AttackEffect.BLUNT_HEAVY;
                addToBot(new AttackDamageRandomEnemyAction(this.card, ae));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
