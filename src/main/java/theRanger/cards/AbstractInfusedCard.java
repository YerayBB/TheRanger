package theRanger.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theRanger.interfaces.AtInfuseModifyTrigger;

import java.util.Iterator;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractInfusedCard extends AbstractDefaultCard {

    public int infuseNumber;
    public int baseInfuseNumber;
    public boolean upgradedInfuseNumber;
    public boolean isInfuseNumberModified;

    public AbstractInfusedCard(final String id,
                              final String img,
                              final int cost,
                              final AbstractCard.CardType type,
                              final AbstractCard.CardColor color,
                              final AbstractCard.CardRarity rarity,
                              final AbstractCard.CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }

    public void upgradeInfuseNumber(int amount){
        this.baseInfuseNumber += amount;
        this.infuseNumber = baseInfuseNumber;
        this.upgradedInfuseNumber = true;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedInfuseNumber) {
            this.infuseNumber = this.baseInfuseNumber;
            this.isInfuseNumberModified = true;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        Iterator var;
        var = AbstractDungeon.player.powers.iterator();
        AbstractPower p;
        float tmp = this.baseInfuseNumber;
        this.isInfuseNumberModified = false;

        //apply player powers
        while(var.hasNext()) {
            p = (AbstractPower)var.next();
            if(p instanceof AtInfuseModifyTrigger) {
                tmp = ((AtInfuseModifyTrigger) p).AtInfuseModify(tmp);
                if (this.baseInfuseNumber != (int) tmp) {
                    this.isInfuseNumberModified = true;
                }
            }
        }

        //apply relics
        AbstractRelic r;
        var = AbstractDungeon.player.relics.iterator();
        while (var.hasNext()){
            r = (AbstractRelic) var.next();
            if(r instanceof AtInfuseModifyTrigger) {
                tmp = ((AtInfuseModifyTrigger) r).AtInfuseModify(tmp);
                if (this.baseInfuseNumber != (int) tmp) {
                    this.isInfuseNumberModified = true;
                }
            }
        }

        if(tmp < 0.0F) tmp = 0.0F;

        this.infuseNumber = MathUtils.floor(tmp);
    }
}
